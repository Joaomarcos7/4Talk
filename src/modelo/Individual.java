package modelo;

import java.util.ArrayList;

public class Individual extends Participante {

	
	private String senha;
	 private boolean administrador;
	 private ArrayList<Grupo> grupos=new ArrayList<>();
	
	public Individual(String nome, String senha, boolean administrador) {
		super(nome);
		this.senha=senha;
		this.administrador=administrador;
		// 
	}
	


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("\n grupos:");
		if (this.grupos.isEmpty()) {
			sb.append(" sem grupo");
		} else {
			for (Grupo g : this.getGrupos()) {
				sb.append("\n --> ").append(g.getNome()).append("\n");
			}
		}
		return sb.toString();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public ArrayList<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(ArrayList<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	public void adicionar(Grupo grupo) {
		grupos.add(grupo);
	}

	public void remover(Grupo grupo) {
		grupos.remove(grupo);
	}
	
	
}
