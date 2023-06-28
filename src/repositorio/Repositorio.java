package repositorio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {

	
	  TreeMap<String,Participante> participantes= new TreeMap<String,Participante>();
	  TreeMap <Integer,Mensagem> mensagens=new TreeMap<Integer,Mensagem>();
	
	  
	  
	  
	  public Repositorio() {
		  this.carregarObjetos();
	  }
	  
	  
	public int GerarId() {
		if(mensagens.isEmpty()) {
			return 1;
		}
		
		Integer ultchave= mensagens.lastKey();
		
		
		return ultchave +1;
		
		
	}
	
	public  TreeMap<String,Participante> getparticipantes(){
		return participantes;
		
	}

	
	public  TreeMap<Integer,Mensagem> getmensagens(){
		return mensagens;
		
	}
	
	
	public   void adicionar(Participante participante) {
		// TODO Auto-generated method stub
		participantes.put(participante.getNome(), participante);

}

	public  void adicionar(Mensagem mensagem) {
		mensagens.put(mensagem.getId(), mensagem);
		
	}
	
	public  void adicionar(Individual individuo) {
		participantes.put(individuo.getNome(),individuo);
		
	}
	
	public void adicionar(Grupo grupo) {
		participantes.put(grupo.getNome(),grupo);
		
	}
	
	

	public Participante localizarParticipante(String nome) {
		
		for(Participante p : participantes.values()) {
			if(p.getNome().equals(nome)) {
				return p;
			}
			
		}
		return null;
	}
	
	
	
	
	public  Individual localizarIndividual(String nome) {
		for(Participante p : participantes.values())
			{
				if(p instanceof Individual i &&  p.getNome().equals(nome) )	
				{
					return i;
				}
				
		
			}
		return null;
	}
	
	
	
	
	
	
	public  Grupo localizarGrupo(String nome) {
		for(Participante p : participantes.values()) {
			
			if(p instanceof Grupo g && p.getNome().equals(nome)) {
				return g;
			}
			
		}
		return null;
	}
	
	
	
	public  Mensagem localizarMensagem(Integer id) {
	

		for(Mensagem m : mensagens.values()) {
			if(m.getId() == id) {
				return m;
			}
			
		}
		return null;
	}
	
	

	
	
	
	public ArrayList<Grupo> getGrupos(){
		
		ArrayList<Grupo> grupos= new ArrayList<>();
		
		for(Participante p : participantes.values()) {
			if (p instanceof Grupo g) {
				grupos.add(g);
			}
			
		}
		return grupos;
	}
	
	
	
	
	public ArrayList<Individual> getIndividuos(){
		
		ArrayList<Individual> individuos= new ArrayList<>();
		
		for(Participante p : participantes.values()) {
			if (p instanceof Individual ind) {
				individuos.add(ind);
			}
			
		}
		return individuos;
	}
	
	public void remover(Mensagem m) {
		 
		this.mensagens.remove(m.getId());
		
	}
	
	

	public void carregarObjetos()  	{
		// carregar para o repositorio os objetos dos arquivos csv
		try {
			//caso os arquivos nao existam, serao criados vazios
			File f1 = new File( new File(".\\mensagens.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File(".\\individuos.csv").getCanonicalPath() ) ; 
			File f3 = new File( new File(".\\grupos.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() || !f3.exists() ) {
				//System.out.println("criando arquivo .csv vazio");
				FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
				FileWriter arquivo3 = new FileWriter(f3); arquivo3.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}

		String linha;	
		String[] partes;	

		try	{
			String nome,senha,administrador;
			File f = new File( new File(".\\individuos.csv").getCanonicalPath())  ;
			Scanner arquivo1 = new Scanner(f);	 //  pasta do projeto
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				nome = partes[0];
				senha = partes[1];
				administrador = partes[2];
				Individual ind = new Individual(nome,senha,Boolean.parseBoolean(administrador));
				this.adicionar(ind);
			}
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de individuos:"+ex.getMessage());
		}

		try	{
			String nome;
			Grupo grupo;
			Individual individuo;
			File f = new File( new File(".\\grupos.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 //  pasta do projeto
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				nome = partes[0];
				grupo = new Grupo(nome);
				if(partes.length>1)
					for(int i=1; i< partes.length; i++) {
						individuo = this.localizarIndividual(partes[i]);
						grupo.adicionar(individuo);
					}
				this.adicionar(grupo);
			}
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de grupos:"+ex.getMessage());
		}


		try	{
			String id, nomeemitente, nomedestinatario,texto;
			Mensagem m;
			Participante emitente,destinatario;
			File f = new File( new File(".\\mensagens.csv").getCanonicalPath() )  ;
			Scanner arquivo3 = new Scanner(f);	 //  pasta do projeto
			while(arquivo3.hasNextLine()) 	{
				linha = arquivo3.nextLine().trim();		
				partes = linha.split(";");	
				//System.out.println(Arrays.toString(partes));
				id = partes[0];
				nomeemitente = partes[1];
				nomedestinatario = partes[2];
				texto = partes[3];
				emitente = this.localizarParticipante(nomeemitente);
				destinatario = this.localizarParticipante(nomedestinatario);
				m = new Mensagem(Integer.parseInt(id),texto,emitente,destinatario,LocalDateTime.now());
				this.adicionar(m);
			} 
			arquivo3.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de mensagens:"+ex.getMessage());
		}

	}



//public int GetUltimoId() { //Tenho que testar ainda!!!
//	try {
		///File f=new File( new File(".\\mensagens.csv").getCanonicalPath());
	
/*		Scanner arquivo1= new Scanner(f);
		
		int cont=0;
		
		while(arquivo1.hasNextLine()) {
			cont++; //pega o numero de linhas do arquivo
		}
		
		
		for(int i=1;i<=cont;i++) {
			String texto=arquivo1.nextLine();
			if (i==cont){ //se for a ultima linha ent ele pega o ID
				String[] array=texto.split(";");
				String id= array[0];
			return 	Integer.parseInt(id);
			}
		}
	}
	
	catch(Exception e) {
		System.out.println(e.getMessage());
	}
	return 0;
}
*/



	public void	salvarObjetos()  {
		//gravar nos arquivos csv os objetos que estão no repositório
		
		try	{
			File f = new File( new File(".\\mensagens.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			for(Mensagem m : mensagens.values()) 	{      //mensagens.values() e nao mensagens!!!!
				arquivo1.write(	m.getId()+";"+
						m.getEmitente().getNome()+";"+
						m.getDestinatario().getNome()+";"+
						m.getTexto()+"\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na criação do arquivo  mensagens "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\individuos.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			for(Individual ind : this.getIndividuos()) {
				arquivo2.write(ind.getNome() +";"+ ind.getSenha() +";"+ ind.getAdministrador() +"\n");	
			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  individuos "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\grupos.csv").getCanonicalPath())  ;
			FileWriter arquivo3 = new FileWriter(f) ; 
			for(Grupo g : this.getGrupos()) {
				String texto="";
				for(Individual ind : g.getIndividuos()) {
					texto += ";" + ind.getNome();
				arquivo3.write(g.getNome() + texto + "\n");	
			} 
			arquivo3.close();
			}
		}
		catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  grupos "+e.getMessage());
			}
		
	}


	
	
	
}
