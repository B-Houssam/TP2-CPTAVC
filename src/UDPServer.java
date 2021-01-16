import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPServer {
	/*
	 * Author Bousri Houssam
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			System.out.println("SERVER RUNNING ...\n");
			DatagramSocket c = new DatagramSocket(2001);
			
			byte[] sendData = new byte[10];
			byte[] ReceiveData = new byte[10];
			
			while (true) {
				
				DatagramPacket ReceivePacket = new DatagramPacket(ReceiveData, ReceiveData.length);
				c.receive(ReceivePacket);
				
				//extraction
				String sentence = new String(ReceivePacket.getData());
				InetAddress IPAdress = ReceivePacket.getAddress();
				int port = ReceivePacket.getPort();
				
				//affichage
				System.out.print("Received sentence: '" + sentence.trim() + "'");
				System.out.println(" From Server: IP: " + IPAdress + " / Port: "+ port);
				
				//modification et renvoie
				
				String upSentence = new String(sentence.trim().toUpperCase());
				
				System.out.println("MODIFICATION and SENDING " + upSentence + " ...\n");
				
				sendData = upSentence.getBytes();
				
				DatagramPacket SendPacket = new DatagramPacket(sendData, sendData.length, IPAdress, port);
				c.send(SendPacket);
				
				
				//renitialiser le array du byte
				ReceiveData = new byte[10];
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception: " + e.toString());
			ExceptionSave s = new ExceptionSave();
			s.save(e);
		}
	}

}
