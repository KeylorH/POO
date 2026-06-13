/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcr.Logic;

import java.io.Serializable;

/**
 *
 * @author Samantha
*/
//Instantiates a concrete Piece object based on the given type
public class SimplePieceFactory implements Serializable {
    public Piece createPiece(PieceTypes piece, ChessColor color, int x, int y){
    return  piece == PieceTypes.PAWN? new Pawn(color, x, y) : 
            piece == PieceTypes.ROOK? new Rook(color, x, y) :       
            piece == PieceTypes.KNIGHT? new Knight(color, x, y) :  
            piece == PieceTypes.BISHOP? new Bishop(color, x, y) :  
            piece == PieceTypes.QUEEN? new Queen(color, x, y) :  
            piece == PieceTypes.KING? new King(color, x, y) :  
            null;
    }
}