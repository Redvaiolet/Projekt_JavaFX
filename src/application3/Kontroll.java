package application3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;



//Kontroll per menaxherin me fxml file "dritare_03.fxml
public class Kontroll {
	//array per te mbajtu karakteret speciale te palejuara qe mund te vendos perdoruesi
    private static char[] karakter_special = { '!', '@', '€', '#', '%', '&', '*', '(', ')','-' ,'_', '=', '[',']', '{', '}', ';', ':', '"', '<', '>', '?', '\\', '|', '~', '§', 'ß'};
    
    //per sasine (per numer negativ)
    private static char[] karakter_special2 = { '!', '@', '€', '#', '%', '&', '*', '(', ')', '_', '=', '[',']', '{', '}', ';', ':', '"', '<', '>', '?', '\\', '|', '~', '§', 'ß'};

    
    @FXML private Button Shto, Perditeso, ShtoCD, PerditesoCD, kthehuLogIn;
    @FXML private TableColumn<Furrnitori, String> furrBlerja, furrData, furrEmer, furrKategori, furrZhaner;
    @FXML private TableView<Furrnitori> furrnitori;
    @FXML private TableColumn<Stoku, String> stokEmri, stokZhaneri, stokKengetari, stokData, stokBlerja, stokShitja, stokSasia;
    @FXML private TableView<Stoku> stoku;
    @FXML private TextField txtEmri, txtZhaneri, txtKengetari, txtData, txtBlerja, txtShitja, txtSasia;

    //static fianl-> nuk lejon ndryshimin derisa aplikacioni eshte i hapur,ndersa static ->per akses nga klasat e tjera
    //lista e furrnitorit 
    private static final ObservableList<Furrnitori> listaProduktesh = FXCollections.observableArrayList();
   
    //lista e stokut 
    private static final ObservableList<Stoku> listaStoku = FXCollections.observableArrayList();
   
    //lista e statistikave 
    private static final ObservableList<Statistika> listaStatistika = FXCollections.observableArrayList();//lista per Statistika
    
    //lista e arketareve 
    private static final ObservableList<TabelaArketare> listaTabelaArketare = FXCollections.observableArrayList();
    
    //getter per te lexuar listen e Arketareve (do perdoret nga jashte qe do na duhet per te shtuar nr.fatureve,nr. e cd dhe totlekeve)
    public static ObservableList<TabelaArketare> getListaTabelaArketare() {
        return listaTabelaArketare;
    }
    
    //per te lexuar listen e stokut
    public static ObservableList<Stoku> getListaStoku() {
        return listaStoku;
    }
    
