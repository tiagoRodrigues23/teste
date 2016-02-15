package controle;


	import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Agenda;
import modelo.Cliente;
import modelo.Pet;

	public class AgendaDAO {
		
	
		 
	public ArrayList<Agenda> buscaAgenda(){
		//Agenda agenda = new Agenda();
		ArrayList<Agenda> agendas= new ArrayList<Agenda>();
		try {  
			
			  Connection con = Conexao.getConexao();
			        Statement stmt = con.createStatement( );
			        String sql = "SELECT * FROM agenda ";
			       
			        ResultSet rs = stmt.executeQuery(sql);
			        
			        Statement stmt2 = con.createStatement( );
			        
			        while (rs.next( )) {
			        	Agenda agenda= new Agenda();
			        	Pet pet=new Pet();
			        	Cliente cli= new Cliente();
			        	String sql2 = "SELECT * FROM cliente where id="+rs.getInt(2);					       
				        ResultSet rs2 = stmt2.executeQuery(sql2);
				        if(rs2.next()){
				        	cli.setNome(rs2.getString(3));
				        	cli.setLogin(rs2.getString(12));
				        	cli.setTelefone(rs2.getString(10));
				        	cli.setCpf(rs2.getString(14));
				        }
			        	
			        	agenda.setServico(rs.getString(4));
			        	agenda.setId(rs.getInt(1));
			        	agenda.setData(rs.getDate(5));
			        	agenda.setHoraInicio(rs.getString(6));
			        	agenda.setHoraFim(rs.getString(7));
			        	agenda.setAtivo(rs.getBoolean(8));
			        	
			        	pet.setNome(rs.getString(3));
			        	pet.setCliente(cli);
			        	agenda.setCliente(cli);
			        	agenda.setPet(pet);
			        	agenda.setIdCliente(rs.getInt(2));
			        	agendas.add(agenda);
			        }
			        con.close();
		} catch (Exception ex) {
	  	  //System.out.println(ex.getMessage());
		    
	    }
		return agendas;
	}
	public boolean salvar(Agenda age) {
		     boolean resp=false;
		     try { 
		    	 
				  Connection con = Conexao.getConexao();
				  String sql = "insert into agenda values(?,?,?,?,?,?,?,?)";
		          PreparedStatement stmt = con.prepareStatement(sql);
		          stmt.setInt(1,age.getId());
		          stmt.setInt(2,age.getCliente().getId());
		          stmt.setString(3,age.getPet().getNome());
		          stmt.setString(4,age.getServico());
		          stmt.setDate(5,new java.sql.Date(age.getData().getTime()));
		          stmt.setString(6,age.getHoraInicio());
		          stmt.setString(7,age.getHoraFim());
		          stmt.setBoolean(8,age.isAtivo());
		          
		          stmt.execute();
		        con.close();
		        resp=true;
		        		     
		      } catch (Exception ex) {
		    	  System.out.println(ex.getMessage());
		    	    
		      }
		     return resp;
	}  
		     
		   public boolean excluirAgenda(Agenda age) {
		     boolean resp = false;      
		     try {  
		  Connection con = Conexao.getConexao();
		        Statement stmt = con.createStatement( );
		        String sql = "DELETE  FROM agenda ";
		               sql += "where id = " + age.getId();
		               stmt.executeUpdate(sql);
		               resp=true;
		        con.close();
		      } catch (Exception ex) { return false; }
		     return resp;
		   }
		   
		   public boolean atualizarAgenda(Agenda age) {
			     boolean resp=false;
			     try { 
			    	 
					  Connection con = Conexao.getConexao();
					  String sql = "update agenda set pet='"+age.getPet().getNome()+"',servico='"+age.getServico()+
							  "',data='"+age.getData()+"',horaFim='"+age.getHoraFim()+"',horaInicio='"+age.getHoraInicio()+"',ativo="+age.isAtivo()+" where id="+age.getId();
			          Statement stmt = con.createStatement();
			          stmt.execute(sql);
			        con.close();
			        resp=true;
			        		     
			      } catch (Exception ex) {
			    	  //System.out.println(ex.getMessage());
			    	    
			      }
			     return resp;
		} 
		   
		  
		  


	}



