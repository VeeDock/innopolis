package part1.task10.multisockets.tests;

import part1.task10.multisockets.serverSide.ChatServer;

import java.io.IOException;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer(8788);
    }
}