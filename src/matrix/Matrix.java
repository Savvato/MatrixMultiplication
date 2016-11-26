package matrix;

import matrix.threads.MatrixInitializationThread;

public class Matrix
{
    private double[][] matrix;

    public Matrix(int strings, int columns) {
        this.matrix = new double[strings][columns];
    }

    public double[][] getMatrix() {
        return this.matrix;
    }

    public double[] getStringOfMaitrix(int index) {
        return this.matrix[index];
    }

    public double[] getColumnOfMatrix(int index) {
        double[] column = new double[this.matrix.length];
        for (int i = 0; i < this.matrix.length; i++) {
            column[i] = this.matrix[i][index];
        }
        return column;
    }

    /**
     * Инициализация матрицы в отдельном потоке
     * @param function Функция расчета значений элементов матрицы
     * @throws InterruptedException Прерывание потока
     */
    public void initialize(MatrixCellFunction function) throws InterruptedException {
        MatrixInitializationThread matrixInitializationThread = new MatrixInitializationThread(this.matrix, function);
        matrixInitializationThread.start();
        matrixInitializationThread.join();
    }

    /**
     * Перемножение массивов
     * @param firstArray Первый массив
     * @param secondArray Второй массив
     * @return Результат перемножения элементов массивов
     */
    public static double multipleArrays(double[] firstArray, double[] secondArray) {
        double result = 0;
        for (int i = 0; i < firstArray.length; i++) {
            result += firstArray[i] * secondArray[i];
        }
        return result;
    }
}
