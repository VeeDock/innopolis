package part1.task10.multisockets.serverSide;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;

public class ChatServer {
    private ServerSocket server;
    private Socket clientSocket;

    /**
     * Сокет для отправки широковещательных сообщений
     */
    private DatagramSocket datagramSocket;

    /**
     * Поле для хранения активных подключений к серверу.
     * LinkedList удобней использовать, потому что будет удобней удалять закрытые подключения из списка.
     */
    public LinkedList<ClientConnection> connections = new LinkedList<>();

    /**
     * Поле, определяющие широковещательный адрес в сети.
     */
    private InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");

    /**
     * Поле, определяющее порт для broadcast сообщений
     */
    private int broadcastPort = 8819;

    /**
     * Отправка широковещательного сообщения
     *
     * @param message текст сообщения
     * @param sender  имя отправителя
     */
    public void sendBroadcast(String message, String sender) {
        byte[] data = (sender + "> " + message).getBytes();
        try {
            datagramSocket.send(new DatagramPacket(data, data.length, broadcastAddress, broadcastPort));
        } catch (IOException ignored) {
        }
    }//...sendBroadcast


    /**
     * Конструктор чат-сервера
     *
     * @param port порт, на котором сервер ожидает подключений клиентов через Socket.
     * @throws IOException
     */
    public ChatServer(int port) throws IOException {
        try {
            server = new ServerSocket(port);
            datagramSocket = new DatagramSocket();
            System.out.println("Chat server started on port " + port);
            while (true) { //сервер находится в постоянном ожидании новых подключений. каждое новое подключение работает в параллельном потоке.
                clientSocket = server.accept();
                try {
                    connections.add(new ClientConnection(clientSocket, this));
                } catch (IOException e) {
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.close();
            System.out.println("Server closed!");
        }
    }
}
