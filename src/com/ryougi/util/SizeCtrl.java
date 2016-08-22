package com.ryougi.util;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SizeCtrl {
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();									// 系统工具包
	private static final int SCREEN_WIDTH = toolkit.getScreenSize().width;				// 获取当前主屏幕宽度
	private static final int SCREEN_HEIGHT = toolkit.getScreenSize().height;				// 获取当前主屏幕高度
	private static final double scale = SCREEN_WIDTH/SCREEN_HEIGHT;					// 屏幕宽高比

	private static int FRAME_WIDTH;									// 主窗口宽度
	private static int FRAME_HEIGHT;									// 主窗口高度
	
	/**
	 * 主界面窗口尺寸控制
	 * 如果当前屏幕尺寸宽高比大于 16/9，设置窗口宽高尺寸为屏幕高的 8/5 和 9/10
	 * 如果小于等于 16/9，设置窗口宽高尺寸为屏幕宽的 9/10 和 81/160
	 * @return 返回主窗口的尺寸
	 */
	public static Dimension mainFrameSizeCtrl() {
		if (scale > 16/9) {
			FRAME_WIDTH = SCREEN_HEIGHT * 8/5;
			FRAME_HEIGHT = SCREEN_HEIGHT * 9/10;
		} else {
			FRAME_WIDTH = SCREEN_WIDTH * 9/10;
			FRAME_HEIGHT = SCREEN_WIDTH * 81/160;
		}
		return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
	}
	/**
	 * 提示窗口尺寸控制
	 * 如果当前屏幕尺寸宽高比大于 16/9，设置窗口宽高尺寸为屏幕高的 8/15 和 3/10
	 * 如果小于等于 16/9，设置窗口宽高尺寸为屏幕宽的 3/10 和 27/160
	 * @return 返回提示窗口的尺寸
	 */
	public static Dimension dialogSizeCtrl() {
		if (scale > 16/9) {
			FRAME_WIDTH = SCREEN_HEIGHT * 8/15;
			FRAME_HEIGHT = SCREEN_HEIGHT * 3/10;
		} else {
			FRAME_WIDTH = SCREEN_WIDTH * 3/10;
			FRAME_HEIGHT = SCREEN_WIDTH * 27/160;
		}
		return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
	}
	/**
	 * 其他界面窗口尺寸控制
	 * 如果当前屏幕尺寸宽高比大于 16/9，设置窗口宽高尺寸为屏幕高的 4/5 和 9/20
	 * 如果小于等于 16/9，设置窗口宽高尺寸为屏幕宽的 9/20 和 81/320
	 * @return 返回其他窗口的尺寸
	 */
	public static Dimension otherFrameSizeCtrl() {
		if (scale > 16/9) {
			FRAME_WIDTH = SCREEN_HEIGHT * 4/5;
			FRAME_HEIGHT = SCREEN_HEIGHT * 9/20;
		} else {
			FRAME_WIDTH = SCREEN_WIDTH * 9/20;
			FRAME_HEIGHT = SCREEN_WIDTH * 81/320;
		}
		return new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
	}
}
