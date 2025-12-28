package factory;

import utility.Piece;
import utility.piece.*;

public class PieceFactory {

    public static Piece createPiece(String pieceType, boolean isWhiteType) throws IllegalAccessException {
        switch (pieceType.toLowerCase()){
            case "pawn":
                return new Pawn(isWhiteType);
            case "king":
                return new King(isWhiteType);
            case "queen":
                return new Queen(isWhiteType);
            case "rook":
                return new Rook(isWhiteType);
            case "knight":
                return new Knight(isWhiteType);
            case "bishop":
                return new Bishop(isWhiteType);
            default:
                throw new IllegalAccessException("Unknown piece type: "+ pieceType);

        }
    }

}
