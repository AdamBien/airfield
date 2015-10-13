package airfield;

import com.airhacks.airfield.TakeDown;

/**
 *
 * @author airhacks.com
 */
public class App {

    public final static void main(String args[]) {
        if (args.length != 2) {
            usage();
            return;
        }
        String local = args[0];
        String remote = args[1];
        TakeDown installer = new TakeDown(local, remote);
        installer.installOrUpdate();
    }

    static void usage() {
        System.out.println("Use: java -jar airfield.App [PATH_TO_LOCAL_APP] [PATH_TO_REMOTE_GIT_REPO]");
    }
}
