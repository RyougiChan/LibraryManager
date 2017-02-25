package com.ryougi.adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.ryougi.Library;
import com.ryougi.iframe.AuthorityFrame;
import com.ryougi.iframe.BorrowFrame;
import com.ryougi.iframe.LoginFrame;
import com.ryougi.iframe.SearchFrame;

public class LabelAdapter extends MouseAdapter {
	
	private String labelText;
	private JFrame curFrame;
	
	public LabelAdapter(JFrame curFrame, String labelText) {
		this.labelText = labelText;
		this.curFrame = curFrame;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		switch(labelText.replaceAll(" ", "")) {
		case "检索":
			new SearchFrame().setVisible(true);
			curFrame.setVisible(false);
			break;
		case "借阅":
			new BorrowFrame().setVisible(true);
			break;
		case "登录":
			new LoginFrame().setVisible(true);
			break;
		case "授权":
			new AuthorityFrame().setVisible(true);
			break;
			// 这是菜单栏的
		case "回到首页":
			if (curFrame.getTitle().equals("图书检索")) {
				curFrame.setVisible(false);
				curFrame.dispose();
				new Library().setVisible(true);
			}
			break;
		}
	}
}
