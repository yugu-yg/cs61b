/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        // FIXME: Add your code here.
        _colName = colName;
        _subStr = subStr;
        _table = input;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        int index = _table.colNameToIndex(_colName);
        return _next.getValue(index).contains(_subStr);
    }

    // FIXME: Add instance variables?
    private String _colName;
    private String _subStr;
    private Table _table;
}
