import javax.swing.*;

public class RowPanel extends JPanel {
    private int x, y;
    private int horizontal, vertical;
    public RowPanel() {
        setLayout(null);
    }
    public void setSpacing(int horizontal, int vertical) {
        this.x = this.horizontal = horizontal;
        this.y = this.vertical = vertical;
    }
    public void addRow(JComponent... components) {
        int maisAlto = 0;
        for (JComponent component : components) {
            if (component.getSize().height > maisAlto) {
                maisAlto = component.getSize().height;
            }
        }
        for (JComponent component : components) {
            int h = component.getSize().height;
            component.setLocation(x, y + (maisAlto - h) / 2);
            add(component);
            x += component.getSize().width + horizontal;
        }
        y += maisAlto + vertical;
        x = horizontal;
    }
}
