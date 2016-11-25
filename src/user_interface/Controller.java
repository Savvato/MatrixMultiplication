package user_interface;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import matrix.MatrixMultiplicator;

@SuppressWarnings("SpellCheckingInspection")
public class Controller
{
    @FXML
    public TextField stringsACountField;

    @FXML
    public TextField columnsACountField;

    @FXML
    public TextField stringsBCountField;

    @FXML
    public TextField columnsBCountField;

    @FXML
    public TextField threadsCountField;

    public void calculate() {
        int stringsA = Integer.parseInt(stringsACountField.getText());
        int columnsA = Integer.parseInt(columnsACountField.getText());
        int stringsB = Integer.parseInt(stringsBCountField.getText());
        int columnsB = Integer.parseInt(columnsBCountField.getText());
        int threadsCount = Integer.parseInt(threadsCountField.getText());

        MatrixMultiplicator multiplicator = null;
        try {
            multiplicator = new MatrixMultiplicator(stringsA, columnsA, stringsB, columnsB, threadsCount);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Ошибка!");
            alert.setHeaderText(null);
            alert.setContentText("Матрицы нельзя перемножить. Количество столбцов матрицы А должно быть равно количеству строк матрицы В");

            alert.showAndWait();
        }

        /**
         * DEBUGGING
         */
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("DEBUG");
        alert.setHeaderText(null);

        assert multiplicator != null;
        //alert.setContentText(String.valueOf(multiplicator.matrixA[5][1]));
        alert.showAndWait();
        /**
         * END_DEBUGGING
         */
    }
}
