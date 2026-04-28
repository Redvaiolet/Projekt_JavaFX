package admin;

import java.util.ArrayList;
import java.util.List;

//klasa Admin
public class Admin {
	private String Username;
	private String Password;
	
	//One-to-Mnay per Arketaret dhe One-to-Many per Menaxherat 
	private List<Arketar> lista_Arketaret = new ArrayList<>();
	private List<Menaxher> lista_Menaxheret = new ArrayList<>();
	
	//Username dhe Password per adminin 
	public Admin(String Username, String Password) {
		this.Username = "admin12";
		this.Password = "123@45";
	}

	//metoda getters per lexim 
	public String getUsername() {
		return Username;
	}
    
	public String getPassword() {
		return Password;
	}

	//metoda getter per te lexuar listen e arketareve dhe menaxhereve
	public List<Arketar> getLista_Arketaret() {
		return lista_Arketaret;
	}

	public List<Menaxher> getLista_Menaxheret() {
		return lista_Menaxheret;
	}
	
}
