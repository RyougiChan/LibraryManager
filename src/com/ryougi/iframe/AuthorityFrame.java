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
public class AuthorityFrame extends FormFrame {
	
	private final int FRAME_WIDTH = this.getWidth();						// 当前窗口宽度
	private final int FRAME_HEIGHT = this.getHeight();						// 当前窗口高度
	private final int DEFAULT_MARGIN_TOP = (FRAME_HEIGHT - 216)/2;				// 默认边界高度

	private String imgFollowURL = "res\\ic_certified_id.png";

	private static Color BG_COLOR = new Color(120, 150, 232);				// 背景颜色
	
	// 左部背景面板，底部表单面板，左部面板背景，读者证件号输入，读者姓名输入，权限状态显示
	private JPanel lPanel, rPanel, lImgPanel, readerId, name, authority;
	private JButton aButton, cButton;										// 提交按钮，取消按钮

	// 读者证件号输入框，读者姓名输入框，权限状态显示框
	private JTextField readerIdField, nameField, authorityField;
	private ArrayList<Object> list = new ArrayList<>();						// 对象（输入框）集合
	
	public void initModule() {
		lPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH/2, FRAME_HEIGHT, 0,0);
		lImgPanel = RyougiModule.ImagePanel(this, imgFollowURL, FRAME_WIDTH * 5/12, FRAME_WIDTH * 25/101, FRAME_WIDTH/24, FRAME_WIDTH/7);
		rPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH/2, FRAME_HEIGHT, FRAME_WIDTH/2, 0);
		
		readerId = RyougiModule.TextFieldWithLabel(rPanel, "证件号", "", 20, 8, DEFAULT_MARGIN_TOP);
		name = RyougiModule.TextFieldWithLabel(rPanel, "    姓名", "", 20, 8, DEFAULT_MARGIN_TOP + 50);
		authority = RyougiModule.TextFieldWithLabel(rPanel, "    权限", "", 20, 8, DEFAULT_MARGIN_TOP + 100);
		
		aButton = RyougiModule.ColorButton(rPanel, "授 权", 20, BG_COLOR, 120, 36, (FRAME_WIDTH/2-280)/2 + 8, DEFAULT_MARGIN_TOP + 150);
		cButton = RyougiModule.ColorButton(rPanel, "取 消", 20, BG_COLOR, 120, 36, FRAME_WIDTH/4, DEFAULT_MARGIN_TOP + 150);
		
		readerIdField = (JTextField)readerId.getComponent(1);
		nameField = (JTextField)name.getComponent(1);
		authorityField = (JTextField)authority.getComponent(1);
		
		readerIdField.addFocusListener(new AutoFillAdapter(this, readerIdField));
		nameField.setEditable(false);
		authorityField.setEditable(false);
		
		list.add(readerIdField);
		list.add(nameField);
		list.add(authorityField);
		
	}
	
	public AuthorityFrame() {
		new OtherResolver().setIcon(this);
		setTitle("  借书权限");
		setLayout(null);
		
		initModule();
		initFormPanel();
	}
	/**
	 * 初始化布局
	 */
	public void initFormPanel() {
		lPanel.setBackground(null);
		rPanel.setBackground(null);
		lPanel.setForeground(null);
		rPanel.setForeground(null);
		lImgPanel.setBackground(null);
		lImgPanel.setForeground(null);
		lPanel.setLayout(null);
		rPanel.setLayout(null);
		lPanel.add(lImgPanel);
		rPanel.add(readerId);
		rPanel.add(name);
		rPanel.add(authority);
		rPanel.add(aButton);
		rPanel.add(cButton);
		add(lPanel);
		add(rPanel);
		aButton.setFocusable(false);
		cButton.setFocusable(false);
		aButton.addMouseListener(new BtnMouseAdapter(this, aButton, list));
		cButton.addMouseListener(new BtnMouseAdapter(this, cButton, list));
	}
}
