package com.epam.testServer.bean;

import java.io.Serializable;

/**
 * Created by Yauheni_Borbut on 4/19/2017.
 */
public class Response implements Serializable{
    public static final long serialVersionUID = 1L;

    private String header;

    private String body;

    public Response() {}

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (header != null ? !header.equals(response.header) : response.header != null) return false;
        return body != null ? body.equals(response.body) : response.body == null;
    }

    @Override
    public int hashCode() {
        int result = header != null ? header.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
