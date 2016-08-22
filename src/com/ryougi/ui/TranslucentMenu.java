package com.ryougi.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

import com.ryougi.adapter.LabelAdapter;
import com.ryougi.adapter.MenuActions;

@SuppressWarnings("serial")
public class TranslucentMenu {
	
	private JFrame curFrame;
	public TranslucentMenu(JFrame curFrame) {
		this.curFrame = curFrame;
	}
	
	private static final Color POPUP_BACK = new Color(0, 0, 0, 0.25f);
	private static final Color ALPHA_ZERO = new Color(0x0, true);
	
	/**
	 * 重绘 JMenuBar, 风格为  0.25f 透明填充
	 * @param height JMenuBar 高度
	 * @param keys JMenuBar 的选择项关键字
	 * @param seckeys 次级菜单选项列表，一个数组表示一组 JMenu 次级键
	 * @return 返回一个重绘的 JMenuBar
	 */
	public JMenuBar createMenuBar(int height, String[] keys, List<String[]> seckeys) {
		JMenuBar mb = new JMenuBar() {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = height;
				return d;
			}
			@Override
			public boolean isOpaque() {
				return false;
			}
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setPaint(POPUP_BACK);
				g2.fillRect(0, 0, getWidth(), getHeight());
				g2.dispose();
			}
		};
		for (String key : keys) {
			mb.add(createMenu(key, seckeys));
		}
		return mb;
	}
	
	public JMenu createMenu(String key, List<String[]> seckeys) {
//		JMenu m = new TransparentMenu("<html><h3 style='text-align:center;width:60px;'>"+key+"</h3></html>");
		JMenu m = new TransparentMenu("  "+key);
		m.setPreferredSize(new Dimension(90, 44));
		int index = 0;
		switch (key) {
		case "书籍管理":
			index = 0;
			break;
		case "人员管理":
			index = 1;
			break;
		case "其他管理":
			index = 2;
			break;
		case "系统管理":
			index = 3;
			break;
		case "回到首页":
			index = 4;
			m.addMouseListener(new LabelAdapter(curFrame, key));
			break;
		}
		if (index!=4) {
			for (String k : seckeys.get(index)) {
//				JMenuItem i = new JMenuItem("<html><h3 style='text-align:center;width:60px;'>"+k+"</h3></html>");
				JMenuItem i = new JMenuItem("  "+k);
				i.setPreferredSize(new Dimension(90, 44));
				i.addActionListener(new MenuActions(curFrame, k.replace(" ", "")));
				m.add(i);
			}
		}
		return m;
	}
	
	static class TransparentMenu extends JMenu {
		private JPopupMenu popupMenu;
		public TransparentMenu(String key) {
			super(key);
		}
		public void popupMenuCreated() {
			if (Objects.isNull(popupMenu)) {
				this.popupMenu = new TranslucentPopupMenu();
				popupMenu.setInvoker(this);
				popupListener = createWinListener(popupMenu);
			}
		}
		@Override
		public boolean isOpaque() {
			return false;
		}
		@Override
		public JMenuItem add(JMenuItem menuItem) {
			popupMenuCreated();
			menuItem.setOpaque(false);
			return popupMenu.add(menuItem);
		}
		@Override
		public Component add(Component c) {
			popupMenuCreated();
			if (c instanceof JComponent) {
				((JComponent) c).setOpaque(false);
			}
			popupMenu.add(c);
			return c;
		}
		@Override
		public void addSeparator() {
			popupMenuCreated();
			popupMenu.addSeparator();
		}
		@Override
		public void insert(String s, int pos) {
			if (pos < 0) {
				throw new IllegalArgumentException("Index less than 0");
			}
			popupMenuCreated();
			popupMenu.insert(new JMenuItem(s), pos);
		}
		@Override
		public JMenuItem insert(JMenuItem mi, int pos) {
			if (pos < 0) {
				throw new IllegalArgumentException();
			}
			popupMenuCreated();
			popupMenu.insert(mi, pos);
			return mi;
		}
		@Override
		public JPopupMenu getPopupMenu() {
			popupMenuCreated();
			return popupMenu;
		}
		@Override
		public boolean isPopupMenuVisible() {
			popupMenuCreated();
			return popupMenu.isVisible();
		}
		@Override
		public void insertSeparator(int index) {
			if (index < 0) {
		        throw new IllegalArgumentException("index less than zero.");
		      }
		      popupMenuCreated();
		      popupMenu.insert(new JPopupMenu.Separator(), index);
		}
	}
	/**
	 * TranslucentPopupMenu 模型
	 * 来源于 http://ateraimemo.com/Swing/TranslucentPopupMenu.html
	 * @author RyougiShiki
	 */
	static class TranslucentPopupMenu extends JPopupMenu {
		@Override
		public boolean isOpaque() {
			return false;
		}
		@Override
		public void show(Component invoker, int x, int y) {
			EventQueue.invokeLater(() -> {
				Container c = getTopLevelAncestor();
				if (c instanceof JWindow) {
					((JWindow) c).setBackground(ALPHA_ZERO);
				}
			});
			super.show(invoker, x, y);
		}
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setPaint(POPUP_BACK);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.dispose();
		}
	}

}

