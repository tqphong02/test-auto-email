package Send;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.DecimalFormat;

public class TestListener implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;
    private int passCount = 0;
    private int failCount = 0;
    private int skipCount = 0;

    public void onStart(ITestContext context) {
        ExtentSparkReporter spark = new ExtentSparkReporter("extent.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String category = ""; 
        String message = ""; 
        String minCondition = ""; 
        String maxCondition = ""; 

        switch (testName) {
            case "sendLogin":
                category = "Test_Login";
                message = "Testcase đăng nhập";
                minCondition = "Đăng nhập với tên người dùng và mật khẩu hợp lệ";
                maxCondition = "Đăng nhập với tên người dùng và mật khẩu dài và phức tạp";
                break;
            case "sendEmail":
                category = "Test_SendEmail";
                message = "Testcase gửi email";
                minCondition = "Gửi email với nội dung đơn giản";
                maxCondition = "Gửi email với nội dung phức tạp và tệp đính kèm";
                break;
            case "openGmailAndCheckEmailAfterSending":
                category = "Test_CheckEmail";
                message = "Testcase kiểm tra email";
                minCondition = "Kiểm tra email sau khi gửi";
                maxCondition = "Kiểm tra email với các tiêu chí cụ thể";               
                break;
            default:
                category = "Other";
                message = "Testcase không xác định";
                break;
        }

        test = extent.createTest(testName)
                .assignAuthor("Send_Mail")
                .assignCategory(category);
        assignDevice(test, "Chrome"); // Thêm trình duyệt Chrome
        assignDevice(test, "Edge"); // Thêm trình duyệt Edge
        assignDevice(test, "FireFox"); // Thêm trình duyệt Firefox
        assignDevice(test, "Cốc_Cốc"); // Thêm trình duyệt Cốc Cốc
        
        test.log(Status.INFO, message);
        test.log(Status.INFO, "Min: " + minCondition);
        test.log(Status.INFO, "Max: " + maxCondition);
    }

    private void assignDevice(ExtentTest test, String deviceName) {
        test.assignDevice(deviceName);
    }

    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test passed");
        passCount++;
    }

    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test failed");
        failCount++;
    }

    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test skipped");
        skipCount++;
    }

    public void onFinish(ITestContext context) {
        extent.flush();
        int totalCount = passCount + failCount + skipCount;
        double passPercentage = (double) passCount / totalCount * 100;
        DecimalFormat df = new DecimalFormat("0.00");

        test.pass("Pass percentage: " + df.format(passPercentage) + "%");

        System.out.println("===============================================");
        System.out.println("Test Suite");
        System.out.println("Total tests run: " + totalCount + ", Passes: " + passCount + ", Failures: " + failCount + ", Skips: " + skipCount);
        System.out.println("Pass percentage: " + df.format(passPercentage) + "%");
        System.out.println("===============================================");
    }
}
