package chesscore.Pieces;

import chesscore.Square;
import chesscore.Move;
import chesscore.Player;
import chesscore.ChessGame;
import chesscore.ChessBoard;

public abstract class Piece {
    private final Player owner;

    protected Piece(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public abstract boolean isValidMove(Move move, ChessGame game);
    public abstract boolean isAttackingSquare(Square pieceSquare, Square squareUnderAttack, ChessBoard board);
}
