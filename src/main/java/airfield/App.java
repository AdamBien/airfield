package airfield;

import com.airhacks.airfield.TakeDown;

/**
 *
 * @author airhacks.com
 */
public class App {

    public final static void main(String args[]) {
        if (args.length < 2) {
            usage();
            return;
        }
        String local = args[0];
        String remote = args[1];
        String user = null;
        String password = null;
        if (args.length == 4) {
            user = args[2];
            password = args[3];
        }
                
        TakeDown installer;
            installer = new TakeDown(local, remote, user, password);            
        installer.installOrUpdate();
    }

    static void usage() {
        System.out.println("Use: java -jar airfield.App PATH_TO_LOCAL_APP PATH_TO_REMOTE_GIT_REPO [userName] [ password]");
    }
}
