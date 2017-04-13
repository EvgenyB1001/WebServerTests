package com.epam.testServer.method;

import com.epam.testServer.bean.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yauheni_Borbut on 4/13/2017.
 */
public class HttpMethodController {

    private List<HttpMethod> methods = new ArrayList<>();

    private static  HttpMethodController instance;

    {
        methods.add(new HttpMethodGet());
    }

    private HttpMethodController() {}

    public static HttpMethodController getInstance() {
        if (instance == null) {
            instance = new HttpMethodController();
        }
        return instance;
    }

    public String executeMethod(Request request) {
        String response = null;
        for (HttpMethod method : methods) {
            if (method.getMethodName().equals(request.getMethod())) {
                response = method.executeMethod(request);
            }
        }

        return response;
    }
}