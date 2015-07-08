package io.github.fedcuit.concurrent.dataprocess;

import java.util.stream.LongStream;

public class VerySlowService {
    public static long timeConsumingOperation(long i) {
        LongStream.range(0, 10000000).map(x -> x * x * x).sum();
        return i;
    }
}
