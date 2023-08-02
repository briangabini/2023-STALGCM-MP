/* 
 * This is an implementation of a 2-way 2-
 * 
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Machine {
    private List<State> states; // Set of states 'Q'
    private List<Character> inputAlphabet; // Input alphabet 'sigma'
    private List<Character> stackAlphabet;
    private State initialState; // 'qI'
    private State finalState; // 'qF'
    private char initialStackSymbol;
    private Stack<Character> stack1;
    private Stack<Character> stack2;
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
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // read the number of states
            int numStates = Integer.parseInt(reader.readLine().trim());

            System.out.println("numStates = " + numStates);

            // read each state, the state names will be read and will be attached to each of
            // the State object
            String[] states = reader.readLine().trim().split(" ");

            System.out.println("States = " + Arrays.toString(states));

            // check if the number of states is matching with the states provided
            if (numStates <= 0 || (numStates != states.length)) {
                throw new CustomException(
                        "Invalid number of states or the number of states doesn't match the provided states");
            }

            this.states = new ArrayList<>();
            for (int i = 0; i < numStates; i++) {
                this.states.add(new State(states[i]));

                System.out.println("States " + i + " = " + this.states.get(i).getName());
            }

            // read the number of inputs
            int numInputs = Integer.parseInt(reader.readLine().trim());

            System.out.println("NumInputs = " + numInputs);

            // iterate and read over every input state
            String[] inputSymbols = reader.readLine().trim().split(" ");
            this.inputAlphabet = new ArrayList<>();

            System.out.println("InputAlphabet = " + Arrays.toString(inputSymbols));

            for (int i = 0; i < inputSymbols.length; i++) {
                if (inputSymbols[i].length() > 1 || (inputSymbols.length != numInputs)) {
                    throw new CustomException("Invalid Input Symbols");
                }

                this.inputAlphabet.add(inputSymbols[i].charAt(0));

            }

            // add these to the input alphabet as well
            this.inputAlphabet.add('^');
            this.inputAlphabet.add('>');
            this.inputAlphabet.add('<');

            for (int i = 0; i < this.inputAlphabet.size(); i++) {
                System.out.println("Alphabet " + i + " = " + this.inputAlphabet.get(i));
            }

            // read number of stack symbols
            int numStackSymbols = Integer.parseInt(reader.readLine().trim());

            System.out.println("NumStackSymbols: " + numStackSymbols);

            // iterate over every stack symbol
            String[] stackSymbols = reader.readLine().trim().split(" ");
            this.stackAlphabet = new ArrayList<>();

            System.out.println("StackSymbols = " + Arrays.toString(stackSymbols));

            for (int i = 0; i < stackSymbols.length; i++) {
                if (stackSymbols[i].length() > 1 || stackSymbols.length != numStackSymbols) {
                    throw new CustomException("Invalid stack symbols");
                }
                this.stackAlphabet.add(stackSymbols[i].charAt(0));
            }
            this.stackAlphabet.add('^');

            System.out.println("Stack Alphabet Size: " + this.stackAlphabet.size());

            for (int i = 0; i < this.stackAlphabet.size(); i++) {
                System.out.println("Stack Symbol " + i + " = " + this.stackAlphabet.get(i));
            }

            // read no. of transitions
            int numTransitions = Integer.parseInt(reader.readLine().trim());

            System.out.println("NumTransitions = " + numTransitions);

            // TODO: capture the error later if the number of transitions doesn't match with
            // the provided transitions
            // TODO: capture the error later if the number of input symbols doesn't match
            // with the provided input symbols

            // add transitions to each state
            // sample transition
            // q1 // a ^ ^ q2 ^ ^ R
            for (int i = 0; i < numTransitions; i++) {
                String[] transitions = reader.readLine().trim().split(" ");

                System.out.println("Transition: " + Arrays.toString(transitions));

                if (transitions.length != 8) {
                    throw new CustomException("Invalid transition.");
                }

                State currentState = State.findStateByName(transitions[0], this.states);
                State nextState = State.findStateByName(transitions[4], this.states);

                System.out.println("Current State: " + currentState.getName());
                System.out.println("Next State: " + nextState.getName());

                /*
                 * System.out.println(this.inputAlphabet.contains(transitions[1].charAt(0)));
                 * System.out.println(this.stackAlphabet.contains(transitions[2].charAt(0)));
                 * System.out.println(this.stackAlphabet.contains(transitions[3].charAt(0)));
                 * System.out.println(this.stackAlphabet.contains(transitions[5].charAt(0)));
                 * System.out.println(this.stackAlphabet.contains(transitions[6].charAt(0)));
                 * System.out.println(transitions[7].charAt(0) == 'R' ||
                 * transitions[7].charAt(0) == 'L');
                 * 
                 * System.out.println("Value: " + transitions[5].charAt(0));
                 * System.out.println("Value: " + transitions[6].charAt(0));
                 */

                // check if
                if (currentState != null && nextState != null && this.inputAlphabet.contains(transitions[1].charAt(0))
                        && this.stackAlphabet.contains(transitions[2].charAt(0))
                        && this.stackAlphabet.contains(transitions[3].charAt(0))
                        && this.stackAlphabet.contains(transitions[5].charAt(0))
                        && this.stackAlphabet.contains(transitions[6].charAt(0))
                        && (transitions[7].charAt(0) == 'R' || transitions[7].charAt(0) == 'L')) {

                    currentState.addTransition(transitions[1].charAt(0),
                            new TransitionKey(transitions[2].charAt(0), transitions[3].charAt(0), nextState,
                                    transitions[5].charAt(0), transitions[6].charAt(0), transitions[7].charAt(0)));

                } else {
                    throw new CustomException("Invalid transitions");
                    // System.out.println("Error transition");
                }
            }

            // read the initial state (starting)
            State initialState = State.findStateByName(reader.readLine(), this.states);
            if (initialState == null) {
                throw new CustomException("Invalid Starting State");
            }
            this.initialState = initialState;

            System.out.println("Initial State: " + this.initialState.getName());

            // read the initial stack symbol
            this.initialStackSymbol = reader.readLine().trim().charAt(0);

            System.out.println("Init stack symbol: " + this.initialStackSymbol);

            // push the initial stack symbol to the 2 stacks
            this.stack1 = new Stack<>();
            this.stack2 = new Stack<>();

            this.stack1.push(this.initialStackSymbol);
            this.stack2.push(this.initialStackSymbol);

            System.out.println(stack1.peek());
            System.out.println(stack2.peek());

            // read the final state (accepting)
            State acceptingState = State.findStateByName(reader.readLine(), this.states);
            if (acceptingState == null) {
                throw new CustomException("Invalid Accepting State");
            }
            acceptingState.setIsFinalState(true);

            this.finalState = acceptingState;

            System.out.println(
                    "Final State = " + this.finalState.getName() + " isFinalState: "
                            + this.finalState.getIsFinalState());

            reader.close();

        } catch (Exception e) {
            throw new CustomException("Invalid File: " + e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Test");

        try {
            File file = new File("machine.txt");
            Machine machine = new Machine(file);

            System.out.println("Inside main: " + machine.initialStackSymbol);

        } catch (Exception e) {
            System.out.println("An error occurred");
        }

        // System.out.println(Arrays.toString(machine.states.toArray()));

    }

    // TODO: implement backtracking

}
