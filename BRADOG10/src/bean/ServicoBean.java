package bean;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import modelo.Servico;
import controle.ServicoDAO;
@ManagedBean


public class ServicoBean {
	private Servico servico=new Servico();
	private ServicoDAO dao=new ServicoDAO();

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public String salvar() throws IOException{
		if(dao.salvar(servico)){
			ConsultaServicoBean.servicos.clear();
			ServicoDAO d=new ServicoDAO();
			ArrayList<Servico> list=d.buscaServicos();
			for(Servico serv:list){
					ConsultaServicoBean.servicos.add(serv);
			}
			FacesContext con=FacesContext.getCurrentInstance();
			con.getExternalContext().getFlash().setKeepMessages(true);
			con.addMessage("info",new FacesMessage(FacesMessage.SEVERITY_INFO,"salvo com sucesso",""));
			ExternalContext ext=con.getExternalContext();
			ext.redirect("/BRADOG10/admin/inicio.jsf");
		}
		return"sucesso";
	}
	
	public String excluir(){
		return"sucesso";
	}
}
