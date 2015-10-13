/*
 */
package com.airhacks.airfield;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;

/**
 *
 * @author adam-bien.com
 */
public class TakeDown {

    private final String remotePath;
    private final String localPath;
    private Git git;

    public TakeDown(String localPath, String remotePath) {
        this.remotePath = remotePath;
        this.localPath = localPath;
    }

    void initialDownload() {
        try {
            this.git = Git.cloneRepository()
                    .setURI(remotePath)
                    .setDirectory(new File(localPath))
                    .call();
            System.out.println("+App installed into: " + this.localPath);
        } catch (GitAPIException ex) {
            System.err.println("--Cannot download files: " + ex.getMessage());
        }

    }

    void update() {
        try {
            this.git.reset().setMode(ResetCommand.ResetType.HARD).call();

            System.out.println("+Changed files removed");
        } catch (GitAPIException ex) {
            throw new IllegalStateException("Cannot reset local repository", ex);
        }
        PullCommand command = this.git.pull();
        try {
            PullResult pullResult = command.call();
            if (pullResult.isSuccessful()) {
                System.out.println("+Files updated, ready to start!");
            } else {
                System.out.println("--Download was not successful " + pullResult.toString());
            }
        } catch (GitAPIException ex) {
            Logger.getLogger(TakeDown.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    boolean openLocal() {
        File localRepo = new File(this.localPath);
        try {
            this.git = Git.open(localRepo);
        } catch (IOException ex) {
            System.out.println("-" + ex.getMessage());
            return false;
        }
        System.out.println("+Application already installed at: " + this.localPath);
        return true;
    }

    public void installOrUpdate() {
        boolean alreadyInstalled = openLocal();
        if (alreadyInstalled) {
            update();
        } else {
            initialDownload();
        }
    }

}
