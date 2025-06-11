package implementation.model;

public class Board {
    private static final int SIZE = 8;
    private Peace[][] house;


    public Board(){
        start();
    }

    public void start(){
        house = new Peace[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                house[i][j] = new Peace();
            }
        }

        for(int row = 0; row < 3; row++){
            for(int column = 0; column < SIZE; column++){
                if((row + column) % 2 == 1){
                    house[row][column] = new Peace(Peace.Type.PEACE_PLAYER2);
                }
            }
        }

        for(int row = 5; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if ((row + column) % 2 == 1) {
                    house[row][column] = new Peace(Peace.Type.PEACE_PLAYER1);
                }
            }
        }
    }

    public Peace getPeace(PositionCheckers position){
        if(!position.isValidate()){
            return new Peace();
        }
        return house[position.getRow()][position.getColumn()];
    }

    public void setPeace(PositionCheckers position, Peace peace){
        if(position.isValidate()){
            house[position.getRow()][position.getColumn()] = peace;
        }
    }

    public void movementPeace(Movement movement){
        Peace peace = getPeace(movement.getOrigin());

        setPeace(movement.getDestination(), peace);
        setPeace(movement.getOrigin(), new Peace());

        if(movement.isCapture()){
            setPeace(movement.getPositionCaputed(), new Peace());
        }
    }

    public int countPeace(int player){
        int count = 0;
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(house[i][j].belongsToPlayer(player)){
                    count++;
                }
            }
        }
        return count;
    }

    public int getValuePeace(int row, int column){
        return getPeace(new PositionCheckers(row, column)).getValue();
    }
}
