package com.neuraloom.ui.screenshot.errors;

public class ScreenshotDiffError extends AssertionError {
    public ScreenshotDiffError() {
        super("Screenshot comparison failed! See diff in attachment!");
    }
}
