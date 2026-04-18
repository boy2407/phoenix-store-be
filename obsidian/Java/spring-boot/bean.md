# @Bean trong Spring Boot

## Câu trả lời ngắn gọn
- @Bean là annotation dùng để đánh dấu một phương thức trả về một bean (object) do Spring quản lý trong ApplicationContext.

## Giải thích chi tiết
- Khi bạn đánh dấu một phương thức với @Bean trong class @Configuration, Spring sẽ gọi phương thức đó và đưa object trả về vào container.
- Các bean này có thể được inject vào các class khác thông qua @Autowired hoặc constructor injection.
- Thường dùng để cấu hình các bean bên ngoài, hoặc các thư viện không thể đánh dấu @Component.
- Mỗi @Bean mặc định là singleton (một instance duy nhất cho toàn ứng dụng).

## Ví dụ cụ thể
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

// Sử dụng
@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
}
```

## Liên kết kiến thức
- [[spring-boot/spring-boot.md]]
- [[spring-boot/example-spring-boot.md]]
- [[core-java/core-java.md]] (OOP, Interface)

## Mẹo/Lưu ý
- @Bean thường dùng cho các bean cấu hình, hoặc các class từ thư viện ngoài.
- Nếu class tự viết, nên dùng @Component, @Service, @Repository để Spring tự động quét.
- Có thể chỉ định scope khác (prototype, request, session) bằng @Scope("prototype").

