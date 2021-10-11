package cs20viewcontroller;

import cs20models.Timer;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Write methods in this class for displaying data in the DrawnView.
 *
 * You can use all the public instance variables you defined in AllModelsForView
 * and DrawnView as though they were part of this class! This is due to the
 * magic of subclassing (i.e. using the extends keyword).
 *
 * The methods for displaying data in the DrawnView are written as methods in
 * this class.
 *
 * Make sure to use these methods in the ViewUserActions class though, or else
 * they will be defined but never used!
 *
 * @author cheng
 */
public class ViewOutputs extends DrawnView {

    public int numOfTimesChecked = 0;
    public boolean getNewString = false;
    public String whatTheUserHasTypedSoFar = "";
    public boolean userTypedCorrectly;
    public int charsTypedSoFar = 0;
    public int numOfTimesInARowUserGotWrong = 0;
    private Border whiteBorder = javax.swing.BorderFactory.createLineBorder(Color.WHITE, 10);
    private Border blackBorder = javax.swing.BorderFactory.createLineBorder(Color.BLACK, 10);
    private Border greenBorder = javax.swing.BorderFactory.createLineBorder(Color.GREEN, 10);
    private Border pinkBorder = javax.swing.BorderFactory.createLineBorder(Color.getHSBColor(0.925f, 1, 1), 10);//pink
    private JLabel warningLabel = new JLabel("");
    public int errorsSoFar = 0;
    public int textDispaySize = 30;// max size 70 min size 15
    public int typedTextSize = 30;// max size 60 min size 15
    public int caretPostionHowMuchToAddOrSubstractBy = 0;//will allow caret to move to the next line
    public String fontType = "comic sans ms";
    SimpleAttributeSet attrSetColorTextPane = new SimpleAttributeSet();
    SimpleAttributeSet attrSetTextPaneAreaNotChanged = new SimpleAttributeSet();
    SimpleAttributeSet attrSetTextPaneAreaChanged = new SimpleAttributeSet();

    public String getUserTextFieldString() {
        return userTextField.getText();
    }

    public String getTextPaneAreaString() {
        return textPaneArea.getText();
    }

    public int getTextPaneAreaStringLength() {
        return getTextPaneAreaString().length();
    }

    public void clearUserTextArea() {
        userTextField.setText("");
    }

