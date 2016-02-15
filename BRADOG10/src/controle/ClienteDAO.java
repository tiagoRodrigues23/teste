package controle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import modelo.Agenda;
import modelo.Cliente;

public class ClienteDAO {
Agenda agenda = new Agenda();
	 
public Cliente buscaPorLogin(String login){
	Cliente cli=new Cliente();
	try {  
		  Connection con = Conexao.getConexao();
		        Statement stmt = con.createStatement( );
		        String sql = "SELECT * FROM cliente where login='"+login+"'";
		       
		        ResultSet rs = stmt.executeQuery(sql);
		        if (rs.next( )) {
		        	
		        	cli.setId(rs.getInt(1));
		        	cli.setNumero(rs.getInt(2));
		        	cli.setNome(rs.getString(3));
		        	cli.setUf(rs.getString(4));
		        	cli.setSexo(rs.getString(5));
		        	cli.setCidade(rs.getString(6));
		        	cli.setBairro(rs.getString(7));
		        	cli.setRua(rs.getString(8));
		        	cli.setCep(rs.getString(9));
		        	cli.setTelefone(rs.getString(10));
		        	cli.seteMail(rs.getString(11));
		        	cli.setLogin(rs.getString(12));
		        	cli.setSenha(rs.getString(13));
		        	cli.setCpf(rs.getString(14));
		        	cli.setDataNascimento(rs.getDate(15));
		        	cli.setAtivo(rs.getBoolean(16));
		        	
		        }
		        con.close();
	} catch (Exception ex) {
  	  //System.out.println(ex.getMessage());
	    return null;
    }
	return cli;
	
}

public Cliente buscaPorCPF(String cpf){
	Cliente cli= new Cliente();
	try {  
		  Connection con = Conexao.getConexao();
		        Statement stmt = con.createStatement( );
		        String sql = "SELECT * FROM cliente where cpf='"+cpf+"'";
		       
		        ResultSet rs = stmt.executeQuery(sql);
		        if (rs.next( )) {
		        	cli.setId(rs.getInt(1));
		        	cli.setNumero(rs.getInt(2));
		        	cli.setNome(rs.getString(3));
		        	cli.setUf(rs.getString(4));
		        	cli.setSexo(rs.getString(5));
		        	cli.setCidade(rs.getString(6));
		        	cli.setBairro(rs.getString(7));
		        	cli.setRua(rs.getString(8));
		        	cli.setCep(rs.getString(9));
		        	cli.setTelefone(rs.getString(10));
		        	cli.seteMail(rs.getString(11));
		        	cli.setLogin(rs.getString(12));
		        	cli.setSenha(rs.getString(13));
		        	cli.setCpf(rs.getString(14));
		        	cli.setDataNascimento(rs.getDate(15));
		        	cli.setAtivo(rs.getBoolean(16));
		        	
		        }else{
		        	cli=null;
		        }
		        con.close();
	} catch (Exception ex) {
  	  System.out.println(ex.getMessage());
	    return null;
    }
	return cli;
	
}

public Cliente buscaPorId(int id){
	Cliente cli= new Cliente();
	try {  
		  Connection con = Conexao.getConexao();
		        Statement stmt = con.createStatement( );
		        String sql = "SELECT * FROM cliente where id="+id;
		       
		        ResultSet rs = stmt.executeQuery(sql);
		        if (rs.next( )) {
		        	cli.setId(rs.getInt(1));
		        	cli.setNumero(rs.getInt(2));
		        	cli.setNome(rs.getString(3));
		        	cli.setUf(rs.getString(4));
		        	cli.setSexo(rs.getString(5));
		        	cli.setCidade(rs.getString(6));
		        	cli.setBairro(rs.getString(7));
		        	cli.setRua(rs.getString(8));
		        	cli.setCep(rs.getString(9));
		        	cli.setTelefone(rs.getString(10));
		        	cli.seteMail(rs.getString(11));
		        	cli.setLogin(rs.getString(12));
		        	cli.setSenha(rs.getString(13));
		        	cli.setCpf(rs.getString(14));
		        	cli.setDataNascimento(rs.getDate(15));
		        	cli.setAtivo(rs.getBoolean(16));
		        	
		        }else{
		        	cli=null;
		        }
		        con.close();
	} catch (Exception ex) {
  	  System.out.println(ex.getMessage());
	    return null;
    }
	return cli;
	
}

public boolean salvar(Cliente cli) {
	     boolean resp=false;
	     try {  
	  Connection con = Conexao.getConexao();
	        Statement stmt = con.createStatement( );
	        String sql = "SELECT * FROM cliente where login = '"+
	                cli.getLogin()+"' ";
	       
	        ResultSet rs = stmt.executeQuery(sql);
	        if (rs.next( )) {
	        	FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_WARN,"login já existe",""));
	        }
	        else{
	        	String sql3 = "SELECT * FROM cliente where cpf = '"+
		                cli.getCpf()+"' ";
		       
		        ResultSet rs2 = stmt.executeQuery(sql3);
		        if (rs2.next( )) {
		        	FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_WARN,"CPF já existe",""));
		        }
		        else {	        	
			        String sql2 = "insert into cliente (id,numero,ativo,nome,uf,sexo,cidade,bairro,rua,cep,cpf,login,senha,eMail,telefone,dataNascimento)" +
			        " values ("+ cli.getId()+","+ cli.getNumero()+","+ cli.getAtivo()+",'"+cli.getNome()+"','"+cli.getUf()+"','"+cli.getSexo()+"','"+cli.getCidade()+
			        "','"+cli.getBairro()+"','"+cli.getRua()+"','"+cli.getCep()+"','"+cli.getCpf()+"','"+cli.getLogin()+"',md5('"+cli.getSenha()+
			        "'),'"+cli.geteMail()+"','"+cli.getTelefone()+"','"+cli.getDataNascimento()+"')";
			        stmt.executeUpdate(sql2);
			        String sql4 = "SELECT * FROM cliente where login = '"+
			                cli.getLogin()+"' ";
			       
			        ResultSet rs3 = stmt.executeQuery(sql4);
			        if (rs3.next( )) {
			        	String sql5="insert into usuario_permissao values("+ rs3.getInt(1)+",'ROLE_USUARIO')";
				        stmt.executeUpdate(sql5);
				        resp=true;
				        
			        }
			        
		        }
	        }
	        con.close();
	        
	     
	     
	      } catch (Exception ex) {
	    	  System.out.println(ex.getMessage());
	    	    
	      }
	     return resp;
	   }  
	     
	   public boolean excluiCliente(Cliente cli) {
	     boolean resp = false;      
	     try {  
	    	Connection con = Conexao.getConexao();
	        Statement stmt = con.createStatement( );
	        String sql = "DELETE  FROM Cliente where id ="+cli.getId();
	        stmt.executeUpdate(sql);
	        resp = true;
	        con.close();
	      } catch (Exception ex) {
	    	  System.out.println(ex.getMessage());
	    	  }
	     return resp;
	   }
	   
	   public boolean autenticar(Cliente cli) {
		     boolean resp = false;
		     try {  
		        Connection con = Conexao.getConexao();
		        String sql = "SELECT * FROM cliente where login = '"+
		                cli.getLogin()+"' and senha='"+cli.getSenha()+"'";
		        Statement stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(sql);
		        if (rs.next( )) {
		            resp = true;
		        }
		        con.close();
		        return resp;
		      } catch (Exception ex) {
		           return false;
		      }
		   }
	   
	   public boolean editarCliente(Cliente cli){
			boolean resp=false;
			try {
				Connection con = Conexao.getConexao();
				Statement stmt = con.createStatement( );
				String sql = "update cliente set numero="+cli.getNumero()+",nome='"+cli.getNome()+"',uf='"+cli.getUf()+
						"',sexo='"+cli.getSexo()+"',cidade='"+cli.getCidade()+"',bairro='"+cli.getBairro()+"',rua='"+cli.getRua()+
						"',cep='"+cli.getCep()+"',telefone='"+cli.getTelefone()+"',eMail='"+cli.geteMail()+"',senha=md5('"+cli.getSenha()+
						"'),dataNascimento='"+cli.getDataNascimento()+"' where id="+cli.getId();
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
	   
	   public ArrayList<Cliente> buscaClientes(){
			ArrayList<Cliente>lista=new ArrayList<>();
			try {  
				  Connection con = Conexao.getConexao();
				        Statement stmt = con.createStatement( );
				        String sql = "SELECT * FROM cliente ";
				       
				        ResultSet rs = stmt.executeQuery(sql);
				        while (rs.next( )) {
				        	Cliente cli=new Cliente();
				        	cli.setId(rs.getInt(1));
				        	cli.setNumero(rs.getInt(2));
				        	cli.setNome(rs.getString(3));
				        	cli.setUf(rs.getString(4));
				        	cli.setSexo(rs.getString(5));
				        	cli.setCidade(rs.getString(6));
				        	cli.setBairro(rs.getString(7));
				        	cli.setRua(rs.getString(8));
				        	cli.setCep(rs.getString(9));
				        	cli.setTelefone(rs.getString(10));
				        	cli.seteMail(rs.getString(11));
				        	cli.setLogin(rs.getString(12));
				        	cli.setSenha(rs.getString(13));
				        	cli.setCpf(rs.getString(14));
				        	cli.setDataNascimento(rs.getDate(15));
				        	cli.setAtivo(rs.getBoolean(16));
				        	lista.add(cli);
				        	
				        }
				        con.close();
			} catch (Exception ex) {
		  	  System.out.println(ex.getMessage());
			    return null;
		    }
			return lista;
			
		}


	  


}
