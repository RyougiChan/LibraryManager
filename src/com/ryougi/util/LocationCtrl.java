package com.ryougi.util;

import java.awt.Point;
import java.awt.Toolkit;

public class LocationCtrl {
	private static Toolkit toolkit = Toolkit.getDefaultToolkit();									// 系统工具包
	private static final int SCREEN_WIDTH = toolkit.getScreenSize().width;				// 获取当前主屏幕宽度
	private static final int SCREEN_HEIGHT = toolkit.getScreenSize().height;				// 获取当前主屏幕高度
	private static final double scale = SCREEN_WIDTH/SCREEN_HEIGHT;					// 屏幕宽高比

//	private static int FRAME_WIDTH;										// 主窗口宽度
//	private static int FRAME_HEIGHT;										// 主窗口高度

	private static int MAIN_XL = (SCREEN_WIDTH - SCREEN_HEIGHT*8/5)/2;					// 主窗口水平位置(宽屏，下同)
	private static int MAIN_YL = SCREEN_HEIGHT /20;														// 主窗口垂直位置
	private static int OTHER_XL  = (SCREEN_WIDTH - SCREEN_HEIGHT*4/5)/2;					// 其他窗口水平位置
	private static int OTHER_YL = SCREEN_HEIGHT *11/40;												// 其他窗口垂直位置
	private static int DIALOG_XL = (SCREEN_WIDTH - SCREEN_HEIGHT*8/15)/2;				// 提示窗口水平位置
	private static int DIALOG_YL = SCREEN_HEIGHT * 7/20;												// 提示窗口垂直位置
	
	private static int MAIN_XS = SCREEN_WIDTH /20;														// 主窗口水平位置(窄屏，下同)
	private static int MAIN_YS= (SCREEN_HEIGHT - SCREEN_WIDTH * 81/160) /2;				// 主窗口垂直位置
	private static int OTHER_XS  = SCREEN_WIDTH * 11/40;												// 其他窗口水平位置
	private static int OTHER_YS = (SCREEN_HEIGHT - SCREEN_WIDTH * 81/320)/2;										// 其他窗口垂直位置
	private static int DIALOG_XS = SCREEN_WIDTH * 7/20;												// 提示窗口水平位置
	private static int DIALOG_YS = (SCREEN_HEIGHT - SCREEN_WIDTH * 27/160)/2;			// 提示窗口垂直位置
	
	/**
	 * 主界面窗口尺寸控制
	 * 如果当前屏幕尺寸宽高比大于 16/9，设置窗口位置为屏幕高的 8/5 和 9/10
	 * 如果小于等于 16/9，设置窗口位置为屏幕宽的 9/10 和 81/160
	 * @return 返回主窗口坐标
	 */
	public static Point mainFrameLocationCtrl() {
		if (scale > 16/9) {
			return new Point(MAIN_XL, MAIN_YL);
		} else {
			return new Point(MAIN_XS, MAIN_YS);
		}
	}
	/**
	 * 提示窗口尺寸控制
	 * 如果当前屏幕尺寸宽高比大于 16/9，设置窗口位置为屏幕高的 8/15 和 3/10
	 * 如果小于等于 16/9，设置窗口位置为屏幕宽的 3/10 和 27/160
	 * @return 返回弹出窗口坐标
	 */
	public static Point dialogLocationCtrl() {
		if (scale > 16/9) {
			return new Point(DIALOG_XL, DIALOG_YL);
		} else {
			return new Point(DIALOG_XS, DIALOG_YS);
		}
	}
	/**
	 * 其他界面窗口尺寸控制
	 * 如果当前屏幕尺寸宽高比大于 16/9，设置窗口位置为屏幕高的 4/5 和 9/20
	 * 如果小于等于 16/9，设置窗口位置为屏幕宽的 9/20 和 81/320
	 * @return 返回其他窗口坐标
	 */
	public static Point otherFrameLocationCtrl() {
		if (scale > 16/9) {
			return new Point(OTHER_XL, OTHER_YL);
		} else {
			return new Point(OTHER_XS, OTHER_YS);
		}
	}
}
