package fxwrapper;

public class Vector2 {
    public static final Vector2 zero = new Vector2(0.0, 0.0);
    public static final Vector2 left = new Vector2(-1.0, 0.0);
    public static final Vector2 right = new Vector2(1.0, 0.0);
    public static final Vector2 up = new Vector2(0.0, -1.0);
    public static final Vector2 down = new Vector2(0.0, 1.0);

    public double x;
    public double y;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2(double degrees){
        this.x = 1.0;
        this.y = 0.0;
        this.setAngle(degrees);
    }

    public void setAngle(double degrees){
        double manitude = this.manitude();
        degrees %= 360;
        if(degrees < 0.0)
            degrees += 360.0;

        this.y = Math.sin(Math.toRadians(degrees));
        this.x = Math.sqrt(1.0 - this.y * this.y);
        if(degrees > 90.0 && degrees < 270.0)
            this.x = -this.x;

        if(!LocalMath.doubleCompare(manitude, 1.0))
            this.mulAssign(manitude);
    }

    public void addAssign(double x, double y){
        this.x += x;
        this.y += y;
    }

    public void addAssign(Vector2 other){
        this.x += other.x;
        this.y += other.y;
    }

    public void subAssign(double x, double y){
        this.x -= x;
        this.y -= y;
    }

    public void subAssign(Vector2 other){
        this.x -= other.x;
        this.y -= other.y;
    }

    public void mulAssign(double delta){
        this.x *= delta;
        this.y *= delta;
    }

    public void divAssign(double delta){
        this.x /= delta;
        this.y /= delta;
    }

    public void copy(Vector2 other){
        this.x = other.x;
        this.y = other.y;
    }

    public boolean moveToWard(Vector2 target, double delta){
        if (LocalMath.doubleCompare(delta, 0.0))
            return false;

        Vector2 sub = this.sub(target);
        double sub_manitude = sub.manitude();

        // Move to target is successful
        if(sub_manitude <= delta) {
            this.x = target.x;
            this.y = target.y;
            return true;
        }

        // Move to target
        Vector2 move_delta = sub.normalize();
        move_delta.mulAssign(sub_manitude);
        this.x -= move_delta.x;
        this.y -= move_delta.y;
        return false;
    }

    public double getAngle(){
        return LocalMath.angle(this.x, this.y);
    }

    public double manitude(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double distance(Vector2 other){
        double x = this.x - other.x;
        double y = this.y - other.y;
        return Math.sqrt(x * x + y * y);
    }

    public Vector2 copy(){
        return new Vector2(this.x, this.y);
    }

    public Vector2 normalize(){
        double manitude = this.manitude();
        return new Vector2(this.x / manitude, this.y / manitude);
    }

    public Vector2 add(double x, double y){
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 add(Vector2 other){
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 sub(double x, double y){
        return new Vector2(this.x - x, this.y - y);
    }

    public Vector2 sub(Vector2 other){
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 mul(double delta){
        return new Vector2(this.x * delta, this.y * delta);
    }

    public Vector2 div(double delta){
        return new Vector2(this.x / delta, this.y / delta);
    }

    @Override
    public String toString(){
        return String.format("Vector(%f, %f)", this.x, this.y);
    }
}