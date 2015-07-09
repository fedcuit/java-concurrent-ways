package io.github.fedcuit.concurrent.dataprocess;

import org.junit.Test;

import java.util.List;

import static io.github.fedcuit.concurrent.dataprocess.CollectionUtil.splitData;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class CollectionUtilTest {
    @Test
    public void shouldSplitLargeDataIntoPartitions() throws Exception {
        List<Long> integers = asList(1L, 2L, 3L, 4L, 5L, 6L, 7L);

        List<List<Long>> partitions = splitData(integers, 3);

        assertThat(partitions.get(0)).isEqualTo(asList(1L, 2L, 3L));
        assertThat(partitions.get(1)).isEqualTo(asList(4L, 5L, 6L));
        assertThat(partitions.get(2)).isEqualTo(asList(7L));
    }
}