package com.example.demo.zadatak2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;

    private static String PAGE_URL="https://magento.softwaretestingboard.com/";

    @FindBy(how = How.LINK_TEXT, using = "Gear")
    private WebElement gearLink;

    @FindBy(how = How.LINK_TEXT, using = "Watches")
    private WebElement watchesLink;

    public HomePage(WebDriver driver){
        this.driver=driver;
        driver.get(PAGE_URL);

        PageFactory.initElements(driver, this);
    }

    public void moveToGearLink() throws InterruptedException {
        WebElement uiIcon = gearLink.findElement(By.cssSelector("span:nth-child(1)"));
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(uiIcon));
        Actions actions = new Actions(driver);
        actions.moveToElement(gearLink).build().perform();
    }

    public void clickWatchesLink() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(watchesLink)).click();
    }
}
