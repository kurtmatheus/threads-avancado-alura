package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class Comando2AcessoBanco implements Callable<String> {

	private PrintStream saida;

	public Comando2AcessoBanco(PrintStream saidaCliente) {
		this.saida = saidaCliente;
	}

	// na classe ComandoC1
	@Override
	public String call() throws Exception {
		// será impresso no console do servidor
		System.out.println("Servidor recebeu comando c2 - Banco");
		saida.println("Processando comando c2 - Banco");
		
		Thread.sleep(15000);
		
		int numero = new Random().nextInt(100) + 1;
		
		// essa mensagem será enviada para o cliente
		this.saida.println("Servidor finalizou comando c2 - Banco");
		
		return Integer.toString(numero);
	}

}
