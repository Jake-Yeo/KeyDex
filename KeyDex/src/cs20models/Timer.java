/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs20models;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jake Yeo
 */
public class Timer implements Runnable {

    private double startTime = System.currentTimeMillis();
    private double elapsedTimeInUserTextFieldFocus = 0;
    private double elapsedTimeOutOfUserTextFieldFocus = 0;
    private double elapsedMinutesInUserTextFieldFocus;
    private double elapsedMinutesOutOfUserTextFieldFocus;
    private boolean startTimer = false;

    public double howManyMinutesInUserTextFieldSoFar() {
       // System.out.println(elapsedMinutesInUserTextFieldFocus);
        return elapsedMinutesInUserTextFieldFocus;
    }

    public boolean isTimerStarted() {
        return startTimer;
    }

    public void startTimer(boolean tf) {
        startTimer = tf;
    }

    public void resetTimer() {
        startTime = System.currentTimeMillis();
        elapsedTimeInUserTextFieldFocus = 0;
        elapsedTimeOutOfUserTextFieldFocus = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Timer.class.getName()).log(Level.SEVERE, null, ex);
            }

            //System.out.println(startTimer);
            if (startTimer) {

                elapsedTimeInUserTextFieldFocus = System.currentTimeMillis() - (startTime + elapsedTimeOutOfUserTextFieldFocus);
                elapsedMinutesInUserTextFieldFocus = (elapsedTimeInUserTextFieldFocus / 1000 / 60);
                // System.out.println(elapsedMinutesInUserTextFieldFocus);
            } else {
                elapsedTimeOutOfUserTextFieldFocus = System.currentTimeMillis() - (startTime + elapsedTimeInUserTextFieldFocus);
                elapsedMinutesOutOfUserTextFieldFocus = (elapsedTimeOutOfUserTextFieldFocus / 1000 / 60);
                //System.out.println(elapsedMinutesOutOfUserTextFieldFocus);

            }
        }
    }
}
