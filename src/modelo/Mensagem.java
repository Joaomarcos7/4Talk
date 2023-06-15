package modelo;

import java.time.LocalDateTime;


public class Mensagem {
	
	
	private static int id;
	private String texto;
	private Participante emitente;
	private Participante destinatario;
	private LocalDateTime datahora;
	
	
	public Mensagem( int id,String texto, Participante emitente, Participante destinatario) {
		Mensagem.id=id;
		this.texto=texto;
		this.emitente=emitente;
		this.destinatario=destinatario;
		this.datahora= LocalDateTime.now();
		
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		Mensagem.id = id;
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


	public LocalDateTime getDatahora() {
		return datahora;
	}


	public void setDatahora(LocalDateTime datahora) {
		this.datahora = datahora;
	}


	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", texto=" + texto + ", emitente=" + emitente + ", destinatario=" + destinatario
				+ ", datahora=" + datahora + "]";
	}
	
	

	



	
	

}
