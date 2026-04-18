# Tổng hợp kiến thức Core Java

## 1. Cơ bản về Java

### Java là gì?
- Java là ngôn ngữ lập trình hướng đối tượng (OOP), biên dịch thành bytecode, chạy trên JVM (Java Virtual Machine).
- Một lần viết, chạy ở mọi nơi (Write Once, Run Anywhere - WORA).

### OOP trong Java (4 nguyên tắc)
- **Encapsulation (Đóng gói)**: Ẩn dữ liệu, chỉ cung cấp interface công khai.
- **Inheritance (Kế thừa)**: Lớp con kế thừa từ lớp cha.
- **Polymorphism (Đa hình)**: Cùng tên, hành động khác nhau.
- **Abstraction (Trừu tượng)**: Ẩn độ phức tạp, hiển thị giao diện.

## 2. Exception (Ngoại lệ)

### Exception là gì?
- Exception là một sự kiện xảy ra trong quá trình thực thi chương trình, làm gián đoạn luồng bình thường.
- Giúp xử lý lỗi một cách an toàn, không làm crash chương trình.

### Phân loại Exception
- **Checked Exception**: Phải xử lý (throws/try-catch), ví dụ: IOException, SQLException.
- **Unchecked Exception (RuntimeException)**: Không bắt buộc xử lý, ví dụ: NullPointerException, IndexOutOfBoundsException.
- **Error**: Lỗi hệ thống không thể khôi phục, ví dụ: StackOverflowError, OutOfMemoryError.

### Cấu trúc try-catch-finally
```java
try {
    // Code có thể gây exception
} catch (ExceptionType e) {
    // Xử lý exception
} finally {
    // Luôn được thực thi, dùng để giải phóng tài nguyên
}
```

## 3. Collection Framework

### List, Set, Map
- **List**: Danh sách có thứ tự, cho phép duplicate.
  - ArrayList: Truy cập nhanh O(1), thêm/xoá chậm O(n).
  - LinkedList: Thêm/xoá nhanh O(1), truy cập chậm O(n).
- **Set**: Tập hợp duy nhất, không có thứ tự (trừ TreeSet, LinkedHashSet).
  - HashSet: O(1) tìm kiếm, thêm, xoá.
  - TreeSet: Sắp xếp tự động, O(log n).
- **Map**: Cặp key-value.
  - HashMap: O(1) truy cập.
  - TreeMap: Sắp xếp theo key.

## 4. String và StringBuilder

### String
- String là immutable (bất biến), mỗi lần thay đổi tạo object mới.
- So sánh: `equals()` so sánh giá trị, `==` so sánh tham chiếu.

### StringBuilder
- Mutable, dùng khi cần nhiều thao tác string (nối, sửa).
- Hiệu suất tốt hơn String khi lặp.

## 5. Luồng (Thread) và Concurrency

### Thread là gì?
- Thread là đơn vị nhỏ nhất của lập trình đa luồng, chạy song song.
- Tạo thread: kế thừa Thread hoặc implement Runnable.

### Synchronized
- Đảm bảo chỉ một thread truy cập tài nguyên tại một thời điểm.
- Tránh race condition.

## Liên kết kiến thức
- [[core-java/interview-questions-core-java.md]]
- [[core-java/example-core-java.md]]
- [[core-java/runtimeexception.md]]
- [[spring-boot/spring-boot.md]] (Exception handler)
- [[hibernate-jpa/hibernate-jpa.md]] (Entity, OOP)

