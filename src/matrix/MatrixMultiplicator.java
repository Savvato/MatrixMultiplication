package matrix;

import matrix.threads.MatrixInitialization;

public class MatrixMultiplicator
{
    private double[][] matrixA;
    private double[][] matrixB;

    private double[][] resultMatrix;

    private int threadsCount;

    /**
     * Геттер для матрицы результата
     * @return Результатная матрица
     */
    public double[][] getResultMatrix() {
        return this.resultMatrix;
    }

    /**
     * Конструктор Мультипликатора матриц
     * @param stringsA Количество строк матрицы А
     * @param columnsA Количество столбцов матрицы А
     * @param stringsB Количество строк в матрице В
     * @param columnsB Количество столбцов в матрице В
     * @param threadsCount Количество нитей, рассчитывающих результат
     */
    public MatrixMultiplicator(int stringsA, int columnsA, int stringsB, int columnsB, int threadsCount) throws Exception {
        this.threadsCount = threadsCount;
        this.matrixA = new double[stringsA][columnsA];
        this.matrixB = new double[stringsB][columnsB];
        if (!this.checkAvailabilityForMultiplication()) {
            throw new Exception("Матрицы невозможно перемножить");
        }
        this.initializeMatrix();

        resultMatrix = new double[stringsA][columnsB];
    }

    /**
     * Проверка возможности перемножения матриц
     * @return
     * True, если количество столбцов матрицы А равно количеству строк матрицы В.
     * False, если количество столбцов матрицы А не равно количеству строк матрицы В
     */
    private boolean checkAvailabilityForMultiplication() {
        return this.matrixA[0].length == this.matrixB.length;
    }

    /**
     * Инициализация матриц
     * @throws InterruptedException Внезапное прерывание работы программы во время инициализации матриц
     */
    private void initializeMatrix() throws InterruptedException {
        MatrixInitialization matrixAInitialization = new MatrixInitialization(this.matrixA, (i, j) -> 5.2 * ((i + 1) - (j + 1)));
        matrixAInitialization.start();

        MatrixInitialization matrixBInitialization = new MatrixInitialization(this.matrixB, (i, j) -> ((i + 1) + (j + 1)) / (i + 1));
        matrixBInitialization.start();

        matrixAInitialization.join();
        matrixBInitialization.join();

        this.matrixA = matrixAInitialization.getResult();
        this.matrixB = matrixBInitialization.getResult();
    }

    public void calculateResult() {

    }
}
