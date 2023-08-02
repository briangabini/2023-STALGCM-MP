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

}