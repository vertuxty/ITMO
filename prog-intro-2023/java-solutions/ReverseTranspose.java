import java.util.Arrays;
import java.util.Scanner;

public class ReverseTranspose {
    public static String[][] fillMatrix(String[][] matrix){
        Scanner sc = new Scanner(System.in);
        int numberOfCurrLine = 0;
        int maxLengthOfLine = 0;
        while (sc.hasNextLine()) {
            String currLine = sc.nextLine();
            Scanner currDigit = new Scanner(currLine);
            String[] tempArr = new String[0];
            int indexDigit = 0;
            while (currDigit.hasNextInt()) {
                if (indexDigit >= tempArr.length) {
                    tempArr = Arrays.copyOf(tempArr, tempArr.length*2 + 1);
                }
                tempArr[indexDigit] = String.valueOf(currDigit.nextInt());
                indexDigit++;
            }
            if (numberOfCurrLine >= matrix.length) {
                matrix = Arrays.copyOf(matrix, matrix.length*2);
            }
            tempArr = Arrays.copyOf(tempArr, indexDigit);
            maxLengthOfLine = Math.max(maxLengthOfLine, tempArr.length);
            if (tempArr.length > 0) {
                matrix[numberOfCurrLine] = tempArr;
                numberOfCurrLine++;
            }
        }
        matrix = Arrays.copyOf(matrix, numberOfCurrLine);
        return toTranspose(matrix, maxLengthOfLine);
    }
    public static void main(String[] args) {
        String[][] matrix = new String[1][1];
        matrix = fillMatrix(matrix);
        printMatrix(matrix);
    }

    public static String[][] toTranspose(String[][] matrix, int maxLengthOfLine){
        String[][] transposeMatrix = new String[maxLengthOfLine][matrix.length]; //Создаем матрицу, где размеры m*n, когда у исходной n*m
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                transposeMatrix[j][i] = matrix[i][j]; //Добавляем элементы в новую матрицу, причем a_ji принимает значение a_ij;
            }
        }
        return transposeMatrix;
    }

    public static void printMatrix(String[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                if (matrix[i][j] != null){
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
