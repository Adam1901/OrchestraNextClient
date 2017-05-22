package utils;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

import views.LoginUser;
import views.Main;

public class UpdateThread implements Runnable {

	private LoginUser lu;
	private Main main;

	public UpdateThread(LoginUser lu, Main main) {
		this.lu = lu;
		this.main = main;
	}

	@Override
	public void run() {
		// Get server version
		
		GetRequest lil = Unirest.head("http://:18080/workstationterminal/scripts/chat.js").basicAuth(lu.getUsername(),
				lu.getPassword());
		
		
		
		System.out.println(lil.getBody());
		throw new RuntimeException("NYI");

	}

}
