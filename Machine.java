/* 
 * This is an implementation of a 2-way 2-
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Machine {
    private List<State> states; // Set of states 'Q'
    private List<Character> inputAlphabet; // Input alphabet 'sigma'
    // maybe we need a stack alpabhet
    private List<Character> stackAlphabet;
    private State initialState; // 'qI'
    private State finalState; // 'qF'
    private int currHead; // the current symbol being read in the input string

    // constructor for the Machine, this will be used in the gui
    public Machine(File file) throws CustomException {

        /*
         * inputs to read from the file in order
         * 
         * number of states
         * state names
         * number of inputs
         * input symbols // lambda doesn't need to be explicitly defined '^'
         * number of stack symbols
         * stack symbols
         * number of transitions
         * transitions
         * initial state
         * initial stack symbol 'Z'
         * final state
         * 
         */

        try {
            Buffered reader = new BufferedReader(new FileReader(file));

            // read the number of states
            int numStates = Integer.parseInt(reader.readLine().trim());

            // read each state, the state names will be read and will be attached to each of
            // the State object
            String[] states = reader.readline().trim().split(" ");

            // check if the number of states is matching with the states provided
            if (numStates <= 0 || (numStates != states.length)) {
                throw new CustomException(
                        "Invalid number of states or the number of states doesn't match the provided states");
            }

            this.states = new ArrayList<>();
            for (int i = 0; i < numStates; i++) {
                this.states.add(new State(states[i]));
            }

            // read the number of inputs
            int numInputs = Integer.parseInt(reader.readLine().trim());

            // iterate and read over every input state
            String[] inputSymbols = reader.readLine().trim().split(" ");
            this.inputAlphabet = new ArrayList<>();

            for (int i = 0; i < inputSymbols.length; i++) {
                if (inputSymbols[i].length() > 1 || (inputSymbols.length != numInputs)) {
                    throw new CustomException("Invalid Input Symbols");
                }
                this.inputAlphabet.add(inputSymbols[i].chartAt[0]);
            }

            // read number of stack symbols
            int numStackSymbols = Integer.parseInt(reader.readLine().trim());

            // iterate over every stack symbol
            String[] stackSymbols = reader.readLine().trim().split(" ");
            this.stackAlphabet = new ArrayList<>();

            for (int i = 0; i < stackSymbols.length; i++) {
                if (stackSymbols[i].length() > 1 || stackSymbols.length != numStackSymbols) {
                    throw new CustomException("Invalid stack symbols");
                }
                this.stackAlphabet.add(stackSymbols[i].charAt[0]);
            }

            // read no. of transitions
            int numTransitions = Integer.parseInt(reader.readLine().trim());

            // TODO: capture the error later if the number of transitions doesn't match with
            // the provided transitions
            // TODO: capture the error later if the number of input symbols doesn't match
            // with the provided input symbols

            // add transitions to each state
            // sample transition
            // q1 // a ^ ^ q2 ^ ^ R
            for (int i = 0; i < numTransitions; i++) {
                String[] transitions = reader.readline().trim().split(" ");

                if (transition.length != 7) {
                    throw new CustomException("Invalid transition.");
                } 

                State currentState = State.findStateByName(transitions[0], this.states);
                State nextState = State.findByStateName(transitions[4], this.states);

                // check if 
                if (currentState != null && nextState != null && (this.inputAlphabet.contains(transitions[1])))
            }

            // read the initial state (starting)
            State initialState = State.findStateByName(reader.readLine(), this.states);
            if (initialState == null) {
                throw new CustomException("Invalid Starting State");
            }
            // read the final state (accepting)
            State acceptingState = State.findStateByName(reader.readLine(), this.states);
            if (acceptingState == null) {
                throw new CustomException("Invalid Accepting State");
            }
        } catch (Exception e) {
            throw new CustomException("Invalid File: " + e);
        }
    }

    /*
     * public main(String[] args) {
     * // check if the files are properly taken
     * }
     */

    // TODO: implement backtracking

    /*
     * public static checkValidTransition() {
     * 
     * }
     */

}
