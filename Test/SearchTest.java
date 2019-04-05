import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchTest {

    private IMDbDemo demo;

    @Before
    public void openBrowser() {
        System.out.println("OPEN BROWSER");
        demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
    }

    @Test
    public void checkIfRightPageFromResultIsShown() {
        for (int i = 0; i < 3; i++) {
            demo.searchResult("game", 1);
            Assert.assertTrue(IMDbDemoChecks.checkTitleAndRatings(demo.getDriver(), "Game of Thrones", "9.5"));
        }
        demo.searchResult("wal", 1);
        Assert.assertTrue(IMDbDemoChecks.checkTitleAndRatings(demo.getDriver(), "The Walking Dead", "8.3"));
        demo.searchResult("game", 2);
        Assert.assertFalse(IMDbDemoChecks.checkTitleAndRatings(demo.getDriver(), "Game of Thrones", "9.5"));
        demo.searchResult("wal", 3);
        Assert.assertFalse(IMDbDemoChecks.checkTitleAndRatings(demo.getDriver(), "The Walking Dead", "8.3"));
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
