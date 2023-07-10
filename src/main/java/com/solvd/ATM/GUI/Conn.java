package com.solvd.ATM.GUI;

import java.sql.*;

public class Conn {
	Connection c;
	Statement s;

	public Conn() {
		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");  
	         
			c = DriverManager.getConnection("jdbc:mysql:///atm", "root", "12345");
			s = c.createStatement();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
