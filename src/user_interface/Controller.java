package user_interface;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    public void calculate() throws InterruptedException {
        int stringsA = Integer.parseInt(stringsACountField.getText());
        int columnsA = Integer.parseInt(columnsACountField.getText());
        int stringsB = Integer.parseInt(stringsBCountField.getText());
        int columnsB = Integer.parseInt(columnsBCountField.getText());
        int threadsCount = Integer.parseInt(threadsCountField.getText());
        MatrixMultiplicator multiplicator;
        try {
            long start = System.currentTimeMillis();
            multiplicator = new MatrixMultiplicator(stringsA, columnsA, stringsB, columnsB, threadsCount);
            multiplicator.start();
            multiplicator.join();

            long finish = System.currentTimeMillis();
            long timeConsumedMillis = finish - start;

            this.showMessage(AlertType.INFORMATION, String.valueOf(timeConsumedMillis) + " милисекунд потребовалось.");
        }
        catch (java.lang.OutOfMemoryError e) {
            this.showMessage(AlertType.ERROR, "Недостаточно памяти! Введены слишком большие размеры матриц.");
        }
        catch (Exception e) {
            this.showMessage(
                    AlertType.ERROR,
                    "Матрицы нельзя перемножить. Количество столбцов матрицы А должно быть равно количеству строк матрицы В"
            );
        }
    }

    /**
     * Вывод модального окна с сообщением
     * @param messageType Тип сообщения
     * @param message Текст сообщения
     */
    private void showMessage(AlertType messageType, String message) {
        Alert alert = new Alert(messageType);
        alert.setTitle("Сообщение!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
