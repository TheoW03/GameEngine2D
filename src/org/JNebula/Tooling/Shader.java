package org.JNebula.Tooling;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Function;


/**
 * @author Theo willis
 * @version 1.0.0
 * ~ project outline here ~
 * @Javadoc
 */
public class Shader {


    private final String fragShdaer2D = "src/shaders/2DFrag.glsl";
    public final String vertexShader2D = "src/shaders/2DVertex.glsl";
    private final String fragShdaer3D = "src/shaders/3DFrag.glsl";
    public final String vertexShader3D = "src/shaders/3DVertex.glsl";

    public static ArrayList<String> effectShaders2D = new ArrayList<>();
    public static ArrayList<String> effectShaders3D = new ArrayList<>();


    public String vertexSource, fragSource;
    public String effectShader2D;
    public String effectShader3D;
    public Function<GL4, Integer> compile2DShaders = (GL4 gl) -> {
        // Your logic here
        StringBuilder fragShader = new StringBuilder();
        StringBuilder vertexShader = new StringBuilder();
        ArrayList<String> a = new ArrayList<>();

        fragShader.append("#version 430 core\n");
        fragShader.append("struct util_effectShader{ \n" +
                "    vec2 iResolution;\n" +
                "    float iTime;\n" +
                "    float deltaTime;\n" +
                "\n" +
                "}; \n");
        for (String s : effectShaders2D) {
            try {
                a = (ArrayList<String>) Files.readAllLines(Path.of(s), StandardCharsets.UTF_8);
                a.forEach(contents -> {
                    fragShader.append(contents);
                    fragShader.append("\n");
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        try {
            a = (ArrayList<String>) Files.readAllLines(Path.of(fragShdaer2D), StandardCharsets.UTF_8);
            a.forEach(contents -> {
                fragShader.append(contents);
                fragShader.append("\n");
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            a = (ArrayList<String>) Files.readAllLines(Path.of(vertexShader2D), StandardCharsets.UTF_8);
            a.forEach(contents -> {
                vertexShader.append(contents);
                vertexShader.append("\n");
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return compileShaders(vertexShader.toString(), fragShader.toString(), gl);

    };

    public Function<GL4, Integer> compile3DShaders = (GL4 gl) -> {
        // Your logic here
        StringBuilder fragShader = new StringBuilder();
        StringBuilder vertexShader = new StringBuilder();
        effectShaders3D.add(fragShdaer3D);
        for (int i = 0; i < effectShaders3D.size(); i++) {
            ArrayList<String> a = null;
            try {
                a = (ArrayList<String>) Files.readAllLines(Path.of(effectShaders3D.get(i)), StandardCharsets.UTF_8);
                a.forEach(contents -> {
                    fragShader.append(contents);
                    fragShader.append("\n");
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        ArrayList<String> a;
        try {
            a = (ArrayList<String>) Files.readAllLines(Path.of(vertexShader3D), StandardCharsets.UTF_8);
            a.forEach(contents -> {
                vertexShader.append(contents);
                vertexShader.append("\n");
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return compileShaders(vertexShader.toString(), fragShader.toString(), gl);
    };


    /**
     * functionaled my code :sunglasses:
     */
//    public static Function<String, String> getShaderString = ((String fileName) -> {
//
//        StringBuilder t = new StringBuilder();
//        String file = "src/shaders/" + fileName;
//        try {
//            ArrayList<String> a = (ArrayList<String>) Files.readAllLines(Path.of(file), StandardCharsets.UTF_8);
//            a.forEach(contents -> {
//                t.append(contents);
//                t.append("\n");
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//            exit(1);
//        }
//        return t.toString();
//    });
//

    public static String getShaderString(String fileName){
        var file = "src/shaders/" + fileName;
        var t = new StringBuilder();
        try{
            Files.readAllLines(Path.of(file), StandardCharsets.UTF_8).stream().forEach(n ->{
                t.append(n);
                t.append("\n");

            });
        }catch (Exception e){

        }
        return t.toString();

    }
    public int load2DShaders(GL4 gl) {
        return compile2DShaders.apply(gl);
//        if (vertexSource != null && fragSource != null) {
//            return compileShaders(vertexSource, fragSource, gl);
//        }
//        vertexSource = getShaderString.apply("2DVertex.glsl");
//        fragSource = getShaderString.apply("2DFrag.glsl");
//        return compileShaders(vertexSource, fragSource, gl);
    }

    public int load3DShaders(GL4 gl) {
        return compile3DShaders.apply(gl);
//        if (vertexSource != null && fragSource != null) {
//            return compileShaders(vertexSource, fragSource, gl);
//        }
//        vertexSource = getShaderString.apply("2DVertex.glsl");
//        fragSource = getShaderString.apply("2DFrag.glsl");
//        return compileShaders(vertexSource, fragSource, gl);
    }

    public int compileShaders(String vertexShaderS, String fragShader, GL4 gl2) {

        int shaderProgram = gl2.glCreateProgram();
        int vertexShader = gl2.glCreateShader(GL2.GL_VERTEX_SHADER);
        gl2.glShaderSource(vertexShader, 1, new String[]{vertexShaderS}, null);
        gl2.glCompileShader(vertexShader);
        int[] sucess = new int[1];
//        int sucess = 0;
        gl2.glGetShaderiv(vertexShader, GL2.GL_COMPILE_STATUS, sucess, 0);
        if (sucess[0] == GL2.GL_FALSE) {
            int[] logLength = new int[1];
            gl2.glGetShaderiv(vertexShader, GL2.GL_INFO_LOG_LENGTH, logLength, 0);

            ByteBuffer infoLog = ByteBuffer.allocate(logLength[0]);
            gl2.glGetShaderInfoLog(vertexShader, logLength[0], null, infoLog);
            String logString = new String(infoLog.array(), 0, logLength[0]);
            System.err.println(logString);
        }
        gl2.glAttachShader(shaderProgram, vertexShader);

// Compile and attach the fragment shader
        int fragmentShader = gl2.glCreateShader(GL2.GL_FRAGMENT_SHADER);
        gl2.glShaderSource(fragmentShader, 1, new String[]{fragShader}, null);
        gl2.glCompileShader(fragmentShader);
        gl2.glAttachShader(shaderProgram, fragmentShader);
        sucess = new int[1];
        gl2.glGetShaderiv(fragmentShader, GL2.GL_COMPILE_STATUS, sucess, 0);

        if (sucess[0] == GL2.GL_FALSE) {
            int[] logLength = new int[1];
            gl2.glGetShaderiv(fragmentShader, GL2.GL_INFO_LOG_LENGTH, logLength, 0);

            ByteBuffer infoLog = ByteBuffer.allocate(logLength[0]);
            gl2.glGetShaderInfoLog(fragmentShader, logLength[0], null, infoLog);
            String logString = new String(infoLog.array(), 0, logLength[0]);
            System.err.println(logString);
        }
        gl2.glLinkProgram(shaderProgram);

        gl2.glUseProgram(shaderProgram);

        return shaderProgram;

    }

    public void genBuffer(int[] buffer, GL4 gl) {

        gl.glGenBuffers(buffer.length, buffer, 0);
    }

    public void bindBuffer(int[] buffer, int GL_CONSTANT, GL4 gl) {
        gl.glBindBuffer(GL_CONSTANT, buffer[0]);
    }

    public void freeBuffer(int[] buffer, int GL_CONSTANT, GL4 gl) {
        gl.glBindBuffer(GL_CONSTANT, 0);

    }

    public void sendMatrices(Matrix4f matrice, GL4 gl, int matricLocation) {
        FloatBuffer matBufferM = Buffers.newDirectFloatBuffer(1024);
        matrice.get(matBufferM);
//        c.initModel(new Vector3(0,0,0)).get(matBufferM);
        gl.glUniformMatrix4fv(matricLocation, 1, false, matBufferM);
        matBufferM.clear();
    }
}
