package io.github.fedcuit.concurrent.dataprocess;

import org.junit.Test;

import static io.github.fedcuit.concurrent.dataprocess.DataProcessorBasedOnFuture.timeConsumingOperationOnLargeData;
import static io.github.fedcuit.concurrent.dataprocess.DataProcessorBasedOnFuture.timeConsumingOperationOnLargeDataParallelly;
import static org.fest.assertions.Assertions.assertThat;

public class DataProcessorBasedOnFutureTest {

    @Test
    public void shouldSumResultsFromService() throws Exception {
        assertThat(timeConsumingOperationOnLargeData()).isEqualTo(499500);
    }

    @Test
    public void shouldSumResultsFromServiceParallelly() throws Exception {
        assertThat(timeConsumingOperationOnLargeDataParallelly()).isEqualTo(499500);
    }
}