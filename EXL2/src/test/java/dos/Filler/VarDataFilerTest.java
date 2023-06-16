package dos.Filler;

import java.util.List;

import dos.EXL.Filer.VarDataFiler;
import dos.EXL.Filer.Builder.ImportsDataBuilder;
import dos.EXL.Filer.Builder.VarDataBuilder;
import dos.EXL.Filer.Imports.ClassData;
import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Parser.Builders.CodeBlockBuilder;
import dos.EXL.Types.Function;
import dos.EXL.Types.Unary.Types.FloatExpr;
import dos.EXL.Types.Unary.Types.IntExpr;
import dos.Validate.Expressions.ValBooleanTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class VarDataFilerTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValBooleanTest.class);
    }

    public static void main(String[] args) {
        testValid();
    }

    public static void testValid(){
        assertValid(
            new Function("", List.of(), "", 
                new CodeBlockBuilder()
                    .addDeclare("Ads", "float", new FloatExpr(0))
                    .addPrint(new IntExpr(2))
                    .addPrint(new IntExpr(2))
                    .addPrint(new IntExpr(2))
                    .addPrint(new IntExpr(2))
                    .addPrint(new IntExpr(2))
                    .addDeclare("Add", "int", new IntExpr(0))
                    .addPrint(new IntExpr(2))
                    .addPrint(new IntExpr(2))
                    .addPrint(new IntExpr(2))
                    .addPrint(new IntExpr(2))
                    .addPrint(new IntExpr(2))
                    .build()
                , List.of())
            ,
            new VarDataBuilder()
                .addVar(new Variable("Ads", "float", 0, new LaterInt(12), 0))
                .addVar(new Variable("Add", "int", 6, new LaterInt(12), 1))
                .build()
        );
    }

    private static void assertValid(Function func, VariableData data){
        var actual = VarDataFiler.getFuncData(func, new ImportsDataBuilder().addImports("", "", new ClassData()).build());
        if(actual.hasError()){
            System.out.println(actual.getError().getFullErrorCode());
            assertTrue(false);
        }
        var vars = actual.getValue().getValue1().getVars();
        var varsD = data.getVars();
        assertTrue(vars.size() == varsD.size());
        for(int i = 0; i < vars.size();i++){
            assertTrue(VariableEq(vars.get(i), varsD.get(i)));
        }
    }

    private static boolean VariableEq(Variable actual, Variable correct){
        return  actual.getStartLine() == correct.getStartLine() && 
                actual.getEndLine()   == correct.getEndLine()   &&
                actual.getName()      == correct.getName()      && 
                actual.getType()      == correct.getType()      && 
                actual.getMemory()    == correct.getMemory()    ;

    }
    
}
