package ro.visualDB.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Created with IntelliJ IDEA.
 * User: Bogdan
 * Date: 25.06.2013
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class StatisticsController {

    @FXML
    Text statisticsInput;

    public void populatePieChart(int tables, int columns, int constraints){
        statisticsInput.setText("Tables no.:" + tables+ "\nColumns no.: " + columns+ "\nConstraints no.: "+constraints);
    }
}
