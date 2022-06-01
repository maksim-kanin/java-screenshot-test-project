package com.neuraloom.ui.steps.main_page;

import com.neuraloom.ui.DevConfig;
import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.steps.AbstractUISteps;

public class MainPageSteps extends AbstractUISteps {

    public MainPageSteps(Browser browser) {
        super(browser);
    }

    public MainPageSteps open() {
        driver().open(DevConfig.DEV_CONFIG.baseURL());
        return this;
    }
}