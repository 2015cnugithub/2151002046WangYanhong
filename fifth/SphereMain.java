/*
 * SphereMain.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author cnu
 */
package sphere;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 *
 * @author  Administrator
 */
public class SphereMain extends JFrame {
    
    /** Creates a new instance of SphereMain */
    public SphereMain() {
        super("mvc演示程序");
        setSize(400,200);
        Sphere sphere=new Sphere(50);//建立模型对象
        TextView textView=new TextView();//建立文本视图
        GraphicsView graphicsView=new GraphicsView();//建立图形视图
        //添加监听器
        textView.getTextField().addActionListener(new TextController(sphere));
		graphicsView.addMouseMotionListener(new ViewController(sphere));
        //模型与视图相连
        sphere.addObserver(textView);
        sphere.addObserver(graphicsView);
        //创建主界面
        Container c=getContentPane();
        c.setLayout(new GridLayout(1,2));
        c.add(textView);
        c.add(graphicsView);
        textView.update(sphere, null);
        graphicsView.update(sphere,null);
    }
    
    /**
     * @param args the command line arguments
     */
	 @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        // TODO code application logic here
        new SphereMain().show();
    }
    
}
