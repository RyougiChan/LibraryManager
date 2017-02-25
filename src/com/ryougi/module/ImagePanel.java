package com.ryougi.module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private int imgWidth, imgHeight, x, y;
	private String imgURL;
	
	public ImagePanel(String imgURL, int width, int height, int x, int y) {
		this.imgURL = imgURL;
		this.imgWidth = width;
		this.imgHeight = height;
		this.x = x;
		this.y = y;
		this.setBounds(x, y, width, height);
		setBackground(new Color(0, 0, 0 ,0));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		Image img = toolkit.getImage(imgURL);
		g2.setPaint(new Color(0, 0, 0, 0));
		g2.drawImage(img, 0, 0, imgWidth, imgHeight, this);
		g2.fillRect(x, y, imgWidth, imgHeight);
		g2.dispose();
	}
	@Override
	public boolean isOpaque() {
		return false;
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(imgWidth, imgHeight);
	}
}
