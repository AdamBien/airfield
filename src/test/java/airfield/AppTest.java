package airfield;

import java.io.PrintStream;
import org.junit.Test;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author airhacks.com
 */
public class AppTest {

    @Test
    public void mainUsage() {
        PrintStream out = mock(PrintStream.class);
        System.setOut(out);
        App.main(new String[]{});
        verify(out).println(startsWith("Use:"));
    }

}
