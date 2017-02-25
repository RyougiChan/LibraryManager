package com.ryougi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ryougi.dao.Dao;
import com.ryougi.util.Endecrypt;

public class DBtest {
	//private static String Query_0 = "SELECT * FROM Books WHERE Books.bookIndex LIKE '%?%' OR Books.ISBN LIKE '%?%'";
	// private static String Query_0 = "SELECT * FROM Books WHERE
	// Books.bookIndex LIKE ? OR Books.ISBN LIKE ?";
	// private static String Query = "SELECT * FROM Books WHERE Books.bookIndex
	// LIKE ? OR Books.ISBN LIKE ?";
	// private static String simpleQuery = "SELECT * FROM Books WHERE ISBN LIKE
	// ? OR bookIndex LIKE ?";
	private static String QUERY_COLUMN = "ISBN,bookName,author,translator,publisher,pressYear,bookIndex,state,number,source";
	private static String fuzzyQuery = "SELECT "+QUERY_COLUMN+" FROM Books WHERE Books.author LIKE ? OR Books.ISBN LIKE ? OR Books.bookName LIKE ?";	
	private static Map<Integer, String> map = new HashMap<>();
	public static void main(String[] args) throws SQLException {
		Connection conn = Dao.getConnection();
//		map.put(1, "%"+"I"+"%");
//		map.put(2, "%"+"i"+"%");
//		map.put(3, "%"+"i"+"%");
		map.put(1, "I");
		map.put(2, "I");
		map.put(3, "i");
		PreparedStatement stat = conn.prepareStatement(fuzzyQuery);
		
		for (Map.Entry<Integer, String> kv : map.entrySet()) {
			System.out.println("Key: "+kv.getKey().intValue()+"--> Value: "+kv.getValue());
//			stat.setString(kv.getKey().intValue(), kv.getValue());
			stat.setString(kv.getKey().intValue(), "%"+kv.getValue()+"%");
			}
		
		ResultSet set = stat.executeQuery();
		System.out.println("---------S--------");
		while (set.next()) {
			System.out.println(set.getString(1));
		}
		System.out.println("---------E--------");
		if (set!=null) {
			set.close();
		}
		if (conn!=null) {
			conn.close();
		}
		System.out.println("----------------------------");
		System.out.println(new Endecrypt().encodeStr("sa"));
		System.out.println("----------------------------");
		System.out.println(new Endecrypt().encodeStr("*hope8848"));
		System.out.println("----------------------------");
		System.out.println(new Endecrypt().decodeStr("KmhvcGU4ODQ4"));
	}
}
