package com.neuraloom.ui;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources("classpath:dev.properties")
public interface DevConfig extends Config {
    DevConfig DEV_CONFIG = ConfigFactory.create(DevConfig.class);

    @Key("baseURL")
    String baseURL();

    @Key("browser.width")
    int browserWidth();

    @Key("browser.height")
    int browserHeight();

    @Key("chrome.version")
    String chromeVersion();

    @Key("firefox.version")
    String firefoxVersion();

    @Key("selenoid.url")
    String selenoidUrl();

    @Key("github.owner")
    String gitHubOwner();

    @Key("github.repo")
    String gitHubRepo();
}
