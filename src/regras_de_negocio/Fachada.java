package regras_de_negocio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	
	
	
	
	public static Individual validarIndividuo(String nomeindividuo,String senha) throws Exception {
		
		if(repositorio.localizarIndividual(nomeindividuo)==null)
		{
			throw new Exception("Indivíduo não existe!");
		}
		else {
			if(senha.equals(repositorio.localizarIndividual(nomeindividuo).getSenha())) {
				return repositorio.localizarIndividual(nomeindividuo);
			}
			else {
				throw new Exception("Senha inválida pra esse individuo");
			}
		}
		
		
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
			throw new Exception("criar Grupo - nome vazio!");
		
			if(repositorio.getparticipantes().isEmpty()) {
				Grupo grupo= new Grupo(nome);
				repositorio.adicionar(grupo);
				repositorio.salvarObjetos();
			}
		
		else {
			Participante p = repositorio.localizarParticipante(nome);
			
			if(p != null) 
				throw new Exception("criar Grupo - Grupo ja existe:" + nome);
		
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
		
		if(ind==null || grupo==null)
			throw new Exception("Inserir Grupo -> Individuo ou grupo não existem");
		
		
		grupo.adicionar(ind);
		repositorio.salvarObjetos();
		
	}
	
	
	
	public static void removerGrupo(String nomeind,String nomegrupo) throws Exception {
		
		if(nomeind.isEmpty() || nomegrupo.isEmpty()) 
			throw new Exception("Remove Grupo - nome fornecido vazio");
		if(repositorio.getGrupos().isEmpty())
			throw new Exception("Remove Grupo - Não há grupos no repositorio");
		
		Grupo grupo = repositorio.localizarGrupo(nomegrupo);
		if(grupo==null)
			throw new Exception("Remove Grupo - Grupo não existe no repositorio!");
		
		
		grupo.remover(nomeind);
		repositorio.salvarObjetos();
		
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

		
		 //INCREMENTAÇÃO DO ID, OU SEJA, TODA VEZ Q CRIARMOS UMA MENSAGEM USANDO O METODO ESTAREMOS INCREMENTANDO A PROPRIEDADE CONT DA FACHADA PRA +1 QUE NO FINAL SERA O ID SEQUENCIAL DA MENSAGEM 
		int idmsg= repositorio.GerarId();
		
		if (destinatario instanceof Grupo g ) {
			Mensagem mensagem= new Mensagem(idmsg,texto,emitente,g,LocalDateTime.now());
			emitente.setEnviadas(mensagem);
			g.setRecebidas(mensagem);
			repositorio.adicionar(mensagem);
			
			//verifica se nesse caso estamos enviando uma mensagem para um destinatario tipo Grupo 
			//CRIACAO DE COPIAS
			for(Individual i : g.getIndividuos()) {
				if(i.getNome() != emitente.getNome()){ //verificao pra mandar msg no grupo caso seja diferente do emitente (no grupo)
				Mensagem msg= new Mensagem(idmsg,emitente.getNome()+"/"+texto,g,i,LocalDateTime.now()); 
				g.setEnviadas(msg);
				i.setRecebidas(msg); 
				repositorio.adicionar(msg); //adicionando ao repositorio
				repositorio.salvarObjetos();
				}
			}
		}
		else {
		Mensagem mensagem = new Mensagem(idmsg,texto,emitente,destinatario,LocalDateTime.now()); //cria objeto de mensagem relacionando emitente e dest
		emitente.setEnviadas(mensagem); //adiciono objeto mensagem no enviados do emitente
		destinatario.setRecebidas(mensagem); //adiciono objeto mensagem no recebidos do destinatario
		repositorio.adicionar(mensagem); //adiciona msg no repositorio 
		repositorio.salvarObjetos();
		}
	}

	//class Mensagem: private int id=0;
	
	
	
	public static ArrayList<Mensagem> obterConversa(String nomeind, String nomedest) throws Exception{
		

		Individual emitente = repositorio.localizarIndividual(nomeind);	
		if(emitente == null) 
			throw new Exception("Usário emitente nao encontrado");

		Participante destinatario = repositorio.localizarParticipante(nomedest);	
		if(destinatario == null) 
			throw new Exception("Usário destinatário nao encontrado");

		
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
	
	
	
	/*public static LocalDateTime formataData(LocalDateTime now) {
			
			String datahora=now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			
			return LocalDateTime.parse(datahora);
			
			
		}*/
	
	
	
	public static void apagarMensagem(String nomeindividuo, int id) throws  Exception{
		Individual emitente = repositorio.localizarIndividual(nomeindividuo);	
		if(emitente == null) 
			throw new Exception("apagar mensagem - nome nao existe:" + nomeindividuo);

		Mensagem m = emitente.localizarEnviada(id);
		if(m == null)
			throw new Exception("apagar mensagem - mensagem nao pertence a este individuo:" + id);

		emitente.removerEnviada(m);
		Participante destinatario = m.getDestinatario();
		
		
		
		if(destinatario instanceof Grupo g) {
			g.removerRecebida(m);
			repositorio.remover(m);
			
			for(Mensagem msg : g.getEnviadas()) {
				if(msg.getId()==m.getId())
				{
					g.removerEnviada(msg);
					msg.getDestinatario().removerRecebida(msg);
					repositorio.remover(msg);
				}
				
			}
			repositorio.salvarObjetos();
		}
		destinatario.removerRecebida(m);
		repositorio.remover(m);	
		repositorio.salvarObjetos();

		
	}


	public static  ArrayList<Mensagem> listarMensagensEnviadas(String nome) throws Exception{
		
		Individual ind= repositorio.localizarIndividual(nome);
		if (ind==null)
			throw new Exception("Indivíduo não existe");
		
		return ind.getEnviadas();
		
	}
	
	public static  ArrayList<Mensagem> listarMensagensRecebidas(String nome) throws Exception{

		Individual ind= repositorio.localizarIndividual(nome);
		if (ind==null)
			throw new Exception("Indivíduo não existe");
		
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
 	
	public static ArrayList<Mensagem> listarMensagens(){
		
		return repositorio.getmensagens();
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
			for(Mensagem m : repositorio.getmensagens()) {
				msgs.add(m);
			}
		}
		
		
		
		for(Mensagem m : Distinct(repositorio.getmensagens())) {
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
		
		Collection<Participante> users= repositorio.getparticipantes().values(); //nome dos participantes
		ArrayList<String> lista= new ArrayList<>();
	
		
		for(Participante i : users) {
			
			if(i.getEnviadas().isEmpty()) {
				lista.add(i.getNome());
			}
			
		}
		
		return lista;
	}
	
	
	public static ArrayList<Mensagem> Distinct(ArrayList<Mensagem> mensagens){ //quando nao quero printar as copias de MESMO ID
		
		ArrayList<Mensagem> distintas= new ArrayList<>();
		ArrayList<Integer>  aux= new ArrayList<>();
		
		for( Mensagem m : mensagens) {
	
			if(distintas.isEmpty()) {
				aux.add(m.getId());
				distintas.add(m);
			}
			else {
				if(!aux.contains(m.getId())) {
					aux.add(m.getId());
					distintas.add(m);
				}
			}
			
			
		}
		
		return distintas;
	}
	
	
	
	
	
	
	
	
	
}
