package com.jspiders.jdbcdynamic.collable;

import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class CollableDemo {
	private static Connection connection;
	private static CallableStatement collableStatement;
	private static ResultSet resultSet;
	private static FileReader fileReader;
	private static Properties properties;
	private static String query;
	private static String filePath="C:\\WEJA1\\JDBCDynamic\\resources\\db_info.properties";
	
	public static void main(String[] args) {
		try {
			fileReader=new FileReader(filePath);
			properties=new Properties();
			properties.load(fileReader);
			
			//1.Load Driver Class
			Class.forName(properties.getProperty("driverPath"));
			
			//2.Open Connection
			connection=DriverManager.getConnection(properties.getProperty("dburl"),properties);
			
			//3.prepareStatement
			query="call proc2()";
			collableStatement=connection.prepareCall(query);
			
			//4.process result
			resultSet=collableStatement.executeQuery();//no argument method
			
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + "||" + resultSet.getString(2) + "||"
						+ resultSet.getString(3) + "||" + resultSet.getString(4));

			}
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection!=null) {
					connection.close();
				}
				if (collableStatement!=null) {
					collableStatement.close();
				}
				if (fileReader!=null) {
					fileReader.close();
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}
		}
		
		}

}
