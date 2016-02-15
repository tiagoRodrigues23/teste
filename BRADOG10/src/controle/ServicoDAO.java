package controle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Servico;

public class ServicoDAO {
	
	
	public boolean salvar(Servico servico){
		boolean resp=false;
		try{
			Connection con=Conexao.getConexao();
			Statement stmt=con.createStatement();
			String sql="insert into servico values("+servico.getId()+",'"+servico.getNome()+"',"+servico.getPreco()+")";
			stmt.executeUpdate(sql);
			con.close();
			resp=true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		return resp;
	}
	
	public ArrayList<Servico> buscaServicos(){
		ArrayList<Servico> lista=new ArrayList<>();
		try{
			Connection con=Conexao.getConexao();
			Statement stmt=con.createStatement();
			String sql="select * from servico";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				Servico serv= new Servico();
				serv.setId(rs.getInt(1));
				serv.setNome(rs.getString(2));
				serv.setPreco(rs.getDouble(3));
				lista.add(serv);
			}
			con.close();
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			
		}
		return lista;
	}
	
	public void exluirServico(Servico servico){
		try{
			Connection con=Conexao.getConexao();
			Statement stmt=con.createStatement();
			String sql="delete from servico where id="+servico.getId();
			stmt.executeUpdate(sql);
			
			con.close();
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			
		}
	}
	
	public boolean alteraServico(Servico servico){
		boolean resp=false;
		try{
			Connection con=Conexao.getConexao();
			Statement stmt=con.createStatement();
			String sql="update servico set nome='"+servico.getNome()+"', preco="+servico.getPreco()+" where id="+servico.getId();
			stmt.executeUpdate(sql);
			resp=true;
			con.close();
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			
		}
		return resp;
	}
}
