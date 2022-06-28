package com.neuraloom.ui.selectors.main_page;

import org.openqa.selenium.By;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class MainPage {
    public By mainHeader = cssSelector(".v-toolbar__content");
    public By chatButton = cssSelector("div#hubspot-messages-iframe-container");
    public By employmentDescriptionLabel = xpath("//div[contains(text(),'Create Omnichannel')]");
    public By recruitEmployeeButton = cssSelector(".join-alpha > div");
    public By employmentHeaderBlock = cssSelector("header a[href='/#employment'] span");
}
