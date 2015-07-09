package io.github.fedcuit.concurrent.dataprocess;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.*;
import static io.github.fedcuit.concurrent.dataprocess.SingleDataProcessor.compute;

/**
 * This class is used to demonstrate how we extend JDK's concurrent api to enable 'Promise' by using Guava.
 */
public class DataProcessorBasedOnListenableFuture {
    public static long timeConsumingOperationOnLargeData() {
        return compute(longs(1_000));
    }

    public static long timeConsumingOperationOnLargeDataParallelly() throws ExecutionException, InterruptedException {
        int availableProcessorsAmount = Runtime.getRuntime().availableProcessors();
        List<List<Long>> partitions = splitData(longs(1_000), availableProcessorsAmount);

        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newWorkStealingPool());

        List<ListenableFuture<Long>> listenableFutures = new ArrayList<>();

        for (List<Long> partition : partitions) {
            listenableFutures.add(listeningExecutorService.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return compute(partition);
                }
            }));
        }

        List<Long> longs = Futures.allAsList(listenableFutures).get();

        return sum(longs);
    }
}
