package classification.svm;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author
 */
public class ClassificationSVM {

    // parameter di SVM
    private double alpa[];
    private double lamda;
    private double gamma;
    private double c;
    private double varian;
    private int iterasi = 1;
    // tempat melakukan penyimpanan data
    private double[][] data;
    private double[][] data_testing;
    private double[] aktual;
    private double b;

    //test tdh
    private int normalisasix = 0;
    
    public ClassificationSVM(int iterasi) {
        this.iterasi = iterasi;
    }
    
    public double getB(){
        return b;
    }

    // proses menghitung SVM
    public int main_testing(double[] particle, double[][] data_testing, double[] alpa, double[] aktual, double[][] data, double b) {
        this.data = data_testing;
        this.alpa = alpa;
        this.aktual = aktual;
        lamda = particle[0];
        gamma = particle[1];
        c = particle[2];
        varian = particle[3];
        // parameter SVM
        normalisasi();
        double[][] kernel;
        int result = 0;
        kernel = hitung_kernel();
        result = getResult(kernel, data_testing, b);
        return result;
    }

    // Proses perhitungan nilai SVM
    public double main_svm(double[] particle, double[][] data, double[] aktual) {
        this.aktual = aktual;
        this.data = data;
        lamda = particle[0];
        gamma = particle[1];
        c = particle[2];
        varian = particle[3];
        // Proses input parameter SVM
        double[][] kernel;
        double[][] D;
        double[] error;
        double[] beta_alpa;
        inisialisasi();
//        normalisasi();
        double result = 0;
        kernel = hitung_kernel();
        D = hitung_d(kernel);
        // melakukan proses iterasi di looping sebanyak jumlah iterasi
//        System.out.println();
        for (int i = 0; i < iterasi; i++) {
            error = hitung_error(D);
            beta_alpa = hitung_beta_alpa(error);
            update_alpa(beta_alpa);
            result = getError(kernel);
        }
        return result;
    }
    
    public void normalisasi(){
        double max = data[0][0];
        double min = data[0][0];
        this.normalisasix++;
        System.out.println("normalisasi : "+normalisasix);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
//                System.out.println("Data"+i+","+j+" : "+data[i][j]);
                // menetapkan nilai maksimal pada fitur yang diinput
                if (data[i][j] > max) {
                    max = data[i][j];
                }

                // menetapkan nilai minimal
                if (data[i][j] < min) {
                    min = data[i][j];
                }

            }
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = (data[i][j] - min) / (max - min);
            }
        }
    }
    
    // getAlpa Proses mendapatkan nilai alpha
    public double[] getAlpa(){
        return alpa;
    }
    
    // Proses menghitung nilai training
    public int getResult(double[][] kernel, double[][] data, double b) {
        int[] hasil = new int[data.length];
        for (int i = 0; i < hasil.length; i++) {
            double result = 0;
            for (int j = 0; j < aktual.length; j++) {
                result += (alpa[i] * aktual[0] * kernel[i][0]) + b;
                if (result < 0) {
                    hasil[i] = -1;
                } else {
                    hasil[i] = 1;
                }
            }
        }
        return hasil[0];
    }

    // Proses menghitung training
    public double getError(double[][] kernel) {
        double[] w_plus = hitung_w_plus(kernel, alpa);
        double[] w_negatif = hitung_w_negatif(kernel, alpa);
        b = hitung_b(aktual, w_plus, w_negatif);
        int[] hasil = new int[data.length];
        // Proses menghitung hasil fungsi x
//        System.out.println("++++++++++++++++++++++++++++");
//        System.out.println();
        for (int i = 0; i < hasil.length; i++) {
            double result = 0;
            for (int j = 0; j < aktual.length; j++) {
                result += (alpa[i] * aktual[j] * kernel[i][j]) + b;
            }
//            System.out.println(result);
            if (result < 0) {
                hasil[i] = -1;
            } else {
                hasil[i] = 1;
            }
//            System.out.print(hasil[i]+" ");
        }
        double benar = 0;
        // Proses menghitung nilai error rate
//        System.out.println("--------------------------------");
        for (int i = 0; i < hasil.length; i++) {
            Double d = new Double(aktual[i]);
            int actual = d.intValue();
//            System.out.println(hasil[i] + " : " + actual);
            if (hasil[i] == actual) {
                benar++;
            }
        }
        double result = (benar * 100) / aktual.length;
//        System.out.println("Hasil: "+result);
        return result;
    }

    // Proses menghitung nilai bias (B)
    public double hitung_b(double[] aktual, double[] w_plus, double[] w_negatif) {
        double b = 0;
        double plus = 0;
        double neg = 0;
        for (int i = 0; i < aktual.length; i++) {
            if (aktual[i] > 0) {
                plus += (alpa[i] * aktual[i] * w_plus[i]);
            } else {
                neg += (alpa[i] * aktual[i] * w_negatif[i]);
            }
        }
        b = -0.5 * (plus + neg);
//        System.out.println("b: " + b);
        return b;
    }

    // Proses menghitung nilai W-
    public double[] hitung_w_negatif(double[][] kernel, double[] alpa) {
        double[] w_negatif = new double[data.length];
        for (int i = 0; i < w_negatif.length; i++) {
            double max = alpa[alpa.length / 2];
            int jj = alpa.length / 2;
            for (int j = alpa.length / 2; j < alpa.length; j++) {
                if (alpa[j] > max) {
                    max = alpa[j];
                    jj = j;
                }
            }
            w_negatif[i] = kernel[i][jj];
        }
//        System.out.println("W-");
//        for (int i = 0; i < w_negatif.length; i++) {
//            System.out.println(w_negatif[i]);
//        }
        return w_negatif;
    }

    // Proses menghitung nilai W+
    public double[] hitung_w_plus(double[][] kernel, double[] alpa) {
        double[] w_plus = new double[data.length];
//        System.out.println(alpa.length+" "+kernel.length);
        for (int i = 0; i < w_plus.length; i++) {
            double max = alpa[0];
            int jj = 0;
            for (int j = 0; j < alpa.length / 2; j++) {
                if (alpa[j] > max) {
                    max = alpa[j];
                    jj = j;
                }
            }
            w_plus[i] = kernel[i][jj];
        }
//        System.out.println("W+");
//        for (int i = 0; i < w_plus.length; i++) {
//            System.out.println(w_plus[i]);
//        }
        return w_plus;
    }

    // Proses menghitung update delta alpha
    public void update_alpa(double[] beta_alpa) {
        for (int i = 0; i < alpa.length; i++) {
            alpa[i] += beta_alpa[i];
        }
//        System.out.println("New Alpa");
//        for (int i = 0; i < alpa.length; i++) {
//            System.out.println(alpa[i]);
//        }
    }

    // Proses memasukkan nilai delta alpha
    public double[] hitung_beta_alpa(double[] error) {
        double[] beta_alpa = new double[data.length];
        for (int i = 0; i < error.length; i++) {
            beta_alpa[i] = Math.min(Math.max(gamma * (1 - error[i]), -1 * alpa[i]), c - alpa[i]);
        }
//        System.out.println("Beta Alpa");
//        for (int i = 0; i < beta_alpa.length; i++) {
//            System.out.println(beta_alpa[i]);
//        }
        return beta_alpa;
    }

    // Proses menghitung nilai error
    public double[] hitung_error(double[][] D) {
        double[] error = new double[data.length];
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D[0].length; j++) {
                error[i] += D[i][j] * alpa[i];
            }
        }
