package org.NayaEngine.Compenents.DifferentCompenents;


import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import org.NayaEngine.Compenents.iComponent;
import org.NayaEngine.GameObjects.GameObject;
import org.NayaEngine.Tooling.loadShader;
import org.NayaEngine.math.Vector3;
import org.NayaEngine.math.VectorMath;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;

/**
 * @author Theo willis
 * @version 1.0.0
 * ~ project outline here ~
 * @Javadoc
 */
public class LightingComponent extends iComponent {

    public LightingComponent(float intensity,
                             float[] lightColor,
                             float strength,
                             GL2 gl) {
        this.gl = gl;


    }

    public LightingComponent(GL2 gl){
        this.gl = gl;

    }

    @Override
    public void init(float dt) {

    }

    @Override
    public String toString() {
        return "LightingComponent";
    }
    public void bake(){

    }
    float i = 0;

    @Override
    public void update(float dt) {


    }

    float i2 = 1.0f;
    @Override
    public void sendtoGPU(int shaderProgram, loadShader sh) {
        int[] buffers = new int[2];
        gl.glGenBuffers(2, buffers, 0);
        int lightColorLoc = gl.glGetUniformLocation(shaderProgram, "lightColor");
        int objColor = gl.glGetUniformLocation(shaderProgram, "objectColor");
        int lightExists = gl.glGetUniformLocation(shaderProgram, "Lightexists");
        int lightExists2 = gl.glGetUniformLocation(shaderProgram, "lightExits2");
        int strength = gl.glGetUniformLocation(shaderProgram, "strength");
        int lightPosition = gl.glGetUniformLocation(shaderProgram, "lightPos");


        System.out.println("light: "+lightColorLoc);
        System.out.println("obj: "+objColor);
        gl.glUniform3f(lightColorLoc, 1.0f,1.0f,1.0f);
        gl.glUniform3f(objColor, 1.0f, 0.5f, 0.31f);
        gl.glUniform1f(lightExists, 1.0f);
        gl.glUniform1f(lightExists2, 1.0f);
        Vector3 a = gameObject.GetCompenent(TransformComponent.class).location;
        gl.glUniform3f(lightPosition,a.x, a.y,0);
        a.x += i2;
        if(a.x <0){
            i2 = 1.0f;
        }else if(a.x > 200){
            i2 = -1.0f;
        }
        gameObject.GetCompenent(TransformComponent.class).location = a;
        gl.glUniform1f(strength, 0.0f);
        VectorMath v = new VectorMath();
        float[] normals = v.vectorNormals(this.gameObject.GetCompenent(SpriteComponents.class).getVecticesAsVector());
        int normalLoc = gl.glGetAttribLocation(shaderProgram,"anormal");
        gl.glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
        gl.glBufferData(GL_ARRAY_BUFFER, normals.length * 4L, FloatBuffer.wrap(normals), GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(normalLoc);
        gl.glVertexAttribPointer(normalLoc, 2, GL_FLOAT, false, 0, 0);
        System.out.println("normals: " + Arrays.toString(normals));
//        if(i2 == 20){
//          i2 = 0;
//        }
//        i2 = i2 - 1.0f;
        System.out.println("u2: "+i2);

    }
}
