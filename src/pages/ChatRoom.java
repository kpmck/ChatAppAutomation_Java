package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChatRoom extends BasePageClass {

	private WebDriver _driver;

    public ChatRoom(WebDriver driver) {
		super(driver);
		_driver = driver;
	}

    private By RoomCreatedOk = By.cssSelector(".X8501 input");
    private By ChatInputField = By.id("X9225");
    private By ChatSendButton = By.id("X1117");
    private By ChatMessage(String user, String message){
        return By.xpath(String.format("//*[text()='%s']/parent::*[contains(text(),\"%s\")][last()]", user, message));
    }

    public Boolean IsAt(){
        return IsAt(ChatInputField);
    }

    public Boolean ChatMessageExists(String user, String message){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(ChatMessage(user, message)));
            return true;
            }
        catch(NoSuchElementException e){
        	return false;
        	}
        catch(ElementNotVisibleException e){
        	return false;
        	}
    }

    public void AcceptPopup()
    {
        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(RoomCreatedOk)).click();
        }
        catch (StaleElementReferenceException e)
        {
            wait.until(ExpectedConditions.elementToBeClickable(RoomCreatedOk)).click();
        }
    }

    public void SendChat(String text)
    {
        _driver.findElement(ChatInputField).sendKeys(text);
        _driver.findElement(ChatSendButton).click();
    }

}
