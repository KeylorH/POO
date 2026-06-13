/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package itcr.Logic;

import Control.ChessController;

/**
 *
 * @author Samantha
 */
public class Chess {

    public static void main(String[] args) {
        ChessController control = ChessController.getInstance();
        try {
            control.newGame("", "");
            control.printBoard();
            control.movePiece(1, 1, 1, 3);
            control.movePiece(1, 3, 1, 4);
            control.movePiece(1, 4, 1, 5);
            control.movePiece(1, 5, 2, 6);
            control.printBoard();
        } catch (Exception e) { 
            System.out.println(e.getMessage());
        }
    }
}
