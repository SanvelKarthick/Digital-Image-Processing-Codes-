public class Diswave {

    public static void main(String[] args) {
        String inputString = "hello";
        System.out.println("Input String: " + inputString);

        double[] waveletCoefficients = applyHaarWavelet(inputString);

        System.out.println("Wavelet Coefficients:");
        for (double coefficient : waveletCoefficients) {
            System.out.print(coefficient + " ");
        }
    }

    private static double[] applyHaarWavelet(String input) {
        int length = input.length();

        // Convert the characters in the string to double values
        double[] data = new double[length];
        for (int i = 0; i < length; i++) {
            data[i] = (double) input.charAt(i);
        }

        // Apply the Haar wavelet transform
        while (length > 1) {
            int half = length / 2;
            double[] temp = new double[length];

            for (int i = 0; i < half; i++) {
                // Calculate average and difference
                temp[i] = (data[2 * i] + data[2 * i + 1]) / 2.0;
                temp[i + half] = (data[2 * i] - data[2 * i + 1]) / 2.0;
            }

            // Copy the temporary array back to the original data array
            System.arraycopy(temp, 0, data, 0, length);
            length = half;
        }

        return data;
    }
}
