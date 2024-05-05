
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import chesscore.*;
import chesscore.Pieces.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author marlymaged
 */
public class Board implements MouseListener {

    private ClassicChessGame game = new ClassicChessGame();
    private ArrayList<ChessPiece> piecesOnFrame;
    private ChessPiece selectedPiece;
    private ArrayList<Square> validSquares;
    private Square toSquare;
    private Square fromSquare;
    private JFrame frame;
    private boolean firstClick;
    private ChessPiece goToTile;

    public Board() {
        frame = new JFrame("Chess");
        frame.setSize(new Dimension(512, 540));
//        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        JPanel p = new JPanel() {
            @Override
            public void paint(Graphics g) {
                boolean flag = true;

                for (int row = 0; row < 8; row++) {
                    for (int column = 0; column < 8; column++) {
                        if (flag) {
                            g.setColor(Color.WHITE);
                        } else {
                            g.setColor(Color.BLACK);
                        }
                        g.fillRect(row * 64, column * 64, 64, 64);

                        flag = !flag;
                    }
                    flag = !flag;
                }

                piecesOnFrame = ChessPiece.putPieces(g, game);
                if (game.getGameStatus() == GameStatus.BLACK_WON) {
//                     JOptionPane pane=new JOptionPane();
                    JOptionPane.showMessageDialog(this, "Black Won!");
                    frame.setVisible(false);
                }
                if (game.getGameStatus() == GameStatus.WHITE_WON) {
//                  JOptionPane pane=new JOptionPane();
                    JOptionPane.showMessageDialog(this, "White Won!");
//                  pane.setInitialSelectionValue(JFrame.DO_NOTHING_ON_CLOSE);
                    frame.setVisible(false);

                }
                if (game.getGameStatus() == GameStatus.INSUFFICIENT_MATERIAL || game.getGameStatus() == GameStatus.STALEMATE) {
                    JOptionPane.showMessageDialog(this, "Draw!");
                    frame.setVisible(false);
                }
                if (game.getGameStatus() == GameStatus.BLACK_UNDER_CHECK || game.getGameStatus() == GameStatus.WHITE_UNDER_CHECK) {
                    highlightKing(g);
                }

                if (firstClick && validSquares != null) {
                    highlightSquares(g);
                }
            }

        };

        frame.add(p);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
        p.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x;
        int y;
        if (game.getWhoseTurn() == Player.WHITE) {
            x = getXCoordinate(e.getX());
            y = getYCoordinate(e.getY());
        } else {
            x = 7 - getXCoordinate(e.getX());
            y = 7 - getYCoordinate(e.getY());
        }
        if (firstClick == false) {
            selectedPiece = getPieceSelected(x, y);
            if (selectedPiece != null) {
                firstClick = true;
                fromSquare = new Square(BoardFile.values()[x], BoardRank.values()[y]);
                validSquares = game.getAllValidMovesFromSquare(fromSquare);
                frame.repaint();
            }
        } else {
            goToTile = getPieceSelected(x, y);
            firstClick = false;
            toSquare = new Square(BoardFile.values()[x], BoardRank.values()[y]);
//            for(int i=0;i<validSquares.size();i++){
//                System.out.println("column= "+validSquares.get(i).getFile().getValue()+",row= "+validSquares.get(i).getRank().getValue());
//            }
//                System.out.println("column= "+toSquare.getFile().getValue()+",row= "+toSquare.getRank().getValue());

            ChessPiece.move(toSquare, fromSquare, game, frame);

            frame.repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("mouse pressed");

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println("mouse released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("mouse entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("mouse exited");
    }

    private int getXCoordinate(int x) {
        return x / 64;
    }

    private int getYCoordinate(int y) {
        return (7 - (y / 64));
    }

    public BoardFile getFile(int n) {
        return BoardFile.values()[n];
    }

    private void highlightSquares(Graphics g) {
        int x;
        int y;
        for (int i = 0; i < validSquares.size(); i++) {
            if (game.getWhoseTurn() == Player.WHITE) {
                x = validSquares.get(i).getFile().getValue() * 64;
                y = (7 - validSquares.get(i).getRank().getValue()) * 64;
            } else {
                x = (7 * 64) - (validSquares.get(i).getFile().getValue() * 64);
                y = (7 * 64) - ((7 - validSquares.get(i).getRank().getValue()) * 64);
            }
            g.setColor(Color.YELLOW);
            int centerX = x + 64 / 2;
            int centerY = y + 64 / 2;
            int radius = 64 / 6;
            g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        }
//        validSquares.clear();
    }

    private ChessPiece getPieceSelected(int x, int y) {
        for (ChessPiece piece : piecesOnFrame) {
            if (piece.getX() == x && piece.getY() == y) {
                return piece;
            }
        }

        return null;
    }

    public void highlightKing(Graphics g) {
        int x;
        int y;

        Square king = Utilities.getKingSquare(game.getWhoseTurn(), game.getBoard());
        if (game.getWhoseTurn() == Player.WHITE) {
            x = king.getFile().getValue() * 64;
            y = (7 - king.getRank().getValue()) * 64;
        } else {
            x = (7 * 64) - (king.getFile().getValue() * 64);
            y = (7 * 64) - ((7 - king.getRank().getValue()) * 64);
        }
        //ChessPiece p = getPieceSelected(x, y);
        g.setColor(Color.RED);
        //g.fillRect(x , y , 64, 64);
        //p.draw(g, x, y, 64, 64);
        g.drawRect(x, y, 64 - 1, 64 - 1);
    }

}
