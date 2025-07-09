package implementation.factories;

import implementation.model.Peace;

public class PeaceFactory {

    public static Peace createPlayer1Peace() {
        return new Peace(Peace.Type.PEACE_PLAYER1);
    }


    public static Peace createPlayer2Peace() {
        return new Peace(Peace.Type.PEACE_PLAYER2);
    }


    public static Peace createPlayer1Checker() {
        return new Peace(Peace.Type.CHECKERS_PLAYER1);
    }


    public static Peace createPlayer2Checker() {
        return new Peace(Peace.Type.CHECKERS_PLAYER2);
    }


    public static Peace createEmpty() {
        return new Peace(Peace.Type.EMPTY);
    }


    public static Peace createFromValue(int value) {
        Peace.Type type = Peace.Type.fromValue(value);
        return new Peace(type);
    }
}
