package A3;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.texture.Texture;

public class HelloBezier implements GLEventListener{
	
	GLU glu;
	
	   public double cameraAngleAboutY;
	    public double cameraCurX, cameraCurY, cameraCurZ;
	    public double cameraDefaultX, cameraDefaultY, cameraDefaultZ;
	    public double lookAtX, lookAtY, lookAtZ;
	    public double upX, upY, upZ;

	    public int prevMouseX, prevMouseY;

		@Override
		public void display(GLAutoDrawable drawable) {
				GL2 gl = drawable.getGL().getGL2();
				gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
				gl.glMatrixMode(GL2.GL_MODELVIEW);
				gl.glLoadIdentity();
				glu.gluLookAt(cameraCurX, cameraCurY, cameraCurZ,lookAtX, lookAtY, lookAtZ, upX, upY, upZ );
				init_lines(gl);
				
				
			     BasicExample(gl);
			     
			     
		
		
			}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		
		//gl.glEnable(GL.GL_TEXTURE_2D);
	//	gl.glShadeModel(GL2.GL_SMOOTH);
		
		gl.glClearColor(1, 1, 1f, 0f);
		gl.glClearDepth(1.0f);
		 
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_MAP2_VERTEX_3);
		gl.glEnable(GL2.GL_AUTO_NORMAL);
		gl.glEnable(GL2.GL_NORMALIZE);
		    
		init_camera();
				
	}

	 public void reshape(GLAutoDrawable drawable, int x, int y,int width, int height) {
	        GL2 gl = drawable.getGL().getGL2();
	
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	 
	        glu.gluPerspective(60, (float) width / height, 1, 1000);
	        glu.gluLookAt(cameraCurX, cameraCurY, cameraCurZ,lookAtX, lookAtY, lookAtZ, upX, upY, upZ );
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	      
	        
	    }
	
	 private void BasicExample(GL2 gl) {
		 //This example draws the control point lines as well as the surface
		 //It chooses to show the grid (false for fill)
		 
		 drawControls(gl,getShapeA(),4,4);
		 draw_surface(gl,getShapeA(), 4,4,false);
		 
	 }
	 
	 
	 private void drawControls(GL2 gl, float[] ctrlpoints, int uorder, int vorder)
	 {
	  
	    int u,v;
	    for(u=0;u<uorder;u++)
	    {
	        gl.glBegin(GL.GL_LINE_STRIP);
	        for(v=0;v<vorder;v++)
	        {
	            gl.glVertex3f(  ctrlpoints[v*3*uorder+u*3+0],
	                            ctrlpoints[v*3*uorder+u*3+1],
	                            ctrlpoints[v*3*uorder+u*3+2]);
	        }
	        gl.glEnd();
	    }
	    for(v=0;v<vorder;v++)
	    {
	        gl.glBegin(GL.GL_LINE_STRIP);
	        for(u=0;u<uorder;u++)
	        {
	            gl.glVertex3f(  ctrlpoints[v*3*uorder+u*3+0],
	                            ctrlpoints[v*3*uorder+u*3+1],
	                            ctrlpoints[v*3*uorder+u*3+2]);
	        }
	        gl.glEnd();
	    }       
	    
	}
	  private float[] getShapeA() {
		 // ctrlpoints[4][4][3]
		 
		 float [] ctrlpoints= new float[]// [v][u][xyz] [4][4][3]
	        {
	                -1.5f,-1.5f,0.0f, -0.5f,-1.5f,0.0f,
	                0.5f,-1.5f,0.0f,  1.5f,-1.5f,0.0f
	          ,
	                -1.5f,-0.5f,0.0f, -0.5f,-0.5f,0.0f,
	                0.5f,-0.5f,0.0f,  1.5f,-0.5f,0.0f
	          ,
	                -1.5f,0.5f,0.0f,  -0.5f,0.5f,0.0f,
	                0.5f,0.5f, 0.0f,  1.5f,0.5f,0.0f
	          ,
	                -1.5f,1.5f,0.0f,  -0.5f,1.5f,0.0f,
	                0.5f,1.5f,0.0f,   1.5f,1.5f,0.0f
	        }; 
	 
		 float offset = 5f;
	      // adjust z-values of the 4 "center" points
	      ctrlpoints[18-1]= ctrlpoints[21-1]=ctrlpoints[30-1]= ctrlpoints[33-1]=offset;
	      return ctrlpoints;
	      
	 }
	 
	
	 
	 public void draw_surface(GL2 gl, float[] ctrlpoints, int uorder, int vorder, boolean showGrid){
		 
		 gl.glMap2f(GL2.GL_MAP2_VERTEX_3,0.0f,1.0f,3,uorder,0.0f,1.0f,3*uorder,vorder,ctrlpoints,0);
		 gl.glMapGrid2f(20,0.0f,1.0f,20,0.0f, 1.0f);
		 if(showGrid)
		     gl.glEvalMesh2(GL2.GL_LINE, 0, 20, 0, 20);
		 else
		     gl.glEvalMesh2(GL2.GL_FILL, 0, 20, 0, 20);

	}
	 
	
	

	private void draw_points(GL2 gl, float[] points){
		
		gl.glPointSize(4);
		gl.glBegin(GL.GL_POINTS);
		for( int i = 0; i < points.length - 2; i += 3) {
			gl.glVertex3f(points[i], points[i+1], points[i+2]);
			//System.out.println("Hello: " + i + " " + i+1 + " " + i +2 + "");
		}
		gl.glEnd();
		
	 }
	
	
	//CAMERA FUNCIONALITY PAST HERE 
	
	 private void init_camera() {
		 cameraAngleAboutY = 0.0;

			cameraDefaultX = 0.0;
			cameraDefaultY = 3.0;
			cameraDefaultZ = 10.0;

			cameraCurX = cameraDefaultX;
			cameraCurY = cameraDefaultY;
			cameraCurZ = cameraDefaultZ;

			lookAtX = 0.0;
			lookAtY = 0.0;
			lookAtZ = 0.0;

			upX = 0.0;
			upY = 1.0;
			upZ = 0.0;
	 }
	 
	 private void init_lines(GL2 gl) {
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glColor3f(1,0,0);
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(100, 0, 0);
			gl.glEnd();
			
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glColor3f(0,1,0);
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(0, 100, 0);
			gl.glEnd();
			
			gl.glBegin(GL.GL_LINE_STRIP);
			gl.glColor3f(0,0,1);
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(0, 0, 100);
			gl.glEnd();
			
		 
	 }

	 
	
}
