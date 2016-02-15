package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import modelo.Funcionario;
import controle.FuncionarioDAO;
@ManagedBean(name="consultaFuncionarioBean")
@SessionScoped
public class ConsultaFuncionarioBean {
	 
	private Funcionario func=new Funcionario();
	FuncionarioDAO dao= new FuncionarioDAO();
	static List<Funcionario> list=  new ArrayList<>();
	private  List<Funcionario> funcionarios=dao.buscaFuncionario();
	
	public ConsultaFuncionarioBean() {
		list.clear();
		for(Funcionario func:funcionarios){
			list.add(func);
		}
	}

	public Funcionario getFunc() {
		return func;
	}

	public void setFunc(Funcionario func) {
		this.func = func;
	}

	

	public List<Funcionario> getList() {
		return list;
	}

	public void setList(List<Funcionario> list) {
		ConsultaFuncionarioBean.list = list;
	}

	public  List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public  void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	public String excluir() throws IOException{
		dao.excluirFuncionario(func);
		
		ConsultaFuncionarioBean.list.clear();
		FuncionarioDAO d=new FuncionarioDAO();
		ArrayList<Funcionario> list=d.buscaFuncionario();
		for(Funcionario func:list){
				ConsultaFuncionarioBean.list.add(func);
		}
		
		FacesContext con= FacesContext.getCurrentInstance();
		ExternalContext ext=con.getExternalContext();
		ext.redirect("/BRADOG10/admin/consultaFunc.jsf");
		return "sucesso";
	}
	
public String editarFuncionario() throws IOException {
	FacesContext con= FacesContext.getCurrentInstance();
	ExternalContext ext=con.getExternalContext();
	ext.redirect("/BRADOG10/admin/alteraFuncionario.jsf");

		return "sucesso";
	}
	
	public String editar() throws IOException {
		
		if(dao.editarFuncionario(func)){
			ConsultaFuncionarioBean.list.clear();
			FuncionarioDAO d=new FuncionarioDAO();
			ArrayList<Funcionario> list=d.buscaFuncionario();
			for(Funcionario func:list){
					ConsultaFuncionarioBean.list.add(func);
			}
			
			FacesContext con=FacesContext.getCurrentInstance();
			con.getExternalContext().getFlash().setKeepMessages(true);
			con.addMessage("erro",new FacesMessage(FacesMessage.SEVERITY_INFO,"Editado com Sucesso!",""));
			ExternalContext ext=con.getExternalContext();
			ext.redirect("/BRADOG10/admin/consultaFunc.jsf");

				
			
		}
		
		return "sucesso";
	}
	
}
