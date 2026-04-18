# Các câu hỏi và giải đáp

Tổng hợp các câu hỏi thực tế và giải đáp liên quan đến Core Java, Spring Boot, Hibernate, Security, ...

---

## Cách tạo liên kết giữa các kiến thức trong Obsidian
- Để liên kết giữa các file hoặc chủ đề, sử dụng cú pháp: `[[tên-file-hoặc-chủ-đề]]`
- Ví dụ: [[core-java]], [[spring-boot]], [[hibernate-jpa]], [[spring-security]], [[swagger]], [[git]], [[rule]], [[payload]]
- Khi ghi chú về một khái niệm, nên tạo liên kết đến file tổng hợp kiến thức hoặc các chủ đề liên quan để dễ tra cứu.
- Khi có câu hỏi mới, nên bổ sung liên kết đến phần kiến thức tương ứng.

---

## 1. @EnableConfigurationProperties là gì?
- Annotation dùng để kích hoạt các class cấu hình properties, giúp binding các giá trị từ file cấu hình vào class Java.
- Xem thêm: [[spring-boot]]

## 2. Tại sao khi chạy app thì database chưa tạo các entity?
- Kiểm tra lại cấu hình datasource, quyền user, tên database, cấu hình Hibernate (ddl-auto), ...
- Xem thêm: [[spring-boot]], [[hibernate-jpa]]

## 3. Tại sao vào swagger thì bị login?
- Do cấu hình Spring Security chưa cho phép truy cập swagger endpoint mà không cần xác thực.
- Xem thêm: [[spring-security]], [[swagger]]

## 4. java: method formLogin in class ... cannot be applied to given types
- Do dùng sai cú pháp hoặc version Spring Security mới yêu cầu truyền Customizer vào formLogin().
- Xem thêm: [[spring-security]]

## ... (bổ sung các câu hỏi khác khi có)
