# Spring Boot - Xử lý Exception

## GlobalExceptionHandler
- Sử dụng @ControllerAdvice để xử lý tập trung các exception.
- Có thể bắt nhiều loại exception khác nhau, ví dụ: BadRequestException, NotFoundException, ...
- Trả về mã lỗi và thông báo phù hợp cho client.

## BadRequestException
- Custom exception kế thừa RuntimeException, dùng để báo lỗi request không hợp lệ.

### Ví dụ:
```java
@ExceptionHandler(BadRequestException.class)
public ResponseEntity<String> handleBadRequest(BadRequestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
}
```

### Liên kết kiến thức:
- [[exception]]
- [[faq]]

