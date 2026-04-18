# Copilot Introduction & Rule

## Rule sử dụng Copilot với Obsidian cho học tập/phỏng vấn Backend Developer


0. Các rule về trình bày ví dụ, cấu trúc, liên kết, v.v. đều phải ghi rõ tại file này (copilot-introduction.md), không ghi lung tung ở các file kiến thức khác. Các file kiến thức chỉ chứa nội dung chuyên môn, ví dụ, câu hỏi, không chứa rule.
1. Khi bạn hỏi về bất kỳ khái niệm, kiến thức, hoặc chủ đề nào (Core Java, Spring Boot, Hibernate, Security, Git, ...), Copilot sẽ:
   - Tạo file Markdown ghi chú kiến thức tổng hợp cho chủ đề đó trong Obsidian.
   - Tạo file chứa các câu hỏi phỏng vấn liên quan đến chủ đề, kèm theo câu trả lời chi tiết.
   - Nếu chủ đề mới, sẽ tạo file tổng hợp kiến thức cho phần đó.
   - Nếu chủ đề đã có, sẽ bổ sung kiến thức/câu hỏi vào file tương ứng.
2. Mục tiêu: Xây dựng hệ thống ghi chú, câu hỏi phỏng vấn, và tài liệu tổng hợp phục vụ ôn tập/phỏng vấn Backend Developer.
3. Đảm bảo phân chia file rõ ràng theo chủ đề, dễ tìm kiếm, dễ mở rộng.
4. Ưu tiên trả lời ngắn gọn, súc tích, dễ hiểu, có ví dụ minh họa nếu cần.
5. Khi có yêu cầu, sẽ tạo thêm file tổng hợp kiến thức hoặc câu hỏi mới theo chủ đề.
6. Khi ghi chú kiến thức hoặc câu hỏi, luôn tạo liên kết giữa các kiến thức liên quan và giữa các câu hỏi với phần kiến thức tương ứng bằng cú pháp `[[tên-file-hoặc-chủ-đề]]` để dễ dàng tra cứu, ví dụ: [[core-java]], [[spring-boot]], [[hibernate-jpa]], ...
7. Luôn sắp xếp kiến thức theo chiều mở rộng (từ tổng quan đến chi tiết, từ cơ bản đến nâng cao) để có một cái nhìn tổng thể, dễ hệ thống hóa và dễ tra cứu.
8. Với mỗi kiến thức, luôn bổ sung ví dụ cụ thể, dễ hiểu, dễ nắm bắt, ưu tiên ví dụ thực tế, có chú thích rõ ràng nếu cần.
9. Với mỗi câu hỏi phỏng vấn, luôn trả lời:
   - **Câu trả lời ngắn gọn**: Tóm tắt ý chính (1-2 dòng).
   - **Giải thích chi tiết**: Khái quát khái niệm, lý thuyết, nguyên lý liên quan.
   - **Ví dụ cụ thể**: Cung cấp ví dụ thực tế, code hoặc kịch bản cụ thể, dễ hiểu.
   - **Liên kết kiến thức**: Tham chiếu đến file kiến thức tương ứng (ví dụ: [[spring-boot/spring-boot.md]], [[core-java/core-java.md]]).
   - **Mẹo/Lưu ý**: Thêm thông tin bổ sung, cú pháp, lỗi thường gặp, hoặc best practice nếu có.
10. Với mỗi kiến thức mới được hỏi, Copilot sẽ tạo hoặc cập nhật theo cấu trúc:
    - **{chủ-đề}/{chủ-đề}.md**: File tổng hợp kiến thức chính (khái quát, lý thuyết, ví dụ).
    - **{chủ-đề}/interview-questions-{chủ-đề}.md**: File câu hỏi phỏng vấn kèm câu trả lời đầy đủ theo rule 9.
    - **{chủ-đề}/example-{chủ-đề}.md**: File ví dụ code, kịch bản thực tế liên quan.
    - Các file phải có liên kết nội dung và tham chiếu lẫn nhau bằng cú pháp `[[tên-file]]`.
