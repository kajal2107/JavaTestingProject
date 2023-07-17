package pageobjects.cord.projects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.base.BasePO;

public class ProjectDetailsDeletePostsPO extends BasePO {

    public ProjectDetailsDeletePostsPO(WebDriver driver) {
        super(driver);
    }

    private final By DeleteButton = By.xpath("//span[text()='Delete']");

    /**
     * Click on Project posts delete button
     *
     * @throws InterruptedException exception
     */
    public void clickProjectPostDeleteConfirmationButton() throws InterruptedException {
        selenium.clickOn(DeleteButton);
        selenium.waitTillElementsCountIsLessThan(DeleteButton, 1);
    }
}

