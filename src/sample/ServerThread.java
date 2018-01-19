package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Gregory on 2018-01-18.
 */
public class ServerThread extends Thread {
    Socket mySocket;

    public ServerThread(Socket socket) {
        super(); // konstruktor klasy Thread
        mySocket = socket;
    }

    public void run() // program w�tku
    {
        try {
			/*
			 * int in; // odbieramy i drukujemy ... while ((in =
			 * mySocket.getInputStream().read()) >= 0) System.out.print((char)
			 * in);
			 */
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            String str;
            while (!(str = in.readLine()).equals("exit")) {
                System.out.println(mySocket.getInetAddress() + " : " + str);
            }
            mySocket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
