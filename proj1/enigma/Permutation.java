package enigma;

import java.util.ArrayList;


/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Yu
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _permutation = helper(cycles);
    }

    public ArrayList<String> helper(String cycles) {
        if (cycles.length() == 0) {
            return new ArrayList<>();
        }
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < cycles.length(); i++) {
            if (cycles.charAt(i) == '(') {
                String tmp = "";
                int bracket = 0;
                while (cycles.charAt(i + 1) != ')') {
                    tmp += cycles.charAt(i + 1);
                    i += 1;
                    bracket = i;
                }
                result.add(tmp);
                i += bracket;
            }
        }
        return result;
    }


    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {}

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char input = _alphabet.toChar(wrap(p));
        char output = permute(input);
        return _alphabet.toInt(output);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char input = _alphabet.toChar(wrap(c));
        char output = invert(input);
        return _alphabet.toInt(output);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        for (String tmp : _permutation) {
            if (tmp.indexOf(p) != -1) {
                if (tmp.length() == 1) {
                    return p;
                } else if (tmp.indexOf(p) + 1 == tmp.length()) {
                    return tmp.charAt(0);
                } else {
                    return tmp.charAt(tmp.indexOf(p) + 1);
                }
            }
        }
        throw new EnigmaException("char is not in the alphabet");
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        for (String tmp : _permutation) {
            if (tmp.indexOf(c) != -1) {
                if (tmp.length() == 1) {
                    return c;
                } else if (tmp.indexOf(c) == 0) {
                    return tmp.charAt(tmp.length() - 1);
                } else {
                    return tmp.charAt(tmp.indexOf(c) - 1);
                }
            }
        }
        throw new EnigmaException("char is not in the alphabet");
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (String tmp : _permutation) {
            if (tmp.length() == 1) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    private ArrayList<String> _permutation;
}
