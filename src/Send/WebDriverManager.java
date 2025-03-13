package Send;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class WebDriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.edge.driver", "C:\\Users\\Asus\\eclipse-workspace\\Web_Test\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.manage().window().setSize(new Dimension(1196, 676));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}