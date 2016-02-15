package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import modelo.Cliente;
import modelo.Pet;
import controle.ClienteDAO;
import controle.PetDAO;
@ManagedBean
@SessionScoped



public class ConsultaPetBean {
	//PetDAO petDao= new PetDAO();
	static List<Pet> listPet= new ArrayList<>();
    private Pet pet=new Pet();
	PetDAO dao= new PetDAO();
	private List<Pet> list= dao.buscaPet();
	ClienteDAO cliDao=new ClienteDAO();
    FacesContext context=FacesContext.getCurrentInstance();
	ExternalContext ext=context.getExternalContext();
	
	public List<Pet> getListPet() {
		return listPet;
	}

	public void setListPet(List<Pet> listPet) {
		ConsultaPetBean.listPet = listPet;
	}
	
	
	public Pet getPet() {
		
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public ConsultaPetBean(){		
		listPet.clear();
	    String login=ext.getRemoteUser();
	    Cliente cli=cliDao.buscaPorLogin(login);
	    pet.setCliente(cli);		
		listPet.clear();
		for(Pet pet:list){
			if(pet.getCliente().getLogin().equals(login)){
				listPet.add(pet);
			}
		}
	}
	public String excluir() throws IOException{
		dao.excluirPet(this.pet);
		String login=ext.getRemoteUser();
	    Cliente cli=cliDao.buscaPorLogin(login);
	    pet.setCliente(cli);		
		ConsultaPetBean.listPet.clear();
		PetDAO d=new PetDAO();
		ArrayList<Pet> list=d.buscaPet();
		for(Pet pet:list){
			if(pet.getCliente().getLogin().equals(login)){
				ConsultaPetBean.listPet.add(pet);
			}
		}
		
		ext.redirect("/BRADOG10/restrito/consultaPet.jsf");
		
		return "/restrito/consultaPet";
	}
	
	public String editarPet() {
		
		return "/restrito/alteraPet";
	}
	
	public String editar() throws InterruptedException {
		
		if(dao.editarPet(pet)){
			
			FacesContext con=FacesContext.getCurrentInstance();
			con.getExternalContext().getFlash().setKeepMessages(true);
			con.addMessage("erro",new FacesMessage(FacesMessage.SEVERITY_INFO,"Editado com Sucesso!",""));
			
		}
		
		return "/restrito/consultaPet";
	}
	
	

	
	
	
	
    

}
