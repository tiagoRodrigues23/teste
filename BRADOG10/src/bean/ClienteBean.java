package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import modelo.Cliente;
import controle.ClienteDAO;
@ManagedBean(name= "clienteBean")
@RequestScoped

public class ClienteBean {
	
	public Cliente cliente = new Cliente();
	private List<String> ufs=new ArrayList<>();
	
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<String> getUfs() {
		return ufs;
	}

	public void setUfs(List<String> ufs) {
		this.ufs = ufs;
	}

	public ClienteBean(){
		
		ClienteDAO cliDao=new ClienteDAO();
	    FacesContext context=FacesContext.getCurrentInstance();
    	ExternalContext ext=context.getExternalContext();
    	String login=ext.getRemoteUser();
    	if(login!="")
    	 cliente=cliDao.buscaPorLogin(login);
		
		ufs.add("AC");
		ufs.add("AM");
		ufs.add("RR");
		ufs.add("PA");
		ufs.add("AP");
		ufs.add("TO");
		ufs.add("MA");
		ufs.add("PI");
		ufs.add("CE");
		ufs.add("RN");
		ufs.add("PB");
		ufs.add("PE");
		ufs.add("AL");
		ufs.add("SE");
		ufs.add("BA");
		ufs.add("MG");
		ufs.add("ES");
		ufs.add("RJ");
		ufs.add("SP");
		ufs.add("PR");
		ufs.add("SC");
		ufs.add("RS");
		ufs.add("MS");
		ufs.add("MT");
		ufs.add("GO");
		ufs.add("DF");
		ufs.add("RO");
		Collections.sort(ufs);
		}
	
	
	public String salvar() throws IOException {
		
		
		cliente.setAtivo(true);
		
		
		ClienteDAO dao= new ClienteDAO();
			if(dao.salvar(cliente)){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO,"salvo com sucesso",""));
			
			ExternalContext ext=context.getExternalContext();
			ext.redirect("/BRADOG10/publico/login.jsf");
			context.getExternalContext().getFlash().setKeepMessages(true);
			}
			
			return "sucesso";
	}
	
	public String editar() throws InterruptedException {
		ClienteDAO dao=new ClienteDAO();
		if(dao.editarCliente(cliente)){
			FacesContext con=FacesContext.getCurrentInstance();
			con.getExternalContext().getFlash().setKeepMessages(true);
			con.addMessage("erro",new FacesMessage(FacesMessage.SEVERITY_INFO,"Editado com Sucesso!",""));
			
		}
		//Thread.sleep(2000);  
		return "/restrito/inicio.jsf";
	}
	public String login() throws IOException{
	
		ClienteDAO dao= new ClienteDAO();
			if(dao.autenticar(cliente)){
				FacesContext.getCurrentInstance().getExternalContext().redirect("menu.jsf");
			}else{
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_WARN,"Nome e ou Senha Inválidos",""));
			}
			return "sucesso";
	}

}
