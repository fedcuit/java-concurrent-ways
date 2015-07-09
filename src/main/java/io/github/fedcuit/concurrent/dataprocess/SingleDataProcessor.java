package io.github.fedcuit.concurrent.dataprocess;

import java.util.List;

public class SingleDataProcessor {
    public static long compute(List<Long> seq) {
        Long sum = 0L;
        for (Long number : seq) {
            sum += VerySlowService.timeConsumingOperation(number);
        }
        return sum;
    }
}
