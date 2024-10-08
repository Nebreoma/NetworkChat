package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.time.LocalTime;
import java.lang.Thread;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiThreadedServer {
    private static final boolean isActive = true;
    private static String nickname = "Аноним";
    private static PrintStream printStream;
    private static Thread thread;
    private static final List<Thread> listThread = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {

//получение номера порта из setting.txt
        int port = readNumberPort("setting.txt");
//запуск сервера
        ServerSocket serverSocket = new ServerSocket(port);
        printStream = new PrintStream(new FileOutputStream("file.log", true));
        System.out.println("Сервер запущен");
        writeInFile("Сервер запущен");

//принимаем соединение от клиента
        while (isActive) {
            Socket clientSocket = serverSocket.accept();
// ждем подключения
            thread = new Thread(() -> {
                try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.println("Подключен новый пользователь");
                    writeInFile("Подключен новый пользователь");
//Поток чтения
                    while (!Thread.interrupted()) {
                        //System.out.println("Поток чтения");
                        String message = in.readLine();
//выход
                        if (message.equalsIgnoreCase("/exit")) {
                            System.out.println(nickname + " вышел");
                            writeInFile(nickname + " вышел");
                            sendMessageAll(out, nickname, "вышел");
                            listThread.remove(thread);
                            nickname = "Аноним";
                            break;
                        }
                        if("Аноним".equals(nickname)) {
                            nickname = message;
                            writeInFile(nickname + " говорит всем привет!");
                            sendMessageAll(out, nickname, "говорит всем привет!");
                            continue;
                        }
                        writeInFile(nickname + " " + message);
                        sendMessageAll(out, nickname, message);
                    }
                    Thread.currentThread().interrupt();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("Разрыв соединения");
                    writeInFile("Разрыв соединения");
                    nickname = "Аноним";
                    listThread.remove(thread);
                    Thread.currentThread().interrupt();
                }
            });
            listThread.add(thread);
            thread.start();
        }//while
    }//main


    //метод чтения из файла setting.txt
    public static synchronized int readNumberPort(String nameFile) {
        try {
            Scanner scanner = new Scanner(new File(nameFile));
            int port = Integer.parseInt(scanner.next());
            //System.out.println(port);
            scanner.close();
            return port;
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка чтения файла");
            return 8080;
        }
    }

    //метод определяющий время
    public static String currentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(myFormatter);
    }

    //метод отправляющий сообщения всем в чате
    public static synchronized void sendMessageAll(PrintWriter out, String name, String text) {
        for (Thread t : listThread) {
            out.println(currentTime() + " " + name + " " + text);
            //System.out.println("Метод отправки всем");
        }
    }

    //метод записывающий в file.log
    public static void writeInFile(String text) {
        printStream.println(currentTime() + " " + text);
        //System.out.println("Запись в файл произведена");
    }

}//class