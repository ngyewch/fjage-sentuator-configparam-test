import org.arl.fjage.LogFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.util.logging.Logger;

public abstract class AbstractTests {

  @Rule
  public TestName testName = new TestName();
  protected final Logger log = Logger.getLogger(getClass().getName());

  @Before
  public void beforeTesting() {
    LogFormatter.install(null);
    org.arl.fjage.GroovyExtensions.enable();
    org.arl.fjage.sentuator.GroovyExtensions.enable();
    log.info(String.format("==== BEGIN %s ====", testName.getMethodName()));
  }

  @After
  public void afterTesting() {
    log.info(String.format("==== END %s ====", testName.getMethodName()));
  }
}
