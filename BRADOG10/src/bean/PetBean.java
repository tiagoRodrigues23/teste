package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import modelo.Cliente;
import modelo.Pet;
import controle.ClienteDAO;
import controle.PetDAO;

@ManagedBean(name= "petBean")
@ViewScoped


public class PetBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pet pet=new Pet();
	PetDAO dao= new PetDAO();
	private List<String> racas= new ArrayList<>();
	
	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
	public List<String> getRacas() {
		return racas;
	}

	public void setRacas(List<String> racas) {
		this.racas = racas;
	}

	public PetBean(){
		racas.add("Alano Espanhol");
		racas.add("Airedale Terrier"); 
		racas.add("American Staffordshire Terrier"); 
		racas.add("American Water Spaniel"); 
		racas.add("Cão de Pastor Inglês");
		racas.add("Basset Azul da Gasconha"); 
		racas.add("Basset Fulvo da Bretanha");
		racas.add("Basset Hound"); 
		racas.add("Beagle"); 
		racas.add("Bearded Collie"); 
		racas.add("Bichon Maltês"); 
		racas.add("Bobtail"); 
		racas.add("Border Collie"); 
		racas.add("Boston Terrier"); 
		racas.add("Boxer"); 
		racas.add("Bull Terrier"); 
		racas.add("Bullmastiff");
		racas.add("Bulldog"); 		
		racas.add("Cão de Montanha dos Pirinéus");
		racas.add("Caniche"); 
		racas.add("Chihuahua");
		racas.add("Cirneco do Etna");
		racas.add("Chow Chow"); 
		racas.add("Cocker Spaniel");
		racas.add("Dálmata"); 
		racas.add("Dobermann ");
		racas.add("Dogue Alemão");
		racas.add("Dogue Argentino");
		racas.add("Dogue Canário");
		racas.add("Fox Terrier"); 
		racas.add("Foxhound");
		racas.add("Galgo");
		racas.add("Golden Retriever"); 
		racas.add("Gos d'Atura"); 
		racas.add("Husky Siberiano");
		racas.add("Laika"); 
		racas.add("Labrador Retriever");	
		racas.add("Malamute-do-Alasca");
		racas.add("Mastin dos Pirenéus");
		racas.add("Mastin do Tibete");
		racas.add("Mastin Espanhol");
		racas.add("Mastín Napolitano");
		racas.add("Pastor Alemão");
		racas.add("Pastor Belga"); 
		racas.add("Pastor de Brie");
		racas.add("Pastor dos Pirenéus de Cara Rosa"); 
		racas.add("Pequinês");
		racas.add("Perdigueiro");
		racas.add("Pitbull"); 
		racas.add("Podengo");
		racas.add("Pointer"); 
		racas.add("Pug");
		racas.add("Rhodesian Ridgeback");
		racas.add("Rottweiler"); 
		racas.add("Rough Collie");
		racas.add("Sabueso");
		racas.add("Saluki");
		racas.add("Samoiedo"); 
		racas.add("São Bernardo"); 
		racas.add("Scottish Terrier"); 
		racas.add("Setter Irlandés"); 
		racas.add("Shar-Pei"); 
		racas.add("Shiba Inu"); 
		racas.add("Smooth Collie");
		racas.add("Staffordshire Bull Terrier");
		racas.add("Teckel");
		racas.add("Terra-nova"); 
		racas.add("Terrier");
		racas.add("Terrier Negro Russo");
		racas.add("Terrier Norfolk");
		racas.add("Terrier Norwich");
		racas.add("Terrier Tibetano");
		racas.add("Welhs Terrier");
		racas.add("West Highland T.");
		racas.add("Wolfspitz");
		racas.add("Yorkshire Terrier");
		
		Collections.sort(racas);


		ClienteDAO cliDao=new ClienteDAO();
	    FacesContext context=FacesContext.getCurrentInstance();
    	ExternalContext ext=context.getExternalContext();
    	String login=ext.getRemoteUser();
    	Cliente cli=cliDao.buscaPorLogin(login);
    	pet.setCliente(cli);
	}
	
	public void carregaRaca(){
		if(pet.getEspecie().equals("Canina")){
			racas.clear();
			racas.add("cão");
		}
		else if(pet.getEspecie().equals("Felina")){
			racas.clear();
			racas.add("gata");
		}else{
			racas.add("Outra");
		}
	}
	
	public String salvar() {
		
		
			dao.salvar(pet);
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO,"salvo com sucesso",""));
			PetDAO dao= new PetDAO();
			ExternalContext ext=context.getExternalContext();
			List<Pet> lista= dao.buscaPet();
			String login=ext.getRemoteUser();
		    ClienteDAO cliDao= new ClienteDAO();
			Cliente cli=cliDao.buscaPorLogin(login);
		    pet.setCliente(cli);
		   ConsultaPetBean.listPet.clear();
			
			for(Pet pet:lista){
				if(pet.getCliente().getLogin().equals(login)){
					ConsultaPetBean.listPet.add(pet);
				}
			}
			
			 
			return "/restrito/inicio.jsf";
	}
	
}
