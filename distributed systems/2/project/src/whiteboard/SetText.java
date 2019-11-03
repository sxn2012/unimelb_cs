package whiteboard;

import rmi.ServercomInter;

import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;

public class SetText {

	private JFrame frmSetText;
	private JTextField textField;
	private Canvas canvas;
	private int locx;
	private int locy;
	public ServercomInter servercomInter;
	/**
	 * Create the application.
	 */
	public SetText(Canvas ca, int x, int y, ServercomInter servercomInter)
	{
		canvas=ca;
		locx=x;
		locy=y;
		this.servercomInter = servercomInter;
		initialize();
		frmSetText.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSetText = new JFrame();
		frmSetText.setTitle("Set Text");
		frmSetText.setBounds(100, 100, 388, 197);
		frmSetText.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSetText.getContentPane().setLayout(null);
		frmSetText.setResizable(false);
		
		textField = new JTextField("");
		textField.setFont(new Font("SimSun", Font.PLAIN, 12));
		textField.setPreferredSize(new Dimension(150, 30));
		textField.setMaximumSize(new Dimension(150, 30));
		textField.setBounds(114, 24, 150, 30);
		frmSetText.getContentPane().add(textField);
		
		JLabel lblText = new JLabel("Text:");
		lblText.setBounds(74, 31, 30, 15);
		frmSetText.getContentPane().add(lblText);
		
		JComboBox fontSelector = new JComboBox(new Object[]{});
		fontSelector.setFont(new Font("SimSun", Font.PLAIN, 12));
		fontSelector.setModel(new DefaultComboBoxModel(new String[] {"Academy Engraved LET", "Adobe Arabic", "Adobe Caslon Pro", "Adobe Caslon Pro Bold", "Adobe Devanagari", "Adobe Garamond Pro", "Adobe Garamond Pro Bold", "Adobe Gothic Std B", "Adobe Gurmukhi", "Adobe Hebrew", "Adobe Myungjo Std M", "Adobe Naskh Medium", "Adobe \u4EFF\u5B8B Std R", "Adobe \u5B8B\u4F53 Std L", "Adobe \u660E\u9AD4 Std L", "Adobe \u6977\u4F53 Std R", "Adobe \u7E41\u9ED1\u9AD4 Std B", "Adobe \u9ED1\u4F53 Std R", "Agency FB", "Algerian", "Arial", "Arial Black", "Arial Narrow", "Arial Rounded MT Bold", "Arial Unicode MS", "AWLUnicode", "Bahnschrift", "Baskerville Old Face", "Bauhaus 93", "Bell MT", "Berlin Sans FB", "Berlin Sans FB Demi", "Bernard MT Condensed", "Birch Std", "Blackadder ITC", "Blackletter686 BT", "Blackoak Std", "Bodoni MT", "Bodoni MT Black", "Bodoni MT Condensed", "Bodoni MT Poster Compressed", "Book Antiqua", "Bookman Old Style", "Bookshelf Symbol 7", "Bradley Hand ITC", "Britannic Bold", "Broadway", "Broadway BT", "Brush Script MT", "Brush Script Std", "Buxton Sketch", "Calibri", "Calibri Light", "Californian FB", "Calisto MT", "Calligraph421 BT", "Cambria", "Cambria Math", "Candara", "Candara Light", "Castellar", "Cataneo BT", "Centaur", "Century", "Century Gothic", "Century Schoolbook", "Chaparral Pro", "Chaparral Pro Light", "Charlemagne Std", "Chiller", "Colonna MT", "Comic Sans MS", "Consolas", "Constantia", "Cooper Black", "Cooper Std Black", "Copperplate Gothic Bold", "Copperplate Gothic Light", "Corbel", "Corbel Light", "Courier New", "Curlz MT", "Dialog", "DialogInput", "Ebrima", "Edwardian Script ITC", "Elephant", "Engravers MT", "Eras Bold ITC", "Eras Demi ITC", "Eras Light ITC", "Eras Medium ITC", "Euclid", "Euclid Extra", "Euclid Fraktur", "Euclid Math One", "Euclid Math Two", "Euclid Symbol", "Felix Titling", "Fences", "Footlight MT Light", "Forte", "Franklin Gothic Book", "Franklin Gothic Demi", "Franklin Gothic Demi Cond", "Franklin Gothic Heavy", "Franklin Gothic Medium", "Franklin Gothic Medium Cond", "Freestyle Script", "French Script MT", "Gabriola", "Gadugi", "Garamond", "Georgia", "Giddyup Std", "Gigi", "Gill Sans MT", "Gill Sans MT Condensed", "Gill Sans MT Ext Condensed Bold", "Gill Sans Ultra Bold", "Gill Sans Ultra Bold Condensed", "Gloucester MT Extra Condensed", "Goudy Old Style", "Goudy Stout", "Haettenschweiler", "Harlow Solid Italic", "Harrington", "High Tower Text", "Highlight LET", "Hobo Std", "HolidayPi BT", "HoloLens MDL2 Assets", "icomoon", "Impact", "Imprint MT Shadow", "Informal Roman", "Ink Free", "Javanese Text", "John Handy LET", "Jokerman", "Jokerman LET", "Juice ITC", "Kozuka Gothic Pr6N B", "Kozuka Gothic Pr6N EL", "Kozuka Gothic Pr6N H", "Kozuka Gothic Pr6N L", "Kozuka Gothic Pr6N M", "Kozuka Gothic Pr6N R", "Kozuka Gothic Pro B", "Kozuka Gothic Pro EL", "Kozuka Gothic Pro H", "Kozuka Gothic Pro L", "Kozuka Gothic Pro M", "Kozuka Gothic Pro R", "Kozuka Mincho Pr6N B", "Kozuka Mincho Pr6N EL", "Kozuka Mincho Pr6N H", "Kozuka Mincho Pr6N L", "Kozuka Mincho Pr6N M", "Kozuka Mincho Pr6N R", "Kozuka Mincho Pro B", "Kozuka Mincho Pro EL", "Kozuka Mincho Pro H", "Kozuka Mincho Pro L", "Kozuka Mincho Pro M", "Kozuka Mincho Pro R", "Kristen ITC", "Kunstler Script", "La Bamba LET", "Leelawadee UI", "Leelawadee UI Semilight", "Letter Gothic Std", "Lithos Pro Regular", "Lucida Bright", "Lucida Calligraphy", "Lucida Console", "Lucida Fax", "Lucida Handwriting", "Lucida Sans", "Lucida Sans Typewriter", "Lucida Sans Unicode", "Magneto", "Maiandra GD", "Malgun Gothic", "Malgun Gothic Semilight", "Marlett", "Matura MT Script Capitals", "Mekanik LET", "Mesquite Std", "Microsoft Himalaya", "Microsoft JhengHei", "Microsoft JhengHei Light", "Microsoft JhengHei UI", "Microsoft JhengHei UI Light", "Microsoft New Tai Lue", "Microsoft PhagsPa", "Microsoft Sans Serif", "Microsoft Tai Le", "Microsoft YaHei UI", "Microsoft YaHei UI Light", "Microsoft Yi Baiti", "Milano LET", "MingLiU-ExtB", "MingLiU_HKSCS-ExtB", "Minion Pro", "Minion Pro Cond", "Minion Pro Med", "Minion Pro SmBd", "MisterEarl BT", "Mistral", "Modern No. 20", "Mongolian Baiti", "Monospaced", "Monotype Corsiva", "MS Gothic", "MS Outlook", "MS PGothic", "MS Reference Sans Serif", "MS Reference Specialty", "MS UI Gothic", "MT Extra", "MT Extra Tiger", "MV Boli", "Myanmar Text", "Myriad Arabic", "Myriad Hebrew", "Myriad Pro", "Myriad Pro Cond", "Myriad Pro Light", "Niagara Engraved", "Niagara Solid", "Nirmala UI", "Nirmala UI Semilight", "Nueva Std", "Nueva Std Cond", "OCR A Extended", "OCR A Std", "Odessa LET", "Old English Text MT", "OldDreadfulNo7 BT", "One Stroke Script LET", "Onyx", "Orange LET", "Orator Std", "Palace Script MT", "Palatino Linotype", "Papyrus", "Parchment", "ParkAvenue BT", "Perpetua", "Perpetua Titling MT", "Playbill", "PMingLiU-ExtB", "Poor Richard", "Poplar Std", "Prestige Elite Std", "Pristina", "Pump Demi Bold LET", "Quixley LET", "Rage Italic", "Rage Italic LET", "Ravie", "Rockwell", "Rockwell Condensed", "Rockwell Extra Bold", "Rosewood Std Regular", "Ruach LET", "SansSerif", "Script MT Bold", "Scruff LET", "Segoe Marker", "Segoe MDL2 Assets", "Segoe Print", "Segoe Script", "Segoe UI", "Segoe UI Black", "Segoe UI Emoji", "Segoe UI Historic", "Segoe UI Light", "Segoe UI Semibold", "Segoe UI Semilight", "Segoe UI Symbol", "Serif", "Showcard Gothic", "SimSun-ExtB", "Sitka Banner", "Sitka Display", "Sitka Heading", "Sitka Small", "Sitka Subheading", "Sitka Text", "SketchFlow Print", "Smudger LET", "Snap ITC", "Source Sans Pro", "Source Sans Pro Black", "Source Sans Pro ExtraLight", "Source Sans Pro Light", "Source Sans Pro Semibold", "Square721 BT", "Staccato222 BT", "Stencil", "Stencil Std", "Sylfaen", "Symbol", "Symbol Tiger", "Symbol Tiger Expert", "Tahoma", "TeamViewer13", "Tekton Pro", "Tekton Pro Cond", "Tekton Pro Ext", "Tempus Sans ITC", "Tiger", "Tiger Expert", "Times New Roman", "Tiranti Solid LET", "Trajan Pro", "Trajan Pro 3", "Trebuchet MS", "Tw Cen MT", "Tw Cen MT Condensed", "Tw Cen MT Condensed Extra Bold", "University Roman LET", "Verdana", "Victorian LET", "Viner Hand ITC", "Vivaldi", "Vladimir Script", "Webdings", "Westwood LET", "Wide Latin", "Wingdings", "Wingdings 2", "Wingdings 3", "Yu Gothic", "Yu Gothic Light", "Yu Gothic Medium", "Yu Gothic UI", "Yu Gothic UI Light", "Yu Gothic UI Semibold", "Yu Gothic UI Semilight", "\u4EFF\u5B8B", "\u534E\u6587\u4E2D\u5B8B", "\u534E\u6587\u4EFF\u5B8B", "\u534E\u6587\u5B8B\u4F53", "\u534E\u6587\u5F69\u4E91", "\u534E\u6587\u65B0\u9B4F", "\u534E\u6587\u6977\u4F53", "\u534E\u6587\u7425\u73C0", "\u534E\u6587\u7EC6\u9ED1", "\u534E\u6587\u884C\u6977", "\u534E\u6587\u96B6\u4E66", "\u5B8B\u4F53", "\u5E7C\u5706", "\u5FAE\u8F6F\u96C5\u9ED1", "\u5FAE\u8F6F\u96C5\u9ED1 Light", "\u65B0\u5B8B\u4F53", "\u65B9\u6B63\u59DA\u4F53", "\u65B9\u6B63\u8212\u4F53", "\u6977\u4F53", "\u7B49\u7EBF", "\u7B49\u7EBF Light", "\u96B6\u4E66", "\u9ED1\u4F53"}));
		fontSelector.setSelectedIndex(0);
		fontSelector.setPreferredSize(new Dimension(100, 20));
		fontSelector.setMaximumSize(new Dimension(100, 20));
		fontSelector.setBounds(114, 72, 150, 20);
		frmSetText.getContentPane().add(fontSelector);
		
		JLabel lblFont = new JLabel("Font:");
		lblFont.setBounds(74, 74, 30, 15);
		frmSetText.getContentPane().add(lblFont);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				DTextModel model = new DTextModel();
				int temp=textField.getText().length();
				model.setBounds(locx-temp/2, locy-60, temp*40+20, 120);
				Color color = canvas.board.pencilcolorchoosed;
						//JColorChooser.showDialog(frmSetText, "Set Color", model.getColor());
				//while(color==null||color.equals(Color.white))
				//{
				//	JOptionPane.showMessageDialog(null, "please choose a valid color!","Error",JOptionPane.INFORMATION_MESSAGE);
				//	color = JColorChooser.showDialog(frmSetText, "Set Color", model.getColor());
				//}

				String id = canvas.addShape(model,null);
				canvas.recolorShape(color);
				canvas.setFont((String)fontSelector.getSelectedItem());
				canvas.setText(textField.getText()); 
				canvas.repaint();
				frmSetText.dispose();

//				String id = canvas.addShape ( model, null );
//				canvas.recolorShape ( color );
//				canvas.repaint ();
//                drawModel ( model, color, null );
				try {
					servercomInter.pubishAddText ( id, model, color, (String)fontSelector.getSelectedItem(), textField.getText() );
				} catch (RemoteException ex) {
					ex.printStackTrace ();
				}
			}
		});
		btnConfirm.setBounds(138, 114, 98, 23);
		frmSetText.getContentPane().add(btnConfirm);
	}
}
