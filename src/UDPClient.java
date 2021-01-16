import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
	/*
	 * Author Bousri Houssam
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			DatagramSocket c = new DatagramSocket();
			
			//une classe pour représenter une adrees Ip 
			//return une IP@ with the name localhost
			InetAddress IPAdress = InetAddress.getByName("localhost");
			
			System.out.println("Enter sentence to send :");
			Scanner sc = new Scanner(System.in);
			String sentence = sc.nextLine();
			
			byte[] sendData = new byte[10];
			byte[] ReceiveData = new byte[10];
			
			//trouver combien de packets on a besoin
			int nbPackets = sentence.length() % 10 == 0 ? sentence.length()/10 : ((int)sentence.length()/10 +1);
			
			String sub = "";
			DatagramPacket sendPacket = null;
			
			System.out.println("\nSENDING...");
			
			for (int i = 0; i < nbPackets; i++) {
				
				if (i == nbPackets-1) {
					sub = sentence.substring(i*10, sentence.length());
					sendData = sub.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAdress, 2001);
					c.send(sendPacket);
					System.out.println("Packet "+ (i+1) + " sending: " + sub);
				}else {
					sub = sentence.substring(i*10, (i+1)*10);
					sendData = sub.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAdress, 2001);
					c.send(sendPacket);
					System.out.println("Packet "+ (i+1) + " sending: " + sub);
				}
				
				//renitialiser le contennue de notre buffer/array de byte
				sendData = new byte[10];
				
			}
			
			System.out.println("\nRECEIVING...");
			
			String all = "";
			
			for (int i = 0; i < nbPackets; i++) {
				
				//création du packet pour la réception
				DatagramPacket receivePacket = new DatagramPacket(ReceiveData, ReceiveData.length);
				c.receive(receivePacket);
				
				//extraction
				String ms = new String(receivePacket.getData());
				
				//affichage apres modification
				System.out.print("From Server: IP: " + receivePacket.getAddress() + " / Port: "+ receivePacket.getPort());
				System.out.println(" After modification: '" + ms.trim() + "'");
				
				all = all+ms.trim();
				
				//renitialiser le contennue de notre buffer/array de byte
				ReceiveData = new byte[10];
					
			}
			
			///fermeture de datagramSocket
			c.close();
			
			System.out.println("\n  " + all.replaceAll("_", " "));
			
			System.out.println("\n---> Done");
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception: " + e.toString());
			ExceptionSave s = new ExceptionSave();
			s.save(e);
		}

	}

}
