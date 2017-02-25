package com.ryougi.adapter;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;
import javax.swing.JPasswordField;

public class PsdFocusedAdapter extends FocusAdapter {
	
	private JComponent childRemove;
	private JComponent childAdd;
	private JPasswordField password;
	
	public PsdFocusedAdapter(JPasswordField password, JComponent childRemove, JComponent childAdd) {
		this.childRemove = childRemove;
		this.childAdd = childAdd;
		this.password = password;
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		childAdd.setVisible(true);
		childRemove.setVisible(false);
		password.setText("");
	}
	
	@Override
	public void focusLost(FocusEvent e) {
		childAdd.setVisible(false);
		childRemove.setVisible(true);
	}
}
