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

import javax.swing.text.html.CSS;
import java.util.ArrayList;
import java.util.List;

public class WatchesPage {
    private WebDriver driver;
    @FindBy(how = How.CLASS_NAME, using = "product-item-info")
    private List<WebElement> watches;

    @FindBy(how = How.CSS, using = "#page-title-heading > span")
    private WebElement heading;

    @FindBy(how = How.CLASS_NAME, using = "showcart")
    private WebElement showBasket;

    @FindBy(how = How.CSS, using = "span.counter-number")
    private WebElement counterNumber;

    @FindBy(how = How.CSS, using = "#mini-cart .product-item-name a")
    private List<WebElement> addedWatchesElements;

    private ArrayList<String> addedWatches = new ArrayList<>();

    public WatchesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addAppropriateToBasket() {
        System.out.println(watches.size());

        for (WebElement watch : watches) {
            WebElement reviewElement = watch.findElement(By.cssSelector(".reviews-actions > a"));

            if (Integer.parseInt(reviewElement.getText().split(" ")[0]) > 2) {
                Actions actions = new Actions(driver);
                actions.moveToElement(watch).build().perform();

                (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.elementToBeClickable(watch.findElement(By.className("tocart")))).click();

                addedWatches.add(watch.findElement(By.cssSelector(".product-item-name > a")).getText());
                waitBasketPopulation();
            }
        }
    }

    public boolean isPageOpened() {
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(heading, "Watches"));
    }

    public void clickShowBasket() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(showBasket)).click();
    }

    public void waitBasketPopulation() {
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(counterNumber, String.valueOf(addedWatches.size())));
    }

    public boolean compareAddedWatches() {
        for (WebElement addedWatchesElement : addedWatchesElements) {
            String elText = addedWatchesElement.getText();

            if (!addedWatches.contains(elText)) {
                return false;
            }
        }

        return true;
    }
}
