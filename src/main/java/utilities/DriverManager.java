package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DriverManager
{
    private WebDriver driver;

    public WebDriver setUp(String browserName)
    {
        switch (browserName.toLowerCase())
        {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();
                options.setAcceptInsecureCerts(true);
                options.addPreference("dom.webnotifications.enabled", false);
                options.addPreference("dom.push.enabled", false);
                driver = new FirefoxDriver(options);
                break;

            case "chrome":
                WebDriverManager.chromedriver().setup();
                //Create a map to store  preferences
                Map<String, Object> prefs = new HashMap<>();
                //add key and value to map as follow to switch off browser notification
                //Pass the argument 1 to allow and 2 to block
                prefs.put("profile.default_content_setting_values.notifications", 2);
                //Create an instance of ChromeOptions
                ChromeOptions cOptions = new ChromeOptions();
                // set ExperimentalOption - prefs
                cOptions.setExperimentalOption("prefs", prefs);

                HashMap<String, Object> chromeLocalStatePrefs = new HashMap<>();
                List<String> experimentalFlags = new ArrayList<>();
                experimentalFlags.add("calculate-native-win-occlusion@2");
                chromeLocalStatePrefs.put("browser.enabled_labs_experiments", experimentalFlags);
                cOptions.setExperimentalOption("localState", chromeLocalStatePrefs);

                cOptions.addArguments("--incognito");
                //Now Pass ChromeOptions instance to ChromeDriver Constructor to initialize chrome driver which will switch off this browser notification on the chrome browser
                driver = new ChromeDriver(cOptions);
                break;

            case "safari":
                System.setProperty("webdriver.safari.driver", "src/main/resources/drivers/safaridriver");
                driver = new SafariDriver();
                break;

            case "chrome-headless":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("headless");
                chromeOptions.addArguments("window-size=1440x900");
                chromeOptions.addArguments("--proxy-server='direct://'");
                chromeOptions.addArguments("--proxy-bypass-list=*");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Please specify valid browser name. Valid browser names are: firefox, chrome,chrome-headless, ie ,edge");

        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    public void tearDown()
    {
        if(driver!=null)
        {
            driver.quit();
        }
    }
}
