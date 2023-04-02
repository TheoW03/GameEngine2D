#version 330 core

in vec3 FragPos;
in vec3 Normal;

in vec2 tCoord;
in vec2 toLight;
in vec4 lightSourcelocation;
uniform sampler2D tSample;

uniform float Lightexists = 0;
uniform float LightSource = 0;
uniform float fColor;
uniform vec3 lightPos;
uniform vec3 lightColor;
uniform vec3 objectColor;
//uniform float ambientStrength;
uniform float diffuseStrength;
uniform float specularStrength;
uniform float shininess;
uniform float strength;
uniform vec3 viewPos;


//uniform float baseIntensity;
//uniform vec3 color;
//uniform vec2 location;
//uniform vec3 lightColor;
uniform float lightIntensity;

out vec4 FragColor;

struct Light {
    vec3 position;
    vec3 color;
    float intensity;
};
struct renderedObject{
    vec3 textureCoords;
    vec4 color;
};

vec4 calcLight(Light light){
    return vec4(0,0,0,0);
}
vec4 getSprite(renderedObject light){
    return vec4(0,0,0,0);
}

void main(){


    if (Lightexists == 1){
        //        float ambientS = 0.1;

        vec3 lightDir = normalize(lightPos - FragPos);
        vec3 viewDir = normalize(viewPos - FragPos);


        vec3 norm = normalize(Normal);
        vec3 reflectDir = reflect(lightDir, norm);
        float spec = pow(max(dot(viewDir, reflectDir), 0.0), 4);

        float diff = max(dot(norm, lightDir), 0.0);

        vec3 diffuse = diff * lightColor;
        vec3 ambient = strength * lightColor;
        vec3 specular = specularStrength * spec * lightColor;

        //        vec3 result = ambient * objectColor;
        //        vec4 color = texture(tSample, tCoord)+vec4(result,1.0);
        //        if (color.a < 0.1){
        //            discard;
        //        }

        if (LightSource == 1){
            vec3 result = (ambient)*objectColor;
            FragColor = vec4(result, 1.0)*texture(tSample, tCoord);

        } else {
            vec3 result = (diffuse+ambient+specular)*objectColor;
            FragColor = vec4(result, 1.0)*texture(tSample, tCoord);

        }

    } else {
        vec4 color = texture(tSample, tCoord);
        FragColor = color ;
    }

    //    color *= vec4(lightColor,1.0);

    //


    //    vec2 distance = location - tCoord;

    //    float raidalFallof = pow(1.0 - distance,2.0);
    //    float angularFalloff = smoothstep()

    //    vec3 color = vec3(texture(tSample, tCoord).r, texture(tSample, tCoord).g, texture(tSample, tCoord).b);

}
