# Câu hỏi phỏng vấn Hibernate JPA

## 1. JPA là gì? Hibernate là gì? Khác nhau thế nào?

**Câu trả lời ngắn gọn:**
- JPA là chuẩn (specification) định nghĩa các quy tắc, API, annotation cho ORM trong Java. Hibernate là framework hiện thực (implementation) JPA, thực thi các quy tắc đó và cung cấp thêm nhiều tính năng nâng cao.

**Giải thích chi tiết:**
- **JPA (Java Persistence API):**
  - Định nghĩa các interface, annotation (@Entity, @Table, @Id, ...) và quy tắc ánh xạ giữa Java object và bảng dữ liệu (ORM).
  - Không phải là framework, không có mã nguồn thực thi, chỉ là chuẩn để các framework tuân theo.
  - Giúp code Java độc lập với framework ORM cụ thể, dễ thay thế (ví dụ: chuyển từ Hibernate sang EclipseLink, OpenJPA...).
- **Hibernate:**
  - Framework ORM phổ biến nhất hiện thực (implement) đầy đủ chuẩn JPA.
  - Cung cấp mã nguồn thực thi các API, annotation, ánh xạ Entity thành bảng, quản lý truy vấn, transaction, cache, lazy loading, ...
  - Bổ sung nhiều tính năng nâng cao ngoài JPA như batch, cache, native query, ...

**Ví dụ cụ thể:**
```java
// Khai báo Entity theo chuẩn JPA
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    // getter/setter
}

// Dùng JPA API (EntityManager)
@PersistenceContext
private EntityManager entityManager;

public void saveProduct(Product product) {
    entityManager.persist(product); // Lưu entity
}

// Hibernate thực thi các thao tác này, ánh xạ xuống DB
```

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]
- [[hibernate-jpa/example-hibernate-jpa.md]]

**Mẹo/Lưu ý:**
- Khi code theo JPA, có thể thay đổi implementation (Hibernate, EclipseLink, ...) mà không cần đổi code.
- Hibernate là implementation phổ biến nhất, mặc định trong Spring Boot.
- Khi cần tính năng đặc biệt, có thể dùng API riêng của Hibernate.

---

## 2. Khi nào framework hiểu được annotation @Entity, @Table, ...?

**Câu trả lời ngắn gọn:**
- Khi dùng Hibernate hoặc bất kỳ framework ORM nào tuân theo chuẩn JPA, các annotation như @Entity, @Table, @Id, ... đều được framework này tự động nhận diện và xử lý.

**Giải thích chi tiết:**
- Annotation như @Entity, @Table, @Id, ... là một phần của chuẩn JPA.
- Hibernate, EclipseLink, OpenJPA... đều là các implementation của JPA nên đều "đọc" và hiểu các annotation này.
- Khi ứng dụng khởi động, framework ORM sẽ quét (scan) các class có annotation JPA, tự động ánh xạ class thành bảng, trường thành cột, ...
- Không chỉ Hibernate, bất kỳ framework nào tuân theo JPA đều xử lý được các annotation này.

**Ví dụ cụ thể:**
```java
@Entity
@Table(name = "products")
public class Product { ... }
```
// Dùng Hibernate, EclipseLink, OpenJPA đều ánh xạ được class Product thành bảng products

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]

**Mẹo/Lưu ý:**
- Annotation JPA là chuẩn, nên code theo JPA để dễ thay đổi framework ORM mà không cần sửa code.
- Nếu dùng Hibernate, Spring Data JPA, EclipseLink... đều không cần đổi annotation.

---

## 3. Entity là gì? @Entity, @Table, @Id, @Column dùng để làm gì?

**Câu trả lời ngắn gọn:**
- Entity là một lớp Java được ghi chú @Entity, ánh xạ với một bảng trong cơ sở dữ liệu. Mỗi instance của Entity tương ứng với một dòng trong bảng.
- @Entity, @Table, @Id, @Column là các annotation dùng để ánh xạ trường/lớp Java với cột/bảng trong database.

**Giải thích chi tiết:**
- **Entity là gì?**
  - Entity là POJO (Plain Old Java Object) được Hibernate/JPA quản lý.
  - Mỗi Entity có một primary key (@Id) duy nhất để xác định bản ghi.
  - Hibernate tự động tạo/cập nhật bảng dựa trên cấu trúc Entity (nếu spring.jpa.hibernate.ddl-auto=create hoặc update).
  - Entity có thể có relationships (OneToMany, ManyToOne, ManyToMany, OneToOne) với các Entity khác.
  - Hibernate theo dõi thay đổi trên Entity và tự động cập nhật vào database.
