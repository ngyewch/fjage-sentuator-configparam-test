import org.arl.fjage.Container;
import org.arl.fjage.Platform;
import org.arl.fjage.RealTimePlatform;
import org.arl.fjage.remote.SlaveContainer;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

public class SlaveContainerSession
    implements Closeable {

  private final Platform platform;
  private final SlaveContainer slaveContainer;

  public SlaveContainerSession(int masterContainerPort, Consumer<Container> containerConsumer)
      throws IOException {
    this("localhost", masterContainerPort, containerConsumer);
  }

  public SlaveContainerSession(String masterContainerHost, int masterContainerPort,
                               Consumer<Container> containerConsumer)
      throws IOException {
    super();

    this.platform = new RealTimePlatform();
    this.slaveContainer = new SlaveContainer(platform, masterContainerHost, masterContainerPort);
    containerConsumer.accept(this.slaveContainer);
    this.platform.start();
  }

  public Platform getPlatform() {
    return platform;
  }

  public SlaveContainer getSlaveContainer() {
    return slaveContainer;
  }

  @Override
  public void close()
      throws IOException {
    platform.shutdown();
  }
}
