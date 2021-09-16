import org.arl.fjage.sentuator.GenericMeasurement;
import org.arl.fjage.sentuator.Measurement;
import org.arl.fjage.sentuator.Quantity;
import org.arl.fjage.sentuator.Sentuator;

/**
 * Sample CTD sensor outputting random values.
 */
public class TestCTDSensorWithoutConfig
    extends Sentuator {

  @Override
  protected void setup() {
    sentuatorName = "ctd/CTD02742";
    setPollingInterval(1000);
  }

  @Override
  protected Measurement measure() {
    final GenericMeasurement m = new GenericMeasurement();
    m.setSensorType(sentuatorName);
    m.set("salinity", new Quantity(35 + Math.random(), "ppt"));
    m.set("temperature", new Quantity(27 + Math.random(), "C"));
    m.set("depth", new Quantity(1 + Math.random(), "m"));
    return m;
  }
}
