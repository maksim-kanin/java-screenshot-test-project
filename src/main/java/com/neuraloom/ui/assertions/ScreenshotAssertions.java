package com.neuraloom.ui.assertions;

import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.screenshot.ScreenShooter;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.By;
import ru.yandex.qatools.ashot.Screenshot;

import javax.annotation.Nullable;

public class ScreenshotAssertions implements BeforeEachCallback, AfterTestExecutionCallback {
    private final Browser browser;
    private String methodName;

    public ScreenshotAssertions(Browser browser) {
        this.browser = browser;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        //TODO переложить в контекст
        methodName = context.getRequiredTestMethod().getName();
    }

    public void compareElement(String name, By selector) {
        compare(name, selector);
    }

    public void comparePage(String name) {
        compare(name, null);
    }

    private void compare(String name, By selector) {
        ScreenShooter shooter = new ScreenShooter(browser, methodName);
        Screenshot actual = getActual(shooter, selector);
        if (!shooter.isScreenshotFound(name)) {
            shooter.save(name, actual);
        } else {
            Screenshot expected = shooter.getScreenshot(name);
            shooter.compare(expected, actual);
        }
    }

    private Screenshot getActual(ScreenShooter shooter, @Nullable By selector) {
        return selector != null ?
                shooter.elementScreenshot(selector) :
                shooter.pageScreenshot();
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
    }
}
