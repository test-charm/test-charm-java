package org.testcharm.cucumber.restful.spec;

import org.testcharm.jfactory.Spec;
import org.testcharm.jfactory.Trait;
import lombok.Getter;
import lombok.Setter;

public class LoginRequests {

    public static class LoginRequest extends Spec<LoginRequestDto> {

        @Trait
        public void WrongPassword() {
            property("password").value("wrongPassword");
        }

    }

    @Getter
    @Setter
    public static class LoginRequestDto {
        private String username, password;
        private Captcha captcha;

        @Getter
        @Setter
        public static class Captcha {
            private String code;
        }
    }
}
