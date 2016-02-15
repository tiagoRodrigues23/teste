package bean;

import java.net.URL;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import modelo.Cliente;

import org.apache.commons.mail.HtmlEmail;

import controle.ClienteDAO;



@ManagedBean
public class RecuperaSenhaBean {
	private Cliente cliente=new Cliente();
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String enviar(){
		// Cria o e-mail 
		ClienteDAO dao=new ClienteDAO();
		Cliente cli=dao.buscaPorCPF(cliente.getCpf());
		if(cli.getCpf()!=null){
			String senha=gerarSenha();
			System.out.println(senha);
			cli.setSenha(senha);
			dao.editarCliente(cli);
			if(cliente.geteMail().equals(cli.geteMail())){
				HtmlEmail email = new HtmlEmail(); 
				email.setHostName("smtp.gmail.com"); 
				try{
					email.setFrom("bradogpet@gmail.com", "BRADOG");
					email.setAuthentication("bradogpet@gmail.com", "bradog12345");
					email.addTo(cli.geteMail(), cli.getNome());			 
					email.setSubject("Recupera��o de Senha"); 
					// adiciona uma imagem ao corpo da mensagem e retorna seu id 
					
					URL url = new URL("http://www.irmaostoku.com.br/bradog/images/logo.png");
					String cid = email.embed(url, "Bradog logo"); 
					// configura a mensagem para o formato HTML
					
					email.setHtmlMsg("<html> <img src=\"cid:"+cid+"\"><br>Ol� "+cli.getNome()+
							"</br> <br>Seu login �: "+cli.getLogin()+"</br><br> Sua nova Senha �: "+
							cli.getSenha()+"</br><br>Recomendamos que voc� fa�a login</br><br>" +
									" e altere sua senha atrav�s do menu usu�rio/editar</html>");  
					
					// configura uma mensagem alternativa caso o servidor n�o suporte HTML
					email.setTextMsg("Ol� "+cli.getNome()+", seu login �: "+cli.getLogin()+", sua nova senha �: "+cli.getSenha());   
					// envia o e-mail
					email.setSmtpPort(587);
					email.setSSL(true);
				
					email.send();
					FacesContext con=FacesContext.getCurrentInstance();
					con.addMessage("info",new FacesMessage(FacesMessage.SEVERITY_INFO,"Voc� recebeu um e-mail contendo sua nova senha!",""));
				
					
				}catch(Exception ex){
					System.out.println(ex.getMessage());
					FacesContext con=FacesContext.getCurrentInstance();
					con.addMessage("info",new FacesMessage(FacesMessage.SEVERITY_WARN,"erro ao enviar o e-mail!",""));
				
				}
			}else{
				FacesContext con=FacesContext.getCurrentInstance();
				con.addMessage("info",new FacesMessage(FacesMessage.SEVERITY_WARN,"O e-mail informado n�o corresponde ao e-mail cadastrado!",""));
			
			}
		}else{
			FacesContext con=FacesContext.getCurrentInstance();
			con.addMessage("info",new FacesMessage(FacesMessage.SEVERITY_WARN,"CPF n�o cadastrado!",""));
		
		}

		return "sucesso";
	}
	
	public String gerarSenha(){
		String senha="";
		Random r=new Random();
		String []letras = new String[10];
		letras[0]="A";
		letras[1]="B";
		letras[2]="C";
		letras[3]="D";
		letras[4]="E";
		letras[5]="F";
		letras[6]="G";
		letras[7]="H";
		letras[8]="I";
		letras[9]="J";
		for(int i=1;i<letras.length;i++){
			senha+=letras[r.nextInt(i)]+i;
		}
		return senha;
	}
}
