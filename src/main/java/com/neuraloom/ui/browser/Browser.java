package com.neuraloom.ui.browser;

import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Browsers.CHROME;
import static com.codeborne.selenide.Browsers.FIREFOX;
import static com.neuraloom.ui.DevConfig.DEV_CONFIG;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

public class Browser implements BeforeEachCallback, AfterEachCallback {
    private static final String UNKNOWN = "unknown";
    private String name;
    private SelenideDriver selenideDriver;

    private Browser(String name) {
        this.name = name;
        initDriver();
    }

    public static Browser browser() {
        return new Browser(UNKNOWN);
    }

    public static Browser chrome() {
        return new Browser(CHROME);
    }

    public static Browser firefox() {
        return new Browser(FIREFOX);
    }

    private static URL getUrl() {
        URL url;
        try {
            url = new URL(DEV_CONFIG.selenoidUrl());
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
        return url;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        if (context.getRequiredTestMethod().getAnnotation(BrowserTest.class) != null) {
            name = context.getStore(create(context.getUniqueId())).get("browser", String.class);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (isCreated()) {
            selenideDriver.close();
        }
    }

    public SelenideDriver getDriver() {
        if (!isCreated()) {
            initDriver();
            selenideDriver.getWebDriver()
                    .manage()
                    .window()
                    .setSize(new Dimension(DEV_CONFIG.browserWidth(), DEV_CONFIG.browserHeight()));
        }
        return selenideDriver;
    }

    public String getName() {
        return name;
    }

    private void initDriver() {
        if (!UNKNOWN.equals(name)) {
            DesiredCapabilities dc = new DesiredCapabilities();
            dc.setCapability("enableVNC", true);
            dc.setCapability("screenResolution", "1920x1200x24");
            switch (name) {
                case CHROME:
                    dc.setBrowserName(CHROME);
                    dc.setVersion(DEV_CONFIG.chromeVersion());
                    break;
                case FIREFOX:
                    dc.setBrowserName(FIREFOX);
                    dc.setVersion(DEV_CONFIG.firefoxVersion());
                    break;
                default:
                    throw new RuntimeException("Unsupported browser: " + name);
            }
            selenideDriver = new SelenideDriver(new SelenideConfig(), new RemoteWebDriver(getUrl(), dc), null);
        }
    }

    private boolean isCreated() {
        return selenideDriver != null;
    }
}
