# Cấu trúc thư mục ghi chú kiến thức Backend Developer (Obsidian)

## Quy tắc chung
- Mỗi chủ đề lớn (Core Java, Spring Boot, Hibernate, Security, Git, ...) là một thư mục con trong `Java/`.
- Mỗi thư mục chủ đề chứa các file kiến thức tổng hợp, ví dụ, câu hỏi phỏng vấn, ...
- Các file tổng hợp, ví dụ, câu hỏi, ... được đặt tên rõ ràng, dễ hiểu, dễ mở rộng.
- Các file rule, hướng dẫn, chỉ đặt tại `.github/copilot-introductions.md`.

## Đề xuất cấu trúc thư mục

```
Java/
  core-java/
    core-java.md
    interview-questions-core-java.md
    example-core-java.md
  spring-boot/
    spring-boot.md
    interview-questions-spring-boot.md
    example-spring-boot.md
  hibernate-jpa/
    hibernate-jpa.md
    interview-questions-hibernate-jpa.md
    example-hibernate-jpa.md
  security/
    spring-security.md
    interview-questions-security.md
    example-security.md
  git/
    git.md
    interview-questions-git.md
    example-git.md
  swagger/
    swagger.md
    swagger-examples.json.txt
  rule.md (liên kết đến copilot-introductions.md)
```

## Lưu ý
- Các file cũ trong `docs/` sẽ được chuyển vào các thư mục chủ đề tương ứng.
- Luôn tạo liên kết giữa các file kiến thức liên quan.
- Ưu tiên mở rộng theo chiều sâu từng chủ đề.

## Ví dụ liên kết
- [[core-java/core-java.md]]
- [[spring-boot/spring-boot.md]]
- [[hibernate-jpa/hibernate-jpa.md]]
- [[security/spring-security.md]]
- [[git/git.md]]
- [[swagger/swagger.md]]

