# BadRequestException vs RuntimeException

## Câu trả lời ngắn gọn
Sử dụng BadRequestException giúp phân biệt rõ lỗi nghiệp vụ (400 Bad Request) với các lỗi hệ thống chung, dễ xử lý và trả về thông báo phù hợp cho client.

## Giải thích chi tiết
- **RuntimeException** là exception tổng quát, dùng cho nhiều loại lỗi khác nhau, không mang ý nghĩa nghiệp vụ cụ thể.
- **BadRequestException** (custom) giúp bạn:
  - Xác định rõ đây là lỗi do dữ liệu đầu vào không hợp lệ hoặc nghiệp vụ (business logic), không phải lỗi hệ thống.
  - Dễ dàng bắt và xử lý riêng trong GlobalExceptionHandler để trả về HTTP 400 với thông báo rõ ràng cho client.
  - Code dễ đọc, dễ bảo trì, dễ mở rộng khi có nhiều loại exception nghiệp vụ khác nhau.

## Ví dụ cụ thể
```java
// Custom exception
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}

// Sử dụng trong service
if (input == null) throw new BadRequestException("Input không hợp lệ");

// Global handler
@ExceptionHandler(BadRequestException.class)
public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
    return ResponseEntity.badRequest().body(ex.getMessage());
}
```

## Liên kết kiến thức
- [[core-java/core-java.md]]
- [[spring-boot/spring-boot.md]]
- [[core-java/interview-questions-core-java.md]]
- [[spring-boot/interview-questions-spring-boot.md]]

## Mẹo/Lưu ý
- Nên tạo các exception nghiệp vụ riêng biệt (BadRequestException, NotFoundException, ...) để quản lý lỗi rõ ràng.
- Giúp API trả về mã lỗi và thông báo đúng chuẩn RESTful.

