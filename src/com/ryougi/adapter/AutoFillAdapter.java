package com.ryougi.adapter;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.ryougi.dao.Dao;
import com.ryougi.iframe.AuthorityFrame;
import com.ryougi.iframe.BorrowFrame;
import com.ryougi.iframe.DonateModifyFrame;
import com.ryougi.iframe.RemandFrame;
import com.ryougi.module.LibraryDialog;
import com.ryougi.util.OtherResolver;

public class AutoFillAdapter extends FocusAdapter {

	private String NOTICE_NO_READER = "数据库中没有这位读者哟~~";									// 无读者信息提示文本
	private String NOTICE_NO_BOOKS = "馆藏中没有这本书哟~~";										// 无馆藏信息提示文本
	private String NOTICE_SEX_ERROR = "性别只能填其他/女/男/0/-1/1哟(-.-;)";
	private String NOTICE_BLANK_ISBN = "ISBN为空哟~~输点什么吧";
	private String NOTICE_ISBN_ERROR = "ISBN码格式有错哟~~";
//	private String NOTICE_ID_NEEDED = "要输入正确的证件号哟~~";
	private String dialogTitle = "提示";
	private String result = "";
	private JFrame curFrame;
	private JTextField tf;
	
	public AutoFillAdapter(JFrame curFrame, JTextField tf) {
		this.tf = tf;
		this.curFrame = curFrame;
	}
	@Override
	public void focusLost(FocusEvent e) {
		super.focusLost(e);
		
		Map<Integer, String> map = new HashMap<>();
		List<JTextField> tfList = getTfList();
		OtherResolver tr = new OtherResolver();
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
		long cu = tr.getCurDateInMills() + 1000*60*60*24*2L;
		long re = tr.getCurDateInMills() + 1000*60*60*24*30L;
		String cuDate = format.format(new Date(cu));
		String reDate = format.format(new Date(re));

		Pattern p = Pattern.compile("978-[0-9-]{13}");
		
		switch (this.getTagName()) {
		case "证件号":
			if (curFrame instanceof BorrowFrame) {
				map.put(1, this.getTfText());
				result = Dao.borrowReaderQuery(map);
				if (result.trim().equals("")) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (curFrame.isVisible()) {
								new LibraryDialog(dialogTitle, NOTICE_NO_READER);
							}
						}
					});
				} else {
					tfList.get(5).setText(" " + result);
				}
			} else if (curFrame instanceof AuthorityFrame) {
				if (tfList.get(0).getText().trim().equals("")) {
					/*SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (curFrame.isVisible()) {
								new LibraryDialog(dialogTitle, NOTICE_ID_NEEDED);
							}
						}
					});*/
				} else {
					map.put(1, tfList.get(0).getText().trim());
					List<String> data = Dao.AuthorityQuery(map);
					if (data.size()!=0) {
						tfList.get(1).setText(" "+data.get(0));
						tfList.get(2).setText(" "+data.get(1));
					}
				}
			}
			break;
		case "ISBN":
			if (curFrame instanceof BorrowFrame) {
				map.put(1, this.getTfText());
				result = Dao.borrowBooksQuery(map);
				if (result.trim().equals("")) {
					
				} else {
					tfList.get(4).setText(" " + result);
				}
				tfList.get(1).setText(" " + cuDate);
				tfList.get(3).setText(" " + reDate);
			} else if (curFrame instanceof RemandFrame) {
				if (tfList.get(0).getText().trim().equals("")) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (curFrame.isVisible()) {
								new LibraryDialog(dialogTitle, NOTICE_BLANK_ISBN);
							}
						}
					});
				} else if (!p.matcher(tfList.get(0).getText().trim()).matches()) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (curFrame.isVisible()) {
								new LibraryDialog(dialogTitle, NOTICE_NO_BOOKS);
							}
						}
					});
				} else {
					map.put(1, tfList.get(0).getText().trim());
					List<String> date = Dao.remandQuery(map);
					if (date.size()!=0) {
						tfList.get(1).setText(date.get(0));
						tfList.get(2).setText(date.get(1));
						try {
							JLabel detect = ((JLabel) tfList.get(0).getParent().getParent().getComponent(4));
							Date bDate = format.parse(date.get(0));
							Date rDate = format.parse(date.get(1));
							int totalDay = tr.calOfTotalBorrowTime(bDate, rDate);
							if (totalDay > 30) {
								detect.setText("过期了"+(totalDay-30)+"天呃~~");
								tfList.get(3).setText(tr.calFineMoneyCum(totalDay-30, 0)+"");
							} else {
								detect.setText("没过期耶 ( •̀ ω •́ )y~~");
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}
			} else if (curFrame instanceof DonateModifyFrame) {
				if (curFrame.getTitle().trim().equals("书籍信息修改")) {
					if (tfList.get(1).getText().trim().equals("")) {
					} else if (!p.matcher(tfList.get(1).getText().trim()).matches()) {
						new LibraryDialog(dialogTitle, NOTICE_ISBN_ERROR);
					} else {
						map.put(1, tfList.get(1).getText().trim());
						List<String> info = Dao.allBookInfoQuery(map);
						if (info.size()==11) {
							for (int i = 0; i < 11; i++) {
								tfList.get(i).setText(info.get(i));
							}
						} else {
							for (int i = 0; i < 11; i++) {
								if (i!=1) {
									tfList.get(i).setText("");
								}
							}
							new LibraryDialog(dialogTitle, NOTICE_NO_BOOKS);
						}
					}
				}
			}
			break;
		case "性别":
			String sex = tfList.get(4).getText()+"";
			if (!sex.equals("其他")&&!sex.equals("女")&&!sex.equals("男")&&!sex.equals("0")&&!sex.equals("1")&&!sex.equals("-1")) {
				if (curFrame.isVisible()) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (curFrame.isVisible()) {
								new LibraryDialog(dialogTitle, NOTICE_SEX_ERROR);
							}
						}
					});
				}
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		super.focusGained(e);
		List<JTextField> tfList = getTfList();
		switch (this.getTagName()) {
		case "性别":
			tfList.get(4).setText("");
			break;

		default:
			break;
		}
	}
	public String getTagName() {
		return ((JLabel)tf.getParent().getComponent(0)).getText().replaceAll(" ", "");
	}
	public String getTfText() {
		if (tf instanceof JTextField) {
			return tf.getText().trim();
		} else {
			return String.valueOf(((JPasswordField)tf).getPassword());
		}
	}
	public List<JTextField> getTfList() {
		List<JTextField> tfList = new ArrayList<>();
		Component[] cs = tf.getParent().getParent().getParent().getComponents();
		for (Component c1 : cs) {
			if (c1 instanceof Container) {
				for (Component c2 : ((Container) c1).getComponents()) {
					if (c2 instanceof Container) {
						for (Component c3 : ((Container) c2).getComponents()) {
							if (c3 instanceof JTextField) {
								tfList.add((JTextField)c3);
							}
						}
					}
				}
			}
		}
		return tfList;
	}
}
