# Cấu trúc xử lý lỗi (exception) trong Spring Boot

## 1. Luồng phát sinh lỗi
- Khi code gặp lỗi (exception), bạn có thể:
  - Tự bắt (try-catch) và xử lý.
  - Không bắt, để exception "nổi lên" (throw) ra ngoài controller.

## 2. Spring xử lý lỗi như thế nào?
- Nếu exception không được bắt, Spring sẽ tìm method @ExceptionHandler phù hợp để xử lý.
- Nếu không có @ExceptionHandler phù hợp, Spring trả về lỗi mặc định (thường là HTTP 500).

## 3. Các loại exception phổ biến
- **Checked Exception**: Exception phải khai báo hoặc bắt (extends Exception, không phải RuntimeException).
- **RuntimeException**: Exception không bắt buộc phải khai báo hoặc bắt (extends RuntimeException). Ví dụ: NullPointerException, IllegalArgumentException, BadRequestException (custom).
- **Error**: Lỗi nghiêm trọng (OutOfMemoryError, StackOverflowError), thường không nên bắt.

## 4. @ExceptionHandler hoạt động ra sao?
- Bạn có thể khai báo nhiều method @ExceptionHandler cho từng loại exception:
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequest(BadRequestException ex) { ... }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) { ... }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) { ... }
}
```
- Khi lỗi xảy ra, Spring sẽ tìm method @ExceptionHandler phù hợp nhất theo thứ tự:
  1. Đúng loại exception nhất (ví dụ: BadRequestException)
  2. Cha gần nhất (RuntimeException)
  3. Exception tổng quát (Exception)

## 5. Ví dụ minh họa
- Nếu bạn throw new BadRequestException("Lỗi nghiệp vụ"):
  - Nếu có @ExceptionHandler(BadRequestException.class) → method này xử lý.
  - Nếu không, tìm @ExceptionHandler(RuntimeException.class) → method này xử lý.
  - Nếu không, tìm @ExceptionHandler(Exception.class) → method này xử lý.
  - Nếu không có, trả về lỗi mặc định (500).

## 6. Mẹo/Lưu ý
- Nên tạo các exception nghiệp vụ riêng (BadRequestException, NotFoundException, ...).
- Nên dùng @ControllerAdvice để xử lý lỗi toàn cục.
- Không nên bắt Error, chỉ nên xử lý exception nghiệp vụ.

## Liên kết kiến thức
- [[spring-boot/exceptionhandler.md]]
- [[spring-boot/controlleradvice.md]]
- [[spring-boot/badrequest-vs-runtimeexception.md]]

