package com.ryougi.iframe;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ryougi.module.FormFrame;
import com.ryougi.module.RyougiModule;
import com.ryougi.util.OtherResolver;

@SuppressWarnings("serial")
public class LibraryPolicyFrame extends FormFrame {
	
	private static final Font DEFAULT_FONT = new Font("微软雅黑",Font.BOLD, 15);
	private final int FRAME_WIDTH = this.getWidth();						// 当前窗口宽度
	private final int FRAME_HEIGHT = this.getHeight();						// 当前窗口高度
	
	private BufferedReader reader;
	
	private String imgFollowURL = "res\\ic_play_complete_pay_movie.png";
	private String libPolicyURL = "res\\LibraryPolicy.txt";
	
	private static Color BG_COLOR = new Color(120, 150, 232);				// 背景颜色
	
	// 左部背景面板，底部表单面板，左部面板背景
	private JPanel lPanel, rPanel, lImgPanel;
	private JScrollPane sPane;												
	private JTextArea libPolicyArea;										// 显示用户须知
	
	/**
	 * 初始化组件
	 */
	public void initModule() {
		lPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH/2, FRAME_HEIGHT, 0,0);
		lImgPanel = RyougiModule.ImagePanel(this, imgFollowURL, FRAME_WIDTH * 2/5, FRAME_WIDTH * 293/820, FRAME_WIDTH/20, FRAME_WIDTH/11);
		rPanel = RyougiModule.ImagePanel(this, "", FRAME_WIDTH/2, FRAME_HEIGHT, FRAME_WIDTH/2, 0);
		libPolicyArea = RyougiModule.MTextArea(getLibraryPolicy(), DEFAULT_FONT, null, BG_COLOR, FRAME_WIDTH*9/20, FRAME_HEIGHT - 44, FRAME_WIDTH/40, FRAME_WIDTH/40);
		sPane = new JScrollPane(libPolicyArea);
	}
	
	public LibraryPolicyFrame() {
		new OtherResolver().setIcon(this);
		setTitle("  书籍借阅须知");
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
		sPane.setBackground(null);
		sPane.setForeground(null);
		lPanel.setLayout(null);
		rPanel.setLayout(null);
		sPane.setBounds(0, 8, FRAME_WIDTH/2, FRAME_HEIGHT - 44);
		sPane.setBorder(null);
		sPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		lPanel.add(lImgPanel);
		rPanel.add(sPane);
		add(lPanel);
		add(rPanel);
	}
	/**
	 * 获取指定输入流中的文本
	 * @return 返回输入流中的完整文本
	 */
	public String getLibraryPolicy() {
		String line = "";
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(libPolicyURL), "UTF-8"));
			while ((line = reader.readLine())!=null) {
				builder.append(line+"\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}
}
