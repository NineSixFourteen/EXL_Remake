package dos;

import dos.Parser.Expressions.LPTest;
import dos.Parser.Expressions.MPTest;
import dos.Parser.Expressions.OPTest;
import dos.Parser.Expressions.SPTest;
import dos.Parser.Expressions.VPTest;
import dos.Tokenizer.TokenizerTest;
import dos.Tokenizer.Util.SGTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class AppTest extends TestCase{

    public static void main(String[] args) {
        runTokenizer();
        runExpressionParsers();
        runParsers();
    }

    static void runTokenizer(){
        System.out.println("Running all Tokenizer section tests");
        int count = 0;
        TestResult result = new TestResult();
        count = runTestSuite("Tokenizer Tests", TokenizerTest.suite(), result) ? ++count: count;
        count = runTestSuite("String Converter ", TokenizerTest.suite(), result)  ? ++count: count;
        count = runTestSuite("String Grabber  ", SGTest.suite(), result)  ? ++count: count;
        System.out.println("Passed " + count + "/3 test sections");
    }

    static void runExpressionParsers(){
        System.out.println("Running all Expressions Parsers tests");
        TestResult result = new TestResult();
        int count = 0;
        count = runTestSuite("Maths Parser ", MPTest.suite(), result)  ? ++count: count;
        count = runTestSuite("Object Parser ", OPTest.suite(), result) ? ++count: count;
        count = runTestSuite("Symbol Parser ", SPTest.suite(), result) ? ++count: count;
        count = runTestSuite("Value Parser ", VPTest.suite(), result)  ? ++count: count;
        //count = runTestSuite("Logic Parser ", LPTest.suite(), result)  ? ++count: count;
        System.out.println("Passed " + count + "/4 test sections");
    }

    static void runParsers(){
        System.out.println("Running all high level parsers tests");
        TestResult result = new TestResult();
        int count = 0;
        count = runTestSuite("Line Parser ", dos.Parser.LPTest.suite(), result)  ? ++count: count;
        count = runTestSuite("Line Parser ", dos.Parser.EPTest.suite(), result)  ? ++count: count;
        count = runTestSuite("Line Parser ", dos.Parser.CBPTest.suite(), result)  ? ++count: count;
        System.out.println("Passed " + count + "/3 test sections");

    }

    static boolean runTestSuite(String name, Test suite, TestResult res){
        System.out.println("\tRunning " + name + " Tests");
        suite.run(res);
        if(!res.wasSuccessful()){
            System.out.println("\t" + name + " failed");
            return false;
        }
        return true;
    }
}
