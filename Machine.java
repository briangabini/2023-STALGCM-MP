/* 
 * This is an implementation of a 2-way 2-
 * 
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Machine {
    private List<State> states; // Set of states 'Q'
    private List<Character> inputAlphabet; // Input alphabet 'sigma'
    private State initialState; // 'qI'
    private State finalState; // 'qF'
    private int currHead; // the current symbol being read in the input string

    // constructor for the Machine, this will be used
    public Machine(File file) throws CustomException {

        try {
            Buffered fileReader = new BufferedReader(new FileReader(file));

            int numStates = Integer.parseInt(fileReader.readLine().trim());

            // Read each state

        } catch (error e) {

        }
    }

    public main(String[] args) {
        // check if the files are properly taken 
    }

    // TODO: implement backtracking

}
