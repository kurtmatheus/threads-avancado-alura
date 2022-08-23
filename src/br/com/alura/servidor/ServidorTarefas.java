package br.com.alura.servidor;	

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {
	
	private ExecutorService threads;
	private ServerSocket servidor;
	private AtomicBoolean estaRodando;
	private BlockingQueue<String> filaComandos;

	public ServidorTarefas() throws IOException {
		System.out.println("----------- Iniciando Servidor ----------");
		servidor = new ServerSocket(12345);
		threads = Executors.newCachedThreadPool(new FabricaDeThreads());                            //newCachedThreadPool();
		this.estaRodando = new AtomicBoolean(true);
		this.filaComandos = new ArrayBlockingQueue<String>(2);
		iniciarConsumidores();
	}
	
	private void iniciarConsumidores() {
		for (int i = 0; i < 2; i++) {
			new Thread(new TarefaConsumidora(filaComandos)).start();
		}
	}

	public void rodar() throws IOException {
		while (this.estaRodando.get()) {
			try {
				Socket socket = servidor.accept();
				System.out.println("Socket aceito conectado via porta " + socket.getPort());
				DistribuidorDeTarefas distribuidor = new DistribuidorDeTarefas(threads, filaComandos, socket, this);
				Thread distriThread = new Thread(distribuidor);
				threads.execute(distriThread);
			} catch (SocketException e) {
				System.out.println("Socket está rodando: " + this.estaRodando.get());
			}
		}
	}

	public void parar() throws IOException {
		servidor.close();
		threads.shutdown();
		this.estaRodando.set(false);
	}
	
	public static void main(String[] args) throws Exception {		
		
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
	}

}
