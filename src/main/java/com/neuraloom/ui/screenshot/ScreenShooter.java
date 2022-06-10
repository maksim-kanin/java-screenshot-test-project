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
    private final String folder;
    private final String name;
    private final SelenideDriver driver;

    public ScreenShooter(Browser browser, String folder, String name) {
        this.browser = browser;
        this.folder = folder;
        this.name = name;
        this.aShot = new AShot().coordsProvider(new WebDriverCoordsProvider());
        this.driver = browser.getDriver();
    }

    public Screenshot elementScreenshot(By selector) {
        return aShot.takeScreenshot(driver.getWebDriver(), driver.$(selector));
    }

    public Screenshot pageScreenshot() {
        return aShot.takeScreenshot(driver.getWebDriver());
    }

    public boolean isScreenshotFound() {
        Path path = Paths.get(screenshotPath());
        return Files.exists(path);
    }

    public void save(Screenshot screenshot) {
        try {
            File testDir = new File(resolveBrowserDir());
            if (!testDir.exists()) {
                testDir.mkdirs();
            }
            Path path = Files.createFile(Paths.get(screenshotPath()));
            ImageIO.write(screenshot.getImage(), "png", new File(path.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        noReference(screenshot, screenshotPath());
    }

    public void compare(Screenshot reference, Screenshot actual) {
        ImageDiff diff = new ImageDiffer()
                .withDiffMarkupPolicy(new PointsMarkupPolicy().withDiffColor(Color.ORANGE))
                .makeDiff(reference, actual)
                .withDiffSizeTrigger(DIFF_SIZE_TRIGGER);
        if (diff.hasDiff()) {
            comparisonFailed(diff, reference, actual, screenshotPath());
        } else {
            comparisonPassed();
        }
    }

    public Screenshot getScreenshot() {
        try {
            return new Screenshot(ImageIO.read(new File(screenshotPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Aspect method
    private void comparisonPassed() {
        // do nothing
    }

    // Aspect method
    private void comparisonFailed(ImageDiff diff, Screenshot reference, Screenshot actual, String path) {
        throw new ScreenshotDiffException();
    }

    // Aspect method
    private void noReference(Screenshot screenshot, String path) {
        throw new NoReferenceScreenshotException();
    }

    private String screenshotPath() {
        return resolveBrowserDir() + "/" + name + ".png";
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
