# Câu hỏi phỏng vấn Hibernate JPA

## 1. Entity là gì?

**Câu trả lời ngắn gọn:**
- Entity là một lớp Java được ghi chú @Entity, ánh xạ với một bảng trong cơ sở dữ liệu. Mỗi instance của Entity tương ứng với một dòng trong bảng.

**Giải thích chi tiết:**
- Entity là POJO (Plain Old Java Object) được Hibernate/JPA quản lý.
- Mỗi Entity có một primary key (@Id) duy nhất để xác định bản ghi.
- Hibernate tự động tạo/cập nhật bảng dựa trên cấu trúc Entity (nếu spring.jpa.hibernate.ddl-auto=create hoặc update).
- Entity có thể có relationships (OneToMany, ManyToOne, ManyToMany, OneToOne) với các Entity khác.
- Hibernate theo dõi thay đổi trên Entity và tự động cập nhật vào database.

**Ví dụ cụ thể:**
```java
// Entity
@Entity
@Table(name = "users") // Tên bảng
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key, tự động tăng

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // getter/setter
}

// Sử dụng
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return userRepository.save(user); // Lưu vào database
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
```

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]
- [[hibernate-jpa/example-hibernate-jpa.md]]

**Mẹo/Lưu ý:**
- Entity phải có constructor không tham số (có thể là private) để Hibernate khởi tạo.
- @Id là bắt buộc trong mỗi Entity.
- @Column là tùy chọn, nếu không có, Hibernate dùng tên trường Java làm tên cột.
- Entity nên immutable ở các trường quan trọng, dùng getter/setter.

---

## 2. Sự khác biệt giữa @OneToMany và @ManyToOne?

**Câu trả lời ngắn gọn:**
- @OneToMany: Một bản ghi có nhiều bản ghi liên quan (ví dụ: một User có nhiều Post).
- @ManyToOne: Nhiều bản ghi cùng liên quan đến một bản ghi (ví dụ: nhiều Post cùng thuộc một User).

**Giải thích chi tiết:**
- **@OneToMany**: Dùng trên cạnh "one" (phía lớp cha), khai báo collection (List, Set) các đối tượng liên quan.
  - Thường kết hợp với mappedBy để chỉ định trường foreign key ở phía "many".
  - Mặc định tạo bảng trung gian (join table), nhưng có thể tùy chỉnh.
- **@ManyToOne**: Dùng trên cạnh "many" (phía lớp con), khai báo tham chiếu đến một đối tượng.
  - Sở hữu foreign key (ngoại khóa) trực tiếp.
  - Thường là phía sở hữu (owner side) của relationship.
- **Quy tắc**: Một relationship có một owner side (sở hữu foreign key) và một non-owner side (dùng mappedBy).

**Ví dụ cụ thể:**
```java
// User: cạnh "One"
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>(); // Non-owner side

    // getter/setter
}

// Post: cạnh "Many"
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Foreign key, Owner side
    private User user;

    // getter/setter
}

// Sử dụng
@Service
public class PostService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public void createPost(Long userId, String title, String content) {
        User user = userRepository.findById(userId).orElseThrow();

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user); // Set user (owner side)

        postRepository.save(post);
        // post.getUser().getPosts() tự động cập nhật (non-owner side)
    }

    public List<Post> getUserPosts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return user.getPosts(); // Lấy tất cả bài viết của user
    }
}
```

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]
- [[hibernate-jpa/example-hibernate-jpa.md]]

**Mẹo/Lưu ý:**
- Luôn set cả hai phía (owner side và non-owner side) khi tạo/cập nhật relationship để tránh dữ liệu không đồng bộ.
- @ManyToOne không cần khai báo mappedBy (vì nó sở hữu foreign key).
- @OneToMany cần mappedBy để chỉ định trường foreign key ở phía @ManyToOne.
- cascade = CascadeType.ALL: khi xoá user, tất cả post của user cũng bị xoá.
- fetch = FetchType.LAZY: tải dữ liệu liên quan khi cần (hiệu suất tốt hơn).

