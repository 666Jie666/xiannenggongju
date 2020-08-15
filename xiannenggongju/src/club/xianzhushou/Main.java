package club.xianzhushou;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame("闲能工具").getFrameWithImg("/images/logo.png");
        //普通修复按钮和强力修复按钮
        MyButton generalRepairButton = new MyButton("普通修复").getButtonWithImg("/images/green_lightning.png");
        MyButton strongRepairButton = new MyButton("强力修复").getButtonWithImg("/images/red_lightning.png");
        //进度条
        MyProgressBar myProgressBar = new MyProgressBar();
        //按钮和进度条区域面板
        MyPanel buttonAndProgressBarPanel = new MyPanel(new FlowLayout(FlowLayout.CENTER, 40, 40),
                generalRepairButton, strongRepairButton, myProgressBar);
        //底部信息
        MyLabel line = new MyLabel("_____________________          _____________________");
        MyLabel from = new MyLabel("出自：闲助手(点击图标访问网站)");
        MyLabel brand = new MyLabel().getLabelWithImg("/images/brand.png", 150, 50).jumpToWebsite();
        MyLabel developer = new MyLabel("开发者：Terminator");
        MyLabel version = new MyLabel("版本号：1.0.0");
        //文字区域面板
        JPanel labelPanel = new MyPanel(new FlowLayout(FlowLayout.CENTER, 25, 11), line, from, brand, developer, version);
        //修复
        RepairController.repair(myFrame, generalRepairButton, strongRepairButton, myProgressBar);
        myFrame.add(buttonAndProgressBarPanel);
        myFrame.add(labelPanel);
        myFrame.setVisible(true);
    }

}
