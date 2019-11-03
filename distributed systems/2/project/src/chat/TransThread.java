package chat;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import util.httpURLConectionGET;

public class TransThread extends Thread
{
	private JRadioButton rdbtnen;
    private JRadioButton rdbtnch;
    private JTextArea textAreaMsg;
	TransThread()
	{
		
	}
	
	public void init(JRadioButton bte,JRadioButton btc,JTextArea tam)
	{
		rdbtnen=bte;
		rdbtnch=btc;
		textAreaMsg=tam;
	}
	
	public synchronized void translate()
	{
		String out="";
		if (rdbtnen.isSelected()){
            out =  httpURLConectionGET.Translate("ch","en",textAreaMsg.getText());
        }else{
            out =  httpURLConectionGET.Translate("en","ch",textAreaMsg.getText());
        }
        if(out.equals("error")){
            //JOptionPane.showMessageDialog(null, "Translation ERROR");
        	System.out.println("Translation ERROR");
        }else{
            textAreaMsg.setText(out);
        }
	}
	
	
	public void run()
	{
		translate();
	}
}
