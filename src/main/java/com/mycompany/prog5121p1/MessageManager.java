package com.mycompany.prog5121p1;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    // The 5 required arrays
    private String[] sentMessages = new String[50];
    private String[] disregardedMessages = new String[50];
    private Message[] storedMessages = new Message[50]; 
    private String[] messageHashes = new String[50];
    private String[] messageIDs = new String[50];

    private int sentCount = 0;
    private int disregardedCount = 0;
    private int storedCount = 0;

    // NEW: Keep track of ALL messages (Sent, Stored, Disregard) so search works!
    private List<Message> allMessages = new ArrayList<>();

    public void addMessage(Message msg) {
        // Add to master list for searching
        allMessages.add(msg);

        // Populate specific arrays based on flag
        if (msg.getFlag().equalsIgnoreCase("Sent")) {
            sentMessages[sentCount++] = msg.getMessageText();
        } else if (msg.getFlag().equalsIgnoreCase("Disregard")) {
            disregardedMessages[disregardedCount++] = msg.getMessageText();
        } else if (msg.getFlag().equalsIgnoreCase("Stored")) {
            storedMessages[storedCount] = msg; 
            messageHashes[storedCount] = msg.getMessageHash();
            messageIDs[storedCount] = msg.getMessageID();
            storedCount++;
        }
    }

    public void displaySenderAndRecipient() {
        System.out.println("\n--- Stored Messages: Sender & Recipient ---");
        for (int i = 0; i < storedCount; i++) {
            System.out.println("Sender: Developer | Recipient: " + storedMessages[i].getRecipient());
        }
    }

    public String getLongestStoredMessage() {
        String longest = "";
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].getMessageText().length() > longest.length()) {
                longest = storedMessages[i].getMessageText();
            }
        }
        return longest;
    }

    // Searches ALL messages, not just stored ones
    public String searchByMessageID(String id) {
        for (Message msg : allMessages) {
            if (msg.getMessageID().equals(id)) {
                return "Recipient: " + msg.getRecipient() + " | Message: " + msg.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    public List<String> searchByRecipient(String recipient) {
        List<String> results = new ArrayList<>();
        for (Message msg : allMessages) {
            if (msg.getRecipient().equals(recipient)) {
                results.add(msg.getMessageText());
            }
        }
        return results;
    }

    public String deleteMessageByHash(String hash) {
        for (int i = 0; i < storedCount; i++) {
            if (messageHashes[i].equals(hash)) {
                String deletedMsg = storedMessages[i].getMessageText();
                
                // Remove from master list
                allMessages.removeIf(m -> m.getMessageHash().equals(hash));
                
                // Shift arrays
                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j] = storedMessages[j + 1];
                    messageHashes[j] = messageHashes[j + 1];
                    messageIDs[j] = messageIDs[j + 1];
                }
                storedMessages[storedCount - 1] = null;
                messageHashes[storedCount - 1] = null;
                messageIDs[storedCount - 1] = null;
                
                storedCount--;
                return "Message: \"" + deletedMsg + "\" successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder("--- Full Stored Messages Report ---\n");
        for (int i = 0; i < storedCount; i++) {
            report.append("Message Hash: ").append(messageHashes[i])
                  .append(" | Recipient: ").append(storedMessages[i].getRecipient())
                  .append(" | Message: ").append(storedMessages[i].getMessageText()).append("\n");
        }
        return report.toString();
    }

    public void readFromJSON() {
        List<Message> loadedMessages = Message.loadExistingMessages();
        if (loadedMessages.isEmpty()) {
            System.out.println("No messages found in JSON file.");
            return;
        }

        storedCount = 0;
        allMessages.clear();

        for (Message msg : loadedMessages) {
            if (msg.getFlag() == null || msg.getFlag().equalsIgnoreCase("Stored")) {
                storedMessages[storedCount] = msg;
                messageHashes[storedCount] = msg.getMessageHash();
                messageIDs[storedCount] = msg.getMessageID(); 
                storedCount++;
                allMessages.add(msg);
            }
        }
        System.out.println("Successfully read JSON file into arrays.");
    }

    public String[] getSentMessages() { return sentMessages; }
    public int getStoredCount() { return storedCount; }
}