- **@Entity, @Table, @Id, @Column là gì?**
  - **@Entity:** Đánh dấu lớp là một Entity, Hibernate sẽ quản lý lớp này.
  - **@Table:** Tuỳ chỉnh tên bảng trong database mà Entity ánh xạ tới.
  - **@Id:** Đánh dấu trường là primary key.
  - **@GeneratedValue:** Sinh giá trị tự động cho primary key.
  - **@Column:** Tuỳ chỉnh tên, kiểu, ràng buộc cột.

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

## 4. Hibernate ánh xạ Entity xuống Database như thế nào?

**Câu trả lời ngắn gọn:**
- Hibernate dùng annotation (hoặc XML) để ánh xạ class Java (Entity) thành bảng, trường thành cột, và tự động tạo/cập nhật bảng khi chạy ứng dụng.

**Giải thích chi tiết:**
- Khi ứng dụng khởi động, Hibernate đọc các class có @Entity, phân tích các annotation như @Table, @Column, @Id, @OneToMany, ...
- Hibernate tạo bảng (table), cột (column), khoá chính/phụ, quan hệ (relationship) dựa trên cấu trúc Entity.
- Nếu cấu hình spring.jpa.hibernate.ddl-auto=update/create, Hibernate sẽ tự động tạo/cập nhật bảng tương ứng với Entity.
- Các thuộc tính như tên bảng, tên cột, kiểu dữ liệu, ràng buộc (nullable, unique, length, ...) được ánh xạ theo annotation.
- Quan hệ giữa các Entity (OneToMany, ManyToOne, ...) sẽ ánh xạ thành foreign key hoặc bảng trung gian.

**Ví dụ cụ thể:**
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "price")
    private BigDecimal price;
}
// Hibernate sẽ tạo bảng products với các cột: id (PK), product_name, price

// Quan hệ:
@Entity
public class Order {
    @Id
    private Long id;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;
}

