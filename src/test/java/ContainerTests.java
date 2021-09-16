import org.arl.fjage.Message;
import org.arl.fjage.param.ParameterReq;
import org.arl.fjage.param.ParameterRsp;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ContainerTests
    extends AbstractTests {

  @Test
  public void testContainerWithoutConfig()
      throws IOException, InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    try (final MasterContainerSession masterContainerSession = new MasterContainerSession(container -> {
      // initialize master container
      container.add("sentuatorScanner", new SentuatorScannerAgent(agentId -> {
        log.info(agentId.getName());
        final ParameterReq parameterReq = new ParameterReq();
        log.info(">> " + parameterReq);
        final Message parameterRsp = agentId.request(parameterReq);
        log.info("<< " + parameterRsp);
        if (parameterRsp instanceof ParameterRsp) {
          countDownLatch.countDown();
        }
      }));
    })) {
      try (final SlaveContainerSession slaveContainerSession = new SlaveContainerSession(
          masterContainerSession.getMasterContainer().getPort(), container -> {
        // initialize slave container
        container.add("ctd", new TestCTDSensorWithoutConfig());
      })) {
        if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
          throw new RuntimeException("ParameterRsp not received");
        }
      }
    }
  }

  @Test
  public void testContainerWithConfig()
      throws IOException, InterruptedException {
    final CountDownLatch countDownLatch = new CountDownLatch(1);
    try (final MasterContainerSession masterContainerSession = new MasterContainerSession(container -> {
      // initialize master container
      container.add("sentuatorScanner", new SentuatorScannerAgent(agentId -> {
        log.info(agentId.getName());
        final ParameterReq parameterReq = new ParameterReq();
        log.info(">> " + parameterReq);
        final Message parameterRsp = agentId.request(parameterReq);
        log.info("<< " + parameterRsp);
        if (parameterRsp instanceof ParameterRsp) {
          countDownLatch.countDown();
        }
      }));
    })) {
      try (final SlaveContainerSession slaveContainerSession = new SlaveContainerSession(
          masterContainerSession.getMasterContainer().getPort(), container -> {
        // initialize slave container
        container.add("ctd", new TestCTDSensorWithConfig());
      })) {
        if (!countDownLatch.await(5, TimeUnit.SECONDS)) {
          throw new RuntimeException("ParameterRsp not received");
        }
      }
    }
  }
}
