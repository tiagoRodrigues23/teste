package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Produto;

import org.primefaces.model.DefaultStreamedContent;

public class ProdutoDAO {

	public boolean salvar(Produto prod) {
	     boolean resp=false;
	     try {  
	  Connection con = Conexao.getConexao();
	  String sql = "insert into produto values (?,?,?,?,?)";
		    PreparedStatement pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1,prod.getId());
	        pstmt.setString(2, prod.getNome());
	        pstmt.setString(3,prod.getDescricao());
	        pstmt.setDouble(4,prod.getPreco());		       
	        pstmt.setBinaryStream(5, prod.getImagem().getStream());        	
	        pstmt.execute();
	        
	        resp=true;
	        con.close();
	     
	      } catch (Exception ex) {
	    	  System.out.println(ex.getMessage());
	      }
	     return resp;
	   } 
	
	public Produto buscaProdutoPorId(int id){
		//Agenda agenda = new Agenda();
		Produto prod= new Produto();
		try {  
			
			  Connection con = Conexao.getConexao();
			        Statement stmt = con.createStatement( );
			        String sql = "SELECT * FROM produto where id="+id;
			        ResultSet rs = stmt.executeQuery(sql);
			       if (rs.next( )) {
			        	prod.setId(rs.getInt(1));
			        	prod.setNome(rs.getString(2));
			        	prod.setDescricao(rs.getString(3));
			        	prod.setPreco(rs.getDouble(4));
			        	   	
			        	prod.setImagem(new DefaultStreamedContent(rs.getBinaryStream(5)));
			        	
			        }
			        con.close();
		} catch (Exception ex) {
	  	  System.out.println(ex.getMessage());
		    
	    }
		return prod;
	}
	
	
	
	public ArrayList<Produto> buscaProduto(){
		//Agenda agenda = new Agenda();
		ArrayList<Produto> listaProd= new ArrayList<Produto>();
		try {  
			
			  Connection con = Conexao.getConexao();
			        Statement stmt = con.createStatement( );
			        String sql = "SELECT * FROM produto ";
			        ResultSet rs = stmt.executeQuery(sql);
			        while (rs.next( )) {
			        	Produto prod= new Produto();
			        	prod.setId(rs.getInt(1));
			        	prod.setNome(rs.getString(2));
			        	prod.setDescricao(rs.getString(3));
			        	prod.setPreco(rs.getDouble(4));
			        	   	
			        	prod.setImagem(new DefaultStreamedContent(rs.getBinaryStream(5)));
			        	listaProd.add(prod);
			        	
			        }
			        con.close();
		} catch (Exception ex) {
	  	  System.out.println(ex.getMessage());
		    
	    }
		return listaProd;
	}
	
	
	public void excluirProduto(Produto prod){		
		
		try {
			Connection con = Conexao.getConexao();
			Statement stmt = con.createStatement( );
			String sql = "delete FROM produto where id="+prod.getId();
	        stmt.executeUpdate(sql);
	        con.close(); 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
       
		
	}
	
	public boolean editarProd(Produto prod){
		boolean resp=false;
		try {
			Connection con = Conexao.getConexao();
			  String sql = "update produto set nome=?, descricao=?, preco=?, imagem=? where id=?";
				    PreparedStatement pstmt = con.prepareStatement(sql);			        
			        pstmt.setString(1, prod.getNome());
			        pstmt.setString(2,prod.getDescricao());
			        pstmt.setDouble(3,prod.getPreco());		       
			        pstmt.setBinaryStream(4, prod.getImagem().getStream()); 
			        pstmt.setInt(5,prod.getId());
			        pstmt.execute();
			        
			        resp=true;
			        con.close();
			     
			      } catch (Exception ex) {
			    	  System.out.println(ex.getMessage());
			      }
		return resp;
		
	}
	

}
