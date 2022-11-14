/*
 * Advance Programming Group Project
 * Date of Submission: 11/11/2022
 * Lab Supervisor: Christopher Panther
 *
 * Group Members:-
 * ~ Gabrielle Johnson      2005322
 * ~ Jazmin Hayles          2006754
 * ~ Rushawn White          2002469
 * ~ Barrignton Patternson  2008034
 *
 */
package com.application.view.utilities;

import java.awt.event.*;

public class InvalidCharListener extends KeyAdapter {
    private final char[] invalidChars;

    /**
     * Primary Constructor
     * @param chars
     */
    public InvalidCharListener(char[] chars) {
        invalidChars = chars;
    }

    /**
     * Listens and Checks each character as it is typed from the keyboard
     * @param e the event to be processed
     */
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        for (char invalidChar : invalidChars) {
            if (c == invalidChar) {
                java.awt.Toolkit.getDefaultToolkit().beep();
                e.consume();
            }
        }
    }
}
