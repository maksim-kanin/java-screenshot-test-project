package com.neuraloom.ui.screenshot.exceptions;

public class ScreenshotDiffException extends AssertionError {
    public ScreenshotDiffException() {
        super("Screenshot comparison failed! See diff in attachment!");
    }
}
