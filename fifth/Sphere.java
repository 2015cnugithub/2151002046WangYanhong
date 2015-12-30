/*
 * Sphere.java
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
import java.util.Observable;
public class Sphere extends Observable {
    private double radius;
    /** Creates a new instance of Sphere */
    public Sphere(int radius) {
        this.radius=radius;
    }
    /**
     * 属性 radius 的获取方法。
     * @return 属性 radius 的值。
     */
    public double getRadius() {
        return radius;
    }
    /**
     * 属性 radius 的设置方法。
     * @param radius 属性 radius 的新值。
     */
    public void setRadius(double radius) {
        this.radius=radius;
        this.setChanged();
        this.notifyObservers();
    }
    
    //计算球的体积
    public double volume() {
        return (double)4/(double)3*Math.PI*radius*radius*radius;
    }
    
    //计算球的表面积
    public double surfaceArea() {
        return 4*Math.PI*radius*radius;
    }
    
}
