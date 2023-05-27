package dos.EXL.Parser.Util;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Tokenizer.Types.Token;
import dos.EXL.Types.Tag;
import dos.Util.Result;
import dos.Util.Results;

public class TagGrabber {
    
    public static Result<Pair<List<Tag>,Integer>>  getClassTags(List<Token> tokens, int point){
        List<Tag> tags = new ArrayList<>();
        boolean lookingTag = true;
        while(lookingTag){
            switch(tokens.get(point++).getType()){
                case Private:
                    tags.add(Tag.Private);
                    break;
                case Static:
                    tags.add(Tag.Static);
                    break;
                case Public:
                    tags.add(Tag.Public);
                    break;
                default:
                    lookingTag = false;
                    point--; 
            }
        }
        return Results.makeResult(new Pair<List<Tag>,Integer>(tags, point));
    }
}
