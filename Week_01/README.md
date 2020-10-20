**必做1：**自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内
容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。

```java
package bytecode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;

public class HelloXlassLoader extends ClassLoader{

    public static void main(String[] args) {
        try {
            Class cl = new HelloXlassLoader().findClass("Hello");
            // 调用非静态方法的时候，需要传入已初始化的实体
            cl.getMethod("hello").invoke(cl.newInstance());
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字节转换 x = 255 - x
     * 然后再用装载该字节码
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 绝对路径的值需要根据文件实际存放位置进行修改
        byte[] bytes = fileConvertToByteArray(new File("E:\\workspace\\ideaWorkSpace\\Practice\\src\\main\\resources\\Hello.xlass"));
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name,bytes,0,bytes.length);
    }

    /**
     * 将指定文件转换成byte数组
     * @param file 待转换成byte数组的文件
     * @return
     */
    private byte[] fileConvertToByteArray(File file) {
        byte[] data = null;
        try{
            // 文件输入流
            FileInputStream fis = new FileInputStream(file);
            // 字节输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer,0,len);
            }
            data = baos.toByteArray();

            fis.close();
            baos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}

````

**必做2：**画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的
关系。

![内存参数关系](./内存参数关系.png)

## 学习笔记

1. 类加载器的作用：将字节码加载到JVM虚拟机中。
   注：不然javac编译出的.class字节码文件 你觉得有啥用呢。

有几种类加载器，以及，每种加载器有什么作用。每种加载器的特点，并详细说一下。

何时进行类的加载？

再有就是类加载器如何对字节码文件进行加载的。

添加引用类的方式

应用类加载器转换成URLloader



每启动一个JVM，就会有一个堆，每启动一个线程，就会有线程栈，每个对象的成员变量都会存储在堆上（无论原生类型还是引用类型）
只有栈才有计算能力，堆是没有计算能力的。
线程与线程之间的操作都是独立的，所以互不相知，容易有并发的错误。

JVM的内存整体结构：线程栈、堆、非堆、JVM自身
启动程序常见的参数 -Xmx（最大堆内存） -Xms（最小堆内存）
所以，分配的-Xmx只是分配的堆的内存，而并没有分配 非堆、JVM自身和栈的。不要把-Xmx分配成跟物理内存相同的，一般不超过总内存的70%

除此之外，还有堆外的内存，比如JNI（Java Native Interface）方法，JNI调用了其他的库，但这其他的库仍然会占用系统的内存。

堆分为了两个区：年轻代和老年代。年轻代又分为伊甸区和存活区，存活区有两部分一个是S0一个是S1，这两个总有一个会是空的，因为，当有些对象被回收的时候，会造成空洞，再索引时还需要花时间一点点查。不如弄成两个可以随意倾倒的桶。
当年轻代 满了就丢到老年代，或者经过几次GC之后丢到老年代，又或者对象一出生就特别大，就直接丢到老年代。

非堆内存：metaspace（原先叫持久代）本地变量表这些存到这里





常用的JVM命令行工具（除了javac、javap、jar之外）：
jps、jinfo：查看java进程
jstat：查看JVM内部 gc相关信息 
`jstat -gc pid 1000（毫秒） 1000（循环次数） #展示的是占用字节`和`jstat -gcutil pid 1000 1000 #展示的是百分比`
jmap：查看heap或类占用空间统计. 什么参数都不配的话，**默认的最大堆内存是内存的四分之一**

`jmap -heap pid`可以清晰的看到内存占用情况。 并且呢，在这里学到了，伊甸 s0 s1的比例是8：1：1
但看了那个CFO分配钱的例子，大概知道比例不会一定符合8：1：1

jstack：查看线程信息 死锁的时候可以查看一下线程信息
jcmd：执行JVM相关分析命令 在1.8.0_181上使用好像跟11用法不一样
jrunscript、jjs：执行js命令



GC背景与原理

1. 引用计数

   比作仓库，用一次计数一次1，归还就改成0. 值得注意的是，引用计数一旦变成0后，就不能变回1了。因为该对象与其他对象的之间的联系断了，变得不可见了。

   关键词：内存泄露，没有人用但对象在一直增加。考虑是对象成环引用。

2. 引用跟踪

   只标记可达的，不可达的不标记，一清理清理一堆。时间快了，解决了循环依赖。
   步骤：标记清除+压缩整理

   promote

   关系跨代 rset rememberset

   串行GC的版本完蛋了。。。

   并行GC：

   ​	年轻代mark-copy 老年代mark-sweep-compact。要么不用，一用就满CPU数量跑GC，但业务线程会在某个时间段全部停止。

   ​	java8默认的是并行GC，9到现在的版本是G1

   CMS GC（Mostly concurrent Mark and sweep garbage collector）一部分GC，一部分继续干活。老年代只标记-清楚 但需要空闲列表维护索引。CMS使用的并发线程数=CPU核心数的四分之一。

   优点是暂停时间短，缺点是慢、复杂。

   六个阶段：1.预标记：标记到第一个点 2.

3. G1 GC：Garbage-First

   把GC的暂停时间可控，为了做一个平衡。

   有一个容忍度，每次只清理一部分。可以做垃圾处理的增量处理。

   尽量的用G1 GC，用了一定的数据之后， 可以调整自己的行为和策略。

   注意事项: G1有个退化问题，退化成单核处理，导致暂停时间特别长。

   

   **GC如何选择？（非常重要）**

   1. 不关心延迟，并行GC
   2. 关心延时，就G1
   3. 内存比较大（4G以上就算比较大的）用G1

    

