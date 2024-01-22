package com.example.demo.zadatak2.tests;

import com.example.demo.zadatak2.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchAndFilterTest extends TestBase {


    @Test
    public void searchAndFilter() {
        SearchPage searchPage = new SearchPage(driver);

        Assert.assertTrue(searchPage.hasOpened());

        String location = "Gunduliceva";
        String startDate = "01/23/2024";
        String endDate = "01/25/2024";
        Integer guests = 2;
        String title = "Cottage";
        Integer minPrice = 20;
        Integer maxPrice = 50;
        String type = "Apartment";
        ArrayList<String> benefits = new ArrayList<>(Arrays.asList("Private Balcony"));

        searchPage.inputLocation(location);
        searchPage.inputDates(startDate, endDate);
        searchPage.inputGuests(guests);
        searchPage.inputTitle(title);
        searchPage.inputMinPrice(minPrice);
        searchPage.inputMaxPrice(maxPrice);
        boolean selectedType = searchPage.selectAccommodationType(type);
        List<String> selectedBenefits = searchPage.selectAccommodationBenefits(benefits);
        searchPage.search();

        Assert.assertTrue(searchPage.verifyResults(location, startDate, endDate, guests, title, minPrice, maxPrice, type, selectedBenefits));
    }
}
