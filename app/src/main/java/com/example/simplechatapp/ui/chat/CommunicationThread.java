package com.example.simplechatapp.ui.chat;

import com.example.simplechatapp.Displayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class CommunicationThread extends Thread {


    // Server IP address.
    private String serverAddress;
    // Displayer function to display messages on...
    private Displayer<String> displayer;
    // Code to call when communication is completed...
    private Runnable onCompleted;

    // Network communication fields
    private Socket conn = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private String cellURI;
    private String messageContent;

    public CommunicationThread(String serverAddress, Displayer<String> displayer, Runnable onCompleted) {
        super(serverAddress);
        this.serverAddress = serverAddress;
        this.displayer = displayer;
        this.onCompleted = onCompleted;
    }
    public CommunicationThread(String cellURI, String messageContent)
    {
        this.cellURI = cellURI;
        this.messageContent = messageContent;
    }

    // Called by another thread to send a message.
    public void sendMessage(String message) {
        try {
            oos.writeObject("CLIENT>>> " + message);
            oos.flush();
            display("CLIENT>>>" + message);
        } catch (IOException ioException) {
            display("ERROR: Error writing object");
        }
    }

    @Override
    public void run() {
        try {/**
             //Connect to server.
            conn = new Socket(serverAddress, 5001);
            display("Connecting to: " + conn.getInetAddress().getHostName());

            // Get and initialise streams.
            oos = new ObjectOutputStream(conn.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(conn.getInputStream());
            display("Got I/O streams");

            // Read messages from server until told to terminate.
            String message = "";

            do {
                message = (String) ois.readObject();
                display(message);
            } while (!message.equals("SERVER>>> TERMINATE"));
         */
        String sms = sendSms(cellURI,messageContent);
        } catch (Exception e) {
            display("ERROR: " + e.getMessage());

        } finally {
            // Close connection.
            close();

            display("Disconnected...");
        }
    }
    public String sendSms(String RecieverNumber, String messageContent) {
        try {
            // Construct data
            String apiKey = "apikey=" + "NmU1NzM0Mzg0YTRlNzc0NTU5NzY0NTMzNDE2MjYyNTk=";//"yourapiKey";
            String message = messageContent;//"&message=" + "This is your message";
            String sender = "Simple Chat App";//"&sender=" + "Jims Autos";
            String numbers = RecieverNumber;//"&numbers=" + "447123456789";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
    private void display(String message) {
        // If not displayer provided, do nothing.
        if(displayer == null) return;

        // Otherwise, display it.
        displayer.display(message);
    }

    private void close() {
        try {
            conn.close();

            // Was an onCompleted runnable provided? If so, run it.
            if(onCompleted != null) onCompleted.run();

        } catch (Exception e) {
            display("ERROR: " + e.getMessage());
        }
    }
}
