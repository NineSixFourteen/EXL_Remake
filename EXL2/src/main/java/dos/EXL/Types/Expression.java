package dos.EXL.Types;

import dos.Util.Maybe;
import dos.Util.ValueRecords;

public interface Expression {
    
    public void accept();

    public String makeString();

    public Maybe<Error> validate(ValueRecords records);

    public void toASM();

    public Result<String,Error> getType(ValueRecords records);
}
