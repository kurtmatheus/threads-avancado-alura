package br.com.alura.servidor;

import java.util.concurrent.ThreadFactory;

public class FabricaDeThreads implements ThreadFactory {
	
	private static int numero = 1;
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, " Servidor de Tarefas " + numero);
		
		System.out.println("Thread Factory Instanciado: Thread número " + numero++);
		
		t.setUncaughtExceptionHandler(new TratamentoDeExcecao());
	    t.setDaemon(true);
	    return t;
	}

}
