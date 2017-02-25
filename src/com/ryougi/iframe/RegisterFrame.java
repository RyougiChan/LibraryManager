package com.ryougi.iframe;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ryougi.adapter.AutoFillAdapter;
import com.ryougi.adapter.BtnMouseAdapter;
import com.ryougi.module.FormFrame;
import com.ryougi.module.RyougiModule;
import com.ryougi.util.OtherResolver;

@SuppressWarnings("serial")
public class RegisterFrame extends FormFrame {

	private final int FRAME_WIDTH = this.getWidth();						// 当前窗口宽度
	private final int FRAME_HEIGHT = this.getHeight();						// 当前窗口高度
	private final int DEFAULT_MARGIN_TOP = FRAME_HEIGHT*11/90;				// 默认边界高度
	
	private String imgFollowURL = "res\\ic_followed_2233.png";
	private String title;

	private static Color BG_COLOR = new Color(120, 150, 232);				// 背景颜色
	
	// 顶部表单面板，底部背景面板，读者ID输入，读者身份输入，读者性别输入，读者姓名输入，读者电话输入，读者邮箱输入
	private JPanel tPanel, bPanel, readerId, identify, sex, name, tel, email;
	private JButton rButton, cButton;										// 提交按钮，取消按钮

	// 读者ID输入框，读者身份输入框，读者性别输入框，读者姓名输入框，读者电话输入框，读者邮箱输入框
	private JTextField readerIdField, identifyField, nameField, sexField, telField, emailField;
	private ArrayList<Object> list = new ArrayList<>();						// 对象（输入框）集合
	
	public void initModule() {
		bPanel = RyougiModule.ImagePanel(this, imgFollowURL, FRAME_WIDTH, FRAME_WIDTH*31/160, 0,FRAME_HEIGHT - FRAME_WIDTH*31/160 -30);
		tPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH, FRAME_HEIGHT - bPanel.getHeight() -30, 0, 0);

		readerId = RyougiModule.TextFieldWithLabel(tPanel, "  证件号", "", 20, 1, DEFAULT_MARGIN_TOP);
		identify = RyougiModule.TextFieldWithLabel(tPanel, "      角色", "", 20, 0, DEFAULT_MARGIN_TOP + 50);
		sex = RyougiModule.TextFieldWithLabel(tPanel, "      性别", "0-其他? | -1-女 | 1-男", 20, 0, DEFAULT_MARGIN_TOP + 100);
		name = RyougiModule.TextFieldWithLabel(tPanel, "    姓名", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP);
		tel = RyougiModule.TextFieldWithLabel(tPanel, "    电话", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 50);
		email = RyougiModule.TextFieldWithLabel(tPanel, "    邮箱", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 100);
		rButton = RyougiModule.ColorButton(bPanel, "注 册", 20, BG_COLOR, 120, 36, FRAME_WIDTH/2 - 157, 10);
		cButton = RyougiModule.ColorButton(bPanel, "取 消", 20, BG_COLOR, 120, 36, FRAME_WIDTH/2 + 34, 10);
		readerIdField = (JTextField)readerId.getComponent(1);
		identifyField = (JTextField)identify.getComponent(1);
		nameField = (JTextField)name.getComponent(1);
		sexField = (JTextField)sex.getComponent(1);
		telField = (JTextField)tel.getComponent(1);
		emailField = (JTextField)email.getComponent(1);
		
		identifyField.setEditable(false);
		if (title.trim().equals("操作员")) {
			identifyField.setText(title);
		} else {
			identifyField.setText(" 读者");
		}
		
		sexField.addFocusListener(new AutoFillAdapter(this, sexField));
		
		list.add(readerIdField);
		list.add(identifyField);
		list.add(nameField);
		list.add(sexField);
		list.add(telField);
		list.add(emailField);
	}
	
	public RegisterFrame(String title) {
		this.title = title;

		new OtherResolver().setIcon(this);
		setTitle("  "+title);
		setLayout(null);
		
		initModule();
		initFormPanel();
	}

	/**
	 * 初始化布局
	 */
	public void initFormPanel() {
		tPanel.setBackground(null);
		bPanel.setBackground(null);
		tPanel.setForeground(null);
		bPanel.setForeground(null);
		tPanel.setLayout(null);
		bPanel.setLayout(null);
		tPanel.add(identify);
		tPanel.add(name);
		tPanel.add(readerId);
		tPanel.add(tel);
		tPanel.add(sex);
		tPanel.add(email);
		bPanel.add(rButton);
		bPanel.add(cButton);
		add(bPanel);
		add(tPanel);
		rButton.setFocusable(false);
		cButton.setFocusable(false);
		rButton.addMouseListener(new BtnMouseAdapter(this, rButton, list));
		cButton.addMouseListener(new BtnMouseAdapter(this, cButton, list));
	}
	
}
