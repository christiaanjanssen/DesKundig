package Screens;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Tycha
 */
public class SteganographyCompare extends javax.swing.JFrame {

    JFileChooser kies;
    File directory;
    File afbeelding1, afbeelding2;
    ImageIcon test;

    public SteganographyCompare() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tekstOrigineel = new javax.swing.JTextField();
        btnBrowse2 = new javax.swing.JButton();
        lblFileChooser1 = new javax.swing.JLabel();
        lblFileChooser3 = new javax.swing.JLabel();
        tekstEncrypted = new javax.swing.JTextField();
        btnBrowse4 = new javax.swing.JButton();
        buttonCompare = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 280));
        setResizable(false);

        tekstOrigineel.setForeground(new java.awt.Color(0, 102, 204));
        tekstOrigineel.setName("tekstOrigineel"); // NOI18N

        btnBrowse2.setText("Browse..");
        btnBrowse2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectOriginal(evt);
            }
        });

        lblFileChooser1.setText("Select original image");

        lblFileChooser3.setText("Select encrypted image");

        tekstEncrypted.setForeground(new java.awt.Color(0, 102, 204));
        tekstEncrypted.setName("tekstEncrypted"); // NOI18N

        btnBrowse4.setText("Browse..");
        btnBrowse4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectEncrypted(evt);
            }
        });

        buttonCompare.setText("Compare");
        buttonCompare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCompareActionPerformed(evt);
            }
        });

        jLabel1.setText("The comparison image will launch in a new window.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCompare, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tekstEncrypted)
                                .addComponent(lblFileChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tekstOrigineel, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnBrowse4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnBrowse2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel1))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBrowse4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tekstEncrypted, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFileChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBrowse2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tekstOrigineel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCompare, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectOriginal(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectOriginal
        kies = new JFileChooser("./");                       //zet standaard pad voor het bestand te kiezen dat ontcijfert moet worden
        kies.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);    //men gaat hier de manier van kiezen koppelen aan de kiezer, dus zowel bestanden als mappen
        int Terugkeerwaarde = kies.showOpenDialog(this);
        if (Terugkeerwaarde == JFileChooser.APPROVE_OPTION) {
            afbeelding1 = kies.getSelectedFile();
            String outFilePath = kies.getSelectedFile().toString();
            directory = kies.getSelectedFile();
            tekstOrigineel.setText(outFilePath);
        }
    }//GEN-LAST:event_selectOriginal

    private void selectEncrypted(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectEncrypted
        kies = new JFileChooser("./");                       //zet standaard pad voor het bestand te kiezen dat ontcijfert moet worden
        kies.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);    //men gaat hier de manier van kiezen koppelen aan de kiezer, dus zowel bestanden als mappen
        int Terugkeerwaarde = kies.showOpenDialog(this);
        if (Terugkeerwaarde == JFileChooser.APPROVE_OPTION) {
            afbeelding2 = kies.getSelectedFile();
            String outFilePath = kies.getSelectedFile().toString();
            directory = kies.getSelectedFile();
            tekstEncrypted.setText(outFilePath);
    }//GEN-LAST:event_selectEncrypted
    }
    private void buttonCompareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCompareActionPerformed
        checkDifferenceImage(afbeelding1, afbeelding2);
    }//GEN-LAST:event_buttonCompareActionPerformed

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
            java.util.logging.Logger.getLogger(SteganographyCompare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SteganographyCompare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SteganographyCompare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SteganographyCompare.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SteganographyCompare().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse2;
    private javax.swing.JButton btnBrowse4;
    private javax.swing.JButton buttonCompare;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblFileChooser1;
    private javax.swing.JLabel lblFileChooser3;
    private javax.swing.JTextField tekstEncrypted;
    private javax.swing.JTextField tekstOrigineel;
    // End of variables declaration//GEN-END:variables

    private boolean checkDifferenceImage(File origineel, File vercijferd) {
        Color red = new Color(255, 0, 0); //Rode kleur om de verschillen aan te duiden op de afbeelding
        int rgbRed = red.getRGB(); //RGB-waarde opvragen van Color red

        try {
            //Nieuwe BufferedImage om de verschillen aan te duiden
            //Hier wordt de originele afbeeldinge gewijzig bij elk verschil
            BufferedImage origineelBuffer = ImageIO.read(origineel);
            BufferedImage vercijferdBuffer = ImageIO.read(vercijferd);
            System.out.println("dit zijn de waarden");
            System.out.println(origineelBuffer.toString());
            BufferedImage vergelijkBuffer = new BufferedImage(origineelBuffer.getWidth(), origineelBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int x = 0; x < origineelBuffer.getWidth(); x++) { //Breedte
                for (int y = 0; y < origineelBuffer.getHeight(); y++) { //Hoogte
                    if (origineelBuffer.getRGB(x, y) != vercijferdBuffer.getRGB(x, y)) {
                        vergelijkBuffer.setRGB(x, y, rgbRed);
                    }
                }
            }

            //BufferedImage vergelijkResized = (BufferedImage) vergelijkBuffer.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            test = new ImageIcon(vergelijkBuffer);

//            imageComparison.setIcon(test);
            JLabel picLabel = new JLabel(test);
            JOptionPane.showMessageDialog(null, picLabel, "About", JOptionPane.PLAIN_MESSAGE, null);

            return true;

        } catch (Exception e) {
            System.err.println("Fout in checkDifferenceImage: [" + e.getMessage() + "]");
            return false;
        }
    }
}
/**/