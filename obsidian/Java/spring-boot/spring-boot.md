# Tổng hợp kiến thức Spring Boot

## 1. Spring Boot là gì?

### Định nghĩa
- Spring Boot là framework built trên Spring Framework, giúp tạo standalone application dễ dàng.
- Tự động cấu hình, embedded server (Tomcat), giảm boilerplate code.

### Ưu điểm
- Convention over Configuration (tự động cấu hình).
- Embedded server (không cần deploy WAR).
- Starter dependencies (dễ thêm thư viện).
- Production-ready (monitoring, logging, health check).

## 2. Dependency Injection (DI) & Beans

### Dependency Injection
- Cơ chế cung cấp dependency (phụ thuộc) từ bên ngoài, không khởi tạo bên trong.
- Giảm coupling, tăng testability.

### Spring Beans
- Object được Spring quản lý, tạo, inject vào các class khác.
- Khai báo: @Component, @Service, @Repository, @Controller, @Configuration.

### Scopes
- **Singleton** (mặc định): một instance cho toàn ứng dụng.
- **Prototype**: một instance mới mỗi lần inject.
- **Request/Session**: một instance per request/session.

## 3. Annotations trong Spring Boot

### Controller & Routing
- @RestController: kết hợp @Controller + @ResponseBody.
- @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping.
- @PathVariable, @RequestParam, @RequestBody.

### Service & Repository
- @Service: lớp business logic.
- @Repository: lớp data access.
- @Autowired: inject dependency.

### Configuration
- @Configuration: class cấu hình.
- @Bean: phương thức tạo bean.
- @EnableConfigurationProperties: ánh xạ properties vào class.
- @Value: inject giá trị từ properties.

## 4. Exception Handling

### Global Exception Handler
- @ControllerAdvice: xử lý exception toàn cầu.
- @ExceptionHandler: xử lý loại exception cụ thể.

### Custom Exception
- Tạo class kế thừa RuntimeException hoặc Exception.
- Throw khi gặp lỗi nghiệp vụ.
- GlobalExceptionHandler sẽ bắt và trả về response phù hợp.

## 5. Database & ORM

### JPA (Java Persistence API)
- Tiêu chuẩn quản lý entity, tương tác với database.
- Hibernate là implementation phổ biến nhất.

### Entity
- POJO được khai báo @Entity, ánh xạ với bảng database.
- @Id: primary key, @Column: tên cột.

### Repository
- Kế thừa JpaRepository, tự động có CRUD methods.
- Có thể tạo custom query với @Query.

## 6. Properties & Configuration

### application.properties
- Lưu các giá trị cấu hình như database, port, logging level.
- Có thể dùng @Value hoặc @ConfigurationProperties để inject.

### Profile
- application-dev.properties, application-prod.properties.
- Chọn profile khi run: spring.profiles.active=dev.

## Liên kết kiến thức
- [[spring-boot/interview-questions-spring-boot.md]]
- [[spring-boot/example-spring-boot.md]]
- [[core-java/core-java.md]] (OOP, Exception)
- [[hibernate-jpa/hibernate-jpa.md]] (Entity, Repository)
- [[security/spring-security.md]] (Authentication)

