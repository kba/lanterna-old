/*
 * This file is part of lanterna (http://code.google.com/p/lanterna/).
 * 
 * lanterna is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2010-2012 mabe02
 */

package com.googlecode.lanterna.test;

import java.nio.charset.Charset;
import com.googlecode.lanterna.LanternException;
import com.googlecode.lanterna.LanternTerminal;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenWriter;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.CommonUnixTerminal;

/**
 *
 * @author martin
 */
public class TerminalTest
{
    public static void main(String[] args) throws LanternException {
        if(args.length > 0) {
            try {
                Thread.sleep(15000);
            }
            catch(InterruptedException e) {
            }
        }

        Terminal rawTerminal = new LanternTerminal().getUnderlyingTerminal();
        Screen screen = new Screen(rawTerminal);
        ScreenWriter writer = new ScreenWriter(screen);
        screen.startScreen();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "Hello world!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, " ello world!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "  llo world!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "   lo world!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "    o world!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "      world!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "      world!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "       orld!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "        rld!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "         ld!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "          d!");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "           !");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        writer.drawString(10, 10, "            ");
        screen.refresh();
        try {
            Thread.sleep(500);
        }
        catch(InterruptedException e) {}
        screen.stopScreen();
    }
    

}
