public class EmptyList extends WeirdList {
    public EmptyList() {
        super(0, null);
    }

    public int length() {
        return 0;
    }

    public String toString() {
        return "";
    }

    public WeirdList map(IntUnaryFunction func) {
        return new EmptyList();
    }
}
