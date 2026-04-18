# Ví dụ Spring Boot

## 1. Dependency Injection & Beans

### Service Injection
```java
@Service
public class UserService {
    public String getUserInfo(Long id) {
        return "User #" + id;
    }
}

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }
}
```

### Configuration & Beans
```java
@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

## 2. Controller & Routing

### REST Endpoints
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Query Parameters & Path Variables
```java
@RestController
@RequestMapping("/api/search")
public class SearchController {
    @GetMapping
    public List<Product> search(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return searchService.search(name, page, size);
    }

    @GetMapping("/{category}/{id}")
    public Product getByCategory(
            @PathVariable String category,
            @PathVariable Long id) {
        return productService.getByCategoryAndId(category, id);
    }
}
```

## 3. Service & Business Logic

### Service Pattern
```java
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Transactional
    public Order createOrder(OrderRequest request) {
        // Validate
        if (request.getItems().isEmpty()) {
            throw new BadRequestException("Order must have items");
        }

        // Process
        Order order = new Order();
        order.setStatus("PENDING");
        order.setItems(request.getItems());

        // Save
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
```

## 4. Exception Handler

### Global Exception Handler
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(status).body(response);
    }
}
```

## 5. Properties & Configuration

### application.properties
```properties
# Server
server.port=8080
server.servlet.context-path=/api

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update

# Logging
logging.level.root=INFO
logging.level.com.example=DEBUG

# Custom Properties
app.name=My Store
app.version=1.0.0
```

### ConfigurationProperties
```java
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {
    private String name;
    private String version;
    private Database database;

    public static class Database {
        private String host;
        private int port;
        // getter/setter
    }

    // getter/setter
}

@RestController
public class InfoController {
    @Autowired
    private AppProperties appProperties;

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("name", appProperties.getName());
        info.put("version", appProperties.getVersion());
        return info;
    }
}
```

## 6. Data Access (Repository)

### Repository & Custom Queries
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.age > :minAge")
    List<User> findUsersOlderThan(@Param("minAge") int minAge);

    @Query(value = "SELECT * FROM users WHERE email LIKE %?1%", nativeQuery = true)
    List<User> searchByEmail(String email);
}

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<User> getAdultUsers() {
        return userRepository.findUsersOlderThan(18);
    }
}
```

## Liên kết kiến thức
- [[spring-boot/spring-boot.md]]
- [[spring-boot/interview-questions-spring-boot.md]]
- [[core-java/core-java.md]] (OOP, Exception)
- [[hibernate-jpa/hibernate-jpa.md]] (Entity, Repository)
- [[security/spring-security.md]] (Configuration)

