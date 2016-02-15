package controle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Horario;

public class HoraDAO {
	public ArrayList<Horario> buscaHorario(){
		//Agenda agenda = new Agenda();
		ArrayList<Horario> listaHora= new ArrayList<Horario>();
		try {  
			
			  Connection con = Conexao.getConexao();
			        Statement stmt = con.createStatement( );
			        String sql = "SELECT * FROM horarios ";
			        ResultSet rs = stmt.executeQuery(sql);
			        while (rs.next( )) {
			        	Horario h=new Horario();
			        	h.setAtivo(rs.getBoolean(3));
			        	h.setHora(rs.getString(2));
			        	h.setId(rs.getInt(1));
			        	h.setData(rs.getDate(4));
			        	listaHora.add(h);
			        	
			        }
			        con.close();
		} catch (Exception ex) {
	  	  System.out.println(ex.getMessage());
		    
	    }
		return listaHora;
	}
	
	public boolean salvar(Horario horario) {
	     boolean resp=false;
	     try {  
	    	 Connection con = Conexao.getConexao();
	    	 Statement stmt = con.createStatement( );
	       	        	
		        String sql2 = "insert into horarios (hora,ativo,data) values('"+horario.getHora()+"',"+horario.isAtivo()+",'"+horario.getData()+"')"; 
		        
		        stmt.executeUpdate(sql2);
		         con.close();
		         resp=true;
	     
	      } catch (Exception ex) {
	    	  System.out.println(ex.getMessage());
	    	    
	      }
	     return resp;
	   }  
	    
	

}
