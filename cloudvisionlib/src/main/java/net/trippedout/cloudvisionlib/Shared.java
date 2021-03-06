package net.trippedout.cloudvisionlib;

import android.graphics.Path;

import java.util.List;

/**
 * All POJOs that are re-used between annotations
 */
public class Shared {
    public static String LIKELIHOOD_UNKNOWN         = "UNKNOWN";
    public static String LIKELIHOOD_VERY_UNLIKELY   = "VERY_UNLIKELY";
    public static String LIKELIHOOD_UNLIKELY        = "UNLIKELY";
    public static String LIKELIHOOD_POSSIBLE        = "POSSIBLE";
    public static String LIKELIHOOD_LIKELY          = "LIKELY";
    public static String LIKELIHOOD_VERY_LIKELY     = "VERY_LIKELY";

    /**
     * Generic EntityAnnotation used by multiple responses in our {@link CloudVisionApi}
     */
    public static class EntityAnnotation {
        public final String mid;
        public final String locale;
        public final String description;
        public final float score;
        public final float confidence;
        public final float topicality;
        public final Shared.BoundingPoly boundingPoly;
        public final List<LocationInfo> locations;
        public final List<Property> properties;

        public EntityAnnotation(String mid, String locale, String description,
                                float score, float confidence, float topicality,
                                Shared.BoundingPoly boundingPoly,
                                List<LocationInfo> locations, List<Property> properties) {
            this.mid = mid;
            this.locale = locale;
            this.description = description;
            this.score = score;
            this.confidence = confidence;
            this.topicality = topicality;
            this.boundingPoly = boundingPoly;
            this.locations = locations;
            this.properties = properties;
        }

        @Override
        public String toString() {
            return "EntityAnnotation{" +
                    "mid='" + mid + '\'' +
                    ", locale='" + locale + '\'' +
                    ", description='" + description + '\'' +
                    ", score=" + score +
                    ", confidence=" + confidence +
                    ", topicality=" + topicality +
                    ", boundingPoly=" + boundingPoly +
                    ", locations=" + locations +
                    ", properties=" + properties +
                    '}';
        }
    }

    public static class BoundingPoly {
        public final List<Vertex> vertices;
        private Path mPath;

        public BoundingPoly(List<Vertex> vertices) {
            this.vertices = vertices;
        }

        public Path getPath() {
            if (mPath == null) {
                mPath = new Path();
                mPath.moveTo(vertices.get(0).x, vertices.get(0).y);
                for (int i = 1; i < vertices.size(); i++) {
                    mPath.lineTo(vertices.get(i).x, vertices.get(i).y);
                }
                mPath.close();
            }
            return mPath;
        }

        @Override
        public String toString() {
            return "BoundingPoly{" +
                    "vertices=" + vertices +
                    '}';
        }

        public void setScaleAndOffsets(float scaleX, float scaleY, float offsetX, float offsetY) {
            for(int i = 0; i < vertices.size(); i++) {
                Vertex v = vertices.get(i);
                v.x = (v.x * scaleX) + offsetX;
                v.y = (v.y * scaleY) + offsetY;
            }
        }
    }

    public static class Vertex {
        public float x;
        public float y;

        public Vertex(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static class Position extends Vertex {
        public float z;

        public Position(float x, float y, float z) {
            super(x, y);
            this.z = z;
        }
    }

    public static class LocationInfo {
        public final LatLng latLng;

        public LocationInfo(LatLng latLng) {
            this.latLng = latLng;
        }

        @Override
        public String toString() {
            return "LocationInfo{" +
                    "latLng=" + latLng +
                    '}';
        }
    }

    public static class LatLng {
        public final float latitude;
        public final float longitude;

        public LatLng(float latitude, float longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "LatLng{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }

    public static class Property {
        public final String name;
        public final String value;

        public Property(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Property{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
