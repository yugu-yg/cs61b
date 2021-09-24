/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {

    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        // FIXME: Add your code here.
        _col1 = colName1;
        _col2 = colName2;
        _table = input;
    }

    @Override
    protected boolean keep() {
        // FIXME: Replace this line with your code.
        int index1 = _table.colNameToIndex(_col1);
        int index2 = _table.colNameToIndex(_col2);
        return _next.getValue(index1).equals(_next.getValue(index2));
    }

    // FIXME: Add instance variables?
    private String _col1;
    private String _col2;
    private Table _table;
}
