package control;

import org.omg.CORBA.INTERNAL;

public class Instruction {

    private InstructionEnum Inst;
    private Integer[] ops = new Integer[3];
    private String opString = "";
    private OPCompEnum comp;

    public enum OPCompEnum {

        MAIORQUE, MENORQUE, IGUALA;
    }

    public enum InstructionEnum {

        PGC,
        SOM,
        SUB,
        MUL,
        DIV,
        AV,
        VT,
        IMP,
        PARE,
        CP,
        SE;
    }

    /**
     * @return the Inst
     */
    public InstructionEnum getInst() {
        return Inst;
    }

    /**
     * @param Inst the Inst to set
     */
    public void setInst(InstructionEnum Inst) {
        this.Inst = Inst;
    }

    /**
     * @return the ops
     */
    public Integer[] getOps() {
        return ops;
    }

    /**
     * @param ops the ops to set
     */
    public void setOps(Integer[] ops) {
        this.ops = ops;
    }

    /**
     * @return the opString
     */
    public String getOpString() {
        return opString;
    }

    /**
     * @param opString the opString to set
     */
    public void setOpString(String opString) {
        this.opString = opString;
    }

    /**
     * @return the comp
     */
    public OPCompEnum getComp() {
        return comp;
    }

    /**
     * @param comp the comp to set
     */
    public void setComp(OPCompEnum comp) {
        this.comp = comp;
    }
}
