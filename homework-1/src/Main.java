import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inData, inArr;
        int len;

        try {
            System.out.println("Input length of array and number of rotated values: x y");
            inData = reader.readLine();
            System.out.println("Input array for change: ");
            inArr = reader.readLine();
            // First task
            moveLeft(strToInts(inArr), strToInts(inData)[1]);
            System.out.println("Input damaged monotone array for automatic repair");
            // Second task
            restoreArray(strToInts(reader.readLine()));
            System.out.println("Input array length");
            len = strToInts(reader.readLine())[0];
            System.out.println("Input array for ranges search");
            // Third task
            rangeSearch(strToInts(reader.readLine()), len);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // make array of ints from String with spaces
    private static int[] strToInts(String inputString) {
        String[] chNumbers = inputString.split("\\s");
        int[] numbers = new int[chNumbers.length];
        int i = 0;
        for (String s : chNumbers) {
            numbers[i++] = Integer.parseInt(s);
        }
        return numbers;
    }
    // make array move left on some positions
    private static void moveLeft(int[] array, int positions) {
        for (int i = 0; i < positions; i++) {
            int tmp = array[0];
            for (int j = 0; j < array.length - 1; j++)
                array[j] = array[j + 1];
            array[array.length - 1] = tmp;
        }
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    private static void restoreArray(int[] array) {
        for (int i = 0; i < array.length - 2; i++) {
            // break down line
            int noMonotone = Math.abs(array[i] - array[i + 1]);
            if (noMonotone > 1) {
                if (array[i] > array[i + 2]) {
                    array[i + 1] = array[i] - 1;
                } else array[i + 1] = array[i] + 1;
            }
        }
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
    private static void rangeSearch(int[] array, int size) {
        // range counter
        int counter = 0;
        // values drop
        int monotone;
        for (int i = 0; i <= size-1; i++) {
            // case for last element
            if (i == (size-1)) {
                if (counter > 0) {
                    System.out.print("[" + array[i - counter] + ".." + array[i] + "]");
                } else System.out.print("[" + array[i] + "]");
            // search on other elements
            } else {
                monotone = Math.abs(array[i + 1] - array[i]);
                if (monotone == 1) counter++;
                // we saw a drop in values
                if ( monotone > 1 ) {
                    if (counter == 0) {
                        System.out.print("[" + array[i] + "]");
                    } else {
                        System.out.print("[" + array[i - counter] + ".." + array[i] + "]");
                        // range closed
                        counter = 0;
                        }
                }
            }
        }
    }
}