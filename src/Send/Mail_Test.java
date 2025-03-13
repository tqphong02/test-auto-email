package Send;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Mail_Test {
    private WebDriver driver;
    private JavascriptExecutor js;

    @BeforeClass
    public void setup() {
        driver = WebDriverManager.getDriver();
        js = (JavascriptExecutor) driver;
    }

    @AfterClass
    public void tearDown() {
        WebDriverManager.quitDriver();
    }

    @Test
    public void sendLogin() {
        System.out.println("Navigating to Gmail login page...");
        driver.get("https://accounts.google.com/v3/signin/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&ddm=0&emr=1&flowEntry=ServiceLogin&flowName=GlifWebSignIn&followup=https%3A%2F%2Fmail.google.com%2Fmail%2Fu%2F0%2F&ifkv=ARZ0qKIjn7xAtfjTLRH7_h2UQM-XB5zux0vObISFdEX4yyoIXP48_6PLNzvknd593aIBEkd0vnwg&osid=1&passive=1209600&service=mail&theme=mn&dsh=S-1543478992%3A1711091685215897");

        System.out.println("Entering username...");
        driver.findElement(By.id("identifierId")).sendKeys("testsendmailneoload@gmail.com");
        driver.findElement(By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d")).click();
        js.executeScript("window.scrollTo(0,0)");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("Waiting for password field...");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd")));

        System.out.println("Entering password...");
        driver.findElement(By.name("Passwd")).sendKeys("pass?");
        driver.findElement(By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d")).click();

        System.out.println("Waiting for login to complete...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(driver.getTitle().contains("Gmail"), "Login failed");
    }

    @Test
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

        // Wait for email to be sent
        Thread.sleep(10000);
    }

    @Test
    public void openGmailAndCheckEmailAfterSending() throws Exception {
        System.out.println("Waiting for Gmail page to load...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("Gmail"));

        // Wait for email to be received
        System.out.println("Waiting for new email...");
        Thread.sleep(5000); // Adjust this time as needed

        System.out.println("Searching for new email...");
        WebElement newEmailElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='bqe'][normalize-space()='TestMail']")));

        System.out.println("Opening new email...");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newEmailElement);

        // Wait for email to open
        System.out.println("Waiting for email to open...");
        Thread.sleep(1000);
    }

}
