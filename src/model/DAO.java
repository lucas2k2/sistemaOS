package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {

		private String Driver = "com.mysql.cj.jdbc.Driver";
		private String url = "jdbc:mysql://127.0.0.1:3306/dbsistema";
		private String user = "root";
		private String password = "";
		private Connection con;
	
		public Connection conectar() {
			//Tratamento de exeções 
			try {
				// as linhas abaixo abrem a conexão com o banco
				Class.forName(Driver);
				con = DriverManager.getConnection(url, user, password);
				return con; 
			}catch (Exception e) {
				System.out.println(e);
				return null;
			}
			
			
		}
		
}

