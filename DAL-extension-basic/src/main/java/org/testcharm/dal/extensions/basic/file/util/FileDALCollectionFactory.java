package org.testcharm.dal.extensions.basic.file.util;

import org.testcharm.dal.runtime.CollectionDALCollection;
import org.testcharm.dal.runtime.DALCollection;
import org.testcharm.dal.runtime.DALCollectionFactory;

import java.io.File;

public class FileDALCollectionFactory implements DALCollectionFactory<File, File> {
    @Override
    public boolean isList(File file) {
        return file.isDirectory();
    }

    @Override
    public DALCollection<File> create(File file) {
        return new CollectionDALCollection<>(Util.listFile(file));
    }
}
