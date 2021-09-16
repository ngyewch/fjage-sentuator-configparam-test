import org.arl.fjage.Message;
import org.arl.fjage.param.ParameterReq;
import org.arl.fjage.param.ParameterRsp;
import org.arl.fjage.remote.JsonMessage;
import org.arl.fjage.sentuator.ConfigParam;
import org.arl.fjage.sentuator.GenericMeasurement;
import org.arl.fjage.sentuator.Quantity;
import org.arl.fjage.sentuator.SentuatorParam;
import org.junit.Test;

import java.util.UUID;

public class JsonTests
    extends AbstractTests {

  @Test
  public void testParameterRspWithoutCustomConfig() {
    final ParameterReq parameterReq = new ParameterReq();
    final ParameterRsp parameterRsp = new ParameterRsp(parameterReq);
    parameterRsp.set(SentuatorParam.enable, true, false);
    serializeAndDeserialize(parameterRsp);
  }

  @Test
  public void testParameterRspWithCustomConfig() {
    final ParameterReq parameterReq = new ParameterReq();
    final ParameterRsp parameterRsp = new ParameterRsp(parameterReq);
    parameterRsp.set(SentuatorParam.enable, true, false);
    parameterRsp.set(new ConfigParam("level"), 1, false);
    serializeAndDeserialize(parameterRsp);
  }

  @Test
  public void testGenericMeasurement() {
    final GenericMeasurement m = new GenericMeasurement();
    m.setMessageID(UUID.randomUUID().toString());
    m.setInReplyTo(UUID.randomUUID().toString());
    m.setSentAt(System.currentTimeMillis());
    m.setSensorType("mySensorType");
    m.set("salinity", new Quantity(35 + Math.random(), "ppt"));
    m.set("temperature", new Quantity(27 + Math.random(), "C"));
    m.set("depth", new Quantity(1 + Math.random(), "m"));
    serializeAndDeserialize(m);
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
