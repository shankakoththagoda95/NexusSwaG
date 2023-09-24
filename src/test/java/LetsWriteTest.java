import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LetsWriteTest {
    private WebDriver driver;
    private WebDriverWait wait;

    public static String URL = "https://www.saucedemo.com/";

    @Before
    public void setup() {
        // Set up WebDriver and navigate to the test URL
        System.setProperty("webdriver.chrome.driver", "Browser\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().window().maximize();
    }

    @Test
    /*
     * Login with problem_user, password: secret_sauce
     * Add items to cart and if any items fail to get added in cart, print the name
     * For example, if there are 6 items in inventory, and only 2 fail to get added
     * in the cart,
     * print the names of those 2 items
     */
    public void addItemsToCartVerify() {
        // Login process
        driver.findElement(By.cssSelector("#user-name")).sendKeys("problem_user");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();
        String page_title = driver
                .findElement(By.cssSelector("#header_container > div.primary_header > div.header_label > div"))
                .getText();
        assert page_title.equals("Swag Labs");

        // Check Cart functionality
        int i = 1;
        while (i < 7) {
            String add_to_cart_button = "#inventory_container > div > div:nth-child(" + i + ") >div > div > button";
            String item_title = driver
                    .findElement(By.cssSelector("#inventory_container > div > div:nth-child(" + i + ") >div > div > a"))
                    .getText();
            driver.findElement(By.cssSelector(add_to_cart_button)).click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
            String add_to_cart_button1 = "#inventory_container > div > div:nth-child(" + i + ") >div > div > button";
            String button_text = driver.findElement(By.cssSelector(add_to_cart_button1)).getText();
            if (button_text.equals("Remove")) { // Checking whether after clicking once, the button changes
                System.out.println("\033[1;32m" + item_title + " Add to cart Button works as expected \033[0;37m");
            } else {
                System.out.println("\033[1;31m" + item_title + " Add to cart Button is not as expected \033[0;37m");
            }
            i++;
        }
    }

    @Test
    /*
     * Login with --> standard_user, password: secret_sauce
     * Verify that the user successfully logged in
     * Sort the list - choose any option in the DropDown.
     * Verify the list is sorted correctly
     */
    public void verifyIsLoginSuccessful() {
        // Lists to store items and prices
        List<String> Items = new ArrayList<>();
        List<String> SortedItems = new ArrayList<>();
        List<String> Price = new ArrayList<>();
        List<String> SortedPrice = new ArrayList<>();
        List<String> ReverseItems = new ArrayList<>();
        List<String> ReversePrice = new ArrayList<>();
        boolean Order = true;

        // Login process
        driver.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();
        String page_title = driver
                .findElement(By.cssSelector("#header_container > div.primary_header > div.header_label > div"))
                .getText();
        assert page_title.equals("Swag Labs");
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // A-Z Sorting dropdown item selected
        Select sort_dropdown = new Select(driver.findElement(By.cssSelector("select.product_sort_container")));
        sort_dropdown.selectByIndex(0);
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check the list with A-Z and Z-A order
        int i = 1;
        while (i < 7) {
            WebElement shopItem = driver.findElement(
                    By.cssSelector("#inventory_container > div > div:nth-child(" + i + ") >div > div > a"));
            String itemText = shopItem.getText();
            Items.add(itemText); // Add the text of the WebElement to the Items list
            i++;
        }
        // Sort the items to check the A-Z sorting function
        SortedItems = new ArrayList<>(Items);
        Collections.sort(SortedItems);
        // Checking the elements
        for (int x = 0; x < Items.size(); x++) {
            if (!SortedItems.get(x).equals(Items.get(x))) {
                Order = false;
                break; // Exit the loop early if elements differ
            }
        }
        assert Order : "A-Z Sorting is not working"; // Checking the sorted List and the list of items are equal
        System.out.println("A-Z Sorting works as expected");

        // Z-A Sorting dropdown item selected
        sort_dropdown.selectByIndex(1);
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i = 1;
        Items.clear();
        while (i < 7) {
            WebElement shopItem = driver.findElement(
                    By.cssSelector("#inventory_container > div > div:nth-child(" + i + ") >div > div > a"));
            String itemText = shopItem.getText();
            Items.add(itemText); // Add the text of the WebElement to the Items list
            i++;
        }
        // Reverse the sorted items to check the Z-A sorting function
        ReverseItems = new ArrayList<>(SortedItems);
        Collections.reverse(ReverseItems);
        for (int x = 0; x < Items.size(); x++) {
            if (!ReverseItems.get(x).equals(Items.get(x))) {
                Order = false;
                break; // Exit the loop early if elements differ
            }
        }
        assert Order : "Z-A Sorting is not working"; // Checking the sorted List and the list of items are equal
        System.out.println("Z-A Sorting works as expected");
        driver.get(driver.getCurrentUrl());
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Price (low to high) Sorting dropdown item selected
        driver.findElement(By.cssSelector("select.product_sort_container")).click();
        driver.findElement(By.cssSelector("select.product_sort_container > option[value=lohi]")).click();
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i = 1;
        while (i < 7) {
            WebElement price = driver.findElement(By.cssSelector(
                    "#inventory_container > div > div:nth-child(" + i + ") >div > div >div.inventory_item_price"));
            String itemprice = price.getText();
            Price.add(itemprice); // Add the text of the WebElement to the Items list
            i++;
        }
        SortedPrice = new ArrayList<>(Price);
        Collections.sort(SortedPrice, new PriceComparator());
        for (int x = 0; x < Items.size(); x++) {
            if (!SortedPrice.get(x).equals(Price.get(x))) {
                Order = false;
                break; // Exit the loop early if elements differ
            }
        }
        assert Order : "Price (low to high) Sorting is not working"; // Checking the sorted List and the list of items are equal
        System.out.println("Price (low to high) Sorting works as expected");

        // Price (high to low) Sorting dropdown item selected
        driver.findElement(By.cssSelector("select.product_sort_container")).click();
        driver.findElement(By.cssSelector("select.product_sort_container > option[value=hilo]")).click();
        try {
            Thread.sleep(2000); // Sleep for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Price.clear();
        i = 1;
        while (i < 7) {
            WebElement price = driver.findElement(By.cssSelector(
                    "#inventory_container > div > div:nth-child(" + i + ") >div > div >div.inventory_item_price"));
            String itemprice = price.getText();
            Price.add(itemprice); // Add the text of the WebElement to the Items list
            i++;
        }
        ReversePrice = new ArrayList<>(SortedPrice);
        Collections.reverse(ReversePrice); // Reverse the values to check High to low prices
        for (int x = 0; x < Items.size(); x++) {
            if (!ReversePrice.get(x).equals(Price.get(x))) {
                Order = false;
                break; // Exit the loop early if elements differ
            }
        }
        assert Order : "Price (high to low) Sorting is not working"; // Checking the sorted List and the list of items are equal
        System.out.println("Price (high to low) Sorting works as expected");
    }

    @After
    public void closeBrowser() {
        driver.close(); // Closes the browser
    }
}
