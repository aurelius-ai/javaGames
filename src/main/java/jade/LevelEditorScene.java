package jade;

import static org.lwjgl.opengl.GL20.*;

public class LevelEditorScene extends Scene{
	private String vertexShaderSource = "#type vertex\r\n" + 
			"#version 330 core\r\n" + 
			"layout (location=0) in vec3 aPos;\r\n" + 
			"layout (location=1) in vec4 aColor;\r\n" + 
			"layout (location=2) in vec2 aTexCoords;\r\n" + 
			"layout (location=3) in float aTexId;\r\n" + 
			"\r\n" + 
			"uniform mat4 uProjection;\r\n" + 
			"uniform mat4 uView;\r\n" + 
			"\r\n" + 
			"out vec4 fColor;\r\n" + 
			"out vec2 fTexCoords;\r\n" + 
			"out float fTexId;\r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"    fColor = aColor;\r\n" + 
			"    fTexCoords = aTexCoords;\r\n" + 
			"    fTexId = aTexId;\r\n" + 
			"\r\n" + 
			"    gl_Position = uProjection * uView * vec4(aPos, 1.0);\r\n" + 
			"}";
	private String fragmentSource = "#type fragment\r\n" + 
			"#version 330 core\r\n" + 
			"\r\n" + 
			"in vec4 fColor;\r\n" + 
			"in vec2 fTexCoords;\r\n" + 
			"in float fTexId;\r\n" + 
			"\r\n" + 
			"uniform sampler2D uTextures[8];\r\n" + 
			"\r\n" + 
			"out vec4 color;\r\n" + 
			"\r\n" + 
			"void main()\r\n" + 
			"{\r\n" + 
			"    if (fTexId > 0) {\r\n" + 
			"        int id = int(fTexId);\r\n" + 
			"        color = fColor * texture(uTextures[id], fTexCoords);\r\n" + 
			"        //color = vec4(fTexCoords, 0, 1);\r\n" + 
			"    } else {\r\n" + 
			"        color = fColor;\r\n" + 
			"    }\r\n" + 
			"}";
	
	private int vertexID, fragmentID, shaderProgramID;
	
	public LevelEditorScene() {

	}
	
	public void compile() {
        // ============================================================
        // Compile and link shaders
        // ============================================================
        int vertexID, fragmentID;

        // First load and compile the vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(vertexID, vertexShaderSource);
        glCompileShader(vertexID);

        // Check for errors in compilation
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: Vertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        // First load and compile the vertex shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // Pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        // Check for errors in compilation
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR:Fragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        // Link shaders and check for errors
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // Check for linking errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: Linking of shaders failed.");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false : "";
        }
    }
	@Override 
	public void init() {
		compile();
		}
	
	@Override
	public void update(float dt) {
		
	}

}
