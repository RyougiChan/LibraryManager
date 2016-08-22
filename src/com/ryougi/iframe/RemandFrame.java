package com.ryougi.iframe;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ryougi.adapter.AutoFillAdapter;
import com.ryougi.adapter.BtnMouseAdapter;
import com.ryougi.module.FormFrame;
import com.ryougi.module.RyougiModule;
import com.ryougi.util.OtherResolver;


@SuppressWarnings("serial")
public class RemandFrame extends FormFrame {
	
	private final int FRAME_WIDTH = this.getWidth();						// 当前窗口宽度
	private final int FRAME_HEIGHT = this.getHeight();						// 当前窗口高度
	private final int DEFAULT_MARGIN_TOP = FRAME_HEIGHT*11/90;				// 默认边界高度
	
	private String imgHideURL = "res\\ic_no_followed_2233.png";
//	private String querySQL = "SELECT tb_borrow.borrowDate, tb_borrow.backDate "
//							+ "FROM tb_borrow "
//							+ "WHERE tb_borrow.bookISBN = ?";				// 借出书的 ISBN 数据库查询
	private static Color BG_COLOR = new Color(120, 150, 232);				// 背景颜色

	// 顶部表单面板，底部背景面板，ISBN输入条目，借出时间输入条目，归还时间显示条目，超期罚款显示条目
	private JPanel tPanel, bPanel, ISBN, bDate, rDate, fine;
	private JLabel detect, notice;		// 提示文字
	private JButton rButton;			// 提交按钮
	// ISBN输入框，借出时间输入框，归还时间显示框，超期罚款显示框
	private JTextField ISBNField, bDateField, rDateField, fineField;

	private ArrayList<Object> list = new ArrayList<>();											// 对象（输入框）列表
	
	public void initModule() {
		bPanel = RyougiModule.ImagePanel(this, imgHideURL, FRAME_WIDTH, FRAME_WIDTH*31/160, 0,FRAME_HEIGHT - FRAME_WIDTH*31/160 -30);
		tPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH, FRAME_HEIGHT - bPanel.getHeight() -30, 0, 0);
		ISBN = RyougiModule.TextFieldWithLabel(tPanel, "    ISBN", "", 20, 0, DEFAULT_MARGIN_TOP);
		bDate = RyougiModule.TextFieldWithLabel(tPanel, "借出日期", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP);
		rDate = RyougiModule.TextFieldWithLabel(tPanel, "应还日期", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 50);
		fine = RyougiModule.TextFieldWithLabel(tPanel, "应收罚款", "", 20, FRAME_WIDTH/2 - 8, DEFAULT_MARGIN_TOP + 100);
		detect = RyougiModule.NoticeLabel(tPanel, "是否过期还书?", 20, null, Color.PINK, 280, 40, 32, DEFAULT_MARGIN_TOP +50);
		notice = RyougiModule.NoticeLabel(tPanel, " 提示:输入书籍的ISBN检测是否过期", 14, null, BG_COLOR, 280, 40, 32, DEFAULT_MARGIN_TOP +100);
		rButton = RyougiModule.ColorButton(bPanel, "回收确认", 20, BG_COLOR, 120, 36, FRAME_WIDTH/2 - 8, 10);
		ISBNField = (JTextField)ISBN.getComponent(1);
		bDateField = (JTextField)bDate.getComponent(1);
		rDateField = (JTextField)rDate.getComponent(1);
		fineField = (JTextField)fine.getComponent(1);
		bDateField.setEditable(false);
		rDateField.setEditable(false);
		fineField.setEditable(false);
		ISBNField.addFocusListener(new AutoFillAdapter(this, ISBNField));
		list.add(ISBNField);
		list.add(bDateField);
		list.add(rDateField);
		list.add(fineField);
	}
	
	public RemandFrame() {
		new OtherResolver().setIcon(this);
		setTitle("  归还");
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
		tPanel.add(rDate);
		tPanel.add(fine);
		tPanel.add(detect);
		tPanel.add(notice);
		bPanel.add(rButton);
		add(bPanel);
		add(tPanel);
		rButton.setFocusable(false);
		rButton.addMouseListener(new BtnMouseAdapter(this, rButton, list));
	}
}
