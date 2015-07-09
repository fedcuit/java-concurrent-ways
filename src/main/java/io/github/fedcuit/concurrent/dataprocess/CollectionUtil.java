package io.github.fedcuit.concurrent.dataprocess;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.LongStream.range;

public class CollectionUtil {
    static List<Long> longs(int size) {
        return range(0, size).boxed().collect(toList());
    }

    static List<List<Long>> splitData(List<Long> largeData, int number) {
        List<List<Long>> partitions = new ArrayList<>();

        int length = largeData.size();
        int sizeOfBlock = (int) Math.ceil(length / (double) number);

        for (int i = 0; i < length; i = i + sizeOfBlock) {
            int toIndex = i + sizeOfBlock;
            toIndex = toIndex > length ? length : toIndex;
            partitions.add(largeData.subList(i, toIndex));
        }
        return partitions;
    }
}
