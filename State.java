import java.util.ArrayList;
import java.util.HashMap;

/* 
 * Represents a state in a deterministic 2-way 2-stack PDA.
 */
public class State {
    private String name;
    private boolean isFinalState;
    private HashMap<Character, TransitionKey> transitions;

    // Main constructor
    public State(String name) {
        this.transitions = new HashMap<>();
        this.isFinalState = false;
        this.name = name;
    }

    // Getters
    public String getName() {
        return name;
    }

    public boolean getIsFinalState() {
        return isFinalState;
    }

    // Setters
    public void setIsFinalState(boolean isFinalState) {
        this.isFinalState = isFinalState;
    }

    /**
     * Helper function to locate a state in the list based on its name.
     *
     * @param stateName The name of the state to find.
     * @param states    The ArrayList of states to search through.
     * @return The State object with the specified name, or null if not found.
     */
    public static State getState(String stateName, ArrayList<State> states) {
        int listLen = states.size();

        // Cycle through the list to find the corresponding state
        for (int i = 0; i < listLen; i++) {
            if (states.get(i).getName().equals(stateName)) {
                return states.get(i);
            }
        }

        return null;
    }

    /**
     * Returns the TransitionKey associated with the given input character.
     *
     * @param input The input character.
     * @return The corresponding TransitionKey, or null if not found.
     */
    public TransitionKey getTransitionKey(char input) {
        return transitions.get(input);
    }

    /**
     * Adds a mapping between the input character and the TransitionKey to the
     * HashMap.
     *
     * @param key   The input character.
     * @param value The corresponding TransitionKey.
     */
    public void addTransitionKey(Character key, TransitionKey value) {
        transitions.put(key, value);
    }

}