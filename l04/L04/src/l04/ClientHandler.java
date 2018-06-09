package l04;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final String diaryFilenameString = "diary.txt";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void sendResponse(Socket socket, int statusCode, String responseString) {
        String contentTypeHeader = "Content-Type: text/html\r\n";
        String serverHeader = "Server: WebServer\r\n";
        String statusLine;

        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
            if (statusCode == 200) {
                statusLine = "HTTP/1.0 200 OK" + "\r\n";

                String contentLengthHeader = "Content-Length: " + responseString.length() + "\r\n";

                out.writeBytes(statusLine);
                out.writeBytes(serverHeader);
                out.writeBytes(contentTypeHeader);
                out.writeBytes(contentLengthHeader);
                out.writeBytes("\r\n");
                out.writeBytes(responseString);
            } else if (statusCode == 405) {
                statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";

                out.writeBytes(statusLine);
                out.writeBytes("\r\n");
            } else {
                statusLine = "HTTP/1.0 404 Not Found" + "\r\n";

                out.writeBytes(statusLine);
                out.writeBytes("\r\n");
            }

            out.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public void handleRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            String headerLine = in.readLine();

            StringTokenizer tokenizer = new StringTokenizer(headerLine);

            String httpMethod = tokenizer.nextToken();

            if (httpMethod.equals("GET")) {
                System.out.println("GET method processed");

                String httpQueryString = tokenizer.nextToken();

                StringBuilder responseBuffer = new StringBuilder();

                responseBuffer.append("<html><h1>Web Server Home Page.... </h1><br>")
                        .append("<b>Welcome to my web server!</b><BR>").append("</html>");

                sendResponse(socket, 200, responseBuffer.toString());
            } else if (httpMethod.equals("POST")) {
                System.out.println("POST method processed");

                File file = new File(diaryFilenameString);

                PrintWriter printWriter = null;

                // create the diary file if it doesn't already exist
                if (file.exists() && !file.isDirectory()) {
                    // diary file exists, do nothing
                } else {
                    try {
                        printWriter = new PrintWriter(diaryFilenameString, "UTF-8");
                    } catch (IOException e) {
                        System.out.println("IOException: " + e.getMessage());
                    } finally {
                        if (printWriter != null) {
                            printWriter.close();
                        }
                    }
                }

                Date date = new Date();
                long currentUnixTimestamp = date.getTime() / 1000;

                try {
                    // write a diary entry including the current Unix time
                    Files.write(Paths.get(diaryFilenameString),
                            (Long.toString(currentUnixTimestamp) + " diary entry\n").getBytes(),
                            StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.out.println("IOException: " + e.getMessage());
                }

                StringBuilder responseBuffer = new StringBuilder();

                responseBuffer.append("POST Request Received");

                sendResponse(socket, 200, responseBuffer.toString());
            } else {
                System.out.println("The HTTP method is not recognized");

                sendResponse(socket, 405, "Method Not Allowed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("\nClientHandler Started for " + this.socket);

        handleRequest(this.socket);

        System.out.println("ClientHandler Terminated for " + this.socket + "\n");
    }
}
