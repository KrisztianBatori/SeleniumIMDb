import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class RecentMovieTest {

    private IMDbDemo demo;

    @Before
    public void openBrowser() {
        System.out.println("OPEN BROWSER");
        demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
    }

    @Test
    public void checkIfRecentMoviesIsTrackedProperly() {
        demo.goToUserRatings();
        Assert.assertEquals(
                new ArrayList<>() {{}}, IMDbDemoChecks.getAllRecentMovie(demo.getDriver())
        );
        demo.searchResult("wal", 1);
        demo.searchResult("hunger", 1);
        demo.goToWatchlist();
        Assert.assertEquals(
                2, IMDbDemoChecks.getAllRecentMovie(demo.getDriver()).size()
        );
        demo.clearHistory();
        demo.refreshPage();
        Assert.assertEquals(
                0, IMDbDemoChecks.getAllRecentMovie(demo.getDriver()).size()
        );
        demo.searchResult("game", 3);
        demo.searchResult("matrix", 2);
        demo.logout();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.goToUserRatings();
        Assert.assertEquals(
                1, IMDbDemoChecks.getAllRecentMovie(demo.getDriver()).size()
        );
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
