package com.ryougi.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class OtherResolver {
	
	public static void main(String[] args) {
		new OtherResolver().saveAccount("1332244", "0000");
		List<String> list = new OtherResolver().getAccount();
		for (String s : list) {
			System.out.println(s);
		}
	}
	/**
	 * 设定窗口图标
	 * @param f 当前JFrame
	 */
	public void setIcon (JFrame f) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image i = tk.getImage("res\\cube.png");
		f.setIconImage(i);
	}
	/**
	 * 设定窗口图标
	 * @param d 当前提示窗口
	 */
	public void setIcon (JDialog d) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image i = tk.getImage("res\\cube.png");
		d.setIconImage(i);
	}
	
	/**
	 * 获取当前北京时间
	 * @return 返回当前按毫秒计的北京时间（从1970/1/1计数）
	 */
	public long getCurDateInMills() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		long longDate = 0;
		try {
			URL url = new URL("http://www.bjtime.cn");
			URLConnection conn = url.openConnection();
			conn.connect();
			longDate = conn.getDate();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return longDate;
	}
	
	/**
	 * 借书总时间计算
	 * 通过得到还书时的年月日计算借书的天数
	 * @param borrow 借书日期
	 * @param remand 还书日期（默认当前日期）
	 * @return 返回借书的总天数
	 */
	public int calOfTotalBorrowTime(Date borrow, Date remand) {
		long bDateMilliseconds = borrow.getTime();
		long rDateMilliseconds = remand.getTime();
		int totalDay = (int)(rDateMilliseconds - bDateMilliseconds) /(1000*60*60*24);
		return totalDay;
	}
	/**
	 * 根据年月获取当前月份的天数
	 * @param year	当前年份
	 * @param month	当前月份
	 * @return	返回指定年月此月份的天数
	 */
	public int getCurrentMonthDay(int year, int month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, 1);
		c.roll(Calendar.DATE, -1);
		return c.get(Calendar.DATE);
	}
	/**
	 * 计算超期还书所需罚款数目，规则：按国家图书馆规定
	 * 								 中文书刊每册每日0.30元，外文书刊每册每日0.50元
	 * @return 返回超期还书的罚款数目
	 * @param overdue 超期时间 = 借书时间 - 允许借书时间（默认 30 天）
	 * @param type 书刊的类型（中文为0/英文为1）
	 * @return 返回超期惩罚的数额
	 */
	public float calFineMoneyCum(int overdue, int type) {
		float money = 0.0f;
		while (overdue > 0) {
			overdue--;
			if (type == 0) {
				money += 0.3f;
			} else {
				money += 0.50f;
			}
		}
		return money;
	}
	/**
	 * 登录账号密码持久化处理
	 * @return 返回登陆系统的账号与密码（已加密）
	 */
	public List<String> getAccount() {
		List<String> list = new ArrayList<>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new File("res\\data.xml"));
			Element root = doc.getDocumentElement();
			NodeList children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				org.w3c.dom.Node child = children.item(i);
				if (child instanceof Element) {
					Element childElement = (Element) child;
					Text textNode = (Text) childElement.getFirstChild();
					list.add(textNode.getData().trim());
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 保存当前登录系统的账号密码（加密）
	 * @param a	加密账号
	 * @param p	加密密码
	 */
	public void saveAccount(String a, String p) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("data");
			Element account = doc.createElement("name");
			Element password = doc.createElement("value");
			
			Text at = doc.createTextNode(a);
			Text pt = doc.createTextNode(p);
			
			doc.appendChild(root);
			root.appendChild(account);
			root.appendChild(password);
			account.appendChild(at);
			password.appendChild(pt);
			
			DOMImplementation impl = doc.getImplementation();
			DOMImplementationLS implLS = (DOMImplementationLS) impl.getFeature("LS", "3.0");
			LSSerializer ser = implLS.createLSSerializer();
			LSOutput out = implLS.createLSOutput();
			out.setEncoding("UTF-8");
			out.setByteStream(Files.newOutputStream(FileSystems.getDefault().getPath("res", "data.xml")));
			ser.write(doc, out);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
