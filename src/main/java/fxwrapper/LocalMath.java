package fxwrapper;

public class LocalMath {
    private static final double E = 1e-6;

    /** Tính góc của <b>vector</b>.
     * @return góc ( tính theo độ ) của vector.
     */
    public static double angle(double x, double y){
        double degrees = Math.toDegrees(Math.atan2(y, x));

        if (degrees < 0.0)
            return degrees + 360.0;
        return degrees;
    }

    /** Tính khoảng cách giữa hai <b>vector</b>.
     * @return khoảng cách giữa hai vector
     */
    public static double distance(double x1, double y1, double x2, double y2){
        double x3 = x1 - x2;
        double y3 = y1 - y2;
        return Math.sqrt(x3 * x3 + y3 * y3);
    }

    /** Tịnh tiến từ <b>current</b> đến <b>target</b> một khoảng <b>delta</b>.
     *
     * @param current giá trị hiện hành
     * @param target giá trị đích
     * @param delta khoảng cách
     * @return giá trị sau khi tịnh tiến
     */
    public static double moveToWard(double current, double target, double delta){
        if (Math.abs(target - current) <= delta)
            return delta;
        if (target > current)
            return current + delta;
        return current - delta;
    }

    /** So sánh gần đúng hai giá trị <b>double</b> với sai số <b>1e-6</b>.
     * @return trả về <b>true</b> nếu hai giá trị gần như bằng nhau.
     */
    public static boolean doubleCompare(double a, double b){
        double abs = Math.abs(a - b);
        return abs < LocalMath.E;
    }
}