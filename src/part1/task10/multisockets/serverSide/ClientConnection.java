package part1.task10.multisockets.serverSide;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Описывает активное подключение клиента к серверу. Создается чат-сервером при подключении клиента.
 */
public class ClientConnection extends Thread {

    /**
     * Поле сокета для общения с клинетом
     */
    private Socket socket;

    private ChatServer server;

    private BufferedWriter out;

    private String name;

    private final String PRIVATE_SEPARATOR = ">>";

    private final char HELLO_INIT = 11;

    /**
     * Паттерн для сообщения, определяющий, что сообщение отправляется лично.
     */
    private final Pattern privatePattern = Pattern.compile("^>>\\w+>");


    /**
     * Конструктор активного подключения к серверу
     *
     * @param connection сокет текущего подключения клиента
     * @param server     объект чат-сервера
     */
    public ClientConnection(Socket connection, ChatServer server) throws IOException {
        this.server = server;
        socket = connection;
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    /**
     * Получение ClientConnection по имени клиента
     * Используем стрим для получения нужного ClientConnection
     *
     * @param name имя клиента
     * @return возвращает ClientConnection, соответствующее этому клиенту.
     */
    public ClientConnection getConnection(String name) {
        return server.getConnections().stream().filter(connection -> connection.name.equals(name)).findFirst().orElse(null);
    }

    /**
     * Вывод сообщения в консоль и отправка через broadcast
     *
     * @param message текст сообщения
     */
    private void broadcastMessage(String message) {
        if (name == null) {
            sendMessage("Идентификация не получена. Сообщение не будет доставлено.", this);
            return;
        }
        System.out.println(String.format("Message from <%s>: %s", name, message));
        server.sendBroadcast(message, this.name);
    }//...printMessage


    /**
     * отправка сообщения клиенту по сокету.
     *
     * @param message текст сообщения.
     */
    private void sendMessage(String message, ClientConnection client) {
        try {
            client.out.write(message + "\n");
            client.out.flush();
        } catch (IOException e) {
            System.out.println("ERROR SENDING PRIVATE MESSAGE: " + e.getMessage());
        }
    }


    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while (true) {
                message = in.readLine();
                if (message == null) {
                    socket.close();
                    server.getConnections().remove(this);
                    break;
                }
                Matcher privateMessageSender = privatePattern.matcher(message);
                if (message.getBytes()[0] == HELLO_INIT) { //сообщение-приветствие если начинается с char == 11. клиент отправляет свое имя
                    name = message.substring(1);
                    broadcastMessage("Connected!");
                } else if (privateMessageSender.find()) { //когда отправляется личное сообщение.
                    String receiverName = message.substring(privateMessageSender.start(), privateMessageSender.end()).replaceAll(">", "");
                    message = message.substring(privateMessageSender.end());
                    ClientConnection receiver = getConnection(receiverName);
                    if (receiver != null) {
                        System.out.println(String.format("Message from <%s> to <%s>: %s", name, receiverName, message));
                        sendMessage(name + PRIVATE_SEPARATOR + message, receiver);
                    } else {
                        System.out.println(String.format("Невозможно отправить сообщение! Клиент с именем <%s> не подключен к серверу. ", receiverName));
                    }
                } else {
                    broadcastMessage(message);
                }

            }
        } catch (IOException ignored) {
        }
    }//...run
}
