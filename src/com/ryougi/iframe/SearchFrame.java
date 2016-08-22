package com.ryougi.iframe;

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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

import com.ryougi.adapter.BtnMouseAdapter;
import com.ryougi.module.ImagePanel;
import com.ryougi.module.RyougiModule;
import com.ryougi.ui.SearchTableModel;
import com.ryougi.ui.TranslucentMenu;
import com.ryougi.util.LocationCtrl;
import com.ryougi.util.OtherResolver;
import com.ryougi.util.SizeCtrl;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame {
	
	private static final Color ALPHA_ZERO = new Color(0x0, true);
	private static final Color POPUP_BACK = new Color(0, 0, 0, 0.25f);
	private static final Color COLOR_FG = Color.WHITE;											// 前景颜色

	private Toolkit toolkit = Toolkit.getDefaultToolkit();							// 系统工具包
	private int FRAME_WIDTH = SizeCtrl.mainFrameSizeCtrl().width;									// 主窗口宽度
	private int FRAME_HEIGHT = SizeCtrl.mainFrameSizeCtrl().height;									// 主窗口高度
	private static final Color LIGHT_BLUE_COLOR = new Color(120, 150, 232);										// 系统主题颜色
	private static final Font DEFAULT_FONT = new Font("微软雅黑", Font.BOLD, 15);									// 系统默认字体
	private Object[][] values = {{"模糊检索 ", null, null, null, null, null, "", FRAME_WIDTH * 9/152-8, FRAME_WIDTH /25},
			 {"ISBN检索 ", null, null, null, null, null, "", FRAME_WIDTH * 9/152-8, FRAME_WIDTH /25},	// 给下拉框提供的参数
			 {"书名检索 ", null, null, null, null, null, "", FRAME_WIDTH * 9/152-8, FRAME_WIDTH /25},
			 {"著者检索 ", null, null, null, null, null, "", FRAME_WIDTH * 9/152-8, FRAME_WIDTH /25}
			 };
	
	private String title = "图书检索";															// 系统名称
	private String copyright = "<html><div style='text-align:center;text-style:bold;width:960px;height:30px;line-height:30px;'>技术支持: RyougiChan &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
			+ "Email : ryougi.chan.fire@gmail.com</div></html>";									// 底部版权信息
	private String libImgURL = "res\\Library.jpg";
	private String sLogoURL = "res\\LMS.png";														// 系统logo所在路径
	private String[] manage = new String[] {"书籍管理", "人员管理", "其他管理", "系统管理", "回到首页"};			// 菜单项文本
	private String[] bookMng = new String[] {"   借书 ", "   归还 ", "   购书 ", "   赠书 ", "   修改 "};		// 次级菜单项文本
	private String[] userMng = new String[] {" 操作员", "    读者"};
	private String[] otherMng = new String[] {"借书权限", "借阅须知"};
	private String[] sysMng = new String[] {"系统信息", "系统升级"};
//	private ResultSet searchResult;																	// 搜索数据库查询返回的结果集
	
	private final int DEFAULT_X = FRAME_WIDTH/16, DEFAULT_Y = FRAME_HEIGHT /10 - 30; 				// 默认外边距;
	private final Rectangle searchPanelRect = new Rectangle(FRAME_WIDTH/16,120,FRAME_WIDTH*7/8, FRAME_HEIGHT*3/5+4);
	
	private JMenuBar jMenuBar;														// 菜单栏
	private JPanel bodyPanel;														// 主内容面板
	private JPanel slogo;													// 设计者logo和系统logo
	private JScrollPane searchResultPane;											// 搜索结果面板，包含一个结果表
	private JPanel footPanel;														// 底部面板
	private JLabel footLabel;														// 底部版权标签
	private JMenuBar menuBar;
	private JTextField searchField;													// 搜索框
	private JComboBox<Object> keywordsList;											// 搜索栏下拉关键词菜单
	
	private JButton sButton;
	
	private JTable sTable;															// 查询结果表
	private TableModel sTableModel;													// 查询结果表模式
	private int rowHeight = FRAME_HEIGHT/20+1;										// 查询结果表行高
	
	
	
	/**
	 * 初始化界面组件
	 */
	public void initModule() {
//		rootPanel = RyougiModule.ImagePanel(this, libImgURL, FRAME_WIDTH, FRAME_HEIGHT-30, 0, 0);
		footLabel = RyougiModule.NoticeLabel(footPanel, copyright, 16, POPUP_BACK, COLOR_FG, FRAME_WIDTH, 60, 0,0);
		searchField = RyougiModule.MTextField(FRAME_WIDTH*4/10, FRAME_WIDTH * 9/152-8, FRAME_WIDTH*3/10 + 5, FRAME_HEIGHT/10 - 30, LIGHT_BLUE_COLOR, Color.WHITE, new Color(20, 20, 20), "  ", DEFAULT_FONT);
		slogo = new ImagePanel(sLogoURL, FRAME_WIDTH * 9/38, FRAME_WIDTH * 9/152, DEFAULT_X, DEFAULT_Y);
		keywordsList = RyougiModule.MComboBox(values, FRAME_WIDTH*1063/1520 + 16, DEFAULT_Y, FRAME_WIDTH*181/1520 -12, FRAME_WIDTH * 9/152-9, Color.WHITE, null);
		sButton = RyougiModule.ColorButton(null, "检 索", 16, LIGHT_BLUE_COLOR, FRAME_WIDTH*181/1520 -12, FRAME_WIDTH * 9/152-13, FRAME_WIDTH*311/380 + 12, FRAME_HEIGHT/10 - 28);
		
		sTableModel = new SearchTableModel();
		sTable = new JTable(sTableModel);
		sTable.setAutoCreateRowSorter(true);
		sTable.setRowHeight(rowHeight);
		sTable.setBackground(Color.WHITE);
		searchResultPane = RyougiModule.SearchResultPane(sTable, searchPanelRect);
		
		bodyPanel = new JPanel();
		footPanel = new JPanel();
		menuBar = addMenuBar();
	}
	
	public SearchFrame() {
		super();
		frameSizeCtrl();
		new OtherResolver().setIcon(this);
		setTitle(title);
		setLocation(LocationCtrl.mainFrameLocationCtrl());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		manageUI();
		setLayout(null);

	    ((JComponent) getContentPane()).setOpaque(false);
		getRootPane().setBackground(Color.black);
		initModule();								// 组件初始化操作
		addBodyPanel();								// 添加主内容面板
		addFootPanel();								// 添加底部面板
		setJMenuBar(menuBar);						// 添加顶部菜单栏
		menuBar.setSize(getPreferredSize());		// 设置菜单栏偏好尺寸
		
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
	 * 包含系统logo，搜索框，下拉菜单，搜索按钮，查询结果面板
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
	 * 添加主面板的详细组件,包括系统logo，搜索框，下拉菜单，搜索按钮，查询结果面板
	 * 添加事件监听器
	 */
	public void addBodyDetail() {
		bodyPanel.add(searchField);
		bodyPanel.add(slogo);
		bodyPanel.add(keywordsList);
		bodyPanel.add(sButton);
		bodyPanel.add(searchResultPane);
		
		ArrayList<Object> list = new ArrayList<>();
		list.add(searchField);
		list.add(searchResultPane);
		sButton.addMouseListener(new BtnMouseAdapter(this, sButton, list));			// 检索按钮点击事件适配器
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

	public JScrollPane getSearchResultPane() {
		return searchResultPane;
	}

	public void setSearchResultPane(JScrollPane searchResultPane) {
		this.searchResultPane = searchResultPane;
	}
	/*
	 * getter和setter定义区
	 */
	public JComboBox<Object> getKeywordsList() {
		return keywordsList;
	}

	public void setKeywordsList(JComboBox<Object> keywordsList) {
		this.keywordsList = keywordsList;
	}

	public TableModel getsTableModel() {
		return sTableModel;
	}

	public void setsTableModel(TableModel sTableModel) {
		this.sTableModel = sTableModel;
	}
	
}
