//package il.ac.technion.cs.yp.btw.geojson;
//
///**
// * Created by anat ana on 09/12/2017..
// */
//public class ToStrings {
//
//    //@Override
//    public String toStringRoad() {
//        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+"\"coordinates\""+":"+
//                "[["+x1+","+y1+"],"+"["+x2+","+y2+"]]},"+"\"properties\":{"+"\"name\":"+"\""+given_name+"\","+
//                "\"sector_start\":"+"\""+given_sector_start+"\","+ "\"sector_end\":"+"\""+given_sector_end+"\","+
//                "\"length\":"+"\""+given_length+"\","+"\"overload\":"+"\""+given_overload+"\"},";
//    }
//
//    //@Override
//    public String toStringTrafficLight() {
//        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
//                "[["+x+","+y+"]},"+"\"properties\":{"+"\"name\":"+"\""+given_name+"\","+
//                "\"overload\":"+"\""+given_overload+"\"},";
//    }
//
//    //@Override
//    public String toStringStreet() {
//        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+"\"coordinates\""+":"+
//                "[["+x1+","+y1+"],"+"["+x2+","+y2+"]]},"+"\"properties\":{"+"\"name\":"+"\""+given_name+"\","+
//                "\"included_streets\":"+"\""+given_included_streets+"\"},";
//    }
//
//    //@Override
//    public String toStringCrossRoad() {
//        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
//                "[["+x+","+y+"]},"+"\"properties\":{"+"\"name\":"+"\""+given_name+"\"},";
//    }
//
//    //@Override
//    public String toStringLocation() {
//        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
//                "[["+x1+","+y1+"],"+"["+x2+","+y2+"],"+"["+x3+","+y3+"],"+"["+x4+","+y4+"]]},"+
//                "\"properties\":{"+"\"name\":"+"\""+given_name+"\","+"\"street\":"+"\""+given_street+"\"},";
//    }
//
//}
