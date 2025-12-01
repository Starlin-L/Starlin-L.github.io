package enhanced_mvc;

import javax.swing.Icon;

/**
 * Data class (POJO) to hold a single destination's data.
 */
public class TextAndIcon {
    private String text;
    private Icon icon;

    public TextAndIcon(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public Icon getIcon() {
        return icon;
    }
}