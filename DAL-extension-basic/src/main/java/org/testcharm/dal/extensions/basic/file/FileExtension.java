package org.testcharm.dal.extensions.basic.file;

import org.testcharm.dal.DAL;
import org.testcharm.dal.extensions.basic.file.util.FileDALCollectionFactory;
import org.testcharm.dal.extensions.basic.file.util.FileJavaClassPropertyAccessor;
import org.testcharm.dal.extensions.basic.file.util.ToString;
import org.testcharm.dal.extensions.basic.file.util.Util;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.util.Sneaky;

import java.io.File;
import java.io.FileInputStream;

@SuppressWarnings("unused")
public class FileExtension implements Extension {

    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder()
                .registerStaticMethodExtension(ToString.class)
                .registerStaticMethodExtension(Methods.class)
                .registerImplicitData(File.class, file -> Sneaky.get(() -> new FileInputStream(file)))
                .registerDALCollectionFactory(File.class, new FileDALCollectionFactory())
                .registerPropertyAccessor(File.class, new FileJavaClassPropertyAccessor())
                .registerDumper(File.class, data -> data.value().isDirectory()
                        ? Util.FILE_DIR_DUMPER : Util.FILE_FILE_DUMPER);
        dal.getRuntimeContextBuilder().getConverter()
                .addTypeConverter(File.class, String.class, File::getName);
    }

}
