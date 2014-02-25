package com.googlecode.lanterna.test.issue;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

public class Issue99 {

	    public static Screen screen = new Screen(new SwingTerminal(60, 25));

	    public static void main(String[] args) throws InterruptedException {
	        screen.startScreen();
	        while (true) {
	            Key key = screen.readInput();
	            if (key != null) {
	            	if (key.getKind() == Key.Kind.Escape)
	            		break;
	            }
	            Thread.sleep(1);
	        }
	        screen.stopScreen();
	    }

}
