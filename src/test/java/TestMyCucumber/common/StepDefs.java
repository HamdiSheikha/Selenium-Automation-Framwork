package TestMyCucumber.common;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class StepDefs {


    public static FluentWait wait;
    String enviroment = "staging";
    public static WebDriver driver;
    DesiredCapabilities capabillities;

    @Before
    public void setupBrowser() throws IOException {

        System.out.println("Before Hook");

        switch ("Chrome") {

            case "Chrome":
                capabillities = DesiredCapabilities.chrome();
                break;


            case "Firefox":
                capabillities = DesiredCapabilities.firefox();
                break;


            case "IE":
                capabillities = DesiredCapabilities.internetExplorer();
                break;


            case "Safari":
                capabillities = DesiredCapabilities.safari();
                break;

            default:
                capabillities = DesiredCapabilities.chrome();
                break;
        }


        this.driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabillities);
        driver.manage().window().maximize();

        wait = new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(Exception.class);

        driver.get("https://mall.cz/");
        driver.navigate();

    }

    /**
     * This method will get the property file that we're using for locators depends on the environment
     *
     * @param value
     * @return
     */
    public String getProp(String value) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(
                    System.getProperty("user.dir") + "/Locators/" + enviroment + ".properties");
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop.getProperty(value);
    }

    /**
     * This method will scroll down the number of times
     *
     * @param times
     */
    public void scrollToElement(int times) {
        times = times * 600;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + times + ")", "");
        sleepTime(1.5);
    }

    /**
     * This Method will return the locator value with replacing the value (toReplace) with (replacedBy)
     *
     * @param locator
     * @param toReplace
     * @param replacedBy
     * @return
     */

    public String getReplaceableLocator(String locator, String toReplace, String replacedBy) {
        locator = getProp(locator);
        locator = locator.replace(toReplace, replacedBy);
        System.out.println("Locator is: " + locator);
        return locator;
    }

    /**
     * This Method is not in use but for calling the list if there's no replaceable words we can use it
     *
     * @param locator
     * @return
     */
    public String getLocator(String locator) {
        locator = getProp(locator);
        System.out.println("Locator is: " + locator);
        return locator;
    }

    /**
     * This Method will return a list of elements
     *
     * @param locator
     * @return
     */
    public List<WebElement> findElements(String locator) {

        try {
            if (!driver.findElements(By.id(locator)).isEmpty()) {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.id(locator)));
                return driver.findElements(By.id(locator));
            } else if (!driver.findElements(By.name(locator)).isEmpty()) {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.name(locator)));
                return driver.findElements(By.name(locator));
            } else if (!driver.findElements(By.cssSelector(locator)).isEmpty()) {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator)));
                return driver.findElements(By.cssSelector(locator));
            } else {
                try {

                } catch (Exception e) {
                }
            }
        } catch (InvalidSelectorException e) {
            if (!driver.findElements(By.xpath(locator)).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                return driver.findElements(By.xpath(locator));
            }
        }
        return null;
    }

    /**
     * This method will check weather the locator is clickable or not
     *
     * @param locator
     * @return
     */
    public WebElement findClickableElement(String locator) {

        try {
            if (!driver.findElements(By.id(locator)).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.id(locator)));
                return driver.findElement(By.id(locator));
            } else if (!driver.findElements(By.name(locator)).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.name(locator)));
                return driver.findElement(By.name(locator));
            } else if (!driver.findElements(By.cssSelector(locator)).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locator)));
                return driver.findElement(By.cssSelector(locator));
            } else {
                try {

                } catch (NoSuchElementException e) {
                }
            }
        } catch (InvalidSelectorException e) {
            if (!driver.findElements(By.xpath(locator)).isEmpty()) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                return driver.findElement(By.xpath(locator));
            }
        }
        return null;
    }

    /**
     * This method will click on locator
     *
     * @param locator
     */
    public void clickOnElement(String locator) {
        try {
            findClickableElement(locator).click();
        } catch (Exception e) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", findClickableElement(locator));
        }
    }

    /**
     * This Method will check the list size and if it's less than the expected size it will fail
     *
     * @param element
     * @param index
     * @param expectedSize
     */
    public void verifyListSize(String element, String index, int expectedSize) {
        element = getReplaceableLocator(element, "replace", index);
        Assert.assertTrue(findElements(element).size() >= expectedSize);
    }


    /**
     * This Method will put the first 15 item in an array list to check the uniqueness
     *
     * @param element
     * @param index
     * @return
     */
    public boolean verifyListUniqueness(String element, String index) {

        ArrayList<String> itemsHeader = new ArrayList<String>();
        //Assign button of next slide
        String btnElement, titleConcat;

        //Get the next button & the title of each item
        if (element.contains("large_half_width")) {
            btnElement = getReplaceableLocator("large_half_width_carousel_next", "replace", index);
            titleConcat = "> article > a > div > div > div:nth-child(1) > h3";
        } else if (element.contains("large_full_width")) {
            btnElement = getReplaceableLocator("large_full_width_carousel_next", "replace", index);
            titleConcat = "> article > a > div > div > div:nth-child(1) > h3";

        } else {
            btnElement = getReplaceableLocator("small_full_width_carousel_next", "replace", index);
            titleConcat = "> a >span:nth-child(2) > span:nth-child(1)";
        }


        element = getReplaceableLocator(element, "replace", index);

        int counter = 0;
        //Loop over the first 15 item and out the title into that list
        while (itemsHeader.size() != 15) {

            String elementTitle = element + titleConcat;
            String itemTitle = (findElements(elementTitle).get(counter).getText());

            //We should click on next if the rest of the items are rendered
            if (itemTitle.isEmpty()) {
                clickOnElement(btnElement);
                sleepTime(1);
            } else {
                itemsHeader.add(itemTitle);
                counter++;
            }

        }
//        To add a new item to the list which is already there in the list
//        itemsHeader.add("LEGO Wear chlapecká bunda Jebel LW-11010262");

        //To check weather any item is duplicated in that list
        for (int i = 0; i < itemsHeader.size(); i++) {
            for (int j = i + 1; j < itemsHeader.size(); j++) {
                if (itemsHeader.get(i).toString().equals(itemsHeader.get(j).toString())) {
                    Assert.assertTrue(false);
                }

            }
        }

        return true;
    }


    /**
     * This method to have a static wait if needed
     *
     * @param time
     */
    public void sleepTime(double time) {
        try {
            Thread.sleep((long) (time * 1000));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    @After
    public void embedScreenshot(Scenario scenario) throws IOException {

        System.out.println("AFTER");
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = "./target/allure-results/" + System.currentTimeMillis() + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            FileInputStream inputstream = new FileInputStream(new File(dest).getAbsolutePath());
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
            driver.quit();
        }
        driver.quit();
    }

}




