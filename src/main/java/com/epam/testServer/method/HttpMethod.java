package com.epam.testServer.method;

import com.epam.testServer.bean.Request;

/**
 * Created by Yauheni_Borbut on 4/13/2017.
 */
public abstract class HttpMethod {

    protected static final String PAGE_NOT_FOUND_TEXT = " 404 Page not found";
    protected static final String CORRECT_ACTION_ANSWER_TEXT = " 200 OK";
    protected static final String SERVER_ERROR_TEXT = " 500 Something go wrong";
    protected static final String APPLICATION_XML_TEXT = "application/xml";
    protected static final String APPLICATION_JSON_TEXT = "application/json";
    protected static final String ROOT_PATH_TEXT = "/";
    protected static final String SERVER_TEXT = "Server: MyServer/2017-04-12";
    protected static final String CONTENT_TYPE_TEXT = "Content-type: ";
    protected static final String CONTENT_LENGTH_TEXT = "Content-Length: ";
    protected static final String CONNECTION_KEEP_ALIVE_TEXT = "Connection: keep-alive";


    public abstract String getMethodName();
    public abstract String executeMethod(Request request);
}
