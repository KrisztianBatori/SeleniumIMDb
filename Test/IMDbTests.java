import org.junit.*;

public class IMDbTests {

    private IMDbDemo demo;

    @Before
    public void openBrowser() {
        System.out.println("OPEN BROWSER");
        demo = new IMDbDemo();
    }

    @Test
    public void checkIfRightUserHasLogged() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        Assert.assertTrue(demo.checkUser(System.getProperty("USER_NAME")));
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
