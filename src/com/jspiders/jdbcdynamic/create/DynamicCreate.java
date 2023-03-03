package com.jspiders.jdbcdynamic.create;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DynamicCreate {
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static FileReader fileReader;
	private static Properties properties;
	private static int result;
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
			query="create table friends(? int(4) primary key,? varchar(49),? varchar(50),? longtext(10))";
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.setString(1,"id");
			preparedStatement.setString(2, "name");
			preparedStatement.setString(3,"email");
			preparedStatement.setString(4,"contact");
			
			
			//4.process result
			result=preparedStatement.executeUpdate();//no argument method
			System.err.println("Query OK,"+result+" row(s) affected.");
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection!=null) {
					connection.close();
				}
				if (preparedStatement!=null) {
					preparedStatement.close();
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
