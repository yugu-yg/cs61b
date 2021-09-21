public class Add implements IntUnaryFunction {

    private int _n;

    public Add(int n) {
        _n = n;
    }

    @Override
    public int apply(int x) {
        return x + this._n;
    }
}
