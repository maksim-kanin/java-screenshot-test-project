package com.neuraloom.ui.screenshot;

import com.codeborne.selenide.SelenideDriver;
import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.screenshot.exceptions.NoReferenceScreenshotException;
import com.neuraloom.ui.screenshot.exceptions.ScreenshotDiffException;
import org.openqa.selenium.By;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.PointsMarkupPolicy;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenShooter {
    private static final int DIFF_SIZE_TRIGGER = 30;
    private static final String PATH_CONTEXT = "src/test/resources/";
    private final Browser browser;
    private final AShot aShot;
    private final String screenshotFolder;
    private final SelenideDriver driver;

    public ScreenShooter(Browser browser, String screenshotFolder) {
        this.browser = browser;
        this.screenshotFolder = screenshotFolder;
        this.aShot = new AShot().coordsProvider(new WebDriverCoordsProvider());
        this.driver = browser.getDriver();
    }

    public Screenshot elementScreenshot(By selector) {
        return aShot.takeScreenshot(driver.getWebDriver(), driver.$(selector));
    }

    public Screenshot pageScreenshot() {
        return aShot.takeScreenshot(driver.getWebDriver());
    }

    public boolean isScreenshotFound(String name) {
        Path path = Paths.get(screenshotPath(name));
        return Files.exists(path);
    }

    public void save(String name, Screenshot screenshot) {
        try {
            File testDir = new File(resolveBrowserDir());
            if (!testDir.exists()) {
                testDir.mkdirs();
            }
            Path path = Files.createFile(Paths.get(screenshotPath(name)));
            ImageIO.write(screenshot.getImage(), "png", new File(path.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new NoReferenceScreenshotException();
    }

    public void compare(Screenshot reference, Screenshot actual) {
        ImageDiff diff = new ImageDiffer()
                .withDiffMarkupPolicy(new PointsMarkupPolicy().withDiffColor(Color.ORANGE))
                .makeDiff(reference, actual)
                .withDiffSizeTrigger(DIFF_SIZE_TRIGGER);
        if (diff.hasDiff()) {
            comparisonFailed(diff, reference, actual);
        } else {
            comparisonPassed();
        }
    }

    public Screenshot getScreenshot(String name) {
        try {
            return new Screenshot(ImageIO.read(new File(screenshotPath(name))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Aspect method
    private void comparisonPassed() {
        // do nothing
    }

    // Aspect method
    private void comparisonFailed(ImageDiff diff, Screenshot reference, Screenshot actual) {
        throw new ScreenshotDiffException();
    }

    private String screenshotPath(String name) {
        return resolveBrowserDir() + "/" + name + ".png";
    }

    private String resolveBrowserDir() {
        return PATH_CONTEXT + screenshotFolder + "/" + resolveBrowser();
    }

    private String resolveBrowser() {
        return isChrome() ? "chrome" : "firefox";
    }

    private boolean isChrome() {
        return "chrome".equals(browser.getName());
    }
}