package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Homepage extends BasePageClass {

    private WebDriver _driver;

    public Homepage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
        _driver = driver;
	}

    private By Name = By.id("X8712");
    private By Subject = By.id("X7728");
    private By CreateChatButton = By.cssSelector(".X8501 input");
    private By EnterRoomButton = By.id("X6668");

    public Boolean IsAt()
    {
        return IsAt(Name);
    }

    public void SignIn(String name)
    {
        _driver.findElement(Name).sendKeys(name);
        _driver.findElement(Subject).sendKeys("chat title");
        _driver.findElement(CreateChatButton).click();
        new ChatRoom(_driver).AcceptPopup();
    }

    public void JoinChat(String name)
    {
        _driver.findElement(Name).sendKeys(name);
        _driver.findElement(EnterRoomButton).click();
    }	
}
