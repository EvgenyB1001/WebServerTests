package com.epam.testServer.utils;

import com.epam.testServer.bean.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Yauheni_Borbut on 4/12/2017.
 */
public class RequestParser {

    private static final String LINE_SEPARATOR = "---";
    private static final String PARAMETER_SEPARATOR = " ";

    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int HTTP_VERSION_INDEX = 2;

    public Request getRequest(InputStream input) throws IOException {
        Request request = new Request();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String header = readHTTPHeader(reader);
        String headerLines[] = header.split(LINE_SEPARATOR);
        String[] firstLineParams = headerLines[0].split(PARAMETER_SEPARATOR);
        request.setMethod(firstLineParams[METHOD_INDEX]);
        request.setPath(firstLineParams[PATH_INDEX]);
        request.setHttpVersion(firstLineParams[HTTP_VERSION_INDEX]);
        for(int i = 1; i < headerLines.length; i++) {
            String param = headerLines[i].substring(0, headerLines[i].indexOf(PARAMETER_SEPARATOR));
            String values = headerLines[i].substring(headerLines[i].indexOf(PARAMETER_SEPARATOR) + 1);
            request.addParameters(param, values);
        }
        request.setBody(readHTTPBody(reader));
        return request;
    }

    private String readHTTPHeader(BufferedReader reader) throws IOException {
        StringBuilder header = new StringBuilder();
        String s = reader.readLine();
        while(s != null && s.trim().length() != 0) {
            header.append(s).append(LINE_SEPARATOR);
            s = reader.readLine();
        }

        return header.toString();
    }

    private String readHTTPBody(BufferedReader reader) throws IOException {
        StringBuilder body = new StringBuilder();
        while(reader.ready()) {
            body.append(reader.readLine());
        }

        return body.toString();
    }
}
