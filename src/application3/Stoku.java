package application3;

//klase e tabels per stokun 
//nuk nje final sepse do behen ndryshime konstante
public class Stoku {
		private String Emri;
		private String Zhaneri;
		private String Kengetari;
		private String Data;
		private String CmimiB;
		private String CmimiSh;
		private String Sasia; //-->modifikimi i sasise dhe shtojme nje set per kete sepse do ndryshoj sasia e stokut shpesh

		// konstruktor
		public Stoku(String Emri, String Zhaneri, String Kengetari, String Data, String CmimiB,String CmimiSh,String Sasia) {
		 this.Emri = Emri;
		 this.Zhaneri = Zhaneri;
		 this.Kengetari = Kengetari;
		 this.Data = Data;
		 this.CmimiB = CmimiB;
		 this.CmimiSh = CmimiSh;
		 this.Sasia = Sasia;

		}

		//metodat gettters
		public String getEmri() { return Emri; }

		public String getZhaneri() { return Zhaneri; }

		public String getKengetari() { return Kengetari; }

		public String getData() { return Data; }

		public String getCmimiB() { return CmimiB; }

		public String getCmimiSh() { return CmimiSh; }

		public String getSasia() { return Sasia; }
		
		
		//shtojm metode setSasia
		 public void setSasia(String Sasia) {
			 this.Sasia = Sasia;
		 }
		 
		 
		 //mbishkrimi per setSasine nga string ne int 
		 //kur te behen ndryshime ne sasi
		 public void setSasia(int Sasia) { //Overload per int 
			 this.Sasia = String.valueOf(Sasia);
		 }
		
}
