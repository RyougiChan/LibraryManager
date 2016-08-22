package com.ryougi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

import com.ryougi.adapter.LabelAdapter;
import com.ryougi.module.ImagePanel;
import com.ryougi.module.RyougiModule;
import com.ryougi.ui.TranslucentMenu;
import com.ryougi.util.LocationCtrl;
import com.ryougi.util.OtherResolver;
import com.ryougi.util.SizeCtrl;

@SuppressWarnings("serial")
public class Library extends JFrame {
	
	private int FRAME_WIDTH = SizeCtrl.mainFrameSizeCtrl().width;									// 主窗口宽度
	private int FRAME_HEIGHT = SizeCtrl.mainFrameSizeCtrl().height;									// 主窗口高度
	
	private String title = "图书馆管理系统";															// 系统名称
	private String copyright = "<html><div style='text-align:center;text-style:bold;width:960px;height:30px;line-height:30px;'>技术支持: RyougiChan &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+ "Email : ryougi.chan.fire@gmail.com</div></html>";									// 底部版权信息
	private String libImgURL = "res\\Library.jpg";
	private String mLogoURL = "res\\ryougi_logo.png";												// 设计者logo所在路径
	private String sLogoURL = "res\\LMS.png";														// 系统logo所在路径
	private String[] manage = new String[] {"书籍管理", "人员管理", "其他管理", "系统管理", "回到首页"};			// 菜单项文本
	private String[] bookMng = new String[] {"    借书 ", "    归还 ", "    购书 ", "    赠书 ", "    修改 "};		// 次级菜单项文本
	private String[] userMng = new String[] {"  操作员", "    读者"};
	private String[] otherMng = new String[] {"借书权限", "借阅须知"};
	private String[] sysMng = new String[] {"系统信息", "系统升级"};
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();							// 系统工具包
//	private final int SCREEN_WIDTH = toolkit.getScreenSize().width;					// 获取当前主屏幕宽度
//	private final int SCREEN_HEIGHT = toolkit.getScreenSize().height;				// 获取当前主屏幕高度
	
//	private static final Color COLOR_BG = new Color(0, 0, 0, 0.25f);								// 背景颜色
	private static final Color COLOR_FG = Color.WHITE;											// 前景颜色
	private static final Color ALPHA_ZERO = new Color(0x0, true);
	private static final Color POPUP_BACK = new Color(0, 0, 0, 0.25f);
	private static final Color LIGHT_BLUE_COLOR = new Color(120, 150, 232);				// 系统主题颜色
	private static final Color LIGHT_YERROW_COLOR = new Color(200, 230, 0);			
	private static final Color LIGHT_GREEN_COLOR = new Color(50, 255, 100);			
	private static final Color LIGHT_ORANGE_COLOR = new Color(255, 100, 0);
//	private static final Dimension MENUITEM_SIZE = new Dimension(90, 44);
	private static final Font DEFAULT_FONT = new Font("微软雅黑", Font.BOLD,15);
//	private JPanel rootPanel;														// 根容器面板
	private JMenuBar jMenuBar;														// 菜单栏
	private JPanel bodyPanel;														// 主内容面板
	private JPanel mlogo, slogo;													// 设计者logo和系统logo
	private JLabel boxLT, boxRT, boxLB, boxRB;
	private JPanel footPanel;														// 底部面板
	private JLabel footLabel;														// 底部版权标签
	private JMenuBar menuBar;
	
	
	public void initModule() {
//		rootPanel = RyougiModule.ImagePanel(this, libImgURL, FRAME_WIDTH, FRAME_HEIGHT, 0, 0);
		bodyPanel = new JPanel();
		footPanel = new JPanel();
		footLabel = RyougiModule.NoticeLabel(footPanel, copyright, 16, POPUP_BACK, COLOR_FG, FRAME_WIDTH, 60, 0,0);
		menuBar = addMenuBar();
	}
	
	public Library() {
		super();
		frameSizeCtrl();
		new OtherResolver().setIcon(this);
		setTitle(title);
		setLocation(LocationCtrl.mainFrameLocationCtrl());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		manageUI();
		initModule();

	    ((JComponent) getContentPane()).setOpaque(false);
		getRootPane().setBackground(Color.black);
		setJMenuBar(menuBar);
		menuBar.setSize(getPreferredSize());
		addBodyPanel();
		addFootPanel();
		
		setResizable(false);
	}
	
	public void manageUI() {
		UIManager.put("MenuBar.border", BorderFactory.createEmptyBorder());
		UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder());

		UIManager.put("Menu.foreground", Color.WHITE);
		UIManager.put("Menu.font", DEFAULT_FONT);
		UIManager.put("Menu.background", ALPHA_ZERO);
		UIManager.put("Menu.selectionBackground", POPUP_BACK);
		UIManager.put("Menu.selectionForeground", LIGHT_BLUE_COLOR);
		UIManager.put("Menu.borderPainted", Boolean.FALSE);

