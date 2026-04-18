# Các loại Exception trong Java

## Exception được bắt lỗi như thế nào?
- Khi chương trình phát sinh Exception, bạn có thể:
  - **Bắt lỗi (try-catch):** Xử lý lỗi, chương trình tiếp tục chạy.
  - **Không bắt lỗi:**
    - Với Checked Exception: IDE sẽ báo lỗi compile, không chạy được nếu không xử lý.
    - Với Unchecked Exception/Error: Nếu không bắt, chương trình sẽ bị crash khi chạy đến lỗi đó.
- Nếu không có @ExceptionHandler trong ứng dụng web, lỗi sẽ trả về mã HTTP mặc định (thường là 500).

## 1. Checked Exception
- Là các exception phải khai báo (throws) hoặc bắt (try-catch).
- Kế thừa từ Exception nhưng không phải RuntimeException.
- Ví dụ: IOException, SQLException, ClassNotFoundException.

## 2. Unchecked Exception
- Là các exception không bắt buộc phải khai báo hoặc bắt.
- Kế thừa từ RuntimeException.
- Ví dụ: NullPointerException, IllegalArgumentException, IndexOutOfBoundsException, ArithmeticException, BadRequestException (custom).

## 3. Error
- Lỗi nghiêm trọng, thường do JVM hoặc môi trường, không nên bắt.
- Kế thừa từ Error.
- Ví dụ: OutOfMemoryError, StackOverflowError.

### Vì sao không nên bắt Error?
- **Error** là các lỗi nghiêm trọng liên quan đến hệ thống hoặc môi trường (ví dụ: hết bộ nhớ, tràn stack, lỗi JVM).
- **Không nên bắt Error** vì:
  - Thường không thể phục hồi hoặc xử lý tiếp được (ví dụ: OutOfMemoryError, StackOverflowError).
  - Nếu cố bắt, có thể che giấu lỗi hệ thống nghiêm trọng, khiến chương trình chạy không ổn định, khó debug.
  - JVM hoặc hệ điều hành cần biết lỗi này để dừng chương trình hoặc giải phóng tài nguyên.
- **Best practice:** Chỉ nên bắt và xử lý Exception (checked/unchecked), KHÔNG nên bắt Error.

## 4. Tổng kết phân biệt
| Loại            | Kế thừa từ      | Bắt buộc try-catch/throws | Ví dụ                        |
|-----------------|-----------------|--------------------------|------------------------------|
| Checked         | Exception       | Có                       | IOException, SQLException    |
| Unchecked       | RuntimeException| Không                    | NullPointerException, ...    |
| Error           | Error           | Không                    | OutOfMemoryError, ...        |

## 5. Ví dụ từng loại Exception

### Checked Exception
```java
// Có sử dụng try-catch
try {
    FileReader reader = new FileReader("file.txt");
} catch (IOException e) {
    System.out.println("File not found!");
}

// Không sử dụng try-catch (bắt buộc phải throws)
public void readFile() throws IOException {
    FileReader reader = new FileReader("file.txt");
}
```

### Unchecked Exception
```java
// Không bắt buộc try-catch
String s = null;
System.out.println(s.length()); // NullPointerException

// Có thể dùng try-catch nếu muốn
try {
    int[] arr = new int[2];
    System.out.println(arr[5]); // IndexOutOfBoundsException
} catch (RuntimeException e) {
    System.out.println("Lỗi runtime: " + e.getMessage());
}
```

### Error
```java
// Không nên bắt Error, ví dụ:
try {
    // Gây lỗi tràn stack
    recursive();
} catch (StackOverflowError e) {
    System.out.println("Stack overflow!");
}

void recursive() {
    recursive();
}
```

## 6. Ưu điểm và nhược điểm từng loại Exception

### Checked Exception
- **Ưu điểm:**
  - Bắt buộc phải xử lý, giúp code an toàn hơn, tránh bỏ sót lỗi quan trọng (ví dụ: lỗi file, database).
  - IDE sẽ cảnh báo nếu bạn không xử lý.
- **Nhược điểm:**
  - Có thể gây nhiều mã try-catch, làm code dài và khó đọc nếu lạm dụng.
  - Không phù hợp cho lỗi do lập trình sai (ví dụ: truyền tham số sai).

### Unchecked Exception
- **Ưu điểm:**
  - Code gọn, không bắt buộc phải try-catch ở mọi nơi.
  - Phù hợp cho lỗi do lập trình sai hoặc dữ liệu không hợp lệ (ví dụ: NullPointerException).
- **Nhược điểm:**
  - Nếu không kiểm soát tốt, lỗi có thể "nổi lên" và làm crash chương trình.
  - IDE không cảnh báo, dễ bỏ sót lỗi nếu không kiểm tra kỹ.

### Error
- **Ưu điểm:**
  - Báo hiệu lỗi nghiêm trọng, giúp lập trình viên biết hệ thống có vấn đề lớn (ví dụ: hết bộ nhớ).
- **Nhược điểm:**
  - Không nên bắt và xử lý, vì thường không thể phục hồi.
  - Nếu cố bắt, có thể che giấu lỗi hệ thống nghiêm trọng, gây khó debug.

## 7. Mã lỗi HTTP trả về khi gặp Exception trong ứng dụng web (Spring Boot)

### Checked Exception
- Nếu không bắt và để exception "nổi lên":
  - Thường trả về mã lỗi **500 Internal Server Error** (trừ khi bạn cấu hình @ExceptionHandler riêng cho loại này).
- Nếu bạn dùng @ExceptionHandler(IOException.class):
  - Có thể trả về mã lỗi tuỳ ý, ví dụ 400, 404, 500,...

### Unchecked Exception
- Nếu không bắt và để exception "nổi lên" (ví dụ: NullPointerException, IndexOutOfBoundsException):
  - Mặc định trả về **500 Internal Server Error**.
- Nếu có @ExceptionHandler(RuntimeException.class) hoặc custom exception:
  - Trả về mã lỗi bạn định nghĩa, ví dụ 400 cho BadRequestException.

### Error
- Nếu gặp Error (OutOfMemoryError, StackOverflowError):
  - Thường trả về **500 Internal Server Error** hoặc ứng dụng bị crash.
- Không nên xử lý Error, chỉ nên để JVM xử lý.

### Tổng kết
| Loại Exception         | Không bắt, không handler | Có @ExceptionHandler      |
|-----------------------|-------------------------|--------------------------|
| Checked Exception     | 500                     | Tuỳ bạn định nghĩa       |
| Unchecked Exception   | 500                     | Tuỳ bạn định nghĩa       |
| Error                 | 500 hoặc crash          | Không nên xử lý          |

> **Lưu ý:**
> - Nên dùng @ControllerAdvice + @ExceptionHandler để trả về mã lỗi và thông báo phù hợp cho client.
> - Các exception nghiệp vụ (BadRequestException, NotFoundException, ...) nên trả về 400, 404,... thay vì 500.

## Liên kết kiến thức
- [[core-java/core-java.md]]
- [[core-java/unchecked-exception.md]]
- [[spring-boot/exception-structure.md]]

## Mẹo/Lưu ý
- Chỉ nên bắt và xử lý exception nghiệp vụ (checked/unchecked), không nên bắt Error.
- Khi viết API, nên tạo custom exception kế thừa RuntimeException để dễ quản lý.
