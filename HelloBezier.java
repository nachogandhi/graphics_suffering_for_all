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

	
	 private static class Point3D { // Structure for a 3-dimensional point (NEW)
	        public double x, y, z;
	 
	        public Point3D() {	        }
	 
	        public Point3D(double x, double y, double z) {
	            this.x = x;
	            this.y = y;
	            this.z = z;
	        }
	 
	        public Point3D add(Point3D q) {
	            return new Point3D(x + q.x, y + q.y, z + q.z);
	        }
	 
	        public Point3D scale(double c) {
	            return new Point3D(x * c, y * c, z * c);
	        }
	 
	        /**
	         * Calculates 3rd degree polynomial based on array of 4 points
	         * and a single variable (u) which is generally between 0 and 1
	         */
	        public static Point3D bernstein(float u, Point3D[] p) {
	            Point3D a = p[0].scale(Math.pow(u, 3));
	            Point3D b = p[1].scale(3 * Math.pow(u, 2) * (1 - u));
	            Point3D c = p[2].scale(3 * u * Math.pow((1 - u), 2));
	            Point3D d = p[3].scale(Math.pow((1 - u), 3));
	 
	            return a.add(b).add(c).add(d);
	        }
	    }

		@Override
		public void display(GLAutoDrawable drawable) {
				GL2 gl = drawable.getGL().getGL2();
				gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
				gl.glMatrixMode(GL2.GL_MODELVIEW);
				gl.glLoadIdentity();
				
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
				gl.glVertex3f(0, 0, 100);
				gl.glEnd();
				
				//luc(gl);
				//still_desperate(gl);
			
				drawMe(gl,drawControls(gl), false);
				
				
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
				
	}

	 public void reshape(GLAutoDrawable drawable, int x, int y,int width, int height) {
	        GL2 gl = drawable.getGL().getGL2();
	
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	 
	        glu.gluPerspective(60, (float) width / height, 1, 1000);
	        //gl.glFrustum(-30,30,-20,20,1,1000);
	        glu.gluLookAt(0.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f);
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	    }
	
	 private void Draw_Sphere(GL2 gl) {
		 
		 float cube_ctrl_points[] = new float[] {
				 0, 0, 0,
				 0,0,1,
				 0,1,1,
				 0,1,0,
				1,0,0, 
				1,0,1, 
				1,1,1,
				1,1,0	 	 
		 };
		/* cube_ctrl_points[0][0] = new Point3D(0,0,0);
		 cube_ctrl_points[0][1] = new Point3D(0,0,1);
		 cube_ctrl_points[0][2] = new Point3D(0,1,1);
		 cube_ctrl_points[0][3] = new Point3D(0,1,0);
		 
		 cube_ctrl_points[1][0] = new Point3D(1,0,0);
		 cube_ctrl_points[1][1] = new Point3D(1,0,1);
		 cube_ctrl_points[1][2] = new Point3D(1,1,1);
		 cube_ctrl_points[1][3] = new Point3D(1,1,0);*/

				 
		 gl.glMap2f(GL2.GL_MAP2_VERTEX_3,0,1,3,4,0,1,12,4,cube_ctrl_points, 0);
		 
		 
	 }

	 private void luc(GL2 gl) {
		 
		 Texture tex = new Texture(0); 
		 
		 tex.enable(gl);
			tex.bind(gl);
		 
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
			gl.glMap2f(GL2.GL_MAP2_TEXTURE_COORD_2, 0, 1, 2, 2, 0, 1, 4, 2, texarray, 0);
			
			gl.glMapGrid2f(20, 0, 1, 20, 0, 1);
			gl.glEvalMesh2(GL2.GL_FILL, 0, 20, 0, 20);
		 
		 
	 }


	 /*private void offsetControls(float offset)
	    {
	      // adjust z-values of the 4 "center" points
	      ctrlpoints[18-1]= ctrlpoints[21-1]=
	      ctrlpoints[30-1]= ctrlpoints[33-1]=offset;
	    }*/
	 private float[] drawControls(GL2 gl)
	 {
	    // assuming they are offset correctly
	    // stdMaterials.setMaterial(gl, stdMaterials.MAT_BLACK_PLASTIC,GL.GL_FRONT_AND_BACK);
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
	 
	    int u,v;
	    for(u=0;u<4;u++)
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
	    for(v=0;v<4;v++)
	    {
	        gl.glBegin(GL.GL_LINE_STRIP);
	        for(u=0;u<4;u++)
	        {
	            gl.glVertex3f(  ctrlpoints[v*12+u*3+0],
	                            ctrlpoints[v*12+u*3+1],
	                            ctrlpoints[v*12+u*3+2]);
	        }
	        gl.glEnd();
	    }       
	    
	    float offset = 5f;
	      // adjust z-values of the 4 "center" points
	      ctrlpoints[18-1]= ctrlpoints[21-1]=ctrlpoints[30-1]= ctrlpoints[33-1]=offset;
	   
	    return ctrlpoints;
	}
	 
	 public void drawMe(GL2 gl, float[] ctrlpoints, boolean showGrid)
	{
	//offsetControls(offset);
	
	 //stdMaterials.setMaterial(gl, stdMaterials.MAT_RED_PLASTIC, GL.GL_FRONT);
	 //stdMaterials.setMaterial(gl, stdMaterials.MAT_GREEN_PLASTIC, GL.GL_BACK);
	 gl.glMap2f(GL2.GL_MAP2_VERTEX_3,0.0f,1.0f,3,4,0.0f,1.0f,12,4,ctrlpoints,0);
	 gl.glEnable(GL2.GL_MAP2_VERTEX_3);
//	 gl.glEnable(GL2.GL_AUTO_NORMAL);
	// gl.glEnable(GL2.GL_NORMALIZE);
	 gl.glMapGrid2f(20,0.0f,1.0f,20,0.0f, 1.0f);
	 //gl.glFrontFace(GL.GL_CW);
	 if(showGrid)
	     gl.glEvalMesh2(GL2.GL_LINE, 0, 20, 0, 20);
	 else
	     gl.glEvalMesh2(GL2.GL_FILL, 0, 20, 0, 20);
	 // gl.glFrontFace(GL.GL_CCW);
	}
	
}
