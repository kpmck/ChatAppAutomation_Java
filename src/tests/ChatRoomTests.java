package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.ChatRoom;
import pages.Homepage;

public class ChatRoomTests extends BaseTestClass {

    private static String applicationPath = "https://www.chatzy.com";
    private String firstUser = "George Harris";
    private String secondUser = "Janet Solis";

    //Instantiate both chat drivers
    private WebDriver firstDriver;
    private WebDriver secondDriver;

    //Instantiate page models for both chat drivers
    private ChatRoom firstUserChat;
    private ChatRoom secondUserChat;

    private Homepage firstHomePage;
    private Homepage secondHomePage;

    @BeforeEach
    public void SetUp()
    {
        //Initialize first chat driver and accompanying page models
		WebDriverManager.chromedriver().setup();
        firstDriver = new ChromeDriver();
        drivers.add(firstDriver);

        firstUserChat = new ChatRoom(firstDriver);
        firstHomePage = new Homepage(firstDriver);

        firstDriver.navigate().to(applicationPath);
    }
    
    @Test
    public void Validate_User_Starts_Chat_Notification()
    {
        firstHomePage.SignIn(firstUser);
        Assertions.assertTrue(firstUserChat.ChatMessageExists(firstUser, "started the chat"));
    }
    
    @Test
    public void Validate_User_Joined_Chat_Notification()
    {
        //Initialize the second chat driver and accompanying page models
        secondDriver = new ChromeDriver();
        drivers.add(secondDriver);
        secondUserChat = new ChatRoom(secondDriver);
        secondHomePage = new Homepage(secondDriver);

        //Navigate to the first user's chat and log in
        Assertions.assertTrue(firstHomePage.IsAt(), "First user's homepage was not loaded.");
        firstHomePage.SignIn(firstUser);
        Assertions.assertTrue(firstUserChat.IsAt(), "first user's chat room was not loaded.");

        //Grab the first user's chat link and use it to log the second chat user in
        String chatRoomUrl = firstDriver.getCurrentUrl();
        secondDriver.navigate().to(chatRoomUrl);
        Assertions.assertTrue(secondHomePage.IsAt(), "Second user's homepage was not loaded.");
        secondHomePage.JoinChat(secondUser);
        Assertions.assertTrue(secondUserChat.IsAt(), "Second user's chat room was not loaded.");

        //Verify both chats notify second user's arrival
        Assertions.assertTrue(firstUserChat.ChatMessageExists(secondUser, "joined the chat"));
        Assertions.assertTrue(secondUserChat.ChatMessageExists(secondUser, "joined the chat"));
    } 
    
    @Test
    public void Validate_Users_Chat_Messages()
    {
        firstHomePage.IsAt();

        //Initialize the second driver and accompanying page models
        secondDriver = new ChromeDriver();
        drivers.add(secondDriver);
        secondUserChat = new ChatRoom(secondDriver);
        secondHomePage = new Homepage(secondDriver);

        //Sign in as the first user
        firstHomePage.SignIn(firstUser);
        firstUserChat.IsAt();

        //Sign in as the second user using the first user's chat url
        String chatRoomUrl = firstDriver.getCurrentUrl();
        secondDriver.navigate().to(chatRoomUrl);
        secondHomePage.IsAt();
        secondHomePage.JoinChat(secondUser);
        secondUserChat.IsAt();

        //Send chat as first user and verify second user sees the chat notification
        firstUserChat.SendChat("Hello Janet");
        Assertions.assertTrue(secondUserChat.ChatMessageExists(firstUser, "Hello Janet"));

        //Send response as second user and verify first user sees the chat notification
        secondUserChat.SendChat("Hi George, what's up?");
        Assertions.assertTrue(firstUserChat.ChatMessageExists(secondUser, "Hi George, what's up?"));
    }
}
