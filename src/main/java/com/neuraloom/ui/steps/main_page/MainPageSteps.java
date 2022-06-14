package com.neuraloom.ui.steps.main_page;

import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.steps.AbstractUISteps;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.neuraloom.ui.DevConfig.DEV_CONFIG;
import static com.neuraloom.ui.selectors.Neuraloom.NL;
import static java.time.Duration.ofSeconds;

public class MainPageSteps extends AbstractUISteps {

    public MainPageSteps(Browser browser) {
        super(browser);
    }

    @Step("Open 'https://neuraloom.ai/'")
    public MainPageSteps open() {
        driver().open(DEV_CONFIG.baseURL());
        driver().$(NL.mainPage.chatButton).shouldBe(visible, ofSeconds(10));
        return this;
    }

    @Step("Open 'https://neuraloom.ai/#employment'")
    public MainPageSteps employment() {
        driver().open(DEV_CONFIG.baseURL() + "#employment");
        driver().$(NL.mainPage.employmentDescriptionLabel).shouldBe(visible, ofSeconds(10));
        return this;
    }
}