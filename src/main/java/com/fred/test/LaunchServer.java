package com.fred.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Frédéric Massart
 */
public class LaunchServer {

	static ServerSocket serverSocket;
	static int nbOfConn = 0;

	public static void main(String[] args) {

		LaunchServer ls = new LaunchServer();
		try {
			serverSocket = ls.start();
		} catch (IOException e) {
			System.out.println("Error while creating the server");
			e.printStackTrace();
		}
		while (true) {
			try {
				final Socket accept = serverSocket.accept();
				nbOfConn++;
				System.out.println("Total of connexion: " + nbOfConn);
				Thread dumpThread = new Thread() {
					@Override
					public void run() {
						LaunchServer ls = new LaunchServer();
						try {
							ls.read(accept);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				dumpThread.setDaemon(true);
				dumpThread.start();

			} catch (IOException e) {
				System.out.println("Error while processing");
				e.printStackTrace();
				break;
			}
		}
		System.out.println("Ending serving socket");
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("Error while closing the socket");
				e.printStackTrace();
			}
		}
	}

	private ServerSocket start() throws IOException {
		System.out.println("Start socket server");
		return new ServerSocket(1025);

	}

	private void read(Socket soc) throws IOException {
		// Un BufferedReader permet de lire par ligne.
		BufferedReader plec = new BufferedReader(
				new InputStreamReader(soc.getInputStream())
		);

		// Un PrintWriter possède toutes les opérations print classiques.
		// En mode auto-flush, le tampon est vidé (flush) à l'appel de println.
		PrintWriter pred = new PrintWriter(
				new BufferedWriter(
						new OutputStreamWriter(soc.getOutputStream())),
				true);

		System.out.println("Prepared for reading on port: " + soc.getPort());


		while(true) {

			char[] buffer = new char[1024];
			int chars;
			while ((chars = plec.read(buffer)) != -1) {
				System.out.print(String.valueOf(buffer, 0, chars)+"\n");
			}
			System.out.println("EOL reached");
			break;
		}
		System.out.println("Close socket and streams");
		plec.close();
		pred.close();
		soc.close();
	}
}
