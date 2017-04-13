package com.epam.testServer;

import java.net.Socket;

/**
 * Created by Yauheni_Borbut on 4/12/2017.
 */
public class HTTPClient extends Thread {
    public static void main(String args[])
    {
        try
        {
            String targs[] = {"GET"};
            Socket s = new Socket("127.0.0.1", 5000);

            String request = targs[0] + " / HTTP/1.1\n" +
                    "Accept: text/html, application/xhtml+xml, image/jxr, */*\n" +
                    "Accept-Language: en-US\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Host: 127.0.0.1:5000\n" +
                    "Connection: Keep-Alive";
            s.getOutputStream().write(request.getBytes());

            System.out.println();
        }
        catch(Exception e)
        {System.out.println("init error: "+e);}
    }

}
