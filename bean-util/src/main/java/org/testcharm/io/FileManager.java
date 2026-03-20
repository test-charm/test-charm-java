package org.testcharm.io;

import org.testcharm.util.Sneaky;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.testcharm.util.Sneaky.sneakyRun;

public class FileManager {
    private final Path root;
    private final List<FileManager> subs = new ArrayList<>();

    public FileManager(Path root) {
        this.root = root;
        if (!Files.exists(root))
            Sneaky.run(() -> Files.createDirectory(root));
    }

    public FileManager() {
        this(Sneaky.get(() -> Files.createTempDirectory("fm-")));
    }

    public Path root() {
        return root;
    }

    public void cleanFiles() {
        subs.forEach(FileManager::cleanFiles);
        Sneaky.run(() -> {
            try (Stream<Path> walk = Files.walk(root)) {
                walk.filter(Files::isRegularFile).forEach(sneakyRun(Files::delete));
            }
        });
    }

    public void clean() {
        subs.forEach(FileManager::clean);
        Sneaky.run(() -> {
            try (Stream<Path> walk = Files.walk(root)) {
                walk.skip(1).filter(path -> Files.isRegularFile(path) || Files.isDirectory(path)).forEach(sneakyRun(Files::delete));
            }
        });
    }

    public Path write(MemoryFile file) {
        return write(file.getName(), file.binary());
    }

    public Path write(String name, byte[] binary) {
        Path path = root.resolve(name);
        Sneaky.run(() -> Files.write(path, binary));
        return path;
    }

    public FileManager mkdir(String sub) {
        FileManager fileManager = new FileManager(root.resolve(sub));
        subs.add(fileManager);
        return fileManager;
    }

    public static class Shared extends FileManager {
        private final Path remote;

        public Shared(Path root, Path remote) {
            super(root);
            this.remote = remote;
        }

        public Shared(Path root) {
            this(root, root);
        }

        public Shared() {
            super();
            remote = root();
        }

        public <A> Path remoteOf(BiFunction<FileManager, A, Path> pathFunction, A arg) {
            return remote.resolve(root().relativize(pathFunction.apply(this, arg)));

        }
    }
}
