package br.com.alura.servidor;

import java.io.PrintStream;

public class Comando1 implements Runnable{

	private PrintStream saida;

	public Comando1(PrintStream saidaCliente) {
		this.saida = saidaCliente;
	}

	//na classe ComandoC1
	@Override
	public void run() {
	    //será impresso no console do servidor
	    System.out.println("Executando comando c1"); 

	    try {
	        Thread.sleep(5000);
	    } catch (InterruptedException e) {
	        throw new RuntimeException(e);
	    } 

	    //essa mensagem será enviada para o cliente
	    this.saida.println("Comando c1 executado com sucesso!"); 
	}

}
