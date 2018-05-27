package il.ac.technion.cs.yp.btw.citysimulation;

/**
 * Exceptions definition for Vehicle parsing
 */
public class VehicleParsingException extends RuntimeException{
     VehicleParsingException(String message) {
        super(message);
    }
}
class MandatoryFieldNotFoundException extends VehicleParsingException{
    MandatoryFieldNotFoundException(String message) {
        super(message);
    }
}
class FileNotOfJsonSyntaxException extends VehicleParsingException{
    FileNotOfJsonSyntaxException(String message) {
        super(message);
    }
}
class IllegalTimeException extends VehicleParsingException{
     IllegalTimeException(String message) {
        super(message);
    }
}
class RatioOutOfBoundException extends VehicleParsingException{
    RatioOutOfBoundException(String message) {
        super(message);
    }
}
class VehicleFileNotFoundException extends VehicleParsingException{

     VehicleFileNotFoundException(String message) {
        super(message);
    }
}
class VehicleFileNotOfJsonFormatException extends VehicleParsingException{

    VehicleFileNotOfJsonFormatException(String message) {
        super(message);
    }
}
class RoadNameDoesntExistException  extends VehicleParsingException{

    RoadNameDoesntExistException(String message) {
        super(message);
    }
}