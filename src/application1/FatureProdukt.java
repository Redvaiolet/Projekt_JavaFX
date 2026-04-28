package application1;

//nuk kerkon modifikim  
//klasa fatura per te ruajtur te dhenat ne fature
//me metoden final jane te pandryshueshme te dhenat
public class FatureProdukt {
    private final String emri;
    private final int sasia;
    private final String cmimiShitjes;

    //konstruktor 
    public FatureProdukt(String emri, int sasia, String cmimiShitjes) {
        this.emri = emri;
        this.sasia = sasia;
        this.cmimiShitjes = cmimiShitjes;
    }
 
    //getters per te  lexuar obj e kesaj klase 
    public String getEmri() { return emri; }
    public int getSasia() { return sasia; }
    public String getCmimiShitjes() { return cmimiShitjes; }
}
