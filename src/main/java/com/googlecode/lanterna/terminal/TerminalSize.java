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
package com.googlecode.lanterna.terminal;

/**
 * Terminal dimensions in 2-d space, measured in number of rows and columns. This class is immutable and cannot change
 * its internal state after creation.
 *
 * @author Martin
 */
public class TerminalSize {

    private final int columns;
    private final int rows;

    public TerminalSize(int columns, int rows) {
        if (columns < 0) {
            throw new IllegalArgumentException("TerminalSize.columns cannot be less than 0!");
        }
        if (rows < 0) {
            throw new IllegalArgumentException("TerminalSize.rows cannot be less than 0!");
        }
        this.columns = columns;
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public TerminalSize withColumns(int columns) {
        return new TerminalSize(columns, this.rows);
    }

    public int getRows() {
        return rows;
    }

    public TerminalSize withRows(int rows) {
        return new TerminalSize(this.columns, rows);
    }

    @Override
    public String toString() {
        return "{" + columns + "x" + rows + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TerminalSize == false) {
            return false;
        }

        TerminalSize other = (TerminalSize) obj;
        return columns == other.columns
                && rows == other.rows;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.columns;
        hash = 53 * hash + this.rows;
        return hash;
    }
}
