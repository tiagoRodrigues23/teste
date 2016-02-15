package modelo;

import java.io.Serializable;



public class Agenda implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String servico;
	private Cliente cliente=new Cliente();
	private Pet pet=new Pet();
	private java.sql.Date data;
	private String horaInicio;
	private java.util.Date dataInicio;
	private String horaFim;
	private String horario;
	private boolean ativo;
	private int idCliente;
	private String nomeCliente;

	
	
	
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	
	
	public int getId() {
		
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public Cliente getCliente() {
		
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	
	public java.util.Date getData() {
		return data;
	}
	public void setData(java.util.Date data) {
		this.data = new java.sql.Date(data.getTime());
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public java.util.Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(java.util.Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
	
	
	

}
