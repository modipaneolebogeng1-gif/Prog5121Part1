package com.mycompany.prog5121p1;

import java.util.Scanner;

public class Prog5121P1 {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();
            
            System.out.println("=================================");
            System.out.println("      REGISTRATION AND LOGIN      ");
            System.out.println("=================================");
            
            // --- Registration (Your Part 1 code) ---
            System.out.println("\n--- Registration ---");
            
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            System.out.print("Enter South African cell number (+27...): ");
            String cellPhone = scanner.nextLine();
            
            String registrationResult = login.registerUser(
                    firstName, lastName, username, password, cellPhone
            );
            
            System.out.println("\n" + registrationResult);
            
            if (!registrationResult.equals("""
                                           Username successfully captured.
                                           Password successfully captured.
                                           Cell number successfully captured.""")) {
                
                System.out.println("\nRegistration failed.");
                return;
            }
            
            
            System.out.println("\n--- Login ---");
            
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            boolean loggedIn = login.loginUser(loginUsername, loginPassword);
            
            System.out.println("\n" + login.returnLoginStatus(loggedIn));

           
            if (!loggedIn) {
                System.out.println("\nYou must log in to use QuickChat. Exiting.");
                return;
            }

         
            MessageManager manager = new MessageManager();

           
            runQuickChat(scanner, manager);
        }
    }

  

    private static void runQuickChat(Scanner scanner, MessageManager manager) {
        System.out.println("\n=================================");
        System.out.println("         Welcome to QuickChat.    ");
        System.out.println("=================================");

        int maxMessages = 0;
        while (maxMessages <= 0) {
            System.out.print("How many messages do you wish to enter? ");
            try {
                maxMessages = Integer.parseInt(scanner.nextLine().trim());
                if (maxMessages <= 0) {
                    System.out.println("Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        boolean running = true;
        boolean messagesAlreadyEntered = false;

        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages (Part 3)"); // NEW OPTION
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    if (messagesAlreadyEntered) {
                        System.out.println("You have already entered " + maxMessages + " messages this session.");
                    } else {
                        messagesAlreadyEntered = true;
                        sendMessages(scanner, maxMessages, manager); // Pass manager
                    }
                }
                case 2 -> System.out.println("Coming Soon.");
                case 3 -> running = false;
                case 4 -> // NEW: Call the Part 3 Sub-menu
                    runStoredMessagesMenu(scanner, manager);
                default -> System.out.println("Invalid option. Choose 1, 2, 3 or 4.");
            }
        }
        System.out.println("\nGoodbye.");
    }

    private static void sendMessages(Scanner scanner, int count, MessageManager manager) {
        for (int i = 0; i < count; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + count + " ---");

            Message msg = new Message();
            msg.setNumMessagesSent(i);
            msg.generateMessageID(i); // Uses the loop counter

            // Recipient Input
            boolean recipientValid = false;
            while (!recipientValid) {
                System.out.print("Enter recipient cell number: ");
                msg.setRecipient(scanner.nextLine().trim());
                String result = msg.checkRecipientCell();
                System.out.println(result);
                recipientValid = result.startsWith("Cell phone number successfully");
            }

            // Message Text Input
            boolean textValid = false;
            while (!textValid) {
                System.out.print("Enter message (max 250 chars): ");
                msg.setMessageText(scanner.nextLine());
                String result = msg.checkMessageLength();
                System.out.println(result);
                textValid = result.startsWith("Message ready to send");
            }

            msg.setMessageHash(msg.createMessageHash());

            // Display Details
            System.out.println("\n===== Message Details =====");
            System.out.println("Message ID:   " + msg.getMessageID());
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Recipient:    " + msg.getRecipient());
            System.out.println("Message:      " + msg.getMessageText());

            // Send/Disregard/Store
            int sendChoice = 0;
            while (sendChoice < 1 || sendChoice > 3) {
                System.out.println("\nWhat would you like to do?");
                System.out.println("1) Send Message");
                System.out.println("2) Disregard Message");
                System.out.println("3) Store Message to send later");
                System.out.print("Choice: ");
                try {
                    sendChoice = Integer.parseInt(scanner.nextLine().trim());
                    if (sendChoice < 1 || sendChoice > 3) {
                        System.out.println("Please choose 1, 2 or 3.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number.");
                }
            }

            System.out.println(msg.SentMessage(sendChoice));

            // NEW: Set flags and populate the Part 3 Parallel Arrays
            switch (sendChoice) {
                case 1 -> {
                    msg.setFlag("Sent");
                    Message.addSentMessage(msg);
                    manager.addMessage(msg);
                }
                case 2 -> {
                    msg.setFlag("Disregard");
                    manager.addMessage(msg);
                }
                case 3 -> {
                    msg.setFlag("Stored");
                    Message.addSentMessage(msg);
                    manager.addMessage(msg);
                }
                default -> {
                }
            }
        }

        System.out.println("\n=================================");
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
        System.out.println("=================================");
    }

    
    private static void runStoredMessagesMenu(Scanner scanner, MessageManager manager) {
        boolean inMenu = true;
        
        while (inMenu) {
            System.out.println("\n=== PART 3: STORED MESSAGES ===");
            System.out.println("a. Display sender and recipient of stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search for messages by recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display full details report");
            System.out.println("g. Read JSON file into arrays");
            System.out.println("x. Back to Main Menu");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine().trim().toLowerCase();
            
            switch (choice) {
                case "a" -> manager.displaySenderAndRecipient();
                case "b" -> System.out.println("Longest Message: " + manager.getLongestStoredMessage());
                case "c" -> {
                    System.out.print("Enter Message ID / Recipient to search: ");
                    System.out.println(manager.searchByMessageID(scanner.nextLine().trim()));
                }
                case "d" -> {
                    System.out.print("Enter Recipient number: ");
                    System.out.println("Messages found: " + manager.searchByRecipient(scanner.nextLine().trim()));
                }
                case "e" -> {
                    System.out.print("Enter Hash to delete (e.g., 00:0:DIDYOU...): ");
                    System.out.println(manager.deleteMessageByHash(scanner.nextLine().trim()));
                }
                case "f" -> System.out.println(manager.generateReport());
                case "g" -> manager.readFromJSON();
                case "x" -> inMenu = false;
                default -> System.out.println("Invalid choice. Please select a, b, c, d, e, f, g, or x.");
            }
        }
    }
}
