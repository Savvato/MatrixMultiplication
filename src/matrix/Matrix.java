package matrix;

import matrix.threads.MatrixInitialization;

public class Matrix
{
    private double[][] matrix;

    public Matrix(int strings, int columns) {
        this.matrix = new double[strings][columns];
    }

    public double[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
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

    public void initialize(MatrixCellFunction function) throws InterruptedException {
        MatrixInitialization matrixInitialization = new MatrixInitialization(this.matrix, function);
        matrixInitialization.start();
        matrixInitialization.join();
        this.matrix = matrixInitialization.getResult();
    }
}
