package matrix.threads;

import matrix.MatrixCellFunction;

public class MatrixInitialization extends Thread
{
    private double[][] matrix;
    private MatrixCellFunction function;

    public MatrixInitialization(double[][] matrix, MatrixCellFunction function) {
        this.matrix = matrix;
        this.function = function;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.matrix.length; i++) {
            for (int j = 0; j < this.matrix[i].length; j++) {
                this.matrix[i][j] = this.function.calculateCell(i, j);
            }
        }
    }

    /**
     * Геттер для инициализированной матрицы
     * @return Инициализированная матрица
     */
    public synchronized double[][] getResult() {
        return this.matrix;
    }
}
