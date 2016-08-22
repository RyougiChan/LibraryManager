package com.ryougi.dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;

import com.ryougi.Library;
import com.ryougi.model.Books;
import com.ryougi.module.LibraryDialog;
import com.ryougi.util.Endecrypt;
import com.ryougi.util.OtherResolver;

public class Dao {

	private static String driverName = null;
	private static String urlName = null;
	private static String userName = null;
	private static String paw = null;
	private static Connection conn = null;

	private static String dialogTitle = "提示";																	// 弹出提示框标题文本
	private static String NOTICE_DBCONN_TIMEOUT = "网络连接/数据库连接有问题哟~";									// 连接数据库超时
	private static String NOTICE_NO_SUCH_USER = "数据库里没有这个账号哟~~";										// 空账号提示文本
	private static String NOTICE_VERIFY_FAIL = "密码验证出错了额，检查一下网络试试~~";								// 数据库连接错误提示文本
	private static String NOTICE_PASSWORD_ERROR = "密码错误额，检查一下再试试~~";									// 密码错误输入框提示文本
	private static String NOTICE_QUERY_FAIL = "查询出错了额 ♪(´▽｀)";
	private static String NOTICE_CANNOT_RESOLVE = "无法解析搜索结果额Σ(´∀｀)";
	private static String NOTICE_STATE_UPDATE_FAIL = "书籍状态更新失败啦~~";
	private static String NOTICE_STATE_UPDATE_SUCCESS = "书籍状态更新成功啦~~";
	private static String NOTICE_BOOKADD_FAIL = "书籍入库失败啦~~";
	private static String NOTICE_BOOKADD_SUCCESS = "书籍入库成功啦~~";
	private static String NOTICE_AUTHORITY_FAIL = "授权失败了  悲剧~~";
	private static String NOTICE_AUTHORITY_SUCCESS = "授权成功啦  Cheer~~";
	private static String NOTICE_BOOK_ONSHELF = "这本书还在架上哟~~";
	private static String NOTICE_REGI_FAIL = "注册失败了  悲剧~~";
	private static String NOTICE_REGI_SUCCESS = "注册成功了 Cheer~~";
	private static String NOTICE_NO_SUCH_READER = "读者信息未注册哟~~";
	//(cover,ISBN,bookName,author,translator,publisher,pressYear,bookIndex,source,number,state,price)
	private static String ALL_BOOKINFO = "SELECT cover,ISBN,bookName,author,translator,publisher,pressYear,bookIndex,number,price FROM Books WHERE Books.ISBN = ?";							// 查询所以书籍信息(12列数据)
	private static String BOOKS_COLUMN = "(ISBN,bookName,author,translator,publisher,pressYear,bookIndex,number,price,cover,source,state)" ;
	private static String SEARCH_QUERY_COLUMN = "ISBN,bookName,author,translator,publisher,pressYear,bookIndex,state,number,source";
	private static String loginQuery = "SELECT password FROM Operator WHERE Operator.admin = ?";
	private static String ISBNQuery = "SELECT "+SEARCH_QUERY_COLUMN+" FROM Books WHERE Books.ISBN LIKE ?";		// ISBN查询模式查询语句
	private static String authorQuery = "SELECT "+SEARCH_QUERY_COLUMN+" FROM Books WHERE Books.author LIKE ?";	// 著者查询模式查询语句
	private static String bookNameQuery = "SELECT "+SEARCH_QUERY_COLUMN+" FROM Books WHERE Books.bookName LIKE ?";	// 书名查询模式查询语句
	private static String fuzzyQuery = "SELECT "+SEARCH_QUERY_COLUMN+" FROM Books WHERE Books.author LIKE ? OR Books.ISBN LIKE ? OR Books.bookName LIKE ?";	// 模糊查询模式查询语句
	private static String borrowQuery_Reader = "SELECT authority FROM Reader WHERE Reader.readerID = ?";	// 读者权限查询
	private static String borrowQuery_Books = "SELECT bookName FROM Books WHERE Books.ISBN = ?";			// 书籍名称查询
	private static String bookStateUpdate = "UPDATE Books SET Books.state = ? WHERE Books.ISBN = ?";
	private static String borrowInsert = "INSERT INTO Borrow (ISBN,readerID,operatorID,borrowDate,backDate) VALUES (?,?,?,?,?)";
	private static String remandDateQuery = "SELECT borrowDate,backDate FROM Borrow WHERE Borrow.ISBN = ?";
	private static String newBookInsert = "INSERT INTO Books "+BOOKS_COLUMN+" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	private static String donatorInfoInsert = "INSERT INTO Donate (ISBN,number,donatorName,donateDate,operatorID)";
	private static String authorityQuery = "SELECT name,authority FROM Reader WHERE Reader.readerID=?";
	private static String authorityUpdate = "UPDATE Reader SET Reader.authority = ? WHERE Reader.readerID = ?";
	private static String readerInsert = "INSERT INTO Reader (readerId,occupation,name,sex,tel,email,authority,registerDate) VALUES (?,?,?,?,?,?,?,?)";
	private static String operatorInsert = "INSERT INTO Operator (operatorID,name,sex,tel,email) VALUES (?,?,?,?,?)";
	private static String orderInsert = "INSERT INTO Order (ISBN,inputDate,number,operatorID,state) VALUES (?,?,?,?,?)";
	
//	private static String allQuery = "SELECT "+QUERY_COLUMN+" FROM Books WHERE Books.ISBN LIKE ?";
	
