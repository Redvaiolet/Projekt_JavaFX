package admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import application3.Statistika;
import application3.Stoku;
import application3.TabelaArketare;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AdminKontroll {
	    
	    //array per te mbajtu karakteret speciale te palejuara qe mund te vendos perdoruesi
        private static char[] karakter_special = { '!', '@', '€', '#', '%', '&', '*', '(', ')','-' ,'_', '=', '[',']', '{', '}', ';', ':', '"', '<', '>', '?', '\\', '|', '~', '§', 'ß'};

	    @FXML
	    private Button Regjistro, Modifiko, Fshij, shfaqTotalin, LogIn, LogoArketar, LogoMenaxher;
	    
	    @FXML
	    private Button Chart;

	    @FXML
	    private TableView<Stafi> Stafi;

	
	    @FXML
	    private TableColumn<Stafi, String> stafDitelindja;

	    @FXML
	    private TableColumn<Stafi, String> stafEmaili;

	    @FXML
	    private TableColumn<Stafi, String> stafEmri;

	    @FXML
	    private TableColumn<Stafi, String> stafNivel_Aksesi;

	    @FXML
	    private TableColumn<Stafi, String> stafPaga;

	    @FXML
	    private TableColumn<Stafi, String> stafTelefoni;

	    @FXML
	    private TableColumn<Stafi, String> stafFjalekalimi;
	    
	    @FXML
	    private TextField txtEmri,txtDatelindja,txtTelefoni,txtPaga,txtNivel_Aksesi,txtFjalekalimi,txtEmaili;
	    
	    //te dheant Statike (per akses nga klasat e tjera)	   //lista per te mbushur TableView e Stafit
	    private static final ObservableList<Stafi> lisatStafi = FXCollections.observableArrayList();
	    
	    //liste/insatence statike e Admin per te mbajtur listat e Arketareve dhe Menaxhereve 
	    private static final Admin instanceAdmin = new Admin("admin12", "123@45");
	    
	    //pike aksesi per te marre instancat , obj e adminit por edhe te Arketarit dhe Menaxherit
	    public static Admin getInstanceadmin() {
			return instanceAdmin;
		}
	    
	    //lista/ insatnce statike per tabelen Te Dhenat (Fitimi total dhe kosto totale)
	    private static final ObservableList<TeDhenatFinanciare> listaTeDhenatFinanciare = FXCollections.observableArrayList();
	   
	    //pike aksesi per te marre te dheant e tabels Te dhenat
	    public static ObservableList<TeDhenatFinanciare> getListaTeDhenatFinanciare() {
			return listaTeDhenatFinanciare;
		}

		//metoda Initialize
		public void initialize() {
	    	//lidh kolonat e tabelse me atributet e klases Stafi
	    	stafEmri.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmri()));
	    	stafDitelindja.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDatelindja()));
	    	stafTelefoni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefoni()));
	    	stafEmaili.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmaili()));
	    	stafPaga.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaga()));
	    	stafNivel_Aksesi.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNivel_Aksesi()));
	    	stafFjalekalimi.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
	    	
	    	//lidh TabelView me listen statike ObservableList
	    	Stafi.setItems(lisatStafi);
	    	
	    	//Listener per selektim (modifikim/fshirje)                    //_ per variabla qe sna duhen ne shprehjen lambda
	    	 Stafi.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> shfaqDetajet(newValue));
	    	
	    	 //---------------------------------------------------------------------------------------------------------------------------------//
	    	 //lidhja per te ardhurat 
	    	 teardhurat_Totale.setCellValueFactory(cellData -> { //po i bejeme format numrit me metoden String.format
	    		 double vlera = cellData.getValue().getTeArdhurat();
	    		 return new SimpleStringProperty(String.format("%.2f $", vlera));
	    	 });
	    	 
	    	 //lidhja per kostot
	    	 kostot_Totale.setCellValueFactory(cellData -> { //e njeta gja i bjeme format n´sasise se kostos
	    		 double vlera = cellData.getValue().getKostot();
	    		 return new SimpleStringProperty(String.format("%.2f $", vlera));
	    		 
	    	 });
	    	 
	    	 //lidh tabelen TabelView me listen e saj
	    	 teDhenat.setItems(listaTeDhenatFinanciare);
	    	 
	    }
	    
	    //metoda ndihmese per te shfaqur Alert
	    private void showAlertDialog(AlertType alertType, String title, String content) {
	    	//per audio
	    	AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Background.wav");
	    	clip.play(); 
	    	
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();
	    }
	    
	    //metoda per rregjistrim
	    public void handleRegjistro() {
	    	//merr vlerat nga TextFields
	    	String emri = txtEmri.getText();
	    	String datelindja = txtDatelindja.getText();
	    	String telefoni = txtTelefoni.getText();
	    	String emaili = txtEmaili.getText();
	    	String paga = txtPaga.getText();
	    	String niveliAksesit = txtNivel_Aksesi.getText();
	    	String fjalekalimi = txtFjalekalimi.getText();
	    	
	    	//validim i pergjithshem per keto fushat email,nivel aksesi dhe password
	    	 if (emaili.isEmpty() || niveliAksesit.isEmpty() || fjalekalimi.isEmpty()) {
	             showAlertDialog(AlertType.WARNING, "Gabim Regjistrimi", "Ju lutem plotesoni te gjitha fushat!");
	             return;
	         }
	    	 
	    	 //kontroll per emrin
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
	    	
			 
			 //kontroll per datelindjen 
			 if (datelindja == null || datelindja.length() == 0 || datelindja.length() > 10) {
				showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Datelindja nuk mund te lihet bosh ose te jete me e madhe se 10 karaktere.Ju lutemi plotesojeni.");
				return;
			 }

			//kontrollo per karaktere speciale dhe shkronjat
			for (int i = 0; i < datelindja.length(); i++) {
				char c = datelindja.charAt(i);

				//kontroll per shkronja
				 if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
					    showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Datelindja nuk mund te kete shkronja.");
	                    return;
	                }
             
				//kontrollon per karaktere speciale
				for (char karakter : karakter_special) {
					if (c == karakter) {
						 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Datelindja nuk mund te kete karaktere speciale.");
						 return;
					}
				}
			}
    		
			
			//kontroll per telefonin
			 if (telefoni == null || telefoni.length() == 0 || telefoni.length() > 10) {
					showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Telefoni nuk mund te lihet bosh ose te jete me e madhe se 10 karaktere.Ju lutemi plotesojeni.");
					return;
			 }

			 //kontrollo per karaktere speciale dhe shkronjat
			  for (int i = 0; i < telefoni.length(); i++) {
				   char c = telefoni.charAt(i);

				  //kontroll per shkronja
				   if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
						 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Telefoni nuk mund te kete shkronja.");
		                 return;
		           }
	             
				   //kontrollon per karaktere speciale
				   for (char karakter : karakter_special) {
					 if (c == karakter) {
						showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Telefoni nuk mund te kete karaktere speciale.");
						return;
					 }
				   }
			  }
	    			
			 //kontroll per pagen
			 if (paga == null || paga.length() == 0) {
				 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Paga nuk mund te lihet bosh.Ju lutemi plotesojeni.");
				 return;
			 }

			 //kontrollo per karaktere speciale dhe shkronjat
			 for (int i = 0; i < paga.length(); i++) {
				   char c = paga.charAt(i);

			      //kontroll per shkronja
			      if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				      showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Paga nuk mund te kete shkronja.");
			          return;
			       }
		             
			      //kontrollon per karaktere speciale
			      for (char karakter : karakter_special) {
				     if (c == karakter) {
					    showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Paga nuk mund te kete karaktere speciale.");
					    return;
				      }
				   }
			   }
			
	    	 //perdorim equalsIgnoreCase per te mos qene case-sensitive
	         if (!niveliAksesit.equalsIgnoreCase("Arketar") && !niveliAksesit.equalsIgnoreCase("Menaxher")) {
	              showAlertDialog(AlertType.WARNING, "Gabim Regjistrimi", "Niveli i Aksesit duhet te jete 'Arketar' ose 'Menaxher'!");
	              return;
	         }
	         
	      //1.krijo objektin Stafi per ta shfaqur ne tabele
	         Stafi newStaf = new Stafi(emri, datelindja, telefoni, emaili, paga, niveliAksesit, fjalekalimi);
	         lisatStafi.add(newStaf); //shto ne listen e tabeles
	         
	      //2.krijo objektin perkates (Arketar ose Menaxher) dhe shtoje ne listen e Admin
	         if (niveliAksesit.equalsIgnoreCase("Arketar")) {
	             Arketar newArketar = new Arketar(emri, datelindja, telefoni, emaili, paga, niveliAksesit, fjalekalimi);
	             instanceAdmin.getLista_Arketaret().add(newArketar);
	             
	             System.out.println("Arketar i ri u shtua ne listen e Admin.");//per info/konfirmim
	         } else if (niveliAksesit.equalsIgnoreCase("Menaxher")) {
	             Menaxher newMenaxher = new Menaxher(emri, datelindja, telefoni, emaili, paga, niveliAksesit, fjalekalimi);
	             instanceAdmin.getLista_Menaxheret().add(newMenaxher);
	             
	              System.out.println("Menaxher i ri u shtua ne listen e Admin.");//per info/konfirmim
	         }
	         
	       //pastro fushat pas regjistrimit
	         txtEmri.clear();
	         txtDatelindja.clear();
	         txtTelefoni.clear();
	         txtEmaili.clear();
	         txtNivel_Aksesi.clear();
	         txtPaga.clear();
	         txtFjalekalimi.clear();
	         
	         //rifreskim i tabeles
	         Stafi.refresh();  
	    }
	    
	    //metode ndihmese per shfaqjen detaje ne textfields (per modifikim/fshirje)
	    public void shfaqDetajet(Stafi staf) {
	        if (staf != null) {
	            //mbush textfields me te dhenat e stafit te selektuar
	            txtEmri.setText(staf.getEmri());
	            txtDatelindja.setText(staf.getDatelindja());
	            txtTelefoni.setText(staf.getTelefoni());
	            txtEmaili.setText(staf.getEmaili());
	            txtPaga.setText(staf.getPaga());
	            txtNivel_Aksesi.setText(staf.getNivel_Aksesi());
	            txtFjalekalimi.setText(staf.getPassword());
	        } else {
	            //pastro fushat nese nuk ka asgje te selektuar
	        	 txtEmri.clear();
		         txtDatelindja.clear();
		         txtTelefoni.clear();
		         txtEmaili.clear();
		         txtNivel_Aksesi.clear();
		         txtPaga.clear();
		         txtFjalekalimi.clear();
	        }
	    }
	    
	    
	    //metoda per modifikim
	    public void handleModifiko() {
	    	 //1.merr anetarin e stafit te selektuar nga tabela
	        int selectedIndex = Stafi.getSelectionModel().getSelectedIndex();
	        Stafi stafSelektuar = Stafi.getSelectionModel().getSelectedItem();

	        if (stafSelektuar == null) {
	            showAlertDialog(AlertType.WARNING, "Asnje Zgjedhje", "Ju lutem zgjidhni nje anetar stafi per te modifikuar.");
	            return;
	        }

	        //ruaj te dhenat origjinale qe na duhen per te gjetur objektin ne listat specifike
	        String emriOrigjinal = stafSelektuar.getEmri();
	        String telefoniOrigjinal = stafSelektuar.getTelefoni(); //perdorim telefonin si identifikues shtese
	        String niveliOrigjinal = stafSelektuar.getNivel_Aksesi();

	        //2.merr te dhenat e reja nga textfields
	        String emriRi = txtEmri.getText();
	        String datelindjaRe = txtDatelindja.getText();
	        String telefoniRi = txtTelefoni.getText();
	        String emailiRi = txtEmaili.getText();
	        String pagaRe = txtPaga.getText();
	        String niveliRi = txtNivel_Aksesi.getText();
	        String fjalekalimiRi = txtFjalekalimi.getText();

	        //3.valido te dhenat e reja 
	        //validim i pergjithshem per keto fushat email,nivel aksesi dhe password
	        if (emailiRi.isEmpty() || niveliRi.isEmpty() || fjalekalimiRi.isEmpty()) {
	            showAlertDialog(AlertType.WARNING, "Gabim Modifikimi", "Ju lutem plotesoni te gjitha fushat!");
	            return;
	        }
	        
	        
	        //kontroll per emrin
		    if (emriRi == null || emailiRi.length() == 0) {
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
	    	
			 
			 //kontroll per datelindjen 
			 if (datelindjaRe == null || datelindjaRe.length() == 0 || datelindjaRe.length() > 10) {
				showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Datelindja nuk mund te lihet bosh ose me e madhe se 10 karaktere.Ju lutemi plotesojeni.");
				return;
			 }

			//kontrollo per karaktere speciale dhe shkronjat
			for (int i = 0; i < datelindjaRe.length(); i++) {
				char c = datelindjaRe.charAt(i);

				//kontroll per shkronja
				 if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
					    showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Datelindja nuk mund te kete shkronja.");
	                    return;
	                }
            
				//kontrollon per karaktere speciale
				for (char karakter : karakter_special) {
					if (c == karakter) {
						 showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Datelindja nuk mund te kete karaktere speciale.");
						 return;
					}
				}
			}
   		
			
			//kontroll per telefonin
			 if (telefoniRi == null || telefoniRi.length() == 0 || telefoniRi.length() > 10) {
					showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Telefoni nuk mund te lihet bosh ose me e madhes se 10 karaktere.Ju lutemi plotesojeni.");
					return;
			 }

			 //kontrollo per karaktere speciale dhe shkronjat
			 for (int i = 0; i <  telefoniRi.length(); i++) {
				char c = telefoniRi.charAt(i);

				//kontroll per shkronja
				if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
					showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Telefoni nuk mund te kete shkronja.");
		            return;
		         }
	             
			    //kontrollon per karaktere speciale
				for (char karakter : karakter_special) {
				   if (c == karakter) {
					   showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Telefoni nuk mund te kete karaktere speciale.");
					   return;
					}
				}
			 }
	    		
				
			  //kontroll per pagen
			   if (pagaRe == null || pagaRe.length() == 0) {
					showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Paga nuk mund te lihet bosh.Ju lutemi plotesojeni.");
					return;
			   }

			   //kontrollo per karaktere speciale dhe shkronjat
			   for (int i = 0; i < pagaRe.length(); i++) {
					char c = pagaRe.charAt(i);

				   //kontroll per shkronja
				   if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
						showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Paga nuk mund te kete shkronja.");
			            return;
			       }
		             
				   //kontrollon per karaktere speciale
				   for (char karakter : karakter_special) {
					  if (c == karakter) {
					     showAlertDialog(AlertType.WARNING, "Gabim!", "Fusha Paga nuk mund te kete karaktere speciale.");
						 return;
					  }
					}
			 }
			
	       
	        //kontroll niveli i aksesit
	        if (!niveliRi.equalsIgnoreCase("Arketar") && !niveliRi.equalsIgnoreCase("Menaxher")) {
	             showAlertDialog(AlertType.WARNING, "Gabim Modifikimi", "Niveli i Aksesit duhet te jete 'Arketar' ose 'Menaxher'!");
	             return;
	        }
	        
	        

	        //4.krijo objektin e ri Stafi dhe perditeso listen e tabeles (listaStafi)
	        Stafi stafPerditesuar = new Stafi(emriRi, datelindjaRe, telefoniRi, emailiRi, pagaRe, niveliRi, fjalekalimiRi);
	        lisatStafi.set(selectedIndex, stafPerditesuar); //kjo perditeson direkt TableView

	        //5.üerditeso listen specifike (lista_Arketaret ose lista_Menaxheret)
	        boolean uGjetDhePerditesua = false; //per te kontrolluar per modifimin e bere 

	        //predicate (merr int kthen boolean) per te gjetur objektin origjinal ne listat specifike (duke perdorur emrin dhe passwordin origjinal)
	        Predicate<Object> gjejOrigjinalin = obj -> {
	            if (obj instanceof Arketar) {
	                Arketar a = (Arketar) obj;
	                return a.getEmri().equals(emriOrigjinal) && a.getTelefoni().equals(telefoniOrigjinal);
	            } else if (obj instanceof Menaxher) {
	                Menaxher m = (Menaxher) obj;
	                return m.getEmri().equals(emriOrigjinal) && m.getTelefoni().equals(telefoniOrigjinal);
	            }
	            return false;
	        };

            //perditesim per nivelin e Stafit ngritje ne deetyre ose ulje ne detyre 
	        if (niveliOrigjinal.equalsIgnoreCase(niveliRi)) {
	            //rasti 1:niveli nuk ka ndryshuar (Arketar -> Arketar ose Menaxher -> Menaxher)
	           
	        	//Arketar -> Arketar 
	        	if (niveliRi.equalsIgnoreCase("Arketar")) {
	                List<Arketar> arketaret = instanceAdmin.getLista_Arketaret();
	                for (int i = 0; i < arketaret.size(); i++) {
	                    if (gjejOrigjinalin.test(arketaret.get(i))) {
	                        Arketar arketarPerditesuar = new Arketar(emriRi, datelindjaRe, telefoniRi, emailiRi, pagaRe, niveliRi, fjalekalimiRi);
	                        arketaret.set(i, arketarPerditesuar);
	                        uGjetDhePerditesua = true;
	                        System.out.println("Arketari u modifikua ne listen specifike.");
	                        break;
	                    }
	                }
	            } else {//Menaxher -> Menaxher
	                List<Menaxher> menaxheret = instanceAdmin.getLista_Menaxheret();
	                 for (int i = 0; i < menaxheret.size(); i++) {
	                    if (gjejOrigjinalin.test(menaxheret.get(i))) {
	                        Menaxher menaxherPerditesuar = new Menaxher(emriRi, datelindjaRe, telefoniRi, emailiRi, pagaRe, niveliRi, fjalekalimiRi);
	                        menaxheret.set(i, menaxherPerditesuar);
	                        uGjetDhePerditesua = true;
	                         System.out.println("Menaxheri u modifikua ne listen specifike.");
	                        break;
	                    }
	                }
	            }
	        } else {
	            //rasti 2:niveli ka ndryshuar (Arketar -> Menaxher ose Menaxher -> Arketar)
	            //heqim nga lista e vjeter dhe shtojme tek lista e re

	            //heqim nga lista origjinale
	            boolean uHoqNgaListaVjeter = false;
	            if (niveliOrigjinal.equalsIgnoreCase("Arketar")) {
	                uHoqNgaListaVjeter = instanceAdmin.getLista_Arketaret().removeIf(gjejOrigjinalin);
	                 if(uHoqNgaListaVjeter) System.out.println("U hoq nga lista e Arketareve.");
	            } else { // niveliOrigjinal ishte Menaxher
	                uHoqNgaListaVjeter = instanceAdmin.getLista_Menaxheret().removeIf(gjejOrigjinalin);
	                 if(uHoqNgaListaVjeter) System.out.println("U hoq nga lista e Menaxhereve.");
	            }

	            //shtojme tek lista e re
	            if (uHoqNgaListaVjeter) { //shtojme vetem nese u hoq me sukses
	                if (niveliRi.equalsIgnoreCase("Arketar")) {
	                    Arketar arketarRi = new Arketar(emriRi, datelindjaRe, telefoniRi, emailiRi, pagaRe, niveliRi, fjalekalimiRi);
	                    instanceAdmin.getLista_Arketaret().add(arketarRi);
	                    uGjetDhePerditesua = true; //konsiderohet si perditesim i suksesshem
	                    System.out.println("U shtua ne listen e Arketareve.");
	                } else { //niveliRi eshte Menaxher
	                    Menaxher menaxherRi = new Menaxher(emriRi, datelindjaRe, telefoniRi, emailiRi, pagaRe, niveliRi, fjalekalimiRi);
	                    instanceAdmin.getLista_Menaxheret().add(menaxherRi);
	                    uGjetDhePerditesua = true; //konsiderohet si perditesim i suksesshem
	                     System.out.println("U shtua ne listen e Menaxhereve.");
	                }
	            } else {
	                 System.err.println("kujdes:Nuk u gjet objekti origjinal per ta hequr nga lista specifike!");
	            }
	        }
                //e njeta gje si uGjetDhePerditesua == false 
	        if (!uGjetDhePerditesua) {
	             System.err.println("kujdes:Objekti u modifikua ne tabelen kryesore, por nuk u gjet/perditesua ne listen specifike!");
	             //gabim sinkronizimi                                            lista te brendshme e ka fjalen per listen e arketarit dhe Menaxherit
	             showAlertDialog(AlertType.ERROR, "Problem Modifikimi", "Modifikimi u krye ne tabele, por pati problem ne perditesimin e listes se brendshme.");
	        } else {
	             showAlertDialog(AlertType.INFORMATION, "Modifikim i Suksesshem", "Te dhenat e stafit u modifikuan me sukses.");
	        }


	        //pastro fushat pas modifikimit
	         txtEmri.clear();
	         txtDatelindja.clear();
	         txtTelefoni.clear();
	         txtEmaili.clear();
	         txtNivel_Aksesi.clear();
	         txtPaga.clear();
	         txtFjalekalimi.clear();
	         Stafi.refresh(); 
	    }
	    
	    //metoda per te fshire nje Anetar dhe Menaxher nga Tabela Stafi
	    public  void handleFshij() {
	        //1.merr anetarin e stafit te selektuar nga tabela
	        Stafi stafSelektuar = Stafi.getSelectionModel().getSelectedItem();

	        if (stafSelektuar == null) {
	            showAlertDialog(AlertType.WARNING, "Asnje Zgjedhje", "Ju lutem zgjidhni nje anetar stafi per te fshire.");
	            return;
	        }

	        //ruaj te dhenat qe na duhen per te gjetur objektin ne listat specifike
	        String emriPerFshirje = stafSelektuar.getEmri();
	        String telefoniPerFshirje = stafSelektuar.getTelefoni(); //identifikues shtese
	        String niveliPerFshirje = stafSelektuar.getNivel_Aksesi();

	        //2.fshij nga lista specifike (Arketar ose Menaxher)
	        boolean uFshiNgaListaSpecifike = false;

	        //predicate per te gjetur objektin qe do fshihet
	        Predicate<Object> gjejPerFshirje = obj -> {
	            if (obj instanceof Arketar) {
	                Arketar a = (Arketar) obj;
	                return a.getEmri().equals(emriPerFshirje) && a.getTelefoni().equals(telefoniPerFshirje);
	            } else if (obj instanceof Menaxher) {
	                Menaxher m = (Menaxher) obj;
	                return m.getEmri().equals(emriPerFshirje) && m.getTelefoni().equals(telefoniPerFshirje);
	            }
	            return false;
	        };

	        if (niveliPerFshirje.equalsIgnoreCase("Arketar")) {
	            uFshiNgaListaSpecifike = instanceAdmin.getLista_Arketaret().removeIf(gjejPerFshirje);
	            if (uFshiNgaListaSpecifike) System.out.println("U fshi nga lista e Arketareve.");
	        } else if (niveliPerFshirje.equalsIgnoreCase("Menaxher")) {
	            uFshiNgaListaSpecifike = instanceAdmin.getLista_Menaxheret().removeIf(gjejPerFshirje);
	             if (uFshiNgaListaSpecifike) System.out.println("U fshi nga lista e Menaxhereve.");
	        }

	        if (!uFshiNgaListaSpecifike) {
	             System.err.println("kujdes:Nuk u gjet objekti per t'u fshire ne listen specifike (" + niveliPerFshirje + ")!");
	             showAlertDialog(AlertType.WARNING, "Problem Modifikimi", "Objekti nuk u gjet ne listen e brendshme, por do te fshihet nga tabela.");
	             
	        }

	        // 3.fshij nga lista e tabeles (listaStafi)
	        //fshirja nga ObservableList do te perditesoje automatikisht TableView
	        boolean uFshiNgaTabela = lisatStafi.remove(stafSelektuar); //remove(object) kthen boolean

	        if(uFshiNgaTabela) {
	             System.out.println("Stafi u fshi nga tabela kryesore.");
	             showAlertDialog(AlertType.INFORMATION, "Fshirje e Suksesshme", "Anetari i stafit '" + emriPerFshirje + "' u fshi me sukses.");
	        } 

	        
	        //4.pastro fushat
	         txtEmri.clear();
	         txtDatelindja.clear();
	         txtTelefoni.clear();
	         txtEmaili.clear();
	         txtNivel_Aksesi.clear();
	         txtPaga.clear();
	         txtFjalekalimi.clear();
	         Stafi.refresh();

	    }
	    
