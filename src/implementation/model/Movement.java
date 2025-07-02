package implementation.model;

public class Movement {
    private final PositionCheckers origin;
    private final PositionCheckers destination;
    private final boolean isCapture;
    private final PositionCheckers positionCaptured;

    public Movement(PositionCheckers origin, PositionCheckers destination) {
        this.origin = origin;
        this.destination = destination;
        this.isCapture = origin.rowDistance(destination) == 2;

        if(isCapture){
            int capturedRow = (origin.getRow() + destination.getRow()) / 2;
            int capturedCol = (origin.getColumn() + destination.getColumn()) / 2;
            this.positionCaptured = new PositionCheckers(capturedRow, capturedCol);
        } else {
            this.positionCaptured = null;
        }
    }

    public PositionCheckers getOrigin(){
        return origin;
    }

    public PositionCheckers getDestination(){
        return destination;
    }

    public PositionCheckers getPositionCaptured(){
        return positionCaptured;
    }

    public boolean movementSimple() {
        return origin.rowDistance(destination) == 1;
    }

    public boolean isCapture(){
        return isCapture;
    }

    public boolean isDiagonal(){
        return origin.isDiagonalOf(destination);
    }
}
