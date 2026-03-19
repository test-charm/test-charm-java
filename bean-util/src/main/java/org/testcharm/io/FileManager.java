package org.testcharm.io;

import org.testcharm.util.Sneaky;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.testcharm.util.Sneaky.sneakyRun;

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
        Sneaky.run(() -> {
            try (Stream<Path> walk = Files.walk(root)) {
                walk.filter(Files::isRegularFile).forEach(sneakyRun(Files::delete));
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
