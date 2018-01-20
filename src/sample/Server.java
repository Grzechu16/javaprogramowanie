package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Created by Gregory on 2017-12-20.
 */
public class Server {


        public static void main(String args[]) {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8080);
                while (true) {
                    Socket socket = serverSocket.accept();
                    (new ServerThread(socket)).start();
                }
            } catch (Exception e) {
                System.err.println(e);
            }finally{
                if(serverSocket != null)
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }