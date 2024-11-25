import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/java/featureFile.feature",
        glue = "",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-html-reports.html",  // HTML report
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        }
)
public class TestRunner {
}
