package controle;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

	public static Connection getConexao()
	    {
	       try 
	        {  
	         Class.forName("com.mysql.jdbc.Driver");
	         String url="jdbc:mysql://localhost/bradog"; 
	         
	          Connection con = DriverManager.getConnection(url, "root", "loaijnupr9384");       
	          return con;
	        } catch(Exception ex) 
	        {
	        	System.out.println(ex.getMessage());
	            return null; 
	        }
	     
	   }
	}


