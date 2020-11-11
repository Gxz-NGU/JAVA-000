作业说明：分别用CompletableFuture、Future、FutureTask、CountDownLatch、CyclicBarrier、Semaphore实现了建一个线程，然后返回结果

## 有个疑问还望助教解答一下：

1. 我这思路对么？
2. 老师课上说有10种，请问还有哪些我没想到的吗？

## 为了助教方便查看，我还是把代码贴一下：

1. 使用CountDownLatch

   ```java
   import java.util.concurrent.CountDownLatch;
   
   /**
    * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
    * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
    * 写出你的方法，越多越好，提交到github。
    *
    * 一个简单的代码参考：
    */
   public class CountDownLatchMethod {
       
       public static void main(String[] args) throws InterruptedException {
   
           long start=System.currentTimeMillis();
           // 在这里创建一个线程或线程池，
   
           final CountDownLatch latch = new CountDownLatch(1);
   
           final int[] result = {0};
           new Thread(new Runnable() {
               public void run() {
                   try{
                       // 异步执行 下面方法
                       result[0] = Fibo.sum(); //这是得到的返回值
                   }finally {
                       latch.countDown();
                   }
               }
           }).start();
   
   
           latch.await(); // 确保执行完一个任务之后
           // 确保  拿到result 并输出
           System.out.println("异步计算结果为："+ result[0]);
            
           System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
           
           // 然后退出main线程
       }
   
   }
   
   ```

2. 使用CyclicBarrier

   ```java
   import java.util.concurrent.BrokenBarrierException;
   import java.util.concurrent.CyclicBarrier;
   
   /**
    * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
    * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
    * 写出你的方法，越多越好，提交到github。
    *
    * 一个简单的代码参考：
    */
   public class CyclicBarrierMethod {
       
       public static void main(String[] args) throws InterruptedException {
   
           long start=System.currentTimeMillis();
           final int[] result = {0};
           // 在这里创建一个线程或线程池，
   
           // 这里为了传参特意声明了一个MyThread
           final CyclicBarrier cyclicBarrier = new CyclicBarrier(1,new MyThread(start,result));
   
   
           new Thread(new Runnable() {
               public void run() {
                   try{
                       // 异步执行 下面方法
                       result[0] = Fibo.sum(); //这是得到的返回值
                       cyclicBarrier.await();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   } catch (BrokenBarrierException e) {
                       e.printStackTrace();
                   }
               }
           }).start();
   
   
           
           // 然后退出main线程
       }
   
   }
   class MyThread implements Runnable {
   
       private Long start;
   
       private int[] result;
   
       public MyThread(Long start, int[] result){
           this.start = start;
           this.result = result;
       }
   
       public void run() {
           // 确保  拿到result 并输出
           System.out.println("异步计算结果为："+ result[0]);
   
           System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
           System.out.println("该完成的任务已经完成");
       }
   }
   ```

3. 使用Semaphore

   ```java
   import java.util.concurrent.Semaphore;
   
   /**
    * 一个工人，有一台机器
    */
   public class SemaphoreMethod {
       public static void main(String[] args) throws InterruptedException {
   
           final long start=System.currentTimeMillis();
           // 在这里创建一个线程或线程池，
   
           int N = 1; // 一个工人
   
           final Semaphore semaphore = new Semaphore(1);
   
           final int[] result = {0};
           new Thread(new Runnable() {
               public void run() {
                   try {
                       semaphore.acquire();
                       // 异步执行 下面方法
                       result[0] = Fibo.sum(); //这是得到的返回值
                       // 确保  拿到result 并输出
                       System.out.println("异步计算结果为："+ result[0]);
   
                       System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
   
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   } finally {
                       semaphore.release();
                   }
               }
           }).start();
   
   
   
   
           // 然后退出main线程
       }
   
   }
   ```

4. Future

   ```java
   import java.util.concurrent.*;
   
   /**
    * 使用Future+线程池的方式实现返回值
    */
   public class FutureMethod {
       public static void main(String[] args) throws ExecutionException, InterruptedException {
           final long start=System.currentTimeMillis();
           ExecutorService executor = Executors.newCachedThreadPool();
           Future<Integer> result = executor.submit(new Callable<Integer>() {
               public Integer call() throws Exception {
                   return Fibo.sum();
               }
           });
           executor.shutdown();
   
           System.out.println("异步计算结果为："+ result.get());
   
           System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
       }
   }
   
   ```

