package TestMyCucumber.test;

import TestMyCucumber.common.StepDefs;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class testPage {

    StepDefs stepDefs = new StepDefs();

    @When("I check the number of items in the \"([^\"]*)\" list for \"([^\"]*)\" carousel will be >= \"([^\"]*)\"$")
    public void verifyListSize(String element, String index, int size) {
        stepDefs.verifyListSize(element, index, size);
    }

    @Then("I verify the items in the \"([^\"]*)\" for \"([^\"]*)\" carousel are unique$")
    public void verifyListUniqness(String element, String index) {
        stepDefs.verifyListUniqueness(element, index);
    }
}
