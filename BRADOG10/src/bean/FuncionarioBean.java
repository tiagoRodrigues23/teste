package bean;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import modelo.Funcionario;
import modelo.Permissao;
import controle.FuncionarioDAO;
@ManagedBean
public class FuncionarioBean {
	private Funcionario funcionario=new Funcionario();
	private FuncionarioDAO dao=new FuncionarioDAO();
	private boolean ativo=true;
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String salvar() throws IOException{
		////salva os valores contrarios nos booleans
		
		if(dao.salvarFuncionario(funcionario)){
			
			ConsultaFuncionarioBean.list.clear();
			FuncionarioDAO d=new FuncionarioDAO();
			ArrayList<Funcionario> list=d.buscaFuncionario();
			for(Funcionario func:list){
					ConsultaFuncionarioBean.list.add(func);
			}
			
			FacesContext con=FacesContext.getCurrentInstance();
			con.getExternalContext().getFlash().setKeepMessages(true);
			con.addMessage("info",new FacesMessage(FacesMessage.SEVERITY_INFO,"salvo com sucesso",""));
			ExternalContext ext=con.getExternalContext();
			ext.redirect("/BRADOG10/admin/inicio.jsf");
		}
		return"SUCESSO";
	}
}
