# Tổng hợp kiến thức Spring Security

## 1. Authentication vs Authorization

### Authentication (Xác thực)
- Xác nhận bạn là ai (kiểm tra danh tính).
- Dùng username/password, OAuth, JWT, ...
- Sau authentication, user nhận một token/session.

### Authorization (Phân quyền)
- Xác nhận bạn được phép làm gì (kiểm tra quyền hạn).
- Dùng role (ADMIN, USER) hoặc permission.
- Kiểm tra xem user có quyền truy cập tài nguyên không.

## 2. SecurityConfig

### SecurityConfig Setup
- Extends WebSecurityConfigurerAdapter (hoặc @Bean SecurityFilterChain trong Spring 5.7+).
- configure(HttpSecurity): quy định endpoint nào cần protection.
- configure(AuthenticationManagerBuilder): cấu hình authentication provider.

### Authorization Rules
- permitAll(): cho phép ai cũng truy cập.
- hasRole(role): chỉ người có role cụ thể.
- hasAnyRole(roles): cho phép một trong các role.
- authenticated(): yêu cầu phải login.
- anyRequest(): các request khác.

## 3. UserDetailsService

### Tùy chỉnh Authentication
- Implement UserDetailsService để tải user từ database.
- loadUserByUsername(username): trả về UserDetails.
- UserDetails: chứa username, password (đã mã hóa), authorities (role).

### PasswordEncoder
- Mã hóa password khi lưu vào database.
- Kiểm tra password khi login.
- BCryptPasswordEncoder là phổ biến nhất.

## 4. JWT (JSON Web Token)

### JWT Overview
- Token chứa thông tin user được ký bằng secret key.
- Client gửi token trong header Authorization: Bearer <token>.
- Server xác thực token mà không cần truy cập database mỗi lần.

### Structure
- Header: loại token, thuật toán ký.
- Payload: claims (dữ liệu user, role, expiry).
- Signature: chữ ký để xác minh token không bị sửa.

## 5. Session vs Stateless Authentication

### Session-based
- Server lưu session trong memory hoặc database.
- Client nhận session ID qua cookie.
- Mỗi request server kiểm tra session.
- Phù hợp: traditional web app.

### Stateless (JWT)
- Server không lưu state.
- Client gửi token mỗi request.
- Server chỉ xác minh signature.
- Phù hợp: REST API, microservices.

## 6. CORS (Cross-Origin Resource Sharing)

### CORS Configuration
- Cho phép request từ domain khác.
- @CrossOrigin trên controller.
- hoặc @Bean để cấu hình global.

## Liên kết kiến thức
- [[security/interview-questions-security.md]]
- [[security/example-security.md]]
- [[spring-boot/spring-boot.md]] (Configuration, Beans)
- [[core-java/core-java.md]] (Interface, Exception)

