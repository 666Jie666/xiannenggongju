package club.xianzhushou;

import javax.swing.*;
import java.awt.*;

public class MyProgressBar extends JProgressBar {

    //进度条前景色
    private static final Color FOREGROUND_COLOR = new Color(255, 175, 75);
    //进度条背景色
    private static final Color BACKGROUND_COLOR = new Color(26, 26, 26);

    static {
        //覆盖前的进度条字体颜色
        UIManager.put("ProgressBar.selectionBackground", FOREGROUND_COLOR);
        //覆盖后的进度条字体颜色
        UIManager.put("ProgressBar.selectionForeground", BACKGROUND_COLOR);
    }

    public MyProgressBar() {
        super();
        setOrientation(JProgressBar.HORIZONTAL);    //将进度条的方向设置为水平
        setMinimum(0);                              //进度条最小值
        setMaximum(100);                            //进度条最大值
        setStringPainted(true);                     //可显示进度条内字符串
        setBackground(BACKGROUND_COLOR);            //进度条背景色
        setForeground(FOREGROUND_COLOR);            //进度条前景色
        setPreferredSize(new Dimension(340, 25));   //进度条宽高
        setBorderPainted(false);                    //去除默认边框
        setFont(new Font("黑体", Font.BOLD, 16));    //进度条内字体样式
        setValue(0);                                //进度条进度
        setString("请选择修复模式");                   //进度条内文字
    }

    public void paintBorder(Graphics g) {
        //画边界区域
        g.setColor(FOREGROUND_COLOR);
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 0, 0);
    }

}
