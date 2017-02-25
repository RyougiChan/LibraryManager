package com.ryougi.module;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.ryougi.util.LocationCtrl;
import com.ryougi.util.OtherResolver;
import com.ryougi.util.SizeCtrl;

@SuppressWarnings("serial")
public class FormFrame extends JFrame {

	private String title = "登录";

	private JPanel mPanel;
	
	private Color BG_COLOR = new Color(240, 240, 240);
	
	public FormFrame() {
		new OtherResolver().setIcon(this);
		setTitle(title);
		setLocation(LocationCtrl.otherFrameLocationCtrl());
		setSize(SizeCtrl.otherFrameSizeCtrl());
		
		mPanel = new JPanel();
		mPanel.setBackground(BG_COLOR);
		mPanel.setLayout(null);
		add(mPanel);

		setResizable(false);
	}
	
}
