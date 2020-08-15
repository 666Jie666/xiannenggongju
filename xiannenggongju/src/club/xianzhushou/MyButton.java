package club.xianzhushou;

import java.awt.*;
import javax.swing.*;

public class MyButton extends JButton {

    //边框和按钮文字颜色
    private static final Color BORDER_AND_TEXT_COLOR = new Color(255, 175, 75);
    //默认背景颜色
    private static final Color DEFAULT_BACKGROUND_COLOR = new Color(51, 51, 51);
    //激活后背景颜色
    private static final Color ACTIVATED_BACKGROUND_COLOR = new Color(77, 77, 77);

    /**
     * 带文字和图片的按钮
     *
     * @param text 按钮文字
     */
    public MyButton(String text) {
        super(text);
        setFocusPainted(false);                     //去除按钮文字的线条
        setContentAreaFilled(false);                //不显示外围矩形区域
        setPreferredSize(new Dimension(150, 50));   //按钮大小
        setForeground(BORDER_AND_TEXT_COLOR);       //按钮字体颜色
        setFont(new Font("黑体", Font.BOLD, 20));    //按钮字体样式
    }

    /**
     * 获取添加图片的按钮
     *
     * @param path 图片路径
     */
    public MyButton getButtonWithImg(String path) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(path));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(16, 32, Image.SCALE_DEFAULT));
        this.setIcon(imageIcon);
        return this;
    }

    public void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            //点击时按钮颜色
            g.setColor(ACTIVATED_BACKGROUND_COLOR);
        } else {
            //松开时按钮颜色
            g.setColor(DEFAULT_BACKGROUND_COLOR);
        }
        //填充圆角矩形区域 也可以为其它的图形
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
        //必须放在最后 否则画不出来
        super.paintComponent(g);
    }

    public void paintBorder(Graphics g) {
        //画边界区域
        g.setColor(BORDER_AND_TEXT_COLOR);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
    }

}