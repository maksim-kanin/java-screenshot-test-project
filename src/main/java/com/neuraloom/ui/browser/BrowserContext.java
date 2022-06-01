package com.neuraloom.ui.browser;

import com.neuraloom.ui.DevConfig;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

class BrowserContext implements TestTemplateInvocationContextProvider {
    private static TestTemplateInvocationContext context(ExtensionContext context, String browser) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                boolean isChrome = "chrome".equals(browser);
                String version = isChrome ? DevConfig.DEV_CONFIG.chromeVersion() : DevConfig.DEV_CONFIG.firefoxVersion();
                return String.format("[%s][ver: %s][width: %s, height: %s] %s",
                        browser.toUpperCase(),
                        version,
                        DevConfig.DEV_CONFIG.browserWidth(),
                        DevConfig.DEV_CONFIG.browserHeight(),
                        context.getDisplayName());
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return singletonList(new BrowserName(browser));
            }
        };
    }

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Method method = context.getRequiredTestMethod();
        String[] browsers = method.getAnnotation(BrowserTest.class).value();
        return Stream.of(browsers)
                .map(browser -> context(context, browser));
    }

    private static class BrowserName implements BeforeEachCallback {

        private final String browser;

        public BrowserName(String browser) {
            this.browser = browser;
        }

        @Override
        public void beforeEach(ExtensionContext context) {
            context.getStore(create(context.getUniqueId())).put("browser", browser);
        }
    }
}
