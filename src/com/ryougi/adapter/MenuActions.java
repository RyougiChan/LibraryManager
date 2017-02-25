package com.ryougi.adapter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.ryougi.iframe.AuthorityFrame;
import com.ryougi.iframe.BorrowFrame;
import com.ryougi.iframe.DonateModifyFrame;
import com.ryougi.iframe.LibraryPolicyFrame;
import com.ryougi.iframe.NewBookFrame;
import com.ryougi.iframe.RegisterFrame;
import com.ryougi.iframe.RemandFrame;
import com.ryougi.iframe.SysMessageFrame;

public class MenuActions implements ActionListener {
	
	private String key;
	
	public MenuActions(JFrame frame, String key) {
		this.key = key;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (key) {
		case "借书":
			new BorrowFrame().setVisible(true);
			break;
		case "归还":
			new RemandFrame().setVisible(true);
			break;
		case "购书":
			new NewBookFrame().setVisible(true);
			break;
		case "赠书":
			new DonateModifyFrame("赠书", "捐赠入库").setVisible(true);
			break;
		case "修改":
			new DonateModifyFrame("书籍信息修改", "提交修改").setVisible(true);
			break;
		case "操作员":
			new RegisterFrame("操作员").setVisible(true);
			break;
		case "读者":
			new RegisterFrame("读者").setVisible(true);
			break;
		case "借书权限":
			new AuthorityFrame().setVisible(true);
			break;
		case "借阅须知":
			new LibraryPolicyFrame().setVisible(true);
			break;
		case "系统信息":
			new SysMessageFrame().setVisible(true);
			break;
		case "系统升级":
			
			break;

		default:
			break;
		}
	}
}