//        System.out.println("Error: ");
//        for (int i = 0; i < error.length; i++) {
//            System.out.println(error[i]);
//        }
        return error;
    }

    // Proses menghitung kernel Dij
    public double[][] hitung_d(double[][] kernel) {
        double[][] D = new double[data.length][data.length];
        for (int i = 0; i < D.length; i++) {
            for (int j = 0; j < D.length; j++) {
                D[i][j] = (aktual[i] * aktual[j]) * (Math.exp(kernel[i][j]) + Math.pow(lamda, 2));
            }
        }
//        System.out.println("D");
//        for (int i = 0; i < D.length; i++) {
//            for (int j = 0; j < D[0].length; j++) {
//                System.out.print(" " + D[i][j]);
//            }
//            System.out.println();
//        }
        return D;
    }

    // Proses menghitung kernel RBF
    public double[][] hitung_kernel() {
        double[][] kernel = new double[data.length][data.length];
        int jj = 0;
        while (jj < data.length) {
            for (int k = jj; k < data.length; k++) {
                for (int j = 0; j < data[0].length; j++) {
                    kernel[k][jj] += (Math.pow(data[jj][j] - data[k][j], 2) / (2 * Math.pow(varian, 2)));
                }
                kernel[k][jj] = (-1 * kernel[k][jj]);
            }
            jj++;
        }
        for (int i = 0; i < kernel.length; i++) {
            for (int j = i; j < kernel[0].length; j++) {
                kernel[i][j] = kernel[j][i];
            }
        }
//        System.out.println("Kernel");
//        for (int i = 0; i < kernel.length; i++) {
//            for (int j = 0; j < kernel[i].length; j++) {
//                System.out.print(" " + kernel[i][j]);
//            }
//            System.out.println();
//        }
        return kernel;
    }

    // Proses inisialisasi
    public void inisialisasi() {
        alpa = new double[data.length];
        for (int i = 0; i < alpa.length; i++) {
            alpa[i] = 0;
        }
    }

// FUNGSI MATRIKS
    // fungsi tranfose matriks
    public double[][] transfose() {
        double[][] transfose = new double[data[0].length][data.length];
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data.length; j++) {
                transfose[i][j] = data[j][i];
            }
        }
        return transfose;
    }
}
