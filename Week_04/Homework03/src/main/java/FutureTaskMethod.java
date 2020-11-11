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
