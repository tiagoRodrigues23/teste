package bean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import modelo.Cliente;
import modelo.Pet;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.model.StreamedContent;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;

import controle.ClienteDAO;
import controle.PetDAO;
import controle.RelatorioUtil;
@ManagedBean
@SessionScoped
public class ConsultaClienteBean {
	private Cliente cliente=new Cliente();
	private ClienteDAO dao=new ClienteDAO();
	private List<Cliente> clientes =dao.buscaClientes();
	static List<Cliente> list=new ArrayList<>();
	private List<Pet> listaPet=new ArrayList<>();
	private FacesContext con=FacesContext.getCurrentInstance();
	private ExternalContext ext=con.getExternalContext();
	private String cpf;
	private StreamedContent arquivoRetorno;
	private int tipoRelatorio;
	
	public ConsultaClienteBean() {
		ConsultaClienteBean.list.clear();
		for(Cliente cli:clientes){
			if(!cli.getLogin().equals("admin")&& !cli.getNome().equals(""))
				list.add(cli);
		}
	}
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<Cliente> getList() {
		return list;
	}
	public void setList(List<Cliente> list) {
		ConsultaClienteBean.list = list;
	}
	
	public  List<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public List<Pet> getListaPet() {
		return listaPet;
	}
	public void setListaPet(List<Pet> listaPet) {
		this.listaPet = listaPet;
	}
	public StreamedContent getArquivoRetorno() {
		
		String nomeRelatorioJasper= "relatorio_clientes";
		String nomeRelatorioSaida= nomeRelatorioJasper;
		RelatorioUtil util=new RelatorioUtil();
		try{
			this.arquivoRetorno = util.geraRelatorio(new HashMap<String, Object>(),nomeRelatorioJasper, nomeRelatorioSaida, tipoRelatorio);
		}catch(Exception ex){
			
		}
		return arquivoRetorno;
	}
	public void setArquivoRetorno(StreamedContent arquivoRetorno) {
		this.arquivoRetorno = arquivoRetorno;
	}
	public int getTipoRelatorio() {
		return tipoRelatorio;
	}
	public void setTipoRelatorio(int tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}
	public void buscaPorCPF() throws IOException{
		ClienteDAO dao=new ClienteDAO();
		FacesContext con=FacesContext.getCurrentInstance();
		if(!cpf.equals("")){
			Cliente cli=dao.buscaPorCPF(cpf);		
			if(cli!=null){
				ConsultaClienteBean.list.clear();
				ConsultaClienteBean.list.add(cli);			
				ext.redirect("/BRADOG10/admin/consultaUsuarios.jsf");
			}else{
				ext.getFlash().setKeepMessages(true);
				con.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,"CPF não encontrado",""));
				ConsultaClienteBean.list.clear();
				ArrayList<Cliente> lista=dao.buscaClientes();
				for(Cliente c:lista){
					if(!c.getLogin().equals("admin")&& !c.getNome().equals(""))
						ConsultaClienteBean.list.add(c);
				}
				ext.redirect("/BRADOG10/admin/consultaUsuarios.jsf");
				
			}
		}else{
			ConsultaClienteBean.list.clear();
			ArrayList<Cliente> lista=dao.buscaClientes();
			for(Cliente cli:lista){
				if(!cli.getLogin().equals("admin")&& !cli.getNome().equals(""))
					ConsultaClienteBean.list.add(cli);
			}
			ext.redirect("/BRADOG10/admin/consultaUsuarios.jsf");
			
		}
		cpf="";
	}
	
	public String editarCliente() throws IOException{
		ext.redirect("/BRADOG10/admin/alteraUsuarios.jsf");
		return "sucesso";
	}
	
	public String editar() throws IOException{
		if(dao.editarCliente(cliente)){
			FacesContext con=FacesContext.getCurrentInstance();
			ExternalContext ext=con.getExternalContext();
			con.addMessage("info", new FacesMessage(FacesMessage.SEVERITY_INFO,"editado com sucesso",""));
			ext.getFlash().setKeepMessages(true);
			ext.redirect("/BRADOG10/admin/consultaUsuarios.jsf");
			cliente = new Cliente();	
		}
		
		return "sucesso";
	}
	
	public String excluir() throws IOException{
		dao.excluiCliente(cliente);
		ConsultaClienteBean.list.clear();
		ClienteDAO d=new ClienteDAO();
		ArrayList<Cliente> lista=d.buscaClientes();
		for(Cliente cli:lista){
			if(!cli.getLogin().equals("admin")&& !cli.getNome().equals(""))
				ConsultaClienteBean.list.add(cli);
		}
		ext.redirect("/BRADOG10/admin/consultaUsuarios.jsf");
		return "sucesso";
	}
	
	public String salvar() throws IOException{
		cliente.setAtivo(true);		
		ClienteDAO dao= new ClienteDAO();
		if(dao.salvar(cliente)){
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("sucesso", new FacesMessage(FacesMessage.SEVERITY_INFO,"salvo com sucesso",""));
			context.getExternalContext().getFlash().setKeepMessages(true);
			ConsultaClienteBean.list.clear();
			ArrayList<Cliente> lista=dao.buscaClientes();
			for(Cliente cli:lista){
				if(!cli.getLogin().equals("admin")&& !cli.getNome().equals(""))
					ConsultaClienteBean.list.add(cli);
			}
			ExternalContext ext=context.getExternalContext();
			ext.redirect("/BRADOG10/admin/inicio.jsf");
			
		}	
		cliente = new Cliente();	
		return "sucesso";
			
	}
	
	public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
         
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
             
            cell.setCellStyle(cellStyle);
        }
    }
     
    public void preProcessPDF(Object document){
	       try{
		    	Document pdf = (Document) document;
		        pdf.open();
		        pdf.setPageSize(PageSize.ARCH_A);
		        pdf.addCreationDate();
		        
		        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		        String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "imagens" + File.separator + "logo.png";
		        String logo2 = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "imagens" + File.separator + "teste.png";
		        	         
		        pdf.add(Image.getInstance(logo));
		        pdf.setMargins(1, 1, 1, 1);
		        pdf.add(Image.getInstance(logo2));
		        
		        
	       }catch(Exception ex){
	    	   System.out.println(ex.getMessage());
	       }
	    }
    public void buscaPet() throws IOException{
    	PetDAO pdao=new PetDAO();
		ArrayList<Pet> lista=new ArrayList<>();
		lista=pdao.buscaPet();
		listaPet.clear();
		for(Pet pet:lista){
			if(pet.getCliente().getLogin().equals(cliente.getLogin())){
				listaPet.add(pet);
			}
		}
		ext.redirect("/BRADOG10/admin/consultaPet.jsf");
    }
	
}
