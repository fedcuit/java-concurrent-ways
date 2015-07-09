package io.github.fedcuit.concurrent.dataprocess;

import org.junit.Test;

import static io.github.fedcuit.concurrent.dataprocess.DataProcessorBasedOnListenableFutureTask.timeConsumingOperationOnLargeDataInThreadPool;
import static io.github.fedcuit.concurrent.dataprocess.DataProcessorBasedOnListenableFutureTask.timeConsumingOperationOnLargeDataWithoutThreadPool;
import static org.fest.assertions.Assertions.assertThat;

public class DataProcessorBasedOnListenableFutureTaskTest {
    @Test
    public void shouldSumResultsFromServiceWithoutThreadPool() throws Exception {
        assertThat(timeConsumingOperationOnLargeDataWithoutThreadPool()).isEqualTo(499500);
    }

    @Test
    public void shouldSumResultsFromServiceInThreadPool() throws Exception {
        assertThat(timeConsumingOperationOnLargeDataInThreadPool()).isEqualTo(499500);
    }

}