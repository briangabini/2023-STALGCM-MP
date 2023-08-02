import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

            // System.out.println("numStates = " + numStates);

            // read each state, the state names will be read and will be attached to each of
            // the State object
            String[] states = reader.readLine().trim().split(" ");

            // System.out.println("States = " + Arrays.toString(states));

            // check if the number of states is matching with the states provided
            if (numStates <= 0 || (numStates != states.length)) {
                throw new CustomException(
                        "Invalid number of states or the number of states doesn't match the provided states");
            }

            this.states = new ArrayList<>();
            for (int i = 0; i < numStates; i++) {
                this.states.add(new State(states[i]));

                // System.out.println("States " + i + " = " + this.states.get(i).getName());
            }

            // read the number of inputs
            int numInputs = Integer.parseInt(reader.readLine().trim());

            // System.out.println("NumInputs = " + numInputs);

            // iterate and read over every input state
            String[] inputSymbols = reader.readLine().trim().split(" ");
            this.inputAlphabet = new ArrayList<>();

            // System.out.println("InputAlphabet = " + Arrays.toString(inputSymbols));

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
                // System.out.println("Alphabet " + i + " = " + this.inputAlphabet.get(i));
            }

            // read number of stack symbols
            int numStackSymbols = Integer.parseInt(reader.readLine().trim());

            // System.out.println("NumStackSymbols: " + numStackSymbols);

            // iterate over every stack symbol
            String[] stackSymbols = reader.readLine().trim().split(" ");
            this.stackAlphabet = new ArrayList<>();

            // System.out.println("StackSymbols = " + Arrays.toString(stackSymbols));

            for (int i = 0; i < stackSymbols.length; i++) {
                if (stackSymbols[i].length() > 1 || stackSymbols.length != numStackSymbols) {
                    throw new CustomException("Invalid stack symbols");
                }
                this.stackAlphabet.add(stackSymbols[i].charAt(0));
            }
            this.stackAlphabet.add('^');

            // System.out.println("Stack Alphabet Size: " + this.stackAlphabet.size());

            for (int i = 0; i < this.stackAlphabet.size(); i++) {
                // System.out.println("Stack Symbol " + i + " = " + this.stackAlphabet.get(i));
            }

            // read no. of transitions
            int numTransitions = Integer.parseInt(reader.readLine().trim());

            // System.out.println("NumTransitions = " + numTransitions);

            // TODO: capture the error later if the number of transitions doesn't match with
            // the provided transitions
            // TODO: capture the error later if the number of input symbols doesn't match
            // with the provided input symbols

            // add transitions to each state
            // sample transition
            // q1 // a ^ ^ q2 ^ ^ R
            for (int i = 0; i < numTransitions; i++) {
                String[] transitions = reader.readLine().trim().split(" ");

                // System.out.println("Transition: " + Arrays.toString(transitions));

                if (transitions.length != 8) {
                    throw new CustomException("Invalid transition.");
                }

                State currentState = State.findStateByName(transitions[0], this.states);
                State nextState = State.findStateByName(transitions[4], this.states);

                // System.out.println("Current State: " + currentState.getName());
                // System.out.println("Next State: " + nextState.getName());

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

            // System.out.println("Initial State: " + this.initialState.getName());

            // read the initial stack symbol
            this.initialStackSymbol = reader.readLine().trim().charAt(0);

            // System.out.println("Init stack symbol: " + this.initialStackSymbol);

            // push the initial stack symbol to the 2 stacks
            this.stack1 = new Stack<>();
            this.stack2 = new Stack<>();

            this.stack1.push(this.initialStackSymbol);
            this.stack2.push(this.initialStackSymbol);

            // System.out.println(stack1.peek());
            // System.out.println(stack2.peek());

            // read the final state (accepting)
            State acceptingState = State.findStateByName(reader.readLine(), this.states);
            if (acceptingState == null) {
                throw new CustomException("Invalid Accepting State");
            }
            acceptingState.setIsFinalState(true);

            this.finalState = acceptingState;

            /*
             * //System.out.println(
             * "Final State = " + this.finalState.getName() + " isFinalState: "
             * + this.finalState.getIsFinalState());
             */

            reader.close();

        } catch (Exception e) {
            throw new CustomException("Invalid File: " + e);
        }
    }

    public static void main(String[] args) {
        // System.out.println("Test");

        try {
            File file = new File("machine.txt");
            Machine machine = new Machine(file);

            machine.currHead = 1; // start with the input and not the start marker '<'

            /* print the transitions of all states */
            /*
             * for (int i = 0; i < machine.states.size(); i++) {
             * for (int j = 0; j < machine.states.get(i).getTransitions().size(); j++) {
             * System.out.println("Transition " + j + ":" + machine.);
             * }
             * }
             */

            // machine.states.get(i).getTransition

            // Scanner scanner = new Scanner();
            // System.out.print("Input string: ");
            /*
             * System.out.println(machine.states.get(0).getTransitionByInput('^').toString()
             * );
             */
            /* System.out.println("Check machine contents: ");
            System.out.println(machine.toString()); */

                // Read the input string from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input string: ");
            String inputString = scanner.nextLine();

            // Convert '^' to empty string for lambda transition
            if (inputString.equals("^")) {
                inputString = "";
            } else {
                inputString = "<" + inputString + ">";
            }

            inputString.trim();

            // Initialize the input head to 0
            machine.currHead = 1;

            // Call the backtrack function with initial state and stacks
            boolean isAccepted = machine.backtrack(machine.initialState, inputString, machine.stack1, machine.stack2, machine.currHead);

            // Check if the input string is accepted or rejected
            if (isAccepted) {
                System.out.println("Input string is accepted!");
            } else {
                System.out.println("Input string is rejected!");
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("An error occurred");
        }

        // System.out.println(Arrays.toString(machine.states.toArray()));

    }

    public boolean backtrack(State currentState, String input, Stack<Character> stack1, Stack<Character> stack2, int inputHead) {
        // TODO: if input is empty check if there are lambda transitions available

        // TODO: handle lambda transitions (transition without reading from the input string)

        // 3 conditions for a string to be accepted
        /* 
         * the input string is read until the end
         * the current state is a final state
         * the stack is empty
         */

        if (currentState.getIsFinalState() && inputHead == input.length() && stack1.isEmpty() && stack2.isEmpty()) {
            return true; // Accepting path is found
        }

            // Get the current input symbol
        char currentInputSymbol = '^'; // Default to lambda
        if (inputHead >= 0 && inputHead < input.length()) {
            currentInputSymbol = input.charAt(inputHead);
        }

        // Get possible transitions for the current state and input symbol (including lambda)
        List<TransitionKey> transitions = new ArrayList<>();
        /* if (currentState.getTransitionByInput(currentInputSymbol) != null) {
            transitions.addAll(currentState.getTransitionByInput(currentInputSymbol));
        }
        if (currentState.getTransitionByInput('^') != null) {
            transitions.addAll(currentState.getTransitionByInput('^'));
        } */
        List<TransitionKey> currentTransitions = currentState.getTransitionByInput(currentInputSymbol);

        if (currentTransitions != null) {
            // If the input symbol matches, add the corresponding transitions to the list
            transitions.addAll(currentTransitions);
        }

        // Also, check for lambda transitions and add them to the list
        List<TransitionKey> lambdaTransitions = currentState.getTransitionByInput('^');
        if (lambdaTransitions != null) {
            transitions.addAll(lambdaTransitions);
        }

        // If there are no transitions available, backtrack
        if (transitions.isEmpty()) {
            return false;
        }

        // Iterate through possible transitions
        for (TransitionKey transition : transitions) {
            // Save current stack configurations to restore later during backtracking
            char stack1Top = stack1.peek();
            char stack2Top = stack2.peek();
            int currentInputHead = inputHead;

            // Perform the transition by popping and pushing stack symbols
            char stack1Pop = transition.getStack1Pop();
            char stack2Pop = transition.getStack2Pop();

            // Check if '^' represents no pop on stack1
            if (stack1Pop != '^') {
                if (stack1.isEmpty() || stack1.peek() != stack1Pop) {
                    continue; // Invalid transition, backtrack to next possibility
                }
                stack1.pop();
            }

            // Check if '^' represents no pop on stack2
            if (stack2Pop != '^') {
                if (stack2.isEmpty() || stack2.peek() != stack2Pop) {
                    continue; // Invalid transition, backtrack to next possibility
                }
                stack2.pop();
            }

            // Check if '^' represents no push on stack1
            if (transition.getStack1Push() != '^') {
                stack1.push(transition.getStack1Push());
            }

            // Check if '^' represents no push on stack2
            if (transition.getStack2Push() != '^') {
                stack2.push(transition.getStack2Push());
            }

            // Move the input head according to the transition direction (left or right)
            char inputDirection = transition.getInputDirection();
            if (inputDirection == 'R') {
                currentInputHead++;
            } else if (inputDirection == 'L') {
                currentInputHead--;
            }

            // Recurse to the next state with updated configurations
            boolean isPathFound = backtrack(transition.getState(), input, stack1, stack2, currentInputHead);

            // If a valid path is found, return true
            if (isPathFound) {
                return true;
            }

            // Backtrack by restoring the previous stack configurations and input head
            stack1.pop();
            stack1.push(stack1Top);
            stack2.pop();
            stack2.push(stack2Top);
        }

        // If no valid path is found, return false
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Q = [");

        for (int i = 0; i < states.size(); i++) {
            sb.append(states.get(i).getName()).append(" ");
        }

        sb.append("]\n");
        sb.append("Σ = ").append(inputAlphabet).append("\n");
        sb.append("Γ = ").append(stackAlphabet).append("\n");
        sb.append("Start State: ").append(initialState.getName()).append("\n");
        sb.append("Initial Stack Symbol: ").append(initialStackSymbol).append("\n");
        sb.append("Final State: ").append(finalState.getName()).append("\n");

        return sb.toString();
    }

    // TODO: implement backtracking

}
