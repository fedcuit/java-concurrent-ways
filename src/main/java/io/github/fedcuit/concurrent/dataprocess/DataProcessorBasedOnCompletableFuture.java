package io.github.fedcuit.concurrent.dataprocess;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.longs;
import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.splitData;
import static io.github.fedcuit.concurrent.dataprocess.SingleDataProcessor.compute;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

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

        // The purpose of this demo is to show how CompletableFuture works, so we avoid to use parallelStream
        List<CompletableFuture<Long>> futures = partitions.stream().map(p -> supplyAsync(() -> compute(p))).collect(toList());

        CompletableFuture<Void> allDone = allOf(futures.toArray(new CompletableFuture[futures.size()]));

        CompletableFuture<Long> sumFuture = allDone.thenApply(v -> futures.stream().map(CompletableFuture::join).mapToLong(x -> x).sum());

        return sumFuture.get();
    }
}
