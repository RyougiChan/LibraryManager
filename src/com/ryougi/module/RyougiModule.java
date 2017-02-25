package com.ryougi.module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.ryougi.ui.ComboBoxCellRender;

/**
 * 封装图书馆管理系统的各种组件
 * @author RyougiShiki
 */
public class RyougiModule {
	
	private static final Color LIGHT_BLUE_COLOR = new Color(120, 150, 232);				// 系统主题颜色
	private static final Font DEFAULT_FONT = new Font("微软雅黑", Font.BOLD, 15);			// 系统默认字体
	private static JPanel tPanel;													// 带标签的输入框面板
	private static JLabel tLabel;
	private static JTextField tField;
	
	private static JPanel pPanel;													// 带标签的密码输入框面板
	private static JLabel pLabel;
	private static JPasswordField pField;
	
	private static JPanel imgPanel;													// 带背景的面板
	private static JButton colorBtn;												// 带颜色按钮
	private static JLabel nLabel;													// 带颜色文本
	
	private static JTextArea tArea;													// 文本区域
	private static JComboBox<Object> cBox;											// 下拉选项框
	private static ListCellRenderer<Object> comboBoxRender;							// 搜索栏下拉关键词菜单渲染器
	private static JScrollPane searchPane;											// 查询结果面板
	
