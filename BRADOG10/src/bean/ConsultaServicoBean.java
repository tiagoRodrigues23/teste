package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import modelo.Servico;
import controle.ServicoDAO;
@ManagedBean
@SessionScoped

public class ConsultaServicoBean {
	private Servico servico=new Servico();
	ServicoDAO dao= new ServicoDAO();
	private List<Servico> list=dao.buscaServicos();
	 static List<Servico> servicos=new ArrayList<>();
	
	public ConsultaServicoBean() {
		servicos.clear();
		for(Servico serv:list){
			servicos.add(serv);
		}
	}
	public  List<Servico> getList() {
		return list;
	}

	public  void setList(List<Servico> list) {
		this.list = list;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	

	

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		ConsultaServicoBean.servicos = servicos;
	}

	

	
	public String excluir() throws IOException{
		dao.exluirServico(servico);
		
		ConsultaServicoBean.servicos.clear();
		ServicoDAO d=new ServicoDAO();
		ArrayList<Servico> list=d.buscaServicos();
		for(Servico serv:list){
			ConsultaServicoBean.servicos.add(serv);
		}
		
		FacesContext con= FacesContext.getCurrentInstance();
		ExternalContext ext=con.getExternalContext();
		ext.redirect("/BRADOG10/admin/consultaServico.jsf");
		return "sucesso";
	}
	
public String editarServico() throws IOException {
	FacesContext con= FacesContext.getCurrentInstance();
	ExternalContext ext=con.getExternalContext();
	ext.redirect("/BRADOG10/admin/alteraServico.jsf");

		return "sucesso";
	}
	
	public String editar() throws IOException {
		
		if(dao.alteraServico(servico)){
			ConsultaServicoBean.servicos.clear();
			ServicoDAO d=new ServicoDAO();
			ArrayList<Servico> list=d.buscaServicos();
			for(Servico serv:list){
					ConsultaServicoBean.servicos.add(serv);
			}
			
			FacesContext con=FacesContext.getCurrentInstance();
			con.getExternalContext().getFlash().setKeepMessages(true);
			con.addMessage("erro",new FacesMessage(FacesMessage.SEVERITY_INFO,"Editado com Sucesso!",""));
			ExternalContext ext=con.getExternalContext();
			ext.redirect("/BRADOG10/admin/consultaServico.jsf");

				
			
		}
		
		return "sucesso";
	}
	

}
