package org.testcharm.cucumber.restful;

import java.net.HttpURLConnection;

public interface ObjectBodyWriter {
    void write(String[] traitSpec, String bodyContent, HttpURLConnection connection);
}
