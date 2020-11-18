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
