package com.epam.testServer;

import com.epam.testServer.bean.Request;
import com.epam.testServer.bean.Response;
import com.epam.testServer.method.HttpMethodController;
import com.epam.testServer.utils.RequestParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

    public static void main(String args[]) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8081, 10, InetAddress.getByName("127.0.0.1"));
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new SocketPerformer(clientSocket)).start();
        }
    }

    private static class SocketPerformer implements Runnable {

        private static final Logger logger = LogManager.getRootLogger();
        private Socket clientSocket;
        private InputStream inputStream;
        private OutputStream outputStream;

        private SocketPerformer(Socket connection) throws IOException {
            this.clientSocket = connection;
            this.inputStream = connection.getInputStream();
            this.outputStream = connection.getOutputStream();
        }

        public void run() {
            try {
                Request request = new RequestParser().getRequest(inputStream);
                Response response = HttpMethodController.getInstance().executeMethod(request);
                writeResponse(response);
            } catch (IOException e) {
                logger.error(e);
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }
        }

        private void writeResponse(Response response) throws IOException {
            outputStream.write(response.getHeader().getBytes());
            outputStream.write(response.getBody().getBytes());
        }
    }
}
