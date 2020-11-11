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