	private static PreparedStatement stat;
	private static ResultSet queryResultSet;
	private static List<Books> dataList;
	private static Books book;
	private static String ISBN, bookName, author, translator, publisher, bookIndex, pressYear, state, source, number;

	public Dao() {
		conn = getConnection();
	}
	
	/**
	 * 创建数据库连接，连接信息从外部属性文件db.propreties获取
	 * 数据库类型为SQLServer数据库
	 * @return 返回数据库连接
	 */
	public static Connection getConnection() {
		Properties properties = new Properties();
		try {
			InputStream in = Files.newInputStream(Paths.get("res\\db.properties"));
			Endecrypt codec = new Endecrypt();
			properties.load(in);
			driverName = properties.getProperty("jdbc.drivers");
			urlName = properties.getProperty("jdbc.url");
			userName = properties.getProperty("jdbc.username");
			paw = properties.getProperty("jdbc.password");
			if (driverName != null) {
				System.setProperty("jdbc.drivers", driverName);
			}
			DriverManager.setLoginTimeout(2);
			return DriverManager.getConnection(urlName, codec.decodeStr(userName), codec.decodeStr(paw));
		} catch (IOException e) {
			return null;
		} catch (SQLException e) {
			new LibraryDialog("提示", NOTICE_DBCONN_TIMEOUT);
			return null;
		}
		
	}
	/**
	 * 执行查询语句
	 * @param sql SQL查询语句
	 * @return 返回查询结果集
	 */
	public static ResultSet excuteQuery(String sql) {
		try {
			if (conn == null) {
				new Dao();
			}
			return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery(sql);
		} catch (SQLException e) {
			return null;
		}
	}
	/**
	 * 执行预备语句
	 * @param sql SQL预备语句
	 * @param map 传值映射，用来填充预备语句
	 * @return 返回查询结果集
	 */
	public static ResultSet excutePrepareQuery(String sql, Map<Integer, String> map) {
		try {
			if (conn == null) {
				new Dao();
			}
			stat = conn.prepareStatement(sql);
			if (sql.contains("LIKE")) {
				for (Map.Entry<Integer, String> keyValue : map.entrySet()) {
					stat.setString(keyValue.getKey().intValue(), "%"+keyValue.getValue()+"%");
				}
			} else {
				for (Map.Entry<Integer, String> keyValue : map.entrySet()) {
					stat.setString(keyValue.getKey().intValue(), ""+keyValue.getValue()+"");
				}
			}
			return stat.executeQuery();
		} catch(SQLException e) {
			new LibraryDialog("提示", "哎呀，查询失败了~~");
			return null;
		}
	}
	/**
	 * 更新数据库
	 * @param sql SQL更新语句
	 * @return 返回更新状态，1表示更新完成， -1表示更新失败
	 */
	public static int excuteUpdate(String sql) {
		try {
			if (conn == null) {
				new Dao();
			}
			return conn.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			return -1;
		}
	}
	/**
	 * 更新数据库预备语句
	 * @param sql SQL更新语句
	 * @param map 传值映射，用来填充预备语句
	 * @return 返回更新状态，1表示更新完成， -1表示更新失败
	 */
	public static int excutePrepareUpdate(String sql, Map<Integer, String> map) {
		try {
			if (conn == null) {
				new Dao();
			}
			stat = conn.prepareStatement(sql);
			for (Map.Entry<Integer, String> kv : map.entrySet()) {
				if (kv.getValue()!=null) {
					stat.setString(kv.getKey().intValue(), kv.getValue());
				} else {
					stat.setNull(kv.getKey().intValue(), Types.LONGVARBINARY);
				}
			}
			return stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * 关闭数据库连接，释放资源
	 */
	public static void closeConn() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Close Failed");
			} finally {
				conn = null;
			}
		}
	}
	/**
	 * 关闭查询结果，释放资源
	 */
	public static void closeSet() {
		if (queryResultSet!=null) {
			try {
				queryResultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
 	
	/**
	 * 
	 * 登录页面
	 * 登录验证查询
	 * @param login	登录窗口
	 * @param password	密码输入框输入的密码
	 * @param map 传值映射，用来填充预备语句
	 */
	public static void loginCert(JFrame login, String password, Map<Integer, String> map) {
		queryResultSet = Dao.excutePrepareQuery(loginQuery, map);
		try {
			if (queryResultSet == null || queryResultSet.next()==false) {
				new LibraryDialog(dialogTitle, NOTICE_NO_SUCH_USER);
			} else {
				String db_psd = queryResultSet.getString(1);
				if (password.equals(db_psd)) {
					login.setVisible(false);
					new Library().setVisible(true);
					new OtherResolver().saveAccount(new Endecrypt().encodeStr(map.get(1)), new Endecrypt().encodeStr(db_psd));
				} else {
					new LibraryDialog(dialogTitle, NOTICE_PASSWORD_ERROR);
				}
			}
		} catch (SQLException e) {
			new LibraryDialog(dialogTitle, NOTICE_VERIFY_FAIL);
		} finally {
			closeSet();
			closeConn();
		}
	}
	/**
	 * <b>检索页面</b> 特定检索模式的检索逻辑处理
	 * 通过关键字匹配（部分或完全匹配）来查询
	 * @param keywords	已选择的检索模式
	 * @param map 传值映射，用来填充预备语句
	 * @return 搜索到的书籍列表
	 */
	public static List<Books> SearchQuery (String keywords, Map< Integer, String> map) {
		switch (keywords.trim()) {
		case "模糊检索":
			queryResultSet = excutePrepareQuery(fuzzyQuery,map);
			break;
		case "ISBN检索":
			queryResultSet = excutePrepareQuery(ISBNQuery, map);
			break;
		case "书名检索":
			queryResultSet = excutePrepareQuery(bookNameQuery, map);
			break;
		case "著者检索":
			queryResultSet = excutePrepareQuery(authorQuery, map);
			break;
		default:
			queryResultSet = excutePrepareQuery(fuzzyQuery, map);
			break;
		}
		try {
			dataList = new ArrayList<>();
			while (queryResultSet.next()) {
				book = new Books();
				ISBN = queryResultSet.getString(1) + "";
				bookName = queryResultSet.getString(2) + "";
				author = queryResultSet.getString(3) + "";
				translator = queryResultSet.getString(4)==null?"无":queryResultSet.getString(4) + "";
				publisher = queryResultSet.getString(5) + "";
				pressYear = queryResultSet.getString(6) + "";
				bookIndex = queryResultSet.getString(7) + "";
				state = queryResultSet.getInt(8)+"'";
				number = queryResultSet.getInt(9) + "";
				source = queryResultSet.getInt(10) + "";
				if (source.equals("0")) {
					source="本地馆藏";
				} else if (state.equals("1")) {
					source="购买入库";
				} else {
					source="捐赠入库";
				}
				if (state.equals("0")) {
					state="正在购买";
				} else if (state.equals("1")) {
					state="在架上";
				} else {
					state="已借出";
				}
				book.setAuthor(author);
				book.setBookIndex(bookIndex);
				book.setBookName(bookName);
				book.setISBN(ISBN);
				book.setNumber(number);
				book.setPressYear(pressYear);
				book.setPrice(0);
				book.setState(state);
				book.setPublisher(publisher);
				book.setTranslator(translator);
				book.setSource(source);
				dataList.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new LibraryDialog(dialogTitle, NOTICE_CANNOT_RESOLVE);
		} finally {
			closeSet();
			closeConn();
		}
		return dataList;
	}
	/**
	 * <b>借出页面</b> 读者权限检索
	 * @param map 读者证件号映射
	 * @return 返回授权结果
	 */
	public static String borrowReaderQuery (Map<Integer, String> map) {
		String result = " ";
		queryResultSet = Dao.excutePrepareQuery(borrowQuery_Reader, map);
		try {
			if (queryResultSet.next()) {
				result = queryResultSet.getString(1);
				if (result.equals("1")) {
					result = "已授权";
				} else {
					result = "未授权";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new LibraryDialog(dialogTitle, NOTICE_CANNOT_RESOLVE);
		} finally {
			closeSet();
			closeConn();
		}
		return result;
	}
	/**
	 * <b>借出页面</b> 书籍信息（书名）检索
	 * @param map ISBN映射
	 * @return 返回书籍名称
	 */
	public static String borrowBooksQuery (Map<Integer, String> map) {
		String result = " ";
		queryResultSet = Dao.excutePrepareQuery(borrowQuery_Books, map);
		try {
			if (queryResultSet.next()) {
				result = queryResultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new LibraryDialog(dialogTitle, NOTICE_CANNOT_RESOLVE);
		} finally {
			closeSet();
			closeConn();
		}
		return result;
	}
	/**
	 * <b>借出\归还页面</b> 更新数据库中Books表中书籍的借阅状态为-1
	 * @param map 传值映射，用来填充预备语句
	 */
	public static void bookStateUpdate(Map<Integer, String> map) {
		int v = Dao.excutePrepareUpdate(bookStateUpdate, map);
		closeConn();
		if (v != 1) {
			new LibraryDialog(dialogTitle, NOTICE_STATE_UPDATE_FAIL);
		} else {
			new LibraryDialog(dialogTitle, NOTICE_STATE_UPDATE_SUCCESS);
		}
	}
	/**
	 * <b>借出页面</b> 更新数据库中Borrow表,插入数据
	 * @param map 传值映射，用来填充预备语句
	 */
	public static void borrowInSert(Map<Integer, String> map) {
		Dao.excutePrepareUpdate(borrowInsert, map);
		closeConn();
	}
	/**
	 * <b>归还页面</b> 更新Books表中书籍的借阅状态为1
	 * @param map	书籍ISBN映射
	 * @return	书籍的借出时间和应还时间列表
	 */
	public static List<String> remandQuery(Map<Integer, String> map) {
		List<String> dateList = new ArrayList<>();
		ResultSet set =	Dao.excuteQuery("SELECT ISBN FROM Books WHERE Books.ISBN='" + map.get(1)+"'");
		try {
			if (set.next()) {
				queryResultSet = Dao.excutePrepareQuery(remandDateQuery, map);
					if (!queryResultSet.next()) {
						new LibraryDialog(dialogTitle, NOTICE_BOOK_ONSHELF);
					} else {
						while (queryResultSet.next()) {
							dateList.add(queryResultSet.getString(1));
							dateList.add(queryResultSet.getString(2));
						}
					}
			} else {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (set!=null) {
				try {
					set.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return dateList;
	}
	/**
	 * <b>新书入库页面</b> 向Books表插入一组新的书籍数据
	 * @param map 书籍信息映射
	 */
	public static void bookInsert(Map<Integer, String> map) {
		int v= Dao.excutePrepareUpdate(newBookInsert, map);
		if (v != -1) {
			new LibraryDialog(dialogTitle, NOTICE_BOOKADD_SUCCESS);
		} else {
			new LibraryDialog(dialogTitle, NOTICE_BOOKADD_FAIL);
		}
	}
	/**
	 * <b>新书入库页面</b> 向Books表插入一组新的书籍数据
	 * @param map 捐赠信息和操作员映射
	 */
	public static void donateInsert(Map<Integer, String> map) {
		int v= Dao.excutePrepareUpdate(donatorInfoInsert, map);
		if (v != -1) {
			new LibraryDialog(dialogTitle, NOTICE_BOOKADD_SUCCESS);
		} else {
			new LibraryDialog(dialogTitle, NOTICE_BOOKADD_FAIL);
		}
	}
	/**
	 * <b>借书授权页面</b> 查询读者授权信息
	 * @param map	读者证件号readID映射
	 * @return	返回授权信息列表
	 */
	public static List<String> AuthorityQuery(Map<Integer, String> map) {
		List<String> data = new ArrayList<>();
		ResultSet set = Dao.excuteQuery("SELECT readerID FROM Reader WHERE Reader.readerID='"+map.get(1)+"'");
		try {
			if (set.next()) {
				queryResultSet = Dao.excutePrepareQuery(authorityQuery, map);
				String authority = "未授权";
				if (queryResultSet.next()) {
					data.add(queryResultSet.getString(1));
					if (queryResultSet.getInt(2)==1) {
						authority = "已授权";
					}
					data.add(authority);
				}
			} else {
				new LibraryDialog(dialogTitle, NOTICE_NO_SUCH_READER);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (set!=null) {
				try {
					set.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}
	/**
	 * <b>借书授权页面</b> 更新读者授权状态为1
	 * @param map 传值映射，用来填充预备语句
	 */
	public static void authorityUpdate(Map<Integer, String> map) {
		int v = Dao.excutePrepareUpdate(authorityUpdate, map);
		closeConn();
		if (v != 1) {
			new LibraryDialog(dialogTitle, NOTICE_AUTHORITY_FAIL);
		} else {
			new LibraryDialog(dialogTitle, NOTICE_AUTHORITY_SUCCESS);
		}
	}
	/**
	 * <b>书籍信息修改页面</b> 根据ISBN查询数据库中书籍的所有信息
	 * @param map	ISBN映射
	 * @return	书籍的完整信息列表
	 */
	public static List<String> allBookInfoQuery(Map<Integer, String> map) {
		List<String> info = new ArrayList<>();
		ResultSet donatorSet = Dao.excuteQuery("SELECT donatorName FROM Donate WHERE Donate.ISBN = '"+map.get(1)+"'");
		queryResultSet = Dao.excutePrepareQuery(ALL_BOOKINFO, map);
		try {
			while (queryResultSet.next()) {
				for (int i = 0; i < 10; i++) {
					String item = queryResultSet.getString(i+1)+"";
					if (item.equals("null")||item.trim().equals("")) {
						item="无";
					}
					info.add(item);
				}
			}
			if (donatorSet.next()) {
				info.add(donatorSet.getString(1));
			} else {
				info.add("无");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new LibraryDialog(dialogTitle, NOTICE_QUERY_FAIL);
		}
		System.out.println(info);
		return info;
	}
	/**
	 * <b>注册页面</b> 操作员/读者注册操作
	 * @param map	注册信息映射
	 */
	public static void NewUserInsert(Map<Integer, String> map) {
		String role = map.get(2);
		int v = -1;
		if (role.equals("0")) {
			map.put(7, "0");
			v = Dao.excutePrepareUpdate(readerInsert, map);
		} else {
			v = Dao.excutePrepareUpdate(operatorInsert, map);
		}
		closeConn();
		if (v != 1) {
			new LibraryDialog(dialogTitle, NOTICE_REGI_FAIL);
		} else {
			new LibraryDialog(dialogTitle, NOTICE_REGI_SUCCESS);
		}
	}
	/**
	 * <b>新书订单表</b> 当购买书籍并向书库插入新数据，调用此方法将书籍额外信息添加带订单表
	 * 以表示馆藏书籍的来源途径
	 * @param map 传值映射，用来填充预备语句
	 */
	public static void OrderInsert(Map<Integer, String> map) {
		Dao.excutePrepareUpdate(orderInsert, map);
	}
	/**
	 * @param admin 操作员的账号,根据账号获取ID
	 * @return 返回操作员ID
	 */
	public static String operatorIDQuery(String admin) {
		try {
			return Dao.excuteQuery("SELECT operatorID FROM Operator WHERE Operator.admin='"+admin+"'").getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
}
