package net.littlean.fastdfs;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author an 将存储文件名以及存储地址对应关系存储到数据库
 * 
 */

public class Saveinbase {
	static String url = "jdbc:mysql://localhost:3306/fastdfs?characterEncoding=utf-8";
	static String username = "root";
	static String password = "root";
	private SplitPath sp = new SplitPath();
	static {
		try {
			// 加载MySql的驱动类
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		}

	}

	public void add(String localpath,String furl) throws SQLException {
		sp.clean(localpath);
		sp.splitRealPath();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		localpath = localpath.replaceAll("\\\\", "\\\\\\\\");
		System.out.println(localpath);
		String sql = "insert into one (fullname,localpath,type,date,url)" + " values (\'" + sp.getfilename() + "." + sp.getfiletype() + "\',\'"
				+ localpath + "\',\'" + sp.getfiletype() + "\',\'" + date + "\',\'" + furl + "\');";
		System.out.println(sql);
		Connection con = DriverManager.getConnection(url, username, password);
		Statement stmt = con.createStatement();
		boolean rs = stmt.execute(sql);
		System.out.println(rs);
	}

}
