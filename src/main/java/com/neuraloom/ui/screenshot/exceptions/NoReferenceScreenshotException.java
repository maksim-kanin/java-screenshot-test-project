package com.neuraloom.ui.screenshot.exceptions;

public class NoReferenceScreenshotException extends AssertionError {
    public NoReferenceScreenshotException() {
        super("No reference screenshot found!");
    }
}
