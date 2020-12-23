/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTextField;
import java.util.List;
import sun.security.krb5.internal.ktab.KeyTabConstants;
import view.JfrmPrincipal;

/**
 *
 * @author Gustavo Rocha
 */
public class ArquivoCS {

    private List<JTextField> textFields;
    private JfrmPrincipal principal;

    public ArquivoCS(JfrmPrincipal principal) {
        this.principal = principal;
        this.textFields = principal.getTextFields();
    }

    public void escrever(String path) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));

        for (JTextField txt : textFields) {
            String textToWrite = txt.getName()+":";
            if (!txt.getText().isEmpty()) {
                textToWrite+=txt.getText() + "\n";
                //buffWrite.append(txt.getText() + "\n");
            } else {
                textToWrite+=txt.getText() + " \n";
                //buffWrite.append("\n");
            }
            buffWrite.append(textToWrite);
            //System.out.println(txt.getText());
        }
        buffWrite.append("cards\n");
        List<Integer> cards = principal.getCards();
        for(Integer card:cards){
            buffWrite.append(card.toString()+"\n");
        }
        buffWrite.close();
    }

    public List<String> abrir(String path) throws IOException {
        List<String> conteudo = new ArrayList<String>();
        BufferedReader buffRead = new BufferedReader(new FileReader(path));

        String line;
        while ((line = buffRead.readLine()) != null) {
            conteudo.add(line);
            //System.out.println(line);
        }
        buffRead.close();
        return conteudo;
    }
}
