package dos.Validate.Line;

import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Parser.Factorys.LineFactory;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.LThanEqExpr;
import dos.EXL.Types.Lines.DeclarLine;
import dos.EXL.Types.Unary.Types.BoolExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Util.Maybe;
import dos.Util.ValueRecords;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

// Validate Block Tests i.e. ifs, for, while
public class VBTest  extends TestCase{
    
    public static Test suite(){
        return new TestSuite(VBTest.class);
    }

    public static void main(String[] args) {  
        testIfs();
        testFors();
        testWhile();
    }

    public static void testWhile() {
        Line line = LineFactory.whileL(
            new LThanEqExpr(new IntExpr(9), new IntExpr(10)),
             new CodeBlockBuilder()
                .addWhile(
                    new BoolExpr(true), 
                    new CodeBlockBuilder().build())
            .build());
        Maybe<MyError> x = line.validate(new ValueRecords());
        if(x.hasValue()){
            assertTrue(false);
        }
        Line line2 = LineFactory.whileL(
            new IntExpr(10),
             new CodeBlockBuilder()
                .addWhile(
                    new BoolExpr(true), 
                    new CodeBlockBuilder().build())
            .build());
        Maybe<MyError> y = line2.validate(new ValueRecords());
        if(!y.hasValue()){
            assertTrue(false);
        }
        Line line3 = LineFactory.whileL(
            new BoolExpr(false),
             new CodeBlockBuilder()
                .addWhile(
                    new IntExpr(10), 
                    new CodeBlockBuilder().build())
            .build());
        Maybe<MyError> z = line3.validate(new ValueRecords());
        if(!z.hasValue()){
            assertTrue(false);
        }
    }

    public static void testFors() {
        Line line = LineFactory.forL(
            new DeclarLine("i","int",new IntExpr(10)),
            new BoolExpr(false),
            LineFactory.exprL(new IntExpr(10)),
            new CodeBlockBuilder()
                .addFor(new DeclarLine("l","int", new IntExpr(0)), new BoolExpr(false), LineFactory.exprL(new IntExpr(10)) , 
                    new CodeBlockBuilder().build())
                .build()
            );
        var x = line.validate(new ValueRecords());
        if(x.hasValue()){
            assertTrue(false);
        }
    }

    public static void testIfs() {
        Line line = LineFactory.ifL(
            new LThanEqExpr(new IntExpr(9), new IntExpr(10)),
             new CodeBlockBuilder()
                .addWhile(
                    new BoolExpr(true), 
                    new CodeBlockBuilder().build())
            .build());
        Maybe<MyError> x = line.validate(new ValueRecords());
        if(x.hasValue()){
            assertTrue(false);
        }
        Line line2 = LineFactory.ifL(
            new IntExpr(10),
             new CodeBlockBuilder()
                .addWhile(
                    new BoolExpr(true), 
                    new CodeBlockBuilder().build())
            .build());
        Maybe<MyError> y = line2.validate(new ValueRecords());
        if(!y.hasValue()){
            assertTrue(false);
        }
        Line line3 = LineFactory.ifL(
            new BoolExpr(false),
             new CodeBlockBuilder()
                .addWhile(
                    new IntExpr(10), 
                    new CodeBlockBuilder().build())
            .build());
        Maybe<MyError> z = line3.validate(new ValueRecords());
        if(!z.hasValue()){
            assertTrue(false);
        }
    }

    
}
