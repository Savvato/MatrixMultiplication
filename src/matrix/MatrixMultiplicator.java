package matrix;

import matrix.threads.CalculationThread;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MatrixMultiplicator extends Thread
{
    public ArrayList<CalculationThread> threads = new ArrayList<>();
    private Matrix matrixA;
    private Matrix matrixB;
    private double[][] resultMatrix;
    private int threadsCount;

    /**
     * Конструктор Мультипликатора матриц
     *
     * @param stringsA     Количество строк матрицы А
     * @param columnsA     Количество столбцов матрицы А
     * @param stringsB     Количество строк в матрице В
     * @param columnsB     Количество столбцов в матрице В
     * @param threadsCount Количество нитей, рассчитывающих результат
     */
    public MatrixMultiplicator(int stringsA, int columnsA, int stringsB, int columnsB, int threadsCount) throws Exception {
        this.threadsCount = threadsCount;
        this.matrixA = new Matrix(stringsA, columnsA);
        this.matrixB = new Matrix(stringsB, columnsB);

        if (!this.checkAvailabilityForMultiplication()) {
            throw new Exception("Матрицы невозможно перемножить");
        }
        resultMatrix = new double[stringsA][columnsB];
    }

    /**
     * Геттер для матрицы результата
     *
     * @return Результатная матрица
     */
    public double[][] getResultMatrix() {
        return this.resultMatrix;
    }

    /**
     * Проверка возможности перемножения матриц
     *
     * @return True, если количество столбцов матрицы А равно количеству строк матрицы В.
     * False, если количество столбцов матрицы А не равно количеству строк матрицы В
     */
    private boolean checkAvailabilityForMultiplication() {
        return this.matrixA.getMatrix()[0].length == this.matrixB.getMatrix().length;
    }

    /**
     * Инициализация матриц
     *
     * @throws InterruptedException Внезапное прерывание работы программы во время инициализации матриц
     */
    private void initializeMatrix() throws InterruptedException {
        matrixA.initialize((i, j) -> 5.2 * ((i + 1) - (j + 1)));
        matrixB.initialize((i, j) -> ((i + 1) + (j + 1)) / (i + 1));
    }

    public void run() {
        try {
            this.initializeMatrix();
            PriorityQueue<Integer> queue = this.getQueue();

            for (int i = 0; i < this.threadsCount; i++) {
                CalculationThread calculationThread = new CalculationThread(this.matrixA, this.matrixB, queue, this.resultMatrix);

                this.threads.add(calculationThread);

                calculationThread.start();

            }

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private PriorityQueue<Integer> getQueue() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < this.matrixA.getMatrix().length; i++) {
            queue.offer(i);
        }
        return queue;
    }
}
