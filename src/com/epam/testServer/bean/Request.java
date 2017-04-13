package com.epam.testServer.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Yauheni_Borbut on 4/12/2017.
 */
public class Request implements Serializable {
    public static final long serialVersionUID = 1L;

    private String method;
    private String path;
    private HashMap<String, String> parameters;
    private String body;

    public Request() {}

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getBody() {
        return body;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addParameters(String paramName, String values) {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        parameters.put(paramName, values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (method != null ? !method.equals(request.method) : request.method != null) return false;
        if (path != null ? !path.equals(request.path) : request.path != null) return false;
        if (parameters != null ? !parameters.equals(request.parameters) : request.parameters != null) return false;
        return body != null ? body.equals(request.body) : request.body == null;
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", parameters=" + parameters +
                ", body='" + body + '\'' +
                '}';
    }
}
