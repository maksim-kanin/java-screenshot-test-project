package com.neuraloom.ui.browser;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;

class BrowserContext implements TestTemplateInvocationContextProvider {
    private static TestTemplateInvocationContext context(ExtensionContext context, String browser) {
        return new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return browser.toUpperCase();
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
        String[] browsers = method.getAnnotation(Browsers.class).value();
        return Stream.of(browsers).map(browser -> context(context, browser));
    }
}
