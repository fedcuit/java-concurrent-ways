package io.github.fedcuit.concurrent.dataprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.longs;
import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.splitData;
import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.sum;
import static io.github.fedcuit.concurrent.dataprocess.SingleDataProcessor.compute;

/**
 * This class is used to demonstrate how we use Java 8 native 'Promise' implementation 'CompletableFuture'.
 */
public class DataProcessorBasedOnCompletableFuture {
    public static long timeConsumingOperationOnLargeData() {
        return compute(longs(1_000));
    }

    public static long timeConsumingOperationOnLargeDataParallelly() throws ExecutionException, InterruptedException {
        int availableProcessorsAmount = Runtime.getRuntime().availableProcessors();
        List<List<Long>> partitions = splitData(longs(1_000), availableProcessorsAmount);

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<Long> results = new ArrayList<>();

        for (List<Long> partition : partitions) {
            futures.add(CompletableFuture.runAsync(() -> results.add(compute(partition))));
        }

        CompletableFuture<Void>[] objects = futures.toArray(new CompletableFuture[futures.size()]);
        CompletableFuture.allOf(objects).join();

        return sum(results);
    }
}
