package modelo;

import java.util.ArrayList;

public class Grupo extends Participante {

	
	private ArrayList<Individual> individuos=new ArrayList<>();
	
	public Grupo(String nome ) {
		super(nome);
		
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("\n individuos do grupo:");
		if (individuos.isEmpty()) {
			sb.append(" vazio");
		} else {
			for (Individual i : getIndividuos()) {
				sb.append("\n --> ").append(i.getNome());
			}
		}
		return sb.toString();
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
	
	
	public void remover(Individual ind) {
		for(int i=0;i<individuos.size();i++) {
			if(individuos.get(i).getNome()==ind.getNome()) {
				individuos.remove(i);
			}
		}
	}
	
}
