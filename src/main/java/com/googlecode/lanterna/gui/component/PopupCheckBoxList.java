package com.googlecode.lanterna.gui.component;

import com.googlecode.lanterna.gui.TextGraphics;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.ACS;
import com.googlecode.lanterna.terminal.TerminalPosition;
import com.googlecode.lanterna.terminal.TerminalSize;

public class PopupCheckBoxList extends RadioCheckBoxList {

	private static final String EMPTY_SELECT = "-------";
	
	private boolean poppedUp = false;
	
	public boolean isPoppedUp() {
		return poppedUp;
	}

	@Override
	protected String createItemString(int index) {
		if (poppedUp) {
			return super.createItemString(index);
		}
		String drawString = (getCheckedItemIndex() > -1)
				? this.getItemAt(index).toString()
				: (getSize() > 0) 
					? this.getItemAt(0).toString()
					: EMPTY_SELECT;
        return ACS.ARROW_DOWN + drawString;
	}
	
	@Override
	public void repaint(TextGraphics graphics) {
		if (poppedUp) {
			super.repaint(graphics);
		} else {
			if (hasFocus()) graphics.applyTheme(getListItemThemeDefinition(graphics.getTheme(), -1));
			graphics.drawString(0, 0, createItemString(getCheckedItemIndex()));
			setHotspot(graphics.translateToGlobalCoordinates(new TerminalPosition(0, 0)));
		}
	}
	
	@Override
	public Result keyboardInteraction(Key key) {
		if (poppedUp) {
			Result parentRet = super.keyboardInteraction(key);
			if (key.equalsString("<cr>") || key.equalsString("<space>")) {
				poppedUp = false;
				invalidate();
				valueChanged();
				return parentRet;
			}
		} 
		if (key.equalsString("<cr>")) {
			poppedUp = true;
			invalidate();
			valueChanged();
		} else if (key.equalsString("<Tab>")) {
			return Result.NEXT_INTERACTABLE_DOWN;
		} else if (key.equalsString("<S-Tab>")) {
			return Result.PREVIOUS_INTERACTABLE_UP;
		}
		return Result.EVENT_HANDLED;
	}

	@Override
	public TerminalSize getPreferredSize() {
		if (poppedUp) {
			return super.getPreferredSize();
		}
//		// TODO size is not correct because of getCheckedItemIndex returning -1
//		System.out.println("" + getCheckedItemIndex());
		int width = createItemString(getCheckedItemIndex()).length();
		return new TerminalSize(width+1, 1);
	}
	
}
