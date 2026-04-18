# Unchecked Exception là gì? NullPointerException là lỗi gì?

## Câu trả lời ngắn gọn
- **Unchecked Exception** là các exception kế thừa từ RuntimeException, không bắt buộc phải khai báo hoặc bắt (try-catch).
- Nếu bạn không bắt lỗi và để một trường bị null gây lỗi, đó là NullPointerException, thuộc loại Unchecked Exception.

## Giải thích chi tiết
- **Checked Exception**: Phải khai báo (throws) hoặc bắt (try-catch). Ví dụ: IOException, SQLException.
- **Unchecked Exception**: Không bắt buộc phải khai báo hoặc bắt. Bao gồm:
  - RuntimeException và các class con (NullPointerException, IllegalArgumentException, IndexOutOfBoundsException, ...)
  - Thường là lỗi do lập trình sai hoặc dữ liệu không hợp lệ.
- **NullPointerException**: Xảy ra khi bạn truy cập hoặc gọi phương thức trên một object bị null.

## Ví dụ cụ thể
```java
String s = null;
System.out.println(s.length()); // Gây ra NullPointerException
```
- Nếu bạn không bắt lỗi này, chương trình sẽ dừng và trả về lỗi 500 nếu là ứng dụng web.

## Liên kết kiến thức
- [[core-java/core-java.md]]
- [[spring-boot/exception-structure.md]]
- [[spring-boot/exceptionhandler.md]]

## Mẹo/Lưu ý
- Luôn kiểm tra null trước khi truy cập object để tránh NullPointerException.
- Có thể dùng Optional, @NonNull, hoặc kiểm tra thủ công để phòng tránh.

