package org.NayaEngine.Compenents.DifferentCompenents;

import org.NayaEngine.Compenents.iComponent;
import org.NayaEngine.math.Ray;
import org.NayaEngine.math.Vector3;
import org.joml.Vector2f;


/**
 * @author Theo willis
 * @version 1.0.0
 * ~ project outline here ~
 * @Javadoc
 */
public class ColliderCompenet extends iComponent {
    public float width, height;

    public Ray[] list;
    public Ray ray;

    public ColliderCompenet() {
//        this.height = this.gameObject.GetCompenent(SpriteComponents.class).height;
//        this.width = this.gameObject.GetCompenent(SpriteComponents.class).width;
    }

    public ColliderCompenet(float colliderWidth, float collliderHeight) {
        this.height = collliderHeight;
        this.width = colliderWidth;

    }
    public ColliderCompenet(Ray[] list){
        this.list = list;
    }
    public ColliderCompenet(Ray ray){
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
        return offset.max(new Vector2f(0, 0)).length();
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
        System.out.println("size unit vector: "+size);
        return getDist(location1, location2, size) == 0;
    }
    public boolean ray_collides(ColliderCompenet collider){
        if(ray == null){
            return false;
        }
        Vector3 location2 = collider.gameObject.GetCompenent(TransformComponent.class).location;
        Vector3 size = collider.gameObject.GetCompenent(SpriteComponents.class).get_size();
        ray.origin = this.gameObject.GetCompenent(TransformComponent.class).location;
        System.out.println(ray.getEndPoint());
        Vector3 origin = ray.origin;
        Vector3 direction = ray.dir.unitVector();
        Vector2f s = new Vector2f(origin.x,origin.y);
        Vector2f d = new Vector2f(direction.x,direction.y);
        int x = (int) Math.floor(s.x);
        int y = (int) Math.floor(s.y);
        float tX, tY;
        float dTx, dTy, stepX, stepY;
        if (direction.x < 0) {
            tX = (s.x - x) / (-d.x);
            stepX = -1;
            dTx = -1 / d.x;
        } else {
            tX = (x + 1 - s.x) / d.x;
            stepX = 1;
            dTx = 1 / d.x;
        }

        if (d.y < 0) {
            tY = (s.y - y) / (-d.y);
            stepY = -1;
            dTy = -1 / d.y;
        } else {
            tY = (y + 1 - s.y) / d.y;
            stepY = 1;
            dTy = 1 / d.y;
        }
        double dist = 0;
        while (dist < ray.length){
            double tMin = Math.min(tX,tY);
            // Check for intersection with each plane
            if (tX == tMin) {
                x += stepX;
                tX += dTx;
            }
            if (tY == tMin) {
                y += stepY;
                tY += dTy;
            }
            if(getDist(new Vector3(x,y),location2,size) == 0){
                return true;
            }
            System.out.println(new Vector3(x,y));
            dist = Math.sqrt((x - s.x)*(x - s.x) + (y - s.y)*(y - s.y));

        }
        return false;
    }
    private void checkUnsignedDist(ColliderCompenet collider){
        Vector3 location1 = this.gameObject.GetCompenent(TransformComponent.class).location;
        Vector3 location2 = collider.gameObject.GetCompenent(TransformComponent.class).location;
        System.out.println("checks: "+getUSdist(location2,location2,collider.gameObject.GetCompenent(SpriteComponents.class).get_size()));

    }
//    public boolean rayCollide(ColliderCompenet collider){
//        if(ray == null){
//            return false;
//        }
//        ray.origin = this.gameObject.GetCompenent(TransformComponent.class).location;
//        Vector3 location1 = this.ray.getEndPoint();
//        Vector3 location2 = collider.gameObject.GetCompenent(TransformComponent.class).location;
//        Vector3 size = new Vector3(0, ray.length);
//        System.out.println(location1);
//        System.out.println("dist: "+getDist(location1, location2, size));
//        return getDist(location1, location2, size) == 0;
//    }
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



