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

---

## 3. So sánh ArrayList và LinkedList?

**Câu trả lời ngắn gọn:**
- ArrayList truy cập nhanh, thêm/xoá chậm; LinkedList truy cập chậm, thêm/xoá nhanh ở đầu/cuối.

**Giải thích chi tiết:**
- **ArrayList**: Dùng mảng động, truy cập phần tử O(1), thêm/xoá ở giữa O(n), bộ nhớ liền mạch.
- **LinkedList**: Dùng danh sách liên kết đôi, truy cập phần tử O(n), thêm/xoá ở đầu/cuối O(1), bộ nhớ phân tán.

**Ví dụ cụ thể:**
```java
List<String> arr = new ArrayList<>();
arr.add("A");
arr.add("B");
arr.remove(0); // Xoá đầu O(n)

List<String> link = new LinkedList<>();
link.add("A");
link.add("B");
link.remove(0); // Xoá đầu O(1)
```

**Liên kết kiến thức:**
- [[core-java/core-java.md]]
- [[core-java/example-core-java.md]]

**Mẹo/Lưu ý:**
- Dùng ArrayList khi cần truy cập nhanh, LinkedList khi cần thêm/xoá nhiều ở đầu/cuối.

---

## 4. So sánh HashMap và Hashtable?

**Câu trả lời ngắn gọn:**
- HashMap không đồng bộ (non-synchronized), cho phép null key/value; Hashtable đồng bộ (synchronized), không cho phép null.

**Giải thích chi tiết:**
- **HashMap**: Không thread-safe, hiệu suất cao hơn, cho phép 1 null key và nhiều null value.
- **Hashtable**: Thread-safe, mọi phương thức đều synchronized, không cho phép null key/value.

**Ví dụ cụ thể:**
```java
Map<String, String> map = new HashMap<>();
map.put(null, "A"); // Được

Map<String, String> table = new Hashtable<>();
table.put(null, "A"); // Lỗi NullPointerException
```

**Liên kết kiến thức:**
- [[core-java/core-java.md]]
- [[core-java/example-core-java.md]]

**Mẹo/Lưu ý:**
- Dùng HashMap cho đa số trường hợp, Hashtable khi cần thread-safe (nên dùng ConcurrentHashMap thay cho Hashtable).

---

## 5. So sánh interface và abstract class?

**Câu trả lời ngắn gọn:**
- Interface chỉ chứa khai báo, abstract class vừa khai báo vừa cài đặt, 1 class implement nhiều interface nhưng chỉ kế thừa 1 abstract class.

**Giải thích chi tiết:**
- **Interface**: Chỉ chứa phương thức abstract (Java 8+ có default/static method), không có constructor, không có state.
- **Abstract class**: Có thể có cả phương thức abstract và cài đặt, có constructor, có state.

**Ví dụ cụ thể:**
```java
interface Flyable {
    void fly();
}

abstract class Animal {
    abstract void makeSound();
    void eat() { System.out.println("eat"); }
}

class Bird extends Animal implements Flyable {
    public void fly() { System.out.println("fly"); }
    void makeSound() { System.out.println("chip chip"); }
}
```

**Liên kết kiến thức:**
- [[core-java/core-java.md]]
- [[core-java/example-core-java.md]]

**Mẹo/Lưu ý:**
- Interface dùng cho đa kế thừa hành vi, abstract class dùng cho phân cấp chung.

---

## 6. Từ khóa final, static, this, super dùng để làm gì?

**Câu trả lời ngắn gọn:**
- final: không thay đổi; static: dùng chung cho class; this: tham chiếu chính nó; super: tham chiếu lớp cha.

**Giải thích chi tiết:**
- **final**: Biến không đổi, phương thức không override, class không kế thừa.
- **static**: Thuộc về class, không thuộc về instance.
- **this**: Tham chiếu đến instance hiện tại.
- **super**: Tham chiếu đến thành phần của lớp cha.

**Ví dụ cụ thể:**
```java
final int x = 5;
static int count = 0;

class A {
    void show() { System.out.println("A"); }
}
class B extends A {
    void show() {
        super.show(); // Gọi phương thức lớp cha
        System.out.println("B");
    }
}
```

**Liên kết kiến thức:**
- [[core-java/core-java.md]]
- [[core-java/example-core-java.md]]

**Mẹo/Lưu ý:**
- final giúp bảo vệ dữ liệu, static tiết kiệm bộ nhớ, this/super giúp truy cập đúng thành phần.

---

## 7. Từ khóa synchronized dùng để làm gì?

**Câu trả lời ngắn gọn:**
- synchronized dùng để đồng bộ hoá truy cập tài nguyên dùng chung giữa các thread.

**Giải thích chi tiết:**
- Khi nhiều thread truy cập chung tài nguyên, synchronized đảm bảo chỉ một thread được truy cập tại một thời điểm.
- Có thể dùng cho method hoặc block.

**Ví dụ cụ thể:**
```java
synchronized void increase() { ... }

synchronized(obj) {
    // code
}
```

**Liên kết kiến thức:**
- [[core-java/core-java.md]]

**Mẹo/Lưu ý:**
- Lạm dụng synchronized gây giảm hiệu năng, dễ deadlock.

---

## 8. Exception và Error khác nhau thế nào?

**Câu trả lời ngắn gọn:**
- Exception là lỗi có thể xử lý, Error là lỗi nghiêm trọng không nên xử lý.

**Giải thích chi tiết:**
- **Exception**: Lỗi logic, có thể catch, ví dụ: NullPointerException, IOException.
- **Error**: Lỗi hệ thống, không nên catch, ví dụ: OutOfMemoryError, StackOverflowError.

**Ví dụ cụ thể:**
```java
try {
    int a = 1/0;
} catch (Exception e) {
    System.out.println("Exception");
}

try {
    int[] arr = new int[Integer.MAX_VALUE];
} catch (Error e) {
    System.out.println("Error");
}
```

**Liên kết kiến thức:**
- [[core-java/core-java.md]]
- [[core-java/example-core-java.md]]

**Mẹo/Lưu ý:**
- Không nên catch Error, chỉ catch Exception.

---
