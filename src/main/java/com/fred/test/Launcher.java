package com.fred.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

/**
 * @author Frédéric Massart
 */
public class Launcher {

	public static void main(String[] args) {
		Launcher launcher = new Launcher();
		launcher.contactGoogle();
	}

	private void contactGoogle() {
		try {
			Socket socket = new Socket("www.google.com", 80);
			System.out.println("Local port of the socket : " + socket.getLocalPort());
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer.println("GET /");
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			System.out.println(socket);
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
