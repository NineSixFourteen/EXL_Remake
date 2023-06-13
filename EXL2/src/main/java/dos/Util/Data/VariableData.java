package dos.Util.Data;

import java.util.ArrayList;
import java.util.List;

import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class VariableData {

    List<Variable> vars;

    public VariableData(){
        vars = new ArrayList<>();
    }

    public List<Variable> get(String name) {
        return vars.stream()
                .filter(var -> var.getName().equals(name))
                .toList();
    }

    public void add(Variable var) {
        vars.add(var);
    }

    public void addMany(List<Variable> vars){
        for(Variable var : vars){
            add(var);
        }
    }

    public Result<Variable> get(String name, int startLine) {
        var maybes = vars.stream()
                .filter(var -> var.getName().equals(name))
                .filter(var -> var.getStartLine() == startLine)
                .toList();
        if(maybes.size() == 1)
            return Results.makeResult(maybes.get(0));
        else if(maybes.size() == 0)
            return Results.makeError(ErrorFactory.makeLogic("Unable to find the variable " + name + " declared on " + startLine ,8));
        else 
            return Results.makeError(ErrorFactory.makeLogic("There are more than one variable " + name + "that has been declared on line " + startLine + "???", 0));
    }
    
}
