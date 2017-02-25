package com.ryougi.iframe;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ryougi.module.FormFrame;
import com.ryougi.module.RyougiModule;
import com.ryougi.util.OtherResolver;

@SuppressWarnings("serial")
public class SysMessageFrame extends FormFrame {
	
	private final int FRAME_WIDTH = this.getWidth();						// 当前窗口宽度
	private final int FRAME_HEIGHT = this.getHeight();						// 当前窗口高度
	private final int DEFAULT_MARGIN_TOP = (FRAME_HEIGHT - 200)/2;				// 默认边界高度

	private String imgFollowURL = "res\\ic_play_complete_pay_movie.png";
	private String dsLabel = "设计者    RyougiChan\n";
	private String dvLabel = "开发者    RyougiChan\n";
	private String ctLabel = "   联系     ryougi.chan.fire@gmail.com\n";
	private String vsLabel = "版本号    1.0.0";

	private static Color BG_COLOR = new Color(120, 150, 232);				// 背景颜色
	
	// 左部背景面板，底部表单面板，左部面板背景
	private JPanel lPanel, rPanel, lImgPanel;
	private JLabel designerLabel;
	private JLabel developerLabel;
	private JLabel contactLabel;
	private JLabel versionLabel;

	public void initModule() {
		lPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH/2, FRAME_HEIGHT, 0,0);
		lImgPanel = RyougiModule.ImagePanel(this, imgFollowURL, FRAME_WIDTH * 2/5, FRAME_WIDTH * 293/820, FRAME_WIDTH/20, FRAME_WIDTH/11);
		rPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH/2, FRAME_HEIGHT, FRAME_WIDTH/2, 0);
		designerLabel = RyougiModule.NoticeLabel(rPanel, dsLabel, 16, null, BG_COLOR, FRAME_WIDTH, 50, 0, DEFAULT_MARGIN_TOP);
		developerLabel = RyougiModule.NoticeLabel(rPanel, dvLabel, 16, null, BG_COLOR, FRAME_WIDTH, 50, 0, DEFAULT_MARGIN_TOP + 40);
		contactLabel = RyougiModule.NoticeLabel(rPanel, ctLabel, 16, null, BG_COLOR, FRAME_WIDTH, 50, 5, DEFAULT_MARGIN_TOP + 80);
		versionLabel = RyougiModule.NoticeLabel(rPanel, vsLabel, 16, null, BG_COLOR, FRAME_WIDTH, 50, 0, DEFAULT_MARGIN_TOP + 120);
		System.out.println(designerLabel.getHeight());
	}
	
	public SysMessageFrame() {
		new OtherResolver().setIcon(this);
		setTitle("  系统信息");
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
		rPanel.add(designerLabel);
		rPanel.add(developerLabel);
		rPanel.add(contactLabel);
		rPanel.add(versionLabel);
		add(lPanel);
		add(rPanel);
	}
}
