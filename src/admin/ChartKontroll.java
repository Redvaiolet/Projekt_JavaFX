package admin;



import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ChartKontroll {

	@FXML
	private PieChart pieChart;

	@FXML
	public Button Admin;

	private PieChart.Data teArdhuratChart; //fushe per te ardhurat
	private PieChart.Data kostotChart; // fushe per kostot
	private double teArdhurat; //fushe per ruajtjen e te ardhurave
	private double kostot; // fushe per ruajtjen e kostove

	//metoda ndihmese per te shfaqur Alert
    private void showAlertDialog(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
	
	@FXML
	public void initialize() {
	    ObservableList<TeDhenatFinanciare> statistika = AdminKontroll.getListaTeDhenatFinanciare();

	    //parrim rrshtin e pare tek tabela Te dhenat
	    TeDhenatFinanciare financat = statistika.get(0);
	    teArdhurat = financat.getTeArdhurat();
	    kostot = financat.getKostot();

	    //krijimi i listes per PieChart
	    ObservableList<PieChart.Data> pieChartStatisitikat = FXCollections.observableArrayList();

	    //mbushja direkte e te dhenave nese kane vlere me te madhe se 0
	    if (teArdhurat > 0) {
	        teArdhuratChart = new PieChart.Data("Te Ardhurat", teArdhurat);
	        pieChartStatisitikat.add(teArdhuratChart);
	    }else {
	    	showAlertDialog(AlertType.WARNING, "Gabim!", "Te Ardhurat dueht te jene me te medha se 0 per te shfaqur grafikun.");
	    	return;
	    }

	    if (kostot > 0) {
	        kostotChart = new PieChart.Data("Kostot", kostot);
	        pieChartStatisitikat.add(kostotChart);
	    }else {
	    	showAlertDialog(AlertType.WARNING, "Gabim!", "Kostot dueht te jene me te medha se 0 per te shfaqur grafikun.");
	    }

	    //vendosim te dennat ne PieChart direkt
	    pieChart.setData(pieChartStatisitikat);

	    
	    pieChart.setStartAngle(90); //fillo mbushjen nga maja e grafikut 
	 
	}


	// metoda per te hapur dritaren e adminstratorit
	public void hapDritarenAdmin() {
		try {
			// mbyll dritaren aktuale duke e kthyer nje scene ne window dhe e merr si stage
			// dhe e mbyll
			Stage currentStage = (Stage) pieChart.getScene().getWindow();
			currentStage.close();

			// ngarkon dhe hap faqen e hyrjes (Log In)
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/dritare_04.fxml"));
			Parent root = loader.load();

			// krojojme nje skene te re per faqen kryesore
			Scene scene = new Scene(root, 1269, 483);
			Stage stage_i_ri = new Stage();

			// i vendosim nje imazh dritares
			stage_i_ri.getIcons().add(new Image(getClass().getResourceAsStream("/Screenshot 2025-03-28 085512.png")));

			// caktojme skene dhe titullin e dritares
			stage_i_ri.setScene(scene);
			stage_i_ri.setTitle("CD STORE");
			// shfaq dritaren
			stage_i_ri.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}