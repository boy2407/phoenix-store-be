# Câu hỏi phỏng vấn Core Java

## 1. So sánh List và Set trong Java?

**Câu trả lời ngắn gọn:**
- List cho phép lưu trữ phần tử trùng lặp và duy trì thứ tự, Set chỉ lưu trữ phần tử duy nhất và không duy trì thứ tự (trừ LinkedHashSet, TreeSet).

**Giải thích chi tiết:**
- **List**: Lưu trữ dữ liệu theo thứ tự chỉ số (index), có thể truy cập phần tử bằng chỉ số. Cho phép duplicate.
  - ArrayList: Triển khai bằng mảng động, truy cập nhanh O(1), thêm/xoá chậm O(n).
  - LinkedList: Triển khai bằng danh sách liên kết, thêm/xoá nhanh O(1), truy cập chậm O(n).
- **Set**: Lưu trữ dữ liệu duy nhất, không cho phép duplicate, không có chỉ số.
  - HashSet: Sử dụng hash table, tìm kiếm, thêm, xoá O(1) bình quân.
  - TreeSet: Sử dụng cây cân bằng (Red-Black Tree), dữ liệu được sắp xếp, tìm kiếm O(log n).
  - LinkedHashSet: Kết hợp HashSet với LinkedList, duy trì thứ tự chèn.

**Ví dụ cụ thể:**
```java
// List
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("A"); // Cho phép duplicate
System.out.println(list); // [A, B, A]
System.out.println(list.get(0)); // Truy cập bằng index: A

// Set
Set<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A"); // Không cho phép duplicate
System.out.println(set); // [B, A] hoặc [A, B] (không có thứ tự)
// System.out.println(set.get(0)); // Lỗi - Set không có index
```

**Liên kết kiến thức:**
- [[core-java/core-java.md]]
- [[core-java/example-core-java.md]]

**Mẹo/Lưu ý:**
- Dùng List khi cần dữ liệu có thứ tự và có thể trùng lặp (ví dụ: danh sách bán hàng).
- Dùng Set khi cần dữ liệu duy nhất, không cần thứ tự (ví dụ: danh sách email duy nhất).
- TreeSet chậm hơn HashSet nhưng dữ liệu luôn sắp xếp.

---

## 2. Giải thích về OOP trong Java?

**Câu trả lời ngắn gọn:**
- OOP (Object-Oriented Programming) là phương pháp lập trình sử dụng các đối tượng (object) và lớp (class), với 4 nguyên tắc: Encapsulation, Inheritance, Polymorphism, Abstraction.

**Giải thích chi tiết:**
- **Encapsulation (Đóng gói)**: Ẩn đi các chi tiết nội tại, chỉ cung cấp interface công khai.
  - Dùng private/protected để hạn chế truy cập.
  - Cung cấp getter/setter để kiểm soát truy cập.
- **Inheritance (Kế thừa)**: Lớp con kế thừa thuộc tính và phương thức từ lớp cha.
  - Dùng từ khóa `extends`.
  - Giúp tái sử dụng code, tạo phân cấp lớp.
- **Polymorphism (Đa hình)**: Cùng tên phương thức nhưng hành động khác nhau tùy thuộc vào đối tượng.
  - **Method Overloading**: Cùng tên nhưng khác tham số.
  - **Method Overriding**: Lớp con ghi đè phương thức của lớp cha.
- **Abstraction (Trừu tượng)**: Ẩn đi độ phức tạp, chỉ hiển thị giao diện cần thiết.
  - Dùng abstract class hoặc interface.

**Ví dụ cụ thể:**
```java
// Encapsulation
class Person {
    private String name; // Ẩn
    private int age;

    public String getName() { // Public getter
        return name;
    }

    public void setName(String name) { // Public setter
        this.name = name;
    }
}

// Inheritance & Polymorphism
abstract class Animal {
    abstract void makeSound(); // Abstraction
}

class Dog extends Animal {
    @Override
    void makeSound() { // Overriding
        System.out.println("Woof");
    }
}

class Cat extends Animal {
    @Override
    void makeSound() { // Overriding
        System.out.println("Meow");
    }
}

// Method Overloading (Polymorphism)
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) { // Khác tham số
        return a + b;
    }
}
```

**Liên kết kiến thức:**
- [[core-java/core-java.md]]
- [[core-java/example-core-java.md]]

**Mẹo/Lưu ý:**
- OOP giúp code dễ bảo trì, mở rộng, tái sử dụng.
- Một class chỉ có thể kế thừa một lớp cha (single inheritance), nhưng có thể implement nhiều interface.
- abstract class có thể có constructor, interface không.

