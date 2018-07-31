package test.analys;

import com.amin.jsons.UnitConvertor;
import com.amin.ui.main.main.Charting;
import org.junit.Test;

import javax.measure.Measure;
import javax.measure.converter.UnitConverter;
import javax.measure.quantity.Pressure;
import javax.measure.quantity.Velocity;
import javax.measure.unit.AlternateUnit;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * is created by aMIN on 19/07/2018 at 04:25 PM
 */
public class js {
    @Test
    public void test0(){
        double gram = Measure.valueOf(1, SI.KILOGRAM).doubleValue(NonSI.POUND);
        System.out.println(gram);
        double ce = Measure.valueOf(1, SI.KELVIN).doubleValue(SI.CELSIUS);
        System.out.println(ce);
        double ve = Measure.valueOf(20, SI.METERS_PER_SECOND).doubleValue(NonSI.KNOT);
        System.out.println(ve);

        double reve = Measure.valueOf(20,NonSI.KNOT).doubleValue(SI.METRES_PER_SECOND);
        System.out.println(reve+"ddd");
        double reve3 = Measure.valueOf(20,NonSI.KNOT).doubleValue((Unit<Velocity>) NonSI.KNOT.getStandardUnit());
        System.out.println(reve3);


        System.out.println(SI.SECOND.getSymbol());
        System.out.println(NonSI.KNOT.toString());
        System.out.println(NonSI.POUND.toString());
        SI.getInstance().getUnits().forEach(unit -> {
            System.out.println(unit.transform(UnitConverter.IDENTITY));
        });

        for (Unit<?> unit : NonSI.getInstance().getUnits()) {
            System.out.println(unit);
        }


        double reve32 = Measure.valueOf(101320,(SI.HECTO(SI.PASCAL)))
                .doubleValue(NonSI.ATMOSPHERE);
        System.out.println(reve32);
    }
    
    
    @Test
    public void test23(){
        Unit<Pressure> atmosphere = UnitConvertor.PRES.units.getAtmosphere();
        AlternateUnit<Pressure> pascal = UnitConvertor.PRES.units.getPascal();
        double reve3 = Measure.valueOf(343423.3,pascal).doubleValue(atmosphere);

        System.out.println(reve3);



    }


    @Test
    public void test24() {

        Method method;
        try {
            method = Charting.class.getMethod("conv28", double.class);
            Object invoke = method.invoke(new Charting(), 28);
            System.out.println(((Double) invoke));


        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }




}
