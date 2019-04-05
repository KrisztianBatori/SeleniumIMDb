import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class,
        SearchTest.class,
        AddWatchlistTest.class,
        EditWatchlistTest.class,
        OrderWatchlistTest.class,
        RecentMovieTest.class
})

public class IMDbTests {
}
