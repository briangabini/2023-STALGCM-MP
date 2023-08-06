/*
 * Guide
 * δ(curr_state, input_symbol, stack1Pop, stack2_pop) → {(next_state,
 * stack1_push, stack2_push, input_direction)}
 */

public class TransitionKey {
    private char stack1Pop; // char to pop in stack 1
    private char stack2Pop; // char to pop in stack 2
    private State state; // next_state to transition to
    private char stack1Push; // char to push to stack 1
    private char stack2Push; // char to push
    private char inputDirection; // direction to read the next input

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
