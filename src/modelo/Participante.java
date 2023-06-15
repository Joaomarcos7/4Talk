package modelo;

import java.util.ArrayList;

public class Participante {
	
	
	 private String nome;
	private ArrayList<Mensagem> recebidas;
	private ArrayList<Mensagem> enviadas;
	
	
	public Participante(String nome) {
		
		this.nome=nome;
		
	}
	
	
	


	@Override
	public String toString() {
		return "Participante [nome=" + nome + ", recebidas=" + recebidas + ", enviadas=" + enviadas + "]";
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public ArrayList<Mensagem> getRecebidas() {
		return recebidas;
	}


	public void setRecebidas(ArrayList<Mensagem> recebidas) {
		this.recebidas = recebidas;
	}


	public ArrayList<Mensagem> getEnviadas() {
		return enviadas;
	}


	public void setEnviadas(ArrayList<Mensagem> enviadas) {
		this.enviadas = enviadas;
	}
	
	public Mensagem localizarEnviada(int id) {
		
		for(Mensagem m : this.getEnviadas()) {
			if(m.getId()==id) {
				return m;
			}
		}
		return null;
		}
	
	
	public void removerEnviada(Mensagem msg) {
		
		this.getEnviadas().remove(msg);
		
	}
	
	
	public void removerRecebida(Mensagem msg) {
		
		this.getRecebidas().remove(msg);
		
	}
	
	
}
