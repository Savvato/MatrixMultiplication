package matrix;

import matrix.threads.CalculationThread;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MatrixMultiplicator extends Thread
{
    /**
     * Массив нитей, рассчитывающих результат
     */
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
        if (threadsCount < 1) {
            throw new Exception("Указано некорректное число потоков");
        }
        this.checkInputData(stringsA, columnsA, stringsB, columnsB);
        this.threadsCount = threadsCount;
        this.matrixA = new Matrix(stringsA, columnsA);
        this.matrixB = new Matrix(stringsB, columnsB);

        if (!this.checkAvailabilityForMultiplication()) {
            throw new Exception("Матрицы нельзя перемножить. Количество столбцов матрицы А должно быть равно количеству строк матрицы В");
        }
        resultMatrix = new double[stringsA][columnsB];
    }

    /**
     * Проверка вводимых данных
     *
     * @param stringsA Количество строк матрицы А
     * @param columnsA Количество столбцов матрицы А
     * @param stringsB Количество строк в матрице В
     * @param columnsB Количество столбцов в матрице В
     * @throws Exception Сообщение об ошибке
     */
    private void checkInputData(int stringsA, int columnsA, int stringsB, int columnsB) throws Exception {
        if (
                stringsA < 1 || columnsA < 1
                        ||
                        stringsB < 1 || columnsB < 1) {
            throw new Exception("Указаны некорректные размеры матриц");
        }
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

    /**
     * Получение очереди индексов обрабатываемых строк матрицы А
     *
     * @return Очередь индексов обрабатываемых строк матрицы А
     */
    private PriorityQueue<Integer> getQueue() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < this.matrixA.getMatrix().length; i++) {
            queue.offer(i);
        }
        return queue;
    }
}
