package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class futureWSfutureAB implements Callable<Void> {

	private Future<String> futureWS;
	private Future<String> futureAB;
	private PrintStream saidaCliente;

	public futureWSfutureAB(Future<String> futureWS, Future<String> futureAB, PrintStream saidaCliente) {
		this.futureWS = futureWS;
		this.futureAB = futureAB;
		this.saidaCliente = saidaCliente;
	}

	@Override
	public Void call() {
		System.out.println("Aguardando resultados do future WS e AB");
		
		try {
			String numeroMagico = this.futureWS.get(10, TimeUnit.SECONDS);
			String numeroMagico2 = this.futureAB.get(10, TimeUnit.SECONDS);
			
			this.saidaCliente.println("Resultado processamento comando c2:" + numeroMagico + ", " + numeroMagico2);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			System.out.println("Timeout: Cacelando comando c2");
			this.saidaCliente.println("Timeout: Execucao comando c2");
			this.futureWS.cancel(true);
			this.futureAB.cancel(true);
			
		}
		
		System.out.println("Finalizou Comando de União de futures");
		
		return null;
	}

}
