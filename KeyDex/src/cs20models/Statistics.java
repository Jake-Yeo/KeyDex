/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs20models;

/**
 *
 * @author Jake Yeo
 */
public class Statistics {
    public double wpm(double minutesSoFar, double charactersSoFar) {
        return Math.round(charactersSoFar/5/minutesSoFar);
    }
    public double accuracy(double errorsSoFar, double whatUserHasTypedSoFarLength) {
        return Math.round(100.0 - (100 * ( errorsSoFar / whatUserHasTypedSoFarLength)));
    }
}
