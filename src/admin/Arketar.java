package admin;

//klase Arketare Relationship:Agregation, One-to-Many (shume arketare mund te rregjistrohen ne tabelne e Adminit/Sistem)
public class Arketar {
	private String emri;
	private String datelindja;
	private String telefoni;
	private String emaili;
	private String paga;
	private String nivel_Aksesi;
	private String Password;
	
    //konstruktor
	public Arketar(String emri, String datelindja, String telefoni, String emaili, String paga, String nivel_Aksesi, String Password) {
		this.emri = emri;
		this.datelindja = datelindja;
		this.telefoni = telefoni;
		this.emaili = emaili;
		this.paga = paga;
		this.nivel_Aksesi = nivel_Aksesi;
		this.Password = Password;
	}
	
    //getters per lexim 
	public String getEmri() {
		return emri;
	}

	public String getDatelindja() {
		return datelindja;
	}

	public String getTelefoni() {
		return telefoni;
	}

	public String getPaga() {
		return paga;
	}

	public String getEmaili() {
		return emaili;
	}

	public String getNivel_Aksesi() {
		return nivel_Aksesi;
	}

	public String getPassword() {
		return Password;
	}	
		
}


