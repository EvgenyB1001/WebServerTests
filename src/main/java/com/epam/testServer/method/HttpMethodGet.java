package com.epam.testServer.method;

import com.epam.testServer.bean.Book;
import com.epam.testServer.bean.Request;
import com.epam.testServer.storage.Storage;
import com.epam.testServer.storage.exception.StorageException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Created by Yauheni_Borbut on 4/13/2017.
 */
public class HttpMethodGet extends HttpMethod {

    private static final String METHOD_NAME = "GET";
    private static final String CONTENT_TYPE_ATTRIBUTE_NAME = "Content-type:";
    private static final String PAGE_REG_EXP = "/\\d+";
    private static final String ID_ATTRIBUTE_NAME = "id";
    private static final String TITLE_ATTRIBUTE_NAME = "title";
    private static final String AUTHOR_ATTRIBUTE_NAME = "author";
    private static final String GENRE_ATTRIBUTE_NAME = "genre";
    private static final String ROOT_ELEMENT_NAME = "books";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    @Override
    public String executeMethod(Request request) {
        String response = request.getHttpVersion();
        try {
            if (request.getPath().equals(ROOT_PATH_TEXT)) {
                String body = createRootResponseBody(request);
                response = createResponse(request, response, body);
            } else if (request.getPath().matches(PAGE_REG_EXP)) {
                String body = createBookResponseBody(request);
                response = createResponse(request, response, body);
            } else {
                response += PAGE_NOT_FOUND_TEXT;
            }
        } catch (StorageException stEx) {
            response += PAGE_NOT_FOUND_TEXT;
        } catch (Exception e) {
            response += SERVER_ERROR_TEXT;
        }
        return response;
    }

    private String createResponse(Request request, String response, String body) {
        response += CORRECT_ACTION_ANSWER_TEXT + "\r\n" +
                SERVER_TEXT + "\r\n" +
                CONTENT_TYPE_TEXT + request.getParameters().get(CONTENT_TYPE_ATTRIBUTE_NAME) + "\r\n" +
                CONTENT_LENGTH_TEXT + body.getBytes().length + "\r\n" +
                CONNECTION_KEEP_ALIVE_TEXT + "\r\n\r\n" + body;
        return response;
    }

    private String createRootResponseBody(Request request) throws JAXBException {
        String body = null;
        String contentType = request.getParameters().get(CONTENT_TYPE_ATTRIBUTE_NAME);
        switch (contentType) {
            case APPLICATION_JSON_TEXT: {
                JSONObject bodyJson = new JSONObject();
                JSONArray books = new JSONArray();

                for(Book bookObj : Storage.getInstance().getBooks()) {
                    JSONObject book = new JSONObject();
                    book.put(ID_ATTRIBUTE_NAME, bookObj.getId());
                    book.put(TITLE_ATTRIBUTE_NAME, bookObj.getTitle());
                    book.put(AUTHOR_ATTRIBUTE_NAME,bookObj.getAuthor());
                    book.put(GENRE_ATTRIBUTE_NAME, bookObj.getGenre());
                    books.add(book);
                }
                bodyJson.put(ROOT_ELEMENT_NAME, books);
                body = bodyJson.toString();
                break;
            }
            case APPLICATION_XML_TEXT: {
                JAXBContext jaxbContext = JAXBContext.newInstance(Storage.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                StringWriter writer = new StringWriter();
                jaxbMarshaller.marshal(Storage.getInstance(), writer);
                body = writer.toString();
                break;
            }
        }
        return body;
    }

    private String createBookResponseBody(Request request) throws StorageException, JAXBException {
        String body = null;
        String contentType = request.getParameters().get(CONTENT_TYPE_ATTRIBUTE_NAME);
        int bookId = Integer.parseInt(request.getPath()
                .substring(request.getPath().indexOf(ROOT_PATH_TEXT) + 1));
        Book book = Storage.getInstance().getBook(bookId);
        switch (contentType) {
            case APPLICATION_JSON_TEXT: {
                JSONObject bodyJson = new JSONObject();
                bodyJson.put(ID_ATTRIBUTE_NAME, book.getId());
                bodyJson.put(TITLE_ATTRIBUTE_NAME, book.getTitle());
                bodyJson.put(AUTHOR_ATTRIBUTE_NAME,book.getAuthor());
                bodyJson.put(GENRE_ATTRIBUTE_NAME, book.getGenre());
                body = bodyJson.toString();
                break;
            }

            case APPLICATION_XML_TEXT: {
                JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                StringWriter sw = new StringWriter();
                jaxbMarshaller.marshal(book, sw);
                body = sw.toString();
                break;
            }
        }
        return body;
    }
}
