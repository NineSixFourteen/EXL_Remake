package dos.Types.Lines;

import dos.Types.Expression;
import dos.Types.Line;

public class PrintLine implements Line {


    public Expression val; 

    public PrintLine(Expression e){
        val = e;
    }

    @Override
    public void accept() {

    }

}
