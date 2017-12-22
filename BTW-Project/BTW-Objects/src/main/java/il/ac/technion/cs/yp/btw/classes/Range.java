package il.ac.technion.cs.yp.btw.classes;

/**
 * Created by Adam on 13/12/2017.
 */
public interface Range {
    enum RangeType {
        EVEN,ODD,CONTINUOUS;
        boolean filter(int num){
            switch (this){
                case ODD:
                    return num%2==1;
                case EVEN:
                    return num%2==0;
            }
            return true;
        }
    }
    int getLowerBound();
    int getUpperBound();
    RangeType getRangeType();
    default boolean isInRange(int num){
        return getRangeType().filter(num)
                && num<=this.getUpperBound()
                && num>=this.getLowerBound();
    }
}

