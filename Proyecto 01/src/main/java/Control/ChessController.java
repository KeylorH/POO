/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Exceptions.InvalidPieceSelectionException;
import Exceptions.PieceNotFoundException;
import itcr.Logic.Board;
import itcr.Logic.ChessColor;
import itcr.Logic.King;
import itcr.Logic.Pawn;
import itcr.Logic.Piece;
import itcr.Logic.PieceTypes;
import itcr.Logic.Player;
import itcr.Logic.Rook;
import itcr.Logic.SimplePieceFactory;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static java.lang.Math.signum;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author lito
 */
//Singleton, controller class for the Chess system
public class ChessController implements Serializable {
    private static ChessController instance;
    private final int MAX_PLAYERS = 2;
    private SimplePieceFactory pieceFactory;
    private Board board;
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean pawnAwaitingPromotion = false;
    
    private ChessController() {
        pieceFactory = new SimplePieceFactory();
        board = new Board();
        players = new ArrayList(MAX_PLAYERS);
        currentPlayerIndex = 0;
    }
    
    private Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
    
    private Player getOtherPlayer() {
        return players.get((currentPlayerIndex + 1) % MAX_PLAYERS);
    }
    
    private void nextTurn() {
        ChessColor c = getCurrentPlayer().getColor();
        //Disable en passant
        for (int j = 0; j < board.getFiles(); j++) {
            for (int i = 0; i < board.getRanks(); i++) {
                try {
                    Piece p = board.getPieceInSquare(i, i);
                    if (p instanceof Pawn pawn) {
                        if (c.equals(pawn.getColor())) {
                            pawn.disableEnPassant();
                        }
                    }
                } catch (Exception e) {
                    
                }
            }
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % MAX_PLAYERS;
    }
    
    public static ChessController getInstance() {
        if (instance == null) {
            instance = new ChessController();
        }
        return instance;
    }
    
    public static final int TILE_SIZE = 48;
    
    //Struct that can be shared with the GUI to display the pieces
    //and interact with them without having access to private properties
    public class GraphicPiece implements Serializable {
        public final PieceTypes type;
        public final ChessColor color;
        public final int x;
        public final int y;
        public BufferedImage image;
        public Image sprite;
        public GraphicPiece(PieceTypes type, ChessColor color, int x, int y) throws IOException {
            this.type = type;
            this.color = color;
            this.x = x;
            this.y = y;
            String path = "src/main/resources/graphics/";
            String colorName = color.equals(ChessColor.WHITE)? "Yellow" : "Purple";
            image = type.equals(PieceTypes.PAWN)?
                        ImageIO.read(new FileInputStream(path + colorName + "P.png")) :
                    type.equals(PieceTypes.KING)?
                        ImageIO.read(new FileInputStream(path + colorName + "K.png")) :
                    type.equals(PieceTypes.QUEEN)?
                        ImageIO.read(new FileInputStream(path + colorName + "Q.png")) :
                    type.equals(PieceTypes.ROOK)?
                        ImageIO.read(new FileInputStream(path + colorName + "R.png")) :
                    type.equals(PieceTypes.KNIGHT)?
                        ImageIO.read(new FileInputStream(path + colorName + "N.png")) :
                    ImageIO.read(new FileInputStream(path + colorName + "B.png"));
            sprite = image.getScaledInstance(TILE_SIZE, TILE_SIZE, BufferedImage.SCALE_SMOOTH);
        }
        public void paint(Graphics2D g) {
            g.drawImage(sprite, x * TILE_SIZE + 3, y * TILE_SIZE + 3, null);
        }
    }
    
    public String getPlayerName(ChessColor color) {
        Player p = color.equals(ChessColor.WHITE)? players.get(0) : players.get(1);
        return p.getName();
    }
    
    public String getCurrentPlayerName() {
        Player p = getCurrentPlayer();
        return p.getName();
    }
    
    public String getOtherPlayerName() {
        Player p = getOtherPlayer();
        return p.getName();
    }
    
    //Returns a list of GraphicPiece objects that mimic the Piece objects in the board
    public List<GraphicPiece> getPiecesList() {
        List<GraphicPiece> result = new ArrayList();
        for (int j = 0; j < board.getFiles(); j++) {
            for (int i = 0; i < board.getRanks(); i++) {
                try {
                    Piece p = board.getPieceInSquare(i, j);
                    System.out.println(p.getClass().getName());
                    PieceTypes type =
                            p.getClass().getName().equals("itcr.Logic.Pawn")? PieceTypes.PAWN :
                            p.getClass().getName().equals("itcr.Logic.King")? PieceTypes.KING :
                            p.getClass().getName().equals("itcr.Logic.Queen")? PieceTypes.QUEEN :
                            p.getClass().getName().equals("itcr.Logic.Rook")? PieceTypes.ROOK :
                            p.getClass().getName().equals("itcr.Logic.Knight")? PieceTypes.KNIGHT :
                            p.getClass().getName().equals("itcr.Logic.Bishop")? PieceTypes.BISHOP :
                            PieceTypes.NONE;
                    try {
                        result.add(new GraphicPiece(type, p.getColor(), i, j));
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } catch (PieceNotFoundException e) {
                    
                }
            }
        }
        return result;
    }
    
    public int getBoardRanks() {
        return board.getRanks();
    }
    
    public int getBoardFiles() {
        return board.getFiles();
    }
    
    public boolean isSquareEmpty(int x, int y) {
        return board.isSquareEmpty(x, y);
    }
    
    public boolean isAwaitingPromotion() {
        return pawnAwaitingPromotion;
    }
    
    public void promotePawn(int x, int y, PieceTypes type) {
        pawnAwaitingPromotion = false;
        board.emptySquare(x, y);
        Player pl = getOtherPlayer();
        Piece p = pieceFactory.createPiece(type, pl.getColor(), x, y);
        board.addPiece(p, x, y);
    }
    
    //Returns a list where each element is a list with its elements
    //being the x and y positions of all valid moves; a list of lists of integers
    //Receives the position of the piece to check for as parameters
    public List<List> getValidMoves(int x, int y) throws Exception {
        List<List> result = new ArrayList();
        Piece p = board.getPieceInSquare(x, y);
        for (int j = 0; j < board.getFiles(); j++) {
            for (int i = 0; i < board.getRanks(); i++) {
                List<Integer> pos = new ArrayList(2);
                try {
                    if (p.isMoveAllowed(board, i, j)) {
                        pos.add(i);
                        pos.add(j);
                        result.add(pos);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ChessController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }
    
    public boolean doesPieceInSquareBelongToPlayer(int x, int y) throws PieceNotFoundException {
        Piece p = board.getPieceInSquare(x, y);
        ChessColor playerColor = getCurrentPlayer().getColor();
        return playerColor.equals(p.getColor());
    }
    
    //Creates a new board with the initial layout of a Chess game
    //Creates both player objects and assigns them a color and name
    public void newGame(String whiteName, String blackName) {
        board = new Board();
        String whitePlayerName = whiteName.isBlank()? "White" : whiteName;
        String blackPlayerName = blackName.isBlank()? "Black" : blackName;
        players.clear();
        players.add(new Player(whitePlayerName, ChessColor.WHITE));
        players.add(new Player(blackPlayerName, ChessColor.BLACK));
        currentPlayerIndex = 0;
        int blackY = 0;
        int whiteY = board.getFiles() - 1;
        for (int i = 0; i < board.getRanks(); i++) {
            //Add pawns
            Piece p = pieceFactory.createPiece(PieceTypes.PAWN, ChessColor.BLACK, i, blackY + 1);
            board.addPiece(p, i, blackY + 1);
            p = pieceFactory.createPiece(PieceTypes.PAWN, ChessColor.WHITE, i, whiteY - 1);
            board.addPiece(p, i, whiteY - 1);
            switch (i) {
                case 0, 7 -> {
                    //Add rooks
                    p = pieceFactory.createPiece(PieceTypes.ROOK, ChessColor.BLACK, i, blackY);
                    board.addPiece(p, i, blackY);
                    p = pieceFactory.createPiece(PieceTypes.ROOK, ChessColor.WHITE, i, whiteY);
                    board.addPiece(p, i, whiteY);
                }
                case 1, 6 -> {
                    //Add knights
                    p = pieceFactory.createPiece(PieceTypes.KNIGHT, ChessColor.BLACK, i, blackY);
                    board.addPiece(p, i, blackY);
                    p = pieceFactory.createPiece(PieceTypes.KNIGHT, ChessColor.WHITE, i, whiteY);
                    board.addPiece(p, i, whiteY);
                }
                case 2, 5 -> {
                    //Add bishops
                    p = pieceFactory.createPiece(PieceTypes.BISHOP, ChessColor.BLACK, i, blackY);
                    board.addPiece(p, i, blackY);
                    p = pieceFactory.createPiece(PieceTypes.BISHOP, ChessColor.WHITE, i, whiteY);
                    board.addPiece(p, i, whiteY);
                }
                case 3 -> {
                    //Add queens
                    p = pieceFactory.createPiece(PieceTypes.QUEEN, ChessColor.BLACK, i, blackY);
                    board.addPiece(p, i, blackY);
                    p = pieceFactory.createPiece(PieceTypes.QUEEN, ChessColor.WHITE, i, whiteY);
                    board.addPiece(p, i, whiteY);
                }
                case 4 -> {
                    //Add kings
                    p = pieceFactory.createPiece(PieceTypes.KING, ChessColor.BLACK, i, blackY);
                    board.addPiece(p, i, blackY);
                    p = pieceFactory.createPiece(PieceTypes.KING, ChessColor.WHITE, i, whiteY);
                    board.addPiece(p, i, whiteY);
                }
            }
        }
    }
    
    private void castle() {
        
    }
    
    public void movePiece(int x, int y, int targetX, int targetY) throws Exception {
    Player pl = getCurrentPlayer();
    try {
        Piece p = board.getPieceInSquare(x, y);
        if (!pl.getColor().equals(p.getColor())) {
            throw new InvalidPieceSelectionException("You must select a piece of your own color.");
        }
        if (p.isMoveAllowed(board, targetX, targetY)) {
            //Handle En Passant 
            if (p instanceof Pawn pn) {
                boolean isEnPassantMove = Math.abs(targetY - y) == 1 && Math.abs(targetX - x) == 1
                        && board.isSquareEmpty(targetX, targetY)
                        && !board.isSquareEmpty(targetX, y)
                        && board.getPieceInSquare(targetX, y) instanceof Pawn
                        && board.getPieceInSquare(targetX, y).getColor() != pn.getColor();

                if (isEnPassantMove) {
                    // Capture the pawn beside 
                    board.emptySquare(targetX, y);
                }
                pn.move(board, targetX, targetY);
                board.emptySquare(x, y);
                board.addPiece(pn, targetX, targetY);

                // Handle promotion
                int promotionY = pn.getColor().equals(ChessColor.WHITE) ? 0 : board.getFiles() - 1;
                if (pn.getY() == promotionY) {
                    pawnAwaitingPromotion = true;
                }
            } else if (p instanceof King k && k.canPerformCastling(board, targetX, targetY)) {
                int sign = (int) Math.signum(targetX - x);

                // Move King
                k.move(board, targetX, targetY);
                board.addPiece(k, targetX, targetY);

                // Move Rook
                int rookX = targetX + 1 * sign;
                int rookTargetX = targetX - 1 * sign;
                Piece r = board.getPieceInSquare(rookX, targetY);
                r.move(board, rookTargetX, targetY);
                board.emptySquare(rookX, y);
                board.addPiece(r, rookTargetX, targetY);
            } else {
                
                p.move(board, targetX, targetY);
                board.addPiece(p, targetX, targetY);
            }
            // Empty the previous square
            board.emptySquare(x, y);
            nextTurn();
        }
    } catch (InvalidPieceSelectionException | PieceNotFoundException e) {
        System.out.println(e.getMessage());
    }
}
    public void printBoard() {
        System.out.println(board.toString());
    }
    
    public static void LoadData(String path) throws ClassNotFoundException, FileNotFoundException, IOException {
        ChessController object;
        try (FileInputStream file = new FileInputStream(path)) {
            ObjectInputStream stream = new ObjectInputStream(file);
            object = (ChessController)stream.readObject();
            stream.close();
        }
        instance = object;
    }
    
    public static void SaveData(String path) throws FileNotFoundException, IOException, Exception  {
        if (instance == null) {
            throw new Exception("There is no data to save.");
        }
        try (FileOutputStream file = new FileOutputStream(path); ObjectOutputStream stream = new ObjectOutputStream(file)) {
            stream.writeObject(instance);
        }
    }
    
    //public Piece DEBUG_getPieceInSquare(int x, int y) throws PieceNotFoundException {
    //    return board.getPieceInSquare(x, y);
    //}
}
