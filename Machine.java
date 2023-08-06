import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLOutput;
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
    TransitionKey transition = null;
    StringBuilder sb1 = new StringBuilder();
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

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // read the number of states
            int numStates = Integer.parseInt(reader.readLine().trim());

            // read each state, the state names will be read and will be attached to each of
            // the State object
            String[] states = reader.readLine().trim().split(" ");

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

                this.inputAlphabet.add(inputSymbols[i].charAt(0));

            }

            // add these to the input alphabet as well
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

            // read no. of transitions
            int numTransitions = Integer.parseInt(reader.readLine().trim());

            for (int i = 0; i < numTransitions; i++) {
                String[] transitions = reader.readLine().trim().split(" ");

                State currentState = State.getState(transitions[0], this.states);
                State nextState = State.getState(transitions[4], this.states);

                currentState.addTransition(transitions[1].charAt(0),
                        new TransitionKey(transitions[2].charAt(0), transitions[3].charAt(0), nextState,
                                transitions[5].charAt(0), transitions[6].charAt(0), transitions[7].charAt(0)));
            }

            // read the initial state (starting)s
            State initialState = State.getState(reader.readLine(), this.states);
            this.initialState = initialState;

            // read the initial stack symbol
            this.initialStackSymbol = reader.readLine().trim().charAt(0);

            // push the initial stack symbol to the 2 stacks
            this.stack1 = new Stack<>();
            this.stack2 = new Stack<>();

            this.stack1.push(this.initialStackSymbol);
            this.stack2.push(this.initialStackSymbol);

            // read the final state (accepting)
            State finalState = State.getState(reader.readLine(), this.states);
            finalState.setIsFinalState(true);
            this.finalState = finalState;

            reader.close();

        } catch (Exception e) {
            throw new CustomException("Invalid File: " + e);
        }
    }

    public static void main(String[] args) {
        // System.out.println("Test");

        try {
            File file = new File("machine4.txt");
            Machine machine = new Machine(file);

            System.out.println("Check machine contents: ");
            System.out.println(machine.toString());

            // Read the input string from the user
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input string: ");
            String inputString = scanner.nextLine();

            inputString.trim();

            var result = machine.checkString(inputString);

            System.out.println("Main. " + result);

            scanner.close();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    public boolean checkString(String input) {
        input = "<" + input + ">"; // add start and end markers to the string

        State currentState = this.initialState; // initialize the start state
        currHead = 0;

        sb1 = new StringBuilder();
        sb1.append("Input " + input);

        boolean stringAccepted = false;

        while (true) {
            if (currHead == -1 || currHead == input.length()) {
                break;
            } else {
                // after the initial stack symbol is popped from both of the stacks, the
                // remaining input from the input string should be ignored
                // it's possible for both stacks to be empty and the current state to be final
                // state even without reading every input
                if (stack1.isEmpty() && stack2.isEmpty() && currentState.getIsFinalState()) {
                    // System.out.println("Accepted");
                    stringAccepted = true;
                }

                /* System.out.println("Current State: " + currentState.getName());
                System.out.println("Current Input: " + input.charAt(currHead));
                System.out.println("Stack 1 Contents:");
                System.out.println(stack1.toString());
                System.out.println("Stack 2 Contents:");
                System.out.println(stack2.toString()); */

                TransitionKey transition = currentState.getTransitionByInput(input.charAt(currHead));

                // this means that this is a dead branch
                if (transition == null) {
                    sb1.append("\n");
                    sb1.append("Rejected no transitions.");
                    sb1.append("\n");
                    System.out.println("Rejected no transitions.");

                    stringAccepted = false;
                    break;
                }

                currentState = transition.getState();

                // check if there are no transitions to the other state
                // final output want to show in a gui
                // System.out.println();

                if (transition.getInputDirection() == 'R')
                    currHead++;
                else if (transition.getInputDirection() == 'L')
                    currHead--;

                // if stack1 is not empty
                if (!stack1.isEmpty()) {
                    if (stack1.peek() != transition.getStack1Pop() && transition.getStack1Pop() != '^') {
                        sb1.append("\n");
                        sb1.append("Rejected. Popping a different stack symbol.");
                        System.out.println("Rejected. Popping a different stack symbol.");

                        stringAccepted = false;
                        break;
                        // return false;
                    } else if (transition.getStack1Pop() == stack1.peek()) {
                        // System.out.println("Popping something stack1");
                        stack1.pop();
                    }
                } else if (stack1.isEmpty()) {
                    if (transition.getStack1Pop() != '^') {
                        // System.out.println("Rejected. Popping on an empty stack.");
                        stringAccepted = false;
                        break;
                        // return false;
                    }
                }

                if (!stack2.isEmpty()) {
                    if (stack2.peek() != transition.getStack2Pop() && transition.getStack2Pop() != '^') {
                        sb1.append("\n");
                        sb1.append("Rejected. Popping a different stack symbol.");
                        System.out.println("Rejected. Popping a different stack symbol.");

                        stringAccepted = false;
                        break;
                        // return false;
                    } else if (transition.getStack2Pop() == stack2.peek()) {
                        // System.out.println("Popping something stack1");
                        stack2.pop();
                    }
                } else if (stack2.isEmpty()) {
                    if (transition.getStack2Pop() != '^') {
                        sb1.append("\n");
                        sb1.append("Rejected. Popping on an empty stack.");
                        System.out.println("Rejected. Popping on an empty stack.");

                        stringAccepted = false;
                        break;
                        // return false;
                    }
                }

                if (transition.getStack1Push() != '^')
                    stack1.push(transition.getStack1Push());

                if (transition.getStack2Push() != '^')
                    stack2.push(transition.getStack2Push());

                /* System.out.println("After popping. ");
                System.out.println("Stack 1 Contents:");
                System.out.println(stack1.toString());
                System.out.println("Stack 2 Contents:");
                System.out.println(stack2.toString());
                System.out.println();
                System.out.println(); */

            }
        }

        return stringAccepted;

    }

    // for debugging only
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
