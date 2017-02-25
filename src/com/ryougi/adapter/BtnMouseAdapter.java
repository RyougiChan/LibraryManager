package com.ryougi.adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ryougi.dao.Dao;
import com.ryougi.iframe.SearchFrame;
import com.ryougi.model.Books;
import com.ryougi.module.LibraryDialog;
import com.ryougi.ui.SearchTableModel;
import com.ryougi.util.OtherResolver;

public class BtnMouseAdapter extends MouseAdapter {

	private ArrayList<Object> list;																		// 输入框集合列表
	private String btnName = "";																		// 被点击的按钮显示文本
	private String dialogTitle = "提示";																	// 弹出提示框标题文本
	private String NOTICE_NO_USER_INPUT = "你在想什么呢…没有输入账号哟~~";								// 空账号输入框提示文本
//	private String NOTICE_NO_SUCH_USER = "数据库里没有这个账号哟~~";										// 空账号提示文本
//	private String NOTICE_VERIFY_FAIL = "密码验证出错了额，检查一下网络试试~~";							// 数据库连接错误提示文本
//	private String NOTICE_PASSWORD_ERROR = "密码错误额，检查一下再试试~~";								// 密码错误输入框提示文本
//	private String NOTICE_NO_READER = "数据库中没有这位读者哟~~";									// 无读者信息提示文本
	private String NOTICE_BLANK_SEARCH = "搜索框是空的哟，输点什么吧~~";									// 空搜索框提示文本
	private String NOTICE_BLANK_ISBN = "ISBN为空哟~~输点什么吧";											// 空ISBN输入提示
	private String NOTICE_NO_RESULT = "什么都没找到哟，换个关键字再试试~~";								// 空查询结果提示文本
	private String NOTICE_BOOKINFO_MISS = "书籍信息不全哦，检查一下吧~~";
	private String NOTICE_ERROR_EMAIL = "邮箱格式不对哦~~";
	private String NOTICE_HAD_AUTHORITIED = "已经授权了额  无须再授权~~";
	private String NOTICE_ID_NEEDED = "要输入正确的证件号哟~~";
	private String NOTICE_MSG_MISSED = "信息没填完整不给注册哟~~";
	private String NOTICE_ISBN_ERROR = "ISBN码格式错了哟~~";
	private String NOTICE_NUMBER_ERROR = "年份||数目||价格格式错了哟~~";
//	private String NOTICE_SEX_ERROR = "性别只能填其他/女/男/0/-1/1哟(-.-;)";
	private String searchText;																			// 搜索框的输入文本
	private List<Books> dataList;
	private Map<Integer, String> map;																	// 查询预备语句填充映射
	
	private JFrame curFrame;																			// 当前操作的主窗口

	
	public BtnMouseAdapter(JFrame curFrame, JButton btn, ArrayList<Object> list) {
		this.curFrame = curFrame;
		this.list = list;
		btnName = btn.getText().replaceAll(" ", "");
	}
	
	public List<String> getTfTextList() {
		List<String> tfTextList = new ArrayList<>();
		for (Object o : list) {
			JTextField tf = (JTextField) o;
			tfTextList.add(tf.getText().trim());
		}
		return tfTextList;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(btnName + " Clicked");
		switch (btnName) {
		case "登录":
			loginBtnLogic();
			break;
		case "重置":
			resetBtnLogic();
			break;
		case "检索":
			searchBtnLogic();
			break;
		case "回收确认":
			recycleBtnLogic();
			break;
		case "借出":
			borrowBtnLogic();
			break;
		case "授权":
			authorityBtnLogic();
			break;
		case "注册":
			registerBtnLogic();
			break;
		case "新书入库":
			newBookInputLogic();
			break;
		case "捐赠入库":
			donateBookLogic();
			break;
		case "提交修改":
			ModifyLogic();
			break;
		case "取消":
			curFrame.setVisible(false);
			curFrame.dispose();
			break;
		default:
			break;
		}
	}

	/**
	 * <b>登录页面</b> <b>登录按钮</b>点击时触发的逻辑处理 主要是进行身份验证获取登录系统的权限
	 */
	private void loginBtnLogic() {
		JTextField adminField = (JTextField) list.get(0);
		JPasswordField passwordField = (JPasswordField) list.get(1);
		String admin = adminField.getText().trim();
		String password = String.valueOf(passwordField.getPassword());
		
		if (admin.equals("")) {
			new LibraryDialog(dialogTitle, NOTICE_NO_USER_INPUT);
		} else {
			map = new HashMap<>();
			map.put(1, admin);
			Dao.loginCert(curFrame, password, map);
		}
	}

