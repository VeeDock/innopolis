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
     * Получение ClientConnection по имени клиента
     * @param name имя клиента
     * @return возвращает ClientConnection, соответствующее этому клиенту.
     */
    public ClientConnection getConnection(String name){
        for (int i = 0; i < server.connections.size(); i++) {
            ClientConnection connection = server.connections.get(i);
            if (connection.name.equals(name)) return connection;
        }
        return null;
    }

    /**
     * Поле сокета для общения с клинетом
     */
    private Socket socket;

    /**
     * Поле, хранящее объект чат-сервера
     */
    private ChatServer server;

    private BufferedReader in;
    private BufferedWriter out;

    /**
     * Имя клиента.
     */
    private String name;


    /**
     * Конструктор активного подключения к серверу
     *
     * @param connection сокет текущего подключения клиента
     * @param server     объект чат-сервера
     * @throws IOException
     */
    public ClientConnection(Socket connection, ChatServer server) throws IOException {
        this.server = server;
        socket = connection;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }


    /**
     * Вывод сообщения в консоль и отправка через broadcast
     *
     * @param message текст сообщения
     */
    private void broadcastMessage(String message) {
        if (name == null){ //если клиент не представился, то от него не будем отправлять сообщения другим юзерам
            sendMessage("Идентификация не получена. Сообщение не будет доставлено.", this);
            return;
        }
        System.out.println("Message from <" + name + ">: " + message);
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
        } catch (IOException ignored) {}
    }//...sendMessage



    @Override
    public void run() {
        try {
            String message;
            /*
            подключение к клиенту работает в параллельном потоке покуда программа чат-сервера не будет закрыта
            каждое сообщение, отправляемое этим клиентом, отправляется всем клиентам, подключенным к серверу.
             */
            while (true) {
                message = in.readLine();
                if (message == null) {
                    broadcastMessage("Bye bye!");
                    socket.close();
                    server.connections.remove(this);
                    break;
                }
                Matcher privMesMatcher = Pattern.compile("^>>\\w+>").matcher(message); //определение, что сообщение отправляется лично
                if (message.getBytes()[0] == 11) { //сообщение-приветствие если начинается с char == 11. клиент отправляет свое имя
                    name = message.substring(1);
                    broadcastMessage("Connected!");
                }
                else if (privMesMatcher.find()){ //когда отправляется личное сообщение.

                    String receiverName = message.substring(privMesMatcher.start(), privMesMatcher.end()).replaceAll(">","");
                    message = message.substring(privMesMatcher.end());
                    ClientConnection receiver = getConnection(receiverName);
                    if (receiver != null){
                        System.out.println("Message from <" + name + "> to <" + receiverName + ">: "  + message);
                        sendMessage(name + ">> " + message, receiver);
                    }
                    else {
                        System.out.println("Невозможно отправить сообщение! Клиент с именем <" + receiverName + "> не подключен к серверу. ");
                    }
                }
                else {
                    broadcastMessage(message);
                }

            }
        } catch (IOException ignored) {
        }
    }//...run
}
