package part1.task10.multisockets.clientSide;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;


/**
 * Подключение клиента к серверу.
 * Для отправки личных сообщений используется формат >>имя_другого_клиента>сообщение
 * для выхода из чата набрать сообщение /quit
 */
public class ChatClient {
    /**
     * Поле сокета для личного общения с сервером. (отправка на сервер и получение личных сообщений)
     */
    private Socket clientSocket;

    private boolean isAlive;

    /**
     * Порт для прослушивания широковещательных сообщений
     */
    private final int MULTICAST_PORT = 8819;

    private final String MULTICAST_ADDRESS = "230.0.0.0";

    private final char HELLO_INIT = 11;

    /**
     * Поле, хранящее имя текущего клиента по умолчанию
     */
    private String name = "Anonymous";

    /**
     * Объект для отправки сообщений с консоли. Выход из чата осуществляется командой /quit
     */
    private Runnable messageSender;

    /**
     * Объект для ожидания сообщений от сервера
     */
    private Runnable serverListener;


    {
        messageSender = () -> {
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                out.write(HELLO_INIT + name + "\n"); //представляемся серверу при подключении.
                out.flush();
                while (isAlive) {
                    String message;
                    message = reader.readLine();
                    if (message.equals("/quit")) {
                        out.write("Я ухожу! Всем пока!\n");
                        out.flush();
                        stopClient();
                        break;
                    } else {
                        out.write(message + "\n");
                        out.flush();
                    }
                }
            } catch (IOException e) {
                System.err.println("ERROR SENDING MESSAGE TO SERVER: " + e.getMessage());
            }
        };

        serverListener = () -> {
            String message;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                while (isAlive) {
                    message = in.readLine();
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        };

    }

    private void startListenBroadcast(){
        String message;
        try (MulticastSocket multiSocket = new MulticastSocket(MULTICAST_PORT)) {
            multiSocket.joinGroup(InetAddress.getByName(MULTICAST_ADDRESS));
            while (isAlive) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                multiSocket.receive(packet);
                message = new String(packet.getData());
                System.out.println(message);
            }
        } catch (IOException e) {
            System.err.println("ERROR RECEIVING BROADCAST MESSAGE: " + e.getMessage());
        }
    }


    /**
     * Останов клиента
     * закрытие всех активных подключений
     */
    private void stopClient() {
        isAlive = false;
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("ERROR STOP CLIENT: " + e.getMessage());
        }
        System.out.println("Disconnecting...");
    }



    private void startListeners() {
        new Thread(serverListener).start();
        new Thread(messageSender).start();
        new Thread(this::startListenBroadcast).start(); //для теста передал метод по ссылке.
    }

    public ChatClient(int port, String name) {
        this.name = name;
        isAlive = true;
        try {
            clientSocket = new Socket(InetAddress.getLocalHost(), port);
            System.out.println("Chat started...");
            startListeners();
        } catch (IOException e) {
            System.err.println("ERROR INIT CHAT CLIENT: " + e.getMessage());
        }
    }
}
