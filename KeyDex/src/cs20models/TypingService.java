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
import java.nio.file.Path;
import java.util.Scanner;

/**
 *
 * @author Jake Yeo
 */
public class TypingService {

    private String[] theRandomSentencesArray;
    private String[] theCustomTextArray;
    private String[] theRandomWordArray;
    private String[] theRandomSymbolArray;
    private int customTextArrayLength = 0;
    private final String DELIMITER = "𝔻𝕖𝕝𝕀𝕄𝕀𝕥𝔼𝕣𝕢𝕥𝕠𝔹ℕ𝕖𝕤";

    public int returnCustomTextArrayLength() {
        return customTextArrayLength;
    }

    public void setUpArrays() throws FileNotFoundException {//sets up every array once so it does not set up each array every time a method is called
        int whatLengthOfRandomSentenceArrayShouldBe = 0;

        File file = new File("src/txts/RandomSentences.txt");// weird bug where ' symbol isn't recognized correctly
        String path = file.getAbsolutePath().replaceAll("\\\\", "/");
        Scanner scanner = new Scanner(new File(path));//make sure to close later on.
        while (scanner.hasNext()) {
            whatLengthOfRandomSentenceArrayShouldBe++;
            scanner.nextLine();
        }

        theRandomSentencesArray = new String[whatLengthOfRandomSentenceArrayShouldBe];

        scanner = new Scanner(new File(path));
        for (int i = 0; i < theRandomSentencesArray.length; i++) {
            theRandomSentencesArray[i] = scanner.nextLine();
        }

        int whatLengthOfRandomWordArrayShouldBe = 0;

        file = new File("src/txts/WordsLowercase.txt");// weird bug where ' symbol isn't recognized correctly
        path = file.getAbsolutePath().replaceAll("\\\\", "/");
        scanner = new Scanner(new File(path));//make sure to close later on.
        while (scanner.hasNext()) {
            whatLengthOfRandomWordArrayShouldBe++;
            scanner.nextLine();
        }

        theRandomWordArray = new String[whatLengthOfRandomWordArrayShouldBe];

        scanner = new Scanner(new File(path));
        for (int i = 0; i < theRandomWordArray.length; i++) {
            theRandomWordArray[i] = scanner.nextLine();
        }
        //System.out.println(theRandomWordArray.length);
        int whatLengthOfRandomSymbolArrayShouldBe = 0;

        file = new File("src/txts/Symbols.txt");// weird bug where ' symbol isn't recognized correctly
        path = file.getAbsolutePath().replaceAll("\\\\", "/");
        scanner = new Scanner(new File(path));//make sure to close later on.
        while (scanner.hasNext()) {
            whatLengthOfRandomSymbolArrayShouldBe++;
            scanner.nextLine();
        }
        theRandomSymbolArray = new String[whatLengthOfRandomSymbolArrayShouldBe];

        scanner = new Scanner(new File(path));
        for (int i = 0; i < theRandomSymbolArray.length; i++) {
            theRandomSymbolArray[i] = scanner.nextLine();
        }
        scanner.close();

    }

    public String getRandomSentence() throws FileNotFoundException {

        String chosenSentenceToReturn = "";

        int randomInt = (int) Math.floor(Math.random() * theRandomSentencesArray.length);
        //System.out.println(randomInt);
        chosenSentenceToReturn = theRandomSentencesArray[randomInt] + " ";

        return chosenSentenceToReturn;
    }

    public String getRandomUppercaseWord() throws FileNotFoundException {
        String chosenUppercaseWordToReturn = "";

        int randomInt = (int) Math.floor(Math.random() * theRandomWordArray.length);
        //System.out.println(randomInt);
        chosenUppercaseWordToReturn = theRandomWordArray[randomInt] + " ";
        chosenUppercaseWordToReturn = ("" + chosenUppercaseWordToReturn.charAt(0)).toUpperCase() + chosenUppercaseWordToReturn.substring(1);

        return chosenUppercaseWordToReturn;
    }

