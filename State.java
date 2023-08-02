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
    private Map<Character, ArrayList<TransitionKey>> transitions; // map an input symbol to multiple transitions, this
                                                                  // is because of non-determinism input to transition
                                                                  // is 1..*
                                                                  // e.g. input symbol 1 is mapped to multiple
                                                                  // transitions

    // constructor for the final states
    public State(String name, boolean isFinalState) {
        this.name = name;
        this.isFinalState = isFinalState;
        this.transitions = new HashMap<>();
    }

    // constructor for the non-final states
    public State(String name) {
        this.name = name;
        this.isFinalState = false;
        this.transitions = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsFinalState() {
        return isFinalState;
    }

    public void setIsFinalState(boolean isFinalState) {
        this.isFinalState = isFinalState;
    }

    // create a function to add transitions
    public void addTransition(Character inputSymbol, TransitionKey transkey) {
        // check if input symbol is not already in the map
        // if it is update the arraylist

        // checks if the hashmap contains the key 'inputSymbol', if it does, replace the
        // current value 'ArrayList<TransitionKey> transitions',
        // otherwise, insert directly
        if (transitions.containsKey(inputSymbol)) {
            ArrayList<TransitionKey> transitionArrList = new ArrayList<>();
            transitionArrList = transitions.get(inputSymbol);
            transitionArrList.add(transkey);

            // update the key-value pair
            transitions.put(inputSymbol, transitionArrList);

        } else {
            // input symbol is not in map
            ArrayList<TransitionKey> newtransitionArrList = new ArrayList<>();
            newtransitionArrList.add(transkey);
            transitions.put(inputSymbol, newtransitionArrList);
        }
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