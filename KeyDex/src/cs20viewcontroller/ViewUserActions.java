/*
 * The controller classes (like the ViewUserActions class) provides actions
 * that the user can trigger through the view classes.  Those actions are 
 * written in this class as private inner classes (i.e. classes 
 * declared inside another class).
 *
 * You can use all the public instance variables you defined in AllModelsForView, 
 * DrawnView, and ViewOutputs as though they were part of this class! This is 
 * due to the magic of subclassing (i.e. using the extends keyword).
 */
package cs20viewcontroller;

import cs20models.Sound;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;

/**
 * ViewUserActions is a class that contains actions users can trigger.
 *
 * User actions are written as private inner classes that implements the
 * ActionListener interface. These actions must be "wired" into the DrawnView in
 * the ViewUserActions constructor, or else they won't be triggered by the user.
 *
 * Actions that the user can trigger are meant to manipulate some model classes
 * by sending messages to them to tell them to update their data members.
 *
 * Actions that the user can trigger can also be used to manipulate the GUI by
 * sending messages to the view classes (e.g. the DrawnView class) to tell them
 * to update themselves (e.g. to redraw themselves on the screen).
 *
 * @author cheng
 */
public class ViewUserActions extends ViewOutputs {

    /*
     * Step 1 of 2 for writing user actions.
     * -------------------------------------
     *
     * User actions are written as private inner classes that implement
     * ActionListener, and override the actionPerformed method.
     *
     * Use the following as a template for writting more user actions.
     */
 /*
     * ViewUserActions constructor used for wiring user actions to GUI elements
     */
    private class OpenOrCloseSettingsButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (settingsButton.isSelected()) {
                keyboardPanel.setVisible(false);
                getNewTextButton.setVisible(true);
                customTextPanel.setVisible(false);
                customTextPanelButton.setSelected(false);
                customTextPanelButton.setText("Click To Open Custom Text Panel");
                settingsPanel.setVisible(true);
                loginSignUpPanel.setVisible(false);
                logoutPanel.setVisible(false);
                loginSignUpButton.setSelected(false);
                typingPanel.setVisible(true);
                typingPanelHostPanel.setVisible(true);
                settingsButton.setText("Click To Close Settings");
                loginSignUpButton.setText("Click To Open Account Page");
            } else {
                if (keyboardVisualRadioButton.isSelected()) {
                    keyboardPanel.setVisible(true);
                } else {
                    keyboardPanel.setVisible(false);
                }
                getNewTextButton.setVisible(true);
                settingsPanel.setVisible(false);
                loginSignUpPanel.setVisible(false);
                logoutPanel.setVisible(false);
                customTextPanel.setVisible(false);
                customTextPanelButton.setSelected(false);
                customTextPanelButton.setText("Click To Open Custom Text Panel");
                loginSignUpButton.setSelected(false);
                settingsButton.setText("Click To Open Settings");
                loginSignUpButton.setText("Click To Open Account Page");
            }
        }
    }

    private class OpenOrCloseLoginSignUpPageButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (loginSignUpButton.isSelected() && !accounts.getIsUserLoggedIn()) {
                settingsPanel.setVisible(false);
                typingPanel.setVisible(false);
                typingPanelHostPanel.setVisible(false);
                loginSignUpPanel.setVisible(true);
                customTextPanel.setVisible(false);
                customTextPanelButton.setSelected(false);
                customTextPanelButton.setText("Click To Open Custom Text Panel");
                settingsButton.setSelected(false);
                logoutPanel.setVisible(false);
                getNewTextButton.setVisible(false);
                loginSignUpButton.setText("Click To Close Account Page");
                settingsButton.setText("Click To Open Settings");
            } else if (!accounts.getIsUserLoggedIn()) {
                if (keyboardVisualRadioButton.isSelected()) {
                    keyboardPanel.setVisible(true);
                } else {
                    keyboardPanel.setVisible(false);
                }
                settingsPanel.setVisible(false);
                loginSignUpPanel.setVisible(false);
                typingPanel.setVisible(true);
                typingPanelHostPanel.setVisible(true);
                customTextPanel.setVisible(false);
                customTextPanelButton.setSelected(false);
                customTextPanelButton.setText("Click To Open Custom Text Panel");
                logoutPanel.setVisible(false);
                settingsButton.setSelected(false);
                getNewTextButton.setVisible(true);
                loginSignUpButton.setText("Click To Open Account Page");
                settingsButton.setText("Click To Open Settings");
            } else if (loginSignUpButton.isSelected() && accounts.getIsUserLoggedIn()) {
                settingsPanel.setVisible(false);
                typingPanel.setVisible(false);
                typingPanelHostPanel.setVisible(false);
                logoutPanel.setVisible(true);
                customTextPanel.setVisible(false);
                customTextPanelButton.setSelected(false);
                customTextPanelButton.setText("Click To Open Custom Text Panel");
                settingsButton.setSelected(false);
                getNewTextButton.setVisible(false);
                loginSignUpButton.setText("Click To Close Account Page");
                settingsButton.setText("Click To Open Settings");
            } else if (accounts.getIsUserLoggedIn()) {
                if (keyboardVisualRadioButton.isSelected()) {
                    keyboardPanel.setVisible(true);
                } else {
                    keyboardPanel.setVisible(false);
                }
                settingsPanel.setVisible(false);
                logoutPanel.setVisible(false);
                customTextPanel.setVisible(false);
                customTextPanelButton.setSelected(false);
                customTextPanelButton.setText("Click To Open Custom Text Panel");
                typingPanel.setVisible(true);
                typingPanelHostPanel.setVisible(true);
                settingsButton.setSelected(false);
                getNewTextButton.setVisible(true);
                loginSignUpButton.setText("Click To Open Account Page");
                settingsButton.setText("Click To Open Settings");
            }

        }
    }

    private class StreamLofi implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            new Thread(//useing thread so that this does not freeze gui
                    new Runnable() {
                public void run() {
                    if (streamLofiButton.isSelected() && sound.isUserConnectedToWifi()) {
                        streamLofiButton.setText("Stop Streaming Lofi");
                        sound.setStopStreaming(false);
                        try {
                            sound.playRandomLofiTrack();
                        } catch (MalformedURLException ex) {
                            Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (!streamLofiButton.isSelected() && sound.isUserConnectedToWifi()) {
                        streamLofiButton.setText("Stream Lofi");
                        sound.setStopStreaming(true);
                    } else if (!sound.isUserConnectedToWifi()) {
                        showWarning("Connect To Wifi To User This Feature");
                        streamLofiButton.setSelected(false);
                        streamLofiButton.setText("Stream Lofi");
                    }
                    streamLofiButton.setVisible(false);
                    try {
                        Thread.sleep(1000);//user can't spam sound button
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    streamLofiButton.setVisible(true);
                }
            }).start();
        }
    }

    private class CustomTextOpenPanel implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!customTextPanelButton.isSelected() && accounts.getIsUserLoggedIn()) {
                getNewTextButton.setVisible(true);
                customTextPanel.setVisible(false);
                typingPanel.setVisible(true);
                typingPanelHostPanel.setVisible(true);
                getNewTextButton.setVisible(true);
                customTextPanelButton.setText("Click To Open Custom Text Panel");
            } else if (accounts.getIsUserLoggedIn()) {
                if (keyboardVisualRadioButton.isSelected()) {
                    keyboardPanel.setVisible(true);
                } else {
                    keyboardPanel.setVisible(false);
                }
                try {
                    customTextTextArea.setText(typingService.setUpCustomTextArea(accounts.getUsername()));
                } catch (IOException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
                getNewTextButton.setVisible(false);
                customTextPanelButton.setText("Click To Close Custom Text Panel");
                loginSignUpButton.setText("Click To Open Account Page");
                settingsButton.setText("Click To Open Settings");
                loginSignUpButton.setSelected(false);
                settingsButton.setSelected(false);
                settingsPanel.setVisible(false);
                loginSignUpPanel.setVisible(false);
                logoutPanel.setVisible(false);
                typingPanel.setVisible(false);
                typingPanelHostPanel.setVisible(false);
                customTextPanel.setVisible(true);
            } else {
                customTextPanelButton.setSelected(false);
                showWarning("Login to use this feature!");
            }
        }
    }

    private class AddCustomText implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (customTextTextArea.getText().trim().equals("")) {
                showWarning("Please enter custom text in the field! Spaces on the end and front don't count!");
            } else {
                Scanner scanner = new Scanner(customTextTextArea.getText());
                String customTextToSend = "";
                while (scanner.hasNextLine()) {
                    customTextToSend += scanner.nextLine() + accounts.getDelimiter();
                }
                try {
                    typingService.saveCustomText(customTextToSend, accounts.getUsername());
                } catch (IOException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class ResetSettings implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            setUpAccountPreferences(accounts.getDefaultPreferences());//sets settings to default
        }
    }

    private class ColorModeOfTypingProgram implements ActionListener {//light mode dark mode etc

        @Override
        public void actionPerformed(ActionEvent ae) {
            updateColorsOfTypingProgram();
        }
    }

    private class Logout implements ActionListener {//light mode dark mode etc

        @Override
        public void actionPerformed(ActionEvent ae) {
            setUpAccountPreferences(accounts.getDefaultPreferences());//sets settings to default
            accounts.setPassword("");
            accounts.setUsername("");
            settingsPanel.setVisible(false);
            loginSignUpPanel.setVisible(false);
            logoutPanel.setVisible(false);
            typingPanel.setVisible(true);
            typingPanelHostPanel.setVisible(true);
            customTextPanel.setVisible(false);
            settingsButton.setSelected(false);
            getNewTextButton.setVisible(true);
            loginSignUpButton.setText("Click To Open Account Page");
            settingsButton.setText("Click To Open Settings");
            passwordField.setText("");
            usernameField.setText("");
            loginSignUpButton.setSelected(false);
            accounts.setIsUserLoggedIn(false);
        }
    }

    private class SavePreferences implements ActionListener {//light mode dark mode etc

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                accounts.saveAccountPreferences(accounts.getUsername(), accounts.getPassword(), sentenceRadioButton.isSelected(), lowercaseRadioButton.isSelected(), numberRadioButton.isSelected(), symbolRadioButton.isSelected(), uppercaseRadioButton.isSelected(), darkModeRadioButton.isSelected(), streamLofiButton.isSelected(), stringLengthSlider.getValue(), typingSoundsVolumeSlider.getValue(), errorSoundsVolumeSlider.getValue(), lofiStreamVolumeSlider.getValue(), textDisplaySizeSlider.getValue(), typedTextSizeSlider.getValue(), customTextRadioButton.isSelected(), keyboardVisualRadioButton.isSelected());
            } catch (IOException ex) {
                Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class Login implements ActionListener {//light mode dark mode etc

        @Override
        public void actionPerformed(ActionEvent ae) {
            String password = "";
            for (int i = 0; i < passwordField.getPassword().length; i++) {
                password += passwordField.getPassword()[i];
            }
            if (!usernameField.getText().equals("") && passwordField.getPassword().length != 0 && !accounts.getIsUserLoggedIn()) {

                try {
                    if (accounts.checkIfAccountExists(usernameField.getText(), password)) {
                        accounts.setPassword(password);
                        accounts.setUsername(usernameField.getText());
                        typingService.setUpCustomTextArray(accounts.getUsername());
                        setUpAccountPreferences(accounts.getAccountPrefrences(usernameField.getText(), password));
                        settingsPanel.setVisible(false);
                        loginSignUpPanel.setVisible(false);
                        customTextPanel.setVisible(false);
                        typingPanel.setVisible(true);
                        typingPanelHostPanel.setVisible(true);
                        settingsButton.setSelected(false);
                        getNewTextButton.setVisible(true);
                        loginSignUpButton.setText("Click To Open Account Page");
                        loginSignUpButton.setSelected(false);
                        settingsButton.setText("Click To Open Settings");
                        passwordField.setText("");
                        usernameField.setText("");
                        accounts.setIsUserLoggedIn(true);
                        try {
                            typingService.saveCustomText("", accounts.getUsername());//sets up custom text array
                        } catch (IOException ex) {
                            Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        showWarning("This account does not exist!");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                showWarning("Please fill out all fields!");
            }
        }
    }

    private class SignUp implements ActionListener {//light mode dark mode etc

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!usernameField.getText().equals("") && passwordField.getPassword().length != 0 && !accounts.getIsUserLoggedIn()) {

                String password = "";
                for (int i = 0; i < passwordField.getPassword().length; i++) {
                    password += passwordField.getPassword()[i];
                }
                try {
                    //String username, String password, boolean typingSentences, boolean lowerCaseWords, boolean numbers, boolean symbols, boolean uppercaseWords, boolean darkMode, boolean streamLofi, int lengthToGenerate, int typingVolume, int errorVolume, int lofiStreamVolume, int textDisplaySize, int typedTextSize
                    if (accounts.checkIfUserNameIsTheSame(usernameField.getText()) != true) {
                        try {

                            accounts.createAccountAndSave(usernameField.getText(), password, sentenceRadioButton.isSelected(), lowercaseRadioButton.isSelected(), numberRadioButton.isSelected(), symbolRadioButton.isSelected(), uppercaseRadioButton.isSelected(), darkModeRadioButton.isSelected(), streamLofiButton.isSelected(), stringLengthSlider.getValue(), typingSoundsVolumeSlider.getValue(), errorSoundsVolumeSlider.getValue(), lofiStreamVolumeSlider.getValue(), textDisplaySizeSlider.getValue(), typedTextSizeSlider.getValue(), customTextRadioButton.isSelected(), keyboardVisualRadioButton.isSelected());
                            accounts.setPassword(password);
                            accounts.setUsername(usernameField.getText());
                            settingsPanel.setVisible(false);
                            loginSignUpPanel.setVisible(false);
                            customTextPanel.setVisible(false);
                            typingPanel.setVisible(true);
                            typingPanelHostPanel.setVisible(true);
                            settingsButton.setSelected(false);
                            getNewTextButton.setVisible(true);
                            loginSignUpButton.setSelected(false);
                            loginSignUpButton.setText("Click To Open Account Page");
                            settingsButton.setText("Click To Open Settings");
                            passwordField.setText("");
                            usernameField.setText("");
                            accounts.getAccountPrefrences(accounts.getUsername(), accounts.getPassword());
                            accounts.setIsUserLoggedIn(true);
                            try {
                                replaceTextPaneAreaString();//fixes bug when you type before signing up
                            } catch (BadLocationException ex) {
                                Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                typingService.saveCustomText("", accounts.getUsername());//sets up custom text array
                            } catch (IOException ex) {
                                Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        usernameField.setText("");
                        passwordField.setText("");
                    } else {
                        showWarning("Please pick different username!");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                showWarning("Please fill out all fields!");
            }
        }
    }

    private class GetNewText implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                replaceTextPaneAreaString();
            } catch (BadLocationException ex) {
                Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class CustomTextRequirements implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!accounts.getIsUserLoggedIn()) {
                customTextRadioButton.setSelected(false);
                showWarning("Login to use this feature!");
            }
            if (accounts.getIsUserLoggedIn()) {
                try {
                    typingService.saveCustomText("", accounts.getUsername());
                } catch (IOException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (typingService.returnCustomTextArrayLength() == 0 && accounts.getIsUserLoggedIn()) {
                customTextRadioButton.setSelected(false);
                showWarning("Enter custom text to use this feature");
            }
        }
    }

    private class SetTextOfStringLengthSliderText implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent arg0) {

            stringLengthSliderLabel.setText("Approximate text length to generate: " + stringLengthSlider.getValue());
        }
    }

    private class SetTypingSoundsVolumeSlider implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent arg0) {
            sound.setKeyBoardSoundsVolume(typingSoundsVolumeSlider.getValue());
            typingSoundsVolumeSliderLabel.setText("Typing sounds volume: " + Math.round(100 * (((double) typingSoundsVolumeSlider.getValue() + 80) / 86)));//will set the text for the volumeSliderLable
        }
    }

    private class SetErrorSoundsVolumeSlider implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent arg0) {
            sound.setErrorSoundsVolume(errorSoundsVolumeSlider.getValue());
            errorSoundsVolumeSliderLabel.setText("Error sounds volume: " + Math.round(100 * (((double) errorSoundsVolumeSlider.getValue() + 80) / 86)));//will set the text for the volumeSliderLable
        }
    }

    private class SetLofiStreamVolumeSlider implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent arg0) {
            sound.setLofiStreamVolume(lofiStreamVolumeSlider.getValue());
            lofiStreamVolumeSliderLabel.setText("Lofi stream volume: " + Math.round(100 * (((double) lofiStreamVolumeSlider.getValue() + 80) / 86)));//will set the text for the volumeSliderLable
        }
    }

    private class TextDisplaySizeSetter implements ChangeListener {//will change the size of text

        @Override
        public void stateChanged(ChangeEvent arg0) {
            textDispaySize = textDisplaySizeSlider.getValue();
            textDisplaySizeSliderLabel.setText("Text Display Size: " + textDisplaySizeSlider.getValue());
            updateTextPaneAreaStyle();
        }
    }

    private class TypedTextSizeSetter implements ChangeListener {//will change the size of text

        @Override
        public void stateChanged(ChangeEvent arg0) {
            typedTextSize = typedTextSizeSlider.getValue();
            typedTextSizeSliderLabel.setText("Text Display Size: " + typedTextSizeSlider.getValue());
            updateTheColorTextPaneColors();
        }
    }

    private class UserTextFieldFocusGained implements FocusListener {

        @Override
        public void focusGained(FocusEvent arg0) {
            userTextField.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false);//userTextField backspace cannot be held down// this disabled backspace for all other fields to be held down// it also disables the backspace button
            textPaneArea.setCaretPosition(whatTheUserHasTypedSoFar.length() + caretPostionHowMuchToAddOrSubstractBy);//this will make textPaneArea automatically scroll
            clickAboveLabel.setText("Click above to stop typing");
            timer.startTimer(true);
            //System.out.println("focus gained");//get rid of in later product
        }

        @Override
        public void focusLost(FocusEvent arg0) {
            //System.out.println("focus lost");//get rid of in later product
            userTextField.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(true);//all other fields can use the backspace button
            clickAboveLabel.setText("Click above to start typing");
            userTextField.setFocusable(false);//needed for clicking of colorTextPane to work
            timer.startTimer(false);

        }
    }

    private class WhenMouseClickedOnColorTextPaneSetFocusToUserTextPane implements MouseListener {//focus on userTextField when this field is clicked

        @Override
        public void mouseClicked(MouseEvent arg0) {
        }

        @Override
        public void mousePressed(MouseEvent arg0) {
            if (userTextField.isFocusable()) {
                userTextField.setFocusable(false);
            } else {
                userTextField.setFocusable(true);
                userTextField.requestFocus();
            }
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
            if (!userTextField.isFocusable()) {
                colorTextPane.setFocusable(true);
                colorTextPane.requestFocus();
            }
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
        }

    }

    private class WhenKeyPressed implements KeyListener {

        @Override
        public void keyTyped(KeyEvent arg0) {
            timer.startTimer(true);
            //System.out.println("chars " + charsTypedSoFar + " mins " + timer.howManyMinutesInUserTextFieldSoFar());
            wpmLabel.setText("wpm: " + Math.round(statistics.wpm(timer.howManyMinutesInUserTextFieldSoFar(), charsTypedSoFar)));//calculates wpm
            trueAccuracyLable.setText("Accuracy: " + Math.round(statistics.accuracy(errorsSoFar, whatTheUserHasTypedSoFar.length())));//calculates acurracy, keeps in mind deleted errors and all
        }

        @Override
        public void keyPressed(KeyEvent arg0) {
            if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE && numOfTimesChecked > 0) {
                if (whatTheUserHasTypedSoFar.length() > 3) {//this will make textPaneArea automatically scroll
                    caretPostionHowMuchToAddOrSubstractBy = -3;
                } else {
                    caretPostionHowMuchToAddOrSubstractBy = 0;
                }
            }
            //System.out.println(caretPostionHowMuchToAddOrSubstractBy);
            textPaneArea.setCaretPosition(whatTheUserHasTypedSoFar.length() + caretPostionHowMuchToAddOrSubstractBy);//this will make textPaneArea automatically scroll since its automatic scroll feature sets the caret at the end of the text which isn't what I want
            userTextField.setCaretPosition(userTextField.getText().length());// this will ensure that caret position is always at the end of userTextField so errors don't occur
        }

        @Override
        public void keyReleased(KeyEvent arg0) {
            String arg0String = ("" + arg0.getKeyChar());
            char key = arg0String.toLowerCase().charAt(0);

            if (getUserTextFieldString().length() > 0 && (key >= 32 && key <= 126) && !(key >= 0 && key <= 31) && key != 127 && !(key > 127) && arg0.isControlDown() == false && arg0.isAltDown() == false) {//this if statment will make it so that keys that don't input letters, symbols or numbers will not trigger this. Make sure to continue to add theses keys in this if statement!!!
                if (whatTheUserHasTypedSoFar.length() < textPaneArea.getText().length() - 1) {    //fixes bug where the method tried to get an index that does not exist
                    changeKeyColors(1);
                }

                if (whatTheUserHasTypedSoFar.length() + 3 < textPaneArea.getText().length()) {//this will make textPaneArea automatically scroll
                    caretPostionHowMuchToAddOrSubstractBy = 3;
                } else {
                    caretPostionHowMuchToAddOrSubstractBy = 0;
                }
                userTextField.setText(colorTextPane.getText() + arg0String);//stops user from holding down a key and repeating a letter multiple times
                whatTheUserHasTypedSoFar = userTextField.getText();
                try {
                    checkWhatUserTyped(arg0String);
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
                updateTextPaneAreaStyle();
                updateTheColorTextPaneColors();//fixes visual bug that occurs when spamming mouse on colorTextPane whilst spamming a key. I dont know why it happens as colorTextPane is not editable or focusable. I know it is somehow getting input as the bug goes away if isEnabled.set(false);

            } else if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE && numOfTimesChecked > 0) {
                if (textPaneArea.getText().length() - whatTheUserHasTypedSoFar.length() < 3) {
                    caretPostionHowMuchToAddOrSubstractBy = 0;//fixes bug where you break the program when you backspace on the second last character of textPaneArea
                }
                try {
                    //makes it so the user can use backspace to fix their errors

                    sound.playKeyBoardClickSound();//will play clicking sound
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
                backspace();
                updateTextPaneAreaStyle();
                changeKeyColors(0);
            }
            if (getNewString == true) {
                try {
                    replaceTextPaneAreaString();// will get a new string more will be added so you can choose randomly generated string aswell
                } catch (BadLocationException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    sound.playDingSound();//will play ding sound
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class ShowTipsAndTricks implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {
        }

        @Override
        public void mousePressed(MouseEvent arg0) {
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
            JOptionPane.showMessageDialog(null, "-NEVER look at the keyboard when typing, refer only to the keyboard visual given to you, this will ensure that you build up muscle memory to type fast and accurately!"
                    + "-If your new to typing, or if you are re-learning how to type, make sure to refer to the keyboard visual below!\n"
                    + "-The colors on the keyboard will represent which hand to use when pressing a key!\n"
                    + "-Highlighted fingers on the diagram indicate which fingers to use to type a specific letter!\n"
                    + "-The keyboard visual shows you the most effective and efficent way to type! Take your time to re-learn/learn which fingers to use to type, this will help you in the long run!\n"
                    + "-Make sure to go back and fix mistakes while typing! Using your pinkie to hit the backspace button!\n\n"
                    + "Info: All royalty free music was provided by ChilledCow https://www.youtube.com/channel/UCSJ4gkVC6NrvII8umztf0Ow\n"
                    + "All public text was provided by California Digital Library https://archive.org/details/cdl?&sort=-week&page=19",
                    "Tips and tricks!", JOptionPane.QUESTION_MESSAGE);
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
        }
    }

    public ViewUserActions() {
        getContentPane().setBackground(Color.BLACK);
        /*
         * Step 2 of 2 for writing user actions.
         * -------------------------------------
         *
         * Once a private inner class has been written for a user action, you
         * can wire it to a GUI element (i.e. one of GUI elements you drew in
         * the DrawnView class and which you gave a memorable public variable
         * name!).
         *
         * Use the following as a template for wiring more user actions.
         */
        typingProgramSetup();
        //jPanel1.setVisible(false);
        //jPanel1.setVisible(true);
        userTextField.addKeyListener(new WhenKeyPressed());//adds a key listener
        userTextField.addFocusListener(new UserTextFieldFocusGained());//will tell me if  the user enters the userTextField
        getNewTextButton.addActionListener(new GetNewText());//will get new text when this button is pressed
        settingsButton.addActionListener(new OpenOrCloseSettingsButton());
        darkModeRadioButton.addActionListener(new ColorModeOfTypingProgram());
        lightModeRadioButton.addActionListener(new ColorModeOfTypingProgram());
        stringLengthSlider.addChangeListener(new SetTextOfStringLengthSliderText());
        typingSoundsVolumeSlider.addChangeListener(new SetTypingSoundsVolumeSlider());
        textDisplaySizeSlider.addChangeListener(new TextDisplaySizeSetter());
        typedTextSizeSlider.addChangeListener(new TypedTextSizeSetter());
        errorSoundsVolumeSlider.addChangeListener(new SetErrorSoundsVolumeSlider());
        lofiStreamVolumeSlider.addChangeListener(new SetLofiStreamVolumeSlider());
        streamLofiButton.addActionListener(new StreamLofi());
        loginButton.addActionListener(new Login());
        signUpButton.addActionListener(new SignUp());
        loginSignUpButton.addActionListener(new OpenOrCloseLoginSignUpPageButton());
        colorTextPane.addMouseListener(new WhenMouseClickedOnColorTextPaneSetFocusToUserTextPane());
        logoutButton.addActionListener(new Logout());
        savePreferencesButton.addActionListener(new SavePreferences());
        resetSettingsButton.addActionListener(new ResetSettings());
        customTextRadioButton.addActionListener(new CustomTextRequirements());
        customTextPanelButton.addActionListener(new CustomTextOpenPanel());
        customTextButton.addActionListener(new AddCustomText());
        tipsLabel.addMouseListener(new ShowTipsAndTricks());
    }
}
