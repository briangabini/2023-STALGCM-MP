import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Machine {
    // TransitionKey transition = null;
    StringBuilder sb1 = new StringBuilder();

    // properties as defined in the Machine Definition on the paper
    private ArrayList<State> states; // Set of states 'Q'
    private ArrayList<Character> inputAlphabet; // Input alphabet 'sigma'
    private ArrayList<Character> stackAlphabet; // Stack alphabet
    private State initialState; // 'qI'
    private State finalState; // 'qF'
    private char initialStackSymbol; // 'zI'
    private Stack<Character> stack1; // stack 1
    private Stack<Character> stack2; // stack 2
    private int currHead; // the current symbol being read in the input string

    // constructor for the Machine, this will be used in the gui
    public Machine(File machineFile) throws CustomException {

        try {
            int i;
            int len;

            this.states = new ArrayList<>();
            this.inputAlphabet = new ArrayList<>();
            this.stackAlphabet = new ArrayList<>();
            this.stack1 = new Stack<>();
            this.stack2 = new Stack<>();

            BufferedReader machineTextReader = new BufferedReader(new FileReader(machineFile));

            // read each state, the state names will be read and will be attached to each of
            // the State object
            String[] states = machineTextReader.readLine().trim().split(" ");

            len = states.length;
            for (i = 0; i < len; i++) {
                State newState = new State(states[i]);

                this.states.add(newState);
            }

            // iterate and read over every input state
            String[] inputSymbols = machineTextReader.readLine().trim().split(" ");

            len = inputSymbols.length;
            for (i = 0; i < len; i++)
                this.inputAlphabet.add(inputSymbols[i].charAt(0));

            // add these to the input alphabet as well
            this.inputAlphabet.add('>');
            this.inputAlphabet.add('<');

            // System.out.println("NumStackSymbols: " + numberOfStackSymbols);

            // iterate over every stack symbol
            String[] stackSymbols = machineTextReader.readLine().trim().split(" ");

            // System.out.println("StackSymbols = " + Arrays.toString(stackSymbols));

            len = stackSymbols.length;
            for (i = 0; i < len; i++) {
                this.stackAlphabet.add(stackSymbols[i].charAt(0));
            }

            String[] transitions = machineTextReader.readLine().trim().split(" ");
            len = transitions.length;

            // number of transitions (number of states) x (number of input symbols + 2),
            // includes '<' and '>'
            do {

                State currentState = State.getState(transitions[0], this.states);
                char inputSymbol = transitions[1].charAt(0);
                char stack1Pop = transitions[2].charAt(0);
                char stack2Pop = transitions[3].charAt(0);
                State nextState = State.getState(transitions[4], this.states);
                char stack1Push = transitions[5].charAt(0);
                char stack2Push = transitions[6].charAt(0);
                char inputDirection = transitions[7].charAt(0);

                TransitionKey transKey = new TransitionKey(stack1Pop, stack2Pop, nextState, stack1Push, stack2Push,
                        inputDirection);

                currentState.addTransitionKey(inputSymbol, transKey);

                transitions = machineTextReader.readLine().trim().split(" ");
                len = transitions.length;

                // if len == 1, that is already the initial state
                // read the initial state
                if (len == 1) {
                    String stateName = transitions[0];

                    State initialState = State.getState(stateName, this.states);
                    this.initialState = initialState;
                }

            } while (len == 8);

            // read the initial stack symbol
            this.initialStackSymbol = machineTextReader.readLine().trim().charAt(0);

            // push the initial stack symbol to the 2 stacks
            this.stack1.push(this.initialStackSymbol);
            this.stack2.push(this.initialStackSymbol);

            // read the final state (accepting)
            String stateName = machineTextReader.readLine();

            State finalState = State.getState(stateName, this.states);
            finalState.setIsFinalState(true);
            this.finalState = finalState;

            machineTextReader.close();

        } catch (Exception e) {
            throw new CustomException("Invalid File: " + e);
        }
    }

    public static void main(String[] args) {
        // System.out.println("Test");

        try {
            File machineFile = new File("machine4.txt");
            Machine machine = new Machine(machineFile);

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

                /*
                 * System.out.println("Current State: " + currentState.getName());
                 * System.out.println("Current Input: " + input.charAt(currHead));
                 * System.out.println("Stack 1 Contents:");
                 * System.out.println(stack1.toString());
                 * System.out.println("Stack 2 Contents:");
                 * System.out.println(stack2.toString());
                 */

                TransitionKey transition = currentState.getTransitionKey(input.charAt(currHead));

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

                /*
                 * System.out.println("After popping. ");
                 * System.out.println("Stack 1 Contents:");
                 * System.out.println(stack1.toString());
                 * System.out.println("Stack 2 Contents:");
                 * System.out.println(stack2.toString());
                 * System.out.println();
                 * System.out.println();
                 */

            }
        }

        return stringAccepted;

    }

    // delete this later
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
