package org.testcharm.pf;

import org.testcharm.io.MemoryFile;
import org.testcharm.util.Sneaky;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiFunction;

public class FileManager {
    private final Path root;

    public FileManager(Path root) {
        this.root = root;
    }

    public FileManager() {
        this(Sneaky.get(() -> Files.createTempDirectory("fm-")));
    }

    public Path root() {
        return root;
    }

    public void clean() {
        Sneaky.run(() -> Files.walk(root).filter(Files::isRegularFile).forEach(f -> Sneaky.run(() -> Files.delete(f))));
    }

    public Path write(MemoryFile file) {
        Path path = root.resolve(file.getName());
        Sneaky.run(() -> Files.write(path, file.binary()));
        return path;
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
