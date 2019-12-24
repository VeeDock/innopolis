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
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader reader;

    /**
     * Поле сокета для личного общения с сервером. (отправка на сервер и получение личных сообщений)
     */
    private Socket clientSocket;

    /**
     * Поле сокета для приема широковещательных сообщений.
     */
    private MulticastSocket multiSocket;

    /**
     * Адрес для приема широковещательных сообщений.
     */
    private InetAddress multiAddress;

    /**
     * Порт для прослушивания широковещательных сообщений
     */
    private int multiPort = 8819;

    /**
     * Поле для объекта-слушателя сообщений от сервера.
     */
    private Thread serverListener;

    private Thread broadcastListener;

    /**
     * Поле объекта-отправителя сообщений, работающий в отдельном потоке.
     */
    private Thread messageSender;

    /**
     * Поле, хранящее имя текущего клиента по умолчанию
     */
    private String name = "Anonymous";

    /**
     * Останов клиента
     * закрытие всех активных подключений
     */
    private void stopClient() {
        System.out.println("You disconnected");
        try {
            clientSocket.close();
            multiSocket.close();
            out.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Запуск ожидания сообщений от сервера в параллельном потоке.
     */
    private void startListenServer() {
        if (serverListener != null && serverListener.isAlive()) return;
        serverListener = new Thread(() -> {
            String message;
            try {
                while (true) {
                    message = in.readLine();
                    if (message.equals("ok")) continue;
                    System.out.println(message);
                }
            } catch (IOException ignored) {
            }
            stopClient();
        });
        serverListener.start();
    }//...startListenServer

    /**
     * Слушаем широковещательные сообщения в параллельном потоке (общие сообщения от сервера)
     */
    private void listenBroadcast() {
        if (broadcastListener != null && broadcastListener.isAlive()) return;
        broadcastListener = new Thread(() -> {
            String message;
            try {
                while (true) {
                    byte[] buf = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    multiSocket.receive(packet);
                    byte[] bytes = packet.getData();
                    message = new String(bytes);
                    System.out.println(message);
                }
            } catch (IOException ignored) {
            }
        });
        broadcastListener.start();
    }//....listenBroadcast

    /**
     * Инициация отправителя сообщений с консоли на сервер.
     * при отправке /quit с консоли
     */
    private void initMessageSender() {
        if (messageSender != null && messageSender.isAlive()) return;
        try {
            out.write((char) 11 + name + "\n"); //представляемся серверу при подключении.
            out.flush();
        } catch (IOException ignored) {
        }
        messageSender = new Thread(() -> {
            while (true) {
                String message;
                try {
                    message = reader.readLine();
                    if (message.equals("/quit")) {
                        out.write("Я ухожу! Всем пока!\n");
                        break;
                    } else {
                        out.write(message + "\n");
                    }
                    out.flush();
                } catch (IOException ignored) {
                }
            }
            stopClient();
        });
        messageSender.start();
    }//...initMessageSender

    public ChatClient(int port, String name) {
        this.name = name;
        try {
            multiAddress = InetAddress.getByName("230.0.0.0");
            reader = new BufferedReader(new InputStreamReader(System.in));
            clientSocket = new Socket("localhost", port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            multiSocket = new MulticastSocket(multiPort);
            multiSocket.joinGroup(multiAddress);
            System.out.println("Chat started...");
            startListenServer();
            initMessageSender();
            listenBroadcast();
        } catch (IOException e) {
            System.err.println(e);
        }
    }//...ChatClient

}
