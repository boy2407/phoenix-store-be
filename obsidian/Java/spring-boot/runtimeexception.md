# RuntimeException trong Java

## Câu trả lời ngắn gọn
- RuntimeException là lớp cha cho các exception xảy ra khi chương trình đang chạy (unchecked exception).

## Giải thích chi tiết
- RuntimeException thuộc gói java.lang, kế thừa từ Exception nhưng không bắt buộc phải khai báo hoặc bắt (không cần throws/catch).
- Thường dùng cho các lỗi lập trình như NullPointerException, IndexOutOfBoundsException, IllegalArgumentException, ...
- Nếu không được xử lý, chương trình sẽ bị dừng tại vị trí lỗi.

## Ví dụ cụ thể
```java
public class Example {
    public static void main(String[] args) {
        String s = null;
        System.out.println(s.length()); // Ném ra NullPointerException (kế thừa RuntimeException)
    }
}
```

## Liên kết kiến thức
- [[core-java/core-java.md]]
- [[core-java/interview-questions-core-java.md]]

## Mẹo/Lưu ý
- Chỉ nên dùng RuntimeException cho các lỗi không thể phục hồi hoặc do lập trình viên gây ra.
- Không nên lạm dụng, tránh che giấu lỗi nghiệp vụ quan trọng.

