package com.epam.testServer.method;

import com.epam.testServer.bean.Request;

/**
 * Created by Yauheni_Borbut on 4/13/2017.
 */
public class HttpMethodGet extends HttpMethod {

    private static final String METHOD_NAME = "GET";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    @Override
    public String executeMethod(Request request) {
        String s = "<html><body><h1>Hello from My Server</h1></body></html>";

        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: YarServer/2009-09-09\r\n" +
                "Content-Type: text/html" + "\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        return result;
    }
}
