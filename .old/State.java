import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {
    private String name;
    private boolean isFinalState;
    // implement transitions, since we will use a non-deterministic 2-way 2-stack
    // PDA, input symbols will be mapped to different transitions, example: reading
    // one input, you can go to different states
    private Map<Character, TransitionKey> transitions; 

    // main constructor
    public State(String name) {
        this.name = name;
        this.isFinalState = false;
        this.transitions = new HashMap<>();
    }

    // getters
    public String getName() {
        return name;
    }

    public boolean getIsFinalState() {
        return isFinalState;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setIsFinalState(boolean isFinalState) {
        this.isFinalState = isFinalState;
    }

    // returns the transition based on the input
    public TransitionKey getTransitionByInput(char input) {
        return transitions.get(input);
    }

    // create a function to add transitions
    public void addTransition(Character currInput, TransitionKey next) {
        transitions.put(currInput, next);
    }

    // this will be a helper function in order to locate the states in the list
    public static State findStateByName(String stateName, List<State> states) {
        int listLen = states.size();

        for (int i = 0; i < listLen; i++) {
            if (states.get(i).getName().equals(stateName)) {
                return states.get(i);
            }
        }

        return null;
    }

}