# Monstarlab Automation Task
This repo is for Monstarlab automation task

This Framework is build on cucumber-courgette framework with maven, because 
- My main stack is java and I am not aware of the tech stack used
- Also I'm using cucumber cause it's easier to share cases with stakeholders
- And it supports parallel execution to run multiple threads for faster execution

The tests are written using cucumber gherkin language under resources file in the test.feature file 

Prerequisite:
- Have selenium grid installed from: https://www.selenium.dev/downloads/
- Download a driver for the browser will be used for testing for example chrome : https://chromedriver.chromium.org/downloads
- Have the driver and selenium grid jar inside the same file so the grid can detect the browser (if it didnt detect you may need to allow the chrome driver from mac security under genral)
- Run the selenium grid using this command <b>java -jar selenium-server-{version}.jar standalone</b>
- The dashboard will be http://localhost:4444/ui/index.html#/ and it will appear in the terminal too

To run the tests there is 2 way:

Using maven command:
- mvn clean test (for parallel execution) its set to 4 threads in the runner class

Using normal run in the IDE:
Just click run near the scenario 

After running using maven you can find the report under target folder 
Reporting supported:
- Cucumber 
- Courgette


