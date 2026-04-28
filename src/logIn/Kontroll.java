package logIn;

import java.io.IOException;
import admin.Admin;
import admin.AdminKontroll;
import admin.Arketar;
import admin.Menaxher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Kontroll {
	    @FXML
	    private TextField emri;

	    @FXML
	    private PasswordField password;
	    
	    @FXML
	    private Button logohu;
	    
	    @FXML
	    private Button dil_sistem;
	    
	    
	    //metoda ndihmese per te shfaqur Alert
        private void showAlert(AlertType alertType, String title, String content) {
        //per audio
        AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Background.wav");
        clip.play();
        	
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); //pa header text
        alert.setContentText(content);
        alert.showAndWait();
    }
	    
        //handleLogIn per te kaluar emrin e Arketarit 
	   public void handleLogin() {
		   String username = emri.getText();
	        String pass = password.getText();

	        // Validim i thjeshte
	        if (username.isEmpty() || pass.isEmpty()) {
	            showAlert(AlertType.WARNING, "Gabim!", "Ju lutem plotesoni emrin dhe fjalekalimin.");
	            return;
	        }

	        //merr instancen statike te Admin nga AdminKontroll me nje obj te klases Admin (pra merr username dhe password)
	        Admin adminData = AdminKontroll.getInstanceadmin();
	        

	        //kontrroll i kredencialeve te Adminit 
	        //krahasim me kredencialet e Adminit (qe jane hardcoded ne klasen Admin)
	        if (username.equals(adminData.getUsername()) && pass.equals(adminData.getPassword())) {
	             System.out.println("Login i suksesshem si Administrator: " + username);
	             hapDritarenAdmin();
	             return; //Log In i sukseshem,dil nga metoda
	        }
	        


	        //1.kontrollo nese eshte Arketar (vetem nese nuk ishte Admin)
	        for (Arketar arketar : adminData.getLista_Arketaret()) {
	            //krahaso username dhe password
	            if (arketar.getEmri().equals(username) && arketar.getPassword().equals(pass)) {
	                System.out.println("Login i suksesshem si Arketar: " + username);
	                //modifikim per thirrur metoden qe do kaloj emrin e Arketarit(username) tek Tabela e Arketareve (tek Menaxheri)  
	                hapDritarenArketar(username);
	                return;//Log In i sukseshem,dil nga metoda
	            }
	        }

	        //2.kontrollo nese eshte Menaxher (vetem nese nuk u gjet si Arketar ose Admin)
	        for (Menaxher menaxher : adminData.getLista_Menaxheret()) {
	            //krahaso username dhe password
	            if (menaxher.getEmri().equals(username) && menaxher.getPassword().equals(pass)) {
	                 System.out.println("Login i suksesshem si Menaxher: " + username);
	                 hapDritarenMenaxher();
	                 return;//Log In i sukseshem,dil nga metoda
	            }
	        }

	        //3.nese nuk u gjet as si Admin,Arketar apo Menaxher
	        System.out.println("Tentative e deshtuar per login: " + username);
	        showAlert(AlertType.ERROR, "Hyrja Deshtoi", "Emri ose fjalekalimi i gabuar.");
	        // Pastro fushen e passwordit per siguri
	        password.clear();
	    }
	  
	   //metoda per te  hapur dritaren e adminstratorit 
	    public void  hapDritarenAdmin() { 
	    try {
	    	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
            Stage currentStage = (Stage) emri.getScene().getWindow();
            currentStage.close();
    		
    		//ngarkon dhe hap faqen e hyrjes (Log In) 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/dritare_04.fxml"));
            Parent root = loader.load();

            //krojojme nje skene te re per faqen kryesore
            Scene scene = new Scene(root, 1269,483);
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
	    
	    //metoda per te hapur dritaren Arketari  
	    //modifikim duke kaluar emrin e arketarit qe logon 
	    public void hapDritarenArketar(String username) {  
	    	try {
		    	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
	            Stage currentStage = (Stage) emri.getScene().getWindow();
	            currentStage.close();
	    		
	    		//ngarkon dhe hap faqen e hyrjes (Log In) 
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application1/dritare_01.fxml"));
	            Parent root = loader.load();
	            
               //merr kontrollerin e dritares se Arketarit PAS ngarkimi 
	            application1.Kontroll arketarController = loader.getController();
	            if(arketarController != null) {
	            	arketarController.setArketarAktual(username); //vendos emrin e arketarit
	            	System.out.println("Emri i arketarit '" + username + "' u dergua tek kontrolleri i Arketarit."); //per konfirmim
	            }else {
	                 System.err.println("Nuk mund te merret kontrolleri per dritaren e Arketarit!");
	                 showAlert(AlertType.ERROR, "Gabim Ngarkimi", "Problem ne konfigurimin e panelit te Arketarit.");
	                  return; //ndal veprimin
	            }
	         
	            
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
	         }catch (Exception e) { // Kap gabime te tjera te papritura
	             e.printStackTrace();
	             showAlert(AlertType.ERROR, "Gabim i Papritur", "Ndodhi një gabim i papritur: " + e.getMessage());
	        }
	    }

	    
	    //metoda per te hapur dritaren e menaxherit
	    public void  hapDritarenMenaxher() {
	    	try {
		    	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
	            Stage currentStage = (Stage) emri.getScene().getWindow();
	            currentStage.close();
	    		
	    		//ngarkon dhe hap faqen e hyrjes (Log In) 
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application3/dritare_03.fxml"));
	            Parent root = loader.load();

	            //krojojme nje skene te re per faqen kryesore
	            Scene scene = new Scene(root, 1535, 766);
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
	    
	    
	    //metoda ndihmese per te shfaqur Alert
        private void MbylljeAlert(AlertType alertType, String title, String content) {
        //per audio
        AudioClip clip = new AudioClip("file:/C:/Windows/Media/Windows%20Unlock.wav");
        clip.play();
        	
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); //pa header text
        alert.setContentText(content);
        alert.showAndWait();
    }
	    
	    
	    
	    //metode per te dale nga programi
	    public void dilNgaProgrami() {
	    	
	    	
	    	//mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage dhe e mbyll
	        Stage stage = (Stage) emri.getScene().getWindow();
	        stage.close();
	        MbylljeAlert(AlertType.INFORMATION, "Informacion","Faleminderit qe perdoret kete softuer.");
	        
	    }
	    
}
