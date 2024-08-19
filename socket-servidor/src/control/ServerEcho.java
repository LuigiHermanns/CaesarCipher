package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Console;

public class ServerEcho {
    private ServerSocket sckServidor;

    public ServerEcho() throws IOException {
        this.sckServidor = new ServerSocket(4000);
        System.out.println("Server escutando na porta 4000");

        for (;;) {
            Socket sckEcho;
            InputStream EntryChannel;
            OutputStream ExitChannel;
            BufferedReader entry;
            PrintWriter exit;

            sckEcho = this.sckServidor.accept();
            EntryChannel = sckEcho.getInputStream();
            ExitChannel = sckEcho.getOutputStream();
            entry = new BufferedReader(new InputStreamReader(EntryChannel));
            exit = new PrintWriter(ExitChannel, true);

            while (true) {
                String operation = entry.readLine();
                String message = entry.readLine();
                String password = entry.readLine();

                if (operation == null || message == null || password == null) {
                    break;
                }

                String result;
                if (operation.equalsIgnoreCase("codificar")) {
                    result = caesarCipher(message, password, true);
                } else if (operation.equalsIgnoreCase("decodificar")) {
                    result = caesarCipher(message, password, false);
                } else {
                    result = "Operação inválida";
                }

                exit.println(result);
            }
            sckEcho.close();
        }
    }

    private String caesarCipher(String text, String password, boolean encode) {
        StringBuilder result = new StringBuilder();
        int passwordLength = password.length();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (Character.isLetter(c)) {
                int shift = Character.getNumericValue(password.charAt(i % passwordLength));
                if (!encode) {
                    shift = -shift;
                }
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) ((c - base + shift + 26) % 26 + base));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}