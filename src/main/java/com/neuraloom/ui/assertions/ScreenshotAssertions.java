package com.neuraloom.ui.assertions;

import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.screenshot.ScreenShooter;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.By;
import ru.yandex.qatools.ashot.Screenshot;

import javax.annotation.Nullable;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

public class ScreenshotAssertions implements BeforeEachCallback {
    private final Browser browser;
    private String folder;

    public ScreenshotAssertions(Browser browser) {
        this.browser = browser;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        ExtensionContext.Store store = context.getStore(create(context.getUniqueId()));
        store.put("folder", context.getRequiredTestMethod().getName());
        folder = store.get("folder", String.class);
    }

    public void compareElement(String name, By selector) {
        compare(name, selector);
    }

    public void comparePage(String name) {
        compare(name, null);
    }

    private void compare(String name, By selector) {
        ScreenShooter shooter = new ScreenShooter(browser, folder, name);
        Screenshot actual = getActual(shooter, selector);
        if (!shooter.isScreenshotFound()) {
            shooter.save(actual);
        } else {
            Screenshot expected = shooter.getScreenshot();
            shooter.compare(expected, actual);
        }
    }

    private Screenshot getActual(ScreenShooter shooter, @Nullable By selector) {
        return selector != null ?
                shooter.elementScreenshot(selector) :
                shooter.pageScreenshot();
    }
}
