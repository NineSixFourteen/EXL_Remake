package dos.Validate.Expressions;

import java.util.List;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.ObjectFieldExpr;
import dos.EXL.Types.Binary.ObjectFuncExpr;
import dos.EXL.Types.Unary.FunctionExpr;
import dos.EXL.Types.Unary.ObjectDeclareExpr;
import dos.EXL.Types.Unary.Types.VarExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.DataInterface;
import dos.Util.Data.FunctionData;
import dos.Util.Data.ImportsData;
import dos.Util.Data.Builder.ClassDataBuilder;
import dos.Util.Data.Builder.FunctionVisitorBuilder;
import dos.Util.Data.Builder.ImportsDataBuilder;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ValObjectTest extends TestCase {

    public static Test suite(){
        return new TestSuite(ValObjectTest.class);
    }

    public static void main(String[] args) {
        testValid();
        testErrors();
    }

    public static void testValid() {
        assertValid(
            new ObjectDeclareExpr("Barry", List.of()),
            "Barry",
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Barry", "Lala.Barry",
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("()Lala.Barry", List.of()))
                                .build()   
                    ).build()
                ).build()
        );
        assertValid(
            new ObjectFieldExpr(new VarExpr("a"), "Baba"),
            "float",
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Bars", "Lala.VBars",
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("()Lala.Bars", List.of()))
                                .addField("Baba", "float")
                                .build()   
                    ).build()
                )
                .addVar("a", "Bars")
                .build()
        );
        assertValid(
            new ObjectFuncExpr(new VarExpr("a"), new FunctionExpr("Babb", List.of())),
            "float",
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Bars", "Lala.VBars",
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("()Lala.Bars", List.of()))
                                .addFunction("Babb", new FunctionData("()F", List.of()))
                                .build()   
                    ).build()
                )
                .addVar("a", "Bars")
                .build()
        );
        assertValid(
            new ObjectFuncExpr(new ObjectFieldExpr(new VarExpr("a"), "babs"), new FunctionExpr("Babb", 
                List.of(
                    new ObjectDeclareExpr("Cura", 
                        List.of(
                            new ObjectFieldExpr(new VarExpr("a"), "babs"),
                            new ObjectFuncExpr(new VarExpr("a"), new FunctionExpr("Babb", List.of()))
                            ))
                    ))),
            "float",
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Bars", "Lala.VBars",
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("()Lala.Bars", List.of()))
                                .addField("babs","Cura")
                                .addFunction("Babb", new FunctionData("()F", List.of()))
                                .build())
                        .addImports("Cura", "CuraCura", 
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("(CuraCuraF)CuraCura",List.of()))
                                .addFunction("Babb", new FunctionData("(CuraCura)F", List.of()))
                                .build())
                            .build()
                )
                .addVar("a", "Bars")
                .build()
        );
    }

    public static void testErrors(){
        assertError(new ObjectFuncExpr(new VarExpr("a"), new FunctionExpr("aaa", List.of())), 
            "L6", 
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Barry", "Baaaaaary", 
                            new ClassDataBuilder().build()).build()
                )
                .addVar("a", "Barry")
                .build()
        );
        assertError(new ObjectFieldExpr(new VarExpr("a"),"aa"), 
            "L6", 
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Barry", "Baaaaaary", 
                            new ClassDataBuilder().build()).build()
                )
                .addVar("a", "Barry")
                .build()
        );
        assertError(new ObjectDeclareExpr("Barry",List.of()), 
            "L6", 
            new FunctionVisitorBuilder()
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Barry", "Baaaaaary", 
                            new ClassDataBuilder().build()).build()
                )
                .addVar("a", "Barry")
                .build()
        );
        assertError(new ObjectDeclareExpr("Barry",List.of()), 
        "L8", 
        new FunctionVisitorBuilder()
            .addImports(
                new ImportsData()
            )
            .addVar("a", "Barry")
            .build()
    );
    }

    private static void assertValid(Expression exp, String predicatedType, DataInterface visitor){
        Result<String> type = exp.getType(visitor);
        if(type.hasError()){
            System.out.println(type.getError().getFullErrorCode());
            assertTrue(false);
        }
        assertTrue(type.getValue().equals(predicatedType));
    }

    public static void assertError(Expression exp, String errorcode, DataInterface visitor){
        Maybe<MyError> errorMaybe = exp.validate(visitor);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
    
}
