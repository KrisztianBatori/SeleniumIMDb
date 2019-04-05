import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class AddWatchlistTest {

    private IMDbDemo demo;

    @Before
    public void openBrowser() {
        System.out.println("OPEN BROWSER");
        demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
    }

    @Test
    public void checkIfMovieAddedToWatchlist() {

        demo.goToWatchlist();
        Assert.assertFalse(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), ""));
        Assert.assertFalse(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "The Walking Dead"));
        demo.searchResult("game", 1);
        demo.addMovieToWatchlist("click button");
        demo.goToWatchlist();
        Assert.assertTrue(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "Game of Thrones"));
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.goToWatchlist();
        Assert.assertTrue(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "The Walking Dead"));
        demo.logout();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
        demo.goToWatchlist();
        Assert.assertTrue(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "The Walking Dead"));
        Assert.assertTrue(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "Game of Thrones"));
        for (int i = 0; i < 2; i++) {
            demo.clearWatchlist();
            demo.refreshPage();
        }
        Assert.assertEquals(
                new ArrayList<>() {{}}, IMDbDemoChecks.getAllWatchlistMovie(demo.getDriver())
        );
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
