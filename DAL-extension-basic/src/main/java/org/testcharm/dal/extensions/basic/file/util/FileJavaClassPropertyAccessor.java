package org.testcharm.dal.extensions.basic.file.util;

import org.testcharm.dal.runtime.JavaClassPropertyAccessor;
import org.testcharm.util.BeanClass;

import java.io.File;
import java.util.Set;

public class FileJavaClassPropertyAccessor extends JavaClassPropertyAccessor<File> {

    public FileJavaClassPropertyAccessor() {
        super(BeanClass.create(File.class));
    }

    @Override
    public Set<?> getPropertyNames(File file) {
        return file.isDirectory() ? Util.listFileNames(file) : super.getPropertyNames(file);
    }

    @Override
    public Object getValue(File file, Object name) {
        return file.isDirectory() ? Util.getSubFile(file, (String) name) : super.getValue(file, name);
    }
}
