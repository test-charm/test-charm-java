package org.testcharm.dal.extensions.basic.sftp;

import org.testcharm.dal.DAL;
import org.testcharm.dal.extensions.basic.sftp.util.SFtpFile;
import org.testcharm.dal.extensions.basic.sftp.util.SFtpFileJavaClassPropertyAccessor;
import org.testcharm.dal.extensions.basic.sftp.util.Util;
import org.testcharm.dal.runtime.CollectionDALCollection;
import org.testcharm.dal.runtime.Extension;
import org.testcharm.dal.runtime.RuntimeContextBuilder;

@SuppressWarnings("unused")
public class SFTPExtension implements Extension {

    @Override
    public void extend(DAL dal) {
        RuntimeContextBuilder builder = dal.getRuntimeContextBuilder();
        builder.registerImplicitData(SFtpFile.class, SFtpFile::download)
                .registerDALCollectionFactory(SFtpFile.class, sFtpFile -> new CollectionDALCollection<>(sFtpFile.ls()))
                .registerPropertyAccessor(SFtpFile.class, new SFtpFileJavaClassPropertyAccessor())
                .registerDumper(SFtpFile.class, data -> data.value().isDir() ? Util.DIR_DUMPER : Util.FILE_DUMPER);
    }
}
