package dos.Parser;

/* Converting file to many
// Line Parser Tests
public class LPTest extends TestCase  {

    public static Test suite(){
        return new TestSuite(LPTest.class);
    }

    private static void assertError(Result<Line> result, String errorcode){
        if(!result.hasError()){
            System.out.println("Error missed code - " + errorcode);
            assertTrue(false);
        } else {
            var error = result.getError();
            assertTrue(errorcode.equals(error.getFullErrorCode()));
        }
    }

    public static void main(String[] args) {
        testErrorFunctions();
    }

    public static void testErrorFunctions(){
        var feildInfo = LineParser.getField(Tokenizer.convertToTokens("private int double = 10;"));
        assertError(
            feildInfo.hasError() ? Results.makeError(feildInfo.getError()) : Results.makeResult(feildInfo.getValue())//WOW this fells dumb convert from Result<Feild> to Result<Line>
            ,"P2"
        );
        var declareInfo = LineParser.getDeclare(Tokenizer.convertToTokens("int double = 10;"));
        var quick = declareInfo.getValue();
        assertError(
            declareInfo.hasError() ? Results.makeError(declareInfo.getError()) : 
                    Results.makeResult(LineFactory.IninitVariable(quick.getValue0(), quick.getValue1(), quick.getValue2()))
            ,"P2"
        );
        var ifInfo = LineParser.getIf(Tokenizer.convertToTokens("if(9 < 10 {return 8;}"));
        var quick2 = ifInfo.getValue();
        assertError(
            ifInfo.hasError() ? Results.makeError(ifInfo.getError()) : 
                    Results.makeResult(LineFactory.ifL(quick2.getValue0(), quick2.getValue1()))
            ,"P3"
        );
    }

    public static void testFieldHelper(){
        FieldHelper("private boolean bees = false", new Field(List.of(Tag.Private), "bees", new VarExpr("false"), "boolean"));
        FieldHelper("public int inner = 5", new Field(List.of(Tag.Public), "inner", new IntExpr(5), "int"));
        FieldHelper("static String sock = \"lala\"", new Field(List.of(Tag.Static), "sock", new StringExpr("lala"), "string"));
        FieldHelper("public static float oats = 5.0", new Field(List.of(Tag.Public, Tag.Static), "oats", new FloatExpr(5), "float"));
        FieldHelper("private static char cull = 'c'", new Field(List.of(Tag.Private, Tag.Static), "cull", new CharExpr('c'), "char"));
    }

    public static void FieldHelper(String message, Field field){
        Result<Field> result = LineParser.getField(Tokenizer.convertToTokens(message)); 
        if(result.hasError()){
            System.out.println(result.getError().getFullErrorCode());
            assertTrue(false);
        } else {
            if(!result.getValue().makeString(0).equals(field.makeString(0))){
                System.out.println(field.makeString(0));
                System.out.println(result.getValue().makeString(0));
                assertTrue(false);
            }
        }
    }

    public static void testVarOverwrite(){
        VarOverwriteHelper("x = 10;", "x", new IntExpr(10));
        VarOverwriteHelper("c = 'd';", "c", new CharExpr('d'));
        VarOverwriteHelper("b = 9 < 10;", "b", new LThanExpr(new IntExpr(9), new IntExpr(10)));
    }

    public static void VarOverwriteHelper(String message, String name, Expression exptect){
        Result<Pair<String,Expression>> result = LineParser.getVarOver(Tokenizer.convertToTokens(message)); 
        if(result.hasError()){
            System.out.println(result.getError().getFullErrorCode());
            assertTrue(false);
        } else {
            var x = result.getValue();
            if(!name.equals(x.getValue0())){
                System.out.println(name);
                System.out.println(x.getValue0());
                assertTrue(false);
            }
            if(!x.getValue1().makeString().equals(exptect.makeString())){
                System.out.println(exptect.makeString());
                System.out.println(x.getValue1().makeString());
                assertTrue(false);
            }
        }
    }
    
}
*/