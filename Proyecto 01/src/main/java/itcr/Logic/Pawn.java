/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcr.Logic;

import Exceptions.PieceNotFoundException;
import java.io.Serializable;
import static java.lang.Math.abs;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lito
 */
//Representation of the Pawn piece from Chess
//Moves one space forward, or two on the first move
//Can capture one square diagonally
//Can be promoted onto any piece by reaching the vertical edge of the board
public class Pawn extends Piece implements Serializable {
    private final int MAX_HORIZONTAL_DISTANCE = 1;
    private int maxVerticalDistance;
    private int orientationMultiplier;
    private boolean vulnerableToEnPassant;
    private boolean ableToDoEnPassant;
    
    private void enableNeighborsToEnPassant(Board board) {
        if (x > 0 && !board.isSquareEmpty(x - 1, y)) {
            try {
                if (board.getPieceInSquare(x - 1, y) instanceof Pawn leftPiece) {
                    leftPiece.enableEnPassant();
                    vulnerableToEnPassant = true;
                }
            } catch (PieceNotFoundException ex) {
                Logger.getLogger(Pawn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (x < (board.getRanks() - 1) && !board.isSquareEmpty(x + 1, y)) {
            try {
                if (board.getPieceInSquare(x + 1, y) instanceof Pawn rightPiece) {
                    rightPiece.enableEnPassant();
                    vulnerableToEnPassant = true;
                }
            } catch (PieceNotFoundException ex) {
                Logger.getLogger(Pawn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected void enableEnPassant() {
        ableToDoEnPassant = true;
    }
    
    public Pawn(ChessColor color, int x, int y) {
        super(color, x, y);
        maxVerticalDistance = 2;
        ableToDoEnPassant = false;
        vulnerableToEnPassant = false;
        //If it's created in the y position 1, it should move downwards
        orientationMultiplier = y == 1? 1 : -1;
    }
    
    public void disableEnPassant() {
        ableToDoEnPassant = false;
    }
    
    public boolean isVulnerableToEnPassant() {
        return vulnerableToEnPassant;
    }

    @Override
    public void move(Board board, int x, int y) throws Exception {
        int verticalDistance = abs(this.y - y);
        this.x = x;
        this.y = y;
        if (firstMove && verticalDistance == maxVerticalDistance) {
            enableNeighborsToEnPassant(board);
        }
        firstMove = false;
        //Moving two spaces forward is no longer possible
        maxVerticalDistance = 1;
    }
    
    @Override
    public boolean isMoveAllowed(Board board, int targetX, int targetY) throws Exception {
    int horizontalDistance = abs(x - targetX);
    int verticalDistance = abs(y - targetY);

    // Moving forward
    if ((targetY - y) * orientationMultiplier <= 0) {
        return false;
    }

    // Is there anything in front?
    if (!board.isSquareEmpty(x, y + (1 * orientationMultiplier)) && horizontalDistance == 0) {
        return false;
    }

    // Is it the first move and moving two squares forward?
    if (firstMove && verticalDistance == 2) {
        if (!board.isSquareEmpty(x, y + (2 * orientationMultiplier)) && horizontalDistance == 0) {
            return false;
        }
    }

    // Is it a diagonal attack?
    if (horizontalDistance == MAX_HORIZONTAL_DISTANCE) {
        // Is there a piece to capture?
        if (verticalDistance != 1) {
            return false;
        }
        // If it can perform En Passant, is it capturing a pawn?
        if (board.isSquareEmpty(targetX, targetY)) {
            if (!ableToDoEnPassant) {
                return false;
            }
            // Check if it's capturing a pawn and if that pawn is eligible to be captured
            if (!(board.getPieceInSquare(targetX, targetY - (1 * orientationMultiplier)) instanceof Pawn)) {
                return false;
            } else {
                Pawn pawn = (Pawn) board.getPieceInSquare(targetX, targetY - (1 * orientationMultiplier));
                if (!pawn.isVulnerableToEnPassant()) {
                    return false;
                }
            }
        }
    }

    // Is there a piece of the same color in the target?
    if (!board.isSquareEmpty(targetX, targetY)) {
        Piece pieceInPosition = board.getPieceInSquare(targetX, targetY);
        if (color.equals(pieceInPosition.getColor())) {
            return false;
        }
    }

    // Is it moving a valid amount of squares?
    return verticalDistance <= maxVerticalDistance && horizontalDistance <= MAX_HORIZONTAL_DISTANCE;
}
}
