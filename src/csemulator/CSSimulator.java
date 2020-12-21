package csemulator;

import java.awt.Dimension;
import java.awt.Toolkit;
import view.JfrmPrincipal;


public class CSSimulator {

    public static void main(String[] args) {
        // TODO code application logic here
        Dimension ds = Toolkit.getDefaultToolkit().getScreenSize();
        JfrmPrincipal jfrmPrincipal = new JfrmPrincipal();
        
        Dimension dw = jfrmPrincipal.getSize();
        jfrmPrincipal.setLocation((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
        jfrmPrincipal.setVisible(true);
    }
}
