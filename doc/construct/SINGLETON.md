#### ⚠️2.1 单例模式

```
确保一个类只有一个实例，并提供一个全局访问点来访问这个唯一实例
```

从大的方向来看，单例模式可以分为**饿汉式**和**懒汉式**，考虑到性能，只讨论懒汉式

##### 2.1.1 线程不安全的实现

最简单的单例模式实现，同时是非线程安全的，具有以下特征：

1. **具有一个该类的实例属性**，该属性是**私有静态**的。私有是为了防止外部访问，`static`是因为`getInstance()`方法是静态的，静态方法无法访问非静态属性

2. **构造方法私有化**。这意味着我们不能new这个类，只能通过`类名.getInstance()`来获取实例

3. **提供一个公有的静态方法，创建（如果没创建的话）并返回该实例**。注意方法用`public static`修饰，这样我们才能通过类名调用它

```java
public class SingletonUnsafe {
    private static SingletonUnsafe instance;

    private SingletonUnsafe() {
    }

    public static SingletonUnsafe getInstance() {
        if (instance == null) {
            instance = new SingletonUnsafe();
        }
        return instance;
    }
}
```

为什么是线程不安全的？

```java
public static SingletonUnsafe getInstance() {
    if (instance == null) {	// <--线程2
        instance = new SingletonUnsafe(); // <--线程1
    }
    return instance;
}
```

在高并发的场景下，可能出现这样的一种情况：

- `线程1`首先判断了`instance`为空，开始尝试创建单例，此时刚好`线程2`也进入判断`instance`是否为空的代码。由于线程1还实际没有完成`instance`的创建，因此会造成单例的重复创建

---

##### 2.1.2 同步方法的实现

通过在 `getInstance` 方法上添加 `synchronized` 关键字，确保多线程环境下只有一个线程可以进入该方法，从而保证线程安全。但这种方式效率较低，因为每次调用 `getInstance` 时都会进行同步（无论单例是否已经创建）。

```java
package com.antares.dp.singleton;

public class SingletonSynchronizedMethod {
    private static SingletonSynchronizedMethod instance;

    private SingletonSynchronizedMethod() {
    }

    public static synchronized SingletonSynchronizedMethod getInstance() {
        if (instance == null) {
            instance = new SingletonSynchronizedMethod();
        }
        return instance;
    }
}
```

---

##### ⚠️2.1.3 双重检查锁的实现

同步方法的实现最大的问题是，锁的范围是整个方法，即使实例已经创建，后续线程调用`getInstance`时仍需要获取锁，造成不必要的同步开销。

在理想的情况下，应该只在创建实例时进行同步：

```java
public class SingletonDCL {
    private static volatile SingletonDCL instance;

    private SingletonDCL() {
    }

    public static SingletonDCL getInstance() {
        if(instance == null) {
            synchronized (SingletonDCL.class) {
                if(instance == null) {
                    instance = new SingletonDCL();
                }
            }
        }
        return instance;
    }
}
```

双重检查锁（Double-Checked Locking）的实现有几个关键点：

- 虽然名叫双重检查锁，但实际上**只使用到了一次锁**，双重的是判空。第一次判空用于快速判断实例是否已经创建，第二次判空用于确保进入了同步代码块的线程只有一个能创建实例
- 实例必须使用`volatile`修饰，这是为了**确保多线程环境下的可见性和禁止指令重排序**

> **可见性问题：**
>
> 在多线程环境中，每个线程都有自己的工作内存，**线程对变量的操作会先在工作内存中进行，然后再同步到主内存**。如果没有 `volatile` 关键字，可能会出现以下情况：
>
> - 线程 A 创建了单例实例，并将其赋值给 `instance`，但这个修改可能只存在于线程 A 的工作内存中，尚未同步到主内存。
> - 线程 B 读取 `instance` 时，可能会从主内存中读取到一个未初始化的 `instance`（即 `null`），导致线程 B 也尝试创建实例，从而破坏单例模式。
>
> `volatile` 的作用是确保变量的修改对所有线程可见。**当一个线程修改了 `volatile` 变量时，修改会立即同步到主内存**；当其他线程读取该变量时，会直接从主内存中读取最新的值。

> **指令重排序问题：**
>
> 在 Java 中，编译器和处理器可能会对指令进行重排序以优化性能。对于以下代码：
>
> ```
> instance = new Singleton();
> ```
>
> 这行代码实际上分为三个步骤：
>
> 1. 分配内存空间。
> 2. 初始化对象（调用构造函数）。
> 3. 将对象的引用赋值给 `instance`。
>
> 如果没有 `volatile`，编译器和处理器可能会将步骤 2 和步骤 3 重排序，导致 `instance` 被赋值时，对象还未完全初始化。这种情况下，其他线程可能会拿到一个未完全初始化的对象，从而导致程序行为异常。

---

##### 2.1.4 静态内部类的实现

静态内部类的方式利用了类加载机制来保证线程安全。只有在调用 `getInstance` 方法时，内部类才会被加载，从而创建实例。

```java
public class SingletonStaticInnerClass {
    private static class InstanceHolder {
        private static final SingletonStaticInnerClass INSTANCE = new SingletonStaticInnerClass();
    }
    
    private SingletonStaticInnerClass() {
    }
    
    public static SingletonStaticInnerClass getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
```

对于`InstanceHolder`：

- private：这是为了防止被外部直接访问，例如`SingletonStaticInnerClass.InstanceHolder.INSTANCE`。实例的访问点应该是唯一的，也即`getInstance`方法
- `static`：静态内部类是延迟加载的，即第一次访问`InstanceHolder`时才加载这个类