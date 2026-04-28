package admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application { //Main trashogohet (klasa femije) nga Application per te ndertuar aplikacionin JavaFX
	@Override          //dritare Menaxher
	public void start(Stage primaryStage) {//metode kryesore qe ekzekutohet ne JavaFX,merr si argument nje objekt Stage qe perfaqeson dritaren kryesore te aplikacionit
		try {                             
			//ngarkon skedarin FXML qe permban pershkrimin e nderfaqes grafike
			Parent root = FXMLLoader.load(getClass().getResource("dritare_04.fxml"));
			
			//krojojme nje skene me permasa ne pixel qe do jete medhesia e dritares/faqes
			Scene scene = new Scene(root,1269,483);
			
			//ngarkojm nje ikone per dritaren e aplikacionit
			Image icon = new Image(getClass().getResourceAsStream("/Screenshot 2025-03-28 085512.png"));
			primaryStage.getIcons().add(icon);//me metoden add() te klases List shton ikonen ne dritaren e prgramit dhe me getIcon() marrim iazhin
			
			
			primaryStage.setScene(scene);//vendosim skenen e krijuaj ne dritaren kryesore me metoden setScene()
			
			primaryStage.setTitle("CD STORE");//vendosim nje titull dritares
			primaryStage.show();//me metoden show() shfaqim dritaren ne ekran
		} catch(Exception e) { //kapim perjashtimet /gb
			e.printStackTrace();//i shfaqim gb ne konsole 
		}
	}
	
	public static void main(String[] args) {//metoda kryesore qe nis aplikacionin JavaFX
		launch(args);//me metoden launch te klases javafx.application.Application ngarkojem/nisim ekzekutimin
	}               //e aplikacionit duke thirrur metoden start()
}