5. FutureTask

   ```java
   import java.util.concurrent.Callable;
   import java.util.concurrent.ExecutionException;
   import java.util.concurrent.FutureTask;
   
   /**
    * 使用FutureTask + 线程的方式
    */
   public class FutureTaskMethod {
       public static void main(String[] args) throws ExecutionException, InterruptedException {
           long start = System.currentTimeMillis();
           // 因为FutureTask可以直接放在线程里面
           FutureTask<Integer> task  = new FutureTask<Integer>(new Callable<Integer>() {
               public Integer call() throws Exception {
                   return Fibo.sum();
               }
           });
           new Thread(task).start();
   
           System.out.println("异步计算结果为："+ task.get());
   
           System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
       }
   }
   ```

6. CompletableFuture

   ```java
   import java.util.concurrent.CompletableFuture;
   import java.util.concurrent.ExecutionException;
   
   /**
    * 使用CompletableFuture
    */
   public class CompletableFutureMethod {
       public static void main(String[] args) throws ExecutionException, InterruptedException {
           long start = System.currentTimeMillis();
           Integer result = CompletableFuture.supplyAsync(()->{return Fibo.sum();}).get();
           System.out.println("异步计算结果为："+ result);
   
           System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
       }
   }
   
   ```

学习笔记

java并发包 juc？

### 有了synchronized为何还要lock接口？

为了灵活；时间可控；拿不到锁就离开，不等待。

### 性能哪个更好？

1.6之前，用显示的锁性能更好，之后性能差不多。之所以并发编程里经常用显式的锁，是因为lock比较灵活。

### 什么是可重入锁？

某个线程已经获得某个锁，再次获取锁时不会出现死锁。synchronized、ReentrantLock(true)

### 什么是公平锁？

最先排队的线程有一定使用锁的优先级。

### 什么读写锁？

读锁与写锁分开，以前是大家抢同一把锁，现在分流

### 优化锁的最佳实践：

减少锁的范围（锁的粒度）；锁分离（一把锁变成几个）

Condition接口

LockSupport工具类

### 用锁的最佳实践：

Doug Lea提到的三个最佳实践：

1. 永远只在更新对象的成员变量时加锁
2. 永远只在访问可变的成员变量时加锁
3. 永远不在调用其他对象的方法时加锁

### 为什么要有并发原子类？

Atomic工具类，被多线程执行时，能按照语义正确的执行

### 原子类的无锁的底层实现？悲观锁和乐观锁CAS=compare and set

1. volatile实现可见性，获取的数据永远是主存中最新的数据
2. CAS

以数据库中的一行记录举例

悲观锁，直接锁住当前行，不让其他线程对该行的数值进行更新。

乐观锁，对原有的值进行记录，在写回的过程中发现原来的值不变，则说明可以把值写入；如果变了，则拿新的值再去做一遍操作（自旋）。

乐观锁的好处在于，在执行操作过程中，别的线程依旧可以对该数值进行改变 ，不需要等待。

### 锁和无锁哪个更好？

一般来讲，无锁好一点，因为无锁，业务可以让线程并发的执行，效率会更高一点。

在线程非常少的情况 ，锁的开销可以忽略不记，有锁和无锁用起来差不多。

在线程非常多的情况，无锁一直在进行大量的自旋操作，消耗大量的CPU资源，然后结果始终无法写回，这个时候还不如加锁。

### LongAdder是如何对atomic进行改进的？

原先是大家竞争的是一个volatile值，现在是每有一个线程，就给它一个值 ，形成一个cell数组，每个线程对自己的cell[i]++，最后再做稽核操作。

### 并发工具类作用？

为了解决多线程之间相互协作的问题。

### AQS：AbstractQueuedSynchronizer队列同步器

核心、复杂、双向队列

### Semaphore-信号量

可以调权重。使用场景：可以明确的指定一个任务线程的数量。

### CountdownLatch门栓

countdown，await，精确的把控线程的结束。场景：await放在主线程中，把控到之后用来做其他的任务

### CyclicBarrier 

等到所有人都到某个状态。场景：和CountdownLatch差不多，有几个任务完成了，就可以做其他的事情了。