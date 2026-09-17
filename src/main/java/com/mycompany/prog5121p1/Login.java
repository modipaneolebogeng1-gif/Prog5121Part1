/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog5121p1;

/**
 *
 * @author OModi
 */

import java.util.regex.Pattern;

public class Login {

    private String storedFirstName;
    private String storedLastName;
    private String storedUsername;
    private String storedPassword;


    /*
     * Regular Expression Attribution:
     * The Java Pattern class is used for regular-expression matching.
     *
     * Oracle Java Pattern documentation:
     * https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
     *
     * The expression validates a South African international cellphone
     * number beginning with +27 followed by nine digits.
     */

    private static final String SA_CELL_REGEX = "^\\+27[0-9]{9}$";

    /**
     * Checks that the username contains an underscore
     * and is no more than five characters long.
     *
     * @param username username entered by the user
     * @return true if the username is correctly formatted
     */
    public boolean checkUserName(String username) {

        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    /**
     * Checks that the password meets the required
     * complexity requirements.
     *
     * Requirements:
     * - At least eight characters
     * - Capital letter
     * - Number
     * - Special character
     *
     * @param password password entered by the user
     * @return true if the password is valid
     */
    public boolean checkPasswordComplexity(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        char[] characters = password.toCharArray();

        for (char character : characters) {

            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }

            if (Character.isDigit(character)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks that the cellphone number contains
     * the international country code and is correctly formatted.
     *
     * @param cellPhone cellphone number entered
     * @return true if the cellphone number is valid
     */
    public boolean checkCellPhoneNumber(String cellPhone) {

        return cellPhone != null
                && Pattern.matches(SA_CELL_REGEX, cellPhone);
    }

    /**
     * Registers the user when the username, password,
     * and cellphone number are valid.
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param username user's username
     * @param password user's password
     * @param cellPhone user's cellphone number
     * @return registration status message
     */
    public String registerUser(
            String firstName,
            String lastName,
            String username,
            String password,
            String cellPhone) {

        boolean usernameValid = checkUserName(username);
        boolean passwordValid = checkPasswordComplexity(password);
        boolean cellPhoneValid = checkCellPhoneNumber(cellPhone);

        if (usernameValid && passwordValid && cellPhoneValid) {

            storedFirstName = firstName;
            storedLastName = lastName;
            storedUsername = username;
            storedPassword = password;

            return "Username successfully captured.\n"
                    + "Password successfully captured.\n"
                    + "Cell number successfully captured.";
        }

        StringBuilder errorMessage = new StringBuilder();

        if (!usernameValid) {

            errorMessage.append(
                    "Username is not correctly formatted; please ensure that "
                    + "your username contains an underscore and is no more "
                    + "than five characters in length."
            );
        }

        if (!passwordValid) {

            if (errorMessage.length() > 0) {
                errorMessage.append("\n");
            }

            errorMessage.append(
                    "Password is not correctly formatted; please ensure that "
                    + "the password contains at least eight characters, a "
                    + "capital letter, a number, and a special character."
            );
        }

        if (!cellPhoneValid) {

            if (errorMessage.length() > 0) {
                errorMessage.append("\n");
            }

            errorMessage.append(
                    "Cell number is incorrectly formatted or does not contain "
                    + "an international code; please correct the number and try again."
            );
        }

        return errorMessage.toString();
    }

    /**
     * Verifies that the entered username and password
     * match the stored registration details.
     *
     * @param username username entered during login
     * @param password password entered during login
     * @return true if the login details are correct
     */
    public boolean loginUser(String username, String password) {

        return username != null
                && password != null
                && username.equals(storedUsername)
                && password.equals(storedPassword);
    }

    /**
     * Returns the appropriate login status message.
     *
     * @param loggedIn login result
     * @return successful or failed login message
     */
    public String returnLoginStatus(boolean loggedIn) {

        if (loggedIn) {

            return "Welcome "
                    + storedFirstName
                    + " "
                    + storedLastName
                    + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}
