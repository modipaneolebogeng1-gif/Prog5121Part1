/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog5121p1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 *
 * @author OModi
 */
public class Message {
    
    private String messageID;
    private int numMessagesSent;
    private String recipient;
    private String messageText;
    private String messageHash;

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final String JSON_FILE = "messages.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String RECIPIENT_REGEX = "^(\\+27|0)[0-9]{9,10}$";

    public void generateMessageID() {
        generateMessageID(this.numMessagesSent);
    }

    public void generateMessageID(int messageNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%02d", Math.abs(messageNumber) % 100));
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(rand.nextInt(10));
        }
        this.messageID = sb.toString();
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String messageIDDisplay() {
        return "Message ID generated: " + messageID;
    }

    public String checkRecipientCell() {
        if (recipient != null && Pattern.matches(RECIPIENT_REGEX, recipient)) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not "
                + "contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText != null && messageText.length() > 250) {
            int over = messageText.length() - 250;
            return "Message exceeds 250 characters by " + over
                    + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    public String createMessageHash() {
        if (messageID == null) {
            generateMessageID();
        }
        String firstTwoID = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        return firstTwoID + ":" + numMessagesSent + ":" + firstWord + lastWord;
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1 -> {
                return "Message successfully sent.";
            }
            case 2 -> {
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                storeMessage();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid choice.";
            }
        }
    }

    public void storeMessage() {
        List<Message> existing = loadExistingMessages();
        existing.add(this);
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(existing, writer);
        } catch (IOException e) {
            System.err.println("Could not write JSON: " + e.getMessage());
        }
    }

    private List<Message> loadExistingMessages() {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            Message[] arr = gson.fromJson(reader, Message[].class);
            return arr == null ? new ArrayList<>() : new ArrayList<>(List.of(arr));
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n\n");
        }
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return sentMessages.size();
    }

    public static void addSentMessage(Message m) {
        sentMessages.add(m);
    }

    public String getMessageID() { return messageID; }
    public void setMessageID(String id) { this.messageID = id; }
    public int getNumMessagesSent() { return numMessagesSent; }
    public void setNumMessagesSent(int n) { this.numMessagesSent = n; }
    public String getRecipient() { return recipient; }
    public void setRecipient(String r) { this.recipient = r; }
    public String getMessageText() { return messageText; }
    public void setMessageText(String t) { this.messageText = t; }
    public String getMessageHash() { return messageHash; }
    public void setMessageHash(String h) { this.messageHash = h; }
}
