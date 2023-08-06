import java.util.ArrayList;
import java.util.HashMap;

public class State {
    private String name;
    private boolean isFinalState;
    // implement transitions, since we will use a non-deterministic 2-way 2-stack
    // PDA, input symbols will be mapped to different transitions, example: reading
    // one input, you can go to different states
    private HashMap<Character, TransitionKey> transitions;

    // main constructor
    public State(String name) {
        this.transitions = new HashMap<>();
        this.isFinalState = false;
        this.name = name;
    }

    // getters
    public String getName() {
        return name;
    }

    public boolean getIsFinalState() {
        return isFinalState;
    }

    // setters
    public void setIsFinalState(boolean isFinalState) {
        this.isFinalState = isFinalState;
    }

    // this will be a helper function in order to locate the states in the list
    public static State getState(String stateName, ArrayList<State> states) {
        int listLen = states.size();

        // cycle through the list to find the corressponding state
        for (int i = 0; i < listLen; i++) {
            if (states.get(i).getName().equals(stateName)) {
                return states.get(i);
            }
        }

        return null;
    }

    // returns the transition based on the input
    public TransitionKey getTransitionKey(char input) {
        return transitions.get(input);
    }

    // add a Character input to the hashmap that is mapped to a TransitionKey value
    public void addTransitionKey(Character key, TransitionKey value) {
        transitions.put(key, value);
    }

}