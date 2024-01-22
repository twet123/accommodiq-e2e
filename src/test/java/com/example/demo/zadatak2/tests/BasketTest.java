package com.example.demo.zadatak2.tests;

import com.example.demo.zadatak2.pages.HomePage;
import com.example.demo.zadatak2.pages.WatchesPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasketTest extends TestBase {
    @Test
    public void addToBasket() throws InterruptedException {
        HomePage homePage = new HomePage(driver);

        homePage.moveToGearLink();
        homePage.clickWatchesLink();

        WatchesPage watchesPage = new WatchesPage(driver);
        Assert.assertTrue(watchesPage.isPageOpened());

        watchesPage.addAppropriateToBasket();
        watchesPage.waitBasketPopulation();
        watchesPage.clickShowBasket();

        Assert.assertTrue(watchesPage.compareAddedWatches());
    }
}
