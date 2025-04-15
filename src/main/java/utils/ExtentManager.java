package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import config.ConfigReader;

public class ExtentManager {
    public static ExtentReports extent;
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(ConfigReader.getExtentReport());
            htmlReporter.config().setDocumentTitle("Amazon Automation Report");
            htmlReporter.config().setReportName("Test Execution Results");

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Environment", ConfigReader.getEnvironment());
            extent.setSystemInfo("Tester", ConfigReader.getTesterName());
        }
        return extent;
    }

}
