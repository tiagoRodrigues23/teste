package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import modelo.Funcionario;
import modelo.Permissao;

public class FuncionarioDAO {
	private Funcionario funcionario=new Funcionario();

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public boolean salvarFuncionario(Funcionario func){
		boolean resp=false;
		try{
		Connection con=Conexao.getConexao();
		String s="select * from cliente where login='"+func.getLogin()+"'";
		Statement stm=con.createStatement();
		ResultSet r=stm.executeQuery(s);
		if(r.next()){
			FacesContext context=FacesContext.getCurrentInstance();
			context.addMessage("erro",new FacesMessage(FacesMessage.SEVERITY_ERROR,"O Login já Existe",""));
			
		}else{
		//////////
		String sql="insert into cliente values(?,?,?,?,?,?,?,?,?,?,?,?,md5(?),?,?,?)";
		PreparedStatement pstmt =con.prepareStatement(sql);
		pstmt.setInt(1, func.getId());
		pstmt.setInt(2,0);
		pstmt.setString(3,"");
		pstmt.setString(4,"");
		pstmt.setString(5,"");
		pstmt.setString(6,"");
		pstmt.setString(7,"");
		pstmt.setString(8,"");
		pstmt.setString(9,"");
		pstmt.setString(10,"");
		
		pstmt.setString(11,"");
		
		
		pstmt.setString(12,func.getLogin());
		pstmt.setString(13,func.getSenha());
		pstmt.setString(14,"");
		pstmt.setDate(15,new java.sql.Date(new java.util.Date().getTime()));
		pstmt.setBoolean(16,true);
		pstmt.execute();
			String sql2="select * from cliente where login='"+func.getLogin()+"'";
			System.out.println("login="+func.getLogin());
			Statement stmt =con.createStatement();
			ResultSet rs=stmt.executeQuery(sql2);
			if(rs.next()){
				int id=rs.getInt(1);
				String sql3="insert into permissao values("+func.getPermissao().getId()+","+func.getPermissao().isAgenda()
					+","+func.getPermissao().isDados()+","+func.getPermissao().isUsuario()+","+id+","+func.getPermissao().isRelatorio()+","+func.getPermissao().isComanda()+")";
				Statement st=con.createStatement();
				st.execute(sql3);
				String sq="insert into usuario_permissao values("+id+",'ROLE_ADMINISTRADOR')";
				Statement stm1=con.createStatement();
				stm1.execute(sq);
			}
			resp=true;
		}
		con.close();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return resp;
	}
	
	public Funcionario buscaPorLogin(String login){
		Funcionario func = new Funcionario();
		try{
			String sql="select * from cliente where login='"+login+"'";
			Connection con=Conexao.getConexao();
			Statement stm=con.createStatement();
			ResultSet rs =stm.executeQuery(sql);
			if(rs.next()){
				
				func.setId(rs.getInt(1));
				func.setLogin(rs.getString(12));
				func.setSenha(rs.getString(13));
				func.setNome(rs.getString(3));
				
				String sql2="select * from permissao where id_func="+func.getId();
				Statement stmt=con.createStatement();
				ResultSet rs2 =stmt.executeQuery(sql2);
				Permissao p=new Permissao();
				if(rs2.next()){
					p.setId(rs2.getInt(1));
					p.setAgenda(rs2.getBoolean(2));
					p.setDados(rs2.getBoolean(3));
					p.setUsuario(rs2.getBoolean(4));
					p.setRelatorio(rs2.getBoolean(6));
					p.setComanda(rs2.getBoolean(7));
				}
				func.setPermissao(p);
			}
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return func;
	}
	
	public ArrayList<Funcionario> buscaFuncionario(){
		ArrayList<Funcionario> lista= new ArrayList<>();
		try{
			String sql="select * from cliente where nome=''";
			Connection con=Conexao.getConexao();
			Statement stm=con.createStatement();
			ResultSet rs =stm.executeQuery(sql);
			while(rs.next()){
				Funcionario func=new Funcionario();
				func.setId(rs.getInt(1));
				func.setLogin(rs.getString(12));
				func.setSenha(rs.getString(13));
				
				String sql2="select * from permissao where id_func="+func.getId();
				Statement stmt=con.createStatement();
				ResultSet rs2 =stmt.executeQuery(sql2);
				Permissao p=new Permissao();
				if(rs2.next()){
					p.setId(rs2.getInt(1));
					p.setAgenda(rs2.getBoolean(2));
					p.setDados(rs2.getBoolean(3));
					p.setUsuario(rs2.getBoolean(4));
					p.setRelatorio(rs2.getBoolean(6));
					p.setComanda(rs2.getBoolean(7));
				}
				func.setPermissao(p);
				lista.add(func);
			}
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return lista;
	}
	
public void excluirFuncionario(Funcionario func){		
		
		try {
			Connection con = Conexao.getConexao();
			
			Statement stmt = con.createStatement( );
			String sql2 = "delete FROM cliente where id="+func.getId();
	        stmt.executeUpdate(sql2);
	        con.close(); 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
       
		
	}

public boolean editarFuncionario(Funcionario func){
	boolean resp=false;
	try {
		Connection con = Conexao.getConexao();
		Statement stmt = con.createStatement( );
		String sql = "update cliente set login='"+func.getLogin()+"', senha=md5('"+func.getSenha()+"') where id="+func.getId();
        stmt.executeUpdate(sql);
        String sql3="update permissao set agenda="+func.getPermissao().isAgenda()
				+",dados="+func.getPermissao().isDados()+",usuario="+func.getPermissao().isUsuario()+
				",relatorio="+func.getPermissao().isRelatorio()+", comanda="+func.getPermissao().isComanda()+" where id_func="+func.getId();
		Statement st=con.createStatement();
		st.execute(sql3);
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
