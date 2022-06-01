package com.neuraloom.ui.steps;

import com.codeborne.selenide.SelenideDriver;
import com.neuraloom.ui.browser.Browser;

public abstract class AbstractUISteps {
    private final Browser browser;

    protected AbstractUISteps(Browser browser) {
        this.browser = browser;
    }

    protected SelenideDriver driver() {
        return browser.getDriver();
    }
}
