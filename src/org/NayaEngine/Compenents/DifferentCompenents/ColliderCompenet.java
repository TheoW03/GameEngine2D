package org.NayaEngine.Compenents.DifferentCompenents;

import org.NayaEngine.Compenents.iComponent;
import org.NayaEngine.Tooling.Rays;
import org.NayaEngine.math.Vector3;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.*;
import java.io.*;


/**
 * @author Theo willis
 * @version 1.0.0
 * ~ project outline here ~
 * @Javadoc
 */
public class ColliderCompenet extends iComponent {
    public float width, height;

    public Rays[] list;
    public Rays ray;

    public ColliderCompenet() {
        this.height = this.gameObject.GetCompenent(SpriteComponents.class).height;
        this.width = this.gameObject.GetCompenent(SpriteComponents.class).width;
    }

    public ColliderCompenet(float colliderWidth, float collliderHeight) {
        this.height = collliderHeight;
        this.width = colliderWidth;

    }
    public ColliderCompenet(Rays[] list){
        this.list = list;
    }
    public ColliderCompenet(Rays ray){
        this.ray = ray;
    }
    @Override
    public void update(float dt) {

    }

    private Vector2f calcOffset(Vector2f p, Vector2f center, Vector2f size) {
        Vector2f o1 = center.sub(p);
        Vector2f o3 = new Vector2f(Math.abs(o1.x), Math.abs(o1.y));
        Vector2f o2 = o3.sub(size);
        return o2;
    }

    private float getDist(Vector3 p, Vector3 center, Vector3 size) {
        Vector2f p1 = new Vector2f(p.x, p.y);
        Vector2f c = new Vector2f(center.x, center.y);
        Vector2f size2 = new Vector2f(size.x, size.y);
        Vector2f offset = calcOffset(p1, c, size2);
        float usignedDIst = offset.max(new Vector2f(0, 0)).length();
        return usignedDIst;
    }


    private Vector3 getUSdist(Vector3 p, Vector3 center, Vector3 size) {
        Vector2f p1 = new Vector2f(p.x, p.y);
        Vector2f c = new Vector2f(center.x, center.y);
        Vector2f size2 = new Vector2f(size.x, size.y);
        Vector2f offset = calcOffset(p1, c, size2);
        float usignedDIst = offset.max(new Vector2f(0, 0)).length();
        System.out.println("usigned dist" + usignedDIst);
        Vector2f signedDIst = offset.max(offset.min(new Vector2f(0, 0)));
        Vector2f output = signedDIst.add(new Vector2f(usignedDIst, usignedDIst));
        return new Vector3(output.x, output.y);
    }

    /**
     * @param collider
     * @return this does the raymarch distance algorithm.
     */
    public boolean isCollided(ColliderCompenet collider) {
        Vector3 location1 = this.gameObject.GetCompenent(TransformComponent.class).location;
        Vector3 location2 = collider.gameObject.GetCompenent(TransformComponent.class).location;
        Vector3 size = collider.gameObject.GetCompenent(SpriteComponents.class).get_size();
        return getDist(location1, location2, size) == 0;
    }
    public boolean rayCollide(ColliderCompenet collider){
        if(ray == null){
            return false;
        }
        ray.origin = this.gameObject.GetCompenent(TransformComponent.class).location;
        Vector3 location1 = this.ray.getEndPoint();
        Vector3 location2 = collider.gameObject.GetCompenent(TransformComponent.class).location;
        Vector3 size = collider.gameObject.GetCompenent(SpriteComponents.class).get_size();
        return getDist(location1, location2, size) == 0;
    }
//    private boolean isCollided(ColliderCompenet collider) {
//        Vector3 location1 = this.gameObject.GetCompenent(TransformComponent.class).location;
//        Vector3 location2 = collider.gameObject.GetCompenent(TransformComponent.class).location;
//        float x1 = Math.max(location1.x, location2.x);
//        float y1 = Math.max(location1.y, location2.y);
//        float x2 = Math.min(location1.x + this.width * 2 / 100, location2.x +
//                collider.width * 2 / 100);
//        float y2 = Math.min(location1.y + this.height * 2 / 100, location2.y +
//                collider.height * 2 / 100);
//        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
//        if ((x2 * width - x1 > 0 && y2 * 1.70 - y1 > 0)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}



