# @ControllerAdvice là gì?

## Câu trả lời ngắn gọn
@ControllerAdvice là annotation của Spring giúp xử lý ngoại lệ (exception) hoặc logic chung cho toàn bộ controller trong ứng dụng.

## Giải thích chi tiết
- Được dùng để định nghĩa một class xử lý ngoại lệ toàn cục (global exception handler) hoặc các logic chung như binding, logging, ...
- Khi có exception xảy ra ở bất kỳ controller nào, các method trong class @ControllerAdvice sẽ tự động bắt và xử lý nếu có @ExceptionHandler phù hợp.
- Giúp tách biệt logic xử lý lỗi khỏi controller, code gọn gàng, dễ bảo trì.

## Ví dụ cụ thể
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
```

## Liên kết kiến thức
- [[spring-boot/spring-boot.md]]
- [[spring-boot/badrequest-vs-runtimeexception.md]]
- [[core-java/core-java.md]]

## Mẹo/Lưu ý
- Nên dùng @ControllerAdvice để xử lý exception chung, trả về mã lỗi và thông báo chuẩn RESTful.
- Có thể kết hợp nhiều @ExceptionHandler cho các loại exception khác nhau.

