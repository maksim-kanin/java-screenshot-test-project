package com.neuraloom.ui.screenshot;

import com.codeborne.selenide.SelenideDriver;
import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.screenshot.errors.NoReferenceScreenshotError;
import com.neuraloom.ui.screenshot.errors.ScreenshotDiffError;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.PointsMarkupPolicy;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.coordinates.CoordsProvider;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class ScreenShooter {
    private static final int DIFF_SIZE_TRIGGER = 30;
    private final CoordsProvider coordsProvider = new WebDriverCoordsProvider();
    private final AShot aShot;
    private final SelenideDriver driver;
    private final WebDriver webDriver;

    public ScreenShooter(Browser browser) {
        this.aShot = new AShot().coordsProvider(coordsProvider);
        this.driver = browser.getDriver();
        this.webDriver = driver.getWebDriver();
    }

    public Screenshot elementScreenshot(By selector) {
        Screenshot screenshot = null;
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(300);
                screenshot = aShot.takeScreenshot(webDriver, driver.$(selector));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return screenshot;
    }

    public Screenshot pageScreenshot() {
        Screenshot screenshot = null;
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(300);
                screenshot = aShot.takeScreenshot(webDriver, driver.$("body"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return screenshot;
    }

    public boolean isScreenshotFound(String path) {
        return Files.exists(Paths.get(path));
    }

    public void save(Screenshot screenshot, ScreenshotDetails details) {
        String path = details.getPath();
        String browserDir = path.split("[\\w-]+\\.png")[0];
        try {
            File testDir = new File(browserDir);
            if (!testDir.exists()) {
                testDir.mkdirs();
            }
            ImageIO.write(screenshot.getImage(), "png", new File(Files.createFile(Paths.get(path)).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        noReference(screenshot, details);
    }

    public void compare(Screenshot reference, Screenshot actual, ScreenshotDetails details) {
        ImageDiff diff = new ImageDiffer()
                .withDiffMarkupPolicy(new PointsMarkupPolicy().withDiffColor(Color.ORANGE))
                .makeDiff(reference, actual)
                .withDiffSizeTrigger(DIFF_SIZE_TRIGGER);
        if (diff.hasDiff()) {
            if (asList(details.getIgnoredHashes()).contains(String.valueOf(diff.hashCode()))) {
                comparisonPassed();
            } else {
                comparisonFailed(diff, reference, actual, details.withHash(String.valueOf(diff.hashCode())));
            }
        } else {
            comparisonPassed();
        }
    }

    public Screenshot getScreenshot(String path) {
        try {
            return new Screenshot(ImageIO.read(new File(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Coords> getCoords(By... elements) {
        Set<Coords> coords = new HashSet<>();
        Arrays.stream(elements)
                .forEach(el -> coords.add(coordsProvider.ofElement(webDriver, driver.$(el))));
        return coords;
    }

    // Aspect method
    private void comparisonPassed() {
        // do nothing
    }

    // Aspect method
    private void comparisonFailed(ImageDiff diff, Screenshot reference, Screenshot actual, ScreenshotDetails details) {
        throw new ScreenshotDiffError();
    }

    // Aspect method
    private void noReference(Screenshot screenshot, ScreenshotDetails details) {
        throw new NoReferenceScreenshotError();
    }
}
