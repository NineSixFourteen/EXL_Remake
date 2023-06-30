package dos.Validate.Expressions;

import java.util.List;

import dos.EXL.Filer.Builder.ClassDataBuilder;
import dos.EXL.Filer.Builder.DataInterfaceBuilder;
import dos.EXL.Filer.Builder.ImportsDataBuilder;
import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.Function.FunctionData;
import dos.EXL.Filer.Program.Function.Variable;
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
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Barry", "Lala.Barry",
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("","Barry","()Lala.Barry", List.of()))
                                .build()   
                    ).build()
                ).build()
        );
        assertValid(
            new ObjectFieldExpr(new VarExpr("a"), "Baba"),
            "float",
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Bars", "Lala.VBars",
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("","Lala.Bars","()Lala.Barry\"", List.of()))
                                .addField("Baba", "float")
                                .build()   
                    ).build()
                )
                .addVar(new Variable("a", "Bars", 0, 0, 0))
                .build()
        );
        assertValid(
            new ObjectFuncExpr(new VarExpr("a"), new FunctionExpr("Babb", List.of())),
            "float",
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Bars", "Lala.VBars",
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("","Lala.Bars","()Lala.Barry\"", List.of()))
                                .addFunction(new FunctionData("Babb","float","()F", List.of()))
                                .build()   
                    ).build()
                )
                .addVar(new Variable("a", "Bars", 0, 0, 0))
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
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Bars", "Lala.VBars",
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("","Lala.Bars","()Lala.Bars", List.of()))
                                .addField("babs","Cura")
                                .addFunction(new FunctionData("Babb","float","()F", List.of()))
                                .build())
                        .addImports("Cura", "CuraCura", 
                            new ClassDataBuilder()
                                .addConstructor(new FunctionData("","CuraCura","(CuraCuraF)CuraCura",List.of()))
                                .addFunction(new FunctionData("Babb","float","(CuraCura)F", List.of()))
                                .build())
                            .build()
                )
                .addVar(new Variable("a", "Bars", 0, 0, 0))
                .build()
        );
    }

    public static void testErrors(){
        assertError(new ObjectFuncExpr(new VarExpr("a"), new FunctionExpr("aaa", List.of())), 
            "L6", 
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Barry", "Baaaaaary", 
                            new ClassDataBuilder().build()).build()
                )
                .addVar(new Variable("a", "Barry", 0, 0, 0))
                .build()
        );
        assertError(new ObjectFieldExpr(new VarExpr("a"),"aa"), 
            "L6", 
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Barry", "Baaaaaary", 
                            new ClassDataBuilder().build()).build()
                )
                .addVar(new Variable("a", "Barry", 0, 0, 0))
                .build()
        );
        assertError(new ObjectDeclareExpr("Barry",List.of()), 
            "L6", 
            new DataInterfaceBuilder("")
                .addImports(
                    new ImportsDataBuilder()
                        .addImports("Barry", "Baaaaaary", 
                            new ClassDataBuilder().build()).build()
                )
                .addVar(new Variable("a", "Barry", 0, 0, 0))
                .build()
        );
        assertError(new ObjectDeclareExpr("Barry",List.of()), 
        "L8", 
        new DataInterfaceBuilder("")
            .addImports(
                new ImportsData()
            )
                .addVar(new Variable("a", "Barry", 0, 0, 0))
            .build()
    );
    }

    private static void assertValid(Expression exp, String predicatedType, DataInterface visitor){
        Result<String> type = exp.getType(visitor,0);
        if(type.hasError()){
            System.out.println(type.getError().getFullErrorCode());
            assertTrue(false);
        }
        assertTrue(type.getValue().equals(predicatedType));
    }

    public static void assertError(Expression exp, String errorcode, DataInterface visitor){
        Maybe<MyError> errorMaybe = exp.validate(visitor,0);
        if(!errorMaybe.hasValue()){
            System.out.println("Missed errorcode - " + errorcode);
            assertTrue(false);
        }
        assertTrue(errorcode.equals(errorMaybe.getValue().getFullErrorCode()));
    }
    
}
