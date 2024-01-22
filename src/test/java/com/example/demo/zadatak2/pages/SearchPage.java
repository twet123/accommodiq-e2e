package com.example.demo.zadatak2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SearchPage {
    private WebDriver driver;
    private static String PAGE_URL = "http://localhost:4200/search";

    @FindBy(how = How.CSS, using = "h1")
    private WebElement heading;

    @FindBy(how = How.CSS, using = "[placeholder*='Location']")
    private WebElement locationInput;

    @FindBy(how = How.CSS, using = "input[placeholder*='Date']")
    private WebElement dateInput;

    @FindBy(how = How.CSS, using = "[placeholder*='Guests']")
    private WebElement guestsInput;

    @FindBy(how = How.CSS, using = "[placeholder*='Search']")
    private WebElement titleInput;

    @FindBy(how = How.XPATH, using = "//button/span[text()='More filters']/..")
    private WebElement moreFiltersButton;

    @FindBy(how = How.XPATH, using = "//button/span[text()='Clear']/..")
    private WebElement clearButton;

    @FindBy(how = How.XPATH, using = "//button/span[text()='Search']/..")
    private WebElement searchButton;

    @FindBy(how = How.CSS, using = "input[placeholder*='Min Price']")
    private WebElement minPriceInput;

    @FindBy(how = How.CSS, using = "input[placeholder*='Max Price']")
    private WebElement maxPriceInput;

    @FindBy(how = How.CSS, using = "span[aria-label*='Select Type']")
    private WebElement typeDropdown;

    @FindBy(how = How.CSS, using = "[placeholder*='Select Benefits']")
    private WebElement benefitsDropdown;

    @FindBy(how = How.CSS, using = "div.p-overlaypanel-content")
    private WebElement overlayPanel;

    @FindBy(how = How.XPATH, using = "//span[@aria-label='Select Type']/..//p-dropdownitem")
    private List<WebElement> typeOptions;

    @FindBy(how = How.XPATH, using = "//*[@placeholder='Select Benefits']//p-multiselectitem")
    private List<WebElement> benefitOptions;

    @FindBy(how = How.CSS, using = "div.accommodation-info")
    private List<WebElement> accommodationCardsInfo;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);

        PageFactory.initElements(driver, this);
    }

    public boolean hasOpened() {
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.textToBePresentInElement(heading, "3 stays"));
    }

    public void inputLocation(String location) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(locationInput));

        locationInput.clear();
        locationInput.sendKeys(location);
    }

    public void inputDates(String startDate, String endDate) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(dateInput));

        dateInput.clear();
        dateInput.sendKeys(startDate + " - " + endDate);
    }

    public void inputGuests(int guests) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(guestsInput));

        guestsInput.clear();
        guestsInput.sendKeys(String.valueOf(guests));
    }

    public void inputTitle(String title) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(titleInput));

        titleInput.clear();
        titleInput.sendKeys(title);
    }

    private void clickMoreFiltersButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(moreFiltersButton)).click();
    }

    public void inputMinPrice(int minPrice) {
        WebDriverWait wait = new WebDriverWait(driver, 4);
        try {
            wait.until(ExpectedConditions.visibilityOf(overlayPanel));
        } catch (TimeoutException ex) {
            clickMoreFiltersButton();
        }

        wait.until(ExpectedConditions.visibilityOf(minPriceInput));

        minPriceInput.clear();
        minPriceInput.sendKeys(String.valueOf(minPrice));
    }

    public void inputMaxPrice(int maxPrice) {
        WebDriverWait wait = new WebDriverWait(driver, 4);
        try {
            wait.until(ExpectedConditions.visibilityOf(overlayPanel));
        } catch (TimeoutException ex) {
            clickMoreFiltersButton();
        }

        wait.until(ExpectedConditions.visibilityOf(maxPriceInput));

        maxPriceInput.clear();
        maxPriceInput.sendKeys(String.valueOf(maxPrice));
    }

    public boolean selectAccommodationType(String type) {
        WebDriverWait wait = new WebDriverWait(driver, 4);
        try {
            wait.until(ExpectedConditions.visibilityOf(overlayPanel));
        } catch (TimeoutException ex) {
            clickMoreFiltersButton();
        }

        wait.until(ExpectedConditions.elementToBeClickable(typeDropdown)).click();

        for (WebElement option : typeOptions) {
            if (option.findElement(By.cssSelector("span")).getText().equals(type)) {
                WebElement optionToClick = option.findElement(By.cssSelector("li"));
                wait.until(ExpectedConditions.elementToBeClickable(optionToClick)).click();
                return true;
            }
        }

        return false;
    }

    public List<String> selectAccommodationBenefits(ArrayList<String> benefits) {
        List<String> found = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, 4);
        try {
            wait.until(ExpectedConditions.visibilityOf(overlayPanel));
        } catch (TimeoutException ex) {
            clickMoreFiltersButton();
        }

        wait.until(ExpectedConditions.elementToBeClickable(benefitsDropdown)).click();

        for (WebElement option : benefitOptions) {
            String optionText = option.findElement(By.cssSelector("span")).getText();
            if (benefits.contains(optionText)) {
                WebElement optionToClick = option.findElement(By.cssSelector("li"));
                wait.until(ExpectedConditions.elementToBeClickable(optionToClick)).click();
                found.add(optionText);
            }
        }

        return found;
    }

    public void search() {
        WebDriverWait wait = new WebDriverWait(driver, 4);

        try {
            wait.until(ExpectedConditions.visibilityOf(overlayPanel));
            clickMoreFiltersButton();
        } catch (TimeoutException ignored) { }

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public boolean verifyResults(String location, String startDate, String endDate, Integer guests, String title, Integer minPrice, Integer maxPrice, String selectedType, List<String> selectedBenefits) {
        WebDriverWait wait = new WebDriverWait(driver, 4);

        for (WebElement accommodationInfo : accommodationCardsInfo) {
            wait.until(ExpectedConditions.visibilityOf(accommodationInfo));

            WebElement accommodationTitle = accommodationInfo.findElement(By.cssSelector("h2"));
            WebElement accommodationLocation = accommodationInfo.findElement(By.cssSelector("[aria-label*='location']"));
            WebElement accommodationGuestRange = accommodationInfo.findElement(By.cssSelector("[aria-label*='guest-range']"));
            WebElement accommodationType = accommodationInfo.findElement(By.cssSelector("[aria-label*='type']"));
            WebElement accommodationBenefits = accommodationInfo.findElement(By.cssSelector("[aria-label*='benefits']"));
            WebElement accommodationPrice = accommodationInfo.findElement(By.cssSelector("span.bold"));
            WebElement accommodationFullPrice = accommodationInfo.findElement(By.cssSelector("[aria-label*='price']"));

            System.out.println(accommodationTitle.getText());
            System.out.println(accommodationLocation.getText());
            System.out.println(accommodationGuestRange.getText());
            System.out.println(accommodationType.getText());
            System.out.println(accommodationBenefits.getText());
            System.out.println(accommodationPrice.getText());
            System.out.println(accommodationFullPrice.getText());

            if (title != null && !accommodationTitle.getText().contains(title))
                return false;

            if (location != null && !accommodationLocation.getText().contains(location))
                return false;

            String[] tokens = accommodationGuestRange.getText().split(" ");
            int minGuests = Integer.parseInt(tokens[0]);
            int maxGuests = Integer.parseInt(tokens[2]);
            if (guests != null && (guests < minGuests || guests > maxGuests))
                return false;

            if (selectedType != null && !accommodationType.getText().equals(selectedType))
                return false;

            if (selectedBenefits != null && !selectedBenefits.isEmpty()) {
                String[] benefitsTokens = accommodationBenefits.getText().split(" ");

                for (String benefitToken : benefitsTokens) {
                    if (!selectedBenefits.contains(benefitToken)) {
                        return false;
                    }
                }
            }

            int price = Integer.parseInt(accommodationPrice.getText().trim().substring(1));
            if (minPrice != null && maxPrice != null && (price < minPrice || price > minPrice))
                return false;

        }

        return true;
    }
}
