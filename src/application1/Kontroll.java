package application1;

import java.io.BufferedWriter; //per te shkruar ne rreshta sesa nje fjale 
import java.io.FileWriter;     //per te shkruar ne file
import java.io.IOException;   
import java.time.LocalDateTime; //ppsionale:per te shtuar daten/oren
import java.time.format.DateTimeFormatter; //Opsionale:per te formatuar daten/oren
import java.util.Optional;
import application3.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

//kontrolleri i arketarit 
public class Kontroll {
	//array per te mbajtu karakteret speciale te palejuara qe mund te vendos perdoruesi
    private char[] karakter_special = { '!', '@', '€', '#', '%', '&', '*', '(', ')', '_', '=', '[',']', '{', '}', ';', ':', '"', '<', '>', '?', '\\', '|', '~', '§', 'ß'};

	
//------------------------------------------------------------------------------------------//	
	    private String emriArketaritAktual; //per te ruajtur emrin e arketarit

	    //,etoda per te vendosur emrin nga LogIn controller 
	    public void setArketarAktual(String emri) {
	        this.emriArketaritAktual = emri;
	        System.out.println("Arketari aktual: " + this.emriArketaritAktual); //per pastrim gabimi qe mund te ndodhe
	    }	
//-----------------------------------------------------------------------------------------//
	    @FXML
	    private TableColumn<FatureProdukt, String> Cmimi;

	    @FXML
	    private TableColumn<FatureProdukt, String> Emri;
	   
	    @FXML
	    private TableColumn<FatureProdukt, Integer> Sasia;
	    
	    @FXML
	    private TableView<FatureProdukt> Fatura;


	    @FXML
	    private TableColumn <Stoku, String> ListeEmri, ListeCmimi;

	    @FXML
	    private TableView<Stoku> ListeProduktesh;

	    
	    @FXML
	    private Button ShtoListe,ktheuLogIn,txtTotali,txtPastro;

	    @FXML
	    private TextField faturaTotale,prdduktEmer,produktSasia;

	    //metoda e inicializimit 
	    public void initialize() {
	    	//lidhja e kolonave me atributet/kolonave te tabeles se klases Stoku
	        ListeEmri.setCellValueFactory(new PropertyValueFactory<>("emri"));
	        ListeCmimi.setCellValueFactory(new PropertyValueFactory<>("cmimiSh"));

	        //merr te dhenat nga listaStoku e application3 dhe vendosi ne ListeProduktesh
	        ObservableList<Stoku> listaNgaStoku = application3.Kontroll.getListaStoku(); //ndryshim ketu
	       //dhe me set i vendosim 
	        ListeProduktesh.setItems(listaNgaStoku);
	        
	            
	        //lidhja e kolonave e tabeles Fatura me klasen FatureProdukt
	        Emri.setCellValueFactory(new PropertyValueFactory<>("emri"));
	        Sasia.setCellValueFactory(new PropertyValueFactory<>("sasia"));
	        Cmimi.setCellValueFactory(new PropertyValueFactory<>("cmimiShitjes"));   
        }
	    
	    //metode per te shfaqur aleretet 
	    public void showAlertDialog(AlertType alertType, String title, String content) {
	    	//per audio
	    	AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Background.wav");
	    	clip.play(); 
	    	
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();//shfaqim alertet 
	    }
	    
	    //metode per te konfirmuar printimin e fatures
	    public void printimAlert(AlertType alertType, String title, String content) {
	    	//per audio
	    	AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Unlock.wav");
	    	clip.play(); 
	    	
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();//shfaqim alertet 
	    }
	    
