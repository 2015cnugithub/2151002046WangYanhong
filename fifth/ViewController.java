package sphere;


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


public class ViewController implements MouseMotionListener{

	private Sphere sphere;
	
	public ViewController(Sphere sphere)
	{this.sphere=sphere;}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getX());
		if(e.getX()>=0)//in case radius<0
			sphere.setRadius(e.getX());
		else
			sphere.setRadius(0);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}	
}