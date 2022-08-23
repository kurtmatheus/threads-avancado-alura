package br.com.alura.servidor;

import java.util.concurrent.BlockingQueue;

public class TarefaConsumidora implements Runnable {

	private BlockingQueue<String> filaComandos;

	public TarefaConsumidora(BlockingQueue<String> filaComandos) {
		this.filaComandos = filaComandos;
	}

	@Override
	public void run() {
		try {
			String comandos = null;
			while((comandos = filaComandos.take()) != null) {
				System.out.println("Consumindo comando c3" + " - " + Thread.currentThread().getName());
				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
				
	}

}
