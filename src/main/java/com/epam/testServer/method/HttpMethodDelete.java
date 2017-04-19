package com.epam.testServer.method;

import com.epam.testServer.bean.Request;
import com.epam.testServer.bean.Response;
import com.epam.testServer.storage.Storage;

/**
 * Created by 777 on 13.04.2017.
 */
public class HttpMethodDelete extends HttpMethod {

    private static final String METHOD_NAME = "DELETE";
    private static final String PAGE_REG_EXP = "/\\d+";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    /**
     * Path to definite book is defined by book's ID. To delete book
     * server required only correct path to book
     */
    @Override
    public Response executeMethod(Request request) {
        String responseHeader = request.getHttpVersion();
        try {
            if (request.getPath().matches(PAGE_REG_EXP)) {
                responseHeader += deleteBook(request);
            } else {
                responseHeader += PAGE_NOT_FOUND_TEXT;
            }
        } catch (Exception e) {
            responseHeader += PAGE_NOT_FOUND_TEXT;
        }

        Response response = new Response();
        response.setHeader(responseHeader);
        return response;
    }

    private String deleteBook(Request request) {
        int bookId = Integer.parseInt(request.getPath()
                .substring(request.getPath().indexOf(ROOT_PATH_TEXT) + 1));
        Storage.getInstance().deleteBook(bookId);
        return CORRECT_ACTION_ANSWER_TEXT;
    }
}
