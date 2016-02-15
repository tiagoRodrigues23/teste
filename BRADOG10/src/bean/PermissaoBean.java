package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import modelo.Funcionario;
import modelo.Permissao;
import controle.FuncionarioDAO;
@ManagedBean
public class PermissaoBean {
	private Funcionario funcionario=new Funcionario();
	private FuncionarioDAO dao=new FuncionarioDAO();
	
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public PermissaoBean() {
		FacesContext context=FacesContext.getCurrentInstance();
    	ExternalContext ext=context.getExternalContext();
    	String login=ext.getRemoteUser();
    	
    		funcionario=dao.buscaPorLogin(login);
    		///////////os valores são trocados porque a entrada precisa estar false para habilitar a
  	    	//////////funcionalidade, mas ela vem como true na hora do cadastro
  	    
    		boolean agenda, dados, usuario,relatorio,comanda;
    		agenda=!funcionario.getPermissao().isAgenda();
    		dados=!funcionario.getPermissao().isDados();
    		usuario=!funcionario.getPermissao().isUsuario();
    		relatorio=!funcionario.getPermissao().isRelatorio();
    		comanda=!funcionario.getPermissao().isComanda();
    		Permissao p=new Permissao();
    		p.setAgenda(agenda);
    		p.setDados(dados);
    		p.setUsuario(usuario);
    		p.setRelatorio(relatorio);
    		p.setComanda(comanda);
    		funcionario.setPermissao(p);
    	
	}
}