    public void checkIfUserIsSpammingKeys() throws BadLocationException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        if (numOfTimesInARowUserGotWrong > 10) {
            replaceTextPaneAreaString();
            userTextField.setFocusable(false);
            sound.playPopupSound();//plays beep sound
            showWarning("Too Many Errors! Try Again!");
        }
    }

    public void setAreaTextToNewSentence() throws FileNotFoundException, BadLocationException {
        String stringToSetTextPaneAreaTo = "";
        int typesSoFar = 6;//like how many random txts is there? lowercase? uppercase?

        while (stringToSetTextPaneAreaTo.length() < stringLengthSlider.getValue()) {//max is 1000 in settings
            int randomInt = (int) Math.floor(Math.random() * typesSoFar);
            //System.out.println(randomInt);
            if (sentenceRadioButton.isSelected() && randomInt == 0) {
                stringToSetTextPaneAreaTo += typingService.getRandomSentence();
            }
            if (uppercaseRadioButton.isSelected() && randomInt == 1) {
                stringToSetTextPaneAreaTo += typingService.getRandomUppercaseWord();
            }
            if (lowercaseRadioButton.isSelected() && randomInt == 2) {
                stringToSetTextPaneAreaTo += typingService.getRandomLowercaseWord();
            }
            if (numberRadioButton.isSelected() && randomInt == 3) {
                stringToSetTextPaneAreaTo += typingService.getRandomNumbers();
            }
            if (symbolRadioButton.isSelected() && randomInt == 4) {
                stringToSetTextPaneAreaTo += typingService.getRandomSymbols();
            }
            if (customTextRadioButton.isSelected() && randomInt == 5) {
                stringToSetTextPaneAreaTo += typingService.getRandomCustomText();
            }
            if (!sentenceRadioButton.isSelected() && !uppercaseRadioButton.isSelected() && !lowercaseRadioButton.isSelected() && !numberRadioButton.isSelected() && !symbolRadioButton.isSelected() && !customTextRadioButton.isSelected()) {//default string picker if nothing is picked
                stringToSetTextPaneAreaTo += typingService.getRandomSentence();
            }
            textPaneArea.setText(stringToSetTextPaneAreaTo.trim());
        }
    }

    public void replaceTextPaneAreaString() throws BadLocationException {

        try {
            setAreaTextToNewSentence();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
        }
        colorTextPane.setText("");
        clearUserTextArea();
        charsTypedSoFar = 0;
        whatTheUserHasTypedSoFar = "";
        numOfTimesChecked = 0;
        numOfTimesInARowUserGotWrong = 0;
        errorsSoFar = 0;
        getNewString = false;
        updateTextPaneAreaStyle();//will reset the colors of the text
        timer.resetTimer();
        timer.startTimer(false);
        if (textPaneArea.getText().length() < 3) {
            caretPostionHowMuchToAddOrSubstractBy = 0;
        } else {
            caretPostionHowMuchToAddOrSubstractBy = 3;
        }
        if (darkModeRadioButton.isSelected()) {
            lightModeOrDarkModeKeys(whiteBorder, Color.WHITE);
        } else {
            lightModeOrDarkModeKeys(blackBorder, Color.BLACK);
        }
        changeKeyColors(0);
    }

    public void updateColorsOfTypingProgram() {
        if (darkModeRadioButton.isSelected()) {
            trueAccuracyLable.setForeground(Color.WHITE);
            tipsLabel.setForeground(Color.WHITE);
            descriptionLabel.setForeground(Color.WHITE);
            keyboardVisualRadioButton.setForeground(Color.WHITE);
            keyboardKeysPanel.setBackground(Color.BLACK);
            keyboardPanel.setBackground(Color.BLACK);
            lightModeOrDarkModeKeys(whiteBorder, Color.WHITE);
            keyboardPanel.setBackground(Color.BLACK);
            wpmLabel.setForeground(Color.WHITE);
            tipsLabel.setForeground(Color.WHITE);
            sentenceRadioButton.setBackground(Color.BLACK);
            sentenceRadioButton.setForeground(Color.WHITE);
            uppercaseRadioButton.setBackground(Color.BLACK);
            uppercaseRadioButton.setForeground(Color.WHITE);
            lowercaseRadioButton.setBackground(Color.BLACK);
            lowercaseRadioButton.setForeground(Color.WHITE);
            symbolRadioButton.setBackground(Color.BLACK);
            symbolRadioButton.setForeground(Color.WHITE);
            numberRadioButton.setBackground(Color.BLACK);
            numberRadioButton.setForeground(Color.WHITE);
            getNewTextButton.setBackground(Color.BLACK);
            getNewTextButton.setForeground(Color.WHITE);
            settingsButton.setBackground(Color.BLACK);
            settingsButton.setForeground(Color.WHITE);
            lightModeRadioButton.setBackground(Color.BLACK);
            lightModeRadioButton.setForeground(Color.WHITE);
            darkModeRadioButton.setBackground(Color.BLACK);
            darkModeRadioButton.setForeground(Color.WHITE);
            typingPanel.setBackground(Color.BLACK);
            typingPanelHostPanel.setBackground(Color.BLACK);
            settingsPanel.setBackground(Color.BLACK);
            usernameLabel.setForeground(Color.WHITE);
            passwordLabel.setForeground(Color.WHITE);
            loginSignUpLabel.setForeground(Color.WHITE);
            loginSignUpPanel.setBackground(Color.BLACK);
            //don't need to set background for sliders they are transparent
            stringLengthSliderLabel.setForeground(Color.WHITE);
            typingSoundsVolumeSliderLabel.setForeground(Color.WHITE);
            typedTextSizeSliderLabel.setForeground(Color.WHITE);
            textDisplaySizeSliderLabel.setForeground(Color.WHITE);
            errorSoundsVolumeSliderLabel.setForeground(Color.WHITE);
            lofiStreamVolumeSliderLabel.setForeground(Color.WHITE);
            streamLofiButton.setBackground(Color.BLACK);
            streamLofiButton.setForeground(Color.WHITE);
            loginButton.setBackground(Color.BLACK);
            loginButton.setForeground(Color.WHITE);
            signUpButton.setBackground(Color.BLACK);
            signUpButton.setForeground(Color.WHITE);
            usernameField.setBackground(Color.BLACK);
            usernameField.setForeground(Color.WHITE);
            passwordField.setBackground(Color.BLACK);
            passwordField.setForeground(Color.WHITE);
            loginSignUpButton.setBackground(Color.BLACK);
            loginSignUpButton.setForeground(Color.WHITE);
            buttonGroupPanel.setBackground(Color.BLACK);
            logoutPanel.setBackground(Color.BLACK);
            shapePanel.setBackground(Color.BLACK);
            logoutButton.setBackground(Color.BLACK);
            logoutButton.setForeground(Color.WHITE);
            savePreferencesButton.setBackground(Color.BLACK);
            savePreferencesButton.setForeground(Color.WHITE);
            resetSettingsButton.setBackground(Color.BLACK);
            resetSettingsButton.setForeground(Color.WHITE);
            clickAboveLabel.setForeground(Color.GREEN);
            customTextRadioButton.setForeground(Color.WHITE);
            customTextPanel.setBackground(Color.BLACK);
            customTextLabel.setForeground(Color.WHITE);
            customTextTextArea.setBackground(Color.BLACK);
            customTextTextArea.setForeground(Color.WHITE);
            customTextButton.setBackground(Color.BLACK);
            customTextButton.setForeground(Color.WHITE);
            customTextPanelButton.setBackground(Color.BLACK);
            customTextPanelButton.setForeground(Color.WHITE);
            updateThePaneTextColorsForTypingOnly();

            UIDefaults defaults = new UIDefaults();//this will change the color af backgrounds since colourTextPane.setBackground(Color.CYAN); does not work on its own
            defaults.put("TextPane.background", new ColorUIResource(Color.BLACK));
            defaults.put("TextPane[Enabled].backgroundPainter", Color.BLACK);
            textPaneArea.putClientProperty("Nimbus.Overrides", defaults);
            textPaneArea.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
            textPaneArea.setBackground(Color.BLACK);

            colorTextPane.putClientProperty("Nimbus.Overrides", defaults);
            colorTextPane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
            colorTextPane.setBackground(Color.BLACK);
            getContentPane().setBackground(Color.BLACK);

            updateTextPaneAreaStyle();
            changeKeyColors(0);
            if (whatTheUserHasTypedSoFar.length() > 0) {
                updateTheColorTextPaneColors();
            }

        } else if (lightModeRadioButton.isSelected()) {
            trueAccuracyLable.setForeground(Color.BLACK);
            tipsLabel.setForeground(Color.BLACK);
            descriptionLabel.setForeground(Color.BLACK);
            keyboardVisualRadioButton.setForeground(Color.BLACK);
            keyboardKeysPanel.setBackground(Color.WHITE);
            keyboardPanel.setBackground(Color.WHITE);
            lightModeOrDarkModeKeys(blackBorder, Color.BLACK);
            wpmLabel.setForeground(Color.BLACK);
            tipsLabel.setForeground(Color.BLACK);
            sentenceRadioButton.setBackground(Color.WHITE);
            sentenceRadioButton.setForeground(Color.BLACK);
            uppercaseRadioButton.setBackground(Color.WHITE);
            uppercaseRadioButton.setForeground(Color.BLACK);
            lowercaseRadioButton.setBackground(Color.WHITE);
            lowercaseRadioButton.setForeground(Color.BLACK);
            symbolRadioButton.setBackground(Color.WHITE);
            symbolRadioButton.setForeground(Color.BLACK);
            numberRadioButton.setBackground(Color.WHITE);
            numberRadioButton.setForeground(Color.BLACK);
            getNewTextButton.setBackground(Color.WHITE);
            getNewTextButton.setForeground(Color.BLACK);
            settingsButton.setBackground(Color.WHITE);
            settingsButton.setForeground(Color.BLACK);
            lightModeRadioButton.setBackground(Color.WHITE);
            lightModeRadioButton.setForeground(Color.BLACK);
            darkModeRadioButton.setBackground(Color.WHITE);
            darkModeRadioButton.setForeground(Color.BLACK);
            typingPanel.setBackground(Color.WHITE);
            typingPanelHostPanel.setBackground(Color.WHITE);
            settingsPanel.setBackground(Color.WHITE);
            usernameLabel.setForeground(Color.BLACK);
            passwordLabel.setForeground(Color.BLACK);
            loginSignUpLabel.setForeground(Color.BLACK);
            loginButton.setBackground(Color.WHITE);
            loginButton.setForeground(Color.BLACK);
            signUpButton.setBackground(Color.WHITE);
            signUpButton.setForeground(Color.BLACK);
            loginSignUpPanel.setBackground(Color.WHITE);
            usernameField.setBackground(Color.WHITE);
            usernameField.setForeground(Color.BLACK);
            passwordField.setBackground(Color.WHITE);
            passwordField.setForeground(Color.BLACK);
            loginSignUpButton.setBackground(Color.WHITE);
            loginSignUpButton.setForeground(Color.BLACK);
            resetSettingsButton.setBackground(Color.WHITE);
            resetSettingsButton.setForeground(Color.BLACK);
            //don't need to set background for sliders they are transparent
            stringLengthSliderLabel.setForeground(Color.BLACK);
            typingSoundsVolumeSliderLabel.setForeground(Color.BLACK);
            typedTextSizeSliderLabel.setForeground(Color.BLACK);
            textDisplaySizeSliderLabel.setForeground(Color.BLACK);
            errorSoundsVolumeSliderLabel.setForeground(Color.BLACK);
            lofiStreamVolumeSliderLabel.setForeground(Color.BLACK);
            streamLofiButton.setBackground(Color.WHITE);
            streamLofiButton.setForeground(Color.BLACK);
            buttonGroupPanel.setBackground(Color.WHITE);
            logoutPanel.setBackground(Color.WHITE);
            logoutButton.setBackground(Color.WHITE);
            logoutButton.setForeground(Color.BLACK);
            savePreferencesButton.setBackground(Color.WHITE);
            savePreferencesButton.setForeground(Color.BLACK);
            shapePanel.setBackground(Color.WHITE);
            clickAboveLabel.setForeground(Color.RED);
            customTextRadioButton.setForeground(Color.BLACK);
            customTextPanel.setBackground(Color.WHITE);
            customTextLabel.setForeground(Color.BLACK);
            customTextTextArea.setBackground(Color.WHITE);
            customTextTextArea.setForeground(Color.BLACK);
            customTextButton.setBackground(Color.WHITE);
            customTextButton.setForeground(Color.BLACK);
            customTextPanelButton.setBackground(Color.WHITE);
            customTextPanelButton.setForeground(Color.BLACK);
            keyboardPanel.setBackground(Color.WHITE);
            updateThePaneTextColorsForTypingOnly();

            UIDefaults defaults = new UIDefaults();//this will change the color af backgrounds since colourTextPane.setBackground(Color.CYAN); does not work on its own
            defaults.put("TextPane.background", new ColorUIResource(Color.WHITE));
            defaults.put("TextPane[Enabled].backgroundPainter", Color.WHITE);
            textPaneArea.putClientProperty("Nimbus.Overrides", defaults);
            textPaneArea.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
            textPaneArea.setBackground(Color.WHITE);

            colorTextPane.putClientProperty("Nimbus.Overrides", defaults);
            colorTextPane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
            colorTextPane.setBackground(Color.WHITE);
            getContentPane().setBackground(Color.WHITE);

            updateTextPaneAreaStyle();

            if (whatTheUserHasTypedSoFar.length() > 0) {
                updateTheColorTextPaneColors();
            }
            changeKeyColors(0);
        }
    }

    public void showWarning(String warningMessage) {
        warningLabel.setText(warningMessage);
        JOptionPane.showMessageDialog(null, warningLabel, "Warning!", JOptionPane.WARNING_MESSAGE);
    }

    public void updateThePaneTextColorsForTypingOnly() {
        StyleConstants.setFontSize(attrSetColorTextPane, typedTextSize);
        StyleConstants.setFontSize(attrSetTextPaneAreaNotChanged, textDispaySize);
        if (darkModeRadioButton.isSelected()) {
            StyleConstants.setForeground(attrSetTextPaneAreaNotChanged, Color.WHITE);
            textPaneArea.setForeground(Color.WHITE);
            StyleConstants.setBackground(attrSetTextPaneAreaChanged, Color.DARK_GRAY);
            colorTextPane.setForeground(Color.WHITE);

            if (userTypedCorrectly) {
                StyleConstants.setForeground(attrSetColorTextPane, Color.WHITE);
                StyleConstants.setBackground(attrSetColorTextPane, Color.getHSBColor(0.3027f, 1f, 0.4f));//dark green
            } else {
                StyleConstants.setForeground(attrSetColorTextPane, Color.WHITE);
                StyleConstants.setBackground(attrSetColorTextPane, Color.getHSBColor(1, 1f, 0.5f));//blood red
            }

        } else if (lightModeRadioButton.isSelected()) {
            StyleConstants.setForeground(attrSetTextPaneAreaNotChanged, Color.BLACK);
            textPaneArea.setForeground(Color.BLACK);
            StyleConstants.setBackground(attrSetTextPaneAreaChanged, Color.YELLOW);
            colorTextPane.setForeground(Color.BLACK);

            if (userTypedCorrectly) {
                StyleConstants.setForeground(attrSetColorTextPane, Color.BLACK);
                StyleConstants.setBackground(attrSetColorTextPane, Color.GREEN);
            } else {
                StyleConstants.setForeground(attrSetColorTextPane, Color.BLACK);
                StyleConstants.setBackground(attrSetColorTextPane, Color.getHSBColor(1, 0.5f, 1));//light red
            }

        }
    }

    public void updateTextPaneAreaStyle() {
        StyleConstants.setFontSize(attrSetTextPaneAreaChanged, textDispaySize);
        String textPaneString = textPaneArea.getText();
        textPaneArea.setText("");
        String textPaneStringToChange = textPaneString.substring(0, numOfTimesChecked);
        String textPaneStringToStayTheSame = textPaneString.substring(numOfTimesChecked);

        StyleConstants.setBold(attrSetTextPaneAreaChanged, true);
        updateThePaneTextColorsForTypingOnly();
        textPaneArea.setCharacterAttributes(attrSetTextPaneAreaChanged, true);

        Document doc = textPaneArea.getStyledDocument();

        StyleConstants.setFontFamily(attrSetTextPaneAreaChanged, fontType);

        try {
            doc.insertString(doc.getLength(), "" + textPaneStringToChange, attrSetTextPaneAreaChanged);
        } catch (BadLocationException ex) {
            Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateThePaneTextColorsForTypingOnly();
        StyleConstants.setBold(attrSetTextPaneAreaNotChanged, true);
        textPaneArea.setCharacterAttributes(attrSetTextPaneAreaNotChanged, true);
        doc = textPaneArea.getStyledDocument();

        StyleConstants.setBold(attrSetTextPaneAreaNotChanged, true);
        StyleConstants.setFontFamily(attrSetTextPaneAreaNotChanged, fontType);

        try {
            doc.insertString(doc.getLength(), "" + textPaneStringToStayTheSame, attrSetTextPaneAreaNotChanged);
        } catch (BadLocationException ex) {
            Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
        }

        caretPostionHowMuchToAddOrSubstractBy = Math.abs(caretPostionHowMuchToAddOrSubstractBy);// will stop the program from trying to set caret to the length -3 which will produce an error.

        textPaneArea.setCaretPosition(whatTheUserHasTypedSoFar.length() + caretPostionHowMuchToAddOrSubstractBy);//set the caret position //there is an error here somewhere // error caused by backspacing before getting new string
        //System.out.println(textPaneArea.getCaretPosition());

        //System.out.println(textPaneStringToChange + "\n" + textPaneStringToStayTheSame);
    }

    public void didUserTypeCorrectly() {
        StyleConstants.setFontFamily(attrSetColorTextPane, fontType);
        StyleConstants.setFontSize(attrSetColorTextPane, typedTextSize);
        if (userTypedCorrectly) {//checks if the user typed incorrectly, I will use this if statement to add up how many errors there are.
            //System.out.println("User Is correct");
            StyleConstants.setBold(attrSetColorTextPane, true);
            colorTextPane.setCharacterAttributes(attrSetColorTextPane, true);

            Document doc = colorTextPane.getStyledDocument();

            updateThePaneTextColorsForTypingOnly();

            try {
                doc.insertString(doc.getLength(), "" + userTextField.getText().charAt(numOfTimesChecked), attrSetColorTextPane);
            } catch (BadLocationException ex) {
                Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            StyleConstants.setBold(attrSetColorTextPane, true);
            colorTextPane.setCharacterAttributes(attrSetColorTextPane, true);

            Document doc = colorTextPane.getStyledDocument();

            updateThePaneTextColorsForTypingOnly();

            try {
                doc.insertString(doc.getLength(), "" + userTextField.getText().charAt(numOfTimesChecked), attrSetColorTextPane);
            } catch (BadLocationException ex) {
                Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
            }

            //System.out.println("User Is wrong");
        }
    }

    public void checkWhatUserTyped(String arg0String) throws LineUnavailableException, UnsupportedAudioFileException, IOException {

        String stringThatIsReturned = typingService.userDidError(getTextPaneAreaStringLength(), getTextPaneAreaString(), "" + getUserTextFieldString().charAt(numOfTimesChecked), numOfTimesChecked);

        charsTypedSoFar += 1;
        //System.out.println(whatTheUserHasTypedSoFar);
        if (stringThatIsReturned.equals("falseGetNewString")) {
            getNewString = true;
            userTypedCorrectly = true;
            numOfTimesInARowUserGotWrong = 0;
            sound.playKeyBoardClickSound();//will play clicking sound

        } else if (stringThatIsReturned.equals("trueGetNewString")) {
            getNewString = true;
            userTypedCorrectly = false;
            numOfTimesInARowUserGotWrong += 1;//this will tell me is user is just spamming keys.
            errorsSoFar += 1;
            sound.playErrorSound();//plays errorSound if user types wrongly

        } else if (stringThatIsReturned.equals("true")) {
            userTypedCorrectly = false;
            numOfTimesInARowUserGotWrong += 1;//this will tell me is user is just spamming keys.
            errorsSoFar += 1;
            sound.playErrorSound();//plays errorSound if user types wrongly

        } else {
            numOfTimesInARowUserGotWrong = 0;
            userTypedCorrectly = true;
            sound.playKeyBoardClickSound();//will play clicking sound

        }

        didUserTypeCorrectly();

        numOfTimesChecked++;

        try {
            checkIfUserIsSpammingKeys();
        } catch (BadLocationException ex) {
            Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateThePaneTextColorsForBackspaceOnly(int i) {
        StyleConstants.setFontFamily(attrSetColorTextPane, fontType);
        StyleConstants.setFontSize(attrSetColorTextPane, typedTextSize);
        if (darkModeRadioButton.isSelected()) {
            if (whatTheUserHasTypedSoFar.charAt(i) == getTextPaneAreaString().charAt(i)) {
                StyleConstants.setForeground(attrSetColorTextPane, Color.WHITE);
                StyleConstants.setBackground(attrSetColorTextPane, Color.getHSBColor(0.3027f, 1f, 0.4f));//dark green

            } else {
                StyleConstants.setForeground(attrSetColorTextPane, Color.WHITE);
                StyleConstants.setBackground(attrSetColorTextPane, Color.getHSBColor(1, 1, 0.5f));//blood red
            }
        } else if (lightModeRadioButton.isSelected()) {
            if (whatTheUserHasTypedSoFar.charAt(i) == getTextPaneAreaString().charAt(i)) {

                StyleConstants.setForeground(attrSetColorTextPane, Color.BLACK);
                StyleConstants.setBackground(attrSetColorTextPane, Color.GREEN);

            } else {
                StyleConstants.setForeground(attrSetColorTextPane, Color.BLACK);
                StyleConstants.setBackground(attrSetColorTextPane, Color.getHSBColor(1, 0.5f, 1));//light red

            }
        }
    }

    public void updateTheColorTextPaneColors() {
        StyleConstants.setFontFamily(attrSetColorTextPane, fontType);
        StyleConstants.setFontSize(attrSetColorTextPane, typedTextSize);
        colorTextPane.setText("");
        for (int i = 0; i < whatTheUserHasTypedSoFar.length(); i++) {//this code here will change the text colors of colorTextPane base on the color mode selected
            if (whatTheUserHasTypedSoFar.charAt(i) == getTextPaneAreaString().charAt(i)) {
                StyleConstants.setBold(attrSetColorTextPane, true);
                colorTextPane.setCharacterAttributes(attrSetColorTextPane, true);

                Document doc = colorTextPane.getStyledDocument();

                updateThePaneTextColorsForBackspaceOnly(i);

                try {
                    doc.insertString(doc.getLength(), "" + whatTheUserHasTypedSoFar.charAt(i), attrSetColorTextPane);
                } catch (BadLocationException ex) {
                    Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                StyleConstants.setFontFamily(attrSetColorTextPane, fontType);
                StyleConstants.setBold(attrSetColorTextPane, true);
                colorTextPane.setCharacterAttributes(attrSetColorTextPane, true);

                Document doc = colorTextPane.getStyledDocument();

                updateThePaneTextColorsForBackspaceOnly(i);

                try {
                    doc.insertString(doc.getLength(), "" + whatTheUserHasTypedSoFar.charAt(i), attrSetColorTextPane);
                } catch (BadLocationException ex) {
                    Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void setUpAccountPreferences(String accountPrefrences) {//sets up prefrences and their visuals when you login
        Scanner scanner = new Scanner(accountPrefrences);
        scanner.useDelimiter(accounts.getDelimiter());
        scanner.next();//skips username
        scanner.next();//skips password

        if (scanner.next().equals("true")) {
            sentenceRadioButton.setSelected(true);
        } else {
            sentenceRadioButton.setSelected(false);
        }
        if (scanner.next().equals("true")) {
            lowercaseRadioButton.setSelected(true);
        } else {
            lowercaseRadioButton.setSelected(false);
        }
        if (scanner.next().equals("true")) {
            numberRadioButton.setSelected(true);
        } else {
            numberRadioButton.setSelected(false);
        }
        if (scanner.next().equals("true")) {
            symbolRadioButton.setSelected(true);
        } else {
            symbolRadioButton.setSelected(false);
        }
        if (scanner.next().equals("true")) {
            uppercaseRadioButton.setSelected(true);
        } else {
            uppercaseRadioButton.setSelected(false);
        }
        if (scanner.next().equals("true")) {
            darkModeRadioButton.setSelected(true);
            lightModeRadioButton.setSelected(false);
        } else {
            darkModeRadioButton.setSelected(false);
            lightModeRadioButton.setSelected(true);
        }
        if (scanner.next().equals("true")) {
            if (!streamLofiButton.isSelected()) {
                streamLofiButton.setSelected(true);
                streamLofiButton.setText("Stop Streaming Lofi");
                streamLofiButton.setText("Stop Streaming Lofi");
                sound.setStopStreaming(false);

                try {
                    sound.playRandomLofiTrack();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            streamLofiButton.setSelected(false);
            sound.setStopStreaming(true);
            streamLofiButton.setText("Stream Lofi");
        }
        stringLengthSlider.setValue(Integer.parseInt(scanner.next()));
        typingSoundsVolumeSlider.setValue(Integer.parseInt(scanner.next()));
        errorSoundsVolumeSlider.setValue(Integer.parseInt(scanner.next()));
        lofiStreamVolumeSlider.setValue(Integer.parseInt(scanner.next()));
        textDisplaySizeSlider.setValue(Integer.parseInt(scanner.next()));
        typedTextSizeSlider.setValue(Integer.parseInt(scanner.next()));
        if (scanner.next().equals("true")) {
            customTextRadioButton.setSelected(true);
        } else {
            customTextRadioButton.setSelected(false);
        }
        if (scanner.next().equals("true")) {
            keyboardPanel.setVisible(true);
            keyboardVisualRadioButton.setSelected(true);
        } else {
            keyboardPanel.setVisible(false);
            keyboardVisualRadioButton.setSelected(false);
        }
        scanner.close();
        updateColorsOfTypingProgram();
        stringLengthSliderLabel.setText("Approximate text length to generate: " + stringLengthSlider.getValue());//sets the stringLength lable to its default value
        typingSoundsVolumeSliderLabel.setText("Typing sounds volume: " + Math.round(100 * (((double) typingSoundsVolumeSlider.getValue() + 80) / 86)));//sets volume lable to its default value
        errorSoundsVolumeSliderLabel.setText("Error sounds volume: " + Math.round(100 * (((double) errorSoundsVolumeSlider.getValue() + 80) / 86)));//sets volume lable to its default value
        lofiStreamVolumeSliderLabel.setText("Lofi stream volume: " + Math.round(100 * (((double) lofiStreamVolumeSlider.getValue() + 80) / 86)));//sets volume lable to its default value
        textDisplaySizeSliderLabel.setText("Text Display Size: " + textDisplaySizeSlider.getValue());
        typedTextSizeSliderLabel.setText("Text Display Size: " + typedTextSizeSlider.getValue());
        try {
            replaceTextPaneAreaString();//proper method to use to set up new text as it resets other stuff as well to make program work
        } catch (BadLocationException ex) {
            Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeKeyBorderColorsBasedOnLightModeDarkMode() {
        if (darkModeRadioButton.isSelected()) {
            lightModeOrDarkModeKeys(whiteBorder, Color.WHITE);
        } else {
            lightModeOrDarkModeKeys(blackBorder, Color.BLACK);
        }
    }

    public void changeKeyColors(int howMuchToAddOrSubtract) {
        changeKeyBorderColorsBasedOnLightModeDarkMode();
        String character = "" + textPaneArea.getText().charAt(whatTheUserHasTypedSoFar.length() + howMuchToAddOrSubtract);

        Pattern pattern = Pattern.compile("[QAZWSXEDCRFVTGB~!@#$%]");
        Matcher matches = pattern.matcher(character);

        boolean doesItMatch = matches.matches();
        if (doesItMatch) {
            rightPinkiePanel.setBackground(Color.getHSBColor(0.925f, 1, 1));
            labelRightShift.setBorder(pinkBorder);
        }
        pattern = Pattern.compile("[YHNUJMIK<OL>P:?{\"}|+_)(*&^)]");
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            leftPinkiePanel.setBackground(Color.GREEN);
            labelLeftShift.setBorder(greenBorder);
        }
        pattern = Pattern.compile("[zZaAqQ1!`~]");
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            leftPinkiePanel.setBackground(Color.GREEN);
        }
        pattern = Pattern.compile("[xXsSwW2@]");
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            leftRingPanel.setBackground(Color.GREEN);
        }
        pattern = Pattern.compile("[cCdDeE3#]");
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            leftMiddlePanel.setBackground(Color.GREEN);
        }
        pattern = Pattern.compile("[vVbBfFgGrRtT4$5%]");
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            leftPointerPanel.setBackground(Color.GREEN);
        }
        pattern = Pattern.compile("[nNhHyY67&uUjJmM^]");
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            rightPointerPanel.setBackground(Color.getHSBColor(0.925f, 1, 1));
        }
        pattern = Pattern.compile("[<kK,iI8*]");
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            rightMiddlePanel.setBackground(Color.getHSBColor(0.925f, 1, 1));
        }
        pattern = Pattern.compile("[9(OoLl.>]");
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            rightRingPanel.setBackground(Color.getHSBColor(0.925f, 1, 1));
        }
        pattern = Pattern.compile("[/?;:pP0)'\"\\|]");//[]{}_=+
        matches = pattern.matcher(character);
        doesItMatch = matches.matches();
        if (doesItMatch) {
            rightPinkiePanel.setBackground(Color.getHSBColor(0.925f, 1, 1));
        }

        if (character.equals("[") || character.equals("]") || character.equals("{") || character.equals("}") || character.equals("_") || character.equals("+") || character.equals("=") || character.equals("-")) {
            rightPinkiePanel.setBackground(Color.getHSBColor(0.925f, 1, 1));
        }

        if (character.toLowerCase().equals("q")) {
            labelQ.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("w")) {
            labelW.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("e")) {
            labelE.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("r")) {
            labelR.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("t")) {
            labelT.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("a")) {
            labelA.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("s")) {
            labelS.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("d")) {
            labelD.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("f")) {
            labelF.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("g")) {
            labelG.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("z")) {
            labelZ.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("x")) {
            labelX.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("c")) {
            labelC.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("v")) {
            labelV.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("b")) {
            labelB.setBorder(greenBorder);
        }
        if (character.equals(" ")) {
            labelSpace.setBorder(greenBorder);
            leftThumbPanel.setBackground(Color.GREEN);
            rightThumbPanel.setBackground(Color.getHSBColor(0.925f, 1, 1));
        }
        if (character.toLowerCase().equals("y")) {
            labelY.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("u")) {
            labelU.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("i")) {
            labelI.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("o")) {
            labelO.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("p")) {
            labelP.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("h")) {
            labelH.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("j")) {
            labelJ.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("k")) {
            labelK.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("l")) {
            labelL.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("n")) {
            labelN.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("m")) {
            labelM.setBorder(pinkBorder);
        }

        if (character.toLowerCase().equals("`") || character.toLowerCase().equals("~")) {
            labelBefore1.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("1") || character.toLowerCase().equals("!")) {
            label1.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("2") || character.toLowerCase().equals("@")) {
            label2.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("3") || character.toLowerCase().equals("#")) {
            label3.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("4") || character.toLowerCase().equals("$")) {
            label4.setBorder(greenBorder);
        }
        if (character.toLowerCase().equals("%") || character.toLowerCase().equals("5")) {
            label5.setBorder(greenBorder);
        }

        if (character.toLowerCase().equals("6") || character.toLowerCase().equals("^")) {
            label6.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("7") || character.toLowerCase().equals("&")) {
            label7.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("8") || character.toLowerCase().equals("*")) {
            label8.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("9") || character.toLowerCase().equals("(")) {
            label9.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("0") || character.toLowerCase().equals(")")) {
            label0.setBorder(pinkBorder);
        }

        if (character.toLowerCase().equals("-") || character.toLowerCase().equals("_")) {
            labelMinus.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("=") || character.toLowerCase().equals("+")) {
            labelEquals.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("[") || character.toLowerCase().equals("{")) {
            labelOpenBracket.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("]") || character.toLowerCase().equals("}")) {
            labelCloseBracket.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("\\") || character.toLowerCase().equals("|")) {
            labelBackSlash.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals(";") || character.toLowerCase().equals(":")) {
            labelSemicolon.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("'") || character.toLowerCase().equals("\"")) {
            labelApostrophe.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals(",") || character.toLowerCase().equals("<")) {
            labelComma.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals(">") || character.toLowerCase().equals(".")) {
            labelPeriod.setBorder(pinkBorder);
        }
        if (character.toLowerCase().equals("/") || character.toLowerCase().equals("?")) {
            labelSlash.setBorder(pinkBorder);
        }

    }

    public void lightModeOrDarkModeKeys(Border border, Color color) {
        leftPalmPanel.setBackground(color);
        leftPinkiePanel.setBackground(color);
        leftRingPanel.setBackground(color);
        leftMiddlePanel.setBackground(color);
        leftPointerPanel.setBackground(color);
        leftThumbPanel.setBackground(color);
        rightPalmPanel.setBackground(color);
        rightPinkiePanel.setBackground(color);
        rightRingPanel.setBackground(color);
        rightMiddlePanel.setBackground(color);
        rightPointerPanel.setBackground(color);
        rightThumbPanel.setBackground(color);

        labelLeftCtrl.setBorder(border);
        labelSpace.setBorder(border);
        labelRightCtrl.setBorder(border);
        labelWindows.setBorder(border);
        labelFn.setBorder(border);
        labelEnter.setBorder(border);
        labelRightAlt.setBorder(border);
        labelLines.setBorder(border);
        labelLeftAlt.setBorder(border);
        labelV.setBorder(border);
        labelB.setBorder(border);
        labelN.setBorder(border);
        labelM.setBorder(border);
        labelComma.setBorder(border);
        labelPeriod.setBorder(border);
        labelF.setBorder(border);
        labelRightShift.setBorder(border);
        labelApostrophe.setBorder(border);
        labelSemicolon.setBorder(border);
        labelL.setBorder(border);
        labelK.setBorder(border);
        labelJ.setBorder(border);
        labelH.setBorder(border);
        labelG.setBorder(border);
        labelC.setBorder(border);
        labelX.setBorder(border);
        labelZ.setBorder(border);
        labelD.setBorder(border);
        labelS.setBorder(border);
        labelBackSlash.setBorder(border);
        labelLeftShift.setBorder(border);
        labelW.setBorder(border);
        labelE.setBorder(border);
        labelR.setBorder(border);
        labelT.setBorder(border);
        labelY.setBorder(border);
        labelU.setBorder(border);
        labelI.setBorder(border);
        labelO.setBorder(border);
        labelP.setBorder(border);
        labelOpenBracket.setBorder(border);
        labelCloseBracket.setBorder(border);
        labelA.setBorder(border);
        labelBefore1.setBorder(border);
        label2.setBorder(border);
        label3.setBorder(border);
        label4.setBorder(border);
        label5.setBorder(border);
        label6.setBorder(border);
        label7.setBorder(border);
        label8.setBorder(border);
        label9.setBorder(border);
        label0.setBorder(border);
        labelMinus.setBorder(border);
        labelEquals.setBorder(border);
        labelBackspace.setBorder(border);
        label1.setBorder(border);
        labelQ.setBorder(border);
        labelTab.setBorder(border);
        labelCaps.setBorder(border);
        labelSlash.setBorder(border);

        labelLeftCtrl.setForeground(color);
        labelSpace.setForeground(color);
        labelRightCtrl.setForeground(color);
        labelWindows.setForeground(color);
        labelFn.setForeground(color);
        labelEnter.setForeground(color);
        labelRightAlt.setForeground(color);
        labelLines.setForeground(color);
        labelLeftAlt.setForeground(color);
        labelV.setForeground(color);
        labelB.setForeground(color);
        labelN.setForeground(color);
        labelM.setForeground(color);
        labelComma.setForeground(color);
        labelPeriod.setForeground(color);
        labelF.setForeground(color);
        labelRightShift.setForeground(color);
        labelApostrophe.setForeground(color);
        labelSemicolon.setForeground(color);
        labelL.setForeground(color);
        labelK.setForeground(color);
        labelJ.setForeground(color);
        labelH.setForeground(color);
        labelG.setForeground(color);
        labelC.setForeground(color);
        labelX.setForeground(color);
        labelZ.setForeground(color);
        labelD.setForeground(color);
        labelS.setForeground(color);
        labelBackSlash.setForeground(color);
        labelLeftShift.setForeground(color);
        labelW.setForeground(color);
        labelE.setForeground(color);
        labelR.setForeground(color);
        labelT.setForeground(color);
        labelY.setForeground(color);
        labelU.setForeground(color);
        labelI.setForeground(color);
        labelO.setForeground(color);
        labelP.setForeground(color);
        labelOpenBracket.setForeground(color);
        labelCloseBracket.setForeground(color);
        labelA.setForeground(color);
        labelBefore1.setForeground(color);
        label2.setForeground(color);
        label3.setForeground(color);
        label4.setForeground(color);
        label5.setForeground(color);
        label6.setForeground(color);
        label7.setForeground(color);
        label8.setForeground(color);
        label9.setForeground(color);
        label0.setForeground(color);
        labelMinus.setForeground(color);
        labelEquals.setForeground(color);
        labelBackspace.setForeground(color);
        label1.setForeground(color);
        labelQ.setForeground(color);
        labelTab.setForeground(color);
        labelCaps.setForeground(color);
        labelSlash.setForeground(color);
    }

    public void typingProgramSetup() {//sets up typing program visuals
        userTextField.setTransferHandler(null);//user cannot copy and paste into userTextField to cheat
        userTextField.setOpaque(false);//gets rid of opaque background
        userTextField.setHighlighter(null);//makes it so user cannot highlight
        userTextField.setFocusTraversalKeysEnabled(false);//stops tab from switching textField/Panes/Areas
        colorTextPane.setFocusTraversalKeysEnabled(false);//stops tab from switching textField/Panes/Areas
        textPaneArea.setFocusTraversalKeysEnabled(false);//stops tab from switching textField/Panes/Areas
        colorTextPane.setFocusable(false);//takes focus away at first
        textPaneArea.setFocusable(false);//user cannot copy/paste/edit this Area
        timer = new Timer();//creates new timer
        thread = new Thread(timer);
        thread.start();//timer started
        this.setExtendedState(this.MAXIMIZED_BOTH);// sets to full screen
        userTextField.setFocusable(false);//needed for clicking of colorTextPane to work
        try {

            typingService.setUpArrays();//sets up the arrays for the textPaneArea
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
        }

        userTextField.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 0));//makes it so that text does not appear as it would be confusing
        try {
            setAreaTextToNewSentence();//will get the initial dispaly of textArea
        } catch (FileNotFoundException | BadLocationException ex) {
            Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
        }

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                return e.getKeyCode() == KeyEvent.VK_F10;//pressing this button breaks the program. This stops this key from being used
            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                return e.getKeyCode() == KeyEvent.VK_END;//pressing this button plus shift breaks the program. This stops this key from being used
            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                return e.getKeyCode() == KeyEvent.VK_HOME;//pressing this button plus shift breaks the program. This stops this key from being used
            }
        });
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                return e.getKeyCode() == KeyEvent.VK_ALT;//pressing this button breaks the program. This stops this key from being used
            }
        });
        userTextField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, KeyEvent.CTRL_MASK), "none");//prevents typing bug when ctrl backspace pressed. 

        stringLengthSliderLabel.setText("Approximate text length to generate: " + stringLengthSlider.getValue());//sets the stringLength lable to its default value
        typingSoundsVolumeSliderLabel.setText("Typing sounds volume: " + Math.round(100 * (((double) typingSoundsVolumeSlider.getValue() + 80) / 86)));//sets volume lable to its default value
        errorSoundsVolumeSliderLabel.setText("Error sounds volume: " + Math.round(100 * (((double) errorSoundsVolumeSlider.getValue() + 80) / 86)));//sets volume lable to its default value
        lofiStreamVolumeSliderLabel.setText("Lofi stream volume: " + Math.round(100 * (((double) lofiStreamVolumeSlider.getValue() + 80) / 86)));//sets volume lable to its default value
        textDisplaySizeSliderLabel.setText("Text Display Size: " + textDisplaySizeSlider.getValue());
        typedTextSizeSliderLabel.setText("Text Display Size: " + typedTextSizeSlider.getValue());

        settingsPanel.setVisible(false);//will hide settings panel at first
        loginSignUpPanel.setVisible(false);//will hide signUpLongin panel at first
        logoutPanel.setVisible(false);//will hide logoutPanel panel at first
        customTextPanel.setVisible(false);//will hide customTextPanel panel at first
        keyboardVisualRadioButton.setSelected(true);

        darkModeRadioButton.setSelected(true);

        DefaultCaret caret = (DefaultCaret) textPaneArea.getCaret();//this will stop textPaneArea from automatically scrolling all the way to the bottom
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);//this will stop textPaneArea from automatically scrolling all the way to the bottom

        try {
            sound.setUpLofiArray();//sets up array so its is only set up once and not multiple times everytime you run the method
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
        }

        updateColorsOfTypingProgram();//will set colors of the typing program
        changeKeyColors(0);
    }

    public void backspace() {
        StyleConstants.setFontSize(attrSetColorTextPane, typedTextSize);
        numOfTimesChecked--;
        int i = 0;//for for loop
        userTextField.setText(userTextField.getText().substring(0, userTextField.getText().length() - 1));
        //System.out.println("backspace used");
        colorTextPane.setText("");
        for (i = 0; i < whatTheUserHasTypedSoFar.length() - 1; i++) {//this code here will delete one character from the string. substring does not work as it will set all the letters to one color
            if (whatTheUserHasTypedSoFar.charAt(i) == getTextPaneAreaString().charAt(i)) {
                StyleConstants.setBold(attrSetColorTextPane, true);
                colorTextPane.setCharacterAttributes(attrSetColorTextPane, true);

                Document doc = colorTextPane.getStyledDocument();

                updateThePaneTextColorsForBackspaceOnly(i);

                try {
                    doc.insertString(doc.getLength(), "" + whatTheUserHasTypedSoFar.charAt(i), attrSetColorTextPane);
                } catch (BadLocationException ex) {
                    Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                StyleConstants.setBold(attrSetColorTextPane, true);
                colorTextPane.setCharacterAttributes(attrSetColorTextPane, true);

                Document doc = colorTextPane.getStyledDocument();

                updateThePaneTextColorsForBackspaceOnly(i);

                try {
                    doc.insertString(doc.getLength(), "" + whatTheUserHasTypedSoFar.charAt(i), attrSetColorTextPane);
                } catch (BadLocationException ex) {
                    Logger.getLogger(ViewOutputs.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        if (whatTheUserHasTypedSoFar.charAt(whatTheUserHasTypedSoFar.length() - 1) != textPaneArea.getText().charAt(whatTheUserHasTypedSoFar.length() - 1)) {//will remove one errors so far 
            if (errorsSoFar > 0) {
                errorsSoFar -= 1;
            }
        }

        whatTheUserHasTypedSoFar = whatTheUserHasTypedSoFar.substring(0, whatTheUserHasTypedSoFar.length() - 1);
    }
}
