package admin;

//klasa Stafi ku ruhen te gjithe pjestarest e biznesit (Arketar dhe Menaxher) te tabeln Stafi
//ne public sepse duhet qe te dhenat te ruheen edhe pas hapjes se nje dritaje tjeter
//me metoden final jane te pandryshueshme te dhenat (nuk fshihen) /rueht aktuali derisa eshte i hapur programi 
public class Stafi {
	private final String Emri;
	private final String Datelindja;
	private final String Telefoni;
	private final String Emaili;
	private final String Paga;
	private final String nivel_Aksesi;
	private final String Password;

	// konstruktor
	public Stafi(String Emri, String Datelindja, String Telefoni, String Emaili, String Paga, String nivel_Aksesi,
			String Password) {
		this.Emri = Emri;
		this.Datelindja = Datelindja;
		this.Telefoni = Telefoni;
		this.Emaili = Emaili;
		this.Paga = Paga;
		this.nivel_Aksesi = nivel_Aksesi;
		this.Password = Password;
	}
    
	//getters per te lexuar obj e klases,pike aksesi 
	public String getEmri() {
		return Emri;
	}

	public String getDatelindja() {
		return Datelindja;
	}

	public String getTelefoni() {
		return Telefoni;
	}

	public String getEmaili() {
		return Emaili;
	}

	public String getPaga() {
		return Paga;
	}

	public String getNivel_Aksesi() {
		return nivel_Aksesi;
	}

	public String getPassword() {
		return Password;
	}
}