    public String getRandomLowercaseWord() throws FileNotFoundException {
        String chosenLowercaseWordToReturn = "";

        int randomInt = (int) Math.floor(Math.random() * theRandomWordArray.length);
        //System.out.println(randomInt);
        chosenLowercaseWordToReturn = theRandomWordArray[randomInt] + " ";

        return chosenLowercaseWordToReturn.toLowerCase();//just in case there are any uppercase words in the array
    }

    public String getRandomNumbers() {
        int chosenNumberToReturn = 0;
        int maxNumThatCanBeReturned = 100000;
        chosenNumberToReturn = (int) Math.floor(Math.random() * maxNumThatCanBeReturned);

        return chosenNumberToReturn + " ";
    }

    public String getRandomSymbols() throws FileNotFoundException {
        String chosenSymbolToReturn = "";

        int randomInt = (int) Math.floor(Math.random() * theRandomSymbolArray.length);
        //System.out.println(randomInt);
        int randomIntForStringLength = (int) Math.ceil(Math.random() * 10);//stops multiple spaces next to each other from being returned
        for (int i = 0; i < randomIntForStringLength; i++) {
            randomInt = (int) Math.floor(Math.random() * theRandomSymbolArray.length);
            chosenSymbolToReturn += theRandomSymbolArray[randomInt];
        }

        return chosenSymbolToReturn + " ";
    }

    public String getRandomCustomText() throws FileNotFoundException {
        String chosenCustomTextToReturn = "";

        int randomInt = (int) Math.floor(Math.random() * theCustomTextArray.length);
        //System.out.println(randomInt);
        chosenCustomTextToReturn = theCustomTextArray[randomInt].trim() + " ";

        return chosenCustomTextToReturn;
    }

    public String userDidError(int lengthOfTextInTextArea, String textInTextArea, String characterInUserTextField, int numOfTimesChecked) {
        String didUserDoError = "";
//        System.out.println("length of text is " +lengthOfTextInTextArea);
//        System.out.println("userTextField returns " +characterInUserTextField);
//        System.out.println("numOfTimesChecked returns " + numOfTimesChecked);
//        System.out.println("textInTextArea returns " + textInTextArea);
        if (numOfTimesChecked < lengthOfTextInTextArea) {
            if (textInTextArea.charAt(numOfTimesChecked) == characterInUserTextField.charAt(0)) {
                didUserDoError = "false";
            } else {
                didUserDoError = "true";
            }
        }
        if (numOfTimesChecked == lengthOfTextInTextArea - 1) {

            if (textInTextArea.charAt(numOfTimesChecked) == characterInUserTextField.charAt(0)) {
                didUserDoError = "falseGetNewString";
            } else {
                didUserDoError = "trueGetNewString";
            }
        }
        //System.out.println(didUserDoError);
        return didUserDoError;
    }

    public void setUpCustomTextArray(String username) throws FileNotFoundException {
        int customTextArraySize = 0;
        int whichIndexToReadCustomText = 0;
        File file = new File("src/txts/CustomText.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            customTextArraySize += 1;
            scanner.nextLine();
        }
        scanner = new Scanner(file);
        String[] customTextsArray = new String[customTextArraySize];

        for (int i = 0; i < customTextsArray.length; i++) {
            customTextsArray[i] = scanner.nextLine();
        }
        for (int i = 0; i < customTextsArray.length; i++) {
            scanner = new Scanner(customTextsArray[i]);
            scanner.useDelimiter(DELIMITER);
            if (scanner.next().equals(username)) {
                whichIndexToReadCustomText = i;
                i = customTextsArray.length + 1000;
            }
        }
        int whatLengthOfCustomTextArrayShouldBe = 0;
        scanner.next();//skips past username and password
        while (scanner.hasNext()) {
            whatLengthOfCustomTextArrayShouldBe++;
            scanner.next();
        }
        //System.out.println(customTextsArray[whichIndexToReadCustomText]);
        theCustomTextArray = new String[whatLengthOfCustomTextArrayShouldBe];
        scanner = new Scanner(customTextsArray[whichIndexToReadCustomText]);
        scanner.useDelimiter(DELIMITER);
        scanner.next();
        scanner.next();
        for (int i = 0; i < theCustomTextArray.length; i++) {
            theCustomTextArray[i] = scanner.next();
            //System.out.println(theCustomTextArray[i]);
        }
        customTextArrayLength = theCustomTextArray.length;
        scanner.close();
    }

