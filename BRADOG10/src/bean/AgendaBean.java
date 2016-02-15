package bean;


	import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import modelo.Agenda;
import modelo.Cliente;
import modelo.Pet;
import modelo.Servico;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.StreamedContent;

import controle.AgendaDAO;
import controle.ClienteDAO;
import controle.HoraDAO;
import controle.PetDAO;
import controle.RelatorioUtil;
import controle.ServicoDAO;
	@ManagedBean(name="agendaBean")
	@ViewScoped


	public class AgendaBean implements Serializable {  

		private static final long serialVersionUID = 1L;


		private ScheduleModel eventModel;
		
		private Date dataAtual=new Date();
	     
	      
	    private DefaultScheduleEvent event = new DefaultScheduleEvent();  
	    ArrayList<DefaultScheduleEvent> eventos = new ArrayList<>();
	
	    
	    private Agenda agenda=new Agenda();
	    PetDAO petDao= new PetDAO();
	    private ArrayList<Pet> list= petDao.buscaPet();
	    HoraDAO horaDao=new HoraDAO();
	    private List<String> listaHorario= new ArrayList<>();
	    private List<String> horarioList= new ArrayList<>();
	    private List<String> listaPet= new ArrayList<>();
	    private List<String> servicos= new ArrayList<>();
	    private AgendaDAO ageDao= new AgendaDAO();
		private List<Agenda> listaAgendas=ageDao.buscaAgenda();
		private int tipoRelatorio;
		private StreamedContent arquivoRetorno;
		private StreamedContent arquivoRetorno2;
		private StreamedContent arquivoRetornoCliente;
		private StreamedContent arquivoRetornoData;
		private String cpf;
		private String pet;
		private List<Agenda> historicoAgendas= new ArrayList<>();
		private List<Agenda> listaAgendasCliente= new ArrayList<>();
		private java.util.Date data1;
		private java.util.Date data2;
		
		
	    Calendar t1 = Calendar.getInstance();
	    Calendar t2 = Calendar.getInstance();
	    AgendaDAO dao=new AgendaDAO();
	   
	    
		public AgendaBean() {
			//inicializar o evento
			ServicoDAO servdao=new ServicoDAO();
			ArrayList<Servico> lista=servdao.buscaServicos();
			for(Servico serv:lista){
				servicos.add(serv.getNome()+", R$"+serv.getPreco());
			}
			horarioList.add("08:00:00");
			horarioList.add("09:00:00");
			horarioList.add("10:00:00");
			horarioList.add("11:00:00");
			horarioList.add("12:00:00");
			horarioList.add("13:00:00");
			horarioList.add("14:00:00");
			horarioList.add("15:00:00");
			horarioList.add("16:00:00");
			horarioList.add("17:00:00");
			
			ClienteDAO cliDao=new ClienteDAO();
		    FacesContext context=FacesContext.getCurrentInstance();
	    	ExternalContext ext=context.getExternalContext();
	    	String login=ext.getRemoteUser();
	    	Cliente cli=cliDao.buscaPorLogin(login);
			
			for(Pet pet:list){
				if(pet.getCliente().getLogin().equals(login)){
					listaPet.add(pet.getNome());
				}
		    }
				    	
	    	agenda.setCliente(cli);
			AgendaDAO dao= new AgendaDAO();
			ArrayList<Agenda> agendas=dao.buscaAgenda();
			
	        eventModel = new DefaultScheduleModel();
	        for(Agenda ag: agendas){
	        	DefaultScheduleEvent evento = new DefaultScheduleEvent();
	        	if(ag.getCliente().getLogin().equals(login)){
	        		evento.setTitle(ag.getCliente().getNome()+", "+ag.getServico()+", "+ag.getPet().getNome());
	        		
	        		String horas[]=ag.getHoraInicio().split(":");
	        		int ho=Integer.parseInt(horas[0]);
	        		String horas2[]=ag.getHoraFim().split(":");
	        		int ho2=Integer.parseInt(horas2[0]);
		        	
		        	Calendar calendar = Calendar.getInstance();
		        	calendar.setTime(ag.getData());
		        	calendar.set(Calendar.HOUR_OF_DAY,ho);
		        	
		        	Calendar calendar2 = Calendar.getInstance();
		        	calendar2.setTime(ag.getData());
		        	calendar2.set(Calendar.HOUR_OF_DAY,ho2);
		        	
	        		evento.setStartDate(calendar.getTime());
	        		evento.setEndDate(calendar2.getTime());
	        		//evento.setStyleClass("myclass");
	        		evento.setData(ag.getId()+","+ag.getHoraInicio()+","+ag.getHoraFim()+","+ag.isAtivo());
	        		if(ag.isAtivo()){
	        			evento.setStyleClass("event1");
	        			eventModel.addEvent(evento);
	        		}else{
	        			evento.setStyleClass("myclass");
	        			eventModel.addEvent(evento);
	        		}
	        	}
	        	
	        } 
	       // eventModel.addEvent(new DefaultScheduleEvent("teste",new Date(),new Date(),"background-color: yellow"));
	    } 
		public Date getDataAtual() {
			return dataAtual;
		}

		public void setDataAtual(Date dataAtual) {
			this.dataAtual = dataAtual;
		}
		
		public List<String> getListaHorario() {
			return listaHorario;
		}
		public void setListaHorario(List<String> listaHorario) {
			this.listaHorario = listaHorario;
		}

		
	    public Agenda getAgenda() {
			return agenda;
			
		}

		public List<String> getListaPet() {
			return listaPet;
		}

		public void setListaPet(List<String> listaPet) {
			this.listaPet = listaPet;
		}

		public void setAgenda(Agenda agenda) {
			this.agenda = agenda;
		}

 
	      
	    
	     
	      
	    public List<String> getServicos() {
			return servicos;
		}

		public void setServicos(List<String> servicos) {
			this.servicos = servicos;
		}

		public ScheduleModel getEventModel() {  
	        return eventModel;  
	    }  
	      
	   
	      
	  
	      
	    public DefaultScheduleEvent getEvent() {  
	        return event;  
	    }  
	  
	    public List<String> getHorarioList() {
			return horarioList;
		}
		public void setHorarioList(List<String> horarioList) {
			this.horarioList = horarioList;
		}
		public void setEvent(DefaultScheduleEvent event) {  
	        this.event = event;  
	    }  
	    
	     
	   
		public List<Agenda> getListaAgendas() {
			return listaAgendas;
		}
		public void setListaAgendas(List<Agenda> listaAgendas) {
			this.listaAgendas = listaAgendas;
		}
		public int getTipoRelatorio() {
			return tipoRelatorio;
		}
		public void setTipoRelatorio(int tipoRelatorio) {
			this.tipoRelatorio = tipoRelatorio;
		}
		public StreamedContent getArquivoRetorno() {
			String nomeRelatorioJasper= "relatorio_servicos";
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
		public String getCpf() {
			return cpf;
		}
		public void setCpf(String cpf) {
			this.cpf = cpf;
		}
		public String getPet() {
			return pet;
		}
		public void setPet(String pet) {
			this.pet = pet;
		}
		public List<Agenda> getHistoricoAgendas() {
			return historicoAgendas;
		}
		public void setHistoricoAgendas(List<Agenda> historicoAgendas) {
			this.historicoAgendas = historicoAgendas;
		}
		public StreamedContent getArquivoRetorno2() {
			FacesContext context=FacesContext.getCurrentInstance();
		    ExternalContext ext=context.getExternalContext();
		    String login=ext.getRemoteUser();
		    	
			String nomeRelatorioJasper= "historico_serviços";
			String nomeRelatorioSaida= nomeRelatorioJasper;
			RelatorioUtil util=new RelatorioUtil();
			try{
				HashMap<String, Object> parametros=new HashMap<String, Object>();
				parametros.put("pet", pet);
				parametros.put("login", login);
				this.arquivoRetorno2 = util.geraRelatorio(parametros,nomeRelatorioJasper, nomeRelatorioSaida, tipoRelatorio);
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			return arquivoRetorno2;
		}
		public void setArquivoRetorno2(StreamedContent arquivoRetorno2) {
			this.arquivoRetorno2 = arquivoRetorno2;
		}
		public StreamedContent getArquivoRetornoCliente() {
			String nomeRelatorioJasper= "relatorio_servicos_cliente";
			String nomeRelatorioSaida= nomeRelatorioJasper;
			RelatorioUtil util=new RelatorioUtil();
			try{
				HashMap<String, Object> parametros=new HashMap<String, Object>();
				parametros.put("cpf",cpf);
				this.arquivoRetornoCliente = util.geraRelatorio(parametros,nomeRelatorioJasper, nomeRelatorioSaida, tipoRelatorio);
			}catch(Exception ex){
				
			}
			return arquivoRetornoCliente;
		}
		
		public void setArquivoRetornoCliente(StreamedContent arquivoRetornoCliente) {
			this.arquivoRetornoCliente = arquivoRetornoCliente;
		}
		public List<Agenda> getListaAgendasCliente() {
			return listaAgendasCliente;
		}
		public void setListaAgendasCliente(List<Agenda> listaAgendasCliente) {
			this.listaAgendasCliente = listaAgendasCliente;
		}
		public java.util.Date getData1() {
			return data1;
		}
		public void setData1(java.util.Date data1) {
			this.data1 = data1;
		}
		public java.util.Date getData2() {
			return data2;
		}
		public void setData2(java.util.Date data2) {
			this.data2 = data2;
		}
		public StreamedContent getArquivoRetornoData() {
			String nomeRelatorioJasper= "relatorio_servicos_data";
			String nomeRelatorioSaida= nomeRelatorioJasper;
			RelatorioUtil util=new RelatorioUtil();
			try{
				HashMap<String, Object> parametros=new HashMap<String, Object>();
				SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
		  	  	String sData1 =format.format(data1);
		  	  	String sData2 =format.format(data2);
				
				parametros.put("data1",sData1);
				parametros.put("data2",sData2);
				this.arquivoRetornoData = util.geraRelatorio(parametros,nomeRelatorioJasper, nomeRelatorioSaida, tipoRelatorio);
			}catch(Exception ex){
				
			}
			return arquivoRetornoData;
		}
		public void setArquivoRetornoData(StreamedContent arquivoRetornoData) {
			this.arquivoRetornoData = arquivoRetornoData;
		}
		public void addEvent(ActionEvent actionEvent) throws ParseException, IOException { 
	  	   if(event.getId() == null) {
	        	
	        	//pega a hora inicial e coloca na data do evento
	        	
	        	
	        	//pega a hora final e coloca na data do evento
	        	/*
		        String horas2[]=agenda.getHoraFim().split(":");
	        	int hora2=Integer.parseInt(horas2[0]);
	        	int min2=Integer.parseInt(horas2[1]);
	        	*/
	        	//zera o horario da data atual para comparação
	        	Calendar cal= Calendar.getInstance();
	        	cal.setTime(dataAtual);
	        	cal.set(Calendar.HOUR_OF_DAY,0);
	        	cal.set(Calendar.MINUTE,0);
	        	cal.set(Calendar.SECOND,0);
	        	cal.set(Calendar.MILLISECOND,0);
	        	
	        	Calendar cal2= Calendar.getInstance();
	        	cal2.setTime(event.getStartDate());
	        	cal2.set(Calendar.HOUR_OF_DAY,0);
	        	cal2.set(Calendar.MINUTE,0);
	        	cal2.set(Calendar.SECOND,0);
	        	cal2.set(Calendar.MILLISECOND,0);
	        	
	        		
	        	
	        	
	        	if( cal2.getTimeInMillis() >= cal.getTimeInMillis() ){ 
	        		
	        		//System.out.println("add="+event.getId());
	        		String horas[]=agenda.getHorario().split(":");
	        		int ho=Integer.parseInt(horas[0]);
	        		int ho2=Integer.parseInt(horas[0]);
		        	ho+=1;
		        	String hora2=Integer.toString(ho)+":00:00";
		        	agenda.setHoraInicio(agenda.getHorario());
		        	agenda.setHoraFim(hora2);
		        	agenda.setData(event.getStartDate());
		        	
		        	Calendar calendar = Calendar.getInstance();
		        	calendar.setTime(event.getStartDate());
		        	calendar.set(Calendar.HOUR_OF_DAY,ho2);
		        	
		        	Calendar calendar2 = Calendar.getInstance();
		        	calendar2.setTime(event.getStartDate());
		        	calendar2.set(Calendar.HOUR_OF_DAY,ho);
		        	
		        	agenda.setAtivo(false);
		        	dao.salvar(agenda);
		        	FacesContext con=FacesContext.getCurrentInstance();
		        	con.getExternalContext().redirect("/BRADOG10/restrito/calendario.jsf");
		        	
		        	eventModel.addEvent(new DefaultScheduleEvent(agenda.getCliente().getNome()+", "+agenda.getCliente().getTelefone()+", "+agenda.getServico()+", "+agenda.getPet().getNome(),calendar.getTime(),calendar2.getTime()));
	        	}else{
	        		FacesContext con=FacesContext.getCurrentInstance();
	        		
		        	con.addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_WARN,"A Data Informada Não é Valida",""));
		        	ClienteDAO cliDao=new ClienteDAO();
			    	ExternalContext ext=con.getExternalContext();
			    	String login=ext.getRemoteUser();
			    	Cliente cli=cliDao.buscaPorLogin(login);
			    	agenda=new Agenda();
			    	agenda.setCliente(cli);
					
		        
	        	}
	        }
	        else  {
	        	
	        	ArrayList<Agenda> l=dao.buscaAgenda();
	        	boolean alterou=false;
	        	boolean existeHorario=false;
	  	  	  	for(Agenda age:l){
	  	  	  		if(age.getId()==agenda.getId()){
	  	  	  			if(!age.getHoraInicio().equals(agenda.getHorario())){
	  	  	  				alterou=true;
	  	  	  			}
	  	  			
	  	  		 }
	  	  	  }
		  	  	for(Agenda age:l){
			  	  	SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
			  	  	String data1=format.format(event.getStartDate());
			  	  	String data2=format.format(age.getData());
			  	  	if(alterou){
		  	  	  			if(data1.equals(data2)){
			  	  	  			if(age.getHoraInicio().equals(agenda.getHorario())){
			  	  	  				existeHorario=true;
			  	  	  			}
		  	  	  			}
		  	  	  		}
	  	  	  }
		  			
	  	  	  
	  	  	  if(!existeHorario){
	  	       
	        	String horas[]=agenda.getHorario().split(":");
        		int ho=Integer.parseInt(horas[0]);
        		int ho2=Integer.parseInt(horas[0]);
	        	ho+=1;
	        	String hora2=Integer.toString(ho)+":00:00";
	        	agenda.setHoraInicio(agenda.getHorario());
	        	agenda.setHoraFim(hora2);
	        	agenda.setData(event.getStartDate());
	        	
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.setTime(event.getStartDate());
	        	calendar.set(Calendar.HOUR_OF_DAY,ho2);
	        	
	        	Calendar calendar2 = Calendar.getInstance();
	        	calendar2.setTime(event.getStartDate());
	        	calendar2.set(Calendar.HOUR_OF_DAY,ho);
	        	
	        	
		        Object o=event.getData();
		        String s=o.toString();
		        String st[]=s.split(",");
		        int id=Integer.parseInt(st[0]);
	        	agenda.setId(id);
	        	
	        	dao.atualizarAgenda(agenda);
	        	FacesContext con=FacesContext.getCurrentInstance();
	        	ExternalContext ext=con.getExternalContext();
	        	ext.redirect("/BRADOG10/restrito/calendario.jsf");
	  	  	}else{
		  		FacesContext con=FacesContext.getCurrentInstance();
	    		
	        	con.addMessage("erro", new FacesMessage(FacesMessage.SEVERITY_WARN,"Horario Indisponivel, Escolha outro horario!",""));
	        	
		  	  }
	        
	        }
	  	  
	       // agenda=new Agenda(); 
	     
	    } 
	    
	    public void excluirAgenda() throws IOException{
	    	dao.excluirAgenda(agenda);
	    	FacesContext con=FacesContext.getCurrentInstance();
        	ExternalContext ext=con.getExternalContext();
        	ext.redirect("/BRADOG10/restrito/calendario.jsf");
        	
	    }
	      
	    public void onEventSelect(SelectEvent selectEvent) {  
	        event = (DefaultScheduleEvent) selectEvent.getObject();
	        prepararHorarios();
	        String ag[] =event.getTitle().split(","); 
	        agenda.setServico(ag[1]+","+ag[2]);
	        Pet pet=new Pet();
	        pet.setNome(ag[3]);
	        agenda.setPet(pet);
	        Object o= event.getData();
	        String ob=o.toString();
	        String obj[]=ob.split(",");
	        int id=Integer.parseInt(obj[0]);
	        agenda.setId(id);
	        agenda.setHoraInicio(obj[1]);
	        agenda.setHoraFim(obj[2]);
	        agenda.setHorario(agenda.getHoraInicio());
	        agenda.setAtivo(Boolean.parseBoolean(obj[3]));
	        prepararHorarios();
	      // System.out.println("hora inicial "+agenda.getHorario());
	        
	    }  
	      
	    public void onDateSelect(SelectEvent selectEvent) {  
	        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject()); 
	        prepararHorarios();
	        
	    }
	    
	    public void prepararHorarios(){
	    	  AgendaDAO agendaDao=new AgendaDAO();
	    	  ArrayList<Agenda> lista=agendaDao.buscaAgenda();
		      
		      
		        //inicializa o array de horarios
		        horarioList.clear();
	    		horarioList.add("08:00:00");
				horarioList.add("09:00:00");
				horarioList.add("10:00:00");
				horarioList.add("11:00:00");
				horarioList.add("12:00:00");
				horarioList.add("13:00:00");
				horarioList.add("14:00:00");
				horarioList.add("15:00:00");
				horarioList.add("16:00:00");
				horarioList.add("17:00:00");
				
				//zera o horario da data atual para comparação e a data de entrada
	        	Calendar cal= Calendar.getInstance();
	        	cal.setTime(dataAtual);
	        	cal.set(Calendar.HOUR_OF_DAY,0);
	        	cal.set(Calendar.MINUTE,0);
	        	cal.set(Calendar.SECOND,0);
	        	cal.set(Calendar.MILLISECOND,0);
	        	/////////////////////////////////////
	        	Calendar cal2= Calendar.getInstance();
	        	cal2.setTime(event.getStartDate());
	        	cal2.set(Calendar.HOUR_OF_DAY,0);
	        	cal2.set(Calendar.MINUTE,0);
	        	cal2.set(Calendar.SECOND,0);
	        	cal2.set(Calendar.MILLISECOND,0);
				
				////////remove os horarios antes da hora atual//////
				Calendar c=Calendar.getInstance();
				c.setTime(dataAtual);
				int hora=c.get(Calendar.HOUR_OF_DAY);
				boolean removeu=false;
				System.out.println("data1"+cal.getTime());
				System.out.println("data2"+event.getStartDate());
				
				if(cal2.getTime().compareTo(cal.getTime())==0){
					
					for(int i=0;i<horarioList.size();i++){
						String horas[]=horarioList.get(i).split(":");
						int h=Integer.parseInt(horas[0]);
						
							if(hora>=h){
								if(removeu){
									i-=i;
								horarioList.remove(i);
								removeu=true;
								}else{
									horarioList.remove(i);
									removeu=true;
																
								}
								
							}
							
						}
					
					for(int i=0;i<horarioList.size();i++){
						String horas[]=horarioList.get(i).split(":");
						int h=Integer.parseInt(horas[0]);
							if(hora>=h){
								if(removeu){
									i-=i;
								horarioList.remove(i);
								removeu=true;
								}else{
									horarioList.remove(i);
									removeu=true;
																
								}
								
							}
							
						}
				}
				
				//verifica as disponibilidades de horarios
		        for(Agenda age:lista){     	
		        	
		        	if(cal2.getTime().compareTo(age.getData())==0){
		        		
		        		if(horarioList.contains(age.getHoraInicio())){
		        			horarioList.remove(age.getHoraInicio());
		    				
		        		}
		        	}
					
				}
	    }
	     
	    public void gerarHistorico(){
	    	historicoAgendas.clear();
	    	AgendaDAO agDao=new AgendaDAO();
	    	ArrayList<Agenda> listaAge=agDao.buscaAgenda();
		    FacesContext context=FacesContext.getCurrentInstance();
	    	ExternalContext ext=context.getExternalContext();
	    	String login=ext.getRemoteUser();
	    	for(Agenda ag:listaAge){
				if(ag.getPet().getNome().equals(pet)){
					if(ag.getPet().getCliente().getLogin().equals(login)){
						historicoAgendas.add(ag);
						
					}
				}
			}	
	    }
	    
	   public void buscaAgendaPorCpf(){
		   listaAgendasCliente.clear();
		   AgendaDAO agDao=new AgendaDAO();
		   ArrayList<Agenda> listaAge=agDao.buscaAgenda();
		   for(Agenda ag:listaAge){
			   if(ag.getCliente().getCpf().equals(cpf)){
				   listaAgendasCliente.add(ag);
			   }
		   }
		   
	   }
	   
	   public void buscaAgendaPorData(){
		   listaAgendasCliente.clear();
		   AgendaDAO agDao=new AgendaDAO();
		   ArrayList<Agenda> listaAge=agDao.buscaAgenda();
		   for(Agenda ag:listaAge){
			   if(ag.getData().getTime()>=data1.getTime() && ag.getData().getTime()<=data2.getTime()){
				   listaAgendasCliente.add(ag);
			   }
		   }
		   
	   }
	    
	    public void onEventMove(ScheduleEntryMoveEvent event) {  
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
	          
	        addMessage(message);  
	    }  
	      
	    public void onEventResize(ScheduleEntryResizeEvent event) {  
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
	          
	        addMessage(message);  
	    }  
	      
	    private void addMessage(FacesMessage message) {  
	        FacesContext.getCurrentInstance().addMessage(null, message);  
	    }

		
	} 


