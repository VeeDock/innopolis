package part1.task10.multisockets.serverSide;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;

public class ChatServer {
    /**
     * Поле для хранения активных подключений к серверу.
     * LinkedList удобней использовать, потому что будет удобней удалять закрытые подключения из списка.
     */
    private LinkedList<ClientConnection> connections = new LinkedList<>();
    private final InetAddress BROADCAST_IP = InetAddress.getByName("255.255.255.255");
    private final int BROADCAST_PORT = 8819;
    private final String SEPARATOR = ">";

    /**
     * Конструктор чат-сервера
     *
     * @param port порт, на котором сервер ожидает подключений клиентов через Socket.
     */
    public ChatServer(int port) throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Chat server started on port " + port);
            while (!server.isClosed()) { //сервер находится в постоянном ожидании новых подключений. каждое новое подключение работает в параллельном потоке.
                try {
                    Socket clientSocket = server.accept();
                    connections.add(new ClientConnection(clientSocket, this));
                } catch (IOException e) {
                    System.out.println("ERROR CONNECT CLIENT: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("START SERVER ERROR: " + e.getMessage());
        } finally {
            System.out.println("Server closed!");
        }
    }


    /**
     * Отправка широковещательного сообщения
     *
     * @param message текст сообщения
     * @param sender  имя отправителя
     */
    public void sendBroadcast(String message, String sender) {
        byte[] data = (sender + SEPARATOR + message).getBytes();
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.send(new DatagramPacket(data, data.length, BROADCAST_IP, BROADCAST_PORT));
        } catch (IOException e) {
            System.err.println("BROADCAST SEND ERROR: " + e.getMessage());
        }
    }//...sendBroadcast

    public LinkedList<ClientConnection> getConnections() {
        return connections;
    }
}
