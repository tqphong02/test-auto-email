package Send;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import java.time.Duration;


public class MailTest {
    private WebDriver driver;
    private JavascriptExecutor js;

    public static void main(String[] args) throws Exception {
        MailTest mailTest = new MailTest();
        mailTest.setUp();
        mailTest.sendLogin();
        mailTest.sendEmail();
        mailTest.openGmailAndCheckEmailAfterSending();
        mailTest.tearDown();
    }

    public void setUp() {
        System.setProperty("webdriver.edge.driver", "path");
        driver = new EdgeDriver();
        driver.manage().window().setSize(new Dimension(1196, 676));
        js = (JavascriptExecutor) driver;
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void sendLogin() {
        driver.get("https://accounts.google.com/v3/signin/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&ddm=0&emr=1&flowEntry=ServiceLogin&flowName=GlifWebSignIn&followup=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&ifkv=ARZ0qKIjn7xAtfjTLRH7_h2UQM-XB5zux0vObISFdEX4yyoIXP48_6PLNzvknd593aIBEkd0vnwg&osid=1&passive=1209600&service=mail&theme=mn&dsh=S-1543478992%3A1711091685215897");
        driver.findElement(By.id("identifierId")).sendKeys("testsendmailneoload@gmail.com");
        driver.findElement(By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d")).click();
        js.executeScript("window.scrollTo(0,0)");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the password input field to be visible
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));
        driver.findElement(By.name("Passwd")).sendKeys("pass?");
        driver.findElement(By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d")).click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the element with class "xKcayf"
   //     driver.findElement(By.cssSelector(".xKcayf")).click();
    }
    
    public void sendEmail() throws Exception {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("testsendmailneoload@gmail.com", "qdvijnywavogoqqq"));
        email.setSSLOnConnect(true);
        email.setFrom("testsendmailneoload@gmail.com");
        email.setSubject("TestMail");
        email.setMsg("Send a message successfully");
        email.addTo("testsendmailneoload@gmail.com");
        email.send();
        System.out.println("Email sent");
        Thread.sleep(10000); // Đợi 5 giây trước khi chuyển sang phương thức kiểm tra email
        openGmailAndCheckEmailAfterSending();
    }
    public void openGmailAndCheckEmailAfterSending() throws Exception {
    	// Đợi cho trang Gmail được tải hoàn chỉnh
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    	wait.until(ExpectedConditions.titleContains("Gmail"));

    	// Tìm phần tử đại diện cho email mới nhận
    	WebElement newEmailElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='bqe'][normalize-space()='TestMail']")));
    	JavascriptExecutor executor = (JavascriptExecutor)driver;

    	// Thực hiện click bằng JavascriptExecutor
    	executor.executeScript("arguments[0].click();", newEmailElement);
    	 Thread.sleep(5000);
    }
    public void tearDown() {
        driver.quit();
    }
}
