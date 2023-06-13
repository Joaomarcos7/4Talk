package modelo;

import java.util.ArrayList;

public class Grupo extends Participante {

	
	private ArrayList<Individual> individuos;
	
	public Grupo(String nome ) {
		super(nome);
		
	}

	@Override
	public String toString() {
		return "Grupo [individuos=" + individuos + "]";
	}

	public ArrayList<Individual> getIndividuos() {
		return individuos;
	}

	public void setIndividuos(ArrayList<Individual> individuos) {
		this.individuos = individuos;
	}
	
	public void adicionar(Individual ind) {
		individuos.add(ind);
	}
	
}
