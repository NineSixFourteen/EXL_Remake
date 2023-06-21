package dos.EXL.Compiler.ASM;


import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import dos.EXL.Filer.Imports.ImportsData;
import dos.EXL.Filer.Program.ProgramData;
import dos.EXL.Types.Function;
import dos.EXL.Types.Program;
import dos.EXL.Types.Lines.Field;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.VisitInterface;

public class Compiler {

    private ProgramData PD; 
    private Program Prog;
    private ClassWriter cw;
    

    public Compiler(ProgramData PD, Program prog){
        this.PD = PD;
        this.Prog = prog;
        cw = new ClassWriter(0);
    }

    public void compile(){
        createFields();
        compileFields();
        compileMethods();
    }

    private void compileMethods() {
        for(Function f : Prog.getFunctions()){
            compileFunc(f);
        }
    }

    private void compileFunc(Function f) {
        ImportsData imports = PD.getImports();
        var mw = cw.visitMethod(Opcodes.ACC_PUBLIC, f.getName(), f.getDesc(imports).getValue(), null, null);
        var method = new MethodInterface(PD.getDataInterface(f.getKey(imports).getValue()).getValue(), new VisitInterface(mw));
        method.compile(f.getBody());
        method.end();
    }

    private void createFields() {
        for(Field f : Prog.getFields()){
            FieldVisitor fieldVisitor = cw.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,f.getName(), f.getType(), null, null);
            fieldVisitor.visitEnd();
        }
    }

    private void compileFields() {
        var FieldMethod = cw.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
        FieldMethod.visitCode();
        var method = new MethodInterface(PD.getDataInterface("fields").getValue(), new VisitInterface(FieldMethod));
        for(Field f : Prog.getFields()){
            compileField(f, method);
        }
    }

    private void compileField(Field f, MethodInterface meth) {
        f.toASM(meth);
        meth.getVisitor().visitFieldInsn(Opcodes.PUTSTATIC, "",  f.getName(), f.getType());
    }
    
}
