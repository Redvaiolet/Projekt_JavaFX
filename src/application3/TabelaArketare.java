package application3;

//klasa e tabels per arketaret 
//ne public sepse duhet qe te dhenat te ruhen edhe pas hapjes se nje dritaje tjeter
//me metoden final jane te pandryshueshme te dhenat (nuk fshihen) /rueht aktuali derisa eshte i hapur programi 
public class TabelaArketare{
	private final String Emri;
	private final int Nr_fatura;
	private final int Nr_CDShitur;
	private final double ShumaLekeve;
	
	//konstruktor 
	public TabelaArketare(String Emri, int Nr_fatura, int Nr_CDShitur, double ShumaLeve) {
		this.Emri = Emri;
		this.Nr_fatura = Nr_fatura;
		this.Nr_CDShitur = Nr_CDShitur;
		this.ShumaLekeve = ShumaLeve;
	}

	//getters per te lexuar objektet e klases
	public String getEmri() { return Emri; }

	public int getNr_fatura() { return Nr_fatura; }

	public int getNr_CDShitur() { return Nr_CDShitur; }

	public double getShumaLekeve() { return ShumaLekeve; }
	
}