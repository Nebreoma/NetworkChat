package ru.netology;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}


//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server {
//    public static volatile int PORT = 8080;
//
//    public static void main(String[] args){
//        //Запускаем сервер на определенном порту и принимаем соединение
//        // порт можете выбрать любой в доступном диапазоне 0-65536.
//        // Но чтобы не нарваться на уже занятый - рекомендуем использовать около 8080
//
//        Scanner scanner = new Scanner(new File("setting.txt"));
//        PORT = scanner.hasNextInt();
//
//
//        ServerSocket serverSocket = new ServerSocket(PORT)
//
//        while(true){
//            try {
//                Socket clientSocket = serverSocket.accept();
//                // ждем подключения
//
//                //Executor.newCachedThreadPool
//
//                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                //Читаем строку и выводим её на экран вместе с номером порта клиента,
//                // с которого пришло соединение:
//                System.out.println("New connection accepted");
//
//                final String name = in.readLine();
//
//                out.println(String.format("Hi %s, your port is %d", name, clientSocket.getPort()));
//            } catch (IOException e) {
//                throw new RuntimeException (e);
//            }
//        }
//    }
//
//}
//
//
//--------------------------------------------
//
//public class Client {
//    public static void main(String[] args) throws IOException {
////public static final int PORT = 127.0.0.1;
//        try(Socket clientSocket = new Socket("localhost", Server.PORT);
//            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
//        ){
//            writer.println("Eva");
//            System.out.println(reader.readLine());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
//
//--------------------------------------------