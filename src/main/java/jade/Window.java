package jade;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

public class Window {
	int width, height;
	String title;
	private long glfwWindow;
	
	private static Window window = null;

	private Window() {
		this.width = 1920;
		this.height = 1080;
		this.title = "Mario";
		
	}
	
	public static Window get() {
		if (Window.window == null) {
			Window.window = new Window();
			
		}
		return Window.window;
	}
	
	public void run() {
		System.out.println("Version: " + Version.getVersion() );
		
		init();
		loop();

		// Free the window callbacks and destroy the window
		//glfwFreeCallbacks(glfwWindow);
		//glfwDestroyWindow(glfwWindow);

		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE); // the window will be maximized

		// Create the window
		glfwWindow = glfwCreateWindow(this.width,this.height, "this.title", NULL, NULL);
		if ( glfwWindow == NULL ) {
			throw new IllegalStateException("Failed to create the GLFW window");
			}
	
		// Make OpenGL context current
		glfwMakeContextCurrent(glfwWindow);
		// Enable v-sync
		glfwSwapInterval(1);
		
		// Make the window visible
		glfwShowWindow(glfwWindow);
		
		// This line is critical for LWJGL's interoperation with GLFW's
				// OpenGL context, or any context that is managed externally.
				// LWJGL detects the context that is current in the current thread,
				// creates the GLCapabilities instance and makes the OpenGL
				// bindings available for use.
				GL.createCapabilities();
				}

	private void loop() {
		while (!glfwWindowShouldClose(glfwWindow)) {
			// Poll events
			glfwPollEvents();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		// red: 1.0f, 0.0f, 0.0f, 1.0f
		// green: 0.0f, 1.0f,1.0f
		// blue: 0.0f, 0.0f, 1.0f, 1.0f
		// white: 1.0f, 1.0f, 1.0f, 1.0f

		glfwSwapBuffers(glfwWindow); // swap the color buffers
		glClear(GL_COLOR_BUFFER_BIT);
		}
		
	}
	
}
