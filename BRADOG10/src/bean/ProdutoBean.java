package bean;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Produto;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

import controle.ProdutoDAO;
@ManagedBean
@ViewScoped
public class ProdutoBean {
	Produto produto=new Produto();
    public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	
	public void fileUpload(FileUploadEvent event) throws IOException{
		FacesContext con=FacesContext.getCurrentInstance();
		con.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Imagem "+event.getFile().getFileName()+" Carregada com Sucesso!",""));
		produto.setImagem(new DefaultStreamedContent(event.getFile().getInputstream()));
		
	}
	public void salvar() throws IOException{
		ProdutoDAO dao=new ProdutoDAO();
		if(dao.salvar(produto)){
					
					FacesContext con=FacesContext.getCurrentInstance();
					con.getExternalContext().getFlash().setKeepMessages(true);
					con.addMessage("info",new FacesMessage(FacesMessage.SEVERITY_INFO,"salvo com sucesso",""));
					ConsultaProdutoBean.listProduto.clear();
					ProdutoDAO d=new ProdutoDAO();
					ArrayList<Produto> list=d.buscaProduto();
					ConsultaProdutoBean.listProduto.clear();
					for(Produto prod:list){
							ConsultaProdutoBean.listProduto.add(prod);
					}
					produto=new Produto();
					con.getExternalContext().redirect("/BRADOG10/admin/cadastro de produto.jsf");
					
			}
		
		}
}
