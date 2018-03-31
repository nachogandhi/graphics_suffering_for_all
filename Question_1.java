package A3;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Question_1 implements GLEventListener{

	GLU glu;
	   private BezierPatch mybezier = new BezierPatch(); 
	   private boolean showCPoints = true;  
	   private int divs = 7;   
	
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
		
		drawGLScene(gl);
		
		
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		glu = new GLU();
		
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0.05f, 0.05f, 0.05f, 0.5f);
		 gl.glClearDepth(1.0f);
		 
		 gl.glEnable(GL.GL_DEPTH_TEST);  
		 gl.glDepthFunc(GL.GL_LEQUAL);  
		  gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST); 
		 
		  int[] texture = new int[1];
	        gl.glGenTextures(1, texture, 0);
			//	gl.glClearColor(1f, 1f, 1f, 1.0f);
	        mybezier.setTexture(texture[0]);
	        initBezier(mybezier);
				
	}

/*	@Override
	public void	reshape(GLAutoDrawable drawable, int x, int y, int width, int height){
		
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0,0, width, height); //Area which will be drawn on (size)
		
		gl.glMatrixMode(GL2.GL_PROJECTION); //Sets/switches current coordinate system 
		gl.glLoadIdentity(); //Set stored matrix to identity matrix
		glu.gluPerspective(60.0f, (float) width / height, 1.0f, 1000.0f);
		// this shows two dimensional space
		glu.gluLookAt(0.0f, 0.0f, 20.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f)	
	}*/
	
	 
    public void reshape(GLAutoDrawable drawable, int x, int y,int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
 
        height = (height == 0) ? 1 : height;
 
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
 
        glu.gluPerspective(45, (float) width / height, 1, 1000);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    
    private void initBezier(BezierPatch bezier) {
        bezier.anchors[0][0] = new Point3D(-0.75, -0.75, -0.5);  // set the bezier vertices
        bezier.anchors[0][1] = new Point3D(-0.25, -0.75, 0.0);
        bezier.anchors[0][2] = new Point3D(0.25, -0.75, 0.0);
        bezier.anchors[0][3] = new Point3D(0.75, -0.75, -0.5);
        bezier.anchors[1][0] = new Point3D(-0.75, -0.25, -0.75);
        bezier.anchors[1][1] = new Point3D(-0.25, -0.25, 0.5);
        bezier.anchors[1][2] = new Point3D(0.25, -0.25, 0.5);
        bezier.anchors[1][3] = new Point3D(0.75, -0.25, -0.75);
        bezier.anchors[2][0] = new Point3D(-0.75, 0.25, 0.0);
        bezier.anchors[2][1] = new Point3D(-0.25, 0.25, -0.5);
        bezier.anchors[2][2] = new Point3D(0.25, 0.25, -0.5);
        bezier.anchors[2][3] = new Point3D(0.75, 0.25, 0.0);
        bezier.anchors[3][0] = new Point3D(-0.75, 0.75, -0.5);
        bezier.anchors[3][1] = new Point3D(-0.25, 0.75, -1.0);
        bezier.anchors[3][2] = new Point3D(0.25, 0.75, -1.0);
        bezier.anchors[3][3] = new Point3D(0.75, 0.75, -0.5);
        bezier.dlBPatch = 0;
    }
    
    private void drawGLScene(GL2 gl) {
        // Clear Screen And Depth Buffer
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);  
        gl.glLoadIdentity();    // Reset The Current Modelview Matrix
         
        // Move Left 1.5 Units And Into The Screen 6.0
        gl.glTranslatef(0.0f, 0.0f, -4.0f);  
        gl.glRotatef(-75.0f, 1.0f, 0.0f, 0.0f);
         
        // Rotate The Triangle On The Z axis ( NEW )
      //  gl.glRotatef(rotz, 0.0f, 0.0f, 1.0f);  
 
        mybezier.drawBezier(gl, divs);
 
        if (showCPoints) {      // If drawing the grid is toggled on
            gl.glDisable(GL.GL_TEXTURE_2D);
            gl.glColor3f(1.0f, 0.0f, 0.0f);
            for (int i = 0; i < 4; i++) {  // draw the horizontal lines
                gl.glBegin(GL.GL_LINE_STRIP);
                for (int j = 0; j < 4; j++)
                    gl.glVertex3d(mybezier.anchors[i][j].x, mybezier.anchors[i][j].y, 
                            mybezier.anchors[i][j].z);
                gl.glEnd();
            }
            for (int i = 0; i < 4; i++) {  // draw the vertical lines
                gl.glBegin(GL.GL_LINE_STRIP);
                for (int j = 0; j < 4; j++)
                    gl.glVertex3d(mybezier.anchors[j][i].x, mybezier.anchors[j][i].y, 
                            mybezier.anchors[j][i].z);
                gl.glEnd();
            }
            gl.glColor3f(1.0f, 1.0f, 1.0f);
            gl.glEnable(GL.GL_TEXTURE_2D);
        }
    }
 
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
	 private static class BezierPatch {  // Structure for a 3rd degree bezier patch (NEW)
	        public Point3D[][] anchors;  // 4x4 grid of anchor points
	        private int dlBPatch;    // Display List for Bezier Patch
	        private int[] texture = new int[1];  // Texture for the patch
	        private int divs = -1;
	 
	        public BezierPatch() {
	            anchors = new Point3D[4][4];
	            for (int i = 0; i < anchors.length; i++) {
	                Point3D[] anchor = anchors[i];
	                for (int j = 0; j < anchor.length; j++) {
	                    anchor[j] = new Point3D();
	                }
	            }
	        }
	 
	        public void setTexture(int texture) {
	            this.texture[0] = texture;
	        }
	 
	        public void drawBezier(GL2 gl, int divs) {
	            if (divs != this.divs)
	                dlBPatch = genBezier(gl, divs);
	            gl.glCallList(dlBPatch);  // Call the Bezier's display list
	        }
	 
	        /**
	         * Generates a display list based on the data in the patch
	         * and the number of divisions
	         */
	        private int genBezier(GL2 gl, int divs) {
	            float py, px, pyold;
	            int drawlist = gl.glGenLists(1);  // make the display list
	            Point3D[] temp = new Point3D[4];
	            Point3D[] last = new Point3D[divs + 1];
	            // array of points to mark the first line of polys
	 
	            if (dlBPatch != 0)  // get rid of any old display lists
	                gl.glDeleteLists(dlBPatch, 1);
	 
	            temp[0] = anchors[0][3];  // the first derived curve (along x axis)
	            temp[1] = anchors[1][3];
	            temp[2] = anchors[2][3];
	            temp[3] = anchors[3][3];
	 
	            for (int v = 0; v <= divs; v++) {  // create the first line of points
	                px = v / ((float) divs);  // percent along y axis
	                // use the 4 points from the derives curve to calculate the points 
	                // along that curve
	                last[v] = Point3D.bernstein(px, temp);
	            }
	 
	            gl.glNewList(drawlist, GL2.GL_COMPILE);  // Start a new display list
	            gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);  // Bind the texture
	 
	            for (int u = 1; u <= divs; u++) {
	                py = ((float) u) / ((float) divs);    // Percent along Y axis
	                pyold = ((float) u - 1.0f) / ((float) divs); // Percent along old Y axis
	 
	                temp[0] = Point3D.bernstein(py, anchors[0]); // Calculate new bezier points
	                temp[1] = Point3D.bernstein(py, anchors[1]);
	                temp[2] = Point3D.bernstein(py, anchors[2]);
	                temp[3] = Point3D.bernstein(py, anchors[3]);
	 
	                gl.glBegin(GL.GL_TRIANGLE_STRIP); // Begin a new triangle strip
	 
	                for (int v = 0; v <= divs; v++) {
	                    px = ((float) v) / ((float) divs);  // Percent along the X axis
	 
	                    gl.glTexCoord2f(pyold, px);  // Apply the old texture coords
	                    gl.glVertex3d(last[v].x, last[v].y, last[v].z); // Old Point
	 
	                    last[v] = Point3D.bernstein(px, temp); // Generate new point
	                    gl.glTexCoord2f(py, px);   // Apply the new texture coords
	                    gl.glVertex3d(last[v].x, last[v].y, last[v].z);  // New Point
	                }
	 
	                gl.glEnd();  // END the triangle srip
	            }
	 
	            gl.glEndList();  // END the list
	 
	            return drawlist;  // Return the display list
	        }
	    }

}


