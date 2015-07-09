package io.github.fedcuit.concurrent.dataprocess;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.*;
import static io.github.fedcuit.concurrent.dataprocess.SingleDataProcessor.compute;

public class DataProcessorBasedOnListenableFutureTask {
    public static long timeConsumingOperationOnLargeDataWithoutThreadPool() throws ExecutionException, InterruptedException {
        int availableProcessorsAmount = Runtime.getRuntime().availableProcessors();
        List<List<Long>> partitions = splitData(longs(1_000), availableProcessorsAmount);

        List<ListenableFuture<Long>> listenableFutures = new ArrayList<>();

        for (List<Long> partition : partitions) {
            ListenableFutureTask<Long> e = ListenableFutureTask.create(() -> compute(partition));
            e.run();
            listenableFutures.add(e);
        }

        List<Long> longs = Futures.allAsList(listenableFutures).get();

        return sum(longs);
    }

    public static long timeConsumingOperationOnLargeDataInThreadPool() throws ExecutionException, InterruptedException {
        int availableProcessorsAmount = Runtime.getRuntime().availableProcessors();
        List<List<Long>> partitions = splitData(longs(1_000), availableProcessorsAmount);
        ExecutorService executorService = Executors.newWorkStealingPool();

        List<ListenableFuture<Long>> listenableFutures = new ArrayList<>();

        for (List<Long> partition : partitions) {
            ListenableFutureTask<Long> e = ListenableFutureTask.create(() -> compute(partition));
            executorService.submit(e);
            listenableFutures.add(e);
        }

        List<Long> longs = Futures.allAsList(listenableFutures).get();
        return sum(longs);
    }
}
