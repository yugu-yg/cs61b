public class Sum implements IntUnaryFunction {

    private int _sum;

    public Sum(int init) {
        _sum = init;
    }

    public int apply(int head) {
        _sum += head;
        return _sum;
    }

    public int get() {
        return _sum;
    }
}
