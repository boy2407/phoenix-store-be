# Tổng hợp kiến thức Hibernate JPA

## 1. JPA (Java Persistence API)

### Định nghĩa
- JPA là tiêu chuẩn Java cho ORM (Object-Relational Mapping).
- Giúp ánh xạ object Java với bảng trong database.
- Hibernate là implementation JPA phổ biến nhất.

### Entity
- POJO được khai báo @Entity, ánh xạ với một bảng.
- Mỗi instance = một dòng trong bảng.
- @Id: primary key, @Column: tên cột.

## 2. Relationships

### One-to-Many & Many-to-One
- **@OneToMany**: một bản ghi có nhiều bản ghi liên quan.
- **@ManyToOne**: nhiều bản ghi liên quan đến một bản ghi.
- Luôn có một "owner side" (sở hữu foreign key) và "non-owner side" (dùng mappedBy).

### Many-to-Many
- @ManyToMany: ánh xạ hai chiều giữa hai entity.
- Thường tạo bảng trung gian (join table).
- Có thể là unidirectional (một chiều) hoặc bidirectional (hai chiều).

### One-to-One
- @OneToOne: quan hệ một-một giữa hai entity.
- Thường dùng khi muốn tách thông tin chi tiết vào bảng riêng.

## 3. Annotations quan trọng

### @Entity, @Table
```java
@Entity
@Table(name = "users") // Tên bảng
public class User { }
```

### @Id, @GeneratedValue
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

### @Column
```java
@Column(name = "email", nullable = false, unique = true, length = 100)
private String email;
```

### @Temporal, @CreationTimestamp, @UpdateTimestamp
```java
@Column(updatable = false)
@CreationTimestamp
private LocalDateTime createdAt;

@UpdateTimestamp
private LocalDateTime updatedAt;
```

## 4. Repository Pattern

### JpaRepository
- Interface kế thừa JpaRepository tự động có CRUD methods.
- Không cần triển khai, Spring tự sinh code.
- @Query: định nghĩa custom query (JPQL hoặc native SQL).

### Pagination & Sorting
- Dùng Pageable, Page để phân trang.
- Dùng Sort để sắp xếp.

## 5. Query Methods

### Named Query vs @Query
- **Named Query**: khai báo trong @Entity, tái sử dụng.
- **@Query**: viết trực tiếp trong repository method.

### JPQL vs Native Query
- **JPQL**: viết bằng entity name (ví dụ: `FROM User WHERE email = :email`).
- **Native Query**: viết SQL thô (ví dụ: `SELECT * FROM users WHERE email = ?`).

## 6. Fetch Strategy

### FetchType.LAZY vs EAGER
- **LAZY**: tải data khi cần (mặc định với collection).
- **EAGER**: tải ngay (mặc định với single object).
- LAZY tiết kiệm memory nhưng có thể gây N+1 problem.

### N+1 Problem
- Khi truy vấn parent, mỗi item phải truy vấn child (1 + n queries).
- Giải pháp: dùng JOIN FETCH, @Query custom, hoặc tìm nạp trước.

## 7. Cascade & Orphan Deletion

### Cascade
- cascade = CascadeType.ALL: xoá parent → xoá tất cả child.
- cascade = CascadeType.PERSIST: persist parent → persist child.

### Orphan Deletion
- orphanRemoval = true: xoá child khỏi collection → xoá child khỏi database.

## Liên kết kiến thức
- [[hibernate-jpa/interview-questions-hibernate-jpa.md]]
- [[hibernate-jpa/example-hibernate-jpa.md]]
- [[spring-boot/spring-boot.md]] (Repository)
- [[core-java/core-java.md]] (OOP)

