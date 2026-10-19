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
            
            if (!registrationResult.equals(
                    "Username successfully captured.\n"
                            + "Password successfully captured.\n"
                            + "Cell number successfully captured.")) {
                
                System.out.println("\nRegistration failed.");
                return;
            }
            
            // --- Login (Your Part 1 code) ---
            System.out.println("\n--- Login ---");
            
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            boolean loggedIn = login.loginUser(loginUsername, loginPassword);
            
            System.out.println("\n" + login.returnLoginStatus(loggedIn));

            // --- PART 2 GATE: Only logged-in users get QuickChat ---
            if (!loggedIn) {
                System.out.println("\nYou must log in to use QuickChat. Exiting.");
                return;
            }

            // Start QuickChat
            runQuickChat(scanner);
        }
    }

    // =========================================================
    //                  PART 2: QUICKCHAT
    // =========================================================

    private static void runQuickChat(Scanner scanner) {
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
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter 1, 2 or 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (messagesAlreadyEntered) {
                        System.out.println("You have already entered " + maxMessages + " messages this session.");
                    } else {
                        messagesAlreadyEntered = true;
                        sendMessages(scanner, maxMessages);
                    }
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Choose 1, 2 or 3.");
            }
        }
        System.out.println("\nGoodbye.");
    }

    private static void sendMessages(Scanner scanner, int count) {
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

            if (sendChoice == 1 || sendChoice == 3) {
                Message.addSentMessage(msg);
            }
        }

        System.out.println("\n=================================");
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
        System.out.println("=================================");
    }
}
