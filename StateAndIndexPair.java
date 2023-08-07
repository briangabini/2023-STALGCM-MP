public class StateAndIndexPair {
    String stateName;
    int index; // 0 for left endmarker, n + 1 for right endmarker

    public StateAndIndexPair(String stateName, int index) {
        this.stateName = stateName;
        this.index = index;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return stateName + index;
    }


}
