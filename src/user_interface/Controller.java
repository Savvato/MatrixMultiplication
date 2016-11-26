package user_interface;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import matrix.MatrixMultiplicator;
import matrix.threads.CalculationThread;

@SuppressWarnings("SpellCheckingInspection")
public class Controller
{
    private final int WRITE_LIMIT = 5;

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

    @FXML
    public TextField timeConsumedMillisField;

    @FXML
    public GridPane grid;

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

            for (CalculationThread thread : multiplicator.threads) {
                thread.join();
            }

            long finish = System.currentTimeMillis();
            long timeConsumedMillis = finish - start;

            this.timeConsumedMillisField.setText(String.valueOf(timeConsumedMillis));

            double[][] result = multiplicator.getResultMatrix();

            for (int i = 0; i < this.WRITE_LIMIT; i++) {
                for (int j = 0; j < this.WRITE_LIMIT; j++) {
                    grid.add(new Label(String.valueOf(result[i][j]) + "  "), i, j);
                }
            }

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
