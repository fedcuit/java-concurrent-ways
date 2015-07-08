package io.github.fedcuit.concurrent.dataprocess;

import static java.util.stream.LongStream.range;

public class DataProcessorBasedOnStream {
    public static long timeConsumingOperationOnLargeData() {
        return range(0, 1_000).map(VerySlowService::timeConsumingOperation).sum();
    }

    public static long timeConsumingOperationOnLargeDataParallelly() {
        return range(0, 1_000).parallel().map(VerySlowService::timeConsumingOperation).sum();
    }
}
