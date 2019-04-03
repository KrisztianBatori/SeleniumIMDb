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
        demo.logout();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        Assert.assertTrue(demo.checkUser(System.getProperty("USER_NAME")));
        demo.logout();
        demo.login("selenium@selenium.com", "12345678");
        Assert.assertFalse(demo.checkUser(System.getProperty("USER_NAME")));
        Assert.assertTrue(demo.checkUser("SeleniumTest"));
    }

    @Test
    public void checkIfRightPageFromResultIsShown() {
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        for (int i = 0; i < 3; i++) {
            demo.searchResult("game", 1);
            Assert.assertTrue(demo.checkTitleAndRatings("Game of Thrones", "9.5"));
        }
        demo.searchResult("wal", 1);
        Assert.assertTrue(demo.checkTitleAndRatings("The Walking Dead", "8.3"));
        demo.searchResult("game", 2);
        Assert.assertFalse(demo.checkTitleAndRatings("Game of Thrones", "9.5"));
        demo.searchResult("wal", 3);
        Assert.assertFalse(demo.checkTitleAndRatings("The Walking Dead", "8.3"));
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
