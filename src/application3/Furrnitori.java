package application3;

import java.util.ArrayList;
import java.util.List;


//klase per te shenuar llojet e perodukteve
//ne public sepse duhet qe te dhenat te ruheen edhe pas hapjes se nje dritaje tjeter
//nuk jane final sepse do te ndryshohen shpesh
public class Furrnitori {
   private String Emri;
   private String Zhaneri;
   private String Kengetari;
   private String Data;
   private String Cmimi;
   
   //lista qe ruan produktet
   private static List<Furrnitori> produktet = new ArrayList<>();

   
   //konstruktor
   public Furrnitori(String Emri, String Zhaneri, String Kengetari, String Data, String Cmimi){
      this.Emri = Emri;
      this.Zhaneri = Zhaneri;
      this.Kengetari = Kengetari;
      this.Data = Data;
      this.Cmimi = Cmimi;
	   
   }


public String getEmri() {
	return Emri;
}


public String getZhaneri() {
	return Zhaneri;
}


public String getKengetari() {
	return Kengetari;
}


public String getData() {
	return Data;
}


public String getCmimi() {
	return Cmimi;
}
   
  
 
 //metode getter per te marre listen e produkteve 
 public static List<Furrnitori> getProduktet() {
     return produktet;
 }
 
 
//metoda per te mbushur listen me produkte 
public static void mbushProduktet() {                                 //data eshte ajo e blerjse se CD nga Furrnitori
   produktet.add(new Furrnitori("Bohemian Rhapsody", "Rock", "Queen", "23/04/2025", "100 $"));
   produktet.add(new Furrnitori("Rolling in the Deep", "Pop", "Adele", "23/04/2025", "120 $"));
   produktet.add(new Furrnitori("Smells Like Teen Spirit", "Grunge", "Nirvana", "23/04/2025", "180 $"));
   produktet.add(new Furrnitori("Take Five", " Jazz", "Brubeck", "23/04/2025", "160 $"));
   produktet.add(new Furrnitori("Starboy", "EDM", "The Weeknd", "23/04/2025", "270 $"));
}
 
}

 
   

