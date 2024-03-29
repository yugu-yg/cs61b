import java.io.Reader;
import java.io.IOException;


/** Translating Reader: a stream that is a translation of an
*  existing reader.
*  @author your name here
*
*  NOTE: Until you fill in the right methods, the compiler will
*        reject this file, saying that you must declare TrReader
* 	     abstract.  Don't do that; define the right methods instead!
*/
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    public TrReader(Reader str, String from, String to) {
        // TODO: YOUR CODE HERE
        _reader = str;
        _from = from;
        _to = to;
    }

    /* TODO: IMPLEMENT ANY MISSING ABSTRACT METHODS HERE
     */
    private Reader _reader;
    private String _from;
    private String _to;

    @Override
    public void close() throws IOException {
        _reader.close();
    }

    @Override
    public int read(char cbuf[], int off, int len) throws IOException {
        if (len == 0)
        {
            return 0;
        }
        int num = _reader.read(cbuf, off, len);
        for (int i = 0; i + off < cbuf.length; i++)  {
            if (_from.indexOf(cbuf[i + off]) != -1) {
                cbuf[i + off] = _to.charAt(_from.indexOf(cbuf[i + off]));
            }
        }
        return num;
    }
}
