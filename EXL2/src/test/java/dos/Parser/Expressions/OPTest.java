package dos.Parser.Expressions;

import java.util.List;

import org.javatuples.Pair;

import dos.Types.Expression;
import dos.Types.Binary.ObjectFieldExpr;
import dos.Types.Binary.ObjectFuncExpr;
import dos.Types.Unary.FunctionExpr;
import dos.Types.Unary.Types.IntExpr;
import dos.Types.Unary.Types.VarExpr;
import dos.Util.Result;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Maths Parser Tests
public class OPTest extends TestCase {

    public static Test suite(){
        return new TestSuite(OPTest.class );
    }

    public static void main(String[] args) {
        testmakeString();
    }

    public static void testmakeString(){
        assertEq("bas.barry.Los(9)", new ObjectFuncExpr(new ObjectFieldExpr(new VarExpr("bas"), "barry"), new FunctionExpr("Los", List.of( new IntExpr(9)))));
        assertEq("bas.Los(9)", new ObjectFuncExpr(new VarExpr("bas"), new FunctionExpr("Los", List.of( new IntExpr(9)))));
        assertEq("bas.barry.barry", new ObjectFieldExpr(new ObjectFieldExpr(new VarExpr("bas"), "barry"), "barry"));
    }

    //Helpers
    public static void assertErr(Result<Pair<Expression, Integer>, Error> res){
        assertTrue(res.hasError());
    }

    public static void assertValue(Result<Pair<Expression, Integer>, Error> res, Expression exp, int point){
        if(res.hasError()){
            assertFalse(true);
        }
        var val = res.getValue();
        assertEq(val.getValue0(), exp);
        assertTrue(val.getValue1() == point);
    }

    private static void assertEq(Expression val, Expression exp) {
        assertTrue(exp.makeString().equals(val.makeString()));
    }

    private static void assertEq(String msg, Expression exp) {
        if(!exp.makeString().equals(msg)){
            System.out.println(exp.makeString());
            System.out.println(msg);
        }
        assertTrue(exp.makeString().equals(msg));
        
    }
    
}
