# @ExceptionHandler là gì, cách dùng

## Câu trả lời ngắn gọn
@ExceptionHandler là annotation của Spring dùng để đánh dấu một method xử lý ngoại lệ (exception) cụ thể trong controller hoặc @ControllerAdvice.

## Giải thích chi tiết
- Khi một exception được ném ra trong controller, method có @ExceptionHandler sẽ tự động bắt và xử lý exception đó.
- Có thể dùng trong controller hoặc class @ControllerAdvice để xử lý ngoại lệ toàn cục.
- Giúp trả về mã lỗi, thông báo phù hợp cho client thay vì lỗi mặc định.

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
- [[spring-boot/controlleradvice.md]]
- [[spring-boot/spring-boot.md]]
- [[spring-boot/badrequest-vs-runtimeexception.md]]

## Mẹo/Lưu ý
- Có thể khai báo nhiều method @ExceptionHandler cho các loại exception khác nhau.
- Ưu tiên dùng @ControllerAdvice để xử lý exception toàn cục.

