package TestMyCucumber.test;

import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.CourgetteTestOutput;
import courgette.api.CucumberOptions;
import courgette.api.junit.Courgette;
import org.junit.runner.RunWith;

@RunWith(Courgette.class)
@CourgetteOptions(
		threads = 4,
		runLevel = CourgetteRunLevel.SCENARIO,
		rerunFailedScenarios = true,
		rerunAttempts = 1,
		testOutput = CourgetteTestOutput.CONSOLE,
		reportTitle = "Courgette-JVM Example",
		reportTargetDir = "target",
		cucumberOptions = @CucumberOptions(
				features = "src/test/resources/features",
				glue = {"TestMyCucumber.test","TestMyCucumber.common"},
				tags = " ",
				publish = true,
				plugin = {
						"pretty",
						"json:target/cucumber-report/cucumber.json",
						"html:target/cucumber-report/cucumber.html",
						"junit:target/cucumber-report/cucumber.xml"
						}
		))

public class RunCukesTest{

}
