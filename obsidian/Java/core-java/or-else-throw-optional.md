# orElseThrow trong Java Optional

## Khái niệm
- `orElseThrow` là phương thức của class `Optional` trong Java, dùng để lấy giá trị nếu tồn tại, nếu không sẽ ném ra exception.
- Cú pháp: `optional.orElseThrow(Supplier<? extends X> exceptionSupplier)`

## Cách hoạt động
- Nếu Optional chứa giá trị, trả về giá trị đó.
- Nếu Optional rỗng, ném exception do bạn cung cấp.

## Ví dụ
```java
Optional<String> name = Optional.ofNullable(null);
String value = name.orElseThrow(() -> new RuntimeException("Not found")); // Ném RuntimeException
```

## Liên kết kiến thức
- [[core-java/core-java.md]]
- [[core-java/interview-questions-core-java.md]]

## Tham khảo
- [Java Optional orElseThrow Javadoc](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html#orElseThrow-java.util.function.Supplier-)

