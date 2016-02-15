package bean;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import modelo.Produto;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import controle.ProdutoDAO;
	@ManagedBean
	@SessionScoped
	public class ConsultaProdutoBean {

		static List<Produto> listProduto= new ArrayList<>();
		private Produto prod=new Produto();
		private StreamedContent imagem;
		ProdutoDAO dao= new ProdutoDAO();
		private List<Produto> list= dao.buscaProduto();
		
		public ConsultaProdutoBean() {
			listProduto.clear();
			for(Produto prod:list){
				listProduto.add(prod);
				
			}
		}
		
		
		
	    public Produto getProd() {
			return prod;
		}


		public void setProd(Produto prod) {
			this.prod = prod;
		}


		public static void setListProduto(List<Produto> listProduto) {
			ConsultaProdutoBean.listProduto = listProduto;
		}

		public List<Produto> getListProduto() {
			return listProduto;
		}

		
		
		
		
		
		public List<Produto> getList() {
			return list;
		}

		public void setList(List<Produto> list) {
			this.list = list;
		}
		
		public void fileUpload(FileUploadEvent event) throws IOException{
			FacesContext con=FacesContext.getCurrentInstance();
			con.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Imagem "+event.getFile().getFileName()+" Carregada com Sucesso!",""));
			prod.setImagem(new DefaultStreamedContent(event.getFile().getInputstream()));
			System.out.println(event.getFile().getFileName());
			
		}

		
		public StreamedContent getImagem() {
			//String s=(String)event.getComponent().getAttributes().get("teste");
			//System.out.println(s);
			Produto p=new Produto();
			FacesContext context=FacesContext.getCurrentInstance();
			if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
	            return new DefaultStreamedContent();
	        }
	        else {
				String id=context.getExternalContext().getRequestParameterMap().get("id");
				ProdutoDAO dao=new ProdutoDAO();
				if(id!=null){
					p=dao.buscaProdutoPorId(Integer.parseInt(id));
				}
				imagem=p.getImagem();
				return imagem;
	        }
		}



		public void setImagem(StreamedContent imagem) {
			this.imagem = imagem;
		}



		public String excluir() throws IOException{
			dao.excluirProduto(prod);
			
			ConsultaProdutoBean.listProduto.clear();
			ProdutoDAO d=new ProdutoDAO();
			ArrayList<Produto> list=d.buscaProduto();
			for(Produto prod:list){
					ConsultaProdutoBean.listProduto.add(prod);
			}
			
			FacesContext con= FacesContext.getCurrentInstance();
			ExternalContext ext=con.getExternalContext();
			ext.redirect("/BRADOG10/admin/consultaProduto.jsf");
			return "/admin/consultaProduto.jsf";
		}
		
		public String editarProduto() {
			
			return "/admin/editarProduto.jsf";
		}
		
		public String editar() throws InterruptedException {
			
			if(dao.editarProd(prod)){
				
				FacesContext con=FacesContext.getCurrentInstance();
				con.getExternalContext().getFlash().setKeepMessages(true);
				con.addMessage("erro",new FacesMessage(FacesMessage.SEVERITY_INFO,"Editado com Sucesso!",""));
				
			}
			
			return "/admin/consultaProduto.jsf";
		}
		
		

		
		
		
		
	    

	}



