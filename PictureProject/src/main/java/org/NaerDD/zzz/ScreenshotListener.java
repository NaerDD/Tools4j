package org.NaerDD.zzz;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScreenshotListener implements NativeKeyListener {

    // 固定裁剪区域
    private static final int CROP_X = 228;  // 裁剪起点X坐标
    private static final int CROP_Y = 24;  // 裁剪起点Y坐标
    private static final int CROP_WIDTH = 1464;  // 裁剪区域宽度
    private static final int CROP_HEIGHT = 1016; // 裁剪区域高度

    public static void main(String[] args) {
        // 关闭日志信息，避免控制台输出过多信息
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {
            // 注册全局键盘监听器
            GlobalScreen.registerNativeHook();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 添加键盘监听器
        GlobalScreen.addNativeKeyListener(new ScreenshotListener());
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        // 检测 F1 键是否被按下
        if (e.getKeyCode() == NativeKeyEvent.VC_F2) {
            takeScreenshot();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    private void takeScreenshot() {
        try {
            // 创建 Robot 对象进行截图
            Robot robot = new Robot();
            String desktopPath = System.getProperty("user.home") + "/Desktop/Screenshots";

            // 确保截图文件夹存在
            Files.createDirectories(Paths.get(desktopPath));

            // 截图并保存为 PNG 文件
            BufferedImage screenshot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            // 裁剪图片
            BufferedImage croppedImage = screenshot.getSubimage(CROP_X, CROP_Y, CROP_WIDTH, CROP_HEIGHT);

            // 保存裁剪后的图片
            String fileName = desktopPath + "/screenshot_" + System.currentTimeMillis() + ".png";
            ImageIO.write(croppedImage, "PNG", new File(fileName));

            System.out.println("Screenshot saved: " + fileName);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }
}
