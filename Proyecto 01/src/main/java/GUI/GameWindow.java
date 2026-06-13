package GUI;

import Control.ChessController;
import Control.ChessController.GraphicPiece;
import itcr.Logic.ChessColor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static java.lang.Math.abs;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author lito
 */
public class GameWindow extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form GameWindow
     */
    ChessController control = ChessController.getInstance();
    Point boardPos;
    List<GraphicPiece> pieces;
    public BoardPanel boardPanel;
    JLabel whiteLabel;
    JLabel blackLabel;
    JButton saveButton;
    JButton loadButton;
    JButton newButton;
    JButton forfeitButton;
    JButton deselectButton;
    
    public GameWindow() {
        //Initialization
        initComponents();
        setTitle("Chess");
        setMinimumSize(new Dimension(512, 512));
        setResizable(false);
        setLayout(null);
        Font font = new Font("Courier New", Font.BOLD, 18);
        
        //Board 
        pieces = control.getPiecesList();
        boardPanel = new BoardPanel(this);
        boardPanel.setVisible(true);
        boardPanel.setAlignmentX(CENTER_ALIGNMENT);
        boardPanel.setAlignmentY(CENTER_ALIGNMENT);
        boardPanel.setBounds((int) (getWidth() / 2.5f - boardPanel.getWidth() / 2),
            (getHeight() - getInsets().top) / 2 - boardPanel.getHeight() / 2,
            ChessController.TILE_SIZE * control.getBoardRanks() + 6,
            ChessController.TILE_SIZE * control.getBoardFiles() + 6
        );
        add(boardPanel);
        
        //Player names
        blackLabel = new JLabel(control.getPlayerName(ChessColor.BLACK));
        blackLabel.setFont(font);
        blackLabel.setBounds(boardPanel.getX(), boardPanel.getY() - 25, 300, 20);
        add(blackLabel);
        
        whiteLabel = new JLabel(control.getPlayerName(ChessColor.WHITE));
        whiteLabel.setFont(new Font("Courier New", Font.BOLD, 18));
        whiteLabel.setBounds(boardPanel.getX() + boardPanel.getWidth() - 300, boardPanel.getY() + boardPanel.getHeight() + 5, 300, 20);
        whiteLabel.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        whiteLabel.setHorizontalAlignment(JLabel.RIGHT);
        add(whiteLabel);
        
        //Buttons
        saveButton = new JButton("Save");
        saveButton.setBounds(boardPanel.getX() + boardPanel.getWidth() + 10, boardPanel.getY(), getWidth() - boardPanel.getWidth() - 40, 30);
        saveButton.setFont(font);
        saveButton.addActionListener(this);
        add(saveButton);
        
        loadButton = new JButton("Load");
        loadButton.setBounds(saveButton.getX(), saveButton.getY() + 50, saveButton.getWidth(), saveButton.getHeight());
        loadButton.setFont(font);
        loadButton.addActionListener(this);
        add(loadButton);
        
        newButton = new JButton("New Game");
        newButton.setBounds(loadButton.getX(), loadButton.getY() + 50, loadButton.getWidth(), loadButton.getHeight());
        newButton.setFont(font);
        newButton.addActionListener(this);
        add(newButton);
        
        forfeitButton = new JButton("Forfeit");
        forfeitButton.setBounds(newButton.getX(), newButton.getY() + 50, newButton.getWidth(), newButton.getHeight());
        forfeitButton.setFont(font);
        forfeitButton.addActionListener(this);
        add(forfeitButton);
        
        deselectButton = new JButton("Deselect");
        deselectButton.setBounds(forfeitButton.getX(), forfeitButton.getY() + 50, forfeitButton.getWidth(), forfeitButton.getHeight());
        deselectButton.setFont(font);
        deselectButton.addActionListener(this);
        add(deselectButton);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(saveButton)) {
            JFileChooser chooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Chess File", "chess");
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File(""));
            int response = chooser.showSaveDialog(this);
            
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    ChessController.SaveData(file.getAbsolutePath() + ".chess");
                    JOptionPane.showMessageDialog(this, "Game saved successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
                }
            }
            //JOptionPane.showMessageDialog(this, "Saved!");
        }
        if (e.getSource().equals(loadButton)) {
            JFileChooser chooser = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Chess File", "chess");
            chooser.setFileFilter(filter);
            chooser.setCurrentDirectory(new File(""));
            int response = chooser.showOpenDialog(this);
            
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    ChessController.LoadData(file.getAbsolutePath());
                    control = ChessController.getInstance();
                    pieces = control.getPiecesList();
                    boardPanel.refresh();
                    blackLabel.setText(control.getPlayerName(ChessColor.BLACK));
                    whiteLabel.setText(control.getPlayerName(ChessColor.WHITE));
                    control.printBoard();
                    JOptionPane.showMessageDialog(this, "Game loaded successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getLocalizedMessage());
                }
            }
            //JOptionPane.showMessageDialog(this, "Saved!");
        }
        if (e.getSource().equals(newButton)) {
            if (
                JOptionPane.showConfirmDialog(
                       this, "All unsaved progress will be lost.\nStart a new game anyway?",
                        "Warning", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION
            ) {
                new PlayerSetupWindow().setVisible(true);
                dispose();
            }
        }
        if (e.getSource().equals(forfeitButton)) {
            String winnerName = control.getOtherPlayerName();
            if (
                JOptionPane.showConfirmDialog(
                       this, String.format("This will end the game immediately, \nresulting in an automatic win for %s.\nDo you wish to forfeit?",
                               winnerName),
                        "Warning", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION
            ) {
                JOptionPane.showMessageDialog(this, winnerName + " wins!");
                new PlayerSetupWindow().setVisible(true);
                dispose();
            }
        }
        if (e.getSource().equals(deselectButton)) {
            boardPanel.deselect();
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

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables

}
