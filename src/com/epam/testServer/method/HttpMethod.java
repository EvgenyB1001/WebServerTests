package com.epam.testServer.method;

import com.epam.testServer.bean.Request;

/**
 * Created by Yauheni_Borbut on 4/13/2017.
 */
public abstract class HttpMethod {

    public abstract String getMethodName();
    public abstract String executeMethod(Request request);
}
