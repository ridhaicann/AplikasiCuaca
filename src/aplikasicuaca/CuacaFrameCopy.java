/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasicuaca;

/**
 *
 * @author USER
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;

public class CuacaFrameCopy extends javax.swing.JFrame {

    DefaultTableModel model;

    public CuacaFrameCopy() {
        initComponents();

        // Pastikan tabel punya kolom
       DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kota");
        model.addColumn("Suhu (°C)");
        model.addColumn("Kondisi");
        model.addColumn("Kelembapan (%)");
        tblCuaca.setModel(model);
        this.model = model;

        // Event tombol
        btnCekCuaca.addActionListener(e -> cekCuaca());
        btnSimpanCSV.addActionListener(e -> simpanCSV());
        btnMuatData.addActionListener(e -> muatData());
        btnKeluar.addActionListener(e -> System.exit(0));
    }

 // === CEK CUACA ===
private void cekCuaca() {
    String kota = cmbKota.getSelectedItem().toString();

    try {
        String apiKey = "GUNAKAN API KEY YANG SAYA TARO DIKOMENTAR PENGUMPULAN";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                + kota + "&appid=" + apiKey + "&units=metric";

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject json = new JSONObject(response.toString());
        double suhu = json.getJSONObject("main").getDouble("temp");
        int kelembapan = json.getJSONObject("main").getInt("humidity");
        String kondisi = json.getJSONArray("weather").getJSONObject(0).getString("main");

        // Tampilkan hasil cuaca di label
        lblHasilCuaca.setText("Cuaca di " + kota + ": " + kondisi + 
                " (" + suhu + " °C, Kelembapan: " + kelembapan + "%)");

        // Cek apakah model tabel sudah ada
        if (tblCuaca.getModel() instanceof DefaultTableModel) {
            DefaultTableModel model = (DefaultTableModel) tblCuaca.getModel();
            model.addRow(new Object[]{kota, suhu, kondisi, kelembapan});
        }

        // Jangan pernah buat JTable atau JScrollPane baru di sini
        tblCuaca.revalidate();
        tblCuaca.repaint();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mengambil data: " + e.getMessage());
    }
}



    // === SIMPAN CSV ===
    private void simpanCSV() {
    if (model == null || model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Tidak ada data untuk disimpan.", "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Pilihan: minta user pilih lokasi file (lebih aman)
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Simpan data cuaca sebagai CSV");
    chooser.setSelectedFile(new java.io.File("data_cuaca.csv"));
    int userChoice = chooser.showSaveDialog(this);
    if (userChoice != JFileChooser.APPROVE_OPTION) {
        return; // pengguna batal
    }

    java.io.File file = chooser.getSelectedFile();

    try (FileWriter writer = new FileWriter(file)) {
        // tulis header
        writer.write("Kota,Suhu (°C),Kondisi,Kelembapan (%)\n");

        for (int i = 0; i < model.getRowCount(); i++) {
            // ambil nilai aman (hindari null pointer)
            String kol = model.getValueAt(i, 0) != null ? model.getValueAt(i, 0).toString() : "";
            String suh = model.getValueAt(i, 1) != null ? model.getValueAt(i, 1).toString() : "";
            String kon = model.getValueAt(i, 2) != null ? model.getValueAt(i, 2).toString() : "";
            String kel = (model.getColumnCount() > 3 && model.getValueAt(i, 3) != null) ? model.getValueAt(i, 3).toString() : "";

            // escape koma sederhana (opsional)
            kol = kol.replace(",", " ");
            kon = kon.replace(",", " ");

            writer.write(kol + "," + suh + "," + kon + "," + kel + "\n");
        }

        writer.flush();
        JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke:\n" + file.getAbsolutePath(), "Sukses", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception ex) {
        // tampilkan pesan dan stacktrace ke console untuk debug
        JOptionPane.showMessageDialog(this, "Gagal menyimpan CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

    // === MUAT CSV ===
    private void muatData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data_cuaca.csv"))) {
            String line;
            model.setRowCount(0); // bersihkan tabel dulu
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
            JOptionPane.showMessageDialog(this, "✅ Data berhasil dimuat dari file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Gagal memuat data: " + e.getMessage());
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        panelUtama = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblPilihKota = new javax.swing.JLabel();
        cmbKota = new javax.swing.JComboBox<>();
        btnCekCuaca = new javax.swing.JButton();
        btnSimpanCSV = new javax.swing.JButton();
        btnMuatData = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCuaca = new javax.swing.JTable();
        lblHasilCuaca = new javax.swing.JLabel();
        lblGambarCuaca1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelUtama.setBackground(new java.awt.Color(204, 255, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("APLIKASI CUACA");

        lblPilihKota.setText("Pilih Kota");

        cmbKota.setBackground(new java.awt.Color(242, 242, 242));
        cmbKota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Kota --", "Banjarmasin", "Banjarbaru", "Jakarta", "Bandung", "Depok", "Lampung", " " }));

        btnCekCuaca.setBackground(new java.awt.Color(242, 242, 242));
        btnCekCuaca.setText("CEK CUACA");

        btnSimpanCSV.setBackground(new java.awt.Color(51, 255, 51));
        btnSimpanCSV.setText("SIMPAN");

        btnMuatData.setBackground(new java.awt.Color(242, 242, 242));
        btnMuatData.setText("REFRESH");

        btnKeluar.setBackground(new java.awt.Color(255, 51, 51));
        btnKeluar.setText("KELUAR");

        tblCuaca.setBackground(new java.awt.Color(242, 242, 242));
        tblCuaca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblCuaca);

        lblGambarCuaca1.setText("Hasil Cuaca  :");

        javax.swing.GroupLayout panelUtamaLayout = new javax.swing.GroupLayout(panelUtama);
        panelUtama.setLayout(panelUtamaLayout);
        panelUtamaLayout.setHorizontalGroup(
            panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUtamaLayout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelUtamaLayout.createSequentialGroup()
                            .addComponent(btnCekCuaca)
                            .addGap(18, 18, 18)
                            .addComponent(btnSimpanCSV)
                            .addGap(22, 22, 22)
                            .addComponent(btnMuatData)
                            .addGap(18, 18, 18)
                            .addComponent(btnKeluar))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUtamaLayout.createSequentialGroup()
                            .addComponent(lblGambarCuaca1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(lblHasilCuaca)
                            .addGap(260, 260, 260)))
                    .addGroup(panelUtamaLayout.createSequentialGroup()
                        .addComponent(lblPilihKota, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbKota, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(94, Short.MAX_VALUE))
        );
        panelUtamaLayout.setVerticalGroup(
            panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelUtamaLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(39, 39, 39)
                .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPilihKota)
                    .addComponent(cmbKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCekCuaca, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanCSV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMuatData, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGambarCuaca1)
                    .addGroup(panelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblHasilCuaca)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    
    
    
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
            java.util.logging.Logger.getLogger(CuacaFrameCopy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CuacaFrameCopy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CuacaFrameCopy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CuacaFrameCopy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CuacaFrameCopy().setVisible(true);
            }
        });
    }
    
 // Variables declaration - do not modify                     
    private javax.swing.JButton btnCekCuaca;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnMuatData;
    private javax.swing.JButton btnSimpanCSV;
    private javax.swing.JComboBox<String> cmbKota;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblGambarCuaca1;
    private javax.swing.JLabel lblHasilCuaca;
    private javax.swing.JLabel lblPilihKota;
    private javax.swing.JPanel panelUtama;
    private javax.swing.JTable tblCuaca;
    // End of variables declaration   
    
}