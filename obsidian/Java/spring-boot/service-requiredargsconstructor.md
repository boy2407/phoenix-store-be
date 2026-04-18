# @Service và @RequiredArgsConstructor

## @Service là gì?
- Annotation của Spring, đánh dấu class là một bean Service (lớp dịch vụ) để Spring quản lý và tự động inject vào các thành phần khác.
- Thường dùng cho tầng business logic.
- Ví dụ:
```java
@Service
public class ProductServiceImpl implements ProductService { ... }
```

## @RequiredArgsConstructor là gì?
- Annotation của Lombok, tự động sinh constructor với tất cả các trường final hoặc @NonNull.
- Giúp bạn không cần viết constructor thủ công để inject dependency.
- Khi dùng cùng Spring, giúp sử dụng constructor injection dễ dàng.
- Ví dụ:
```java
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final ProductRepository productRepository;
    // Lombok sẽ sinh constructor:
    // public ProductServiceImpl(ProductRepository productRepository) { ... }
}
```

## Liên kết kiến thức
- [[core-java/core-java.md]]
- [[spring-boot/spring-boot.md]]
- [[core-java/interview-questions-core-java.md]]
- [[spring-boot/interview-questions-spring-boot.md]]

## Mẹo/Lưu ý
- Ưu tiên dùng constructor injection với @RequiredArgsConstructor để code dễ test, dễ bảo trì.
- @Service giúp Spring quản lý vòng đời bean, tự động inject khi cần.

