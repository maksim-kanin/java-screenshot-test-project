package com.neuraloom.ui.screenshot.errors;

public class NoReferenceScreenshotError extends AssertionError {
    public NoReferenceScreenshotError() {
        super("No reference screenshot found!");
    }
}
