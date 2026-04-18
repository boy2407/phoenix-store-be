# Ví dụ Core Java

## 1. Exception Handling

### Try-Catch-Finally
```java
public class ExceptionExample {
    public static void main(String[] args) {
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[5]); // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Lỗi: " + e.getMessage());
        } finally {
            System.out.println("Khối finally luôn được thực thi");
        }
    }
}
```

### Custom Exception
```java
public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

public class Person {
    private int age;

    public void setAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Tuổi không hợp lệ: " + age);
        }
        this.age = age;
    }
}

public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        try {
            person.setAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Bắt được exception: " + e.getMessage());
        }
    }
}
```

### Try-With-Resources (Java 7+)
```java
import java.io.*;

public class TryWithResourcesExample {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
        // BufferedReader được tự động đóng
    }
}
```

## 2. Collection Framework

### ArrayList vs LinkedList
```java
import java.util.*;

public class CollectionExample {
    public static void main(String[] args) {
        // ArrayList: truy cập nhanh
        List<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        System.out.println(arrayList.get(0)); // A, O(1)

        // LinkedList: thêm/xoá nhanh
        List<String> linkedList = new LinkedList<>();
        linkedList.add("X");
        linkedList.add(0, "Y"); // Thêm vào đầu, O(1)
        System.out.println(linkedList); // [Y, X]

        // Set: duy nhất
        Set<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("A"); // Không thêm được
        System.out.println(set.size()); // 2

        // Map: key-value
        Map<String, Integer> map = new HashMap<>();
        map.put("Alice", 25);
        map.put("Bob", 30);
        System.out.println(map.get("Alice")); // 25
    }
}
```

### TreeSet (Sorted)
```java
import java.util.*;

public class TreeSetExample {
    public static void main(String[] args) {
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(3);
        treeSet.add(1);
        treeSet.add(2);
        System.out.println(treeSet); // [1, 2, 3] - tự động sắp xếp
    }
}
```

## 3. String Manipulation

### String vs StringBuilder
```java
public class StringExample {
    public static void main(String[] args) {
        // String: inefficient trong loop
        String str = "";
        for (int i = 0; i < 10000; i++) {
            str += i; // Tạo object mới mỗi lần - chậm
        }

        // StringBuilder: efficient
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append(i); // Mutable - nhanh
        }
        System.out.println(sb.toString());

        // String methods
        String s = "Hello World";
        System.out.println(s.length()); // 11
        System.out.println(s.toUpperCase()); // HELLO WORLD
        System.out.println(s.substring(0, 5)); // Hello
        System.out.println(s.contains("World")); // true
    }
}
```

## 4. Thread (Đa luồng)

### Tạo Thread
```java
// Cách 1: Kế thừa Thread
class MyThread extends Thread {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

// Cách 2: Implement Runnable
class MyRunnable implements Runnable {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

public class ThreadExample {
    public static void main(String[] args) {
        // Cách 1
        MyThread thread1 = new MyThread();
        thread1.start(); // Không gọi run() trực tiếp

        // Cách 2
        Thread thread2 = new Thread(new MyRunnable());
        thread2.start();
    }
}
```

### Synchronized
```java
public class Counter {
    private int count = 0;

    // Synchronized method: chỉ một thread truy cập cùng lúc
    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}

public class SynchronizedExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(counter.getCount()); // 2000 (nếu không synchronized: random)
    }
}
```

## Liên kết kiến thức
- [[core-java/core-java.md]]
- [[core-java/interview-questions-core-java.md]]
- [[spring-boot/spring-boot.md]] (Exception handler)
- [[hibernate-jpa/hibernate-jpa.md]] (Entity)

