package com.ryougi.iframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ryougi.adapter.BtnMouseAdapter;
import com.ryougi.adapter.PsdFocusedAdapter;
import com.ryougi.module.ImagePanel;
import com.ryougi.util.LocationCtrl;
import com.ryougi.util.OtherResolver;
import com.ryougi.util.SizeCtrl;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	
	private static final Font DEFAULT_FONT = new Font("微软雅黑", Font.BOLD,16);
	
	private String title = "登录";
	private String imgVisURL = "res\\ic_2233.png";												// 左下图片资源路径(Visible)
	private String imgHideURL = "res\\ic_2233_hide.png";									// 左下图片资源路径(Hide)
	
	private JPanel tPanel;
	private JPanel bVisPanel;
	private JPanel bHidePanel;
	private JPanel mPanel = new JPanel();
	private GridBagLayout bagLayout;
	private GridBagConstraints bagConstraints;
	private JLabel uLabel = new JLabel("账 号    ");
	private JLabel pLabel = new JLabel("密 码    ");
	private JLabel bLabel_0 = new JLabel(" ");
	private JLabel bLabel_1 = new JLabel(" ");
	private JLabel bLabel_2 = new JLabel(" ");
	private JLabel bLabel_3 = new JLabel(" ");
	private JTextField user;
	private JPasswordField password;
	private ArrayList<Object> list = new ArrayList<>();
	private Color LIGHT_BLUE_COLOR = new Color(120, 150, 232);
	private Color BG_COLOR = new Color(240, 240, 240);
	private JButton submitBtn;
	private JButton resetBtn;
	
	public void initLayout() {
		tPanel = new JPanel();																				// 初始化顶部的JPanel
		bVisPanel = new  ImagePanel(imgVisURL, getWidth(), getWidth() * 5/32, 0, getWidth()*3/8);
		bHidePanel = new  ImagePanel(imgHideURL, getWidth(), getWidth() * 5/32, 0, getWidth()*3/8);
		user = new JTextField();
		password = new JPasswordField();
		submitBtn = new JButton("登  录");
		resetBtn = new JButton("重  置");
		list.add(user);
		list.add(password);
	}
	
	public LoginFrame() {
		new OtherResolver().setIcon(this);
		setTitle(title);
		setLocation(LocationCtrl.otherFrameLocationCtrl());
		frameSizeCtrl();
		initLayout();
		
		mPanel.setBackground(BG_COLOR);
		mPanel.setLayout(null);
		add(mPanel);
		addHidePanel();
		addVisPanel();	
		addTopPanel();	
//		list.add(user);
//		list.add(password);

		setResizable(false);
	}
	/**
	 * 初始化登录表单，系统管理员在此键入账号密码登录系统
	 */
	public void addTopPanel() {
		uLabel.setForeground(LIGHT_BLUE_COLOR);
		uLabel.setFont(DEFAULT_FONT);
		pLabel.setForeground(LIGHT_BLUE_COLOR);
		pLabel.setFont(DEFAULT_FONT);
		
		bagLayout = new GridBagLayout();																// 初始化一个网布局
		bagConstraints = new GridBagConstraints();												// 初始化网布局参数

		bagConstraints.fill = GridBagConstraints.BOTH;
		bagConstraints.gridwidth = GridBagConstraints.REMAINDER; 						// 用户行结束
		tPanel.add(uLabel);
		tPanel.add(initUserField());
		bagLayout.setConstraints(user, bagConstraints);

		bagConstraints.gridwidth = GridBagConstraints.REMAINDER; 						// 空白行结束
		bLabel_0.setPreferredSize(new Dimension(1, 10));
		tPanel.add(bLabel_0);
		bagLayout.setConstraints(bLabel_0, bagConstraints);

		bagConstraints.gridwidth = GridBagConstraints.REMAINDER; 						// 密码行结束
		tPanel.add(pLabel);
		tPanel.add(initPasswordField());
		bagLayout.setConstraints(password, bagConstraints);
		
		bagConstraints.gridwidth = GridBagConstraints.REMAINDER; 						// 空白行结束
		bLabel_1.setPreferredSize(new Dimension(1, 10));
		tPanel.add(bLabel_1);
		bagLayout.setConstraints(bLabel_1, bagConstraints);
		
		bLabel_2.setPreferredSize(new Dimension(1, 10));
		tPanel.add(bLabel_2);
		
		initBtn();
		
		tPanel.setPreferredSize(new Dimension(getWidth(), getWidth()*3/8));
		tPanel.setBackground(BG_COLOR);
		tPanel.setLayout(bagLayout);
		tPanel.setBounds(0, 0, getWidth(), getWidth()*3/8);
		mPanel.add(tPanel);
	}
	/**
	 * 初始化底部图片
	 */
	public void addVisPanel() {
		bVisPanel.setPreferredSize(new Dimension(getWidth(), getWidth()*3/16));
		bVisPanel.setBackground(BG_COLOR);
		bVisPanel.setVisible(true);
		mPanel.add(bVisPanel);
	}
	public void addHidePanel() {
		bHidePanel.setPreferredSize(new Dimension(getWidth(), getWidth()*3/16));
		bHidePanel.setBackground(BG_COLOR);
		bHidePanel.setVisible(false);
		mPanel.add(bHidePanel);
	}
	
	/**
	 * 初始化账号输入框
	 * @return 返回初始化后的文本输入框
	 */
	public JTextField initUserField() {
		user.setPreferredSize(new Dimension(200, 30));
		user.setBorder(BorderFactory.createEtchedBorder(LIGHT_BLUE_COLOR, LIGHT_BLUE_COLOR));
		user.setForeground(LIGHT_BLUE_COLOR);
		user.setBackground(null);
		return user;
	}
	/**
	 * 初始化密码输入框
	 * @return 返回初始化后的密码输入框
	 */
	public JPasswordField initPasswordField() {
		password.setPreferredSize(new Dimension(200, 30));
		password.setBorder(BorderFactory.createEtchedBorder(LIGHT_BLUE_COLOR, LIGHT_BLUE_COLOR));
		password.setForeground(LIGHT_BLUE_COLOR);
		password.setBackground(null);
		password.addFocusListener(new PsdFocusedAdapter(password, bVisPanel, bHidePanel));
		return password;
	}
	/**
	 * 初始化登陆和重置按钮
	 */
	public void initBtn() {
		submitBtn.setBackground(LIGHT_BLUE_COLOR);
		submitBtn.setForeground(Color.WHITE);
		submitBtn.setBorder(null);
		submitBtn.setPreferredSize(new Dimension(90, 30));
		submitBtn.setFont(DEFAULT_FONT);

		resetBtn.setBackground(LIGHT_BLUE_COLOR);
		resetBtn.setForeground(Color.WHITE);
		resetBtn.setBorder(null);
		resetBtn.setPreferredSize(new Dimension(90, 30));
		resetBtn.setFont(DEFAULT_FONT);
		
		tPanel.add(submitBtn);
		bLabel_3.setPreferredSize(new Dimension(20, 10));
		tPanel.add(bLabel_3);
		tPanel.add(resetBtn);

		submitBtn.addMouseListener(new BtnMouseAdapter(this, submitBtn, list));
		resetBtn.addMouseListener(new BtnMouseAdapter(this, resetBtn, list));
	}
	
	/**
	 * 主窗口尺寸控制
	 */
	public void frameSizeCtrl() {
		setSize(SizeCtrl.otherFrameSizeCtrl());
	}
	
}
