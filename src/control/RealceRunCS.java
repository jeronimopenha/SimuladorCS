package control;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List; 
import javax.swing.JLabel;
import javax.swing.JTextField;
import view.JdlgEscaninho;
import view.JdlgImpress;
import view.JfrmPrincipal;

public class RealceRunCS {

    //private JButton jbtnStart, jbtnStop;
    private JfrmPrincipal jfrmPrincipal;
    private JdlgEscaninho jdlgEscaninho;
    private JdlgImpress jdlgImpress;
    private HashMap<Integer, JTextField> textMap;
    private HashMap<Integer, JTextField> progMap = new HashMap<>();
    private HashMap<Integer, JLabel> labelMap;
    private HashMap<Integer, JLabel> dataMap = new HashMap<>();
    private List<Integer> cardList = new ArrayList<>();
    private RealceCodeInterpreter codeInterpreter = new RealceCodeInterpreter();

    public RealceRunCS(HashMap<Integer, JTextField> textMap, JfrmPrincipal jfrmPrincipal) {
        this.textMap = textMap;
        this.jfrmPrincipal = jfrmPrincipal;
    }

    public void start() {
        this.openWindows();
        this.cardList.clear();
        this.jfrmPrincipal.blockReformatComponents();
        this.labelMap = this.jdlgEscaninho.getLabelMap();
        cardList = this.jfrmPrincipal.getCards();
        ArrayList<Integer> programCells = this.codeInterpreter.getProgramCells(this.textMap);
        setProgramCells(programCells);
        setDataCells(this.codeInterpreter.getDataCells(this.textMap), programCells);
        this.codeInterpreter.createInstructions(this.progMap);
        this.codeInterpreter.execute(this.dataMap, cardList, this.jdlgImpress.getJtxaImpress(), this.labelMap);
    }

    public void stop() {
        this.closeWindows();
        this.jfrmPrincipal.releaseComponents();
    }

    public String[] removeEmptyData(String[] s) {
        String[] aux;
        int emptyCounter = 0;

        for (String s0 : s) {
            if (s0.equals("")) {
                emptyCounter++;
            }
        }
        aux = new String[s.length - emptyCounter];
        int i = 0;
        for (String s0 : s) {
            if (!s0.equals("")) {
                aux[i] = s0;
                i++;
            }
        }
        return aux;
    }

    public String removeSpaces(String raw) {
        char[] charArray = raw.toCharArray();
        raw = "";

        for (char c : charArray) {
            if (c != ' ') {
                raw += c;
            }
        }
        return raw;
    }

    public void setProgramCells(ArrayList<Integer> progCells) {
        for (Integer i = 1; i < 17; i++) {
            if (progCells.contains(i)) {
                this.progMap.put(i, this.textMap.get(i));
                this.labelMap.get(i).setText(this.textMap.get(i).getText());
            }
        }
    }

    public void setDataCells(ArrayList<Integer> cells, ArrayList<Integer> progCells) {
        for (Integer i = 1; i < 17; i++) {
            if (!progCells.contains(i)) {
                this.dataMap.put(i, this.labelMap.get(i));
                this.labelMap.get(i).setText(this.textMap.get(i).getText());
            }
        }
        for (Integer i : cells) {
            this.labelMap.get(i).setText(this.textMap.get(i).getText());
        }
    }

    private void openWindows() {
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        //Dimension dw = jdlgEscaninho.getSize();
        //setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
        if (this.jdlgEscaninho == null) {
            this.jdlgEscaninho = new JdlgEscaninho(this.jfrmPrincipal, false);

            Dimension dw = this.jdlgEscaninho.getSize();
            this.jdlgEscaninho.setLocation(0, 0);
            this.jdlgEscaninho.setVisible(true);
        }
        if (this.jdlgImpress == null) {
            this.jdlgImpress = new JdlgImpress(this.jfrmPrincipal, false);

            Dimension dw = this.jdlgImpress.getSize();
            this.jdlgImpress.setLocation(0, this.jdlgEscaninho.getHeight());
            this.jdlgImpress.setVisible(true);
        }
        this.jfrmPrincipal.setLocation(this.jdlgEscaninho.getWidth(), 0);
    }

    private void closeWindows() {
        if (this.jdlgEscaninho != null) {
            this.jdlgEscaninho.dispose();
            this.jdlgEscaninho = null;
        }
        if (this.jdlgImpress != null) {
            this.jdlgImpress.dispose();
            this.jdlgImpress = null;
        }
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dw = this.jfrmPrincipal.getSize();
        this.jfrmPrincipal.setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
    }
}
