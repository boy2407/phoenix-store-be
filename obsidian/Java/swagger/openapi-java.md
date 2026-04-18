# io.swagger.v3.oas.models.OpenAPI trong Java/Spring Boot

## Câu trả lời ngắn gọn
- `io.swagger.v3.oas.models.OpenAPI` là class đại diện cho mô hình OpenAPI Specification, dùng để cấu hình và sinh tài liệu API tự động cho ứng dụng Java/Spring Boot.

## Giải thích chi tiết
- Đây là class thuộc thư viện Swagger (springdoc-openapi), dùng để mô tả toàn bộ cấu trúc API (endpoint, info, security, schema, ...).
- Khi bạn khai báo một bean kiểu OpenAPI trong Spring Boot, springdoc-openapi sẽ tự động sử dụng nó để sinh file openapi.json và giao diện Swagger UI.
- Bạn có thể tuỳ chỉnh title, version, description, contact, license, server, ... cho tài liệu API.
- Việc tích hợp giúp frontend, mobile, đối tác có thể xem, thử, hoặc generate client SDK từ tài liệu này.

## Ví dụ cụ thể
```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Phoenix Store API")
                .version("1.0.0")
                .description("API documentation for Phoenix Store project"));
    }
}
```
- Khi chạy app, truy cập `/swagger-ui.html` hoặc `/swagger-ui/index.html` sẽ thấy giao diện tài liệu API tự động.

## Liên kết kiến thức
- [[swagger/openapi.md]]
- [[swagger/swagger.md]]
- [[spring-boot/spring-boot.md]] (Cấu hình Swagger)

## Mẹo/Lưu ý
- Thư viện springdoc-openapi là chuẩn hiện nay cho Spring Boot (thay thế springfox cũ).
- Có thể mở rộng cấu hình: security, server, externalDocs, tags, ...
- Đảm bảo khai báo bean OpenAPI trong @Configuration để Spring Boot tự động nhận diện.

