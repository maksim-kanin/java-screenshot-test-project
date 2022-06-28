package ui;

import com.neuraloom.ui.assertions.Ignore;
import com.neuraloom.ui.assertions.ScreenshotAssertions;
import com.neuraloom.ui.browser.Browser;
import com.neuraloom.ui.browser.BrowserTest;
import com.neuraloom.ui.steps.NeuraloomUISteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.codeborne.selenide.Browsers.CHROME;
import static com.neuraloom.ui.selectors.Neuraloom.NL;


@Epic("UI Tests")
@Feature("Main Page")
@Story("Simple tests")
public class MainPageTests {
    @RegisterExtension
    Browser browser = Browser.browser();
    @RegisterExtension
    ScreenshotAssertions assertions = new ScreenshotAssertions(browser);
    NeuraloomUISteps steps = new NeuraloomUISteps(browser);

    @BrowserTest({CHROME})
    @DisplayName("Open 'https://neuraloom.ai/' and check header")
    void shouldSeeMainPageHeader() {
        steps.openMainPage();
        assertions.compareElement("header", NL.mainPage.mainHeader);
    }

    @BrowserTest({CHROME})
    @DisplayName("Open 'https://neuraloom.ai/#employment' and check whole page")
    @Ignore(hashes = {"-313485312", "122437632"})
    void shouldSeeRealisticAvatarsPage() {
        steps.openMainPage()
                .employment();
        assertions.comparePage("page");
    }

    @BrowserTest({CHROME})
    @DisplayName("Open 'https://neuraloom.ai/' and check whole page")
    void shouldSeeMainPageWithIgnoring() {
        steps.openMainPage();
        assertions.comparePage("page", NL.mainPage.recruitEmployeeButton, NL.mainPage.employmentHeaderBlock);
    }
}