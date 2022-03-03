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

import util.Time;

public class Window {
	int width, height;
	String title;
	private long glfwWindow;
	
	public float r,g,b,a;
	
	private static Window window = null;

	private static Scene currentScene;
	
	private Window() {
		this.width = 1920;
		this.height = 1080;
		this.title = "Mario";
		r = 1; 	b = 1; 	g = 1; 	a = 1;
	}
	
	public static void changeScene(int newScene) {
		switch (newScene) {
		case 0:
			currentScene = new LevelEditorScene();
			//currentScene.init();
			break;
		case 1:
			currentScene = new LevelScene();
			break;
		default:
			assert false: "Unknown scene '" +  newScene + "'";
			break;
			}
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
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);

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
		
		glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback); // lambda syntax to return pos callback to function
	
		glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
		glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
		glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
		
		// Make OpenGL context current
		glfwMakeContextCurrent(glfwWindow);

		// locks to refresh rate of monitor (enables v-sync)
		glfwSwapInterval(1);
		
		// Make the window visible
		glfwShowWindow(glfwWindow);
		
		// This line is critical for LWJGL's interoperation with GLFW's
				// OpenGL context, or any context that is managed externally.
				// LWJGL detects the context that is current in the current thread,
				// creates the GLCapabilities instance and makes the OpenGL
				// bindings available for use.
		
		GL.createCapabilities();
		Window.changeScene(0);
	}

	
	
	private void loop() {
		float beginTime = Time.getTime();
		float endTime = Time.getTime();
		float dt = -1.0f;
		while (!glfwWindowShouldClose(glfwWindow)) {
			// Poll events
			glfwPollEvents();

		// Set the clear color
		glClearColor(r, g, b, a);
		glClear(GL_COLOR_BUFFER_BIT);
		
		if(dt >= 0) currentScene.update(dt);
			
		glfwSwapBuffers(glfwWindow); // swap the color buffers
		
		endTime = Time.getTime();
		dt = endTime - beginTime;
		beginTime = endTime;
		
	}
	
}}

