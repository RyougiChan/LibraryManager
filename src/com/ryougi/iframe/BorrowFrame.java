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
public class BorrowFrame extends FormFrame {

	private final int FRAME_WIDTH = this.getWidth();						// 当前窗口宽度
	private final int FRAME_HEIGHT = this.getHeight();						// 当前窗口高度
	private final int DEFAULT_MARGIN_TOP = FRAME_HEIGHT*11/90;				// 默认边界高度
	
	private String imgFollowURL = "res\\ic_no_followed_2233.png";

	private static Color BG_COLOR = new Color(120, 150, 232);				// 背景颜色
	
	// 顶部表单面板，底部背景面板，ISBN输入，读者证件号输入，书籍名称，应还时间显示，借书权限显示
	private JPanel tPanel, bPanel, ISBN, readerId, bookName, bDate, tDate, authority;
	private JButton sButton, cButton;			// 提交按钮，取消按钮

	// 读者证件号输入框，ISBN输入框，借出时间显示框，书籍名称显示框，归还时间显示框，借书权限显示框
	private JTextField readerIdField, ISBNField, bDateField, bookNameField, rDateField, authorityField;
	private ArrayList<Object> list = new ArrayList<>();						// 对象（输入框）集合
	
	public void initModule() {
		bPanel = RyougiModule.ImagePanel(this, imgFollowURL, FRAME_WIDTH, FRAME_WIDTH*31/160, 0,FRAME_HEIGHT - FRAME_WIDTH*31/160 -30);
		tPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH, FRAME_HEIGHT - bPanel.getHeight() -30, 0, 0);

		readerId = RyougiModule.TextFieldWithLabel(tPanel, "  证件号", "", 20, 3, DEFAULT_MARGIN_TOP);
		ISBN = RyougiModule.TextFieldWithLabel(tPanel, "     ISBN", "", 20, 0, DEFAULT_MARGIN_TOP + 50);
		bookName = RyougiModule.TextFieldWithLabel(tPanel, "      书名", "", 20, 2, DEFAULT_MARGIN_TOP + 100);
		bDate = RyougiModule.TextFieldWithLabel(tPanel, "借出日期", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP);
		tDate = RyougiModule.TextFieldWithLabel(tPanel, "应还日期", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 50);
		authority = RyougiModule.TextFieldWithLabel(tPanel, "借书权限", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 100);
		sButton = RyougiModule.ColorButton(bPanel, "借 出", 20, BG_COLOR, 120, 36, FRAME_WIDTH/2 - 157, 10);
		cButton = RyougiModule.ColorButton(bPanel, "取 消", 20, BG_COLOR, 120, 36, FRAME_WIDTH/2, 10);
		readerIdField = (JTextField)readerId.getComponent(1);
		ISBNField = (JTextField)ISBN.getComponent(1);
		bDateField = (JTextField)bDate.getComponent(1);
		bookNameField = (JTextField)bookName.getComponent(1);
		rDateField = (JTextField)tDate.getComponent(1);
		authorityField = (JTextField)authority.getComponent(1);
		readerIdField.addFocusListener(new AutoFillAdapter(this, readerIdField));
		ISBNField.addFocusListener(new AutoFillAdapter(this, ISBNField));
		bDateField.setEditable(false);
		rDateField.setEditable(false);
		authorityField.setEditable(false);
		bookNameField.setEditable(false);
		list.add(readerIdField);
		list.add(ISBNField);
		list.add(bDateField);
		list.add(bookNameField);
		list.add(rDateField);
		list.add(authorityField);
	}
	
	public BorrowFrame() {
		new OtherResolver().setIcon(this);
		setTitle("  借书");
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
		tPanel.add(ISBN);
		tPanel.add(bDate);
		tPanel.add(readerId);
		tPanel.add(tDate);
		tPanel.add(bookName);
		tPanel.add(authority);
		bPanel.add(sButton);
		bPanel.add(cButton);
		add(bPanel);
		add(tPanel);
		sButton.setFocusable(false);
		cButton.setFocusable(false);
		sButton.addMouseListener(new BtnMouseAdapter(this, sButton, list));
		cButton.addMouseListener(new BtnMouseAdapter(this, cButton, list));
	}
	
}