    //per te lexuar liste e Statistikave 
    public static ObservableList<Statistika> getListaStatistika() {
		return listaStatistika;
	}

    
   //metoda qe inicializon tabelat me qelizat perkatese 
	public void initialize() {
		//mbushe Furrnitorin me disa CD fillestare
    	if (listaProduktesh.isEmpty()) {
            Furrnitori.mbushProduktet();
            listaProduktesh.setAll(Furrnitori.getProduktet());
        }
        
    	//inicializim i tabels  Furrnitori
    	//emri i qelize/kolones e tabels -->qe i shenojme nje vlere qe do mbushet me gettersat perkates
        furrEmer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmri()));
        furrZhaner.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZhaneri()));
        furrKategori.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKengetari()));
        furrData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getData()));
        furrBlerja.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCmimi()));
        //bejme lidhjen e tabels me ObservableList
        furrnitori.setItems(listaProduktesh);
        //shtojme nje listener qe do te bushe fushat e textfields kur likohet nje element ne tabelen Furrnitor ne shprehje lambda
        furrnitori.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> Ploteso_TextFields(newValue));

        //e njejta gje edhe per tabeln e stokut inicializim
        stokEmri.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmri()));
        stokZhaneri.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZhaneri()));
        stokKengetari.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKengetari()));
        stokData.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getData()));
        stokBlerja.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCmimiB()));
        stokShitja.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCmimiSh()));
        stokSasia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSasia()));
       //bejme lidhjen e tabels me ObservableList
        stoku.setItems(listaStoku);
        //shtojme nje listener per stokun ne shprehje lambda
        stoku.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> Ploteso_TextFields_Stoku(newValue));
        
        
        //inicializimi per tabelen e statistikave
        staCD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCD()));
        staTeBlere.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCD_te_blere()));
        staTeShitur.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCD_te_shitur()));
       
        //lidhja e tabeles me listen
        tabelaStatistika.setItems(listaStatistika);
        
        //konfigurim per TabelaArketare 
        arkEmri.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmri()));
        arkFatura.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNr_fatura()));
        arkCD.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNr_CDShitur()));
        
        //merr vleren dhe e formatojme ne String me $ pas 
        arkLeke.setCellValueFactory(cellData -> {
            //merr vleren double nga objekti TabelaArketare
            double shuma = cellData.getValue().getShumaLekeve();
            //formatoje ne String me dy shifra pas presjes dhe shenjen $ ne fund
            String formattedShuma = String.format("%.2f $", shuma);
            //ktheje si property qe e kupton TableColumn<TabelaArketare, String> arkLeke
            return new SimpleStringProperty(formattedShuma);
        });
        
        //lidh tabelen me listen statike
        tabelaArketar.setItems(listaTabelaArketare);
        
        
        //llogarit totalet fillestare (pasi tabela eshte lidhur)
        updateTotalStats();
        
        
        //kontrollo gjendjen e stokut sapo hapet programi
        kontrolloStokun();
    }
    
    //metode ndihmese per te shfqur alertet ne vendn qe te shkruhej metode per metode dhe thirret kjo metode
    public void showAlertDialog(AlertType alertType, String title, String content) {
    	//per audio
    	AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Background.wav");
    	clip.play();
    	
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait(); //shfaq alertin dhe pret perdoruesin qe ta mbylle ate
    }
    
    
    
    @FXML
    public void handleShto() {//metoda per te shtur produkt ne stok
        // Merr vlerat nga TextFields per Stoku
        String emri = txtEmri.getText();
        String zhaneri = txtZhaneri.getText();
        String kengetari = txtKengetari.getText();
        String data = txtData.getText();
        String cmimiBlerjes = txtBlerja.getText();
        String cmimiShitjes = txtShitja.getText();
        String sasiaStr = txtSasia.getText();
        
        //kontroll per emrin e CD
	    if (emri == null || emri.length() == 0) {
		   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
		   return; //per te ndalur veprimet 
		}
   	 
	    //kontrollo per karaktere speciale dhe numra
		for (int i = 0; i < emri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			 char c = emri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

			 //kontrollon per numra
			 if (c >= '0' && c <= '9') {
				showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete numra.");
				return;
			 }

			 //kontrollon per karaktere speciale
			 for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
				if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
					showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete karaktere speciale.");
					return; //per te ndalur veprimet 
				}
			  }
		 }
   	      
		
		 //kontroll per zhanerin e CD
		 if (zhaneri == null || zhaneri.length() == 0) {
			   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			   return; //per te ndalur veprimet 
		 }
	   	 
		 //kontrollo per karaktere speciale dhe numra
	     for (int i = 0; i < zhaneri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			  char c = zhaneri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

			  //kontrollon per numra
			  if (c >= '0' && c <= '9') {
				   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te kete numra.");
				   return;
			  }

			  //kontrollon per karaktere speciale
			  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
						showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te kete karaktere speciale.");
						return; //per te ndalur veprimet 
					}
			  }
		 }
         
         
	   //kontroll per kengetarin e CD
		if (kengetari == null || kengetari.length() == 0) {
			   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			   return; //per te ndalur veprimet 
		 }
	   	 
		 //kontrollo per karaktere speciale dhe numra
	     for (int i = 0; i < kengetari.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			  char c = kengetari.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

			  //kontrollon per numra
			  if (c >= '0' && c <= '9') {
				   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te kete numra.");
				   return;
			  }

			  //kontrollon per karaktere speciale
			  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
						showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te kete karaktere speciale.");
						return; //per te ndalur veprimet 
					}
			  }
		 }
        
        
	   //kontroll per daten e blerjes se CD
		if (data == null || data.length() == 0 || data.length() > 10) {
			showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te lihet bosh ose te jete me e madhe se 10 karaktere.Ju lutemi plotesojeni.");
			return;
		}

		//kontrollo per karaktere speciale dhe shkronjat
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);

			//kontroll per shkronja
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te kete shkronja.");
                  return;
             }
         
			//kontrollon per karaktere speciale
			for (char karakter : karakter_special) {
				if (c == karakter) {
					 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te kete karaktere speciale.");
					 return;
				}
			}
		}
        
		
		//kontroll per Cmimin e blerjes se CD
		if (cmimiBlerjes == null || cmimiBlerjes.length() == 0) {
			showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			return;
		}

		//kontrollo per karaktere speciale dhe shkronjat
		for (int i = 0; i < cmimiBlerjes.length(); i++) {
			char c = cmimiBlerjes.charAt(i);

			//kontroll per shkronja
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te kete shkronja.");
                  return;
             }
         
			//kontrollon per karaktere speciale
			for (char karakter : karakter_special) {
				if (c == karakter) {
					 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te kete karaktere speciale.");
					 return;
				}
			}
		}
		
		
		//kontroll per Cmimin e shitjes se CD
		if (cmimiShitjes == null || cmimiShitjes.length() == 0) {
			showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i shitjes nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			return;
		}

		//kontrollo per karaktere speciale dhe shkronjat
		for (int i = 0; i < cmimiShitjes.length(); i++) {
			char c = cmimiShitjes.charAt(i);

			//kontroll per shkronja
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i shitjes nuk mund te kete shkronja.");
                  return;
             }
         
			//kontrollon per karaktere speciale
			for (char karakter : karakter_special) {
				if (c == karakter) {
					 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i shitjes nuk mund te kete karaktere speciale.");
					 return;
				}
			}
		}
		
		
		//kontroll per Cmimin e shitjes se CD
		if (sasiaStr == null || sasiaStr.length() == 0) {
			showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Sasia nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			return;
		}

		//kontrollo per karaktere speciale dhe shkronjat
		for (int i = 0; i < sasiaStr.length(); i++) {
			char c = sasiaStr.charAt(i);

			//kontroll per shkronja
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Sasia nuk mund te kete shkronja.");
                  return;
             }
         
			//kontrollon per karaktere speciale
			for (char karakter : karakter_special2) {
				if (c == karakter) {
					 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Sasia nuk mund te kete karaktere speciale.");
					 return;
				}
			}
		}
		
		
        //kontroll nese sasia eshte numer negativ
        int sasiaInt;
        try {
         sasiaInt = Integer.parseInt(sasiaStr);
         if(sasiaInt <= 0) {
        	 showAlertDialog(AlertType.WARNING,"Gabim!","Sasia duhet te jete nje numer pozitiv.");
             return; 
         }
        }catch(NumberFormatException e) { //nqs aaray i karaltereve speciale2 si kap te gjitha gabimet
        	showAlertDialog(AlertType.WARNING,"Gabim!","Sasia duhet te jete nje numer.");
            return; //return ketu qe te mos vazhdoje kodi nqs ka gb
        }
         
         
        //krijojme  nje objekt te ri Stoku dhe shto ne tabelen e stokut
        Stoku produkt = new Stoku(emri, zhaneri, kengetari, data, cmimiBlerjes, cmimiShitjes, sasiaStr);
        listaStoku.add(produkt);
        
        
        
        //pjesa ku shtohen te dhenat ne tabeln Statistika duket thirrur metoden,0 sepse ne fillim kolona e CD te shitur eshte bosh
        updateStatistika(emri, sasiaInt, 0); //sepse nuk kemi shitur asnje CD
        
        

        //pastrim i textField pas shtimit
        txtEmri.clear();
        txtZhaneri.clear();
        txtKengetari.clear();
        txtData.clear();
        txtBlerja.clear();
        txtShitja.clear();
        txtSasia.clear();
        
        //per refresh te tabelave pas cdo CD te shitur sepse ato dueht te perditesohen me ngjarjet e reja 
        stoku.refresh();
        tabelaStatistika.refresh();
        
        //tabela e Statistikave
        updateTotalStats(); //rifreskon tabelat e blerjeve/shitjeve
        kontrolloStokun();//ri-kontrollon nivelin e stokut
        
    }
    
    //metoda ndihmese per perditesimin e statistikave (shtim/perditesim)
    public void updateStatistika(String cdEmri, int sasiBlereShtuar, int sasiShiturShtuar) {
    	//kontrollo nese ky CD (cdEmri) ekziston ne listen e statistikave,me nje stream Optional qe perdoret per NullValues,NumberError etj
    	Optional<Statistika> ekziston = listaStatistika.stream().
    			filter(data -> data.getCD().equals(cdEmri)).
    			findFirst();//merr te parin qe gjen
    	
    	if(ekziston.isPresent()) {//rasti 1:CD-ja ekziston ne statistike
    		Statistika ekzistuese = ekziston.get();// merr objektin ekzistues 
    		
    		//llogarit sasite e reja duke shtuar sasite e reja te blera/shitura
    		int blereRe = ekzistuese.getCD_te_blere() + sasiBlereShtuar;
    		int shiturRe = ekzistuese.getCD_te_shitur() + sasiShiturShtuar;
    		
    		//krijo nje objekt te ri Statistika (sepse, fushat jane final) me vlerat e perdituesuar
    		Statistika statRe = new Statistika(ekzistuese.getCD(), shiturRe, blereRe);
    		
    		//gjej indeksin e objektit te vjeter ne liste
    		int indeks = listaStatistika.indexOf(ekzistuese);
    		
    		//zevendeso objektin e vjeter me te riun ne te njetin pozicion
    		listaStatistika.set(indeks,statRe);
    	}else {//rasti 2:CD-ja nuk ekziston tek statistika
    		//krijojme nje objekt te ri Statistika me te dheant fillestare
    		Statistika statRe = new Statistika(cdEmri, sasiShiturShtuar, sasiBlereShtuar);
    		
    		//shto objektin e ri ne fund te listes se statistikave
    		listaStatistika.add(statRe);
    		
    	}
    	
    }
    
    //metoda per te llogaritur dhe shtuar totalet ne TextField--> Totati i CD te blere dhe Totati i CD te shitur
    public void updateTotalStats() {
    	int totaliBlerje = 0;
    	int totaliShitje = 0;
    	
    	//shuma e vlerave nga lista e statistikave
    	for(Statistika stats : listaStatistika) {
    		totaliBlerje = totaliBlerje + stats.getCD_te_blere();
    		totaliShitje = totaliShitje + stats.getCD_te_shitur();
    	}
    	
    	if(Statistia_te_blere != null) {
    		Statistia_te_blere.setText(String.valueOf(totaliBlerje));
    	}
    	
    	if(Statistika_te_shitur != null) {
    		Statistika_te_shitur.setText(String.valueOf(totaliShitje));
    	}
    	
    }
    
    //metode per te kontrolluar cdo zhaner te stokut (private per mos te bere gabime ne importe)
    private void kontrolloStokun() {
    	//me nje  hashMap ku e para eshte emri i zhanerit dhe i dyti eshte Sasia e atij zhaneri
        Map<String, Integer> stokZhaneriMap = new HashMap<>();
        
        //for-each produkt ne tabeln e stkut,kontrollojm zhaner per zhaner
        for (Stoku produkt : listaStoku) {
            String zhaneri = produkt.getZhaneri();
            int sasia = Integer.parseInt(produkt.getSasia());
            //i bejme te gjithe bashke duke i ruajtur si nje obj te vetem per cdo zhaner dhe me totalin e sasive te tyre 
            stokZhaneriMap.put(zhaneri, stokZhaneriMap.getOrDefault(zhaneri, 0) + sasia);
        }
        
        //kontrollojme per cdo zhaner qe me metoden enteryset marrim  "celes-vlere" pra emrin e zhanerit dhe sasine e tij
        //kjo eshte hashmap specifik
        for (Map.Entry<String, Integer> entry : stokZhaneriMap.entrySet()) {
        	//kontrollojme sasine duke marre vleren me metoden getValue() nqs eshte me pak se 5
            if (entry.getValue() < 5) {
            	//shfaqim nje alert duke thirrur metoden shfaqAlert dhe me getKey() marrim emrin e atij zhaneri
                shfaqAlert(entry.getKey());
            }
        }
    }
    
    //metoda per shfaqjen e nivelit te stokut me nje alert sapo te futet Menaxheri (Alert specifik)
    private void shfaqAlert(String zhaneri) {
    	//per audio
    	AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Notify.wav");
    	clip.play();
    	
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Mungese Stoku");
        alert.setHeaderText("Zhaneri " + zhaneri + " ka me pak se 5 CD!");
        alert.setContentText("Ju lutemi furnizoni kete zhaner.");
        alert.showAndWait();
    }


    
    @FXML
    public void handleSHtoCD() {//metode per te shtuar CD te rinje ne furrnitor
    	String emri = txtEmri.getText();
        String zhaneri = txtZhaneri.getText();
        String kengetari = txtKengetari.getText();
        String data = txtData.getText();
        String cmimiBlerjes = txtBlerja.getText();

        //kontroll per emrin e CD
	    if (emri == null || emri.length() == 0) {
		   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
		   return; //per te ndalur veprimet 
		}
   	 
	    //kontrollo per karaktere speciale dhe numra
		for (int i = 0; i < emri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			 char c = emri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

			 //kontrollon per numra
			 if (c >= '0' && c <= '9') {
				showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete numra.");
				return;
			 }

			 //kontrollon per karaktere speciale
			 for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
				if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
					showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete karaktere speciale.");
					return; //per te ndalur veprimet 
				}
			  }
		 }
   	      
		
		 //kontroll per zhanerin e CD
		 if (zhaneri == null || zhaneri.length() == 0) {
			   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			   return; //per te ndalur veprimet 
		 }
	   	 
		 //kontrollo per karaktere speciale dhe numra
	     for (int i = 0; i < zhaneri.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			  char c = zhaneri.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

			  //kontrollon per numra
			  if (c >= '0' && c <= '9') {
				   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te kete numra.");
				   return;
			  }

			  //kontrollon per karaktere speciale
			  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
						showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te kete karaktere speciale.");
						return; //per te ndalur veprimet 
					}
			  }
		 }
         
         
	   //kontroll per kengetarin e CD
		if (kengetari == null || kengetari.length() == 0) {
			   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			   return; //per te ndalur veprimet 
		 }
	   	 
		 //kontrollo per karaktere speciale dhe numra
	     for (int i = 0; i < kengetari.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
			  char c = kengetari.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

			  //kontrollon per numra
			  if (c >= '0' && c <= '9') {
				   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te kete numra.");
				   return;
			  }

			  //kontrollon per karaktere speciale
			  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
						showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te kete karaktere speciale.");
						return; //per te ndalur veprimet 
					}
			  }
		 }
        
        
	   //kontroll per daten e blerjes se CD
		if (data == null || data.length() == 0 || data.length() > 10) {
			showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te lihet bosh ose te jete me e madhe se 10 karaktere.Ju lutemi plotesojeni.");
			return;
		}

		//kontrollo per karaktere speciale dhe shkronjat
		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);

			//kontroll per shkronja
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te kete shkronja.");
                  return;
             }
         
			//kontrollon per karaktere speciale
			for (char karakter : karakter_special) {
				if (c == karakter) {
					 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te kete karaktere speciale.");
					 return;
				}
			}
		}
        
		
		//kontroll per Cmimin e blerjes se CD
		if (cmimiBlerjes == null || cmimiBlerjes.length() == 0) {
			showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			return;
		}

		//kontrollo per karaktere speciale dhe shkronjat
		for (int i = 0; i < cmimiBlerjes.length(); i++) {
			char c = cmimiBlerjes.charAt(i);

			//kontroll per shkronja
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te kete shkronja.");
                  return;
             }
         
			//kontrollon per karaktere speciale
			for (char karakter : karakter_special) {
				if (c == karakter) {
					 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te kete karaktere speciale.");
					 return;
				}
			}
		}
        
        
        //nqs duam te shtojme nje CD te ri nga dritarja e Menaxherit
        Furrnitori cd_i_Ri = new Furrnitori(emri, zhaneri, kengetari, data, cmimiBlerjes);
        //marrim produktin e ri dhe e vendosim tek tabela Furrnitor 
        Furrnitori.getProduktet().add(cd_i_Ri);
        //dhe e vendosim edhe nje here produketet e tjera qe jane ne klasen Furrnitor 
        listaProduktesh.setAll(Furrnitori.getProduktet());
        //rifresh tabels
        furrnitori.refresh();

        //pastrojme textfields-at
        txtEmri.clear();
        txtZhaneri.clear();
        txtKengetari.clear();
        txtData.clear();
        txtBlerja.clear();
    }
    

    //metode per te plotesuar textfields me te dhenat e selektuar ne tabele (do ishte humbje kohe per ti shkruar qe te gjitha)
  	public void Ploteso_TextFields(Furrnitori produkt) {//me nje parameter qe eshte ai i produktit te selektuar
  	    //kontroll nqs ka produket ne tabele
  		if (produkt != null) {
  	        //ploteson tekstet ne textfields me vlerat e produktit te selektuar
  	        txtEmri.setText(produkt.getEmri());
  	        txtZhaneri.setText(produkt.getZhaneri());
  	        txtKengetari.setText(produkt.getKengetari());
  	        txtData.setText(produkt.getData());
  	        txtBlerja.setText(produkt.getCmimi());
  	    }
  	}
  	
  	//e njeat gje edhe per stokun qe ploteson textfieldsa me produktin e selektuar
  	public void Ploteso_TextFields_Stoku(Stoku produkt) {//me nje parameter qe eshte produkti i zgjedhur 
  	    //kontroll nqs ka produket ne tabele
  		if (produkt != null) {
  	        txtEmri.setText(produkt.getEmri());
  	        txtZhaneri.setText(produkt.getZhaneri());
  	        txtKengetari.setText(produkt.getKengetari());
  	        txtData.setText(produkt.getData());
  	        txtBlerja.setText(produkt.getCmimiB());
  	        txtShitja.setText(produkt.getCmimiSh());
  	        txtSasia.setText(produkt.getSasia());
  	    }
  	}
  	
  	
   //metode per perditesimin e tabeles se stoku   
  	public void peditesoStokun() {
  		 //marrim pozicionin e selektuar te atij produkti
  	     int index = stoku.getSelectionModel().getSelectedIndex();
  	    //marrim  objektin e selektuar per perditesim 
  	     Stoku produktiSelektuar = stoku.getSelectionModel().getSelectedItem(); // Merr objektin e selektuar

  	     //kontrollo nese dicka eshte selektuar
  	     if (produktiSelektuar != null) {
  	         //merr vlerat e reja nga textfields
  	         String emriRi = txtEmri.getText();
  	         String zhaneriRi = txtZhaneri.getText();
  	         String kengetariRi = txtKengetari.getText();
  	         String dataRe = txtData.getText();
  	         String cmimiBlerjesRi = txtBlerja.getText();
  	         String cmimiShitjesRi = txtShitja.getText();
  	         String sasiaReStr = txtSasia.getText();
  	         
  	        //kontroll per emrin e CD
  		    if (emriRi == null || emriRi.length() == 0) {
  			   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
  			   return; //per te ndalur veprimet 
  			}
  	   	 
  		    //kontrollo per karaktere speciale dhe numra
  			for (int i = 0; i < emriRi.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
  				 char c = emriRi.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

  				 //kontrollon per numra
  				 if (c >= '0' && c <= '9') {
  					showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete numra.");
  					return;
  				 }

  				 //kontrollon per karaktere speciale
  				 for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
  					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
  						showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri nuk mund te kete karaktere speciale.");
  						return; //per te ndalur veprimet 
  					}
  				  }
  			 }
  	   	      
  			
  			 //kontroll per zhanerin e CD
  			 if (zhaneriRi == null || zhaneriRi.length() == 0) {
  				   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te lihet bosh.Ju lutemi plotesojeni.");
  				   return; //per te ndalur veprimet 
  			 }
  		   	 
  			 //kontrollo per karaktere speciale dhe numra
  		     for (int i = 0; i < zhaneriRi.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
  				  char c = zhaneriRi.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

  				  //kontrollon per numra
  				  if (c >= '0' && c <= '9') {
  					   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te kete numra.");
  					   return;
  				  }

  				  //kontrollon per karaktere speciale
  				  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
  						if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
  							showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Zhaneri nuk mund te kete karaktere speciale.");
  							return; //per te ndalur veprimet 
  						}
  				  }
  			 }
  	         
  	         
  		   //kontroll per kengetarin e CD
  			if (kengetariRi == null || kengetariRi.length() == 0) {
  				   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te lihet bosh.Ju lutemi plotesojeni.");
  				   return; //per te ndalur veprimet 
  			 }
  		   	 
  			 //kontrollo per karaktere speciale dhe numra
  		     for (int i = 0; i < kengetariRi.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
  				  char c = kengetariRi.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

  				  //kontrollon per numra
  				  if (c >= '0' && c <= '9') {
  					   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te kete numra.");
  					   return;
  				  }

  				  //kontrollon per karaktere speciale
  				  for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
  						if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
  							showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Kengetari nuk mund te kete karaktere speciale.");
  							return; //per te ndalur veprimet 
  						}
  				  }
  			 }
  	        
  	        
  		   //kontroll per daten e blerjes se CD
  			if (dataRe == null || dataRe.length() == 0 || dataRe.length() > 10) {
  				showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te lihet bosh ose te jete me e madhe se 10 karaktere.Ju lutemi plotesojeni.");
  				return;
  			}

  			//kontrollo per karaktere speciale dhe shkronjat
  			for (int i = 0; i < dataRe.length(); i++) {
  				char c = dataRe.charAt(i);

  				//kontroll per shkronja
  				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
  					  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te kete shkronja.");
  	                  return;
  	             }
  	         
  				//kontrollon per karaktere speciale
  				for (char karakter : karakter_special) {
  					if (c == karakter) {
  						 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Data nuk mund te kete karaktere speciale.");
  						 return;
  					}
  				}
  			}
  	        
  			
  			//kontroll per Cmimin e blerjes se CD
  			if (cmimiBlerjesRi == null || cmimiBlerjesRi.length() == 0) {
  				showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te lihet bosh.Ju lutemi plotesojeni.");
  				return;
  			}

  			//kontrollo per karaktere speciale dhe shkronjat
  			for (int i = 0; i < cmimiBlerjesRi.length(); i++) {
  				char c = cmimiBlerjesRi.charAt(i);

  				//kontroll per shkronja
  				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
  					  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te kete shkronja.");
  	                  return;
  	             }
  	         
  				//kontrollon per karaktere speciale
  				for (char karakter : karakter_special) {
  					if (c == karakter) {
  						 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i blerjes nuk mund te kete karaktere speciale.");
  						 return;
  					}
  				}
  			}
  			
  			
  			//kontroll per Cmimin e shitjes se CD
  			if (cmimiShitjesRi == null || cmimiShitjesRi.length() == 0) {
  				showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i shitjes nuk mund te lihet bosh.Ju lutemi plotesojeni.");
  				return;
  			}

  			//kontrollo per karaktere speciale dhe shkronjat
  			for (int i = 0; i < cmimiShitjesRi.length(); i++) {
  				char c = cmimiShitjesRi.charAt(i);

  				//kontroll per shkronja
  				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
  					  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i shitjes nuk mund te kete shkronja.");
  	                  return;
  	             }
  	         
  				//kontrollon per karaktere speciale
  				for (char karakter : karakter_special) {
  					if (c == karakter) {
  						 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Cmimi i shitjes nuk mund te kete karaktere speciale.");
  						 return;
  					}
  				}
  			}
  			
  			
  			//kontroll per Cmimin e shitjes se CD
  			if (sasiaReStr == null || sasiaReStr.length() == 0) {
  				showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Sasia nuk mund te lihet bosh.Ju lutemi plotesojeni.");
  				return;
  			}

  			//kontrollo per karaktere speciale dhe shkronjat
  			for (int i = 0; i < sasiaReStr.length(); i++) {
  				char c = sasiaReStr.charAt(i);

  				//kontroll per shkronja
  				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
  					  showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Sasia nuk mund te kete shkronja.");
  	                  return;
  	             }
  	         
  				//kontrollon per karaktere speciale
  				for (char karakter : karakter_special2) {
  					if (c == karakter) {
  						 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Sasia nuk mund te kete karaktere speciale.");
  						 return;
  					}
  				}
  			}
  			 
  			
  	         //vendi ku behet  llogaritja e diferences
  	         int sasiRe;
  	         int sasiVjeter;

  	         try {
  	             sasiRe = Integer.parseInt(sasiaReStr); //konverto sasine e re
  	             if (sasiRe <= 0) { //sasia nuk mund te jete negative
  	                 showAlertDialog(AlertType.WARNING, "Gabim!", "Sasia nuk mund te jete negative.");
  	                 return;
  	             }
  	             //merr sasine e vjeter nga objekti qe ishte selektuar para perditesimit
  	             sasiVjeter = Integer.parseInt(produktiSelektuar.getSasia());

  	         } catch (NumberFormatException e) {
  	        	 //kontroll nqs sasia e re nuk eshte nje numer 
  	             showAlertDialog(AlertType.WARNING, "Gabim!", "Sasia duhet te jete nje numer.");
  	             return;
  	         }

  	         //llogarisim ndryshimin ne sasi
  	         int sasiDiferenca = sasiRe - sasiVjeter;
  	         //-------------------------------------------

  	         //krijojme objektin e perditesuar Stoku me sasine e re te vendosur
  	         Stoku produktPerditesuar = new Stoku(emriRi, zhaneriRi, kengetariRi, dataRe, cmimiBlerjesRi, cmimiShitjesRi, sasiaReStr);

  	         //vendosim objektin e perditesuar ne listen e stokut ne pozicionin qe u zgjodh ne fillim
  	         listaStoku.set(index, produktPerditesuar);

  	         //perditeso tabelen e statistikave "vetem" nese stok i ri eshte shtuar 
  	         //nese sasia e re eshte me e madhe se e vjetra,atehere diferenca eshte sasia e furnizuar "kjo e reja"
  	         if (sasiDiferenca > 0) {
  	             //sasia u rrit -> u blene/furnizuan me shume CD
  	             //shto diferencen tek totali i CD-ve te blera ne ne tabeln statistika
  	             System.out.println("Furnizim i ri u krye: " + sasiDiferenca + " cope per " + emriRi); //per info
  	             updateStatistika(emriRi, sasiDiferenca, 0); //shtohet vetem tek kolona e Cd-ve te blere
  	         }

  	         //pastro fushat
  	         txtEmri.clear();
  	         txtZhaneri.clear();
  	         txtKengetari.clear();
  	         txtData.clear();
  	         txtBlerja.clear();
  	         txtShitja.clear();
  	         txtSasia.clear();

  	         //rifresko te dyja tabelat
  	         stoku.refresh();
  	         tabelaStatistika.refresh(); //e rendesishme per te shfaqur ndryshimet ne statistika

  	         //rillogarit dhe shfaq totalet
  	         updateTotalStats();

  	         //ri-kontrollo nivelin e stokut
  	         kontrolloStokun();

  	     } else {
  	         showAlertDialog(AlertType.WARNING,"Gabim!" ,"Ju lutem zgjidhni nje produkt per perditesim!");
  	     }
  	 }
  
  	//metode per te perditesuar furrnitorin me nje Cd te re 
  	 public void perditesoFurrnitor() {
  		int index = furrnitori.getSelectionModel().getSelectedIndex();//marrim pozicionin e atij obj te tabels per perdtesim
  	    //nqs indeksi nuk eshte 0/ eshte klikuar diku ne tabele
  		if (index >= 0) {
  			//e perditesojm ate CD qe eshte zgjedhuar 
  	        listaProduktesh.set(index, new Furrnitori(txtEmri.getText(), txtZhaneri.getText(), txtKengetari.getText(), txtData.getText(), txtBlerja.getText()));
  	        
  	        //pastrojm fushat
  	        txtEmri.clear();
  	        txtZhaneri.clear();
  	        txtKengetari.clear();
  	        txtData.clear();
  	        txtBlerja.clear();
  	        txtShitja.clear();
  	        txtSasia.clear();
  	        
  	        //rifresh tabels me ndryshimet e reja 
  	        furrnitori.refresh();
  	    } else {
  	    	showAlertDialog(AlertType.WARNING,"Gabim!" ,"Ju lutem zgjidhni nje produkt per perditesim!"); 
         }
	    }

  	//metoda per tu kthyer ne dritaren log In
    public void kthehuLogIn() {
        try {
        	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
            Stage currentStage = (Stage) txtEmri.getScene().getWindow();
            currentStage.close();
    		
    		//ngarkon dhe hap faqen e hyrjes (Log In) 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logIn/dritare_00.fxml"));
            Parent root = loader.load();

            //krojojme nje skene te re per faqen kryesore
            Scene scene = new Scene(root, 580,514);
            Stage stage_i_ri = new Stage();
            
            //i vendosim nje imazh dritares
            stage_i_ri.getIcons().add(new Image(getClass().getResourceAsStream("/Screenshot 2025-03-28 085512.png")));

            //caktojme skene dhe titullin e dritares 
            stage_i_ri.setScene(scene);
            stage_i_ri.setTitle("CD STORE");
            //shfaq dritaren 
            stage_i_ri.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //===========================================Tabela Arketari=================================================================================
    @FXML
    private TableView<TabelaArketare> tabelaArketar;     
    @FXML
    private TableColumn<TabelaArketare, Integer> arkCD;

    @FXML
    private TableColumn<TabelaArketare, String> arkEmri;

    @FXML
    private TableColumn<TabelaArketare, Integer> arkFatura;

    @FXML
    private TableColumn<TabelaArketare, String> arkLeke;
    
    //krijimi i nje metode satitke (mund te shihet dhe nga klasa te tjera) per te perditesuar vetem CD e shitur ne tabeln Statistika
    public static void regjistroShitjeStatistika(String cdEmri, int sasiShitur) {
        if (sasiShitur <= 0) return; //nuk ka kuptim te regjistrosh 0 ose negativ

        //me nje stream Optional qe perdoret per NullValues,NumberError etj
        Optional<Statistika> ekziston = listaStatistika.stream()
                .filter(data -> data.getCD().equals(cdEmri)) //nuk perditeson listen/tabelen aktuale por krijon nje te re dhe kerkon nje vend
                .findFirst();                                //se ku te rueht ,lazy operator  dhe metoda findFirst() gjen CD e pare 
         //nqs CD ekziston tashme ne tabele                                                          qe eshte me ate emer ne tabele
        if (ekziston.isPresent()) {
            Statistika ekzistuese = ekziston.get();
            int blereRe = ekzistuese.getCD_te_blere(); //CD e blere nuk ndryshojne veq i marrim 
            int shiturRe = ekzistuese.getCD_te_shitur() + sasiShitur; //shtojme CD e shitur ne kolonen e CD te shitur

            Statistika statRe = new Statistika(ekzistuese.getCD(), shiturRe, blereRe);
            //marrim indeksin e atij element te tabele statistika / ku ndollet CD-ja
            int indeks = listaStatistika.indexOf(ekzistuese);
            
            //nqs gjendet /nqs perdoruesi ka zgjedhur nje CD  / -1 njesoj si te thuash null
            if (indeks != -1) {
            	//vendose ate sesi ne ate pozicion 
                listaStatistika.set(indeks, statRe);
            } else {
                 System.err.println("Problem gjetja e indeksit per statistike: " + cdEmri);
            }
        } 
    }
    
    //metoda statike (mund te shihet edhe nga klasa te tjera) per te shtuar/perditesuar te dhenat ne tabelen e arketareve
    public static void shtoTeDhenaArketari(String emriArketarit, int sasiCDShiturNeFature, double shumaLekNeFature) {
         if (emriArketarit == null || emriArketarit.isEmpty()) return; //duhet emri i arketarit 
         
         //me nje stream Optional qe perdoret per NullValues,NumberError etj
         Optional<TabelaArketare> ekziston = listaTabelaArketare.stream()   //nuk perditeson listen/tabelen aktuale por krijon nje te re dhe kerkon nje vend
                 .filter(data -> data.getEmri().equals(emriArketarit))    //se ku te rueht ,lazy operator  dhe metoda findFirst() gjen emrin e 
                 .findFirst();                                            //arketarit te pare me ate emer
         
         //kontroll nqs ky arketar ekziston ne tabelen e arketareve (te klases Menaxher)
         if (ekziston.isPresent()) {
             //krketari ekziston,perditeso te dhenat
             TabelaArketare ekzistuese = ekziston.get();
             int faturaRe = ekzistuese.getNr_fatura() + 1; //rrit numrin e faturave me 1 
             int cdShiturRe = ekzistuese.getNr_CDShitur() + sasiCDShiturNeFature; //rrit numrin e cd te shitur 
             double shumaRe = ekzistuese.getShumaLekeve() + shumaLekNeFature; //rrit sasine e lekeve  per ate arketar
             
             //e perditesojm arketarin me te dhenat e reja 
             TabelaArketare arketarPerditesuar = new TabelaArketare(emriArketarit, faturaRe, cdShiturRe, shumaRe);
             //gjen pozicionin ne tabele 
             int indeks = listaTabelaArketare.indexOf(ekzistuese);
             //nqs gjendet / -1 njesoj si te thuash null
             if (indeks != -1) {
                 listaTabelaArketare.set(indeks, arketarPerditesuar);
             } else {
            	 //perndryshe mund te ndoll qe se gjen 
                  System.err.println("Problem gjetja e indeksit per arketar: " + emriArketarit);
             }
         } else {
             //Nqs eshte nje arketari i ri ne kase(nuk ekziston),shto rresht te ri
             TabelaArketare arketarRi = new TabelaArketare(emriArketarit, 1, sasiCDShiturNeFature, shumaLekNeFature);
             listaTabelaArketare.add(arketarRi);
         }
    }
 
    //===========================================Tabela Statistika=================================================================================
    @FXML
    private TextField Statistia_te_blere;

    @FXML
    private TextField Statistika_te_shitur;
    
    @FXML
    private TableColumn<Statistika, String> staCD;

    @FXML
    private TableColumn<Statistika, Integer> staTeBlere;

    @FXML
    private TableColumn<Statistika, Integer> staTeShitur;
    
    @FXML
    private TableView<Statistika> tabelaStatistika;
       
}

  	 

