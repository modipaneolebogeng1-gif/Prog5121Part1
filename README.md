# PROG5121 Part 1 - Registration and Login

This is a console application for Part 1 of the Programming 1A PoE.
It includes:
- User registration with validation
- Login authentication
- Regex cell phone number validation
- 14 JUnit unit tests
## 🚀 Part 2: QuickChat Features
*   **User Authentication:** Secure registration and login system (validating usernames, passwords, and SA cell numbers).
*   **Message Generation:** Automatically generates unique Message IDs and cryptographic-style Message Hashes.
*   **Message Validation:** Checks if the recipient's cell number is correctly formatted and ensures message text does not exceed 250 characters.
*   **Message Routing:** Allows the user to choose the fate of their message:
    *   **Send:** Immediately sends the message.
    *   **Disregard:** Discards the message.
    *   **Store:** Saves the message to a local JSON file to be sent later.

## 🗄️ Part 3: Stored Messages & Arrays
Part 3 expands the application by introducing parallel arrays to store message data and a new menu to manage stored messages.

*   **Parallel Arrays:** The application populates 5 specific arrays (`sentMessages`, `disregardedMessages`, `storedMessages`, `messageHashes`, and `messageIDs`) dynamically based on the user's actions.
*   **Stored Messages Menu:** A dedicated fourth menu option that allows users to:
    *   **a.** Display the sender and recipient of all stored messages.
    *   **b.** Display the longest stored message.
    *   **c.** Search for a message by ID and display its recipient and text.
    *   **d.** Search for all messages sent to a specific recipient.
    *   **e.** Delete a message using its unique Message Hash (shifts arrays to prevent gaps).
    *   **f.** Display a full detailed report of all stored messages.
*   **JSON File Handling:** Uses the Gson library to read and write stored messages to a `messages.json` file, ensuring data persistence across application restarts.

---

## 🧪 Unit Testing
The project includes a comprehensive suite of JUnit 5 tests located in `src/test/java/com/mycompany/prog5121p1/MessageTest.java`. 
The tests verify:
1.  The `Sent` messages array is correctly populated.
2.  The application correctly identifies the longest stored message.
3.  The search-by-ID function works accurately.
4.  The search-by-recipient function returns all relevant messages.
5.  The delete-by-hash function successfully removes a message without breaking the parallel arrays.
6.  The generated report contains the correct hash, recipient, and message text.

## ⚙️ How to Run
1. Clone this repository to your local machine.
2. Open the project in **Apache NetBeans** (or any IDE that supports Maven).
3. Clean and Build the project (to download Maven dependencies like Gson and JUnit).
4. Open `src/main/java/com/mycompany/prog5121p1/Prog5121P1.java`.
5. Right-click and select **Run File** (or press `Shift + F6`).
