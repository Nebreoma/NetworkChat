package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static ru.netology.MultiThreadedServer.readNumberPort;

public class Client {
    private static String userName = "Аноним";
    private static int port = 8081;
    private static Thread readerThread;
    private static volatile String message = "";

    public static void main(String[] args) {

        port = readNumberPort("setting.txt");

        try(Socket clientSocket = new Socket("localhost", port);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); Scanner sc = new Scanner(System.in)
        ){
//поток чтения
            readerThread = new Thread (() -> {
                try {
                    while (!readerThread.isInterrupted()) {
                        //System.out.println("Поток чтения");
                        System.out.println(in.readLine());
                    }
                } catch (IOException e) {
                    readerThread.isInterrupted();
                }
            });
            readerThread.start();

//поток ввода
            System.out.println("Добро пожаловать в чат!");
            System.out.println("Выберите себе ник.\n"
                    + "После вводите сообщения для отправки пользователям или /exit для выхода из чата:");
            while (!"/exit".equals(message)) {
                //System.out.println("Поток ввода");
                message = sc.nextLine();
                if(userName.equals("Аноним"))
                    userName = message;
                out.println(message);
                out.flush();
            }
            userName = "Аноним";
            out.println("/exit");
            out.flush();
            readerThread.isInterrupted();
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            readerThread.isInterrupted();
        }
    }
}