package part1.task10.multisockets.tests;

import part1.task10.multisockets.clientSide.ChatClient;

public class ClientTest {
    public static void main(String[] args) {
        ChatClient chatClient1 = new ChatClient(8788, "Ash");
    }
}
