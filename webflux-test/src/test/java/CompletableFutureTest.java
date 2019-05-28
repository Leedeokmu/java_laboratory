import java.util.concurrent.*;

public class CompletableFutureTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<Void> future1
                = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over");
        });
        CompletableFuture<Void> future2
                = CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("the");
                });
        CompletableFuture<Void> future3
                = CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("world");
                });

        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(future1, future2, future3);



//        String combined = Stream.of(future1, future2, future3)
//                .map(CompletableFuture::join)
//                .collect(Collectors.joining("/"));
//        System.out.println("combined : " + combined);

    }

}

class CalculateAsync {
    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            TimeUnit.MILLISECONDS.sleep(5000);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    public Future<String> calculateAsyncWithCancellation() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.cancel(true);
            return null;
        });

        return completableFuture;
    }
}
