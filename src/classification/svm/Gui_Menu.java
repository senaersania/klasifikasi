/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classification.svm;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

/**
 *
 * @author
 */
public class Gui_Menu extends javax.swing.JFrame {

    private int banyak_data = 25;
    private int parameter = 4;
    private double[][] data = new double[banyak_data][parameter + 1];
    private double[] aktual = new double[banyak_data];
    int sheetNumber = 0;

    /**
     * Creates new form Gui_Menu
     */
    public Gui_Menu() throws IOException, BiffException {
        initComponents();
        input_data();
        normalize();
        // set parameter
        jTextFieldBBLamda.setText("0.001");
        jTextFieldBALamda.setText("10");
        jTextFieldBBGamma.setText("0.1");
        jTextFieldBAGamma.setText("20");
        jTextFieldBBC.setText("1");
        jTextFieldBAC.setText("1000");
        jTextFieldBBVarian.setText("1");
        jTextFieldBAVarian.setText("2000");
        jTextFieldLearningRateSVM.setText("5");
        jTextFieldIterasi.setText("20");
        jTextFieldPartikel.setText("10");
        jTextFieldC1.setText("1");
        jTextFieldC2.setText("1");
        Object data_n[] = {"Recency", "Frequency", "Monetary", "Time", "Class"};
        Object data_new[][] = new Object[data.length][data[0].length];
        DefaultTableModel mdl = new DefaultTableModel(data_n, 0);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data_new[i][j] = data[i][j];
            }
        }
        for (int i = 0; i < data_new.length; i++) {
            mdl.addRow(data_new[i]);
        }
        jTableDataset.setModel(mdl);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    // input data
    public void input_data() throws IOException, BiffException {
        // memilih file dan directorynya

        File f = new File("./xls/data.xls");
        // membaca workbooknya
        Workbook w = Workbook.getWorkbook(f);
        // lalu pilih sheetnya
        Sheet sheet = w.getSheet(sheetNumber);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                Cell cell = sheet.getCell(j, i); // kolom, baris
                data[i][j] = Double.valueOf(cell.getContents());
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < aktual.length; i++) {
            Cell cell = sheet.getCell(4, i);
            aktual[i] = Double.valueOf(cell.getContents());
        }
    }

    //normalisasi
    public void normalize(){
        double[] max = new double[5];
        double[] min = new double[5];
        for (int i = 0; i < parameter; i++) {
            max[i] = 0;
            min[i] = data[i][0];
            for (int j = 0; j < banyak_data; j++) {
//                System.out.println("Data"+i+","+j+" : "+data[i][j]);
                // menetapkan nilai maksimal pada fitur yang diinput
                if (data[j][i] > max[i]) {
                    max[i] = data[j][i];
                }

                // menetapkan nilai minimal
                if (data[j][i] < min[i]) {
                    min[i] = data[j][i];
                }
                System.out.print(data[j][i] + " ");
            }
            System.out.println();
            System.out.println("max " + i + " " + max[i]);
            System.out.println("min " + i + " " + min[i]);
        }

        for (int i = 0; i < parameter; i++) {
            for (int j = 0; j < banyak_data; j++) {
                data[j][i] = (data[j][i] - min[i]) / (max[i] - min[i]);
            }
        }
    }

    // input alpa
    public double[] input_alpa() throws IOException, BiffException {
        // memilih file dan directorynya
        File f = new File("./xls/alpa.xls");
        // membaca workbooknya
        Workbook w = Workbook.getWorkbook(f);
        // lalu pilih sheetnya
        Sheet sheet = w.getSheet(sheetNumber);
        double[] alpa = new double[10];
        for (int i = 0; i < alpa.length; i++) {
            Cell cell = sheet.getCell(0, i); // kolom, baris
            alpa[i] = Double.valueOf(cell.getContents());
        }
        return alpa;
    }

    // input parameter
    public double[] input_parameter() throws IOException, BiffException {
        // memilih file dan directorynya
        File f = new File("./xls/parameter.xls");
        // membaca workbooknya
        Workbook w = Workbook.getWorkbook(f);
        // lalu pilih sheetnya
        Sheet sheet = w.getSheet(sheetNumber);
        double[] parameter = new double[4];
        for (int i = 0; i < parameter.length; i++) {
            Cell cell = sheet.getCell(0, i); // kolom, baris
            parameter[i] = Double.valueOf(cell.getContents());
        }
        return parameter;
    }

    // input b
    public double input_b() throws IOException, BiffException {
        // memilih file dan directorynya
        File f = new File("./xls/b.xls");
        // membaca workbooknya
        Workbook w = Workbook.getWorkbook(f);
        // lalu pilih sheetnya
        Sheet sheet = w.getSheet(sheetNumber);
        double b;
        Cell cell = sheet.getCell(0, 0); // kolom, baris
        b = Double.valueOf(cell.getContents());
        return b;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDataset = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldBBLamda = new javax.swing.JTextField();
        jTextFieldBALamda = new javax.swing.JTextField();
        jTextFieldBBGamma = new javax.swing.JTextField();
        jTextFieldBAGamma = new javax.swing.JTextField();
        jTextFieldBBC = new javax.swing.JTextField();
        jTextFieldBAC = new javax.swing.JTextField();
        jTextFieldBBVarian = new javax.swing.JTextField();
        jTextFieldBAVarian = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldLearningRateSVM = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldIterasi = new javax.swing.JTextField();
        jTextFieldPartikel = new javax.swing.JTextField();
        jTextFieldC1 = new javax.swing.JTextField();
        jTextFieldC2 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableResult = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabelAkurasi = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldRecency = new javax.swing.JTextField();
        jTextFieldFrequency = new javax.swing.JTextField();
        jTextFieldMonetary = new javax.swing.JTextField();
        jTextFieldTime = new javax.swing.JTextField();
        jButton = new javax.swing.JButton();
        jLabelHasil = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableDataset.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTableDataset);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Dataset Pendonor Darah");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(268, 268, 268)
                        .addComponent(jLabel1)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        jTabbedPane2.addTab("Data", jPanel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Batasan SVM");

        jLabel4.setText("Lamda");

        jLabel5.setText("Gamma");

        jLabel6.setText("C");

        jLabel7.setText("Varian");

        jTextFieldBBGamma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBBGammaActionPerformed(evt);
            }
        });

        jTextFieldBAGamma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBAGammaActionPerformed(evt);
            }
        });

        jTextFieldBBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBBCActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Parameter PSO");

        jLabel9.setText("Learning rate SVM");

        jLabel10.setText("Partikel");

        jLabel11.setText("Iterasi");

        jLabel12.setText("C1");

        jLabel13.setText("C2");

        jTextFieldIterasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIterasiActionPerformed(evt);
            }
        });

        jTextFieldPartikel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPartikelActionPerformed(evt);
            }
        });

        jTableResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableResult);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Hasil Training PSO");

        jButton1.setText("PROSES");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("BEST AKURASI:");

        jLabelAkurasi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelAkurasi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAkurasi.setText("%");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                        .addComponent(jLabel4)
                                                        .addGap(18, 18, 18))
                                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addGap(14, 14, 14)))
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addComponent(jLabel6)
                                                    .addGap(42, 42, 42)))
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addGap(16, 16, 16)))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextFieldBBVarian, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                            .addComponent(jTextFieldBBGamma)
                                            .addComponent(jTextFieldBBLamda)
                                            .addComponent(jTextFieldBBC)))
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAkurasi, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextFieldLearningRateSVM, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                    .addComponent(jTextFieldBALamda, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldBAGamma, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldBAC, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldBAVarian, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel13))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextFieldIterasi, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                            .addComponent(jTextFieldPartikel)
                                            .addComponent(jTextFieldC1)
                                            .addComponent(jTextFieldC2)))
                                    .addComponent(jButton1))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldBBLamda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldBALamda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextFieldBBGamma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldBAGamma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(jTextFieldPartikel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jTextFieldBBC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldBAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)
                                    .addComponent(jTextFieldC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jTextFieldBBVarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldBAVarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jTextFieldC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(jTextFieldIterasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jTextFieldLearningRateSVM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAkurasi, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Training", jPanel3);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Recency");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Frequency");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Monetary");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Time");

        jTextFieldFrequency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFrequencyActionPerformed(evt);
            }
        });

        jButton.setText("PROSES");
        jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });

        jLabelHasil.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelHasil.setText("x");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("HASIL:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldRecency)
                            .addComponent(jTextFieldFrequency)
                            .addComponent(jTextFieldMonetary)
                            .addComponent(jTextFieldTime, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(99, 99, 99)
                        .addComponent(jLabelHasil, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jButton)))
                .addContainerGap(241, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(268, 268, 268)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(305, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldRecency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldFrequency, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabelHasil, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMonetary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jButton)
                .addContainerGap(55, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(34, 34, 34)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(208, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Simulasi", jPanel4);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Sistem Klasifikasi Pendonor Darah Dengan SVM dan PSO");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldBBGammaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBBGammaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBBGammaActionPerformed

    private void jTextFieldBAGammaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBAGammaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBAGammaActionPerformed

    private void jTextFieldBBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBBCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBBCActionPerformed

    private void jTextFieldIterasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIterasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIterasiActionPerformed

    private void jTextFieldPartikelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPartikelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPartikelActionPerformed

    // PROSES TRAINING
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // batasan SVM
        double BBLamda = Double.valueOf(jTextFieldBBLamda.getText());
        double BALamda = Double.valueOf(jTextFieldBALamda.getText());
        double BBGamma = Double.valueOf(jTextFieldBBGamma.getText());
        double BAGamma = Double.valueOf(jTextFieldBAGamma.getText());
        double BBC = Double.valueOf(jTextFieldBBC.getText());
        double BAC = Double.valueOf(jTextFieldBAC.getText());
        double BBEpsilon = Double.valueOf(jTextFieldBBVarian.getText());
        double BAEpsilon = Double.valueOf(jTextFieldBAVarian.getText());
        // parameter SVM & PSO
        int learningRate = Integer.valueOf(jTextFieldLearningRateSVM.getText());
        int iterasi = Integer.valueOf(jTextFieldIterasi.getText());
        int partikel = Integer.valueOf(jTextFieldPartikel.getText());
        double c1 = Double.valueOf(jTextFieldC1.getText());
        double c2 = Double.valueOf(jTextFieldC2.getText());
        // proses PSO
        PSO pso = new PSO(BALamda, BALamda, BAGamma, BAGamma, BAC, BAC, BAEpsilon, BAEpsilon, learningRate);
        try {
            pso.main(iterasi, partikel, data, aktual);
        } catch (IOException ex) {
            Logger.getLogger(Gui_Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Gui_Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(Gui_Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[][] result = pso.getGbest();
        double[] value = pso.getFitness();
        Object data_n[] = {"Fitness", "Lamda", "Gamma", "C", "Epsilon"};
        Object data_new[][] = new Object[result.length][data[0].length];
        DefaultTableModel mdl = new DefaultTableModel(data_n, 0);
        for (int i = 0; i < result.length; i++) {
            data_new[i][0] = value[i];
            for (int j = 0; j < result[0].length; j++) {
                data_new[i][j + 1] = result[i][j];
            }
        }
        for (int i = 0; i < data_new.length; i++) {
            mdl.addRow(data_new[i]);
        }
        jTableResult.setModel(mdl);
        jLabelAkurasi.setText(String.valueOf(value[value.length - 1]));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextFieldFrequencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFrequencyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFrequencyActionPerformed

    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActionPerformed
        double alpa[] = null;
        double parameter[] = null;
        double b = 0;
        try {
            alpa = input_alpa();
            parameter = input_parameter();
            b = input_b();
            input_data();
        } catch (IOException ex) {
            Logger.getLogger(Gui_Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(Gui_Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[][] data = new double[1][4];
        data[0][0] = Double.valueOf(jTextFieldRecency.getText());
        data[0][1] = Double.valueOf(jTextFieldFrequency.getText());
        data[0][2] = Double.valueOf(jTextFieldMonetary.getText());
        data[0][3] = Double.valueOf(jTextFieldTime.getText());
        int learningRate = 5;
        ClassificationSVM csvm = new ClassificationSVM(learningRate);
        int hasil = csvm.main_testing(parameter, data, alpa, aktual, this.data, b);
        String result_hasil = "";
        System.out.println(hasil);
        if (hasil > 0) {
            result_hasil = "Bisa Donor";
        } else {
            result_hasil = "Tidak Bisa Donor";
        }
        jLabelHasil.setText(result_hasil);
    }//GEN-LAST:event_jButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gui_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Gui_Menu().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(Gui_Menu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BiffException ex) {
                    Logger.getLogger(Gui_Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAkurasi;
    private javax.swing.JLabel jLabelHasil;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTableDataset;
    private javax.swing.JTable jTableResult;
    private javax.swing.JTextField jTextFieldBAC;
    private javax.swing.JTextField jTextFieldBAGamma;
    private javax.swing.JTextField jTextFieldBALamda;
    private javax.swing.JTextField jTextFieldBAVarian;
    private javax.swing.JTextField jTextFieldBBC;
    private javax.swing.JTextField jTextFieldBBGamma;
    private javax.swing.JTextField jTextFieldBBLamda;
    private javax.swing.JTextField jTextFieldBBVarian;
    private javax.swing.JTextField jTextFieldC1;
    private javax.swing.JTextField jTextFieldC2;
    private javax.swing.JTextField jTextFieldFrequency;
    private javax.swing.JTextField jTextFieldIterasi;
    private javax.swing.JTextField jTextFieldLearningRateSVM;
    private javax.swing.JTextField jTextFieldMonetary;
    private javax.swing.JTextField jTextFieldPartikel;
    private javax.swing.JTextField jTextFieldRecency;
    private javax.swing.JTextField jTextFieldTime;
    // End of variables declaration//GEN-END:variables
}
