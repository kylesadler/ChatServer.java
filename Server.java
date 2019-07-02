import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Server {
	
	private ArrayList<Socket> clients;
	private ServerSocket server;
	
	public static void main(String[] args) throws IOException{
		Server server = new Server();
		server.run();
	}
	
	public Server() throws IOException{
		server = new ServerSocket(5000);
		clients = new ArrayList<Socket>();
	}
	
	public void run() throws IOException{
		System.out.println("waiting...");
		
		while(true){
			Thread accept = new Thread(new Runnable(){
				public void run(){
					try{accept();} catch(IOException e){}
				}
			});
			Thread serve = new Thread(new Runnable(){
				public void run(){
					try{serve();} catch(IOException e){}
				}
			});
			
			
			accept.start();
			serve.start();
		}
	}
	
	public void accept() throws IOException{
		// System.out.println("accepting");
		Socket client = server.accept();
		System.out.println(client.getRemoteSocketAddress().toString().split("/")[1] + " connected to server!");
		clients.add(client);
		
	}
	public void serve() throws IOException{
		//System.out.println("serving " + clients.size());
		for(Socket client : this.clients){
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			String input = br.readLine();
			if(input == "exit" || input == "logout"){
				logout(client);
			}else if(input != null){
				messageAllClients(input);
			}
			
			br.close();
		}
	}		
	
	public void messageAllClients(String message) throws IOException{
		for(Socket client : this.clients){
			PrintWriter pw = new PrintWriter(client.getOutputStream());
			pw.println(message);
			pw.flush();
			pw.close();
		}
	}
	
	public void logoutAll() throws IOException{
		for(Socket client : this.clients){
			logout(client);
		}
	}
	
	public void logout(Socket client) throws IOException{
		client.close();
	}
}