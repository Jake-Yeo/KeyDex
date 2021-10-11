/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs20models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Jake Yeo
 */
public class Accounts {

    /**
     * @param args the command line arguments
     */
    private String password, username;
    private final String DELIMITER = "𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤";
    private final String defaultPreferences = "Default𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤Default𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤false𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤false𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤false𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤false𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤false𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤true𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤false𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤500𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤-8𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤-8𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤3𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤30𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤30𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤false𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤true";
    private boolean isUserLoggedIn;

    public String getDefaultPreferences() {
        return defaultPreferences;
    }

    public String getDelimiter() {
        return DELIMITER;
    }

    public Accounts() {
        this.username = "";
        this.password = "";
        this.isUserLoggedIn = false;
    }

    public boolean getIsUserLoggedIn() {
        return isUserLoggedIn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsUserLoggedIn(boolean tf) {
        this.isUserLoggedIn = tf;
    }

    public boolean checkIfAccountExists(String username, String password) throws FileNotFoundException {
        boolean doesAccountExist = false;
        int accountsAndPrefrencesArraySize = 0;
        File file = new File("src/txts/Accounts.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            accountsAndPrefrencesArraySize += 1;
            scanner.nextLine();
        }
        scanner = new Scanner(file);
        String[] accountsAndPrefrencesArray = new String[accountsAndPrefrencesArraySize];

        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            accountsAndPrefrencesArray[i] = scanner.nextLine();
        }

        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            scanner = new Scanner(accountsAndPrefrencesArray[i]);
            scanner.useDelimiter(DELIMITER);
            // System.out.println(accountsAndPrefrencesArray[i]);
            //System.out.println(scanner.next() + " " + scanner.next());
            if (scanner.next().equals(username) && scanner.next().equals(password)) {
                doesAccountExist = true;
                i = accountsAndPrefrencesArray.length + 1000;
            } else {
                //System.out.println("running");
                doesAccountExist = false;
            }
        }
        scanner.close();
        return doesAccountExist;
    }

    public boolean checkIfUserNameIsTheSame(String username) throws FileNotFoundException {
        boolean isUserNameTheSame = false;
        int accountsAndPrefrencesArraySize = 0;
        File file = new File("src/txts/Accounts.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            accountsAndPrefrencesArraySize += 1;
            scanner.nextLine();
        }
        scanner = new Scanner(file);
        String[] accountsAndPrefrencesArray = new String[accountsAndPrefrencesArraySize];

        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            accountsAndPrefrencesArray[i] = scanner.nextLine();
        }

        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            scanner = new Scanner(accountsAndPrefrencesArray[i]);
            scanner.useDelimiter(DELIMITER);
            // System.out.println(accountsAndPrefrencesArray[i]);
            //System.out.println(scanner.next() + " " + scanner.next());
            if (scanner.next().equals(username)) {
                isUserNameTheSame = true;
                i = accountsAndPrefrencesArray.length + 1000;
            } else {
                isUserNameTheSame = false;
            }
        }
        scanner.close();
        return isUserNameTheSame;
    }

    public void createAccountAndSave(String username, String password, boolean typingSentences, boolean lowerCaseWords, boolean numbers, boolean symbols, boolean uppercaseWords, boolean darkMode, boolean streamLofi, int lengthToGenerate, int typingVolume, int errorVolume, int lofiStreamVolume, int textDisplaySize, int typedTextSize, boolean customText, boolean showKeyBoardVisual) throws FileNotFoundException, IOException {
        this.username = username;
        this.password = password;
        File file = new File("src/txts/Accounts.txt");
        String path = file.getAbsolutePath().replaceAll("\\\\", "/");
        FileWriter fileWriter = new FileWriter(path, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println(this.username + DELIMITER + this.password + DELIMITER + typingSentences + DELIMITER + lowerCaseWords + DELIMITER + numbers + DELIMITER + symbols + DELIMITER + uppercaseWords + DELIMITER + darkMode + DELIMITER + streamLofi + DELIMITER + lengthToGenerate + DELIMITER + typingVolume + DELIMITER + errorVolume + DELIMITER + lofiStreamVolume + DELIMITER + textDisplaySize + DELIMITER + typedTextSize + DELIMITER + customText + DELIMITER + showKeyBoardVisual);

        printWriter.close();
        fileWriter.close();
        
        file = new File("src/txts/CustomText.txt");
        path = file.getAbsolutePath().replaceAll("\\\\", "/");
        fileWriter = new FileWriter(path, true);
        printWriter = new PrintWriter(fileWriter);
        printWriter.println(this.username + DELIMITER + this.password);
        printWriter.close();
        fileWriter.close();

    }

    public String getAccountPrefrences(String username, String password) throws FileNotFoundException {//use array, find which index the prefrences are at and remove the user name and password
        int accountsAndPrefrencesArraySize = 0;
        int whichIndexToFindPreferences = 0;
        File file = new File("src/txts/Accounts.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            accountsAndPrefrencesArraySize += 1;
            scanner.nextLine();
        }
        scanner = new Scanner(file);
        String[] accountsAndPrefrencesArray = new String[accountsAndPrefrencesArraySize];

        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            accountsAndPrefrencesArray[i] = scanner.nextLine();
        }
        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            scanner = new Scanner(accountsAndPrefrencesArray[i]);
            scanner.useDelimiter(DELIMITER);
            if (scanner.next().equals(username)) {
                whichIndexToFindPreferences = i;
                i = accountsAndPrefrencesArray.length + 1000;
            }
        }
        scanner.close();
        return accountsAndPrefrencesArray[whichIndexToFindPreferences];
    }

    public void saveAccountPreferences(String username, String password, boolean typingSentences, boolean lowerCaseWords, boolean numbers, boolean symbols, boolean uppercaseWords, boolean darkMode, boolean streamLofi, int lengthToGenerate, int typingVolume, int errorVolume, int lofiStreamVolume, int textDisplaySize, int typedTextSize, boolean customText, boolean showKeyBoardVisual) throws FileNotFoundException, IOException {//use an array and save into txt
        int accountsAndPrefrencesArraySize = 0;
        int whichIndexToSavePreferencesTo = 0;
        File file = new File("src/txts/Accounts.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            accountsAndPrefrencesArraySize += 1;
            scanner.nextLine();
        }
        scanner = new Scanner(file);
        String[] accountsAndPrefrencesArray = new String[accountsAndPrefrencesArraySize];

        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            accountsAndPrefrencesArray[i] = scanner.nextLine();
        }
        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            scanner = new Scanner(accountsAndPrefrencesArray[i]);
            scanner.useDelimiter(DELIMITER);
            if (scanner.next().equals(username)) {
                whichIndexToSavePreferencesTo = i;
                i = accountsAndPrefrencesArray.length + 1000;
            }

        }
        String path = file.getAbsolutePath().replaceAll("\\\\", "/");
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("");
        accountsAndPrefrencesArray[whichIndexToSavePreferencesTo] = this.username + DELIMITER + this.password + DELIMITER + typingSentences + DELIMITER + lowerCaseWords + DELIMITER + numbers + DELIMITER + symbols + DELIMITER + uppercaseWords + DELIMITER + darkMode + DELIMITER + streamLofi + DELIMITER + lengthToGenerate + DELIMITER + typingVolume + DELIMITER + errorVolume + DELIMITER + lofiStreamVolume + DELIMITER + textDisplaySize + DELIMITER + typedTextSize + DELIMITER + customText + DELIMITER + showKeyBoardVisual;
        for (int i = 0; i < accountsAndPrefrencesArray.length; i++) {
            printWriter.println(accountsAndPrefrencesArray[i]);
        }
        printWriter.close();
        fileWriter.close();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