	/**
	 * <b>登录界面</b> <b>重置按钮</b>点击时触发的逻辑处理 将用户和密码输入框清空
	 */
	private void resetBtnLogic() {
		for (Object textField : list) {
			((JTextField) textField).setText("");
		}
	}

	/**
	 * <b>检索页面</b> <b>检索按钮</b>点击时出发的逻辑处理
	 * 根据搜索框输入的关键词和JComboBox下拉菜单的查询模式从数据库查询数据并呈现到列表中 如果无查询结果，弹出提示框
	 */
	private void searchBtnLogic() {
		searchText = ((JTextField)list.get(0)).getText().trim(); 		// 获取搜索框输入的搜索文本内容
																// 获取下拉菜单关键词判断搜索模式
		String keywords = (String) ((Object[]) (((SearchFrame)curFrame).getKeywordsList().getSelectedItem()))[0];
		map = new HashMap<>();
		if (keywords.trim().equals("模糊检索")) {
			map.put(1, searchText);									// 创建关键词映射作为查询预备语句
			map.put(2, searchText);									// 创建关键词映射作为查询预备语句
			map.put(3, searchText);									// 创建关键词映射作为查询预备语句
		} else {
			map.put(1, searchText);									// 创建关键词映射作为查询预备语句
		}
		
		if (searchText.trim().equals("")) {
			new LibraryDialog(dialogTitle, NOTICE_BLANK_SEARCH);
		} else {
 			dataList = Dao.SearchQuery(keywords, map);
			if (dataList.size() == 0) {
				new LibraryDialog(dialogTitle, NOTICE_NO_RESULT);
			} else {
				List<Books> dataList = Dao.SearchQuery(keywords, map);
				SearchFrame sFrame = (SearchFrame) curFrame;
				SearchTableModel sModel = (SearchTableModel)sFrame.getsTableModel();
				sModel.setData(dataList);
			}
		}
	}

