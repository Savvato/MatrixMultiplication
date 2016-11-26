package matrix.threads;

import matrix.Matrix;

import java.util.PriorityQueue;

public class CalculationThread extends Thread
{
    private Matrix matrixA;
    private Matrix matrixB;

    private double[][] resultMatrix;

    private PriorityQueue<Integer> queue;

    public CalculationThread(Matrix matrixA, Matrix matrixB, PriorityQueue<Integer> queue, double[][] resultMatrix) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.queue = queue;
        this.resultMatrix = resultMatrix;
    }

    public void run() {
        while (true) {
            int stringIndex;
            synchronized (queue) {
                if (this.queue.peek() == null) {
                    break;
                }
                stringIndex = this.queue.poll();
            }
            double[] string = this.matrixA.getStringOfMatrix(stringIndex);
            for (int i = 0; i < this.matrixA.getMatrix().length; i++) {
                this.resultMatrix[stringIndex][i] = Matrix.multipleArrays(string, this.matrixB.getColumnOfMatrix(i));
            }
        }
    }
}
