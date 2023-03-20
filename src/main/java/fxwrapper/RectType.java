package fxwrapper;

public class RectType {
    public int x, y;
    private int w, h;

    public RectType(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
    }

    public int getWidth(){
        return this.w;
    }

    public int getHeight(){
        return this.h;
    }

    protected void setSize(int w, int h){
        this.w = w;
        this.h = h;
    }

    public boolean collidePoint(int x, int y){
        boolean is_x = x >= this.x && x < this.x + this.w;
        boolean is_y = y >= this.y && y < this.y + this.h;
        return is_x && is_y;
    }

    public boolean collidePoint(Vector2 point){
        return this.collidePoint((int)point.x, (int)point.y);
    }

    public Vector2 getTopLeft(){
        return new Vector2(this.x, this.y);
    }

    public Vector2 getTopRight(){
        return new Vector2(this.x + this.w - 1, this.y);
    }

    public Vector2 getBottomLeft(){
        return new Vector2(this.x, this.y + this.h - 1);
    }

    public Vector2 getBottomRight(){
        return new Vector2(this.x + this.w - 1, this.y + this.h - 1);
    }

    public Vector2 getCenter(){
        int mid_x = this.x + this.w / 2;
        int mid_y = this.y + this.h / 2;
        return new Vector2(mid_x, mid_y);
    }

    public Vector2 getMidTop(){
        int mid_x = this.x + this.w / 2;
        return new Vector2(mid_x, this.y);
    }

    public Vector2 getMidBottom(){
        int mid_x = this.x + this.w / 2;
        return new Vector2(mid_x, this.y + this.h - 1);
    }

    public Vector2 getMidLeft(){
        int mid_y = this.y + this.h / 2;
        return new Vector2(this.x, mid_y);
    }

    public Vector2 getMidRight(){
        int mid_y = this.y + this.h / 2;
        return new Vector2(this.x + this.w - 1, mid_y);
    }

    public void setTopLeft(Vector2 point){
        this.x = (int)point.x;
        this.y = (int)point.y;
    }

    public void setTopRight(Vector2 point){
        this.x = (int)point.x - this.w + 1;
        this.y = (int)point.y;
    }

    public void setBottomLeft(Vector2 point){
        this.x = (int)point.x;
        this.y = (int)point.y - this.h + 1;
    }

    public void setBottomRight(Vector2 point){
        this.x = (int)point.x - this.w + 1;
        this.y = (int)point.y - this.h + 1;
    }

    public void setCenter(Vector2 point){
        this.x = (int)point.x - this.w / 2;
        this.y = (int)point.y - this.h / 2;
    }

    public void setMidTop(Vector2 point){
        this.x = (int)point.x - this.w / 2;
        this.y = (int)point.y;
    }

    public void setMidBottom(Vector2 point){
        this.x = (int)point.x - this.w / 2;
        this.y = (int)point.y - this.h + 1;
    }

    public void setMidLeft(Vector2 point){
        this.x = (int)point.x;
        this.y = (int)point.y - this.h / 2;
    }

    public void setMidRight(Vector2 point){
        this.x = (int)point.x - this.w + 1;
        this.y = (int)point.y - this.h / 2;
    }
}