# A collection of each error code, a description and places that they are created i.e. files

## Logic 

1  - Using a math operator on two incompatible types, used in []
2  - Using a compare i.e. <,>, ==, != on two values that are of differn't types, used in [ValidateBoolean](../../main/java/dos/EXL/Validator/Boolean/ValBoolean.java)
3  - Using a Not(!) on a non boolean expression, used in [NotExpr](../../main/java/dos/EXL/Types/Unary/NotExpr.java)
4  - Using a non boolean value next a to &&, ||, used in [ValidateBoolean](../../main/java/dos/EXL/Validator/Boolean/ValBoolean.java)
5  - When using a short form if, when both side of the : are of differnt types, used in [LogicExpr](../../main/java/dos/EXL/Types/Trechery/LogicExpr.java)
6  - Function or field used on object was not found in the objects class data, used in [ObjectFuncExpr](../../main/java/dos/EXL/Types/Binary/ObjectFuncExpr.java), [ObjectDeclareExpr](../../main/java/dos/EXL/Types/Unary/ObjectDeclareExpr.java) [ClassData]()
7  - Unable to find variable that was refrenced, used in [ValueRecords.java](),
8  - Class/Object refrenced has not been imported, used in  [ValueRecords.java](), [DescripiotnMake]()
9  - Empty Array initlise, used in [ArrayDec](../../main/java/dos/EXL/Types/ArrayExpr.java)
10 - When assigning a value to variable and the value type is incompatible with type of the variable, used in [DeclareLine](../../main/java/dos/EXL/Types/Lines/DeclarLine.java), [Field](../../main/java/dos/EXL/Types/Lines/Field.java), [VarOverwrite](../../main/java/dos/EXL/Types/Lines/Field.java)
11 - The expression is required to be of type boolean i.e. for if statement, used in [ForLine](../../main/java/dos/EXL/Types/Lines/ForLine.java), [IfLine](../../main/java/dos/EXL/Types/Lines/IfLine.java), [whileLine](../../main/java/dos/EXL/Types/Lines/WhileLine.java)
14 - Issue with tags for a function, used in [TagValidator](../../main/java/dos/EXL/Validator/Misc/TagValidator.java)
15 - Issue with tags for a class, used in [TagValidator](../../main/java/dos/EXL/Validator/Misc/TagValidator.java)
20 - Could not determine intended type of function, used in [ValueRecords.java](),
21 - Duplicate Function with same description, used in [ValueRecords.java](),
30 - Could not determine mutal type bewteen types, used in [TypeCombiner]()

## Parser - Prefix is P so all error 1 will show as P1

1  - Unexpected Token, Used in [LogicParser]()
2  - Expected Token but got, used in [LogicParser](), [FunctionParser](), [LineParser](), [ProgramParser]()
3  - Could not find closing bracket, used in [Grabber]()
4  - Not(!) missing expression afterward, used in [LogicParser]()
5  - Could not find the end of line, used in [Grabber]()
7  - Malformed Paramer, used in [FunctionParser]()
8  - Duplicate Paramater name used, used in [FunctionParser]()
10 - Unknown line the parser can not tell what line the user is trying to do is used in [CodeBlockParser](../../main/java/dos/EXL/Parser/CodeBlockParser.java), [ProgramParser]()
12 - New Declaration is missing parts, used in [SymbolParser]()