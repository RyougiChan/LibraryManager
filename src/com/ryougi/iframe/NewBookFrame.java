package com.ryougi.iframe;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ryougi.adapter.BtnMouseAdapter;
import com.ryougi.module.FormFrame;
import com.ryougi.module.RyougiModule;
import com.ryougi.util.OtherResolver;

@SuppressWarnings("serial")
public class NewBookFrame extends FormFrame {

	private final int FRAME_WIDTH = this.getWidth();						// 当前窗口宽度
	private final int FRAME_HEIGHT = this.getHeight();						// 当前窗口高度
	private final int DEFAULT_MARGIN_TOP = FRAME_HEIGHT/20;				// 默认边界高度
	
	private String imgFollowURL = "res\\ic_followed_2233.png";

	private static Color BG_COLOR = new Color(120, 150, 232);				// 背景颜色
	
	// 顶部表单面板，底部背景面板，ISBN输入，书名输入，出版社输入，年份输入，著者输入，价格输入
	private JPanel tPanel, bPanel, ISBN, bookName, year, publisher, author, price, index, number, translator, face;
	private JButton rButton, cButton;										// 提交按钮，取消按钮

	// ISBN输入框，书名输入框，出版社输入框，年份输入框，著者输入框，价格输入框
	private JTextField ISBNField, bookNameField, publisherField, yearField, authorField, priceField, indexField, numberField, translatorField, faceField;
	private ArrayList<Object> list = new ArrayList<>();						// 对象（输入框）集合
	
	public void initModule() {
		bPanel = RyougiModule.ImagePanel(this, imgFollowURL, FRAME_WIDTH, FRAME_WIDTH*31/160, 0,FRAME_HEIGHT - FRAME_WIDTH*31/160 -30);
		tPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH, FRAME_HEIGHT - bPanel.getHeight() -30, 0, 0);

		ISBN = RyougiModule.TextFieldWithLabel(tPanel, "     ISBN", "", 20, 1, DEFAULT_MARGIN_TOP);
		bookName = RyougiModule.TextFieldWithLabel(tPanel, "      书名", "", 20, 0, DEFAULT_MARGIN_TOP + 35);
		year = RyougiModule.TextFieldWithLabel(tPanel, "      年份", "", 20, 0, DEFAULT_MARGIN_TOP + 70);
		publisher = RyougiModule.TextFieldWithLabel(tPanel, "出版社", "", 20, FRAME_WIDTH/2 - 7, DEFAULT_MARGIN_TOP);
		author = RyougiModule.TextFieldWithLabel(tPanel, "    著者", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 35);
		price = RyougiModule.TextFieldWithLabel(tPanel, "    价格", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 70);
		index = RyougiModule.TextFieldWithLabel(tPanel, "索书号", "", 20, FRAME_WIDTH/2 - 7, DEFAULT_MARGIN_TOP + 105);
		number = RyougiModule.TextFieldWithLabel(tPanel, "      数量", "", 20, 0, DEFAULT_MARGIN_TOP + 105);
		translator = RyougiModule.TextFieldWithLabel(tPanel, "      译者", "", 20, 0, DEFAULT_MARGIN_TOP + 140);
		face = RyougiModule.TextFieldWithLabel(tPanel, "    封面", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 140);
		
		rButton = RyougiModule.ColorButton(bPanel, "新书入库", 20, BG_COLOR, 120, 36, FRAME_WIDTH/2 - 157, 10);
		cButton = RyougiModule.ColorButton(bPanel, "取 消", 20, BG_COLOR, 120, 36, FRAME_WIDTH/2 + 34, 10);
		ISBNField = (JTextField)ISBN.getComponent(1);
		bookNameField = (JTextField)bookName.getComponent(1);
		publisherField = (JTextField)publisher.getComponent(1);
		yearField = (JTextField)year.getComponent(1);
		priceField = (JTextField)price.getComponent(1);
		authorField = (JTextField)author.getComponent(1);
		indexField = (JTextField)index.getComponent(1);
		numberField = (JTextField)number.getComponent(1);
		translatorField = (JTextField)translator.getComponent(1);
		faceField = (JTextField)face.getComponent(1);

		list.add(ISBNField);
		list.add(bookNameField);
		list.add(authorField);
		list.add(translatorField);
		list.add(publisherField);
		list.add(yearField);
		list.add(indexField);
		list.add(numberField);
		list.add(priceField);
		list.add(faceField);
	}
	
	public NewBookFrame() {
		new OtherResolver().setIcon(this);
		setTitle("  新书入库");
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
		tPanel.add(bookName);
		tPanel.add(publisher);
		tPanel.add(ISBN);
		tPanel.add(author);
		tPanel.add(year);
		tPanel.add(price);
		tPanel.add(index);
		tPanel.add(face);
		tPanel.add(number);
		tPanel.add(translator);
		bPanel.add(rButton);
		bPanel.add(cButton);
		add(bPanel);
		add(tPanel);
		rButton.setFocusable(false);
		cButton.setFocusable(false);
		rButton.addMouseListener(new BtnMouseAdapter(this, rButton, list));// 添加新书入库按钮监听器
		cButton.addMouseListener(new BtnMouseAdapter(this, cButton, list));// 添加取消按钮监听器
	}
	
}
