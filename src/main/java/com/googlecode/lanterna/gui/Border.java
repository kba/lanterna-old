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
 * Copyright (C) 2010-2012 Martin
 */

package com.googlecode.lanterna.gui;

import com.googlecode.lanterna.terminal.ACS;
import com.googlecode.lanterna.terminal.TerminalPosition;
import com.googlecode.lanterna.terminal.TerminalSize;

/**
 * Class responsible for defining and rendering a border around a component. The
 * actuals border implementations are available through subclasses.
 * @author Martin
 */
public abstract class Border
{
    public abstract void drawBorder(TextGraphics graphics, TerminalSize actualSize, String title);
    public abstract TerminalSize getInnerAreaSize(int width, int height);
    public abstract TerminalPosition getInnerAreaLocation(int width, int height);
    public abstract TerminalSize surroundAreaSize(TerminalSize TerminalSize);

    public static class Standard extends Border
    {
    	private final int horizontalPadding;
		private final int verticalPadding;

        public Standard() {
        	this(1, 0);
        }

        public Standard(int horizontalPadding, int verticalPadding) {
        	this.horizontalPadding = horizontalPadding;
        	this.verticalPadding = verticalPadding;
        }

        @Override
        public void drawBorder(TextGraphics graphics, TerminalSize actualSize, String title)
        {
            graphics.applyTheme(graphics.getTheme().getDefinition(Theme.Category.BORDER));

            final int columnsWidth = actualSize.getColumns();
            final int rowsHeight = actualSize.getRows();

            // Top
            graphics.drawString(0, 0, ACS.ULCORNER + "");
            for(int x = 1; x < columnsWidth - 1; x++)
                graphics.drawString(x, 0, ACS.HLINE + "");
            graphics.drawString(columnsWidth - 1, 0, ACS.URCORNER + "");

            // Each row
            for(int i = 1; i < rowsHeight - 1; i++) {
                graphics.drawString(0, i, ACS.VLINE + "");
                graphics.drawString(0 + columnsWidth - 1, i, ACS.VLINE + "");
            }

            // Bottom
            graphics.drawString(0, rowsHeight - 1, ACS.LLCORNER + "");
            for(int x = 1; x < columnsWidth - 1; x++)
                graphics.drawString(x, rowsHeight - 1, ACS.HLINE + "");
            graphics.drawString(columnsWidth - 1, rowsHeight - 1, ACS.LRCORNER + "");

            // Title
            graphics.applyTheme(graphics.getTheme().getDefinition(Theme.Category.DIALOG_AREA));
            graphics.setBoldMask(true);
            graphics.drawString(2, 0, title);
        }

        @Override
        public TerminalPosition getInnerAreaLocation(int width, int height)
        {
            if(width > 2 && height > 2)
                return new TerminalPosition(1 + horizontalPadding, 1 + verticalPadding);
            else
                return new TerminalPosition(0,0);
        }

        @Override
        public TerminalSize getInnerAreaSize(int width, int height)
        {
            if(width > 2 && height > 2)
				return new TerminalSize(
						width - 2 * (1 + horizontalPadding),
						height - 2 * (1 + verticalPadding));
            else
                return new TerminalSize(width, height);
        }

        @Override
        public TerminalSize surroundAreaSize(TerminalSize terminalSize)
        {
            final int surroundColumnStretch = 4;
            final int surroundRowStretch = 2;

            int terminalSizeColumns = terminalSize.getColumns();
            int terminalSizeRows = terminalSize.getRows();

            int surroundSizeColumns;
            if (terminalSizeColumns == Integer.MAX_VALUE) {
                surroundSizeColumns = terminalSizeColumns;
            } else {
                surroundSizeColumns = terminalSizeColumns + surroundColumnStretch;
            }

            int surroundSizeRows;
            if (terminalSizeRows == Integer.MAX_VALUE) {
                surroundSizeRows = terminalSizeRows;
            } else {
                surroundSizeRows = terminalSizeRows + surroundRowStretch;
            }

            TerminalSize surroundAreaTerminalSize = new TerminalSize(
                surroundSizeColumns, surroundSizeRows);

            return surroundAreaTerminalSize;
        }
    }

    public static class Bevel extends Border
    {
        private boolean isRaised;

        public Bevel(boolean isRaised) {
            this.isRaised = isRaised;
        }

