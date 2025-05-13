## 设计模式

### 1. 面向对象的设计原则

#### 1.1 单一职责原则

```
一个对象应该只包含单一的职责，并且该职责被完整地封装在一个类中
```

另一种表达方式是：

```
每个类应该有且仅有一个引起它变化的原因
```

单一职责原则限定了类的粒度大小

#### 1.2 开闭原则

```
软件实体应当对扩展开放，对修改关闭
```

#### 1.3 里氏代换原则

```
所有引用基类的地方必须能透明地使用其子类的对象
```

#### 1.4 依赖倒置原则

```
高层模块不应该依赖于低层模块，它们都应该依赖抽象。抽象不依赖于细节，细节应该依赖于抽象
```

使用接口和抽象类来降低模块之间的耦合度

#### 1.5 接口隔离原则

```
客户端不应该依赖哪些它不需要的接口
```

接口应该被划分为细粒度的部分

#### 1.6 合成复用原则

```
优先使用对象组合，而不是通过继承来达到复用的目的
```

#### 1.7 迪米特法则

```
每一个软件单位对其他单位都只有最少的知识，而且局限于那些与本单位密切相关的软件单位
```

当对象之间存在负责的相互调用关系时，应该通过一个中间对象来进行通信

---

| 范围 / 目的 | 2. 创建型模式                                                | 3. 结构型模式                                                | 4. 行为型模式                                                |
| ----------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 类模式      | [2.3 工厂方法模式](https://github.com/qk-antares/antares-design-pattern/blob/main/doc/construct/FACTORY_METHOD.md) | [2.7 (类)适配器模式]()                                       | [2.15 解释器模式]()<br>模板方法模式                          |
| 对象模式    | [2.1 单例模式](https://github.com/qk-antares/antares-design-pattern/blob/main/doc/construct/SINGLETON.md)<br>[2.2 简单工厂模式](https://github.com/qk-antares/antares-design-pattern/blob/main/doc/construct/SIMPLE_FACTORY.md)<br>[2.4 抽象工厂模式](https://github.com/qk-antares/antares-design-pattern/blob/main/doc/construct/ABSTRACT_FACTORY.md)<br>[2.5 建造者模式](https://github.com/qk-antares/antares-design-pattern/blob/main/doc/construct/BUILDER.md)<br>[2.6 原型模式](https://github.com/qk-antares/antares-design-pattern/blob/main/doc/construct/PROTOTYPE.md) | [2.7 (对象)适配器模式]()<br>[2.9 桥接模式]()<br>[2.10 组合模式]()<br>[2.11 装饰模式]()<br>[2.12 外观模式]()<br>[2.13 享元模式]()<br>[2.14 代理模式]() | 责任链模式<br>命令模式<br>迭代器模式<br>中介者模式<br>备忘录模式<br>观察者模式<br>状态模式<br>策略模式<br>访问者模式 |