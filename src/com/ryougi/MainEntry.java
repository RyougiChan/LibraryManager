package com.ryougi;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.ryougi.iframe.LoginFrame;

public class MainEntry {
	public static void main(String[] args) {
		EventQueue.invokeLater(()-> {
			JFrame frame = new LoginFrame();
			frame.setVisible(true);
		});
	}
}
