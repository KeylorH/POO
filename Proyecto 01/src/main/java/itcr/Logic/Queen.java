/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itcr.Logic;

import Exceptions.PieceNotFoundException;
import java.io.Serializable;
import static java.lang.Math.abs;

/**
 *
 * @author Samantha
 */
//Representation of the Queen piece from Chess
//Moves in any direction any amount of spaces
public class Queen extends Piece implements Serializable {
    //Constructor
    public Queen(ChessColor color, int x, int y){
        super(color, x, y);
        this.x = x;
        this.y = y;
    }
    //Posiciones de Queen
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    //Movimiento normal
    public void move(int targetX, int targetY){
        this.x = targetX;
        this.y = targetY;
    }
               
    //Movimiento es permitido?
    @Override
    public boolean isMoveAllowed(Board board, int targetX, int targetY) throws PieceNotFoundException{
        int horizontalDistance = abs(this.x - targetX);
        int verticalDistance = abs(this.y - targetY);
        //Deben ser movimiento diagonales o en línea recta
        if (horizontalDistance != verticalDistance && horizontalDistance != 0 && verticalDistance != 0){
            return false;
        }
        
        int xMove = (targetX > this.x) ? 1:(targetX < this.x)? -1:0;
        int yMove = (targetY > this.y) ? 1:(targetY < this.y)? -1:0;
        int actualX = this.x + xMove; //Inicializar posiciones
        int actualY = this.y + yMove;
        
        //Realizar recorrido de la pieza
        while (actualX != targetX || actualY != targetY){
            if (!board.isSquareEmpty(actualX, actualY)){
                return false;
            }
            //Avanza en las direcciones
            actualX += xMove;
            actualY += yMove; 
        }

        //Pieza debe ser de diferente color
        if (!board.isSquareEmpty(targetX, targetY)){
            Piece pieceInPosition = board.getPieceInSquare(targetX, targetY);
            if(pieceInPosition.getColor() == this.getColor()){
                return false;
            }
        }
        //Movimiento válido
        return true;    
    }
}