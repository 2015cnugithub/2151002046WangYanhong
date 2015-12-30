/*
 * TextController.java
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

//该类用来控制文本视图的显示
public class TextController implements ActionListener{
    //该控制器所控制的Sphere对象
    private Sphere sphere;
    
    /** Creates a new instance of TextController */
    public TextController(Sphere sphere) {
        this.sphere=sphere;
    }
    
    public void actionPerformed(ActionEvent e) {
        JTextField t = (JTextField)e.getSource();
        double r = Double.parseDouble(t.getText());
        sphere.setRadius(r);
    }

}