//----------------------------------Tabela Te dhenat--------------------------------------------------------------------------------//	    
	    @FXML
	    private TableView<TeDhenatFinanciare> teDhenat;  //tipi i tabeles

	    @FXML
	    private TableColumn<TeDhenatFinanciare, String> teardhurat_Totale;
        
	    @FXML
	    private TableColumn<TeDhenatFinanciare, String> kostot_Totale;
	    
	    
	    //metode per te shfaqur totalin
	    @FXML // Sigurohu qe ke @FXML
		public void handleShfaqTotalin() {
		    //merr listat statike ne kontrolleri i Menaxherit
		    ObservableList<TabelaArketare> listaArketare = application3.Kontroll.getListaTabelaArketare();
		    ObservableList<Statistika> listaStatistika = application3.Kontroll.getListaStatistika();
		    ObservableList<Stoku> listaStoku = application3.Kontroll.getListaStoku();
	        //lista e stafit eshte lokale:lisatStafi


		    //llogaritja Te Ardhurat Totale 
		    double teArdhuraTotale = 0.0;
		    //kontroll nqs nuk kemi personel ne klase 
		    if (listaArketare != null) {
		        for(TabelaArketare arkShumaLekeve : listaArketare) {
		            teArdhuraTotale += arkShumaLekeve.getShumaLekeve();
		        }
		    }else {
		    	showAlertDialog(AlertType.WARNING, "Gabim!", "Nuk keni staf ne kase ju letemi vendosni disa persona per te shitur produkte!");
		    	return;
		    }


		    // llogaritja per Kostot Totale 
		    double kostoTotale = 0.0; 
		    double kostoTotaleCD = 0.0;
		    double kostoTotalePaga = 0.0;

		    //llogaritja e kostos se CD-ve te blera
		    if (listaStatistika != null && listaStoku != null) {
		        for(Statistika CD : listaStatistika) {
		            String CDemra =  CD.getCD();  //marrim emrat e CD
		            int blerjaTotale = CD.getCD_te_blere(); //marrim sasine e CD (gjithmone atyre te blere)

		            if (blerjaTotale > 0) {
		                //gjej cmimin e blerjes ne listen e stokut me nje stream
		                Optional<Stoku> cmimiStreams = listaStoku.stream()
		                        .filter( data -> data.getEmri().equals(CDemra))
		                        .findFirst();

		                if (cmimiStreams.isPresent()) {
		                    Stoku stokuProdukt = cmimiStreams.get();
		                    String cmimiBlerjesStr = stokuProdukt.getCmimiB().replaceAll("[^\\d.]", ""); //pastron cmimin e blerjese nga $
		                    //nqs cmimi i cd nuk eshte bosh dhe eshte numer valid
		                    if (!cmimiBlerjesStr.isEmpty()) {
		                           double cmimiBlerjes = Double.parseDouble(cmimiBlerjesStr);
		                           kostoTotaleCD += blerjaTotale * cmimiBlerjes;      
		                      } 
		            }
		        } // cikli per CD mbaron ketu
		    }

	        //llogaritje kosto e pagave
		    if (lisatStafi != null) {
		        for(Stafi staf : lisatStafi) {
		            String pagaStr = staf.getPaga().replaceAll("[^\\d.]", ""); //pastro pagen nga simboli $
		            if (!pagaStr.isEmpty()) {
		                   double paga = Double.parseDouble(pagaStr);
		                   kostoTotalePaga += paga;  
		        }
		    }
	        //cikli per pagat mbaron ketu

		    //shuma e kostove (pasi kemi marre te gjitha kostot Cd + paga)
		    kostoTotale = kostoTotaleCD + kostoTotalePaga;
	        // Shtojme kontroll minimal per NullPointerException
	        if (listaTeDhenatFinanciare != null) {
		       listaTeDhenatFinanciare.clear();//Pastro listen para se te shtosh rreshtin e ri
		       listaTeDhenatFinanciare.add(new TeDhenatFinanciare(teArdhuraTotale, kostoTotale));
	        }
		    //per te pare perdoruesin se jane bere llogaritjet 
		    showAlertDialog(AlertType.INFORMATION, "Totale te Llogaritura",  "Te ardhurat totale dhe kostot totale jane llogaritur dhe shfaqur.");
		   }
		  }
	    }   
	    
	  
	       @FXML
	       public void handleShowChart() {
	        // Kontrollo nese ka te dhena per te shfaqur (nese butoni Shfaq Totalin eshte shtypur)
	        if (listaTeDhenatFinanciare.size() == 0) {
	            showAlertDialog(AlertType.WARNING, "Nuk ka te dhena", "Ju lutem shtypni butonin 'Shfaq Totalin' perpara se te shfaqni grafikun.");
	            return;
	        }

	        try {
	        	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
	            Stage currentStage = (Stage) txtEmaili.getScene().getWindow();
	            currentStage.close();
	    		
	    		//ngarkon dhe hap faqen e hyrjes (Log In) 
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("chart.fxml"));
	            Parent root = loader.load();

	            //krojojme nje skene te re per faqen kryesore
	            Scene scene = new Scene(root, 720,488);
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
	            showAlertDialog(AlertType.ERROR, "Gabim Ngarkimi", "Problem në ngarkimin e dritares së grafikut.");
	        } catch (Exception e) {
	             e.printStackTrace();
	             showAlertDialog(AlertType.ERROR, "Gabim", "Ndodhi nje gabim i papritur gjate hapjes se grafikut.");
	        }
	    }	    
//--------------------------------------------------------------------------------------------------------------------------------//	    
	    public void ktheuLogIn() {
	        try {
	        	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
	            Stage currentStage = (Stage) txtEmaili.getScene().getWindow();
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
	    
	    //metode per te hapur Sistemin e menaxherit
	    public void dritareMenaxher() {
	        try {
	        	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
	            Stage currentStage = (Stage) txtEmaili.getScene().getWindow();
	            currentStage.close();
	    		
	    		//ngarkon dhe hap faqen e hyrjes (Log In) 
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application3/dritare_03.fxml"));
	            Parent root = loader.load();

	            //krojojme nje skene te re per faqen kryesore
	            Scene scene = new Scene(root, 1535,766);
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
	    
	  public void dritareArketar() {
		  try {
	        	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
	            Stage currentStage = (Stage) txtEmaili.getScene().getWindow();
	            currentStage.close();
	    		
	    		//ngarkon dhe hap faqen e hyrjes (Log In) 
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application1/dritare_01.fxml"));
	            Parent root = loader.load();

	            //krojojme nje skene te re per faqen kryesore
	            Scene scene = new Scene(root, 892, 619);
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
