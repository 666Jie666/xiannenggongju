package club.xianzhushou;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public MyFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setResizable(false);                                //窗口禁止调整大小
        setLocationRelativeTo(null);                        //窗口显示在屏幕中间
        setLayout(new GridLayout(2, 1));
    }

    /**
     * 获取添加图标的Frame
     *
     * @param path 图片路径
     */
    public MyFrame getFrameWithImg(String path) {
        Image image = Toolkit.getDefaultToolkit().createImage(MyFrame.class.getResource(path));
        this.setIconImage(image);
        return this;
    }

}
