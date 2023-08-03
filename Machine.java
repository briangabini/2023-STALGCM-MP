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

    // constructor for the Machine, this will be used in the gui
    public Machine(File file) throws CustomException {

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
            // assume input is correct
            /*
             * if (numStates <= 0 || (numStates != states.length)) {
             * throw new CustomException(
             * "Invalid number of states or the number of states doesn't match the provided states"
             * );
             * }
             */

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
            // this.inputAlphabet.add('^');
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
            // this.stackAlphabet.add('^');

            // System.out.println("Stack Alphabet Size: " + this.stackAlphabet.size());

            for (int i = 0; i < this.stackAlphabet.size(); i++) {
                // System.out.println("Stack Symbol " + i + " = " + this.stackAlphabet.get(i));
            }

            // read no. of transitions
            int numTransitions = Integer.parseInt(reader.readLine().trim());

            // System.out.println("NumTransitions = " + numTransitions);

            for (int i = 0; i < numTransitions; i++) {
                String[] transitions = reader.readLine().trim().split(" ");

                // System.out.println("Transition: " + Arrays.toString(transitions));

                // assume input is correct so no need for this
                /*
                 * if (transitions.length != 8) {
                 * throw new CustomException("Invalid transition.");
                 * }
                 */

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
                /*
                 * if (currentState != null && nextState != null &&
                 * this.inputAlphabet.contains(transitions[1].charAt(0))
                 * && (this.stackAlphabet.contains(transitions[2].charAt(0)) ||
                 * transitions[2].charAt(0) == '^')
                 * && (this.stackAlphabet.contains(transitions[3].charAt(0)) ||
                 * transitions[3].charAt(0) == '^')
                 * && (this.stackAlphabet.contains(transitions[5].charAt(0)) ||
                 * transitions[5].charAt(0) == '^')
                 * && (this.stackAlphabet.contains(transitions[6].charAt(0)) ||
                 * transitions[6].charAt(0) == '^')
                 * && (transitions[7].charAt(0) == 'R' || transitions[7].charAt(0) == 'L')) {
                 */

                currentState.addTransition(transitions[1].charAt(0),
                        new TransitionKey(transitions[2].charAt(0), transitions[3].charAt(0), nextState,
                                transitions[5].charAt(0), transitions[6].charAt(0), transitions[7].charAt(0)));

                // }
                // assume input is correct
                /*
                 * else {
                 * throw new CustomException("Invalid transitions");
                 * // System.out.println("Error transition");
                 * }
                 */
            }

            // read the initial state (starting)
            State initialState = State.findStateByName(reader.readLine(), this.states);
            // assume input is correct
            /*
             * if (initialState == null) {
             * throw new CustomException("Invalid Starting State");
             * }
             */
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
            State finalState = State.findStateByName(reader.readLine(), this.states);

            // assume input is correct
            /*
             * if (finalState == null) {
             * throw new CustomException("Invalid Accepting State");
             * }
             */
            finalState.setIsFinalState(true);

            this.finalState = finalState;

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
            File file = new File("machine3.txt");
            Machine machine = new Machine(file);

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

            System.out.println("Check machine contents: ");
            System.out.println(machine.toString());

            // Read the input string from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input string: ");
            String inputString = scanner.nextLine();

            inputString.trim();

            var result = machine.checkStringAcceptance(inputString);

            scanner.close();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

        // System.out.println(Arrays.toString(machine.states.toArray()));

    }

    public boolean checkStringAcceptance(String input) {
        input = "<" + input + ">"; // add start and end markers to the string

        State currentState = this.initialState; // initialize the start state
        currHead = 0;

        while (true) {
            if (currentState.getIsFinalState() && stack1.isEmpty() && stack2.isEmpty()
                    && (currHead == input.length())) {
                System.out.println("Accepted");
                return true;
            } else if ((currHead == input.length())
                    && ((!stack1.isEmpty() || !stack2.isEmpty()) || !currentState.getIsFinalState())) {
                System.out.println("Rejected");
                return false;
            } else {
                // for debugging

                /*
                 * System.out.println("Current State: " + currentState.getName());
                 * System.out.println("Current Input: " + input.charAt(currHead));
                 * System.out.println("Stack 1 Contents:");
                 * System.out.println(stack1.toString());
                 * System.out.println("Stack 2 Contents:");
                 * System.out.println(stack2.toString());
                 */

                TransitionKey transition = currentState.getTransitionByInput(input.charAt(currHead));

                // this means that this is a dead branch
                if (transition == null) {
                    System.out.println("Rejected no transitions.");
                    return false;
                }

                currentState = transition.getState();

                // check if there are no transitions to the other state

                System.out.println("Current stack1pop: " + transition.getStack1Pop());
                System.out.println("Current stack2pop: " + transition.getStack2Pop());

                // System.out.println();

                if (transition.getInputDirection() == 'R')
                    currHead++;
                else if (transition.getInputDirection() == 'L')
                    currHead--;

                // if stack1 is not empty
                if (!stack1.isEmpty()) {
                    if (stack1.peek() != transition.getStack1Pop() && transition.getStack1Pop() != '^') {
                        System.out.println("Rejected. Popping a different stack symbol.");
                        return false;
                    } else if (transition.getStack1Pop() == stack1.peek()) {
                        // System.out.println("Popping something stack1");
                        stack1.pop();
                    }
                } else if (stack1.isEmpty()) {
                    if (transition.getStack1Pop() != '^') {
                        // System.out.println("Rejected. Popping on an empty stack.");
                        return false;
                    }
                }

                if (!stack2.isEmpty()) {
                    if (stack2.peek() != transition.getStack2Pop() && transition.getStack2Pop() != '^') {
                        System.out.println("Rejected. Popping a different stack symbol.");
                        return false;
                    } else if (transition.getStack2Pop() == stack2.peek()) {
                        // System.out.println("Popping something stack1");
                        stack2.pop();
                    }
                } else if (stack2.isEmpty()) {
                    if (transition.getStack2Pop() != '^') {
                        System.out.println("Rejected. Popping on an empty stack.");
                        return false;
                    }
                }

                if (transition.getStack1Push() != '^')
                    stack1.push(transition.getStack1Push());

                if (transition.getStack2Push() != '^')
                    stack2.push(transition.getStack2Push());

            }
        }

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

}
