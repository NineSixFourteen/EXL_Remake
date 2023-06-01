package dos;

import dos.Parser.CBPTest;
import dos.Parser.EPTest;
import dos.Parser.FPTest;
import dos.Parser.PPTest;
import dos.Parser.Builders.CBBTest;
import dos.Parser.Builders.FBTest;
import dos.Parser.Builders.PBTest;
import dos.Parser.Expressions.LPTest;
import dos.Parser.Expressions.MPTest;
import dos.Parser.Expressions.OPTest;
import dos.Parser.Expressions.SPTest;
import dos.Parser.Expressions.VPTest;
import dos.Parser.Lines.DeclareParserTest;
import dos.Parser.Lines.FieldParserTest;
import dos.Parser.Lines.IfParserTest;
import dos.Parser.Lines.SELParserTest;
import dos.Parser.Lines.VarOverParserTest;
import dos.Parser.Lines.WhileParserTest;
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
        runLineParsers();
        runBuilders();
    }

    static void runTokenizer(){
        System.out.println("Running all Tokenizer section tests");
        int count = 0;
        count = runTestSuite("Tokenizer Tests", TokenizerTest.suite()) ? ++count: count;
        count = runTestSuite("String Converter ", TokenizerTest.suite())  ? ++count: count;
        count = runTestSuite("String Grabber  ", SGTest.suite())  ? ++count: count;
        System.out.println("Passed " + count + "/3 test sections");
    }

    static void runExpressionParsers(){
        System.out.println("Running all Expressions Parsers tests");
        int count = 0;
        count = runTestSuite("Object Parser ", OPTest.suite()) ? ++count: count;
        count = runTestSuite("Symbol Parser ", SPTest.suite()) ? ++count: count;
        count = runTestSuite("Value Parser ", VPTest.suite())  ? ++count: count;
        count = runTestSuite("Maths Parser ", MPTest.suite())  ? ++count: count;
        count = runTestSuite("Logic Parser ", LPTest.suite())  ? ++count: count;
        System.out.println("Passed " + count + "/5 test sections");
    }

    static void runParsers(){
        System.out.println("Running all high level parsers tests");
        int count = 0;
        count = runTestSuite("Expression Parser ", EPTest.suite())  ? ++count: count;
        count = runTestSuite("CodeBlock Parser ", CBPTest.suite())  ? ++count: count;
        count = runTestSuite("Function Parser ", FPTest.suite())  ? ++count: count;
        count = runTestSuite("Program Parser ", PPTest.suite())  ? ++count: count;
        System.out.println("Passed " + count + "/4 test sections");
    }

    static void runBuilders(){
        System.out.println("Running all Builder tests");
        int count = 0;
        count = runTestSuite("Code Block Builder ", CBBTest.suite())  ? ++count: count;
        count = runTestSuite("Function Builder ", FBTest.suite())  ? ++count: count;
        count = runTestSuite("Program Builder ", PBTest.suite())  ? ++count: count;
        System.out.println("Passed " + count + "/3 test sections");
    }

    static void runLineParsers(){
        System.out.println("Running all Line parser tests");
        int count = 0;
        count = runTestSuite("Declare Parser", DeclareParserTest.suite())  ? ++count: count;
        count = runTestSuite("Field Parser ", FieldParserTest.suite())  ? ++count: count;
        count = runTestSuite("If Parser ", IfParserTest.suite())  ? ++count: count;
        count = runTestSuite("SEL Parser ", SELParserTest.suite())  ? ++count: count;
        count = runTestSuite("Variable Overwrite Parser", VarOverParserTest.suite())  ? ++count: count;
        count = runTestSuite("While Parser ", WhileParserTest.suite())  ? ++count: count;
        System.out.println("Passed " + count + "/6 test sections");
    }

    static boolean runTestSuite(String name, Test suite){
        System.out.println("\tRunning " + name + " Tests");
        TestResult res = new TestResult();
        suite.run(res);
        if(!res.wasSuccessful()){
            System.out.println("\t" + name + " failed");
            return false;
        }
        return true;
    }
}
