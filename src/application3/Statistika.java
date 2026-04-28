package application3;


//klase per tabelen e statistikes ne public 
//ne public sepse duhet qe te dhenat te ruheen edhe pas hapjes se nje dritaje tjeter
//me metoden final jane te pandryshueshme te dhenat (nuk fshihen) /rueht aktuali  derisa eshte i hapur programi
public class Statistika{
	private final String CD;
	private final int CD_te_shitur;
	private final int CD_te_blere;
	
	//konstruktor 
	public Statistika(String CD,int CD_te_shitur,int CD_te_blere) {
		this.CD = CD;
		this.CD_te_shitur = CD_te_shitur;
		this.CD_te_blere = CD_te_blere;
	
	}

	//metodat getter per te lexuar produktet
	public String getCD() { return CD; }

	public int getCD_te_shitur() { return CD_te_shitur; }

	public int getCD_te_blere() { return CD_te_blere; }  
}