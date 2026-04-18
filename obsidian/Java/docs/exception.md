# Xử lý Exception tuỳ chỉnh trong Spring Boot

## BadRequestException

- Là một custom exception kế thừa RuntimeException, dùng để ném lỗi khi request không hợp lệ (ví dụ: dữ liệu đầu vào sai, nghiệp vụ không đúng).
- Khi gặp lỗi nghiệp vụ, chỉ cần throw new BadRequestException("Lý do lỗi").
- Exception này sẽ được GlobalExceptionHandler bắt và trả về HTTP 400 cùng message lỗi.

### Ví dụ sử dụng:
```java
if (categoryRepository.findById(request.getParentId()).isEmpty()) {
    throw new BadRequestException("Parent category không tồn tại");
}
```

### Liên kết kiến thức:
- [[spring-boot]]
- [[faq]]
- [[hibernate-jpa]]

