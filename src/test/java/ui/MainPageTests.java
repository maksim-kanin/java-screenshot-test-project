package ui;

import com.neuraloom.ui.assertions.ScreenshotAssertions;
import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.browser.BrowserTest;
import com.neuraloom.ui.steps.NeuraloomUISteps;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Browsers.CHROME;
import static com.codeborne.selenide.Browsers.FIREFOX;


public class MainPageTests {
    @RegisterExtension
    Browser browser = Browser.browser();
    @RegisterExtension
    ScreenshotAssertions assertions = new ScreenshotAssertions(browser);
    NeuraloomUISteps steps = new NeuraloomUISteps(browser);

    @BrowserTest({CHROME})
    void shouldSeeHeaderTest() {
        steps.openMainPage();
        assertions.comparePage("main_page");
    }

    @BrowserTest({FIREFOX})
    void shouldSeeHeaderTest2() {
        steps.openMainPage();
        assertions.compareElement("main_header", By.cssSelector(".v-toolbar__content"));
    }
}