	/**
	 * <b>归还界面</b> 确认回收按钮点击处理逻辑
	 */
	private void recycleBtnLogic() {
		String ISBN = ((JTextField)list.get(0)).getText().trim();
		if (!getTfTextList().get(3).trim().equals("")) {
			map.put(1, "1");
			map.put(2, ISBN);
			Dao.bookStateUpdate(map);
		}
	}
	/**
	 * <b>捐赠界面</b> 捐赠入库按钮点击处理逻辑
	 */
	private void donateBookLogic() {
		newBookInputLogic();
		map.put(13, getTfTextList().get(10));
		System.out.println(getTfTextList().get(10));
	}
	/**
	 * <b>书籍信息修改界面</b> 提交修改按钮点击处理逻辑
	 */
	private void ModifyLogic() {
		Pattern p = Pattern.compile("978-[0-9-]{13}");
		List<String> bm = getTfTextList();
		if (bm.get(0).equals("")) {
			new LibraryDialog(dialogTitle, NOTICE_BLANK_ISBN);
		} else if (!p.matcher(bm.get(0)).matches()) {
			new LibraryDialog(dialogTitle, NOTICE_ISBN_ERROR);
		} else {
			
		}
	}
	/**
	 * <b>新书购入界面</b> 新书入库按钮点击处理逻辑
	 */
	private void newBookInputLogic() {
		List<String> bm = getTfTextList();
		map = new HashMap<>();
		Map<Integer, String> orderMap = new HashMap<>();
		String admin = new OtherResolver().getAccount().get(0);
		if (bm.get(0).equals("")||bm.get(1).equals("")||bm.get(2).equals("")||bm.get(8).equals("")||bm.get(4).equals("")||bm.get(5).equals("")||bm.get(6).equals("")||bm.get(7).equals("")) {
			new LibraryDialog(dialogTitle, NOTICE_BOOKINFO_MISS);
		} else {
			if (bm.get(0).length()!=17) {
				new LibraryDialog(dialogTitle, NOTICE_ISBN_ERROR);
			} else if (Pattern.compile("[0-9.]*").matcher(bm.get(5)+bm.get(6)+bm.get(7)).matches()) {
				for (int i = 0; i < bm.size(); i++) {
					// -----------！此处应该修改(添加照片信息而不是null)！------------
					if (i==9) {
						map.put(10, null);
					} else {
						map.put(i+1, bm.get(i));
					}
				}
				map.put(11, "1");
				map.put(12, "1");
				Dao.bookInsert(map);
				orderMap.put(1, bm.get(0));
				orderMap.put(2, getDate());
				orderMap.put(3, bm.get(7));
				orderMap.put(4, Dao.operatorIDQuery(admin));
				orderMap.put(5, "1");
				Dao.OrderInsert(orderMap);
			} else {
				new LibraryDialog(dialogTitle, NOTICE_NUMBER_ERROR);
			}
		}
	}
	/**
	 * 获取当前北京时间
	 * @return 返回当前北京时间
	 */
	public String getDate() {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
		OtherResolver tr = new OtherResolver();
		long cu = tr.getCurDateInMills() + 1000*60*60*24*2L;
		String cuDate = format.format(new Date(cu));
		return cuDate;
	}
	/**
	 * <b>注册界面</b> 注册按钮点击处理逻辑
	 */
	private void registerBtnLogic() {
		Pattern emailPattern = Pattern.compile("[a-zA-Z0-9_]+[.-]*[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[\\w]+");	// 邮箱格式验证
		Pattern phonePattern = Pattern.compile("[0-9]{11}");
		
		OtherResolver tr = new OtherResolver();
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
		long cu = tr.getCurDateInMills() + 1000*60*60*24*2L;
		String cuDate = format.format(new Date(cu));
		
		// msg 列表项顺序 --> 证件号ID，角色，姓名，性别，电话，邮箱
		List<String> msg = getTfTextList();
		map = new HashMap<>();

		System.out.println(getTfTextList());
		if (msg.get(1).equals("") || msg.get(2).equals("") || msg.get(3).equals("") || msg.get(5).equals("")) {
			new LibraryDialog(dialogTitle, NOTICE_MSG_MISSED);
		} else if (!emailPattern.matcher(msg.get(5)).matches()) {
			new LibraryDialog(dialogTitle, NOTICE_ERROR_EMAIL);
		} else if (!phonePattern.matcher(msg.get(4)).matches()) {
			new LibraryDialog(dialogTitle, NOTICE_ERROR_EMAIL);
		} else {
			if (curFrame.getTitle().trim().equals("读者")) {
				for (int i = 0; i < msg.size(); i++) {
					if (i == 1) {
						map.put(2, "0");
					} else {
						map.put(i + 1, msg.get(i));
					}
				}
				map.put(8, cuDate);
			} else {
				for (int i = 0; i < 5; i++) {
					if (i == 0) {
						map.put(1, msg.get(0));
					} else {
						map.put(i + 1, msg.get(i + 1));
					}
				}
			}
			Dao.NewUserInsert(map);
		}
	}
	/**
	 * <b>借书界面</b> 借出按钮点击处理逻辑
	 */
	private void borrowBtnLogic() {
		Pattern p = Pattern.compile("978-[0-9-]{13}");
		Map<Integer, String> bMap = new HashMap<>();
		List<String> borrowMsg = getTfTextList();
		map = new HashMap<>();
		if (!borrowMsg.get(0).equals("")) {
			if (!p.matcher(borrowMsg.get(1).trim()).matches()) {
				new LibraryDialog(dialogTitle, NOTICE_ISBN_ERROR);
			} else {
				if (borrowMsg.get(2)!=null&&!borrowMsg.get(2).trim().equals("")) {
					map.put(1, "-1");
					map.put(2, borrowMsg.get(1));
					Dao.bookStateUpdate(map);
					bMap.put(1, borrowMsg.get(1));
					bMap.put(2, borrowMsg.get(0));
					bMap.put(3, Dao.operatorIDQuery(new OtherResolver().getAccount().get(0)));
					bMap.put(4, borrowMsg.get(2));
					bMap.put(5, borrowMsg.get(4));
					Dao.borrowInSert(bMap);
				}
			}
		}
	}
	/**
	 * <b>借书权限界面</b> 授权按钮点击处理逻辑
	 */
	private void authorityBtnLogic() {
		List<String> msg = getTfTextList();
		map = new HashMap<>();
		if (msg.get(2).trim().equals("")) {
			new LibraryDialog(dialogTitle, NOTICE_ID_NEEDED);
		} else {
			if (msg.get(2)!=null&&!msg.get(2).trim().equals("")) {
				if (msg.get(2).equals("已授权")) {
					new LibraryDialog(dialogTitle, NOTICE_HAD_AUTHORITIED);
				} else {
					map.put(1, "1");
					map.put(2, msg.get(0));
					Dao.authorityUpdate(map);
				}
			}
		}
	}
	
}
