package TestMyCucumber.test;

import TestMyCucumber.common.StepDefs;
import io.cucumber.java.en.Given;

public class commonPage {
    StepDefs stepDefs = new StepDefs();

    @Given("I scroll \"([^\"]*)\" times$")
    public void scrollTo(int times) {
        stepDefs.scrollToElement(times);
    }
}