    public String setUpCustomTextArea(String username) throws FileNotFoundException, IOException {
        int customTextArraySize = 0;
        int whichIndexToReadCustomText = 0;
        String whatToSetCustomTextAreaTo = "";
        File file = new File("src/txts/CustomText.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            customTextArraySize += 1;
            scanner.nextLine();
        }
        scanner = new Scanner(file);
        String[] customTextsArray = new String[customTextArraySize];

        for (int i = 0; i < customTextsArray.length; i++) {
            customTextsArray[i] = scanner.nextLine();
        }
        for (int i = 0; i < customTextsArray.length; i++) {
            scanner = new Scanner(customTextsArray[i]);
            scanner.useDelimiter(DELIMITER);
            if (scanner.next().equals(username)) {
                whichIndexToReadCustomText = i;
                i = customTextsArray.length + 1000;
            }
        }
        int whatLengthOfCustomTextArrayShouldBe = 0;
        scanner.next();//skips past username and password
        while (scanner.hasNext()) {
            whatLengthOfCustomTextArrayShouldBe++;
            scanner.next();
        }
        //System.out.println(customTextsArray[whichIndexToReadCustomText]);
        scanner = new Scanner(customTextsArray[whichIndexToReadCustomText]);
        scanner.useDelimiter(DELIMITER);
        scanner.next();
        scanner.next();
        for (int i = 0; i < whatLengthOfCustomTextArrayShouldBe; i++) {
            whatToSetCustomTextAreaTo += scanner.next().trim() + "\n";
        }
        scanner.close();
        return whatToSetCustomTextAreaTo;
    }

    public void saveCustomText(String customString, String username) throws IOException {
        int customTextArraySize = 0;
        int whichIndexToAddCustomTextTo = 0;
        String whatTextToSave = username + DELIMITER + "fakepassword" + DELIMITER;
        File file = new File("src/txts/CustomText.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            customTextArraySize += 1;
            scanner.nextLine();
        }
        scanner = new Scanner(file);
        String[] customTextsArray = new String[customTextArraySize];

        for (int i = 0; i < customTextsArray.length; i++) {
            customTextsArray[i] = scanner.nextLine();
        }
        for (int i = 0; i < customTextsArray.length; i++) {
            scanner = new Scanner(customTextsArray[i]);
            scanner.useDelimiter(DELIMITER);
            if (scanner.next().equals(username)) {
                whichIndexToAddCustomTextTo = i;
                i = customTextsArray.length + 1000;
            }

        }
        String path = file.getAbsolutePath().replaceAll("\\\\", "/");
        FileWriter fileWriter = new FileWriter(path);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("");
        scanner = new Scanner(customString);
        scanner.useDelimiter(DELIMITER);
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (!next.equals("")) {
            whatTextToSave += next + DELIMITER;
            }
        }
        if (!customString.equals("")) {
            customTextsArray[whichIndexToAddCustomTextTo] = whatTextToSave;
        }
        for (int i = 0; i < customTextsArray.length; i++) {
            printWriter.println(customTextsArray[i]);
        }
        printWriter.close();
        fileWriter.close();
        scanner.close();
        setUpCustomTextArray(username);
    }

}
