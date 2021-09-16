import org.arl.fjage.Agent;
import org.arl.fjage.AgentID;
import org.arl.fjage.TickerBehavior;
import org.arl.fjage.sentuator.Services;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SentuatorScannerAgent
    extends Agent {

  private final Consumer<AgentID> consumer;
  private final Set<AgentID> handledAgentIds = new HashSet<>();

  public SentuatorScannerAgent(Consumer<AgentID> consumer) {
    super();

    this.consumer = consumer;
  }

  @Override
  protected void init() {
    add(new TickerBehavior(100, () -> {
      final AgentID[] agentIds = agentsForService(Services.SENTUATOR);
      for (final AgentID agentId : agentIds) {
        if (handledAgentIds.add(agentId)) {
          consumer.accept(agentId);
        }
      }
    }));
  }
}
