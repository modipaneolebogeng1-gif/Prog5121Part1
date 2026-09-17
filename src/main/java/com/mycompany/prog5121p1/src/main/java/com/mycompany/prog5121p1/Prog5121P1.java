/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog5121p1;

/**
 *
 * @author OModi
 */

import java.util.Scanner;

public class Prog5121P1 {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            Login login = new Login();
            
            System.out.println("=================================");
            System.out.println("      REGISTRATION AND LOGIN      ");
            System.out.println("=================================");
            
            // Registration
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
                    firstName,
                    lastName,
                    username,
                    password,
                    cellPhone
            );
            
            System.out.println("\n" + registrationResult);
            
            if (!registrationResult.equals(
                    "Username successfully captured.\n"
                            + "Password successfully captured.\n"
                            + "Cell number successfully captured.")) {
                
                System.out.println("\nRegistration failed.");
                scanner.close();
                return;
            }
            
            // Login
            System.out.println("\n--- Login ---");
            
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();
            
            boolean loggedIn = login.loginUser(
                    loginUsername,
                    loginPassword
            );
            
            System.out.println(
                    "\n" + login.returnLoginStatus(loggedIn)
            );
        }
    }
}
