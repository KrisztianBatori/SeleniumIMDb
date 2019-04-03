import org.junit.*;

public class IMDbTests {

    private IMDbDemo demo = new IMDbDemo(); // Open a chrome browser.

    @Before
    public void login() {
        System.out.println("LOG INTO THE IMDB ACCOUNT");
        demo.login();
    }

    @Test
    public void checkIfRightUserHasLogged() {
        System.out.println("CHECK IF THE RIGHT USER HAS LOGGED");
        Assert.assertTrue(demo.checkUser(System.getProperty("USER_NAME")));
        System.out.println("THE RIGHT USER HAS LOGGED");
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