        @Override
        public void drawBorder(TextGraphics graphics, TerminalSize actualSize, String title)
        {
            // Record current terminal size
            final int columnsWidth = actualSize.getColumns();
            final int rowsHeight = actualSize.getRows();

            // Select current overall theme
            final Theme theme = graphics.getTheme();

            // Select the current theme's definition of upper-left and
            // lower-right borders rendering, considering whether they should be
            // displayed with raised looks as well
            final Theme.Definition upperLeftTheme;
            final Theme.Definition lowerRightTheme;
            if(isRaised) {
                upperLeftTheme = theme.getDefinition(Theme.Category.RAISED_BORDER);
                lowerRightTheme = theme.getDefinition(Theme.Category.BORDER);
            }
            else {
                upperLeftTheme = theme.getDefinition(Theme.Category.BORDER);
                lowerRightTheme = theme.getDefinition(Theme.Category.RAISED_BORDER);
            }

            // Select the current theme's dialog area style definition
            final Theme.Definition dialogAreaTheme = theme.getDefinition(Theme.Category.DIALOG_AREA);

            // Top
            graphics.applyTheme(upperLeftTheme);
            graphics.drawString(0, 0, ACS.ULCORNER + "");
            for(int i = 1; i < columnsWidth - 1; i++)
                graphics.drawString(i, 0, ACS.HLINE + "");
            graphics.applyTheme(lowerRightTheme);
            graphics.drawString(columnsWidth - 1, 0, ACS.URCORNER + "");

            // Each row
            for(int i = 1; i < rowsHeight - 1; i++) {
                graphics.applyTheme(upperLeftTheme);
                graphics.drawString(0, i, ACS.VLINE + "");
                graphics.applyTheme(lowerRightTheme);
                graphics.drawString(columnsWidth - 1, i, ACS.VLINE + "");
            }

            // Bottom
            graphics.applyTheme(upperLeftTheme);
            graphics.drawString(0, rowsHeight - 1, ACS.LLCORNER + "");
            graphics.applyTheme(lowerRightTheme);
            for(int i = 1; i < columnsWidth - 1; i++)
                graphics.drawString(i, rowsHeight - 1, ACS.HLINE + "");
            graphics.drawString(columnsWidth - 1, rowsHeight - 1, ACS.LRCORNER + "");

            // Title
            graphics.applyTheme(dialogAreaTheme);
            graphics.setBoldMask(true);
            graphics.drawString(2, 0, title);
        }

        @Override
        public TerminalPosition getInnerAreaLocation(int width, int height)
        {
            if(width > 2 && height > 2)
                return new TerminalPosition(2, 1);
            else
                return new TerminalPosition(0,0);
        }

        @Override
        public TerminalSize getInnerAreaSize(int width, int height)
        {
            if(width > 2 && height > 2)
                return new TerminalSize(width - 4, height - 2);
            else
                return new TerminalSize(width, height);
        }

        @Override
        public TerminalSize surroundAreaSize(TerminalSize terminalSize)
        {
            final int surroundColumnStretch = 4;
            final int surroundRowStretch = 2;

            int terminalSizeColumns = terminalSize.getColumns();
            int terminalSizeRows = terminalSize.getRows();

            int surroundSizeColumns;
            if (terminalSizeColumns == Integer.MAX_VALUE) {
                surroundSizeColumns = terminalSizeColumns;
            } else {
                surroundSizeColumns = terminalSizeColumns + surroundColumnStretch;
            }

            int surroundSizeRows;
            if (terminalSizeRows == Integer.MAX_VALUE) {
                surroundSizeRows = terminalSizeRows;
            } else {
                surroundSizeRows = terminalSizeRows + surroundRowStretch;
            }

            TerminalSize surroundAreaTerminalSize = new TerminalSize(
                surroundSizeColumns, surroundSizeRows);

            return surroundAreaTerminalSize;
        }
    }

    public static class Invisible extends Border
    {
        @Override
        public void drawBorder(TextGraphics graphics, TerminalSize actualSize, String title)
        {
        }

        @Override
        public TerminalPosition getInnerAreaLocation(int width, int height)
        {
            return new TerminalPosition(0,0);
        }

        @Override
        public TerminalSize getInnerAreaSize(int width, int height)
        {
            return new TerminalSize(width, height);
        }

        @Override
        public TerminalSize surroundAreaSize(TerminalSize TerminalSize)
        {
            return TerminalSize;
        }
    }
}
