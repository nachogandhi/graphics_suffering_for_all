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
				
				 //gl.glMap2f(GL2.GL_MAP2_VERTEX_3,0.0f,1.0f,3,4,0.0f,1.0f,12,4,ctrlpoints,0);
				
				draw_surface(gl,getShapeA(),3 ,4 ,12,4 ,true);
				draw_surface(gl,getShapeB(),3 ,4 ,12,4 ,true);
				//gl.glMap2f(GL2.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, ctlarray, 0);
				//draw_surface(gl, luc(gl), 3,4,12,4, true);
				
				
				
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
	        //gl.glFrustum(-30,30,-20,20,1,1000);
	        //glu.gluLookAt(0.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
	        glu.gluLookAt(cameraCurX, cameraCurY, cameraCurZ,lookAtX, lookAtY, lookAtZ, upX, upY, upZ );
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
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
	 
	 private float[] getShapeB() {
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
	 
		 float offset = -5f;
	      // adjust z-values of the 4 "center" points
	      ctrlpoints[18-1]= ctrlpoints[21-1]=ctrlpoints[30-1]= ctrlpoints[33-1]=offset;
	      return ctrlpoints;
	      
	 }

	 private void drawControls(GL2 gl, float[] ctrlpoints, int uorder, int vorder)
	 {
	    // assuming they are offset correctly
	    // stdMaterials.setMaterial(gl, stdMaterials.MAT_BLACK_PLASTIC,GL.GL_FRONT_AND_BACK);
	   
	    int u,v;
	    for(u=0;u<uorder;u++)
	    {
	        gl.glBegin(GL.GL_LINE_STRIP);
	        for(v=0;v<4;v++)
	        {
	            gl.glVertex3f(  ctrlpoints[v*12+u*3+0],
	                            ctrlpoints[v*12+u*3+1],
	                            ctrlpoints[v*12+u*3+2]);
	        }
	        gl.glEnd();
	    }
	    for(v=0;v<vorder;v++)
	    {
	        gl.glBegin(GL.GL_LINE_STRIP);
	        for(u=0;u<uorder;u++)
	        {
	            gl.glVertex3f(  ctrlpoints[v*12+u*3+0],
	                            ctrlpoints[v*12+u*3+1],
	                            ctrlpoints[v*12+u*3+2]);
	        }
	        gl.glEnd();
	    }       
	    
	}
	 
	 
	 public void draw_surface(GL2 gl, float[] ctrlpoints,int ustride , int uorder ,int vstride , int vorder,boolean showGrid){
		 
		 //gl.glMap2f(GL2.GL_MAP2_VERTEX_3,0.0f,1.0f,3,4,0.0f,1.0f,12,4,ctrlpoints,0);
		 gl.glMap2f(GL2.GL_MAP2_VERTEX_3,0.0f,1.0f,ustride,uorder,0.0f,1.0f,vstride,vorder,ctrlpoints,0);
		 gl.glMapGrid2f(20,0.0f,1.0f,20,0.0f, 1.0f);
		 if(showGrid)
		     gl.glEvalMesh2(GL2.GL_LINE, 0, 20, 0, 20);
		 else
		     gl.glEvalMesh2(GL2.GL_FILL, 0, 20, 0, 20);
	}
	 
	 
	 private void init_camera() {
		 cameraAngleAboutY = 0.0;

			cameraDefaultX = 0.0;
			cameraDefaultY = 0.0;
			cameraDefaultZ = 5.0;

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

	 private float[] luc(GL2 gl) {
		 
	//	 Texture tex = new Texture(0); 
	//	 tex.enable(gl);
	// tex.bind(gl);
		 
		 float top = 0.75f, bottom = 0.5f, highMid = 0.125f, lowMid = 1.5f;
			float dist = 0.552284749831f;
			float[] ctlarray = new float[] {
					0f,0f,1f * bottom,
					dist * bottom,0f,1f * bottom,
					1f * bottom,0f,dist * bottom,
					1f * bottom,0f,0f,
					
					0f,0.75f,1f * lowMid,
					dist * lowMid,0.75f,1f * lowMid,
					1f * lowMid,0.75f,dist * lowMid,
					1f * lowMid,0.75f,0f,
					
					0f,1.5f,1f * highMid,
					dist * highMid,1.5f,1f * highMid,
					1f * highMid,1.5f,dist * highMid,
					1f * highMid,1.5f,0f,
					
					0f,2.25f,1f * top,
					dist * top,2.25f,1f * top,
					1f * top,2.25f,dist * top,
					1f * top,2.25f,0f
			};
			
			float[] texarray = new float[] {
					0.0f,0.0f,
					0.0f,1f,
					0.25f,1f,
					0.25f,0.0f
			};
			
			
			gl.glColor3f(1, 0, 0);
			gl.glMap2f(GL2.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, ctlarray, 0);
			//gl.glMap2f(GL2.GL_MAP2_TEXTURE_COORD_2, 0, 1, 2, 2, 0, 1, 4, 2, texarray, 0);
			
			gl.glMapGrid2f(20, 0, 1, 20, 0, 1);
			gl.glEvalMesh2(GL2.GL_FILL, 0, 20, 0, 20);
		 
			return ctlarray;
	 }

	 
	 
	
}
