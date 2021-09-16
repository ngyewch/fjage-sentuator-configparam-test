import org.arl.fjage.Container;
import org.arl.fjage.Platform;
import org.arl.fjage.RealTimePlatform;
import org.arl.fjage.remote.MasterContainer;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

public class MasterContainerSession
    implements Closeable {

  private final Platform platform;
  private final MasterContainer masterContainer;

  public MasterContainerSession(Consumer<Container> containerConsumer) {
    super();

    this.platform = new RealTimePlatform();
    this.masterContainer = new MasterContainer(platform);
    containerConsumer.accept(this.masterContainer);
    this.platform.start();
  }

  public Platform getPlatform() {
    return platform;
  }

  public MasterContainer getMasterContainer() {
    return masterContainer;
  }

  @Override
  public void close()
      throws IOException {
    platform.shutdown();
  }
}
