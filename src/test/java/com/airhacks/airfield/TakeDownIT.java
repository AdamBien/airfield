package com.airhacks.airfield;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author adam-bien.com
 */
public class TakeDownIT {

    private TakeDown cut;
    static final String LOCAL_REPO = "target/repo";

    @Before
    public void init() throws IOException {
        Path directory = Paths.get(LOCAL_REPO);
        if (Files.exists(directory)) {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

            });
        }
        this.cut = new TakeDown(LOCAL_REPO, "git://localhost:4242/");
    }

    @Test
    public void initialDownload() {
        Path file = Paths.get(LOCAL_REPO + "/app.txt");
        assertFalse(Files.exists(file));
        this.cut.initialDownload();
        assertTrue(Files.exists(file));
    }

    @Test
    public void openLocal() {
        boolean repoExists = this.cut.openLocal();
        assertFalse(repoExists);
        this.cut.initialDownload();
        repoExists = this.cut.openLocal();
        assertTrue(repoExists);
    }

}
