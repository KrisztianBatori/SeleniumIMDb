import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {

    private IMDbDemo demo;

    @Before
    public void openBrowser() {
        System.out.println("OPEN BROWSER");
        demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
    }

    @Test
    public void checkIfRightUserHasLogged() {
        Assert.assertTrue(IMDbDemoChecks.checkUser(demo.getDriver(), System.getProperty("USER_NAME")));
        demo.logout();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        Assert.assertTrue(IMDbDemoChecks.checkUser(demo.getDriver(), System.getProperty("USER_NAME")));
        demo.logout();
        demo.login("selenium@selenium.com", "12345678");
        Assert.assertFalse(IMDbDemoChecks.checkUser(demo.getDriver(), System.getProperty("USER_NAME")));
        Assert.assertTrue(IMDbDemoChecks.checkUser(demo.getDriver(), "SeleniumTest"));
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
