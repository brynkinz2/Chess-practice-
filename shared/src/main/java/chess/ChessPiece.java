package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor color;
    private PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();

        if (getPieceType() == PieceType.KING) {
            int directions[][] = {{-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}};

            for (int[] direction : directions) {
                int checkRow = currRow + direction[0];
                int checkCol = currCol + direction[1];
                if (checkRow < 1 || checkCol < 1 || checkRow > 8 || checkCol > 8) {
                    continue;
                }

                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
                if (checkPiece == null || checkPiece.getTeamColor() != color) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                }
            }
        }
        else if (getPieceType() == PieceType.QUEEN) {
            int directions[][] = {{-1,1}, {0,1}, {1,1}, {1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}};

            for (int[] direction : directions) {
                int checkRow = currRow + direction[0];
                int checkCol = currCol + direction[1];

                while (checkRow > 0 && checkCol > 0 && checkRow <= 8 && checkCol <= 8) {
                    ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
                    if (checkPiece == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                    }
                    else if (checkPiece.getTeamColor() != color) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                        break;
                    }
                    else {
                        break;
                    }
                    checkRow += direction[0];
                    checkCol += direction[1];
                }
            }
        }
        else if (getPieceType() == PieceType.BISHOP) {
            int directions[][] = {{-1, 1}, {1,1}, {1,-1}, {-1,-1}};

            for (int[] direction : directions) {
                int checkRow = currRow + direction[0];
                int checkCol = currCol + direction[1];

                while (checkRow > 0 && checkCol > 0 && checkRow <= 8 && checkCol <= 8) {
                    ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
                    if (checkPiece == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                    }
                    else if (checkPiece.getTeamColor() != color) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                        break;
                    }
                    else {
                        break;
                    }
                    checkRow += direction[0];
                    checkCol += direction[1];
                }
            }

        }
        else if (getPieceType() == PieceType.KNIGHT) {
            int directions[][] = {{1,-2}, {2,-1}, {2,1}, {1,2}, {-1,2}, {-2, 1}, {-2,-1}, {-1,-2}};

            for (int[] direction : directions) {
                int checkRow = currRow + direction[0];
                int checkCol = currCol + direction[1];
                if (checkRow < 1 || checkCol < 1 || checkRow > 8 || checkCol > 8) {
                    continue;
                }

                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
                if (checkPiece == null || checkPiece.getTeamColor() != color) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                }
            }
        }
        else if (getPieceType() == PieceType.ROOK) {
            int directions[][] = {{-1, 0}, {0,1}, {1,0}, {0,-1}};

            for (int[] direction : directions) {
                int checkRow = currRow + direction[0];
                int checkCol = currCol + direction[1];

                while (checkRow > 0 && checkCol > 0 && checkRow <= 8 && checkCol <= 8) {
                    ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
                    if (checkPiece == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                    }
                    else if (checkPiece.getTeamColor() != color) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                        break;
                    }
                    else {
                        break;
                    }
                    checkRow += direction[0];
                    checkCol += direction[1];
                }
            }
        }
        else if (getPieceType() == PieceType.PAWN) {
            boolean canPromote = false;
            boolean firstMove = false;
            int[][] directions = {{1,0}, {1,-1}, {1,1}};
            if (color == ChessGame.TeamColor.BLACK) {
                for (int[] direction : directions) {
                    direction[0] = -1;
                }
            }
            if ((color == ChessGame.TeamColor.WHITE && currRow == 7) || (color == ChessGame.TeamColor.BLACK && currRow == 2)) {
                canPromote = true;
            }
            if ((color == ChessGame.TeamColor.WHITE && currRow == 2) || (color == ChessGame.TeamColor.BLACK && currRow == 7)) {
                firstMove = true;
            }

            for (int[] direction : directions) {
                int checkRow = currRow + direction[0];
                int checkCol = currCol + direction[1];

                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
                if (direction[1] == 0) {
                    if (checkPiece == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                        if (firstMove && board.getPiece(new ChessPosition(checkRow + direction[0], checkCol)) == null) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(checkRow + direction[0], checkCol), null));
                        }
                    }
                }
            }


//            ChessPiece checkPiece = board.getPiece(new ChessPosition(currRow + 1, currCol));
//            if (checkPiece == null) {
//                moves.add(new ChessMove(myPosition, new ChessPosition(currRow + 1, currCol), null));
//            }
//            if (firstMove) {
//                checkPiece = board.getPiece(new ChessPosition(currRow + 2, currCol));
//                if (checkPiece == null) {
//                    moves.add(new ChessMove(myPosition, new ChessPosition(currRow + 2, currCol), null));
//                }
//            }
//
//            checkPiece = board.getPiece(new ChessPosition(currRow + 1, currCol - 1));
//            if (checkPiece != null && checkPiece.getTeamColor() != color) {
//                moves.add(new ChessMove(myPosition, new ChessPosition(currRow + 1, currCol - 1), null));
//            }
//            checkPiece = board.getPiece(new ChessPosition(currRow + 1, currCol + 1));
//            if (checkPiece != null && checkPiece.getTeamColor() != color) {
//                moves.add(new ChessMove(myPosition, new ChessPosition(currRow + 1, currCol + 1), null));
//            }
        }
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "color=" + color +
                ", type=" + type +
                '}';
    }
}
