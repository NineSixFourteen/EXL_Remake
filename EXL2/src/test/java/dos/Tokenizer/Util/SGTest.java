package dos.Tokenizer.Util;

import org.javatuples.Pair;

import dos.EXL.Tokenizer.Util.StringGrabber;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// String Grabber Tests
public class SGTest extends TestCase {

    public static void main(String[] args) {
        testGetString();
        testGetChar();
        testGetWord();
    }

    public static Test suite(){
        return new TestSuite(SCTest.class );
    }

    public static void testGetString(){
        assertEq(StringGrabber.getString("ressa \" lala \" ressa", 6), " lala ", 13);
        assertEq(StringGrabber.getString("\" lala  \".ok()", 0), " lala  ", 8);
        assertEq(StringGrabber.getString("\"lala\"", 0), "lala", 5);
    }

    public static void testGetChar(){
        assertEq(StringGrabber.getChar("ressa 'r' ressa", 6), "r", 8);
        assertEq(StringGrabber.getChar("char c = 'c';", 9), "c", 11);
        assertEq(StringGrabber.getChar("'csadsdsa'", 0), "csadsdsa", 9); // make sense when redo StringGrabber to grab errors
    }

    public static void testGetWord(){
        assertEq(StringGrabber.getWord(" ressaressa as", 1), "ressaressa", 10);
        assertEq(StringGrabber.getWord("ressaressa", 0), "ressaressa", 9);
        assertEq(StringGrabber.getWord("ressaressa ", 0), "ressaressa", 9);
    }

    public static void assertEq(Pair<String, Integer> res, String msg, int point){
        assertTrue(Eq(res, msg, point));
    }

    public static boolean Eq(Pair<String, Integer> res, String msg, int point){
        if(res.getValue0().equals(msg) && res.getValue1() == point){
            return true;
        } else {
            System.out.println("V1: " + res.getValue0() + " V2: " + res.getValue1());
            System.out.println("V1: " + msg + " V2: " + point);
            return false;
        }
    }


    
}
