package com.epam.testServer.method;

import com.epam.testServer.bean.Book;
import com.epam.testServer.bean.Request;
import com.epam.testServer.bean.Response;
import com.epam.testServer.method.exception.MethodException;
import com.epam.testServer.storage.Storage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by 777 on 13.04.2017.
 */
public class HttpMethodPost extends HttpMethod {

    private static final String METHOD_NAME = "POST";
    private static final String ROOT_PATH_TEXT = "/";
    private static final String CONTENT_TYPE_ATTRIBUTE_NAME = "Content-type:";
    private static final String ID_ATTRIBUTE_NAME = "id";
    private static final String TITLE_ATTRIBUTE_NAME = "title";
    private static final String AUTHOR_ATTRIBUTE_NAME = "author";
    private static final String GENRE_ATTRIBUTE_NAME = "genre";
    private static final String CONTENT_TYPE_EXCEPTION_TEXT = "Content type isn't supported";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    /**
     * Method reads body of request and create new book. Only one book
     * can be created per request
     */
    @Override
    public Response executeMethod(Request request) {
        String responseHeader = request.getHttpVersion();
        Response response = new Response();
        try {
            if (request.getPath().equals(ROOT_PATH_TEXT)) {
                String body = request.getBody();
                responseHeader += addBook(request, body);
            } else {
                responseHeader += PAGE_NOT_FOUND_TEXT;
            }
        } catch (ParseException | ParserConfigurationException | SAXException | IOException e) {
            responseHeader += SERVER_ERROR_TEXT;
        } catch (MethodException methEx) {
            responseHeader += WRONG_ATTRIBUTES_TEXT;
        }

        response.setHeader(responseHeader);
        return response;
    }

    private String addBook(Request request, String body) throws ParseException,
            ParserConfigurationException, SAXException, IOException, MethodException {

        String response = null;
        switch (request.getParameters().get(CONTENT_TYPE_ATTRIBUTE_NAME)) {
            case APPLICATION_JSON_TEXT: {
                JSONParser parser = new JSONParser();
                JSONObject jsonObj = (JSONObject) parser.parse(body);
                Book book = new Book();
                book.setId((int) jsonObj.get(ID_ATTRIBUTE_NAME));
                book.setAuthor((String) jsonObj.get(AUTHOR_ATTRIBUTE_NAME));
                book.setTitle((String) jsonObj.get(TITLE_ATTRIBUTE_NAME));
                book.setGenre((String) jsonObj.get(GENRE_ATTRIBUTE_NAME));
                Storage.getInstance().setBook(book);
                response = CORRECT_ACTION_ANSWER_TEXT;
                break;
            }

            case APPLICATION_XML_TEXT: {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(body)));
                Element bookElement = document.getDocumentElement();
                Book book = new Book();
                book.setId(Integer.parseInt(bookElement.getAttribute(ID_ATTRIBUTE_NAME)));
                book.setTitle(getSingleElement(bookElement, TITLE_ATTRIBUTE_NAME).getTextContent().trim());
                book.setAuthor(getSingleElement(bookElement, AUTHOR_ATTRIBUTE_NAME).getTextContent().trim());
                book.setGenre(getSingleElement(bookElement, GENRE_ATTRIBUTE_NAME).getTextContent().trim());
                Storage.getInstance().setBook(book);
                response = CORRECT_ACTION_ANSWER_TEXT;
                break;
            }
        }

        if (response == null) {
            throw new MethodException(CONTENT_TYPE_EXCEPTION_TEXT);
        }
        return response;
    }

    private Element getSingleElement(Element root, String tagName) throws DOMException {
        NodeList list = root.getElementsByTagName(tagName);
        Element requiredElement = (Element) list.item(0);
        return requiredElement;
    }
}
