# Câu hỏi phỏng vấn Spring Security

## 1. Authentication vs Authorization?

**Câu trả lời ngắn gọn:**
- **Authentication**: Xác nhận bạn là ai (kiểm tra username/password).
- **Authorization**: Xác nhận bạn được phép làm gì (kiểm tra quyền hạn).

**Giải thích chi tiết:**
- **Authentication (Xác thực)**:
  - Quá trình kiểm tra danh tính của người dùng.
  - Thường dùng username/password, OAuth, JWT token, ...
  - Kết quả: người dùng được xác nhận hay không.
  - Trong Spring Security: UsernamePasswordAuthenticationFilter, AuthenticationProvider.

- **Authorization (Phân quyền)**:
  - Quá trình kiểm tra những hành động mà người dùng được phép thực hiện.
  - Thường dùng role (ADMIN, USER, MANAGER) hoặc permission.
  - Kết quả: người dùng được phép truy cập tài nguyên hay không.
  - Trong Spring Security: @PreAuthorize, @Secured, @RolesAllowed.

**Ví dụ cụ thể:**
```java
// SecurityConfig: Cấu hình authentication & authorization
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Authentication: xác thực user từ database
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Authorization: phân quyền truy cập endpoint
        http
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll() // Ai cũng được truy cập
                .antMatchers("/api/admin/**").hasRole("ADMIN") // Chỉ ADMIN
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN") // USER hoặc ADMIN
                .anyRequest().authenticated() // Endpoint khác cần xác thực
            .and()
            .formLogin()
            .and()
            .logout();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// UserService: Triển khai UserDetailsService để authentication
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            true, // enabled
            true, // accountNonExpired
            true, // credentialsNonExpired
            true, // accountNonLocked
            getAuthorities(user) // Gán role/authority
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
            .collect(Collectors.toList());
    }
}

// Controller: Sử dụng @PreAuthorize để phân quyền
@RestController
@RequestMapping("/api")
public class MyController {
    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public data"; // Ai cũng truy cập được
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Authorization: chỉ USER/ADMIN
    public String userEndpoint() {
        return "User data";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") // Authorization: chỉ ADMIN
    public String adminEndpoint() {
        return "Admin data";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        // Authentication: kiểm tra username/password
        Authentication auth = new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        );
        Authentication authenticated = authenticationManager.authenticate(auth);
        return ResponseEntity.ok("Login successful");
    }
}
```

**Liên kết kiến thức:**
- [[security/spring-security.md]]
- [[security/example-security.md]]

**Mẹo/Lưu ý:**
- Authentication trước Authorization (phải xác thực trước mới được phân quyền).
- Role là danh sách các quyền (ví dụ: ADMIN có quyền create, read, update, delete).
- @PreAuthorize có thể dùng expression phức tạp: `@PreAuthorize("hasRole('ADMIN') and #id == principal.id")`.
- Luôn mã hóa password (BCryptPasswordEncoder) để bảo mật.

---

## 2. Cách cấu hình bảo mật endpoint?

**Câu trả lời ngắn gọn:**
- Dùng SecurityConfig (extends WebSecurityConfigurerAdapter) để cấu hình authorize endpoints theo role/permission, kết hợp với @PreAuthorize trên controller.

**Giải thích chi tiết:**
- Có hai cách cấu hình:
  1. **Declarative (Khai báo)**: Dùng @PreAuthorize, @Secured, @RolesAllowed trên method.
  2. **Imperative (Chỉ thị)**: Dùng SecurityConfig để cấu hình chung.
- Trong SecurityConfig.configure(HttpSecurity):
  - authorizeRequests(): Bắt đầu cấu hình quyền truy cập.
  - antMatchers(pattern): Chỉ định URL pattern cần bảo vệ.
  - permitAll(): Cho phép ai cũng truy cập.
  - hasRole(role): Chỉ người dùng có role cụ thể.
  - hasAnyRole(roles): Cho phép một trong các role.
  - authenticated(): Yêu cầu phải xác thực.
  - anyRequest(): Các request khác.

**Ví dụ cụ thể:**
```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Kích hoạt @PreAuthorize
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Tắt CSRF để dễ test
            .authorizeRequests()
                // Public endpoints
                .antMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                // User endpoints
                .antMatchers(HttpMethod.GET, "/api/orders/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/orders").hasRole("USER")

                // Admin endpoints
                .antMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/categories/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")

                // Bất kỳ request nào khác đều phải xác thực
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
            .and()
            .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// Controller: Sử dụng @PreAuthorize để bảo vệ method
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll(); // Public
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Chỉ ADMIN
    public ResponseEntity<Category> create(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Chỉ ADMIN
    public ResponseEntity<Category> update(
            @PathVariable Long id,
            @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Chỉ ADMIN
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Liên kết kiến thức:**
- [[security/spring-security.md]]
- [[security/example-security.md]]

**Mẹo/Lưu ý:**
- Quy tắc cấu hình: từ cụ thể đến chung (ví dụ: /admin/** trước /api/**).
- @PreAuthorize được kiểm tra ở method level, SecurityConfig ở request level.
- Nên tắt CSRF (csrf().disable()) khi phát triển REST API, nhưng bật ở production.
- Dùng hasRole('ROLE_NAME') để kiểm tra role (Spring tự thêm "ROLE_" prefix).
- Có thể kết hợp nhiều điều kiện: `@PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id)")`.

