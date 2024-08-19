package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientEcho {

    public static void main(String[] args) {
        Socket socket;
        InputStream EntryChannel;
        OutputStream ExitChannel;
        BufferedReader entry;
        PrintWriter exit;

        try {
            socket = new Socket("127.0.0.1", 4000);
            EntryChannel = socket.getInputStream();
            ExitChannel = socket.getOutputStream();

            entry = new BufferedReader(new InputStreamReader(EntryChannel));
            exit = new PrintWriter(ExitChannel, true);

            Scanner leitor = new Scanner(System.in);

            System.out.println("Você quer codificar ou decodificar uma mensagem?(Escreva 'codificar' ou 'decodificar'):");
            String operation = leitor.nextLine();
            exit.println(operation);

            System.out.println("Sua mensagem:");
            String message = leitor.nextLine();
            exit.println(message);

            System.out.println("Sua senha:");
            String password = leitor.nextLine();
            exit.println(password);

            String result = entry.readLine();
            System.out.println("Resposta: " + result);

            socket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}