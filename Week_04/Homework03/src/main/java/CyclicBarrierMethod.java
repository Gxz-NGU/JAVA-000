import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
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