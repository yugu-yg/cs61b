/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {

    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        // FIXME: Add your code here.
        _colName = colName;
        _ref = ref;
        _table = input;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        int index = _table.colNameToIndex(_colName);
        return (_next.getValue(index).compareTo(_ref) > 0);
    }

    // FIXME: Add instance variables?
    private String _colName;
    private String _ref;
    private Table _table;
}
