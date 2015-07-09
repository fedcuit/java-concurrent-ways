package io.github.fedcuit.concurrent.dataprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.longs;
import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.splitData;

/**
 * This class is used to demonstrate that how we split the data source and merge the results with Future(the old way before CompletableFuture and stream).
 */
public class DataProcessorBasedFuture {

    private static long compute(List<Long> seq) {
        Long sum = 0L;
        for (Long number : seq) {
            sum += VerySlowService.timeConsumingOperation(number);
        }
        return sum;
    }

    public static long timeConsumingOperationOnLargeData() {
        return compute(longs(1_000));
    }

    public static long timeConsumingOperationOnLargeDataParallelly() throws ExecutionException, InterruptedException {
        int availableProcessorsAmount = Runtime.getRuntime().availableProcessors();
        List<List<Long>> partitions = splitData(longs(1_000), availableProcessorsAmount);

        ExecutorService executorService = Executors.newWorkStealingPool();
        List<Future<Long>> futures = new ArrayList<>();

        for (List<Long> partition : partitions) {
            futures.add(executorService.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return compute(partition);
                }
            }));
        }

        Long sum = 0L;
        for (Future<Long> future : futures) {
            sum += future.get();
        }
        return sum;
    }
}
