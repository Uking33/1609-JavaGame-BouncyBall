/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.*;

/**
 *
 * @author Administrator
 */
public class drawBall extends Panel implements Runnable, KeyListener {

    private int x;
    private int y;
    private int vx;
    private int vy;
    private int diameter;
    private int width;
    private int heigth;
    private Image im;
    private Graphics dbg;
    private Thread gamethread;
    private static final int FPS = 50;
    private boolean running = true;
    private boolean isPaused = false;

    public drawBall() {
        x = 250;
        y = 250;
        Random rand1 = new Random();
        vx = rand1.nextInt(5)+1;
        Random rand2 = new Random();
        vy = rand2.nextInt(5)+1;
        diameter = 100;
        width = 500;
        heigth = 500;
        setBackground(Color.pink);
        setPreferredSize(new Dimension(width, heigth));
        this.addKeyListener(this);

    }

    public void gameStart() {
        gamethread = new Thread(this);
        gamethread.start();
    }

    public void gamePaint() {
        Graphics g;
        try {
            g = this.getGraphics();
            if (g != null && im != null) {
                g.drawImage(im, 0, 0, null);
            }
            g.dispose();
        } catch (Exception e) {
        }
    }

    public void gameRender() {
        if (im == null) {
            im = createImage(width, heigth);
            if (im == null) {
                System.out.println("im is null");
            } else {
                dbg = im.getGraphics();
            }
        }
        dbg.setColor(Color.pink);
        dbg.fillRect(0, 0, width, heigth);
        dbg.setColor(Color.blue);
        dbg.fillOval(x, y, diameter, diameter);
        dbg.setColor(Color.yellow);
        dbg.drawString("UKing", x+diameter/3, y+diameter/2);
    }

    public void gameUpdate() {
        if (x+diameter >= width) {
            vx = -vx;
        }
        if (y+diameter >= heigth) {
        	vy = -vy;
        }
        if (x <= 0) {
        	vx = -vx;
        }
        if (y <= 0) {
        	vy = -vy;
        }
        x += vx;
        y += vy;
    }

    public void run() {
        long t1,t2,dt,sleepTime;  
        long period=1000/FPS;  //计算每一次循环需要的执行时间，单位毫秒
        t1=System.nanoTime();  //保存游戏循环执行前的系统时间，单位纳秒
          
        while (running) {
            if (!isPaused) {
                gameUpdate();
            }
           gameRender();
           gamePaint();
           t2= System.nanoTime() ; //游戏循环执行后的系统时间，单位纳秒
           dt=(t2-t1)/1000000L;  //本次循环实际花费的时间，并转换为毫秒
           sleepTime = period - dt;//计算本次循环剩余的时间，单位毫秒
           if(sleepTime<=0)        //防止sleepTime值为负数
                 sleepTime=2;
           try {     
           Thread.sleep(sleepTime); //让线程休眠，由sleepTime值决定
          } catch (InterruptedException ex) { }
             t1 = System.nanoTime();  //重新获取当前系统时间
        }
    }


    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            running = false;
            System.out.println("key is ESC" );
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            isPaused = !isPaused;
            if(!isPaused){
                Random rand1 = new Random();
                vx = rand1.nextInt(5)+1;
                Random rand2 = new Random();
                vy = rand2.nextInt(5)+1;
            }
            System.out.println("key is P" );
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}

