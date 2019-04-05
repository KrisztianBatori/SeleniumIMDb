import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EditWatchlistTest {

    private IMDbDemo demo;

    @Before
    public void openBrowser() {
        System.out.println("OPEN BROWSER");
        demo = new IMDbDemo();
        demo.login(System.getProperty("USER_EMAIL"), System.getProperty("USER_PASSWORD"));
    }

    @Test
    public void editWatchlistProperly() {
        demo.searchResult("matrix", 1);
        demo.addMovieToWatchlist("click button");
        demo.searchResult("world", 2);
        demo.addMovieToWatchlist("click text");
        demo.searchResult("throne", 3);
        demo.addMovieToWatchlist("click button");
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.refreshPage();
        demo.goToWatchlist();
        Assert.assertEquals(4, IMDbDemoChecks.getWatchlistSize(demo.getDriver()));
        demo.removeMovieFromWatchlistByNumber(3);
        demo.refreshPage();
        Assert.assertEquals(3, IMDbDemoChecks.getWatchlistSize(demo.getDriver()));
        demo.removeMovieFromWatchlistByName("The Walking Dead");
        demo.refreshPage();
        Assert.assertFalse(IMDbDemoChecks.checkIfHasMovieOnWatchlist(demo.getDriver(), "The Walking Dead"));
        demo.clearWatchlist();
        demo.refreshPage();
        Assert.assertEquals(0, IMDbDemoChecks.getWatchlistSize(demo.getDriver()));
        demo.searchResult("wal", 1);
        demo.addMovieToWatchlist("click text");
        demo.goToWatchlist();
        demo.removeMovieFromWatchlistByNumber(1);
        demo.refreshPage();
        Assert.assertEquals(0, IMDbDemoChecks.getWatchlistSize(demo.getDriver()));
    }

    @After
    public void closeBrowser() {
        System.out.println("CLOSE BROWSER");
        demo.closeBrowser();
    }
}
