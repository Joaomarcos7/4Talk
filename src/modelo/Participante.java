package modelo;

import java.util.ArrayList;

public class Participante {
	
	
	 private String nome;
	private ArrayList<Mensagem> recebidas=new ArrayList<>();
	private ArrayList<Mensagem> enviadas= new ArrayList<>();
	
	
	public Participante(String nome) {
		
		this.nome=nome;
		
	}
	
	
	


	@Override
	public String toString() {
		return "Nome:" + nome;
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


	public void setRecebidas(Mensagem recebidas) {
		this.recebidas.add(recebidas);
	}


	public ArrayList<Mensagem> getEnviadas() {
		return enviadas;
	}


	public void setEnviadas(Mensagem enviada) {
		this.enviadas.add(enviada);
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
