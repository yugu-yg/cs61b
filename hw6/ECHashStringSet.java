import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/** A set of String values.
 *  @author
 */
class ECHashStringSet implements StringSet {

    public ECHashStringSet() {
        _buckets = new LinkedList[5];
        for (int i = 0; i < _buckets.length; i += 1) {
            _buckets[i] = new LinkedList();
        }
    }

    public int getHashCode(String s) {
        return (s.hashCode() & 0x7fffffff) % _buckets.length;
    }

    private void resize() {
        List<String> [] resized_buckets = new LinkedList[_buckets.length * 5];
        for (int i = 0; i < resized_buckets.length; i += 1) {
            resized_buckets[i] = new LinkedList();
        }
        for (List list: _buckets) {
            for (Object s: list) {
                int hashCode = (s.hashCode() & 0x7fffffff) % resized_buckets.length;
                resized_buckets[hashCode].add(s.toString());
            }
        }

        _buckets = resized_buckets;
    }

    @Override
    public void put(String s) {
        if ( num / _buckets.length > _loadFactorMax) {
            resize();
        }
        if (s != null) {
            int hashCode = getHashCode(s);
            _buckets[hashCode].add(s);
            num += 1;
        }
    }

    @Override
    public boolean contains(String s) {
        int hashCode = getHashCode(s);
        return _buckets[hashCode].contains(s);
    }

    @Override
    public List<String> asList() {
        List<String> result = new ArrayList<>();
        for (List list: _buckets) {
            for (Object s: list) {
                result.add(s.toString());
            }
        }
        return result;
    }

    private int _loadFactorMax= 5;
    private double _loadFactorMin= 0.2;
    private List<String>[] _buckets;
    private int num;
}
