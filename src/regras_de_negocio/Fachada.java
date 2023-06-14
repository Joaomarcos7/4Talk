package regras_de_negocio;

import java.util.ArrayList;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {

	
	
	public static void criarIndividuo(String nomeindividuo, String senha) {
		if(Repositorio.localizarParticipante(nomeindividuo)==null && senha!=null) {
			Repositorio.adicionar(new Individual(nomeindividuo,senha));
		}
		
	}
	
	
	
	public static boolean validarIndividuo(String nomeindividuo,String senha) {
		
		if(Repositorio.localizarIndividual(nomeindividuo)!=null)
		{
			return true;
		}
		
		return false;
		
		
	}
	
	
	
	public static void criarAdministrador(String nomeadministrador,String senha) {
		
		if(Repositorio.localizarParticipante(nomeadministrador)==null && senha!=null) 
		{
			Repositorio.adicionar(new Individual(nomeadministrador,senha,true));
		}
		
		
	}
	
	
	
	
	public static void criarGrupo(String nomegrupo) {
		
		if(Repositorio.localizarParticipante(nomegrupo)==null) 
		{
			Repositorio.adicionar(new Grupo(nomegrupo));
		}
	}
	
	
	
	public static void inserirGrupo(String nomeind,String nomegrupo) {
		Individual ind= Repositorio.localizarIndividual(nomeind);
		
		Grupo grupo = Repositorio.localizarGrupo(nomegrupo);
		
		grupo.adicionar(ind);
		
	}
	
	
	
	public static void removerGrupo(String nomegrupo,String nomeind) {
		
		Individual ind= Repositorio.localizarIndividual(nomeind); //nao esta sendo usado 
		
		Grupo grupo = Repositorio.localizarGrupo(nomegrupo);
		
		grupo.remover(nomeind);
		
	}
	
	
	public static void criarMensagem(String nomeind,String nomedest,String texto) {
		
		
		Individual ind= Repositorio.localizarIndividual(nomeind);
		
		Individual dest= Repositorio.localizarIndividual(nomedest);
		
		Mensagem msg= new Mensagem(texto,ind,dest); ////construtor errado // como fazer o id sequencial????
		
		Repositorio.adicionar(msg); //precisa adicionar ao repositorio??
		
		
	}
	
	
	
	public static ArrayList<Mensagem> obterConversa(String nomeind, String nomedest){
		
				Individual ind= Repositorio.localizarIndividual(nomeind);
				
				Participante dest= Repositorio.localizarParticipante(nomedest);
				
				
				ArrayList<Mensagem> msgs= new ArrayList<>();
				
				for(Mensagem m : Repositorio.getmensagens().values()) {
					
					if(m.getDestinatario().getNome()==nomedest && m.getEmitente().getNome()==nomeind || m.getDestinatario().getNome()==nomeind && m.getEmitente().getNome()==nomedest ) 
					{
						msgs.add(m);
					}
				}
				
				msgs.sort()
				
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
