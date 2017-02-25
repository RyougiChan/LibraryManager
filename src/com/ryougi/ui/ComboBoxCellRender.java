package com.ryougi.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class ComboBoxCellRender implements ListCellRenderer<Object> {
	
	private JLabel render;												// 列表项
	private Font font;													// 列表项JLabel的字体
	private Icon icon;													// 列表项JLabel的图标
	private Color usBgColor, sBgColor, usFgColor, sFgColor;				// 列表项JLabel的背景颜色和前景颜色
	private String text, iconURL;										// 列表项JLabel的显示文本和图标
	private int width, height;											// 列表项的大小
	private Color UNSELECTED_BG_COLOR = new Color(255, 255, 255, 200);	// 列表项JLabel未选择时的默认背景颜色
	private Color SELECTED_BG_COLOR = new Color(255, 255, 255, 255);	// 列表项JLabel选择时的默认背景颜色255
	private Color UNSELECTED_FG_COLOR = new Color(0, 0, 0, 200);		// 列表项JLabel未选择时的默认前景颜色
	private Color SELECTED_FG_COLOR = new Color(120, 150, 232);			// 列表项JLabel未选择时的默认前景颜色
	private Font DEFAULT_FONT = new Font("微软雅黑", Font.BOLD, 16);		// 列表项JLabel的默认字体
	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	@SuppressWarnings("rawtypes")
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		render = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		render.setOpaque(true);
		render.setBorder(null);
		render.setHorizontalAlignment(SwingConstants.CENTER);
		
		if (value instanceof Object[]) {
			Object values[] = (Object[]) value;
			if (values[1] == null) {
				values[1] = DEFAULT_FONT;
			}
			if (values[2] == null) {
				values[2] = UNSELECTED_BG_COLOR;
			}
			if (values[3] == null) {
				values[3] = SELECTED_BG_COLOR;
			}
			if (values[4] == null) {
				values[4] = UNSELECTED_FG_COLOR;
			}
			if (values[5] == null) {
				values[5] = SELECTED_FG_COLOR;
			}
			text = (String) values[0];
			font = (Font) values[1];
			usBgColor = (Color) values[2];
			sBgColor = (Color) values[3];
			usFgColor = (Color) values[4];
			sFgColor = (Color) values[5];
			iconURL = (String) values[6];
			width = (int) values[7];
			height = (int) values[8];
			icon = new ImageIcon(iconURL);
			
		} else {
			text = "";
			font = DEFAULT_FONT;
			sBgColor = SELECTED_BG_COLOR;
			usBgColor = UNSELECTED_BG_COLOR;
			sFgColor = UNSELECTED_FG_COLOR;
			usFgColor = SELECTED_FG_COLOR;
			iconURL = "";
			icon = null;
		}
		
		render.setPreferredSize(new Dimension(width, height));				// 设置每个菜单项的默认尺寸
		
		if (!isSelected) {
			render.setBackground(usBgColor);
			render.setForeground(usFgColor);
			render.setFont(font);
			render.setText(text);
			render.setIcon(icon);
		} else {
			render.setBackground(sBgColor);
			render.setForeground(sFgColor);
			render.setFont(font);
			render.setText(text);
			render.setIcon(icon);
		}
		
		return render;
	}
}
