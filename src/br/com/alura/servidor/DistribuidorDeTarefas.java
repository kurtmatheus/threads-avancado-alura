package br.com.alura.servidor;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuidorDeTarefas implements Runnable {
	
	private Socket socket;
	private ServidorTarefas servidor;
	private ExecutorService threads;
	private BlockingQueue<String> filaComandos;

	public DistribuidorDeTarefas(ExecutorService threads, BlockingQueue<String> filaComandos, Socket socket, ServidorTarefas servidor) {
		this.threads = threads;
		this.filaComandos = filaComandos;
		this.socket = socket;
		this.servidor = servidor;
	}

	@Override
	public void run() {
		try {
			System.out.println("Distribuindo as tarefas para o cliente " + socket);

	        Scanner entradaCliente = new Scanner(socket.getInputStream());
	        PrintStream saidaCliente = new PrintStream(socket.getOutputStream());

	        while (entradaCliente.hasNextLine()) {

	            String comando = entradaCliente.nextLine();
	            System.out.println("Comando recebido " + comando);

	            switch (comando) {
	                case "c1": 
	                    saidaCliente.println("Confirmação do comando c1");
	                    Comando1 c1 = new Comando1(saidaCliente);
	                    this.threads.execute(c1);
	                    break;
	                case "c2": 
	                    saidaCliente.println("Confirmação do comando c2");
	                    Comando2WebService c2WS = new Comando2WebService(saidaCliente);
	                    Comando2AcessoBanco c2AB = new Comando2AcessoBanco(saidaCliente);
	                    Future<String> futureWS = this.threads.submit(c2WS);
	                    Future<String> futureAB = this.threads.submit(c2AB);
	                    
	                    this.threads.submit(new futureWSfutureAB(futureWS, futureAB, saidaCliente));
	                    
	                    break;
	                case "c3":
	                	this.filaComandos.put("c3");
	                	saidaCliente.println("Comando c3 adicionado na fila");
	                	servidor.parar();
	                	break;
	                case "fim":
	                	saidaCliente.println("Saindo do Servidor");
	                	servidor.parar();
	                	break;
	                default: 
	                    saidaCliente.println("Comando não encontrado");
	            }

	            System.out.println(comando);
	        }

	        saidaCliente.close();
	        entradaCliente.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
