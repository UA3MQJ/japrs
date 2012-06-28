package loc;
public class Coordinates {

    private double lon, lat, e;
    private float altitude;
    private long time;
    public static final double METERS_PER_RADIAN = 6371000.0;
    private final static double RADIUS = 6372795.0;

    public static double distance(Coordinates from, Coordinates to) {

        double lat1 = Math.toRadians(from.lat);

        double long1 = Math.toRadians(from.lon);

        double lat2 = Math.toRadians(to.lat);

        double long2 = Math.toRadians(to.lon);

        double d1 = Math.sin((lat1 - lat2) / 2);

        d1 *= d1;

        double d2 = Math.cos(lat1) * Math.cos(lat2);

        double d3 = Math.sin((long1 - long2) / 2);

        d3 *= d3;

        double d4 = METERS_PER_RADIAN * 2.0;

        double d5 = Math.sqrt(d1 + d2 * d3);


        return d4 * MathFunc.asin(d5);
    }

    public static float curse(Coordinates from, Coordinates to) {

        // Convert from degrees to radians.
        double lat1 = Math.toRadians(from.lat);
        double lon1 = Math.toRadians(from.lon);
        double lat2 = Math.toRadians(to.lat);
        double lon2 = Math.toRadians(to.lon);
        double deltaLon = lon2 - lon1;
        double cosLat2 = Math.cos(lat2);
        double c1 = Math.sin(deltaLon) * cosLat2;
        double c2 = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * cosLat2 * Math.cos(deltaLon);
        double courseInRadians = MathFunc.atan2(c1, c2);

        double course = Math.toDegrees(courseInRadians);
        course = (360.0 + course) % 360.0;  // Normalize to [0,360)
        return (float) course;
    }

    public static double speed(Coordinates from, Coordinates to) {
        double deltaTime = (double) Math.abs(to.time - from.time);

        deltaTime = deltaTime / 3600000d;

        double distance = Coordinates.distance(from, to) * 1000d;

        double speed = distance / deltaTime;

        return speed;
    }

    public String toString() {
        String s;

        // Add the latitude.
        if (lat >= 0.0) {
            s = String.valueOf(lat);
            s += "°N ";
        } else {
            s = String.valueOf(-1 * lat);
            s += "°S ";
        }

        // Add the longitude.
        if (lon >= 0.0) {
            s += String.valueOf(lon);
            s += "°E";
        } else {
            s += String.valueOf(-1 * lon);
            s += "°W";
        }

        // Add the altitude.
        if (Float.isNaN(altitude) == false) {
            s += (" " + altitude + "m");
        }

        return s;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public void setLatitude(double latitude) {
        if (Double.isNaN(latitude) || (latitude < -90.0 || latitude >= 90.0)) {
            throw new IllegalArgumentException("Latitude (" + latitude + ") is invalid.");
        } else {
            this.lat = latitude;
        }
    }

    public void setLongitude(double longitude) {
        if (Double.isNaN(longitude) || (longitude < -180.0 || longitude >= 180.0)) {
            throw new IllegalArgumentException("Longitude (" + longitude + ") is invalid.");
        } else {
            this.lon = longitude;
        }
    }

    public Coordinates(double latitude, double longitude, float altitude) {
        time = System.currentTimeMillis();
        setLatitude(latitude);
        setLongitude(longitude);
        setAltitude(altitude);
    }

    public Coordinates(double lat, double lon) {
        time = System.currentTimeMillis();
        setLatitude(lat);
        setLongitude(lon);
        setAltitude(0);
    }

    public Coordinates() {
        time = System.currentTimeMillis();
        setLatitude(0);
        setLongitude(0);
        setAltitude(0);
    }

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }

    public boolean equals(Object other) {
        // This is an allowable difference to account for floating point imprecisions.
        final double tolerance = 0.000001;

        if (other == null) {
            return false;
        }

        if ((other instanceof Coordinates) == false) {
            return false;
        }

        // Otherwise it is a Coordinates object.
        Coordinates c = (Coordinates) other;

        if ((lat < c.lat - tolerance) || (lat > c.lat + tolerance)) {
            return false;
        }

        if ((lon < c.lon - tolerance) || (lon > c.lon + tolerance)) {
            return false;
        }

        if ((Float.isNaN(altitude) == true) && (Float.isNaN(c.altitude) == false)) {
            return false;
        }
        if ((Float.isNaN(altitude) == false) && (Float.isNaN(c.altitude) == true)) {
            return false;
        }
        if (Float.isNaN(altitude) && Float.isNaN(c.altitude)) {
            return true;
        }
        if ((altitude < c.altitude - tolerance) || (altitude > c.altitude + tolerance)) {
            return false;
        }

        // If we got here the two coordinates are equal.
        return true;
    }

    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.lon) ^ (Double.doubleToLongBits(this.lon) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.lat) ^ (Double.doubleToLongBits(this.lat) >>> 32));
        return hash;
    }
}
