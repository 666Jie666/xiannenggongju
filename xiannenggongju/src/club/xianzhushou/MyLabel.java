package club.xianzhushou;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Desktop;

import javax.swing.*;

public class MyLabel extends JLabel {

    //文字颜色
    private static final Color TEXT_COLOR = new Color(255, 175, 75);

    public MyLabel() {
        this("");
    }

    public MyLabel(String text) {
        super(text);
        setForeground(TEXT_COLOR);                  //字体颜色
        setFont(new Font("黑体", Font.BOLD, 16));    //字体样式
    }

    /**
     * 点击跳转到网站
     */
    public MyLabel jumpToWebsite() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.xianzhushou.club/"));
                } catch (IOException | URISyntaxException exception) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
        });
        return this;
    }

    /**
     * 获取添加图片的Label
     *
     * @param path 图片路径
     */
    public MyLabel getLabelWithImg(String path, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(path));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);
        return this;
    }

}
