package com.example.examplemod.utils;

import com.example.examplemod.TimeStampr;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    private final Path userDir;

    public FileUtil(TimeStampr timeStampr) {
        this.userDir = this.getRawPath("mods/autotip/" + timeStampr.getGameProfile().getId());
    }

    public File getFile(String path) {
        return this.getPath(path).toFile();
    }

    public Path getPath(String path) {
        return this.getPath(this.userDir, path);
    }

    private File getFile(Path directory, String path) {
        return this.getPath(directory, path).toFile();
    }

    private Path getPath(Path directory, String path) {
        return directory.resolve(this.separator(path));
    }

    private Path getRawPath(String path) {
        return Paths.get(this.separator(path));
    }

    private String separator(String s) {
        return s.replaceAll("///", File.separator);
    }
}
