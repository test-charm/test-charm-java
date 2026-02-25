package org.testcharm.dal.extensions.basic.string;

import org.testcharm.dal.DAL;
import org.testcharm.dal.extensions.basic.string.util.CharSequenceChecker;
import org.testcharm.dal.runtime.Extension;

import static org.testcharm.dal.extensions.basic.binary.BinaryExtension.readAll;
import static org.testcharm.dal.extensions.basic.file.util.FileGroup.register;
import static org.testcharm.dal.extensions.basic.string.Methods.string;

@SuppressWarnings("unused")
public class StringExtension implements Extension {

    @Override
    public void extend(DAL dal) {
        dal.getRuntimeContextBuilder().registerStaticMethodExtension(Methods.class);
        dal.getRuntimeContextBuilder().checkerSetForMatching()
                .register(CharSequence.class, CharSequenceChecker::matches);
        dal.getRuntimeContextBuilder().checkerSetForEqualing()
                .register(CharSequence.class, CharSequence.class, CharSequenceChecker::equals);

        register("txt", inputStream -> string(readAll(inputStream)));
        register("TXT", inputStream -> string(readAll(inputStream)));
    }
}
