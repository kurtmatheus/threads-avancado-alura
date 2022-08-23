package br.com.alura.cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefa {
	public static void main(String[] args) throws Exception {
		
		Socket socket = new Socket("localhost", 12345);
		
		if (socket.isConnected()) {
			System.out.println("conexão estabelecida");
		}
		
		
		Thread threadEnviaComando = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("----Enviando comandos para o servidor-----");
					PrintStream saida = new PrintStream(socket.getOutputStream());
					Scanner entradaComandos = new Scanner(System.in);	
					while (entradaComandos.hasNextLine()) {
						
						String next = entradaComandos.nextLine();
						if (next.trim().equals("")) {
							break;
						}
						
						saida.println(next);
					}
					
					saida.close();
					entradaComandos.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}				
			}
			
		});
		
		Thread threadRecebeRespostaServidor = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("----Recebendo dados servidor-----");
					Scanner respostaServidor = new Scanner(socket.getInputStream());
					
					while (respostaServidor.hasNextLine()) {
						String next = respostaServidor.nextLine();
						System.out.println(next);
					}
					
					respostaServidor.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}				
			}
			
		});
		
		threadRecebeRespostaServidor.start();
		threadEnviaComando.start();
		
		
		threadEnviaComando.join();
		
		socket.close();
	}
}
