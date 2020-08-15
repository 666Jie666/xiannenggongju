package club.xianzhushou;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {

    //面板背景色
    private static final Color BACKGROUND_COLOR = new Color(26, 26, 26);

    /**
     * @param layoutManager 布局方式
     * @param components    组件
     */
    public MyPanel(LayoutManager layoutManager, Component... components) {
        super(layoutManager);
        setBackground(BACKGROUND_COLOR);
        add(components);
    }

    private void add(Component[] components) {
        for (Component component : components) {
            add(component);
        }
    }

}
