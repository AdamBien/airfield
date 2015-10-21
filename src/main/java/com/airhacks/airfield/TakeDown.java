/*
 */
package com.airhacks.airfield;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author adam-bien.com
 */
public class TakeDown {

    private final String remotePath;
    private final String localPath;
    private Git git;
    CredentialsProvider cp = null;

    public TakeDown(String localPath, String remotePath, String userName, String password) {
        this.remotePath = remotePath;
        this.localPath = localPath;
        if (userName != null && password != null) {
            cp = new UsernamePasswordCredentialsProvider(userName, password);
        }
    }

    void initialDownload() {

        try {
            CloneCommand clone = Git.cloneRepository()
                    .setURI(remotePath)
                    .setDirectory(new File(localPath));
            if (cp != null) {
                clone = clone.setCredentialsProvider(cp);
            }
            this.git = clone.call();

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
        if (cp != null) {
            command.setCredentialsProvider(cp);
        }
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