	/**
	 *  带颜色的方块
	 * @param text	方块显示的文本
	 * @param bgColor	方块的背景颜色
	 * @param fgColor	方块的前景色
	 * @param font	方块文本的字体
	 * @param rect	方块的边界尺寸和位置属性
	 * @param icon	方块显示的图标
	 * @return	返回带文本、字体、前/背景颜色、尺寸位置的 JLabel
	 */
	public static JLabel ColorfulBox(String text, Font font, Color bgColor, Color fgColor, Rectangle rect, ImageIcon icon) {
		JLabel label = new JLabel(text);
		label.setOpaque(true);
		label.setBackground(bgColor);
		label.setForeground(fgColor);
		label.setFont(font);
		label.setBounds(rect);
		Image img = icon.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		bi.getGraphics().drawImage(img, 25 ,35, img.getWidth(null)/2, img.getHeight(null)/2, null);
		Icon i = new ImageIcon(bi);
		label.setIcon(i);
		label.setIconTextGap(10);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				label.setBackground(bgColor.brighter());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				label.setBackground(bgColor);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				label.setBackground(bgColor.brighter());
			}
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				label.setBackground(bgColor);
			}
		});
		return label;
	}
	
	/**
	 * 自定义数据库搜索查询结果表面板
	 * @param table 查询结果表列名数组集
	 * @param rt 面板的边界
	 * @return 自定义的容器
	 */
	public static JScrollPane SearchResultPane(JTable table, Rectangle rt) {
		searchPane = new JScrollPane(table);
		
		searchPane.setBounds(rt);
		searchPane.setBorder(null);
		searchPane.setBackground(Color.white);
		
		return searchPane;
	}
	
	/**
	 * 自定义的JComboBox下拉框
	 * @param values <h3>初始化渲染器的参数列表，Object[][] values</h3>
	 * 		 <div>{String text, Font font, Color unselectedBgColor, Color selectedBgColor, unselectedFgColor, selectedFgColor</div>
	 * 		  <div>String iconURL, String itemWidth, String itemHeight}</div>
	 * 	<div>形如{{"label_1", new Font("微软雅黑", Font.BOLD, 16), Color.PINK, Color.PINK, Color.PINK, Color.PINK, "res\\bg.png", FRAME_WIDTH * 9/152-8, FRAME_WIDTH /25},</div>
	 *		 <div>{"label_2", null, null, null, null, null, "", FRAME_WIDTH * 9/152-8, FRAME_WIDTH /25},</div>
	 *		<div>};</div>
	 * @param x 下拉列表框的水平位置
	 * @param y 下拉列表框的垂直位置
	 * @param width 下拉列表框的宽度
	 * @param height 下拉列表框的高度
	 * @param bgColor 背景颜色
	 * @param fgColor 前景色
	 * @return 返回经设定正确参数的下拉列表框
	 */
	public static JComboBox<Object> MComboBox(Object[] values, int x, int y, int width, int height, Color bgColor, Color fgColor) {
		cBox = new JComboBox<>(values);
		comboBoxRender = new ComboBoxCellRender();
		cBox.setFont(DEFAULT_FONT);
		cBox.setRenderer(comboBoxRender);
		cBox.setBounds(x, y, width, height);
		cBox.setBorder(BorderFactory.createLineBorder(LIGHT_BLUE_COLOR, 1));
		cBox.setEditable(false);
		if (bgColor == null) {
			cBox.setBackground(Color.WHITE);
		} else {
			cBox.setBackground(bgColor);
		}
		if (fgColor == null) {
			cBox.setForeground(Color.BLACK);
		} else {
			cBox.setForeground(fgColor);
		}
		return cBox;
	}
	
	/**
	 * 自定义JTextField，需提供7个参数来设置边框颜色，前景颜色，背景颜色，默认文本，字体，字体风格，字体大小
	 * @param boc 边框颜色
	 * @param bgc 背景颜色
	 * @param fgc 前景颜色
	 * @param s 默认显示文本
	 * @param fontName 字体
	 * @param fontSize 字体风格 ITALC, BOLD, PLAIN 等
	 * @param fontStyle 字体大小
	 * @return 返回自设定样式的JTextField
	 */
	public static JTextField MTextField (Color boc, Color bgc, Color fgc, String s, String fontName, int fontSize, int fontStyle) {
		tField = new JTextField(s);
		tField.setFont(new Font(fontName, fontStyle , fontSize));
		tField.setBackground(bgc);
		tField.setForeground(fgc);
		tField.setBorder(BorderFactory.createEtchedBorder(boc, boc));
		return tField;
	}
	
	/**
	 * 自定义JTextField，需提供10个参数来设置宽高，位置，边框颜色，前景颜色，背景颜色，默认文本，字体，字体风格，字体大小
	 * @param width 宽
	 * @param height 高
	 * @param x 水平位置
	 * @param y 垂直位置
	 * @param boc 边框颜色
	 * @param bgc 背景颜色
	 * @param fgc 前景颜色
	 * @param s 默认显示文本
	 * @param font 设置字体, new Font(name, style, size)
	 * @return 返回自设定样式的JTextField
	 */
	public static JTextField MTextField (int width, int height, int x, int y, Color boc, Color bgc, Color fgc, String s, Font font) {
		tField = new JTextField(" " + s);
		tField.setFont(font);
		tField.setBackground(bgc);
		tField.setForeground(fgc);
		tField.setBorder(BorderFactory.createEtchedBorder(boc, boc));
		tField.setBounds(x, y, width, height);
		return tField;
	}
	
	/**
	 * 带标签的文本输入框，标签颜色和输入框颜色 Color(120, 150, 232)
	 * @param c 父组件
	 * @param label 标签显示的文本
	 * @param text 输入框显示的文本
	 * @param fontSize 字体大小
	 * @param x 当父布局 setLayout(null) 时，重新定位组件的水平位置
	 * @param y 当父布局 setLayout(null) 时，重新定位组件的垂直位置
	 * @return 返回自设定样式的带输入框JPanel面板
	 */
	public static JPanel TextFieldWithLabel(JComponent c, String label, String text, int fontSize, int x, int y) {
		
		tPanel = new JPanel();
		tLabel = new JLabel(label);
		tField = new JTextField(" " + text);
		tLabel.setForeground(LIGHT_BLUE_COLOR);
		tLabel.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
		
		tField.setPreferredSize(new Dimension(180, 30));
		tField.setFont(DEFAULT_FONT);
		tField.setBorder(BorderFactory.createEtchedBorder(LIGHT_BLUE_COLOR, LIGHT_BLUE_COLOR));
		tField.setForeground(LIGHT_BLUE_COLOR);
		tField.setBackground(null);

		tPanel.setBounds(x, y, 280, 40);
		
		tPanel.add(tLabel);
		tPanel.add(tField);
		
		return tPanel;
	}
	/**
	 * 带标签的文本输入框，标签颜色和输入框颜色 Color(120, 150, 232)
	 * @param c 父组件
	 * @param label 标签显示的文本
	 * @param text 输入框显示的文本
	 * @param fontSize 字体大小
	 * @return 返回自设定样式的带输入框JPanel面板
	 */
	public static JPanel TextFieldWithLabel(JComponent c, String label, String text, int fontSize) {
		
		tPanel = new JPanel();
		tLabel = new JLabel(label);
		tField = new JTextField(" " + text);
		tLabel.setForeground(LIGHT_BLUE_COLOR);
		tLabel.setFont(new Font("微软雅黑", Font.BOLD, fontSize));

		tField.setPreferredSize(new Dimension(180, 30));
		tField.setFont(DEFAULT_FONT);
		tField.setBorder(BorderFactory.createEtchedBorder(LIGHT_BLUE_COLOR, LIGHT_BLUE_COLOR));
		tField.setForeground(LIGHT_BLUE_COLOR);
		tField.setBackground(null);
		
		tPanel.add(tLabel);
		tPanel.add(tField);
		
		return tPanel;
	}
	/**
	 * 带标签的密码输入框，标签颜色和输入框颜色  Color(120, 150, 232)
	 * @param c 父组件
	 * @param label 标签显示的文本
	 * @param text 输入框显示的文本
	 * @param fontSize 字体大小
	 * @param x 当父布局 setLayout(null) 时，重新定位组件的水平位置
	 * @param y 当父布局 setLayout(null) 时，重新定位组件的垂直位置
	 * @return 返回自设定样式的带密码框JPanel面板
	 */
	public static JPanel PasswordFieldWithLabel(JComponent c, String label, String text, int fontSize, int x, int y) {
		
		pPanel = new JPanel();
		pLabel = new JLabel(label);
		pField = new JPasswordField(" " + text);
		pLabel.setForeground(LIGHT_BLUE_COLOR);
		pLabel.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
		
		pField.setPreferredSize(new Dimension(180, 30));
		pField.setFont(DEFAULT_FONT);
		pField.setBorder(BorderFactory.createEtchedBorder(LIGHT_BLUE_COLOR, LIGHT_BLUE_COLOR));
		pField.setForeground(LIGHT_BLUE_COLOR);
		pField.setBackground(null);

		pPanel.setBounds(x, y, 240, 40);
		
		pPanel.add(pLabel);
		pPanel.add(pField);
		return pPanel;
	}
	/**
	 * 带标签的密码输入框，标签颜色和输入框颜色 Color(120, 150, 232) 浅蓝色
	 * @param c 父组件
	 * @param label 标签显示的文本
	 * @param fontSize 字体大小
	 * @param text 输入框显示的文本
	 * @return 返回自设定样式的带密码框JPanel面板
	 */
	public static JPanel PasswordFieldWithLabel(JComponent c, String label, String text, int fontSize) {
		pPanel = new JPanel();
		pLabel = new JLabel(label);
		pField = new JPasswordField(" " + text);
		pLabel.setForeground(LIGHT_BLUE_COLOR);
		pLabel.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
		
		pField.setPreferredSize(new Dimension(180, 30));
		pField.setFont(DEFAULT_FONT);
		pField.setBorder(BorderFactory.createEtchedBorder(LIGHT_BLUE_COLOR, LIGHT_BLUE_COLOR));
		pField.setForeground(LIGHT_BLUE_COLOR);
		pField.setBackground(null);

		pPanel.add(pLabel);
		pPanel.add(pField);
		return pPanel;
	}
	/**
	 * 图片背景面板，加载背景图片等情况使用
	 * @param c 父组件
	 * @param imgURL 图片的存放路径
	 * @param imgWidth 设置图片宽度
	 * @param imgHeight 设置图片高度
	 * @param x 设置图片水平位置
	 * @param y 设置图片垂直位置
	 * @return 返回自设定样式的带背景JPanel面板
	 */
	public static JPanel ImagePanel(JComponent c, String imgURL, int imgWidth, int imgHeight, int x, int y) {
		imgPanel = new ImagePanel(imgURL, imgWidth, imgHeight, x, y);
		return imgPanel;
	}
	/**
	 * 图片背景面板，加载背景图片等情况使用
	 * @param f 父窗口
	 * @param imgURL 图片的存放路径
	 * @param imgWidth 设置图片宽度
	 * @param imgHeight 设置图片高度
	 * @param x 设置图片水平位置
	 * @param y 设置图片垂直位置
	 * @return 返回自设定样式的带背景JPanel面板
	 */
	public static JPanel ImagePanel(JFrame f, String imgURL, int imgWidth, int imgHeight, int x, int y) {
		imgPanel = new ImagePanel(imgURL, imgWidth, imgHeight, x, y);
		return imgPanel;
	}
	/**
	 * 背景颜色为 color 的按钮
	 * @param c 父组件
	 * @param label 按钮文本
	 * @param color 按钮背景颜色
	 * @param btnWidth 按钮宽度，多数情况下可设为 90
	 * @param btnHeight 按钮高度，多数情况下可设为 30
	 * @param fontSize 字体大小
	 * @param x 按钮的水平位置
	 * @param y 按钮的垂直位置
	 * @return 返回自设定样式的带颜色按钮
	 */
	public static JButton ColorButton(JComponent c, String label, int fontSize, Color color, int btnWidth, int btnHeight, int x, int y) {
		colorBtn = new JButton(label);
		colorBtn.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
		colorBtn.setBackground(color);
		colorBtn.setForeground(Color.WHITE);
		colorBtn.setBorder(null);
		colorBtn.setPreferredSize(new Dimension(90, 30));
		colorBtn.setBounds(x, y, btnWidth, btnHeight);
		colorBtn.setFocusable(false);
		return colorBtn;
	}
	/**
	 * 背景颜色为 color 的按钮
	 * @param c 父组件
	 * @param label 按钮文本
	 * @param color 按钮背景颜色
	 * @param fontSize 字体大小
	 * @param btnWidth 按钮宽度，多数情况下可设为 90
	 * @param btnHeight 按钮高度，多数情况下可设为 30
	 * @return 返回自设定样式的带颜色按钮
	 */
	public static JButton ColorButton(JComponent c, String label, int fontSize, Color color, int btnWidth, int btnHeight) {
		colorBtn = new JButton(label);
		colorBtn.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
		colorBtn.setBackground(color);
		colorBtn.setForeground(Color.WHITE);
		colorBtn.setBorder(null);
		colorBtn.setPreferredSize(new Dimension(90, 30));
		colorBtn.setFocusable(false);
		return colorBtn;
	}
	/**
	 * 带样式的提示文本信息，具有前景色（文本颜色）和背景色属性，位置，宽高等
	 * @param c 父组件
	 * @param label 文本信息
	 * @param fontSize 文本字体大小
	 * @param bgColor 文本背景色
	 * @param fgColor 文本前景色（文本颜色）
	 * @param width 文本组件宽度
	 * @param height 文本组件高度
	 * @param x 文本的水平位置
	 * @param y 文本的垂直位置
	 * @return 返回自设定样式提醒文本
	 */
	public static JLabel NoticeLabel(JComponent c, String label, int fontSize, Color bgColor, Color fgColor, int width, int height, int x, int y) {
		nLabel = new JLabel(label);
		nLabel.setOpaque(true);
		nLabel.setFont(new Font("", Font.BOLD, fontSize));
		nLabel.setBackground(bgColor);
		nLabel.setForeground(fgColor);
		nLabel.setBounds(x, y, width, height);
		return nLabel;
	}
	/**
	 * 带样式的提示文本信息，具有前景色（文本颜色）和背景色属性等
	 * @param c 父组件
	 * @param label 文本信息
	 * @param fontSize 文本字体大小
	 * @param bgColor 文本背景色
	 * @param fgColor 文本前景色（文本颜色）
	 * @return 返回自设定样式提醒文本
	 */
	public static JLabel NoticeLabel(JComponent c, String label, int fontSize, Color bgColor, Color fgColor) {
		nLabel = new JLabel(label);
		nLabel.setFont(new Font("", Font.BOLD, fontSize));
		return nLabel;
	}
	/**
	 * 自定义JTextField，需要10个参数完成初始化
	 * @param text	field框显示的文本
	 * @param font	文本的字体样式	
	 * @param bgColor	背景颜色
	 * @param fgColor	前景颜色
	 * @param width		宽度
	 * @param height	高度
	 * @param x			水平位置
	 * @param y			垂直位置
	 * @return	自定义样式的文本框
	 */
	public static JTextArea MTextArea(String text, Font font ,Color bgColor, Color fgColor, int width, int height, int x, int y) {
		tArea = new JTextArea(text);
		tArea.setFont(font);
		tArea.setEditable(false);
		tArea.setBackground(bgColor);
		tArea.setForeground(fgColor);
		tArea.setLineWrap(true);
		tArea.setBounds(x, y, width, height);
		tArea.setBorder(null);
		return tArea;
	}
}
