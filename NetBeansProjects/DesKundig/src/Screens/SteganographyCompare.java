package Screens;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Tycha
 */
public class SteganographyCompare extends javax.swing.JFrame {

    JFileChooser kies;
    File directory;
    private File afbeelding;

    public SteganographyCompare() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageComparison = new javax.swing.JLabel();
        imageOrigineel = new javax.swing.JLabel();
        tekstOrigineel = new javax.swing.JTextField();
        btnBrowse2 = new javax.swing.JButton();
        lblFileChooser1 = new javax.swing.JLabel();
        lblFileChooser3 = new javax.swing.JLabel();
        imageEncrypted = new javax.swing.JLabel();
        tekstEncrypted = new javax.swing.JTextField();
        btnBrowse4 = new javax.swing.JButton();
        buttonCompare = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        imageComparison.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        imageOrigineel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tekstOrigineel.setForeground(new java.awt.Color(0, 102, 204));
        tekstOrigineel.setName("tekstOrigineel"); // NOI18N

        btnBrowse2.setText("Browse..");
        btnBrowse2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectOriginal(evt);
            }
        });

        lblFileChooser1.setText("Select original image");

        lblFileChooser3.setText("Select original image");

        imageEncrypted.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tekstOrigineel, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(97, 97, 97))
                            .addComponent(imageOrigineel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBrowse2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFileChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(imageEncrypted, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tekstEncrypted)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBrowse4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 23, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imageComparison, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonCompare, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tekstOrigineel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBrowse2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageOrigineel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblFileChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tekstEncrypted, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBrowse4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imageEncrypted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imageComparison, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCompare, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectOriginal(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectOriginal
        kies = new JFileChooser("./");                       //zet standaard pad voor het bestand te kiezen dat ontcijfert moet worden
        kies.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);    //men gaat hier de manier van kiezen koppelen aan de kiezer, dus zowel bestanden als mappen
        int Terugkeerwaarde = kies.showOpenDialog(this);
        if (Terugkeerwaarde == JFileChooser.APPROVE_OPTION) {
            afbeelding = kies.getSelectedFile();
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
            afbeelding = kies.getSelectedFile();
            String outFilePath = kies.getSelectedFile().toString();
            directory = kies.getSelectedFile();
            tekstEncrypted.setText(outFilePath);
    }//GEN-LAST:event_selectEncrypted
    }
    private void buttonCompareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCompareActionPerformed
        // ZIE FB LINK VAN STEVEN!!!!!!!
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
    private javax.swing.JLabel imageComparison;
    private javax.swing.JLabel imageEncrypted;
    private javax.swing.JLabel imageOrigineel;
    private javax.swing.JLabel lblFileChooser1;
    private javax.swing.JLabel lblFileChooser3;
    private javax.swing.JTextField tekstEncrypted;
    private javax.swing.JTextField tekstOrigineel;
    // End of variables declaration//GEN-END:variables
}
/*private boolean checkDifferenceImage(BufferedImage original, BufferedImage newImg, File f) {
        Color red = new Color(255, 0, 0); //Rode kleur om de verschillen aan te duiden op de afbeelding
        int rgbRed = red.getRGB(); //RGB-waarde opvragen van Color red
        
        try {
            //Nieuwe BufferedImage om de verschillen aan te duiden
            //Hier wordt de originele afbeeldinge gewijzig bij elk verschil
            BufferedImage biDiffImg = original;
            
            for (int x = 0; x < original.getWidth(); x++) { //Breedte
                for (int y = 0; y < original.getHeight(); y++) { //Hoogte
                    if (original.getRGB(x, y) != newImg.getRGB(x, y)) {
                        biDiffImg.setRGB(x, y, rgbRed);
                    }
                }
            }
            ImageIO.write(biDiffImg, "png", f);
            return true;
        }catch (Exception e) {
            System.err.println("Fout in checkDifferenceImage: [" + e.getMessage() + "]");
            return false;
        }
    }*/