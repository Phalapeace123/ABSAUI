import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Map;

public class StepDef {
    WebDriver driver;

    @Before
    public void initializingBrowser(){
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);

    }
    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        driver.manage().window().maximize();
        driver.get(url);
    }

    @Then("I validate that I am on the {string} page")
    public void i_validate_that_i_am_on_the_page(String pageTitle) {
        WebElement tableHeader = driver.findElement(By.xpath("//span[text()='User Name']"));
        Assert.assertTrue("Failed to find the User List Table header", tableHeader.isDisplayed());
    }



    @When("I add the following users:")
    public void i_add_the_following_users(DataTable dataTable) {
        List<Map<String, String>> users = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> user : users) {
            // Open the "Add User" form if it's not already open
            WebElement addUserButton = driver.findElement(By.xpath("//button[contains(text(), 'Add User')]"));
            if (driver.findElement(By.xpath("//span[text()='User Name']")).isDisplayed()) {
                addUserButton.click();
            }

            // Fill in the user details
            driver.findElement(By.name("FirstName")).clear();
            driver.findElement(By.name("FirstName")).sendKeys(user.get("First Name"));
            driver.findElement(By.name("LastName")).clear();
            driver.findElement(By.name("LastName")).sendKeys(user.get("Last Name"));
            driver.findElement(By.name("UserName")).clear();
            driver.findElement(By.name("UserName")).sendKeys(user.get("User Name"));
            driver.findElement(By.name("Password")).clear();
            driver.findElement(By.name("Password")).sendKeys(user.get("Password"));

            // Select Customer
            if (user.get("Customer").equalsIgnoreCase("Company AAA")) {
                driver.findElement(By.xpath("//input[@value='15']")).click(); // Select Company AAA
            } else {
                driver.findElement(By.xpath("//input[@value='16']")).click(); // Select Company BBB
            }

            // Select Role
            WebElement roleDropdown = driver.findElement(By.name("RoleId"));
            roleDropdown.sendKeys(user.get("Role"));

            // Fill in Email and Cell
            driver.findElement(By.name("Email")).clear();
            driver.findElement(By.name("Email")).sendKeys(user.get("Email"));
            driver.findElement(By.name("Mobilephone")).clear();
            driver.findElement(By.name("Mobilephone")).sendKeys(user.get("Cell"));

            // Click Save
            driver.findElement(By.xpath("//button[text()='Save']")).click();
        }
    }

    @Then("I ensure the following users are added to the list:")
    public void iEnsureTheFollowingUsersAreAddedToTheList(DataTable userTable) throws InterruptedException {
        Thread.sleep(3000);  // Adjust if necessary, but try to avoid using Thread.sleep in production code.

        // Parse the DataTable into a list of expected usernames
        List<Map<String, String>> expectedUsers = userTable.asMaps(String.class, String.class);

        // Locate the table rows in the DOM
        List<WebElement> tableRows = driver.findElements(By.xpath("//tbody/tr"));

        // Iterate over the expected users and verify each one exists in the table
        for (Map<String, String> expectedUser : expectedUsers) {
            String expectedUserName = expectedUser.get("User Name");
            boolean userFound = false;

            // Check each row to see if it contains the expected username
            for (WebElement row : tableRows) {
                // Locate the 3rd cell (assuming "User Name" is in the 3rd column)
                WebElement userNameCell = row.findElement(By.xpath("td[3]"));

                if (userNameCell.getText().equals(expectedUserName)) {
                    userFound = true;
                    break;
                }
            }

            // Assert that the user was found in the table
            Assert.assertTrue("User with username '" + expectedUserName + "' was not found in the table.", userFound);
        }
    }


}
