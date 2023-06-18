package dos.EXL.Validator.Filer;

import java.util.ArrayList;
import java.util.List;

import dos.EXL.Filer.Program.Function.Variable;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;

public class ValidateVarData {
    
    public static Maybe<MyError> validate(VariableData data){
        List<Variable> vars = data.getVars();
        List<String> names = getNames(vars);
        var checkNames = checkNames(names);
        var checkDups = checkForDups(vars);
        if(checkNames.hasValue())
            return checkNames;
        return checkDups;
    }

    private static List<String> getNames(List<Variable> vars) {
        return vars.stream().map(var -> var.getName()).toList();
    }

    private static Maybe<MyError> checkNames(List<String> names){
        for(String name : names){
            var isValid = checkValid(name);
            if(isValid.hasValue())
                return isValid;
        }
        return new Maybe<>();
    }

    private static Maybe<MyError> checkForDups(List<Variable> vars){
        List<Variable> nVars = new ArrayList<>();
        for(Variable var : vars){
            for(Variable nVar : nVars){
                if(nVar.getName() == var.getName()){
                    if(var.getStartLine() >= nVar.getStartLine() && var.getStartLine() <= nVar.getEndLine()){
                        return new Maybe<>(ErrorFactory.makeLogic("Error has duplicate name in same scope " + var.getName() + " that is declared on line" + var.getStartLine(), 44));
                    }
                }
            }
        }
        return new Maybe<>();
    }



    private static Maybe<MyError> checkValid(String name) { // Todo do valid var names
        return new Maybe<>();
    }
}