	    @FXML
	    public void handleShtoNeListe() {//metode per te shtuar produkete ne tabeln e listes
	        String emriProduktit = prdduktEmer.getText();
	        String sasiaStr = produktSasia.getText();

	        //kontroll per emrin e CD/produktit
		    if (emriProduktit == null || emriProduktit.length() == 0) {
			   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri i produktit  nuk mund te lihet bosh.Ju lutemi plotesojeni.");
			   return; //per te ndalur veprimet 
			}
	   	 
		    //kontrollo per karaktere speciale dhe numra
			for (int i = 0; i < emriProduktit.length(); i++) {//iteron neper gjatesine e stringes me nje indeks i
				 char c = emriProduktit.charAt(i);//dhe e ruan secilin karakter ne variablin c per tu shqyrtuar karakter per karakter/charAt() eshte nje metode objeti e klases String ne java

				 //kontrollon per numra
				 if (c >= '0' && c <= '9') {
					showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri i produktit nuk mund te kete numra.");
					return;
				 }

				 //kontrollon per karaktere speciale
				 for (char karakter : karakter_special) {// for-each loop qe merr secilin element te array-t      karakter eshte variabel e perkoheshme
					if (c == karakter) {//karakter_special rradhazi dhe e cakton ate te karakter per cdo kalim
						showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Emri i produktit nuk mund te kete karaktere speciale.");
						return; //per te ndalur veprimet 
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
				for (char karakter : karakter_special) {
					if (c == karakter) {
						 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Sasia nuk mund te kete karaktere speciale.");
						 return;
					}
				}
			}
	        
	        //variabel per sasine 
	        int sasia;
	        
	        try {
	        // e kthejme nga string ne int 
	        sasia = Integer.parseInt(sasiaStr);
	        //kontroll nqs eshte nje numer pozitiv 
	        if (sasia <= 0) {
	        showAlertDialog(AlertType.WARNING,"Gabim!", "Sasia duhet te jetë nje numer pozitiv!");
	        return;    
	        }
	        }catch(NumberFormatException e) {
	           //kontroll nqs sasia nuk eshte nje numer (nqs array nuk i kap te gjitha gabimet)
	        	showAlertDialog(AlertType.WARNING, "Gabim!", "Sasia duhet te jete nje numer.");
	        	return;
	        }
	        
	        //kerko produktin ne listen e stokut me nje obj te tabeles Stoku 
	        Stoku produktiGjetur = null;
	        for (Stoku s : ListeProduktesh.getItems()) { //kontrollojme me nje for-each element per element 
	            if (s.getEmri().equals(emriProduktit)) { //nqs emri qe ka vendodur arketari eshte = me merin e listen e stkut 
	                produktiGjetur = s; //eshte gjetur CD
	                break; //dil nga kerkimi 
	            }
	        }

	        //kontroll nqs produkti nuk gjendet ne liste
	        if (produktiGjetur == null) {
	            showAlertDialog(AlertType.WARNING,"Gabim!", "Produkti nuk gjendet ne liste.");
	            return;
	        }

	          //kontroll nese sasia e atij produkti ka mbaruar 
	          int sasiNeStok = Integer.parseInt(produktiGjetur.getSasia());
	          
	          //nqs sasia ne table eshte nje numer me i madh se ai ne stok 
	          if(sasia > sasiNeStok) {
	        	  showAlertDialog(AlertType.WARNING, "Sasia e Pamjaftueshme", "Nuk ka sasi te majftueshme ne stok per " + emriProduktit + ". Sasia aktuale: " + sasiNeStok);
	             return;
	          }
	        
	        //shto produktin ne tabelen e fatures
	        Fatura.getItems().add(new FatureProdukt(produktiGjetur.getEmri(), sasia, produktiGjetur.getCmimiSh()));

	        //pastro fushat e tekstit pas shtimit
	        prdduktEmer.clear();
	        produktSasia.clear();
	    }
	    
	    
	    //private per mos tu ngaterruar me importimet 
	    @FXML
	    private void llogaritTotali() { //metode per te llogaritur totalin 
	        //kontroll nwse lista e artikujve ne tabelen Fatura eshte bosh
	        if (Fatura.getItems().isEmpty()) {
	            showAlertDialog(AlertType.WARNING, "Informacion", "Lista e faturës eshte bosh.Shtoni produkte.");
	            return;
	        }

	        //kontroll per emrin e arketarit 
	        if (emriArketaritAktual == null || emriArketaritAktual.isEmpty()) {
	            showAlertDialog(AlertType.ERROR, "Gabim Sistemi", "Emri i arketarit nuk eshte percaktuar.");
	            return;
	        }

	        //deklarimi i variablave per totalet
	        double totali = 0.0; //totali i shumes se produkteve 
	        int totali_CD_te_ShiturNeFature = 0; 
	        boolean gabimStoku = false; //per te ndaluar nese ka problem me stokun (p.sh ka mbaruar/nuk eshte furrnizuar ai produkt)


	        //kopjo listen e fatures per te shmangur modifikimin e listes origjinale
	        ObservableList<FatureProdukt> produkteNeFature = FXCollections.observableArrayList(Fatura.getItems());

	        //cikli 1:llogarit totalet dhe perditeso stokun/statistikat
	        for (FatureProdukt produkt : produkteNeFature) { //for-each per te iteruar per cdo produkt te fatures

	            //emrin e cd se shitur 
	            String emriCD = produkt.getEmri(); 
	            //sasia e produktit te shitur 
	            int sasiShitur = produkt.getSasia(); 

	            //gjej produktin ne listen Kryesore(ate te Menaxherit) te stokut
	            Optional<Stoku> stokuOptional = application3.Kontroll.getListaStoku().stream()
	                    .filter(s -> s.getEmri().equals(emriCD))  //per te kontrolluar nese ky produkt ekziston ne Stok 
	                    .findFirst();
                
	            //nqs ekziston 
	            if (stokuOptional.isPresent()) {
	            	//e marrim ate produkt 
	                Stoku stokuProdukt = stokuOptional.get();
	                try {
	                	//marrim sasin e tij aktuale sepse do e zberim ate me sasine e shitur
	                    int sasiAktualeNeStok = Integer.parseInt(stokuProdukt.getSasia());

	                    //rikontroll sasie (per siguri,ndoshta dikush tjeter e bleu nderkohe)
	                    if (sasiShitur > sasiAktualeNeStok) {
	                        showAlertDialog(AlertType.ERROR, "Problem Stoku", "Sasia per '" + emriCD + "' ka ndryshuar. Sasia aktuale: " + sasiAktualeNeStok + ". Anuloni faturen dhe provoni perseri.");
	                        gabimStoku = true;
	                        break; //dil nga cikli
	                    }

	                    //zbrit sasine nga stoku
	                    int sasiReNeStok = sasiAktualeNeStok - sasiShitur;
	                    stokuProdukt.setSasia(sasiReNeStok); //perditeso objektin Stoku

	                    //regjistro shitjen ne statistikat e pergjithshme
	                    application3.Kontroll.regjistroShitjeStatistika(emriCD, sasiShitur);

	                    //llogarit cmimin per kete produkt ne fature
	                    String cmimiStr = produkt.getCmimiShitjes().replaceAll("[^\\d.]", ""); //per te hequr simbolin e $ nga String-a
	                    //e kthejme ne numer
	                    double cmimi = Double.parseDouble(cmimiStr);

	                   //bejme llogaritjen per ate produkt 
	                    totali += cmimi * sasiShitur; 
	                    //totali i cd te shitur nga nje arketar 
	                    totali_CD_te_ShiturNeFature += sasiShitur; 

	                } catch (NumberFormatException e) {
	                    showAlertDialog(AlertType.ERROR, "Gabim te dhenash", "Problem me konvertimin e numrave per produktin: " + emriCD);
	                    gabimStoku = true; //gabim stoku per te ndaluar
	                    break; //dil nga cikli
	                }
	            } else {
	                showAlertDialog(AlertType.ERROR, "Gabim te Dhenash", "Produkti '" + emriCD + "' nga fatura nuk u gjet me ne stok!");
	                gabimStoku = true; //gabim stoku per te ndaluar
	                break; //dil nga cikli 
	            }
	        } //fundi i ciklit for

	        //nese pati gabim gjate perpunimit te stokut, mos vazhdo
	        if (gabimStoku) {
	            faturaTotale.setText("GABIM STOKU!");
	            return;
	        }

	        //regjistro te dhenat per kete arketar ne tabelen e menaxherit
	        application3.Kontroll.shtoTeDhenaArketari(emriArketaritAktual, totali_CD_te_ShiturNeFature, totali); 

	        //shfaq totalin ne TextField te Arketarit
	        faturaTotale.setText(String.format("%.2f $", totali)); 
	        
	        //njoftim qe fatura u printua
	        printimAlert(AlertType.INFORMATION, "Fatura u Printua", "Totali: " + String.format("%.2f $", totali)); 
  
	    }
	    
	    
	    //metode per te pastruar te tabelen 
	    public void handlePastro() {
	        Fatura.getItems().clear(); 
	        faturaTotale.clear();
	        
	    }

	
	    //metode per ty kther ne Log In 
	    public void ktheuLogIn() {
	        try {
	        	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
	            Stage currentStage = (Stage) txtTotali.getScene().getWindow();
	            currentStage.close();
	    		
	    		//ngarkon dhe hap faqen e hyrjes (Log In) 
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/logIn/dritare_00.fxml"));
	            Parent root = loader.load();

	            //krojojme nje skene te re per faqen kryesore
	            Scene scene = new Scene(root,580,514);
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
}
