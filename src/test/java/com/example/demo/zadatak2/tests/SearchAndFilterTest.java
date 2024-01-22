package com.example.demo.zadatak2.tests;

import com.example.demo.zadatak2.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchAndFilterTest extends TestBase {

    @DataProvider(name = "testData")
    public static Object[][] createData() {
        return new Object[][]{
                new Object[]{"Gunduliceva", null, null, null, null, null, null, null, false},
                new Object[]{"Gunduliceva", null, null, 1, null, null, null, null, false},
                new Object[]{"Gunduliceva", null, null, 1, "Cottage", null, null, null, false},
                new Object[]{"Gunduliceva", null, null, 1, "Cottage", null, null, "Cottage", false},
                new Object[]{"Gunduliceva", null, null, 1, "Cottage", 20, 60, "Cottage", false},
                new Object[]{null, null, null, null, null, null, null, null, true},
                new Object[]{null, "01/25/2024", "01/27/2024", 2, null, null, null, null, true},
                new Object[]{"Gunduliceva", "01/25/2024", "01/27/2024", 2, "Cottage", 20, 120, "Cottage", false}

        };
    }

    @Test(dataProvider = "testData")
    public void searchAndFilter(String location, String startDate, String endDate, Integer guests, String title, Integer minPrice, Integer maxPrice, String type, boolean benefits) {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.clear();

        Assert.assertTrue(searchPage.hasOpened());


        ArrayList<String> benefitsArr = null;
        if(benefits) {
            benefitsArr =  new ArrayList<>(Arrays.asList("Private Balcony"));
        }

        if (location != null) {
            searchPage.inputLocation(location);
        }

        if (startDate != null && endDate != null) {
            searchPage.inputDates(startDate, endDate);
        }

        if (guests != null) {
            searchPage.inputGuests(guests);
        }

        if (title != null) {
            searchPage.inputTitle(title);
        }

        if (minPrice != null) {
            searchPage.inputMinPrice(minPrice);
        }

        if (maxPrice != null) {
            searchPage.inputMaxPrice(maxPrice);
        }

        if (type != null) {
            searchPage.selectAccommodationType(type);
        }

        List<String> selectedBenefits = null;
        if (benefitsArr != null) {
            selectedBenefits = searchPage.selectAccommodationBenefits(benefitsArr);
        }

        searchPage.search();

        Assert.assertTrue(searchPage.verifyResults(location, startDate, endDate, guests, title, minPrice, maxPrice, type, selectedBenefits));
    }
}
