package whiteboard;

public class DTextModel extends DShapeModel{
	
	private String defaultText = "Sample Text"; 
    private String defaultFont = "Comic Sans Ms"; 
     
    private String text; 
    private String font; 
    
    public DTextModel() { 
        super(); 
        text = defaultText; 
        font = defaultFont; 
    }
    
    public String getText() { 
        return text; 
    } 
    
    public String getFont() { 
        return font; 
    }
    
    public void setText(String s) { 
        text = s; 
     
    }
    
    public void setFont(String s) { 
        font = s; 
     
    } 
}
