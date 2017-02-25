package com.ryougi.module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ryougi.util.LocationCtrl;
import com.ryougi.util.OtherResolver;
import com.ryougi.util.SizeCtrl;

@SuppressWarnings("serial")
public class LibraryDialog extends JDialog {
	
	private String title;
	private String btnText = "确定";
	private Color LIGHT_BLUE_COLOR = new Color(120, 150, 232);
	
	private JPanel tPanel, bPanel;
	private JLabel nLabel;
	private JLabel bLabel_0, bLabel_1;
	private JButton nButton;
	private GridLayout gridLayout;
	
	public LibraryDialog(String title, String notice) {
		
		this.title = title;
		
		tPanel = new JPanel();
		bPanel = new JPanel();
		nLabel = new JLabel("<html><h2>" + notice + "</h2></html>");
		bLabel_0 = new JLabel("");
		bLabel_1 = new JLabel("");
		nButton = new JButton(btnText);
		gridLayout = new GridLayout(4, 1, 0, 0);
		
		initDialog();
		pack();
	}
	
	public void initDialog() {
		new OtherResolver().setIcon(this);
		setTitle(title);
		setResizable(false);
		setLayout(gridLayout);
		setPreferredSize(new Dimension(384, 216));
		setSize(SizeCtrl.dialogSizeCtrl());
		setLocation(LocationCtrl.dialogLocationCtrl());
		setVisible(true);
		
		nLabel.setForeground(LIGHT_BLUE_COLOR);
		nButton.setBackground(LIGHT_BLUE_COLOR);
		nButton.setForeground(Color.WHITE);
		nButton.setBorder(null);
		nButton.setPreferredSize(new Dimension(90, 30));
		nButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		tPanel.add(nLabel);
		bPanel.add(nButton);
		
		this.add(bLabel_0);
		this.add(tPanel);
		this.add(bLabel_1);
		this.add(bPanel);
	}
}
