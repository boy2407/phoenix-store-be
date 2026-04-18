# Câu hỏi phỏng vấn Spring Boot

## 1. @EnableConfigurationProperties là gì?

**Câu trả lời ngắn gọn:**
- @EnableConfigurationProperties giúp bạn tự động ánh xạ các thuộc tính từ file cấu hình (application.properties) vào một lớp Java (configuration class).

**Giải thích chi tiết:**
- Dùng để kích hoạt @ConfigurationProperties trên một lớp cụ thể.
- Cho phép Spring tự động inject các giá trị từ application.properties vào các trường của lớp.
- Thường dùng khi bạn muốn tách biệt các cấu hình (như database URL, port, timeout) ra khỏi code.
- Bạn có thể dùng @Component + @ConfigurationProperties thay vì @EnableConfigurationProperties.

**Ví dụ cụ thể:**
```java
// File: application.properties
app.name=My Store
app.version=1.0.0
app.database.url=jdbc:mysql://localhost:3306/mydb
app.database.username=root
app.database.password=1234

// File: AppConfig.java
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String name;
    private String version;
    private Database database;

    public static class Database {
        private String url;
        private String username;
        private String password;
        // getter/setter
    }

    // getter/setter
}

// File: Application.java
@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// File: Controller.java
@RestController
public class MyController {
    @Autowired
    private AppConfig appConfig;

    @GetMapping("/config")
    public String getConfig() {
        return "App: " + appConfig.getName() + ", Version: " + appConfig.getVersion();
    }
}
```

**Liên kết kiến thức:**
- [[spring-boot/spring-boot.md]]
- [[spring-boot/example-spring-boot.md]]

**Mẹo/Lưu ý:**
- @ConfigurationProperties class phải có getter/setter để Spring ánh xạ giá trị.
- Có thể dùng @Value để ánh xạ một thuộc tính duy nhất, nhưng @ConfigurationProperties tiện hơn khi có nhiều thuộc tính.
- Spring Boot tự động quét @ConfigurationProperties nếu dùng @Component + @ConfigurationProperties, không cần @EnableConfigurationProperties.

---

## 2. Cách xử lý exception trong Spring Boot?

**Câu trả lời ngắn gọn:**
- Dùng @ControllerAdvice + @ExceptionHandler để xử lý tập trung các exception và trả về response phù hợp.

**Giải thích chi tiết:**
- @ControllerAdvice: Lớp này sẽ xử lý exception toàn cầu (global exception handler).
- @ExceptionHandler: Phương thức được ghi chú này sẽ xử lý một loại exception cụ thể.
- Có thể trả về ResponseEntity với status code và message tùy chỉnh.
- Cách xử lý:
  1. Tạo custom exception (ví dụ: BadRequestException, NotFoundException).
  2. Tạo GlobalExceptionHandler với @ControllerAdvice.
  3. Định nghĩa @ExceptionHandler cho từng loại exception.
  4. Throw exception khi cần (ví dụ: throw new BadRequestException("Lỗi")).

**Ví dụ cụ thể:**
```java
// Custom Exception
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}

// Global Exception Handler
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(BadRequestException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

// Service
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public User createUser(UserRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new BadRequestException("User name is required");
        }
        return userRepository.save(new User(request.getName()));
    }
}

// Controller
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
        // Nếu không tìm thấy, sẽ throw NotFoundException → GlobalExceptionHandler xử lý
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
        // Nếu name null, sẽ throw BadRequestException → GlobalExceptionHandler xử lý
    }
}
```

**Liên kết kiến thức:**
- [[spring-boot/spring-boot.md]]
- [[spring-boot/example-spring-boot.md]]
- [[core-java/runtimeexception.md]]

**Mẹo/Lưu ý:**
- @ControllerAdvice chỉ xử lý exception từ controller, không xử lý filter hoặc interceptor.
- Có thể dùng @RestControllerAdvice (tương đương @ControllerAdvice + @ResponseBody).
- Thứ tự @ExceptionHandler: từ cụ thể đến chung (ví dụ: BadRequestException trước Exception).
- Nên tạo custom exception để dễ xử lý và bảo trì.

