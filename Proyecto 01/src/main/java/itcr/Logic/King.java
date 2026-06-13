/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcr.Logic;

import java.io.Serializable;
import static java.lang.Math.abs;
import static java.lang.Math.signum;
import javax.swing.JOptionPane;

/**
 *
 * @author lito
 */
//Representation of the King piece from Chess
//Moves one space in any direction
//Can perfrom the special move Castling with a Rook piece
public class King extends Piece implements Serializable {
    private final int MAX_HORIZONTAL_DISTANCE = 1;
    private final int MAX_VERTICAL_DISTANCE = 1;
    
    private int getDistanceToRook(Board board, int sign) throws Exception {
        int distanceToEdge = sign > 0? board.getRanks() - 1 - x : x;
        for (int i = 1; i <= distanceToEdge; i++) {
            if (!board.isSquareEmpty(x + (i * sign), y)) {
                if (board.getPieceInSquare(x + (i * sign), y) instanceof Rook rook) {
                    if (color.equals(rook.color)) {
                        return i;
                    }
                }
            }
        }
        throw new Exception("The rook was not found.");
    }
    
    private boolean canRookPerformCastling(Rook rook) throws Exception {
        return color.equals(rook.color) && rook.firstMove;
    }
    
    public boolean canPerformCastling(Board board, int targetX, int targetY) {
        int horizontalDistance = abs(x - targetX);
        int verticalDistance = abs(y - targetY);
        //Is it the first move?
        if (!firstMove) {
            return false;
        }
        //Is it moving a valid amount of squares for castling?
        if (horizontalDistance != 2 || verticalDistance > 0) {
            return false;
        }
        //Is its rook partner eligible for castling?
        int sign = (int) signum(targetX - x);
        try {
            int distanceToRook = getDistanceToRook(board, sign);
            Rook rook = (Rook) board.getPieceInSquare(x + distanceToRook * sign, y);
            if (!canRookPerformCastling(rook)) {
                return false;
            }
            //Are the squares between it and the rook empty?
            for (int i = 1; i < distanceToRook; i++) {
                if (!board.isSquareEmpty(x + (i * sign), y)) {
                    return false;
                }
                //Is it castling out of, through, or into a check?
                if (isInCheck(board, x + (i * sign), y)) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public King(ChessColor color, int x, int y) {
        super(color, x, y);
    }
    
    public boolean isInCheck(Board board, int targetX, int targetY) throws Exception {
        for (int j = 0; j < board.getFiles(); j++) {
            for (int i = 0; i < board.getRanks(); i++) {
                if (!board.isSquareEmpty(i, j)) {
                    Piece piece = board.getPieceInSquare(i, j);
                    if (!(piece instanceof King)) {
                        boolean differentColor = !color.equals(piece.color);
                        boolean validMove = piece.isMoveAllowed(board, targetX, targetY);
                        if (validMove && differentColor) {
                            return true;
                        }   
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isMoveAllowed(Board board, int targetX, int targetY) throws Exception {
        int horizontalDistance = abs(x - targetX);
        int verticalDistance = abs(y - targetY);
        //Is there a piece of the same color in the target?
        if (!board.isSquareEmpty(targetX, targetY)) {
            Piece pieceInPosition = board.getPieceInSquare(targetX, targetY);
            if (color.equals(pieceInPosition.color)) {
                return false;
            }
        }
        //Can it castle?
        if (canPerformCastling(board, targetX, targetY)) {
            return true;
        }
        //Is it moving into a check?
        if (isInCheck(board, targetX, targetY)) {
            return false;
        }
        //Is it moving a valid amount of squares?
        return verticalDistance <= MAX_HORIZONTAL_DISTANCE && horizontalDistance <= MAX_VERTICAL_DISTANCE;
    }
}