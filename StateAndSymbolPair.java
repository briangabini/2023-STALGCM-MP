public class StateAndSymbolPair {
    State state;
    char symbol; // Can be input character or a direction to move the head

    public StateAndSymbolPair(State state, char symbol) {
        this.state = state;
        this.symbol = symbol;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}

