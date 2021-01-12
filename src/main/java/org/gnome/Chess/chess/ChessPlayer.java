package org.gnome.Chess.chess;

import java.util.EventObject;

import javax.lang.model.type.NullType;

import org.gnome.Chess.util.Signal;

public class ChessPlayer {

    public static class ChessPlayerEvent extends EventObject {

        private static final long serialVersionUID = 1L;

        public ChessPlayerEvent(ChessPlayer source) {
            super(source);
        }

        @Override
        public ChessPlayer getSource() {
            return (ChessPlayer) super.getSource();
        }

    }

    public static class DoMoveEvent extends ChessPlayerEvent {

        private static final long serialVersionUID = 1L;

        private String move;

        private boolean apply;

        public DoMoveEvent(ChessPlayer source, String move, boolean apply) {
            super(source);
            this.move = move;
            this.apply = apply;
        }

        public String getMove() {
            return move;
        }

        public boolean shouldApply() {
            return apply;
        }

    }

    public Color color;
    public Signal<DoMoveEvent, Boolean> doMove = new Signal<>();
    public Signal<ChessPlayerEvent, NullType> doUndo = new Signal<>();
    public Signal<ChessPlayerEvent, Boolean> doResign = new Signal<>();
    public Signal<ChessPlayerEvent, NullType> doClaimDraw = new Signal<>();

    private boolean localHuman = false;

    public boolean isLocalHuman() {
        return localHuman;
    }

    public void setLocalHuman(boolean localHuman) {
        this.localHuman = localHuman;
    }

    public ChessPlayer(Color color) {
        this.color = color;
    }

    public boolean move(String move) {
        return move(move, true);
    }

    public boolean move(String move, boolean apply) {
        return doMove.emit(new DoMoveEvent(this, move, apply));
    }

    public boolean moveWithCoords(int r0, int f0, int r1, int f1) {
        return moveWithCoords(r0, f0, r1, f1, true, PieceType.QUEEN);
    }

    public boolean moveWithCoords(int r0, int f0, int r1, int f1, boolean apply) {
        return moveWithCoords(r0, f0, r1, f1, apply, PieceType.QUEEN);
    }

    public boolean moveWithCoords(int r0, int f0, int r1, int f1, PieceType promotionType) {
        return moveWithCoords(r0, f0, r1, f1, true, promotionType);
    }

    public boolean moveWithCoords(int r0, int f0, int r1, int f1, boolean apply, PieceType promotionType) {
        String move = String.format("%c%d%c%d", 'a' + f0, r0 + 1, 'a' + f1, r1 + 1);

        switch (promotionType) {
            case QUEEN:
                /* Default is queen so don't add anything */
                break;
            case KNIGHT:
                move += "=N";
                break;
            case ROOK:
                move += "=R";
                break;
            case BISHOP:
                move += "=B";
                break;
            default:
                break;
        }

        return doMove.emit(new DoMoveEvent(this, move, apply));
    }

    public void undo() {
        doUndo.emit(new ChessPlayerEvent(this));
    }

    public boolean resign() {
        return doResign.emit(new ChessPlayerEvent(this));
    }

    public void claimDraw() {
        doClaimDraw.emit(new ChessPlayerEvent(this));
    }

}
