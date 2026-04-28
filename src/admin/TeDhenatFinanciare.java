package admin;

//Per Tabelen Te dhenat,final sespe nuk duam qe te ndryshohen el gjate aplikacionit qe qendron i hapur 
public class TeDhenatFinanciare {
   private final double teArdhurat;
   private final double kostot;
   
   //konstruktor 
   public TeDhenatFinanciare(double teArdhurat, double kostot) {
	this.teArdhurat = teArdhurat;
	this.kostot = kostot;
}

 //metoda getter per te lexuar te dheant   
public double getTeArdhurat() {
	return teArdhurat;
}

//getter 
public double getKostot() {
	return kostot;
}
   
}