		UIManager.put("MenuItem.foreground", Color.WHITE);
		UIManager.put("MenuItem.font", DEFAULT_FONT);
		UIManager.put("MenuItem.background", ALPHA_ZERO);
		UIManager.put("MenuItem.selectionBackground", POPUP_BACK);
		UIManager.put("MenuItem.selectionForeground", LIGHT_BLUE_COLOR);
		UIManager.put("MenuItem.borderPainted", Boolean.FALSE);
	}
	
	@Override
	protected JRootPane createRootPane() {
		return new JRootPane() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Image img = toolkit.getImage(libImgURL);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setPaint(new Color(0, 0, 0, 0));
				g2.drawImage(img, 0, 0, FRAME_WIDTH, FRAME_HEIGHT, this);
				g2.fillRect(0, 0, FRAME_WIDTH,FRAME_HEIGHT);
				g2.dispose();
			}
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
			}
			@Override
			public boolean isOpaque() {
				return true;
			}
		};
	}
	
	/**
	 * 添加菜单栏，并向菜单栏添加菜单项和次级菜单
	 * 如果当前菜单项为书籍管理，添加次级菜单列表——借书，归还，购书，赠书，检索
	 * 如果当前菜单项为人员管理，添加次级菜单列表——操作员，学生，教职工
	 * 如果当前菜单项为其他管理，添加次级菜单列表——借书权限，丢失赔偿
	 * 如果当前菜单项为系统管理，添加次级菜单列表——系统信息，系统升级
	 * @return 返回一个重新设定样式的菜单，包括背景，前景，边界
	 */
	public JMenuBar addMenuBar() {
		List<String[]> seckeys = new ArrayList<>();
		seckeys.add(bookMng);
		seckeys.add(userMng);
		seckeys.add(otherMng);
		seckeys.add(sysMng);
		TranslucentMenu tMenu = new TranslucentMenu(this);
		jMenuBar = tMenu.createMenuBar(44, manage, seckeys);
		return jMenuBar;
	}
	/**
	 * 添加主面板
	 * 包含两个 Logo 和 4 个方块
	 */
	public void addBodyPanel() {
		bodyPanel.setLayout(null);
		bodyPanel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT - 118);
		bodyPanel.setPreferredSize(new Dimension(getWidth(), getHeight() - 118));
		bodyPanel.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.25f));
		bodyPanel.setForeground(null);
		add(bodyPanel);
		addBodyDetail();
	}
	/**
	 * 添加主面板的详细组件主要包括两个logo，一个在右上角，一个在右下角
	 */
	public void addBodyDetail() {
		// 两个 logo
		int MLogoX = FRAME_WIDTH * 19/25,  MLogoY = 30, 						// mLogo 的位置;
			 SLogoX =FRAME_WIDTH * 13/20, SLogoY = FRAME_HEIGHT * 10/15; 		// sLogo 的位置;
		mlogo = new ImagePanel(mLogoURL, FRAME_WIDTH * 5/24, FRAME_WIDTH * 5/91, MLogoX, MLogoY);	// 设计者logo
		slogo = new ImagePanel(sLogoURL, FRAME_WIDTH * 5/16, FRAME_WIDTH * 5/64, SLogoX, SLogoY);
		slogo.setBackground(null);
		mlogo.setBackground(null);
		// 4 个方块
		Rectangle rtSearch = new Rectangle(160, 160, 120, 120);
		Rectangle rtKey = new Rectangle(290, 290, 120, 120);
		Rectangle rtLibrary = new Rectangle(290, 160, 120, 120);
		Rectangle rtLock = new Rectangle(160, 290, 120, 120);
		ImageIcon icSearch = new ImageIcon("res\\ic_search.png");
		ImageIcon icKey = new ImageIcon("res\\ic_vpn_key.png");
		ImageIcon icLibrary = new ImageIcon("res\\ic_my_library.png");
		ImageIcon icLock = new ImageIcon("res\\ic_vpn_Lock.png");
		boxLT = RyougiModule.ColorfulBox("检索", DEFAULT_FONT, LIGHT_BLUE_COLOR, COLOR_FG, rtSearch, icSearch);
		boxLB = RyougiModule.ColorfulBox("借阅", DEFAULT_FONT, LIGHT_YERROW_COLOR, COLOR_FG, rtKey, icKey);
		boxRT = RyougiModule.ColorfulBox("登录", DEFAULT_FONT, LIGHT_GREEN_COLOR, COLOR_FG, rtLibrary, icLibrary);
		boxRB = RyougiModule.ColorfulBox("授权", DEFAULT_FONT, LIGHT_ORANGE_COLOR, COLOR_FG, rtLock, icLock);
		boxLT.setToolTipText("书籍检索");
		boxLB.setToolTipText("借书授权");
		boxRT.setToolTipText("书籍借阅");
		boxRB.setToolTipText("登录");
		boxLT.addMouseListener(new LabelAdapter(this, "检索"));
		boxLB.addMouseListener(new LabelAdapter(this, "借阅"));
		boxRT.addMouseListener(new LabelAdapter(this, "登录"));
		boxRB.addMouseListener(new LabelAdapter(this, "授权"));
		ToolTipManager.sharedInstance().setDismissDelay(10000);
		bodyPanel.add(slogo);
		bodyPanel.add(mlogo);
		bodyPanel.add(boxLT);
		bodyPanel.add(boxLB);
		bodyPanel.add(boxRT);
		bodyPanel.add(boxRB);
	}
	/**
	 * 添加底部面板
	 * 面板包含一个显示 copyright 的 JLabel
	 */
	public void addFootPanel() {
		footPanel.setLayout(null);
		footPanel.setBounds(0, FRAME_HEIGHT-120, FRAME_WIDTH, 60);
		footPanel.setPreferredSize(new Dimension(getWidth(), 44));
		footPanel.setBackground(new Color(0, 0, 0, 0.25f));
		footLabel.setForeground(COLOR_FG);
		footPanel.add(footLabel);
		add(footPanel);
	}	
	/**
	 * 主窗口尺寸控制
	 */
	public void frameSizeCtrl() {
		Dimension dimension = SizeCtrl.mainFrameSizeCtrl();
		setSize(dimension);
	}
}
