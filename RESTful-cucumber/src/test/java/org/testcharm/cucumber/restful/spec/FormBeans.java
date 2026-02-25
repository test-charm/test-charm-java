package org.testcharm.cucumber.restful.spec;

import org.testcharm.cucumber.restful.FormFileLinkName;
import org.testcharm.jfactory.Spec;
import lombok.Data;

public class FormBeans {

    @Data
    public static class FormBean {
        private String str;

        private FileData oneFile;
    }

    @Data
    public static class FileData {

        @FormFileLinkName
        private String name;
    }

    public static class DefaultFormBean extends Spec<FormBean> {
        @Override
        public void main() {
            FileData fileData = new FileData();
            fileData.name = "aFile";
            property("oneFile").value(fileData);
        }
    }
}
