package classification.svm;

;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author
 */


public class PSO {

    private double[][] data;
    private double[] aktual;
    private double[][][] particle;
    private double[][] fitness;
    private double[][] b;
    private double[][] pbest;
    private double[] b_pbest;
    private double[][] gbest;
    private double b_gbest;
    private double[] fitness_pbest;
    private double[] fitness_gbest;
    private double[][] alpa_gbest;
    private double[][] alpa_fitness;
    private double[][] alpa_pbest;
    private double[][][] kecepatan;
    private double[][] w;
    private double w_min = 0.4, w_max = 0.8;
    private double c1 = 1;
    private double c2 = 1;
    private double max, min;
    // batasan parameter
    private double bw_lamda = 0.01;
    private double ba_lamda = 5;
    private double bw_gamma = 0.001;
    private double ba_gamma = 10;
    private double bw_c = 0.01;
    private double ba_c = 10000;
    private double bw_epsilon = 1;
    private double ba_epsilon = 1000;
    private int learningRate = 0;

    public PSO(double bw_lamda, double ba_lamda, double bw_gamma, double ba_gamma, double bw_c, double ba_c, double bw_epsilon, double ba_epsilon, int learningRate) {
        this.bw_lamda = bw_lamda;
        this.ba_lamda = ba_lamda;
        this.bw_gamma = bw_gamma;
        this.ba_gamma = ba_gamma;
        this.bw_c = bw_c;
        this.ba_c = ba_c;
        this.bw_epsilon = bw_epsilon;
        this.ba_epsilon = ba_epsilon;
        this.learningRate = learningRate;
    }

    /* menjalankan pso
     * inisialisasi
     * loop {fitness, update_pbest, update_gbest, hitung kecepatan, update posisi
     */
    public void main(int iter, int partikel, double[][] data, double[] aktual) throws IOException, BiffException, WriteException {
        int n = partikel;
        int iterasi = iter;
        this.data = data;
        this.aktual = aktual;
        int panjang = 4;
        particle = new double[iterasi][n][panjang];
        int tt = 0;
        w = new double[iterasi + 1][panjang];
        fitness = new double[iterasi][n];
        b = new double[iterasi][n];
        pbest = new double[n][panjang];
        gbest = new double[iterasi][panjang];
        fitness_pbest = new double[panjang];
        b_pbest = new double[panjang];
        fitness_gbest = new double[iterasi];
        alpa_gbest = new double[iterasi][data.length];
        alpa_pbest = new double[n][data.length];
        alpa_fitness = new double[n][data.length];
        kecepatan = new double[iterasi + 1][n][panjang];

        inisialisasi_particle();
        while (tt < iterasi) {
            fitness(tt); // hitung fitness
            update_pbest(tt); // update pbest
            update_gbest(tt); // update gbest
            hitung_kecepatan(tt, iterasi); // hitung kecepatan
            update_posisi(tt); // update posisi
//            System.out.println("Gbest: " + fitness_gbest);
            tt++;
        }
        double[] b = new double[1];
        b[0] = b_gbest;
        save(alpa_gbest[alpa_gbest.length - 1], gbest[gbest[0].length - 1], b);
    }

    // save value of weight and beta
    public void save(double[] alpa, double[] parameter, double[] b) throws IOException, WriteException {
        String direct1 = "E:\\LULUS\\Classification SVM\\xls\\alpa.xls";
        String direct2 = "E:\\LULUS\\Classification SVM\\xls\\parameter.xls";
        String direct3 = "E:\\LULUS\\Classification SVM\\xls\\b.xls";
        input(alpa, direct1);
        input(alpa, direct2);
        input(b, direct3);
    }

    // melakukan input data ke excel
    public void input(double[] hasil, String directory) throws IOException, WriteException {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList<String> dt = new ArrayList<String>(hasil.length);
        for (int i = 0; i < hasil.length; i++) {
            dt.add(Double.toString(hasil[i]));
        }
        data.add(dt);
        File fExcel = new File(directory);
        input(fExcel, data);
    }

