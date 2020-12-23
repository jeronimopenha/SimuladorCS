package control;

import control.Instruction.InstructionEnum;
import control.Instruction.OPCompEnum;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RealceCodeInterpreter {
    private Timer timer;
    private HashMap<Integer, Instruction> instructions = new HashMap<Integer, Instruction>();
    /**Função responsável por interpretar o código do Computador Simplificado
     * e executá-lo.
     *
     * @param dataMap - 
     * @param cardList 
     * @param dataStack - pilha de dados a serem executados
     * @param labelMap
     * @param jtxa - acho que é uma collection com os componentes jTextField
     */
    public void execute(HashMap<Integer, JLabel> dataMap, List<Integer> cardList, JTextArea jtxa, HashMap<Integer, JLabel> labelMap) {
        //try {
        
        timer = new Timer();
        
        timer.schedule(new TimerTask() {
            int counter = 1, aux;
            boolean stop = false;
            @Override
            public void run() { 
                if(!stop){
                    for(aux=1;aux<=16;aux++)
                        labelMap.get(aux).setBackground(Color.white);
             
                    if (counter > 16) {
                        jtxa.setText(jtxa.getText() + "ERRO!" + "\n");
                        return;
                    }
                    Integer data0, data1, data2;
                    Instruction inst = instructions.get(counter);
                    labelMap.get(counter).setBackground(Color.yellow);
                    
                    switch (inst.getInst()) {
                        case PGC:
                            if (cardList.isEmpty()) {
                                labelMap.get(counter).setBackground(Color.red);
                                jtxa.setText(jtxa.getText() + "ERRO NÃO EXISTEM MAIS CARTÕES" + "\n");
                                //return;
                                this.stop=true;
                                return;
                            }
                            data0 = cardList.remove(0);
                            labelMap.get(inst.getOps()[0]).setBackground(Color.YELLOW);
                            dataMap.get(inst.getOps()[0]).setText(data0.toString());
                            break;
                        case SOM:
                            data0 = Integer.parseInt(dataMap.get(inst.getOps()[0]).getText());
                            data1 = Integer.parseInt(dataMap.get(inst.getOps()[1]).getText());
                            data2 = data0 + data1;
                            dataMap.get(inst.getOps()[2]).setText(data2.toString());
                            break;
                        case SUB:
                            data0 = Integer.parseInt(dataMap.get(inst.getOps()[0]).getText());
                            data1 = Integer.parseInt(dataMap.get(inst.getOps()[1]).getText());
                            data2 = data1 - data0;
                            dataMap.get(inst.getOps()[2]).setText(data2.toString());
                            break;
                        case MUL:
                            data0 = Integer.parseInt(dataMap.get(inst.getOps()[0]).getText());
                            data1 = Integer.parseInt(dataMap.get(inst.getOps()[1]).getText());
                            data2 = data0 * data1;
                            dataMap.get(inst.getOps()[2]).setText(data2.toString());
                            break;
                        case DIV:
                            data0 = Integer.parseInt(dataMap.get(inst.getOps()[0]).getText());
                            data1 = Integer.parseInt(dataMap.get(inst.getOps()[1]).getText());
                            data2 = data0 / data1;
                            dataMap.get(inst.getOps()[2]).setText(data2.toString());
                            break;
                        case AV:
                        case VT:
                            counter = inst.getOps()[0];
                            labelMap.get(counter).setBackground(Color.GREEN);
                            //continue;
                            return;
                            
                        case IMP:
                            if (inst.getOps()[0] != null) {
                                jtxa.setText(jtxa.getText() + dataMap.get(inst.getOps()[0]).getText());
                                if (!inst.getOpString().equals("")) {
                                    jtxa.setText(jtxa.getText() + " " + inst.getOpString());
                                }
                                jtxa.setText(jtxa.getText() + "\n");
                            } else {
                                if (!inst.getOpString().equals("")) {
                                    jtxa.setText(jtxa.getText() + inst.getOpString() + "\n");
                                }
                            }
                            break;
                        case PARE:
                            jtxa.setText(jtxa.getText() + "PARE\n");
                            stop = true;
                            break;
                        case CP:
                            dataMap.get(inst.getOps()[1]).setText(dataMap.get(inst.getOps()[0]).getText());
                            break;
                        case SE:
                            data0 = Integer.parseInt(dataMap.get(inst.getOps()[0]).getText());
                            data1 = Integer.parseInt(dataMap.get(inst.getOps()[1]).getText());
                            data2 = inst.getOps()[2];
                            switch (inst.getComp()) {
                                case IGUALA:
                                    if (data0 == data1) {
                                        labelMap.get(counter).setBackground(Color.green);
                                        counter = data2;
                                        //continue;
                                        return;
                                    }else{
                                        labelMap.get(counter).setBackground(Color.red);
                                    }
                                    break;
                                case MAIORQUE:
                                    if (data0 > data1) {
                                        labelMap.get(counter).setBackground(Color.green);
                                        counter = data2;
                                        //continue;
                                        return;
                                    }else{
                                        labelMap.get(counter).setBackground(Color.red);
                                    }
                                    break;
                                case MENORQUE:
                                    if (data0 < data1) {
                                        labelMap.get(counter).setBackground(Color.green);
                                        counter = data2;
                                        //continue;
                                        return;
                                    }else{
                                        labelMap.get(counter).setBackground(Color.red);
                                    }
                            }
                            break;
                    }
                    counter++;
                }else{
                    timer.cancel();
                }
            }
        }, 500, 1000);
        //} catch (Exception e) {
        //JOptionPane.showMessageDialog(null, "Ocorreu um erro de execução");
        //    jtxa.setText(jtxa.getText() + "ERRO!" + "\n");
        //}
    }

    public void createInstructions(HashMap<Integer, JTextField> progMap) {
        this.instructions.clear();
        for (int i = 1; i < 17; i++) {
            if (progMap.containsKey(i)) {
                Instruction instruction = new Instruction();
                String raw = progMap.get(i).getText();

                //remove os espaços
                raw = removeSpaces(raw);
                //Remove as instruções
                raw = verifyInstruction(raw, instruction);
                //Remove os comparadores
                raw = verifyComp(raw, instruction);
                //Operadores
                verificaOperadores(raw, instruction);
                this.instructions.put(i, instruction);
            }
        }
        int a = 1;
    }

    public void verificaOperadores(String raw, Instruction instruction) {
        String[] ops = raw.split(",");
        for (int i = 0; i < ops.length; i++) {
            if (i > 2) {
                continue;
            }
            try {
                String s = ops[i].replaceAll("E", "");
                int op = Integer.parseInt(s);
                instruction.getOps()[i] = op;
            } catch (Exception e) {
                instruction.setOpString(ops[i]);
            }
        }
    }

    public String removeSpaces(String raw) {
        char[] charArray = raw.toCharArray();
        raw = "";
        boolean fisrtChar = false, firstSpace = false;

        for (char c : charArray) {
            if (!fisrtChar) {
                if (c >= 'A' && c <= 'Z') {
                    raw += c;
                    fisrtChar = true;
                }
            } else if (!firstSpace) {
                raw += c;
                if (c == ' ') {
                    firstSpace = true;
                }
            } else {
                if (!(c == ' ')) {
                    raw += c;
                }
            }
        }
        return raw;
    }

    private String verifyComp(String raw, Instruction instruction) {
        char[] charArray = raw.toCharArray();
        raw = "";
        for (char c : charArray) {
            switch (c) {
                case '>':
                    instruction.setComp(OPCompEnum.MAIORQUE);
                    raw += ",";
                    break;
                case '<':
                    instruction.setComp(OPCompEnum.MENORQUE);
                    raw += ",";
                    break;
                case '=':
                    instruction.setComp(OPCompEnum.IGUALA);
                    raw += ",";
                    break;
                default:
                    raw += c;
            }
        }
        return raw;
    }

    private String verifyInstruction(String raw, Instruction instruction) {
        String[] inst = raw.split(" ");

        //verifica Operadores
        switch (inst[0]) {
            case "SOM":
                instruction.setInst(InstructionEnum.SOM);
                //instruction.setInstructionType(InstructonType.TWO_COMMA);
                break;
            case "SUB":
                instruction.setInst(InstructionEnum.SUB);
                //instruction.setInstructionType(InstructonType.TWO_COMMA);
                break;
            case "MUL":
                instruction.setInst(InstructionEnum.MUL);
                //instruction.setInstructionType(InstructonType.TWO_COMMA);
                break;
            case "DIV":
                instruction.setInst(InstructionEnum.DIV);
                //instruction.setInstructionType(InstructonType.TWO_COMMA);
                break;
            case "PGC":
                instruction.setInst(InstructionEnum.PGC);
                //instruction.setInstructionType(InstructonType.NO_COMMA);
                break;
            case "AV":
                instruction.setInst(InstructionEnum.AV);
                //instruction.setInstructionType(InstructonType.NO_COMMA);
                break;
            case "VT":
                instruction.setInst(InstructionEnum.VT);
                //instruction.setInstructionType(InstructonType.NO_COMMA);
                break;
            case "IMP":
                instruction.setInst(InstructionEnum.IMP);
                //instruction.setInstructionType(InstructonType.NO_COMMA);
                break;
            case "PARE":
                instruction.setInst(InstructionEnum.PARE);
                break;
            case "CP":
                instruction.setInst(InstructionEnum.CP);
                //instruction.setInstructionType(InstructonType.ONE_COMMA);
                break;
            case "SE":
                instruction.setInst(InstructionEnum.SE);
                //instruction.setInstructionType(InstructonType.ONE_COMMA);
                break;
            default:
            //erro
        }
        if (inst.length > 1) {
            return inst[1];
        }
        return "";
    }

    public ArrayList<Integer> getProgramCells(HashMap<Integer, JTextField> textMap) {
        ArrayList<Integer> cells = new ArrayList<Integer>();

        for (int i = 1; i < textMap.size() + 1; i++) {
            JTextField jtxt = textMap.get(i);
            String s = jtxt.getText();
            s = s.replaceAll(" ", "");
            if (isCode(s)) {
                cells.add(i);
            }
        }
        return cells;
    }

    public ArrayList<Integer> getDataCells(HashMap<Integer, JTextField> textMap) {
        ArrayList<Integer> cells = new ArrayList<Integer>();

        for (int i = 1; i < textMap.size() + 1; i++) {
            JTextField jtxt = textMap.get(i);
            String s = jtxt.getText();
            s = s.replaceAll(" ", "");
            if (isData(s)) {
                cells.add(i);
            }
        }
        return cells;
    }

    private boolean isCode(String s) {
        char[] charArray = s.toCharArray();
        for (char c : charArray) {
            if (c >= 'A' && c <= 'Z') {
                return true;
            }
        }
        return false;
    }

    private boolean isData(String s) {
        char[] charArray = s.toCharArray();

        for (char c : charArray) {
            if (c >= '0' && c <= '9') {
                return true;
            }
        }
        return false;
    }
}
