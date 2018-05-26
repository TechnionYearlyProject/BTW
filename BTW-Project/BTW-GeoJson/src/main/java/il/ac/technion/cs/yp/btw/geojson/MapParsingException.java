package il.ac.technion.cs.yp.btw.geojson;

/**
 * Exceptions definition for Vehicle parsing
 */
public class MapParsingException extends RuntimeException{
    MapParsingException(String message) {
        super(message);
    }
}
class MapFileNotFoundException extends MapParsingException{
    MapFileNotFoundException(String message) {
        super(message);
    }
}
class MapFileNotWithGeoJsonExtensionException extends MapParsingException{
    MapFileNotWithGeoJsonExtensionException(String message) {
        super(message);
    }
}
class FileNotOfJsonSyntaxException extends MapParsingException{
    FileNotOfJsonSyntaxException(String message) {
        super(message);
    }
}