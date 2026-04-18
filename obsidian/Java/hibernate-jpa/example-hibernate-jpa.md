# Ví dụ Hibernate JPA

## 1. Entity & Mapping

### Basic Entity
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "age")
    private Integer age;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // getter/setter
}
```

## 2. One-to-Many & Many-to-One

### User (One side)
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }

    // getter/setter
}
```

### Post (Many side)
```java
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // getter/setter
}
```

## 3. Repository

### Basic Repository
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByAgeGreaterThan(Integer age);
}

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    List<Post> findByTitleContaining(String title);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<Post> findRecentPostsByUser(@Param("userId") Long userId);
}
```

## 4. Service

### CRUD Service
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, UserRequest request) {
        User user = getUserById(id);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Post createPost(Long userId, PostRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<Post> getUserPosts(Long userId) {
        return postRepository.findRecentPostsByUser(userId);
    }
}
```

## 5. Many-to-Many

### Category & Product
```java
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    // getter/setter
}

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_category",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    // getter/setter
}
```

## 6. Pagination

### Pageable Repository
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(String name, Pageable pageable);
}

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> searchProducts(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return productRepository.findByNameContaining(name, pageable);
    }
}

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> search(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> result = productService.searchProducts(name, page, size);
        return ResponseEntity.ok(result);
    }
}
```

## 7. Custom Query

### JPQL & Native SQL
```java
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // JPQL
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.status = :status")
    List<Order> findUserOrders(@Param("userId") Long userId, @Param("status") String status);

    // Native SQL
    @Query(value = "SELECT * FROM orders WHERE created_at >= DATE_SUB(NOW(), INTERVAL 30 DAY)", 
           nativeQuery = true)
    List<Order> findRecentOrders();

    // Projection
    @Query("SELECT new map(o.id as id, o.status as status, COUNT(d) as itemCount) " +
           "FROM Order o LEFT JOIN o.details d GROUP BY o.id")
    List<Map<String, Object>> getOrderSummary();
}
```

## Liên kết kiến thức
- [[hibernate-jpa/hibernate-jpa.md]]
- [[hibernate-jpa/interview-questions-hibernate-jpa.md]]
- [[spring-boot/spring-boot.md]] (Repository, Service)
- [[core-java/core-java.md]] (OOP, Collection)

