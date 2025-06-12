package implementation.model;

public class Movement {
    private final PositionCheckers origin;
    private final PositionCheckers destination;
    // private final boolean isCapture;
    // private final PositionCheckers positionCaputed;

    public Movement(PositionCheckers origin, PositionCheckers destination) {
        this.origin = origin;
        this.destination = destination;
        // this.isCapture = isCapture;
        // this.positionCaputed = positionCaputed;
    }

    public PositionCheckers getOrigin(){
        return origin;
    }

    public PositionCheckers getDestination(){
        return destination;
    }

  // public PositionCheckers getPositionCaputed(){
  //      return positionCaputed;
  //  }

    public boolean movementSimple() {
        return origin.rowDistance(destination) == 1;
    }

   // public boolean isCapture(){
   //     return isCapture;
   // }

    public boolean isDiagonal(){
        return origin.isDiagonalOf(destination);
    }
}