    // melakukan input data ke arrayList
    public void input(File fExcel, List<ArrayList<String>> data) throws IOException, WriteException {
        WritableWorkbook w2 = null;
        try {
            w2 = Workbook.createWorkbook(fExcel);
            WritableSheet s2 = w2.createSheet("Hasil Optimasi", 0);
            Label lab = null;
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < data.get(i).size(); j++) {
                    lab = new Label(i, j, data.get(i).get(j));
                    s2.addCell(lab);
                }
            }
            w2.write();
            w2.close();
        } catch (Exception e) {
        }
    }

    // mengambil nilai fitness dari SVM
    public double[] getFitness() {
        return fitness_gbest;
    }

    // mengambil nilai gbest dari pbest
    public double[][] getGbest() {
        return gbest;
    }

    // melakukan inisialisasi particle
    public void inisialisasi_particle() {
        // membangkitkan partikel, 0: lamda, 1: x2, 2: x3, 3: x4, 4: b
        for (int i = 0; i < particle[0].length; i++) {
            particle[0][i][0] = Math.random() * (ba_lamda - bw_lamda) + bw_lamda;
            particle[0][i][1] = Math.random() * (ba_gamma - bw_gamma) + bw_gamma;
            particle[0][i][2] = Math.random() * (ba_c - bw_c) + bw_c;
            particle[0][i][3] = Math.random() * (ba_epsilon - bw_epsilon) + bw_epsilon;
        }
//        System.out.println("Posisi Awal");
//        for (int i = 0; i < particle.length; i++) {
//            for (int j = 0; j < particle[0].length; j++) {
//                System.out.print(" "+particle[i][j]);
//            }
//            System.out.println();
//        }
        for (int i = 0; i < kecepatan[0].length; i++) {
            for (int j = 0; j < kecepatan[0][i].length; j++) {
                kecepatan[0][i][j] = 0;
            }
        }
//        System.out.println("Kecepatan");
//        for (int i = 0; i < kecepatan[0].length; i++) {
//            for (int j = 0; j < kecepatan[0][i].length; j++) {
//                System.out.print(" "+kecepatan[0][i][j]);
//            }
//            System.out.println();
//        }
    }

    // menghitung nilai fitness dengan k-means
    public void fitness(int tt) throws IOException, BiffException {
        ClassificationSVM csvm = new ClassificationSVM(learningRate);
        // looping particle 
        for (int i = 0; i < particle[tt].length; i++) {
            fitness[tt][i] = csvm.main_svm(particle[tt][i], data, aktual);
            alpa_fitness[i] = csvm.getAlpa();
            b[tt][i] = csvm.getB();
        }
//        System.out.println("Fitness iterasi ke-"+tt);
//        for (int i = 0; i < fitness.length; i++) {
//            System.out.println(fitness[i]);
//        }
    }

    // melakukan update pbest
    public void update_pbest(int tt) {
        // kondisi awal
        if (tt == 0) {
            // proses melakukan create pbest
            for (int i = 0; i < particle[tt].length; i++) {
                pbest[i] = particle[tt][i];
                alpa_pbest[i] = alpa_fitness[i];
            }
            for (int i = 0; i < fitness_pbest.length; i++) {
                fitness_pbest[i] = fitness[tt][i];
                b_pbest[i] = b[tt][i];
                alpa_pbest[i] = alpa_fitness[i];
            }
        } else {
            // proses melakukan update pbest
            for (int i = 0; i < fitness_pbest.length; i++) {
                if (fitness[tt][i] > fitness_pbest[i]) {
                    pbest[i] = particle[tt][i];
                    fitness_pbest[i] = fitness[tt][i];
                    alpa_pbest[i] = alpa_fitness[i];
                    b_pbest[i] = b[tt][i];
                }
            }
        }
//        System.out.println("Pbest iterasi ke-"+tt);
//        for (int i = 0; i < fitness_pbest.length; i++) {
//            System.out.println(fitness_pbest[i]);
//        }
    }

    // melakukan update nilai gbest
    public void update_gbest(int tt) {
        // kondisi awal
        if (tt == 0) {
            fitness_gbest[tt] = fitness_pbest[0];
            gbest[tt] = pbest[0];
            alpa_gbest[tt] = alpa_pbest[0];
            b_gbest = b[tt][0];
        }
        for (int i = 0; i < fitness_pbest.length; i++) {
            if (fitness_pbest[i] > fitness_gbest[tt]) {
                gbest[tt] = pbest[i];
                fitness_gbest[tt] = fitness_pbest[i];
                alpa_gbest[tt] = alpa_pbest[i];
                b_gbest = b[tt][i];
            }
        }
//        System.out.println("Gbest: " + fitness_gbest);
    }

    // melakukan hitung kecepatan dan juga menggunakan self adaptive
    public void hitung_kecepatan(int tt, int t_max) {
        double v_max[] = new double[particle[0].length];
        double v_min[] = new double[particle[0].length];
        double k = 0;
        // menghitung kecepatan max dan min, Vmax = k*(Xmax - Xmin)/2, dimana k = (0, 1]
        for (int j = 0; j < v_max.length; j++) {
            k = Math.random();
            if (j == v_max.length - 1) {
                v_max[j] = k * (max - 0) / 2;
                v_min[j] = v_max[j] * -1;
            } else {
                v_max[j] = k * (1 - 0) / 2;
                v_min[j] = v_max[j] * -1;
            }
        }
        for (int i = 0; i < w[0].length; i++) {
            w[tt][i] = w_min + (w_max - w_min) * ((t_max - tt) / t_max);
        }
        // Proses menghitung kecepatan partikel ; v = w*v + c1*r1(pbest – xit )+ c2*r2(gbest – x)
        for (int i = 0; i < kecepatan[tt].length; i++) {
            for (int l = 0; l < kecepatan[tt][i].length; l++) {
                double r1 = Math.random();
                double r2 = Math.random();
                kecepatan[tt + 1][i][l] = (w[tt][l] * kecepatan[tt][i][l]) + (c1 * r1 * (pbest[i][l] - particle[tt][i][l])) + (c2 * r2 * (gbest[tt][l] - particle[tt][i][l]));
                // batasan kecepatan
                if (kecepatan[tt + 1][i][l] > v_max[l]) {
                    kecepatan[tt + 1][i][l] = v_max[l];
                } else if (kecepatan[tt + 1][i][l] < v_min[l]) {
                    kecepatan[tt + 1][i][l] = v_min[l];
                }
            }
        }
//        System.out.println("New Kecepatan");
//        for (int i = 0; i < kecepatan[t+1].length; i++) {
//            for (int j = 0; j < kecepatan[t+1][i].length; j++) {
//                System.out.print(" "+kecepatan[t+1][i][j]);
//            }
//            System.out.println();
//        }
    }

    // melakukan update posisi particle
    public void update_posisi(int tt) {
        // x = x + v(t+1)
        for (int i = 0; i < particle[tt].length; i++) {
            for (int k = 0; k < particle[tt][i].length; k++) {
                particle[tt][i][k] += kecepatan[tt + 1][i][k];
            }
        }
//        System.out.println("New Posisi");
//        for (int i = 0; i < particle.length; i++) {
//            for (int j = 0; j < particle[0].length; j++) {
//                System.out.print(" "+particle[i][j]);
//            }
//            System.out.println();
//        }
    }
}
