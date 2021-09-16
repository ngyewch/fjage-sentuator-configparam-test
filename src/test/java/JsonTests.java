import org.arl.fjage.Message;
import org.arl.fjage.param.ParameterReq;
import org.arl.fjage.param.ParameterRsp;
import org.arl.fjage.remote.JsonMessage;
import org.arl.fjage.sentuator.ConfigParam;
import org.arl.fjage.sentuator.SentuatorParam;
import org.junit.Test;

public class JsonTests
    extends AbstractTests {

  @Test
  public void testConfigParamWithoutConfig() {
    final ParameterReq parameterReq = new ParameterReq();
    final ParameterRsp parameterRsp = new ParameterRsp(parameterReq);
    parameterRsp.set(SentuatorParam.enable, true, false);
    serializeAndDeserialize(parameterRsp);
  }

  @Test
  public void testConfigParamWithCustomConfig() {
    final ParameterReq parameterReq = new ParameterReq();
    final ParameterRsp parameterRsp = new ParameterRsp(parameterReq);
    parameterRsp.set(SentuatorParam.enable, true, false);
    parameterRsp.set(new ConfigParam("level"), 1, false);
    serializeAndDeserialize(parameterRsp);
  }

  private void serializeAndDeserialize(Message message) {
    final JsonMessage jsonMessage = new JsonMessage();
    jsonMessage.message = message;
    final String json = jsonMessage.toJson();
    log.info(">> " + json);

    final JsonMessage jsonMessage1 = JsonMessage.fromJson(json);
    log.info("<< " + jsonMessage1.toJson());
  }
}
