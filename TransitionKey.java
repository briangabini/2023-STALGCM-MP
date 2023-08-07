/*
 * Represents a transition key that defines a transition in a deterministic 2-way 2-stack PDA.
*/
public class TransitionKey {
    private char stack1Pop; // char to pop in stack 1
    private char stack2Pop; // char to pop in stack 2
    private State state; // next_state to transition to
    private char stack1Push; // char to push to stack 1
    private char stack2Push; // char to push to stack 2
    private char inputDirection; // direction to read the next input

    /**
     * Constructs a TransitionKey with specified parameters.
     *
     * @param stack1Pop      The character to pop from stack 1 during the transition.
     * @param stack2Pop      The character to pop from stack 2 during the transition.
     * @param state          The next state to transition to.
     * @param stack1Push     The character to push onto stack 1 during the transition.
     * @param stack2Push     The character to push onto stack 2 during the transition.
     * @param inputDirection The direction to read the next input ('L' for left, 'R' for right).
     */
    public TransitionKey(char stack1Pop, char stack2Pop, State state, char stack1Push, char stack2Push,
            char inputDirection) {
        this.stack1Pop = stack1Pop;
        this.stack2Pop = stack2Pop;
        this.state = state;
        this.stack1Push = stack1Push;
        this.stack2Push = stack2Push;
        this.inputDirection = inputDirection;
    }

    // getters
    public char getStack1Pop() {
        return stack1Pop;
    }

    public char getStack2Pop() {
        return stack2Pop;
    }

    public State getState() {
        return state;
    }

    public char getStack1Push() {
        return stack1Push;
    }

    public char getStack2Push() {
        return stack2Push;
    }

    public char getInputDirection() {
        return inputDirection;
    }
}
