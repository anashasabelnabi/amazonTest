package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    public static ExtentReports extent;
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/extent-report.html");
            htmlReporter.config().setDocumentTitle("Amazon Automation Report");
            htmlReporter.config().setReportName("Test Execution Results");

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Anas");
        }
        return extent;
    }

}
