package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Mensagem {
	
	
	private int id;
	private String texto;
	private Participante emitente;
	private Participante destinatario;
	private String datahora;
	
	
	public Mensagem( int id,String texto, Participante emitente, Participante destinatario) {
		this.id=id;
		this.texto=texto;
		this.emitente=emitente;
		this.destinatario=destinatario;
		this.datahora= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public Participante getEmitente() {
		return emitente;
	}


	public void setEmitente(Participante emitente) {
		this.emitente = emitente;
	}


	public Participante getDestinatario() {
		return destinatario;
	}


	public void setDestinatario(Participante destinatario) {
		this.destinatario = destinatario;
	}


	public String getDatahora() {
		return datahora;
	}


	public void setDatahora(String datahora) {
		this.datahora = datahora;
	}


	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", texto=" + texto + ", emitente=" + emitente + ", destinatario=" + destinatario
				+ ", datahora=" + datahora + "]";
	}
	
	

	



	
	

}
