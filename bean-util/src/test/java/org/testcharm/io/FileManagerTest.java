package org.testcharm.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.testcharm.dal.Assertions.expect;

class FileManagerTest {
    FileManager fileManager = new FileManager();

    @BeforeEach
    void clean() {
        fileManager.clean();
    }

    @Test
    void create_binary_file() {
        Path file = fileManager.write(new MemoryFile() {
            @Override
            public String getName() {
                return "hello";
            }

            @Override
            public byte[] binary() {
                return new byte[]{1};
            }
        });
        expect(fileManager.root()).should("hello.binary: [1y]");
        expect(file).should("binary: [1y]");
    }

    @Test
    void clean_file() {
        fileManager.write(new MemoryFile() {
            @Override
            public String getName() {
                return "hello";
            }

            @Override
            public byte[] binary() {
                return new byte[]{1};
            }
        });

        fileManager.clean();

        expect(fileManager.root()).should(": []");
    }
}