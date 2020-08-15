package club.xianzhushou;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 修复控制器
 */
public class RepairController extends Thread {

    //程序框
    private static MyFrame myFrame;
    //普通修复按钮
    private static MyButton generalRepairButton;
    //强力修复按钮
    private static MyButton strongRepairButton;
    //进度条
    private static MyProgressBar myProgressBar;
    //修复模式（0：普通修复   1：强力修复）
    private static int repairMode;
    //已点击标记
    private static boolean clickedFlag;
    //普通修复命令
    private static final String GENERAL_REPAIR = "sfc /SCANNOW";
    //强力修复命令
    private static final String STRONG_REPAIR_SCAN = "DISM.exe /Online /Cleanup-image /Scanhealth";
    private static final String STRONG_REPAIR_RESTORE = "DISM.exe /Online /Cleanup-image /Restorehealth";
    //立即重启命令
    private static final String RESTART_NOW = "shutdown -r -t 0";

    private RepairController() {
    }

    /**
     * 修复
     */
    public static void repair(MyFrame myFrame, MyButton generalRepairButton, MyButton strongRepairButton, MyProgressBar myProgressBar) {
        RepairController.myFrame = myFrame;
        RepairController.generalRepairButton = generalRepairButton;
        RepairController.strongRepairButton = strongRepairButton;
        RepairController.myProgressBar = myProgressBar;
        RepairController.clickedFlag = false;
        //普通修复按钮点击监听
        generalRepairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!RepairController.clickedFlag) {
                    RepairController.repairMode = 0;
                    RepairController.clickedFlag = true;
                    new RepairController().start();
                }
            }
        });
        //强力修复按钮点击监听
        strongRepairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!RepairController.clickedFlag) {
                    RepairController.repairMode = 1;
                    RepairController.clickedFlag = true;
                    new RepairController().start();
                }
            }
        });
    }

    @Override
    public void run() {
        try {
            myProgressBar.setIndeterminate(true);
            if (repairMode == 0) {
                //普通修复
                myProgressBar.setString("普通修复(正在扫描系统文件)");
                execute(GENERAL_REPAIR, false);
                myProgressBar.setIndeterminate(false);
                setProgress(0, "普通修复(已完成0%)");
                bogusProgress();
            } else {
                //强力修复
                myProgressBar.setString("强力修复(正在扫描系统文件)");
                execute(STRONG_REPAIR_SCAN, false);
                myProgressBar.setIndeterminate(false);
                setProgress(0, "强力修复(已完成0%)");
                execute(STRONG_REPAIR_RESTORE, true);
            }
            clickedFlag = false;
            showDialog();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 伪造的进度
     */
    private void bogusProgress() {
        try {
            Random random = new Random();
            int randomNum = 0;
            for (int i = 0; i < 100; i += 5) {
                randomNum = random.nextInt(6);
                Thread.sleep(1000 * randomNum);
                setProgress(i + randomNum, "普通修复(已完成" + (i + randomNum) + "%)");
            }
            setProgress(100, "普通修复(已完成100%)");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void execute(String command, boolean showStatus) {
        BufferedReader bufferedReader = null;
        try {
            Process process = Runtime.getRuntime().exec(command);
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            String progress = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (showStatus) {
                    progress = getProgress(line);
                    if (progress != null) {
                        setProgress(Integer.parseInt(progress), "强力修复(已完成" + progress + "%)");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取进度
     */
    private String getProgress(String line) {
        if (line.contains("%")) {
            int length = line.length();
            String firstChar = "";
            String secondChar = "";
            for (int i = 0; i < length; i++) {
                if ((firstChar = line.charAt(i) + "").matches("[0-9]")) {
                    if (i + 1 < length) {
                        secondChar = line.charAt(i + 1) + "";
                        if (i + 2 < length && (firstChar + secondChar + line.charAt(i + 2)).matches("[0-9]+")) {
                            //到达100%
                            return "100";
                        }
                        if (secondChar.matches("[0-9]")) {
                            //两位数
                            return firstChar + secondChar;
                        }
                    }
                    return firstChar;
                }
            }
        }
        return null;
    }

    /**
     * 设置进度
     */
    private void setProgress(int value, String text) {
        myProgressBar.setValue(value);
        myProgressBar.setString(text);
    }

    private void showDialog() {
        JDialog jDialog = new JDialog(myFrame, "通知");
        jDialog.setSize(350, 166);
        jDialog.setResizable(false);                                //窗口禁止调整大小
        jDialog.setLocationRelativeTo(null);                        //窗口显示在屏幕中间
        MyLabel myLabel = initMyLabel();
        MyButton restartNowButton = initMyButton("立即重启");
        MyButton restartLaterButton = initMyButton("稍后重启");
        restart(restartNowButton, restartLaterButton, jDialog);     //重启按钮监听
        MyPanel myPanel = initMyPanel(myLabel, restartNowButton, restartLaterButton);
        jDialog.setContentPane(myPanel);
        jDialog.setVisible(true);
    }

    private MyLabel initMyLabel() {
        return new MyLabel("修复完成，需要重启您的计算机。").getLabelWithImg("/images/success.png", 30, 30);
    }

    private MyButton initMyButton(String text) {
        MyButton myButton = new MyButton(text);
        myButton.setPreferredSize(new Dimension(102, 34));   //按钮大小
        myButton.setFont(new Font("黑体", Font.BOLD, 15));    //按钮字体样式
        return myButton;
    }

    private MyPanel initMyPanel(Component... components) {
        return new MyPanel(new FlowLayout(FlowLayout.CENTER, 20, 20), components);
    }

    /**
     * 重启
     */
    private void restart(MyButton restartNowButton, MyButton restartLaterButton, JDialog jDialog) {
        //立即重启按钮监听
        restartNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec(RESTART_NOW);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        //稍后重启按钮监听
        restartLaterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
    }

}