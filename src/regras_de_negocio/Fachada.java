package regras_de_negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {

	private static Repositorio repositorio= new Repositorio();
	
	
	public  static void criarIndividuo(String nome, String senha) throws Exception {
		if(nome.isEmpty()) 
			throw new Exception("criar individual - nome vazio:");
		if(senha.isEmpty()) 
			throw new Exception("criar individual - senha vazia:");
		
		if(repositorio.getparticipantes().isEmpty()) {
			
			Individual individuo = new Individual(nome,senha, false);
			repositorio.adicionar(individuo);	
			repositorio.salvarObjetos();
		}
		else {
		Participante p = repositorio.localizarParticipante(nome);
		if(p != null) 
			throw new Exception("criar individual - nome ja existe:" + nome);


		Individual individuo = new Individual(nome,senha, false);
		repositorio.adicionar(individuo);
		repositorio.salvarObjetos();
		}
		
	}
	
	
	
	public static boolean validarIndividuo(String nomeindividuo,String senha) {
		
		if(repositorio.localizarIndividual(nomeindividuo)!=null)
		{
			return true;
		}
		
		return false;
		
		
	}
	
	
	
	public static void criarAdministrador(String nome,String senha) throws Exception {
		
		if(nome.isEmpty()) 
			throw new Exception("criar individual - nome vazio:");
		if(senha.isEmpty()) 
			throw new Exception("criar individual - senha vazia:");
		
		
		if(repositorio.getparticipantes().isEmpty()) {   //verifica se o repositorio de participantes esta vazio e estamos adicionando o primeiro 
			
			Individual individuo = new Individual(nome,senha,true);
			repositorio.adicionar(individuo);	
			repositorio.salvarObjetos();
		}
		
		else { //verificamos se existe outro participante 
		Participante p = repositorio.localizarParticipante(nome);
		if(p != null) 
			throw new Exception("criar administrador - adm ja existe:" + nome);


		Individual individuo = new Individual(nome,senha,true);
		repositorio.adicionar(individuo);	
		repositorio.salvarObjetos();
		}
		
	}
	
	
	
	
	public static void criarGrupo(String nome) throws Exception {
		if(nome.isEmpty()) 
			throw new Exception("criar Grupo - nome vazio:");
	
		if(repositorio.getparticipantes().isEmpty()) {
			Grupo grupo= new Grupo(nome);
			repositorio.adicionar(grupo);
			repositorio.salvarObjetos();
		}
		
		else {
		Participante p = repositorio.localizarParticipante(nome);
		if(p != null) 
			throw new Exception("criar Grupo - nome ja existe:" + nome);


		Grupo grupo= new Grupo(nome);
		repositorio.adicionar(grupo);
		repositorio.salvarObjetos();
		}
	}
	
	
	
	public static void inserirGrupo(String nomeind,String nomegrupo) throws Exception {
		if(nomeind.isEmpty() || nomegrupo.isEmpty()) 
			throw new Exception("Inserir Gruopo - nome vazio");
		
		
		Individual ind= repositorio.localizarIndividual(nomeind);
		
		Grupo grupo = repositorio.localizarGrupo(nomegrupo);
		
		grupo.adicionar(ind);
		
	}
	
	
	
	public static void removerGrupo(String nomegrupo,String nomeind) throws Exception {
		
		if(nomeind.isEmpty() || nomegrupo.isEmpty()) 
			throw new Exception("Remove Grupo - nome fornecido vazio");
		if(repositorio.getGrupos().isEmpty())
			throw new Exception("Remove Grupo - Não há grupos no repositorio");
		
		Grupo grupo = repositorio.localizarGrupo(nomegrupo);
		if(grupo==null)
			throw new Exception("Remove Grupo - Grupo não existe no repositorio!");
		
		
		grupo.remover(nomeind);
	
		
	}
	
	
	public static void criarMensagem(String nomeind,String nomedest,String texto) throws Exception  {
		
	
		if(texto.isEmpty()) 
			throw new Exception("criar mensagem - texto vazio:");

		Individual emitente = repositorio.localizarIndividual(nomeind);	
		if(emitente == null) 
			throw new Exception("criar mensagem - emitente nao existe:" + nomeind);

		Participante destinatario = repositorio.localizarParticipante(nomedest);	
		if(destinatario == null) 
			throw new Exception("criar mensagem - destinatario nao existe:" + nomedest);

		if(destinatario instanceof Grupo g && repositorio.localizarGrupo(g.getNome())==null)
			throw new Exception("criar mensagem - grupo nao permitido:" + nomedest);
		
		 //INCREMENTAÇÃO DO ID, OU SEJA, TODA VEZ Q CRIARMOS UMA MENSAGEM USANDO O METODO ESTAREMOS INCREMENTANDO A PROPRIEDADE CONT DA FACHADA PRA +1 QUE NO FINAL SERA O ID SEQUENCIAL DA MENSAGEM 
		int cont= repositorio.GerarId();
		
		if (destinatario instanceof Grupo g ) { //verifica se nesse caso estamos enviando uma mensagem para um destinatario tipo Grupo
			for(Individual i : g.getIndividuos()) {
				if(i.getNome() != emitente.getNome()){ //verificao pra mandar msg no grupo caso seja diferente do emitente (no grupo)
				Mensagem msg= new Mensagem(cont,texto,destinatario,i);
				destinatario.setEnviadas(msg);
				i.setRecebidas(msg); 
				repositorio.adicionar(msg);
				repositorio.salvarObjetos();
				}
			}
		}
		
		Mensagem mensagem = new Mensagem(cont,texto,emitente,destinatario); //cria objeto de mensagem relacionando emitente e dest
		emitente.setEnviadas(mensagem); //adiciono objeto mensagem no enviados do emitente
		destinatario.setRecebidas(mensagem); //adiciono objeto mensagem no recebidos do destinatario
		repositorio.adicionar(mensagem); //adiciona msg no repositorio 
		repositorio.salvarObjetos();
		
	}

	//class Mensagem: private int id=0;
	
	
	
	public static ArrayList<Mensagem> obterConversa(String nomeind, String nomedest) throws Exception{
		

		Individual emitente = repositorio.localizarIndividual(nomeind);	
		if(emitente == null) 
			throw new Exception("Uusário emitente nao encontrado");

		Participante destinatario = repositorio.localizarParticipante(nomedest);	
		if(destinatario == null) 
			throw new Exception("Uusário destinitário nao encontrado");

		
			ArrayList<Mensagem> enviadas= emitente.getEnviadas();

			ArrayList<Mensagem> recebidas= emitente.getRecebidas();
				
			
			
			ArrayList<Mensagem> conversa= new ArrayList<>();
			
			
			for(Mensagem m : enviadas) {
				if(m.getDestinatario().getNome()==nomedest) {
					conversa.add(m);
				}
			}
			
			
			for(Mensagem m : recebidas) {
				if(m.getEmitente().getNome()==nomedest) {
					conversa.add(m);
				}
			}
			
			//Ordenação da conversa para ter as mensagens em ordem CRONOLOGICA
			
			Collections.sort(conversa,new Comparator<Mensagem>() {
				public int compare(Mensagem m1,Mensagem m2) {
					return Integer.compare(m1.getId(), m2.getId());
				}
			});
			
			return conversa;
	}
	
	
	
	
	
	
	public static void apagarMensagem(String nomeindividuo, int id) throws  Exception{
		Individual emitente = repositorio.localizarIndividual(nomeindividuo);	
		if(emitente == null) 
			throw new Exception("apagar mensagem - nome nao existe:" + nomeindividuo);

		Mensagem m = emitente.localizarEnviada(id);
		if(m == null)
			throw new Exception("apagar mensagem - mensagem nao pertence a este individuo:" + id);

		emitente.removerEnviada(m);
		Participante destinatario = m.getDestinatario();
		destinatario.removerRecebida(m);
		repositorio.remover(m);	
		repositorio.salvarObjetos();

		if(destinatario instanceof Grupo g) {
			ArrayList<Mensagem> lista = destinatario.getEnviadas();
			lista.removeIf(new Predicate<Mensagem>() {
				@Override
				public boolean test(Mensagem t) {
					if(t.getId() == m.getId()) {
						t.getDestinatario().removerRecebida(t);
						repositorio.remover(t);	
						repositorio.salvarObjetos();
						return true;		//apaga mensagem da lista
					}
					else
						return false;
				}

			});

		}
	}


	public static  ArrayList<Mensagem> listarMensagensEnviadas(String nome){
		
		Individual ind= repositorio.localizarIndividual(nome);
		
		return ind.getEnviadas();
		
	}
	
	public static  ArrayList<Mensagem> listarMensagensRecebidas(String nome){

		Individual ind= repositorio.localizarIndividual(nome);
		
		return ind.getRecebidas();
		
	}

	
	
	public  static ArrayList<Individual> listarIndividuos()
	{
		ArrayList<Individual> ind= repositorio.getIndividuos();
		
		
		return ind;
	}


	
	
	public static ArrayList<Grupo> listarGrupos(){
		
		ArrayList<Grupo> grupos= repositorio.getGrupos();
		
		return grupos;
	}
 	
	public static Collection<Mensagem> listarMensagens(){
		
		return repositorio.getmensagens().values();
	}
	
	
	public static ArrayList<Mensagem> espionarMensagens(String nomeadmin, String termo) throws Exception{
		
		if(nomeadmin.isEmpty()) {
			throw new Exception(" Não pode espionar Mensangens, Nome do ADM não foi fornecido");
		}
		
		Individual admin= repositorio.localizarIndividual(nomeadmin);
		
		if(admin.getAdministrador()==false) {
			throw new Exception(nomeadmin+ "não é um participante administrador!");
		}
		
		ArrayList<Mensagem> msgs= new ArrayList<>();
		
		
		if(termo.isEmpty()) {
			for(Mensagem m : repositorio.getmensagens().values()) {
				msgs.add(m);
			}
		}
		
		
		
		for(Mensagem m : repositorio.getmensagens().values()) {
				String[] array= m.getTexto().split(" ");
				
				for(String s : array) {
						if(s.equals(termo)) {
							msgs.add(m);
						}
				}
		}
		
		return msgs;
	}
	
	
	
	public static ArrayList<String> ausentes(String nomeadmin) throws Exception
	{
		if(nomeadmin.isEmpty()) {
			throw new Exception(" Nome nao foi fornecido corretamente");
		}
		
		Individual admin = repositorio.localizarIndividual(nomeadmin);
		
		if(admin.getAdministrador()==false) {
			throw new Exception(nomeadmin + "não é um administrador do sistema");
		}
		
		Collection<Participante> users= repositorio.getparticipantes().values();
		ArrayList<String> lista= new ArrayList<>();
		
		for(Participante i : users) {
			
			if(i.getEnviadas().isEmpty()) {
				lista.add(i.getNome());
			}
			
		}
		
		return lista;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
