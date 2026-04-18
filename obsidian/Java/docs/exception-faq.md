# FAQ - Xử lý Exception trong Spring Boot

## Làm thế nào để trả về lỗi 400 (Bad Request) khi gặp lỗi nghiệp vụ?
- Tạo custom exception (ví dụ: BadRequestException kế thừa RuntimeException).
- Tạo GlobalExceptionHandler dùng @ControllerAdvice để bắt exception này và trả về HTTP 400.
- Khi gặp lỗi, chỉ cần throw new BadRequestException("Lý do lỗi").

### Liên kết kiến thức:
- [[exception]]
- [[spring-boot]]