11. Luôn cập nhật các file kiến thức, example, interview, questions cho các chủ đề khi có câu hỏi hay thắc mắc mới:
    - Bổ sung kiến thức vào {chủ-đề}/{chủ-đề}.md.
    - Thêm ví dụ code vào {chủ-đề}/example-{chủ-đề}.md.
    - Thêm câu hỏi phỏng vấn + câu trả lời vào {chủ-đề}/interview-questions-{chủ-đề}.md.
    - Kiểm tra và thêm liên kết giữa các file (ví dụ: liên kết "Exception" từ core-java sang spring-boot exception handler).

## Quy trình chi tiết khi có vấn đề/câu hỏi mới (Workflow Example)

**Ví dụ: Hỏi "Exception là gì?"**

### Bước 1: Xác định chủ đề
- Chủ đề: Core Java
- Vấn đề: Exception (kế thừa từ RuntimeException)

### Bước 2: Tạo/Cập nhật file kiến thức (core-java/core-java.md)
- Giải thích Exception là gì?
- Phân loại: Checked vs Unchecked Exception
- Phân loại: Error vs Exception
- Khi nào dùng throw/throws/try-catch
- Ví dụ chi tiết

### Bước 3: Tạo/Cập nhật file example (core-java/example-core-java.md)
- Ví dụ try-catch
- Ví dụ throw exception
- Ví dụ custom exception
- Ví dụ finally block
- Ví dụ try-with-resources (Java 7+)

### Bước 4: Tạo/Cập nhật file interview questions (core-java/interview-questions-core-java.md)
- Câu hỏi: "Exception là gì?"
- Câu hỏi: "Checked Exception vs Unchecked Exception?"
- Câu hỏi: "Khi nào dùng throw, throws?"
- Mỗi câu trả lời theo rule 9

### Bước 5: Tạo liên kết kiến thức thông minh
- Exception trong core-java → liên kết tới custom exception (BadRequestException) trong spring-boot
- Exception trong core-java → liên kết tới RuntimeException
- Exception trong core-java → liên kết tới exception handler trong spring-boot
- Ví dụ: [[core-java/core-java.md]] → [[spring-boot/spring-boot.md]] (exception handler)

### Bước 6: Đảm bảo tính nhất quán
- Kiểm tra xem các file core-java, example, interview có đầy đủ không?
- Các liên kết có hợp lý không?
- Ví dụ có dễ hiểu không?

12. Các rule sẽ được nâng cấp thường xuyên khi phát hiện:
    - Vấn đề trong workflow hoặc cấu trúc.
    - Kiến thức mới hoặc phức tạp cần quy tắc chi tiết hơn.
    - Feedback từ ôn tập/phỏng vấn.
    - Luôn ghi rõ lý do nâng cấp rule.
13. Luôn tổng hợp các kiến thức có liên quan, tạo cấu trúc folder và file thông minh:
    - Khi có kiến thức mới, kiểm tra các chủ đề liên quan để bổ sung liên kết hai chiều (ví dụ: Exception trong core-java liên kết đến Exception Handler trong spring-boot).
    - Nếu kiến thức liên quan thuộc chủ đề khác, tạo hoặc cập nhật file ở chủ đề đó và thêm liên kết qua lại.
    - Cấu trúc folder/file phải phản ánh rõ ràng mối quan hệ giữa các chủ đề (ví dụ: core-java, spring-boot, hibernate-jpa, security, git, swagger, ...), mỗi chủ đề là một folder, mỗi khía cạnh là một file (kiến thức tổng hợp, ví dụ, interview, ...).
    - Ưu tiên cấu trúc dễ mở rộng, dễ tìm kiếm, dễ tra cứu, tránh trùng lặp nội dung.
