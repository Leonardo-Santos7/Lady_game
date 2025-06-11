package implementation.model;

public class Peace {
    public enum Type {
        EMPTY(0),
        PEACE_PLAYER1(1),
        PEACE_PLAYER2(2),
        CHECKERS_PLAYER1(3),
        CHECKERS_PLAYER2(4);

        private final int value;

        Type(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }

        public static Type fromValue(int value) {
            for (Type type : values()) {
                if (type.value == value) {
                    return type;
                }
            }
            return EMPTY;
        }
    }

    private Type type;
    private int player;

    public Peace(Type type){
        this.type = type;
        this.player = definePlayer(type);
    }

    public Peace(){
        this(Type.EMPTY);
    }

    private int definePlayer(Type type){
        return switch (type) {
            case PEACE_PLAYER1, CHECKERS_PLAYER1 -> 1;
            case PEACE_PLAYER2, CHECKERS_PLAYER2 -> 2;
            default -> 0;
        };
    }

    public boolean isChecker(){
        return type == Type.CHECKERS_PLAYER1 || type == Type.CHECKERS_PLAYER2;
    }

    public boolean isEmpty(){
        return type == Type.EMPTY;
    }

    public boolean belongsToPlayer(int player){
        return this.player == player;
    }

    public void promoteToChecker(){
        if(type == Type.PEACE_PLAYER1){
            type = Type.CHECKERS_PLAYER1;
        } else {
            type = Type.CHECKERS_PLAYER2;
        }
    }

    public Type getType(){
        return type;
    }
    public int getPlayer(){
        return player;
    }
    public int getValue(){
        return type.getValue();
    }
}


