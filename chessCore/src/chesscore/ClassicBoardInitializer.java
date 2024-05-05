package chesscore;

import chesscore.Pieces.Pawn;
import chesscore.Pieces.King;
import chesscore.Pieces.Queen;
import chesscore.Pieces.Knight;
import chesscore.Pieces.Bishop;
import chesscore.Pieces.Piece;
import chesscore.Pieces.Rook;

public final class ClassicBoardInitializer implements BoardInitializer {
    private static final BoardInitializer instance = new ClassicBoardInitializer();

    private ClassicBoardInitializer() {
    }

    public static BoardInitializer getInstance() {
        return instance;
    }

    @Override
    public Piece[][] initialize() {
        Piece[][] initialState = {
            {new Rook(Player.WHITE), new Knight(Player.WHITE), new Bishop(Player.WHITE), new Queen(Player.WHITE), new King(Player.WHITE), new Bishop(Player.WHITE), new Knight(Player.WHITE), new Rook(Player.WHITE)},
            {new Pawn(Player.WHITE), new Pawn(Player.WHITE), new Pawn(Player.WHITE), new Pawn(Player.WHITE), new Pawn(Player.WHITE), new Pawn(Player.WHITE), new Pawn(Player.WHITE), new Pawn(Player.WHITE)},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {new Pawn(Player.BLACK), new Pawn(Player.BLACK), new Pawn(Player.BLACK), new Pawn(Player.BLACK), new Pawn(Player.BLACK), new Pawn(Player.BLACK), new Pawn(Player.BLACK), new Pawn(Player.BLACK)},
            {new Rook(Player.BLACK), new Knight(Player.BLACK), new Bishop(Player.BLACK), new Queen(Player.BLACK), new King(Player.BLACK), new Bishop(Player.BLACK), new Knight(Player.BLACK), new Rook(Player.BLACK)}
        };
        return initialState;
    }
}
