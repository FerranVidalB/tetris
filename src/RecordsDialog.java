
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alu20925473g
 */
public class RecordsDialog extends javax.swing.JDialog {

    private class Record {

        public int record;
        public String name;

        public Record(int record, String name) {
            this.record = record;
            this.name = name;
        }
    }

    private static final String RECORDS_FILE_NAME = "records.txt";
    private int score;
    private JLabel[] recordLabels;
    private int minRecord;
    private ArrayList<Record> listofRecords;

    /**
     * Creates new form RecordsDialog
     */
    public RecordsDialog(java.awt.Frame parent, boolean modal, int score) {
        super(parent, modal);
        initComponents();
        initRecordLabels();
        minRecord = 0;
        this.score = score;
        listofRecords = new ArrayList<Record>();
        try {
            readRecords();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        processRecord();
    }

    public void processRecord() {
        jLabelCurrentScore.setText("Your score: " + score);
        if (score < minRecord) {
            jLabelName.setVisible(false);
            jTextFieldName.setVisible(false);
        }
    }

    private void readRecords() throws IOException {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(RECORDS_FILE_NAME));
            int lineCount = 0;
            String line;
            String[] lineRecord = null;
            while ((line = input.readLine()) != null && (lineCount < 5)) {
                lineRecord = line.split(",");

                recordLabels[lineCount].setText(lineRecord[0] + ": " + lineRecord[1]);
                listofRecords.add(new Record(Integer.parseInt(lineRecord[0]), lineRecord[1]));
                lineCount++;
            }
            if (lineCount > 0 && lineCount>=5) {
                try {
                    minRecord = Integer.parseInt(lineRecord[0]);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (input != null) {
                input.close();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelCurrentScore = new javax.swing.JLabel();
        jLabelRecord1 = new javax.swing.JLabel();
        jLabelRecord2 = new javax.swing.JLabel();
        jLabelRecord3 = new javax.swing.JLabel();
        jLabelRecord4 = new javax.swing.JLabel();
        jLabelRecord5 = new javax.swing.JLabel();
        jButtonOK = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabelName.setText("Name:");

        jTextFieldName.setText("InsertName");

        jLabelCurrentScore.setText("jLabel2");

        jLabelRecord1.setText("0: NoName");

        jLabelRecord2.setText("0: NoName");

        jLabelRecord3.setText("0: NoName");

        jLabelRecord4.setText("0: NoName");

        jLabelRecord5.setText("0: NoName");

        jButtonOK.setText("Ok");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelCurrentScore)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelName)
                        .addGap(91, 91, 91)
                        .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelRecord1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelRecord2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelRecord3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelRecord4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelRecord5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOK)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelCurrentScore)
                .addGap(19, 19, 19)
                .addComponent(jLabelRecord1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRecord2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRecord3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRecord4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelRecord5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelName)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonOK)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveRecord() {
        PrintWriter output = null;
       
        try {
            output = new PrintWriter(new FileWriter(RECORDS_FILE_NAME));
            int lineCounter = 0;
             boolean alreadyWrittenScore = false;
            for (Record record : listofRecords) {
                if (score > record.record && !alreadyWrittenScore) {
                    output.println(score + ", " + jTextFieldName.getText());
                    lineCounter++;
                    alreadyWrittenScore = true;
                }
                if (lineCounter < 5) {
                    output.println(record.record + ", " + record.name);
                    lineCounter++;
                }
                    
            }
            if(!alreadyWrittenScore){
                output.println(score + ", " + jTextFieldName.getText());
            }
        } catch (IOException ex) {
ex.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        // TODO add your handling code here:
        if (score >= minRecord) {
            saveRecord();
           
        }
         dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed
    private void initRecordLabels() {
        recordLabels = new JLabel[5];
        recordLabels[0] = jLabelRecord1;
        recordLabels[1] = jLabelRecord2;
        recordLabels[2] = jLabelRecord3;
        recordLabels[3] = jLabelRecord4;
        recordLabels[4] = jLabelRecord5;
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonOK;
    private javax.swing.JLabel jLabelCurrentScore;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelRecord1;
    private javax.swing.JLabel jLabelRecord2;
    private javax.swing.JLabel jLabelRecord3;
    private javax.swing.JLabel jLabelRecord4;
    private javax.swing.JLabel jLabelRecord5;
    private javax.swing.JTextField jTextFieldName;
    // End of variables declaration//GEN-END:variables
}
