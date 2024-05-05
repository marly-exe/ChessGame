/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import chesscore.*;
import chesscore.PawnPromotion;
import chesscore.Pieces.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author marlymaged
 */
public class ChessPiece {

    private BufferedImage image;
    private int x;
    private int y;
//    private String name;

    public ChessPiece() {
    }

    public ChessPiece(Piece piece, int y, int x) {
        this.x = x;
        this.y = y;
        try {

            if (piece instanceof Rook) {

                if (piece.getOwner() == Player.BLACK) {
                    image = ImageIO.read(new File("BlackRook.png"));
//                    name = "BRook";
                } else {
//                    name = "WRook";
                    image = ImageIO.read(new File("WhiteRook.png"));
                }
            }
            if (piece instanceof King) {

                if (piece.getOwner() == Player.BLACK) {
//                    name = "BKing";
                    image = ImageIO.read(new File("BlackKing.png"));
                } else {
//                    name = "WKing";
                    image = ImageIO.read(new File("WhiteKing.png"));
                }
            }

            if (piece instanceof Queen) {

                if (piece.getOwner() == Player.BLACK) {
//                    name = "BQueen";
                    image = ImageIO.read(new File("BlackQueen.png"));
                } else {
//                    name = "WQueen";
                    image = ImageIO.read(new File("WhiteQueen.png"));
                }
            }

            if (piece instanceof Bishop) {
                if (piece.getOwner() == Player.BLACK) {
//                    name = "BBishop";
                    image = ImageIO.read(new File("BlackBishop.png"));
                } else {
//                    name = "WBishop";
                    image = ImageIO.read(new File("WhiteBishop.png"));
                }
            }

            if (piece instanceof Knight) {
                if (piece.getOwner() == Player.BLACK) {
//                    name = "BKnight";
                    image = ImageIO.read(new File("BlackKnight.png"));
                } else {
//                    name = "WKnight";
                    image = ImageIO.read(new File("WhiteKnight.png"));
                }

            }
            if (piece instanceof Pawn) {
                if (piece.getOwner() == Player.BLACK) {
//                    name = "BPawn";
                    image = ImageIO.read(new File("BlackPawn.png"));
                } else {
//                    name = "WPawn";
                    image = ImageIO.read(new File("WhitePawn.png"));
                }
            }

        } catch (IOException e) {
            System.out.println("cannot load image");
        }

    }

    public void draw(Graphics g, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    static public ArrayList<ChessPiece> putPieces(Graphics g, ClassicChessGame game) {
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        //columns and rows start from top left
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Piece piece = game.getBoard().getBoard()[row][column];
                if (piece != null) {
                    ChessPiece pieceImage;
                      pieceImage = new ChessPiece(piece, row, column);
                    
                    //(7-row) to start filling from bottom left as in array of pieces
                    if(game.getWhoseTurn() == Player.WHITE)
                    {
                        pieceImage.draw(g, (column) * 64, (7 - row) * 64, 64, 64);
                        pieces.add(pieceImage);
                    }
                    else
                    {
                        pieceImage.draw(g, (7 * 64 ) - ((column) * 64), (7 * 64) - ((7 - row) * 64), 64, 64);
                        pieces.add(pieceImage);
                    }
                }
            }
        }
        return pieces;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static void move(Square toSquare, Square fromSquare, ClassicChessGame game, JFrame frame) {
        boolean moveDone;
        Move move ;     
        //condition to know which "Move" constructor to choose
        if (!(game.getPieceAtSquare(fromSquare) instanceof Pawn)) {
            //System.out.println("hi");
            move = new Move(fromSquare, toSquare);
            Piece toSquarePiece = game.getPieceAtSquare(toSquare);
            moveDone = game.makeMove(move);
            //System.out.println("Move Done? " + moveDone);
        } else {
            if (game.isPawnPromotion(fromSquare, toSquare) ) {
               JFrame promotion= new Promotion(toSquare, fromSquare, game, frame);
               promotion.setVisible(true);
            } 
            else{      
                move = new Move(fromSquare, toSquare);
                moveDone = game.makeMove(move);
                
            }
           
        }      
    }
    
}
