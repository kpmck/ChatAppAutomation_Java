package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageClass {
    private WebDriver _driver;
    public WebDriverWait wait;

    public BasePageClass(WebDriver driver){
        _driver = driver;
        wait = new WebDriverWait(_driver, Duration.ofSeconds(10));
    }

    public Boolean IsAt(By by){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        }
        catch(NoSuchElementException e){
            return false;
        }
        catch(ElementNotVisibleException e){
        	return false;
        }
    }
}
