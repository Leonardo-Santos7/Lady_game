package implementation.model;

public class Movement {
    private final PositionCheckers origin;
    private final PositionCheckers destination;
    private final boolean isCapture;
    private final PositionCheckers positionCaputed;

    public Movement(PositionCheckers origin, PositionCheckers destination, boolean isCapture, PositionCheckers positionCaputed) {
        this.origin = origin;
        this.destination = destination;
        this.isCapture = isCapture;
        this.positionCaputed = positionCaputed;
    }

    public PositionCheckers getOrigin(){
        return origin;
    }

    private PositionCheckers getDestination(){
        return destination;
    }

    private PositionCheckers getPositionCaputed(){
        return positionCaputed;
    }
}
