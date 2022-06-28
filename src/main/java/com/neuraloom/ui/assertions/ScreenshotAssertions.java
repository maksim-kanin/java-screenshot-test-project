package com.neuraloom.ui.assertions;

import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.screenshot.ScreenShooter;
import com.neuraloom.ui.screenshot.ScreenshotDetails;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.By;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.Coords;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

public class ScreenshotAssertions implements BeforeEachCallback {
    private static final String PATH_CONTEXT = "src/test/resources/";
    private final Browser browser;
    private String folder;
    private String[] ignoredHashes;

    public ScreenshotAssertions(Browser browser) {
        this.browser = browser;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        Method method = context.getRequiredTestMethod();
        ExtensionContext.Store store = context.getStore(create(context.getUniqueId()));
        if (method.isAnnotationPresent(Ignore.class)) {
            String[] hashes = method.getAnnotation(Ignore.class).hashes();
            store.put("ignoredHashes", hashes);
        } else {
            store.put("ignoredHashes", new String[0]);
        }
        ignoredHashes = store.get("ignoredHashes", String[].class);
        store.put("folder", method.getName());
        folder = store.get("folder", String.class);
    }

    public void compareElement(String id, By element, By... ignoredElements) {
        compare(id, element, ignoredElements);
    }

    public void comparePage(String id, By... ignoredElements) {
        compare(id, null, ignoredElements);
    }

    private void compare(String id, By element, By... ignoredElements) {
        ScreenShooter shooter = new ScreenShooter(browser);
        Set<Coords> ignoredAreas = shooter.getCoords(ignoredElements);
        Screenshot actual = getActual(shooter, element);
        actual.setIgnoredAreas(ignoredAreas);
        String path = screenshotPath(id);
        ScreenshotDetails details = new ScreenshotDetails()
                .withIgnoredAreas(ignoredAreas)
                .withPath(path);
        if (!shooter.isScreenshotFound(path)) {
            shooter.save(actual, details);
        } else {
            Screenshot expected = shooter.getScreenshot(path);
            expected.setIgnoredAreas(ignoredAreas);
            shooter.compare(expected, actual, details.withIgnoredHashes(ignoredHashes));
        }
    }

    private Screenshot getActual(ScreenShooter shooter, @Nullable By selector) {
        return selector != null ?
                shooter.elementScreenshot(selector) :
                shooter.pageScreenshot();
    }

    private String screenshotPath(String id) {
        return resolveBrowserDir() + "/" + id + ".png";
    }

    private String resolveBrowserDir() {
        return PATH_CONTEXT + folder + "/" + resolveBrowser();
    }

    private String resolveBrowser() {
        return isChrome() ? "chrome" : "firefox";
    }

    private boolean isChrome() {
        return "chrome".equals(browser.getName());
    }
}