@Entity
public class OrderItem {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
// Hibernate sẽ tạo bảng order_item có cột order_id làm foreign key sang order
```

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]
- [[hibernate-jpa/example-hibernate-jpa.md]]

**Mẹo/Lưu ý:**
- Có thể dùng annotation hoặc file XML để ánh xạ, nhưng annotation phổ biến hơn.
- Khi đổi Entity, nên kiểm tra lại cấu trúc bảng để tránh mất dữ liệu (đặc biệt với ddl-auto=create/update).
- Có thể xem log SQL Hibernate sinh ra bằng cấu hình: spring.jpa.show-sql=true

---

## 5. Sự khác biệt giữa @OneToMany và @ManyToOne?

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

---

## 6. Lazy vs Eager loading là gì?

**Câu trả lời ngắn gọn:**
- Lazy: Chỉ tải dữ liệu khi truy cập; Eager: Tải luôn toàn bộ dữ liệu liên quan khi truy vấn.

**Giải thích chi tiết:**
- **Lazy (trì hoãn):** Dữ liệu liên quan chỉ được truy vấn khi gọi getter.
- **Eager (tức thì):** Hibernate sẽ join và lấy luôn dữ liệu liên quan khi truy vấn entity chính.
- Lazy giúp tối ưu hiệu suất, tránh tải dữ liệu không cần thiết.

**Ví dụ cụ thể:**
```java
@OneToMany(fetch = FetchType.LAZY)
private List<Order> orders;

@ManyToOne(fetch = FetchType.EAGER)
private User user;
```

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]

**Mẹo/Lưu ý:**
- Nên dùng LAZY cho collection, EAGER cho @ManyToOne nếu thực sự cần.

---

## 7. EntityManager là gì? Khác gì với JpaRepository?

**Câu trả lời ngắn gọn:**
- EntityManager là API chuẩn của JPA để thao tác với database, JpaRepository là interface Spring Data cung cấp sẵn các hàm CRUD.

**Giải thích chi tiết:**
- **EntityManager:** Quản lý vòng đời Entity, cung cấp các hàm persist, merge, remove, find, query...
- **JpaRepository:** Interface mở rộng từ CrudRepository, có sẵn các hàm CRUD, query động, phân trang, sort...
- Khi dùng JpaRepository, bạn không cần code SQL/JPQL thủ công.

**Ví dụ cụ thể:**
```java
// EntityManager
entityManager.persist(entity);
entityManager.find(Entity.class, id);

// JpaRepository
userRepository.save(entity);
userRepository.findById(id);
```

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]

**Mẹo/Lưu ý:**
- Dùng JpaRepository cho CRUD nhanh, EntityManager cho thao tác phức tạp.

---

## 8. Các giá trị của spring.jpa.hibernate.ddl-auto là gì? Nên dùng khi nào?

**Câu trả lời ngắn gọn:**
- spring.jpa.hibernate.ddl-auto có các giá trị: none, validate, update, create, create-drop. Mỗi giá trị quyết định cách Hibernate tạo/cập nhật bảng khi khởi động ứng dụng.

**Giải thích chi tiết:**
- **none**: Không can thiệp vào schema database.
- **validate**: Chỉ kiểm tra schema hiện tại có khớp với Entity không, nếu sai sẽ báo lỗi.
- **update**: Tự động cập nhật schema cho khớp với Entity (thêm bảng/cột mới, không xoá dữ liệu cũ).
- **create**: Xoá toàn bộ schema cũ, tạo lại mới hoàn toàn mỗi lần chạy ứng dụng.
- **create-drop**: Như create, nhưng khi tắt ứng dụng sẽ xoá schema.

**Ví dụ cụ thể:**
```properties
spring.jpa.hibernate.ddl-auto=update
# Khi thêm trường mới vào Entity, Hibernate sẽ tự động thêm cột vào bảng.

spring.jpa.hibernate.ddl-auto=create
# Mỗi lần chạy lại sẽ xoá toàn bộ bảng và tạo lại từ đầu.
```

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]
- [[hibernate-jpa/example-hibernate-jpa.md]]

**Mẹo/Lưu ý:**
- **update** phù hợp cho môi trường phát triển (dev), không nên dùng cho production vì có thể gây lỗi dữ liệu.
- **create**/**create-drop** chỉ nên dùng để test nhanh, không dùng cho môi trường thật.
- **validate** phù hợp khi muốn chắc chắn schema đúng, không tự động thay đổi.
- **none** dùng khi bạn tự quản lý schema thủ công.

---

## 9. NamedEntityGraph là gì?

**Câu trả lời ngắn gọn:**
- `@NamedEntityGraph` là annotation JPA/Hibernate cho phép định nghĩa trước các Entity Graph để kiểm soát việc fetch các thuộc tính liên quan khi truy vấn Entity, giúp tối ưu hiệu suất và tránh N+1 problem.

**Giải thích chi tiết:**
- Entity Graph là một tập hợp các thuộc tính (thường là quan hệ) sẽ được nạp cùng entity khi truy vấn.
- `@NamedEntityGraph` định nghĩa graph với tên cụ thể trên entity, có thể tái sử dụng khi truy vấn qua EntityManager hoặc Repository.
- Khi truy vấn, chỉ định tên graph để JPA/Hibernate biết cần fetch những thuộc tính nào (EAGER/LAZY tuỳ ý), tránh truy vấn dư thừa hoặc lazy loading nhiều lần.
- Rất hữu ích khi entity có nhiều quan hệ phức tạp, hoặc khi cần tối ưu số lượng query sinh ra.

**Ví dụ cụ thể:**
```java
@Entity
@NamedEntityGraph(
    name = "User.detail",
    attributeNodes = {
        @NamedAttributeNode("posts"),
        @NamedAttributeNode("roles")
    }
)
public class User { ... }

// Khi truy vấn:
EntityGraph<?> graph = entityManager.getEntityGraph("User.detail");
Map<String, Object> hints = new HashMap<>();
hints.put("javax.persistence.fetchgraph", graph);
User user = entityManager.find(User.class, id, hints);
```

**Liên kết kiến thức:**
- [[hibernate-jpa/hibernate-jpa.md]]
- [[hibernate-jpa/example-hibernate-jpa.md]]
- [[spring-boot/spring-boot.md]]

**Mẹo/Lưu ý:**
- Dùng EntityGraph khi cần truy vấn nhiều quan hệ phức tạp, tránh lazy loading nhiều lần.
- Có thể dùng với cả JPQL (setHint) hoặc Repository (Spring Data JPA hỗ trợ @EntityGraph).
- Đặt tên rõ ràng cho EntityGraph để dễ tái sử dụng.
