package controle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import modelo.Cliente;
import modelo.Pet;

public class PetDAO {

	public void salvar(Pet pet) {
	     
	     try {  
	  Connection con = Conexao.getConexao();
	        Statement stmt = con.createStatement( );
	                	
	        String sql = "insert into pet (id,nome,sexo,id_cliente,idade,raca,peso,especie)" +
	        " values ("+ pet.getId()+",'"+ pet.getNome()+"','"+pet.getSexo()+"',"+pet.getCliente().getId()+","+pet.getIdade()+",'"+pet.getRaca()+
	        "',"+pet.getPeso()+",'"+pet.getEspecie()+"')";
	        stmt.executeUpdate(sql);
	        
	        
	        con.close();
	     
	      } catch (Exception ex) {
	    	  FacesContext con = FacesContext.getCurrentInstance();
	    	  con.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,ex.getMessage(),""));
	    	    
	      }
	   } 
	
	public ArrayList<Pet> buscaPet(){
		//Agenda agenda = new Agenda();
		ArrayList<Pet> listaPet= new ArrayList<Pet>();
		try {  
			
			  Connection con = Conexao.getConexao();
			        Statement stmt = con.createStatement( );
			        String sql = "SELECT * FROM pet ";
			        Statement stmt2 = con.createStatement( );
			        ResultSet rs = stmt.executeQuery(sql);
			        while (rs.next( )) {
			        	Pet pet=new Pet();
			        	Cliente cli= new Cliente();
			        	String sql2 = "SELECT * FROM cliente where id="+rs.getInt(4);
			        	
				        ResultSet rs2 = stmt2.executeQuery(sql2);
				        if(rs2.next()){
				        	cli.setNome(rs2.getString(3));
				        	cli.setLogin(rs2.getString(12));
				        }
			        	
			        	pet.setId(rs.getInt(1));
			        	pet.setNome(rs.getString(2));
			        	pet.setSexo(rs.getString(3));
			        	pet.setCliente(cli);
			        	pet.setIdade(rs.getInt(5));
			        	pet.setRaca(rs.getString(6));
			        	pet.setPeso(rs.getDouble(7));
			        	pet.setEspecie(rs.getString(8));
			        	listaPet.add(pet);
			        	
			        }
			        con.close();
		} catch (Exception ex) {
	  	  System.out.println(ex.getMessage());
		    
	    }
		return listaPet;
	}
	
	public void excluirPet(Pet pet){		
		
		try {
			Connection con = Conexao.getConexao();
			Statement stmt = con.createStatement( );
			String sql = "delete FROM pet where id="+pet.getId();
	        stmt.executeUpdate(sql);
	        con.close(); 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
        
		
	}
	
	public boolean editarPet(Pet pet){
		boolean resp=false;
		try {
			Connection con = Conexao.getConexao();
			Statement stmt = con.createStatement( );
			System.out.println("id do pet="+pet.getId());
			String sql = "update pet set idade="+pet.getIdade()+",nome='"+pet.getNome()
					+"',sexo='"+pet.getSexo()+"',raca='"+pet.getRaca()+"',peso="+pet.getPeso()
					+",especie='"+pet.getEspecie()+"' where id="+pet.getId();
	        stmt.executeUpdate(sql);
	        resp=true;
	        
	        con.close(); 
		} catch (SQLException e) {
			FacesContext con=FacesContext.getCurrentInstance();
			con.addMessage("erro",new FacesMessage(FacesMessage.SEVERITY_WARN,e.getMessage(),""));
			//System.out.println(e.getMessage());
			return false;
		}
		return resp;
		
	}
	
}
