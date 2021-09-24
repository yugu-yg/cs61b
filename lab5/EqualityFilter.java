/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        // FIXME: Add your code here.
        _colName = colName;
        _match = match;
        _table = input;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        int index = _table.colNameToIndex(_colName);
        return _next.getValue(index).equals(_match);
    }

    // FIXME: Add instance variables?
    private String _colName;
    private String _match;
    private Table _table;
}
