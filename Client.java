import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws IOException{
		Scanner scan = new Scanner(System.in);
		Socket s = new Socket("127.0.0.1", 5000);
		System.out.println("connected to server!");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter writer = new PrintWriter(s.getOutputStream());
		
		
		String message = null;
		while(message != "exit"){
			
			// send message
			message = null;
			do {
				message = scan.nextLine();
			}while(message == null);
			writer.println(message);
			writer.flush();
			
			// get response
			String response = reader.readLine();
			System.out.println("server says: "+response);
		}
		
	}
}