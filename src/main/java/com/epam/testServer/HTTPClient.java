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
            Socket s = new Socket("127.0.0.1", 8081);

            String request = targs[0] + " / HTTP/1.1\n" +
                    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                    "Accept-Language: en-US,en;q=0.5\n" +
                    "Content-type: application/json\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Host: 127.0.0.1:8081\n" +
                    "Connection: Keep-Alive\n";
            s.getOutputStream().write(request.getBytes());

            System.out.println();
        }
        catch(Exception e)
        {System.out.println("init error: "+e);}
    }

}
