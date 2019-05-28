import java.util.concurrent.*;

public class FactorialTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
//        List<Future<Integer>> results = new ArrayList<>();
//        Random random = new Random();
//        IntStream.range(0,4).forEach(number -> {
//            Integer num = random.nextInt(50);
//            FactorialCalculator fc = new FactorialCalculator(num);
//            Future result = executor.submit(fc);
//            results.add(result);
//        });
//
//        for(Future<Integer> future : results)
//        {
//            try
//            {
//                System.out.println("Future result is - " + " - " + future.get() + "; And Task done is " + future.isDone());
//            }
//            catch (InterruptedException | ExecutionException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        executor.shutdwn();

        ForkJoinPool forkJoinPool = new ForkJoinPool(128);
        FactorialSquareCalculator calculator = new FactorialSquareCalculator(10000);
        Future<Long> future = forkJoinPool.submit(calculator);
        while(!future.isDone()){
            TimeUnit.MILLISECONDS.sleep(10);
        }
        Long i = future.get();

//        System.out.println(forkJoinPool.invoke(calculator));
        forkJoinPool.shutdownNow();

    }
}

class FactorialCalculator implements Callable<Integer> {
    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        int result = 1;
        if ((number == 0) || (number == 1)) {
            result = 1;
        } else {
            for (int i = 2; i <= number; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }
        System.out.println("Result for number = " + number + " -> " + result);
        return result;
    }
}

class FactorialSquareCalculator extends RecursiveTask<Long> {

    private Integer n;

    public FactorialSquareCalculator(Integer n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1) {
            return (long)n;
        }

        FactorialSquareCalculator calculator
                = new FactorialSquareCalculator(n - 1);
//        System.out.printf("n : %d\n", n);

        calculator.fork();
        Long result =  n * n + calculator.join();
        System.out.printf("result : %d\n", result);
        return result;
    }
}