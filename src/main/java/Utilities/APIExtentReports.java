package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class APIExtentReports implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report- " + timeStamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\report\\" + repName);
        sparkReporter.config().setDocumentTitle("APIAutomation");
        sparkReporter.config().setReportName("UserAPI");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Pet Store user API");
        extent.setSystemInfo("Operating system Info", System.getProperty("OS.Name"));
        extent.setSystemInfo("UserName", System.getProperty("user.name"));
        extent.setSystemInfo("Env", "QA");
        extent.setSystemInfo("username", "Bijayalaxmi");
    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.log(Status.FAIL, "Test Failed");
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.log(Status.FAIL, throwable.getMessage());
        } else {
            test.log(Status.FAIL, "No error message available");
        }
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.log(Status.SKIP, "Test Skipped");
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            test.log(Status.SKIP, throwable.getMessage());
        } else {
            test.log(Status.SKIP, "No error message available");
        }
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
