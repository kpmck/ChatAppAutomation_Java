package tests;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;

public class BaseTestClass
{
    //This object will contain both drivers so we can 
    public ArrayList<WebDriver> drivers = new ArrayList<WebDriver>();
    
    @AfterEach
    public void TearDown()
    {
        for(int i = 0; i<drivers.size();i++)
        {
        	drivers.get(i).quit();
        }
    }
}