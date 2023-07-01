package dos.EXL.Filer.Program.Function;

import java.util.ArrayList;
import java.util.List;

import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Result;
import dos.Util.Results;

public class VariableData {

    List<Variable> vars;
    int nextMemory; 

    public VariableData(){
        vars = new ArrayList<>();
        nextMemory = 0;
    }

    public List<Variable> get(String name) {
        return vars.stream()
                .filter(var -> var.getName().equals(name))
                .toList();
    }
    

    public void add(Variable var) {
        switch(var.getType()){
            case "double":
            case "long":
                nextMemory += 2;
                break;
            default:
                nextMemory++;
        }
        vars.add(var);
    }

    public void setNextMemory(int nextMemory) {
        this.nextMemory = nextMemory;
    }

    public int getNextMemory() {
        return nextMemory;
    }

    public void addMany(List<Variable> vars){
        for(Variable var : vars){
            add(var);
        }
    }

    public List<Variable> getVars() {
        return vars;
    }

    public Result<Variable> get(String name, int startLine) {
        List<Variable> maybes = vars.stream()
                .filter(var -> var.getName().equals(name))
                .filter(var -> var.getStartLine() == startLine)
                .toList();
        if(maybes.size() == 1)
            return Results.makeResult(maybes.get(0));
        else if(maybes.size() == 0){
            var maybess = vars.stream()
                .filter(var -> var.getName().equals(name))
                .filter(var -> startLine >= var.getStartLine() && startLine <= var.getEndLine())
                .toList();
            if(maybess.size() == 1)
                return Results.makeResult(maybess.get(0)); 
            else 
                return Results.makeError(ErrorFactory.makeLogic("Unable to find the variable " + name + " declared on " + startLine ,8));
        }
        else 
            return Results.makeError(ErrorFactory.makeLogic("There are more than one variable " + name + "that has been declared on line " + startLine + "???", 0));
    }
    
}
