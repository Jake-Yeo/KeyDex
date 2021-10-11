/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs20models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Jake Yeo
 */
public class Sound {

    private AudioInputStream audioStream = null;
    private SourceDataLine songLine = null;
    private URL url;
    private URL lofiUrlArray[];
    private URL googleUrl;
    private float lofiStreamVolume = 6;//default volume for lofi songs
    private float keyBoardSoundsVolume = -10;//default volume for keyboard sounds such as typing and when a new paragraph is gotten
    private float errorSoundsVolume = -10;//default volume for all error sound including the error popup
    private boolean stopStreaming;

    public boolean getStopStreaming() {
        return stopStreaming;
    }

    public void setStopStreaming(boolean stopStreaming) {
        this.stopStreaming = stopStreaming;
    }

    public float getKeyBoardSoundsVolume() {
        return keyBoardSoundsVolume;
    }

    public void setKeyBoardSoundsVolume(float keyBoardSoundsVolume) {
        this.keyBoardSoundsVolume = keyBoardSoundsVolume;
    }

    public float getErrorSoundsVolume() {
        return errorSoundsVolume;
    }

    public void setErrorSoundsVolume(float errorSoundsVolume) {
        this.errorSoundsVolume = errorSoundsVolume;
    }

    public void setLofiStreamVolume(float lofiStreamVolume) {
        this.lofiStreamVolume = lofiStreamVolume;
    }

    public float getLofiStreamVolume() {
        return lofiStreamVolume;
    }

    public void playKeyBoardClickSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File file = new File("src/sounds/keyboardClick.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(keyBoardSoundsVolume); // Sets volume
        clip.start();
    }

    public void playErrorSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File file = new File("src/sounds/errorSound.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(errorSoundsVolume); // Sets volume
        clip.start();
        clip.start();
    }

    public void playPopupSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File file = new File("src/sounds/popupSound.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(errorSoundsVolume); // Sets volume
        clip.start();
        clip.start();
    }

    public void playDingSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {//for when you finish a paragraph
        File file = new File("src/sounds/dingSound.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL());///change this, this is weird
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(keyBoardSoundsVolume); // Sets volume
        clip.start();
        clip.start();
    }

    public void setUpLofiArray() throws FileNotFoundException {try {
        //sets up every array once so it does not set up each array every time a method is called
        googleUrl = new URL("http://www.google.com");
        } catch (MalformedURLException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
        int whatLengthOfLofiUrlArrayShouldBe = 0;

        File file = new File("src/txts/LofiUrl.txt");// weird bug where ' symbol isn't recognized correctly
        String path = file.getAbsolutePath().replaceAll("\\\\", "/");
        Scanner scanner = new Scanner(new File(path));//make sure to close later on.
        while (scanner.hasNext()) {
            whatLengthOfLofiUrlArrayShouldBe++;
            scanner.nextLine();
        }
        scanner = new Scanner(new File(path));
        lofiUrlArray = new URL[whatLengthOfLofiUrlArrayShouldBe];
        for (int i = 0; i < lofiUrlArray.length; i ++) {
            try {
                lofiUrlArray[i] =  new URL(scanner.nextLine());
            } catch (MalformedURLException ex) {
                Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        scanner.close();
    }

    public void playRandomLofiTrack() throws MalformedURLException {//all songs are royalty free
        if (stopStreaming != true) {//stops user from corrupting audio bytes by spamming streamLofiButton
            int randomInt = (int) Math.floor(Math.random() * lofiUrlArray.length);
            if (songLine != null) {
                //System.out.println("works");
                //System.out.println(!songLine.isRunning());
                if (true) {
                    System.out.println("not working");
                    setUpLofiUrl(lofiUrlArray[randomInt]);
                }
            } else {
                setUpLofiUrl(lofiUrlArray[randomInt]);
            }
        }
    }

    public boolean isUserConnectedToWifi() {

        URLConnection connection;
        try {
            connection = googleUrl.openConnection();
            connection.connect();
        } catch (IOException ex) {
            return false;//stops error from printing when there is no wifi, instead a popup window is given to tell the user they are not conncted to wifi
        }
        return true;
    }

    public void setUpLofiUrl(URL uniformResourceLocator) {//delete if not needed
        url = uniformResourceLocator;
        new Thread(//useing thread so that this does not freeze gui
                new Runnable() {
            public void run() {
                if (stopStreaming != true) {
                    try {
                        try {
                            audioStream = AudioSystem.getAudioInputStream(url);//puts url into audioStream
                        } catch (UnsupportedAudioFileException | IOException ex) {
                            System.err.println("no wifi");
                        }

                        streamLofiUrl();//will stream the url of the lofi song requested
                    } catch (IOException ex) {
                        Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    stopStreaming = false;//stops 
                }
            }
        }).start();
    }

    public void streamLofiUrl() throws IOException {
        if (stopStreaming != true) {
            try {
                songLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, audioStream.getFormat()));
                songLine.open(audioStream.getFormat());
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
            }
            songLine.start();//starts songLine so that data can start being streamed to it

            int bufferSize = 10000;

            byte[] bytesBuffer = new byte[bufferSize];
            int bytesRead = 0;

            while (bytesRead > -1 && stopStreaming != true) {//stops mixer from trying to play/outputting an invalid length
                songLine.write(bytesBuffer, 0, bytesRead);
                FloatControl volumeControl = (FloatControl) songLine.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(lofiStreamVolume);//will allow user to change volume of lofi track whilst it plays

                bytesRead = audioStream.read(bytesBuffer);
            }
            songLine.flush();
            songLine.drain();
            songLine.stop();
            playRandomLofiTrack();//loops and plays new lofi track

        }
    }

}
