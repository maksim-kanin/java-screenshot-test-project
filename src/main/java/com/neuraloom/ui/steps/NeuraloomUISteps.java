package com.neuraloom.ui.steps;

import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.steps.main_page.MainPageSteps;
import io.qameta.allure.Step;

public class NeuraloomUISteps {
    private final Browser browser;

    public NeuraloomUISteps(Browser browser) {
        this.browser = browser;
    }

    @Step("Open main page")
    public MainPageSteps openMainPage() {
        return new MainPageSteps(browser).open();
    }
}
