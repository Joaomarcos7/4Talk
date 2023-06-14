package modelo;

import java.util.ArrayList;

public class Individual extends Participante {

	
	private String senha;
	 private boolean administrador;
	 private ArrayList<Grupo> grupos;
	
	public Individual(String nome, String senha, boolean administrador) {
		super(nome);
		this.senha=senha;
		this.administrador=administrador;
		// 
	}
	public Individual(String nome, String senha) {
		super(nome);
		this.senha=senha;
		this.administrador=false;
		// 
	}


	@Override
	public String toString() {
		return "Individual [senha=" + senha + ", administrador=" + administrador + ", grupos=" + grupos + "]";
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
	
	

	
	
	
	
	
}
