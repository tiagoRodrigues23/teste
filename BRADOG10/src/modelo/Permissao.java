package modelo;

public class Permissao {
	private int id;
	private boolean agenda;
	private boolean usuario;
	private boolean dados;
	private boolean relatorio;
	private boolean comanda;
	public boolean isAgenda() {
		return agenda;
	}
	public void setAgenda(boolean agenda) {
		this.agenda = agenda;
	}
	public boolean isUsuario() {
		return usuario;
	}
	public void setUsuario(boolean usuario) {
		this.usuario = usuario;
	}
	public boolean isDados() {
		return dados;
	}
	public void setDados(boolean dados) {
		this.dados = dados;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isRelatorio() {
		return relatorio;
	}
	public void setRelatorio(boolean relatorio) {
		this.relatorio = relatorio;
	}
	public boolean isComanda() {
		return comanda;
	}
	public void setComanda(boolean comanda) {
		this.comanda = comanda;
	}


}
