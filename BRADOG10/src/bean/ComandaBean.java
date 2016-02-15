package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;

import modelo.Agenda;
import modelo.Comanda;

import org.primefaces.model.StreamedContent;
import org.springframework.web.bind.annotation.SessionAttributes;

import controle.AgendaDAO;
@ManagedBean
@SessionAttributes
public class ComandaBean {
	private AgendaDAO agDao =new AgendaDAO();
	private List<Comanda> comandas=new ArrayList<>();
	private Date data=new Date();
	private String cpf;
	private StreamedContent relatorio;
	private double total;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;	}

	public List<Comanda> getComandas() {
		return comandas;
	}
	public void setComandas(List<Comanda> comandas) {
		this.comandas = comandas;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public StreamedContent getRelatorio() {
				return relatorio;
			
	}
	public void setRelatorio(StreamedContent relatorio) {
		this.relatorio = relatorio;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void gerarComandas() throws IOException{
		ArrayList<Agenda> listAg= agDao.buscaAgenda();
			total=0;
			comandas.clear();
			String servico="";
			double valores=0;
			for(Agenda age:listAg){
				if(age.getData().compareTo(data)==0){
					if(!cpf.equals("")){
						if(age.getCliente().getCpf().equals(cpf)){
							comandas.clear();
							Comanda com=new Comanda();
							String dados[]=age.getServico().split(",");
							String preco=dados[1].substring(3);
							double valor=Double.parseDouble(preco);
							valores+=valor;
							total+=valor;
							com.setCliente(age.getCliente().getNome());
							servico=servico+" "+dados[0]+". ";
							com.setServico(servico);
							com.setData(age.getData());
							com.setValor(valores);
							comandas.add(com);
						}
					}else{
						
						Comanda com=new Comanda();
						String dados[]=age.getServico().split(",");
						String preco=dados[1].substring(3);
						double valor=Double.parseDouble(preco);
						com.setValor(valor);
						total+=valor;
						com.setCliente(age.getCliente().getNome());
						com.setServico(dados[0]);
						com.setData(age.getData());
						comandas.add(com);
					}
				}
			}
			
		}
			
	
}
	

