package com.neuraloom.ui.browser;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.create;

public class BrowserName implements BeforeEachCallback {
    private final String browser;

    public BrowserName(String browser) {
        this.browser = browser;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        context.getStore(create(context.getUniqueId())).put("browser", browser);
    }
}
