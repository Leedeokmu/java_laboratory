import com.freeefly.webflux.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public class MonoFluxTest {
    public static void main(String[] args) throws InterruptedException {
//        Flux<Integer> flux = Flux.just(4,5,6);
//        Mono<Integer> mono = Mono.just(4);
//        System.out.println("phase 1\n");
//        flux.doOnNext(i -> System.out.printf("call %d\n", i)).
//                subscribe();
//        Publisher<Integer> publisher = null;
//        System.out.println("phase 2");
//        flux.subscribe(System.out::println);

//        FluxTest fluxTest = new FluxTest();
//        Disposable disposable = fluxTest.vehicleHigherThen120Detected();

//        List<Integer> elements = new ArrayList<>();
//        Flux.just(1, 2, 3, 4)
//                .log()
//                .map(i -> i * 2)
//                .subscribeOn(Schedulers.parallel())
//                .subscribe(elements::add);
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        elements.stream().forEach(System.out::println);

//        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
//            int i = 0;
//            while(true) {
//                fluxSink.next(System.currentTimeMillis());
//            }
//        })
//                .sample(Duration.ofSeconds(2))
////                .log()
//                .publish();
//
//        publish.subscribe(System.out::println);
//        publish.connect();
        Hooks.onOperatorDebug();

        final List<String> basket1 = Arrays.asList(new String[]{"kiwi", "orange", "lemon", "orange", "lemon", "kiwi"});
        final List<String> basket2 = Arrays.asList(new String[]{"banana", "lemon", "lemon", "kiwi"});
        final List<String> basket3 = Arrays.asList(new String[]{"strawberry", "orange", "lemon", "grape", "strawberry"});
        final List<List<String>> baskets = Arrays.asList(basket1, basket2, basket3);
        final Flux<List<String>> basketFlux = Flux.fromIterable(baskets);

        basketFlux.flatMapSequential(basket -> {
            final Flux<String> source = Flux.fromIterable(basket).publishOn(Schedulers.parallel()).log().publish().autoConnect(2);
            final Mono<List<String>> distinctFruits = source.distinct().collectList().log();
            final Mono<Map<String, Long>> countOfFruits = source.log()
                    .groupBy(fruit -> fruit)
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                    final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                    fruitCount.put(groupedFlux.key(), count);
                                    return fruitCount;
                                }
                            )
                    )
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<>() {{
                        putAll(accumulatedMap);
                        putAll(currentMap);
                    }})
                    .subscribeOn(Schedulers.parallel())
                    ;
            return Flux.zip(distinctFruits, countOfFruits, (distinct, count) -> new FruitInfo(distinct, count));

        }).subscribe(System.out::println, Throwable::printStackTrace, () -> log.info("basketFlux end"));
        IntStream.iterate(0, n -> n+1)
                .limit(10)
                .close();
        TimeUnit.SECONDS.sleep(2);
    }
}

@Slf4j
class FluxTest{
    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    public Disposable vehicleHigherThen120Detected() {
        AtomicInteger counter = new AtomicInteger(0);
        return webClient.get()
                .uri("/vehicles")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(Vehicle.class))
                .filter(v -> v.getSpeed() > 120)
                .delayElements(Duration.ofMillis(250))
                .subscribe(s -> {
                            log.info(counter.incrementAndGet() + " >>>>>>>>>> " + s);
                        },
                        err -> log.info("Error on Vehicle Stream: " + err),
                        () -> log.info("Vehicle stream stopped!"));
    }

    Integer getAnyInteger() throws Exception {
        throw new RuntimeException("An error as occured for no reason.");
    }

    // Now, comparison between the two methods
    void compareMonoCreationMethods() {
        Mono<Integer> fromCallable = Mono.fromCallable(this::getAnyInteger);

        // result -> Mono.error(RuntimeException("An error as occured for no reason."))

        Mono<Integer> defer = Mono.defer(() -> {
            try {
                Integer res = this.getAnyInteger();
                return Mono.just(res);
            } catch(Exception e) {
                return Mono.error(e);
            }
        });
    }
}

class FruitInfo {
    private final List<String> distinctFruits;
    private final Map<String, Long> countFruits;

    public FruitInfo(List<String> distinctFruits, Map<String, Long> countFruits) {
        this.distinctFruits = distinctFruits;
        this.countFruits = countFruits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FruitInfo fruitInfo = (FruitInfo) o;

        if (distinctFruits != null ? !distinctFruits.equals(fruitInfo.distinctFruits) : fruitInfo.distinctFruits != null)
            return false;
        return countFruits != null ? countFruits.equals(fruitInfo.countFruits) : fruitInfo.countFruits == null;
    }

    @Override
    public int hashCode() {
        int result = distinctFruits != null ? distinctFruits.hashCode() : 0;
        result = 31 * result + (countFruits != null ? countFruits.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FruitInfo{" +
                "distinctFruits=" + distinctFruits +
                ", countFruits=" + countFruits +
                '}';
    }
}
