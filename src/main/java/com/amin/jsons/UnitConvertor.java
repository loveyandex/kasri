package com.amin.jsons;

import javax.measure.quantity.*;
import javax.measure.unit.*;

/**
 * is created by aMIN on 19/07/2018 at 04:17 PM
 */
public enum UnitConvertor {
;

    public enum PRES {
        units(SI.PASCAL, NonSI.ATMOSPHERE, NonSI.BAR, NonSI.MILLIMETER_OF_MERCURY);

        PRES(AlternateUnit<Pressure> pascal, Unit<Pressure> atmosphere,
             Unit<Pressure> bar, Unit<Pressure> millimeterOfMercury) {
            this.pascal = pascal;
            this.atmosphere = atmosphere;
            this.bar = bar;
            this.millimeterOfMercury = millimeterOfMercury;
        }

        private final AlternateUnit<Pressure> pascal;
        private final Unit<Pressure> atmosphere;
        private final Unit<Pressure> bar;
        private final Unit<Pressure> millimeterOfMercury;

        public AlternateUnit<Pressure> getPascal() {
            return pascal;
        }

        public Unit<Pressure> getAtmosphere() {
            return atmosphere;
        }

        public Unit<Pressure> getBar() {
            return bar;
        }

        public Unit<Pressure> getMillimeterOfMercury() {
            return millimeterOfMercury;
        }
    }


    public enum HGHT {
        units(SI.METER, NonSI.FOOT, NonSI.MILE, NonSI.YARD, NonSI.INCH);


        HGHT(Unit<Length> meter, Unit<Length> foot, Unit<Length> mile, Unit<Length> yard, Unit<Length> inch) {
            this.meter = meter;
            this.foot = foot;
            this.mile = mile;
            this.yard = yard;
            this.inch = inch;
        }

        private final Unit<Length> meter;
        private final Unit<Length> foot;
        private final Unit<Length> mile;
        private final Unit<Length> yard;
        private final Unit<Length> inch;

        public Unit<Length> getMeter() {
            return meter;
        }

        public Unit<Length> getFoot() {
            return foot;
        }

        public Unit<Length> getMile() {
            return mile;
        }

        public Unit<Length> getYard() {
            return yard;
        }

        public Unit<Length> getInch() {
            return inch;
        }


    }


    public enum TEMP {
        units(SI.CELSIUS, SI.KELVIN, NonSI.FAHRENHEIT, NonSI.RANKINE);

        private final Unit<Temperature> celsius;
        private final BaseUnit<Temperature> kelvin;
        private final Unit<Temperature> fahrenheit;
        private final Unit<Temperature> rankine;

        public Unit<Temperature> getCelsius() {
            return celsius;
        }

        public BaseUnit<Temperature> getKelvin() {
            return kelvin;
        }

        public Unit<Temperature> getFahrenheit() {
            return fahrenheit;
        }

        public Unit<Temperature> getRankine() {
            return rankine;
        }

        TEMP(Unit<Temperature> celsius, BaseUnit<Temperature> kelvin, Unit<Temperature> fahrenheit, Unit<Temperature> rankine) {
            this.celsius = celsius;
            this.kelvin = kelvin;
            this.fahrenheit = fahrenheit;
            this.rankine = rankine;
        }
    }

    public enum DRCT{

        units(SI.RADIAN,NonSI.DEGREE_ANGLE,NonSI.GRADE,NonSI.MINUTE_ANGLE,NonSI.SECOND_ANGLE,NonSI.REVOLUTION
        );

        private final AlternateUnit<Angle> radian;
        private final Unit<Angle> degreeAngle;
        private final Unit<Angle> grade;
        private final Unit<Angle> minuteAngle;
        private final Unit<Angle> secondAngle;

        public AlternateUnit<Angle> getRadian() {
            return radian;
        }

        public Unit<Angle> getDegreeAngle() {
            return degreeAngle;
        }

        public Unit<Angle> getGrade() {
            return grade;
        }

        public Unit<Angle> getMinuteAngle() {
            return minuteAngle;
        }

        public Unit<Angle> getSecondAngle() {
            return secondAngle;
        }

        public Unit<Angle> getRevolution() {
            return revolution;
        }

        private final Unit<Angle> revolution;

        DRCT(AlternateUnit<Angle> radian, Unit<Angle> degreeAngle, Unit<Angle> grade, Unit<Angle> minuteAngle, Unit<Angle> secondAngle, Unit<Angle> revolution) {

            this.radian = radian;
            this.degreeAngle = degreeAngle;
            this.grade = grade;
            this.minuteAngle = minuteAngle;
            this.secondAngle = secondAngle;
            this.revolution = revolution;
        }
    }

    public enum SPEED{
        units(SI.METERS_PER_SECOND,NonSI.KNOT,NonSI.KILOMETRES_PER_HOUR,NonSI.MACH, NonSI.MILES_PER_HOUR);

        private final Unit<Velocity> metersPerSecond;
        private final Unit<Velocity> knot;
        private final Unit<Velocity> kilometresPerHour;
        private final Unit<Velocity> mach;

        public Unit<Velocity> getMetersPerSecond() {
            return metersPerSecond;
        }

        public Unit<Velocity> getKnot() {
            return knot;
        }

        public Unit<Velocity> getKilometresPerHour() {
            return kilometresPerHour;
        }

        public Unit<Velocity> getMach() {
            return mach;
        }

        public Unit<Velocity> getMilesPerHour() {
            return milesPerHour;
        }

        private final Unit<Velocity> milesPerHour;

        SPEED(Unit<Velocity> metersPerSecond, Unit<Velocity> knot, Unit<Velocity> kilometresPerHour, Unit<Velocity> mach, Unit<Velocity> milesPerHour) {
            this.metersPerSecond = metersPerSecond;
            this.knot = knot;
            this.kilometresPerHour = kilometresPerHour;
            this.mach = mach;
            this.milesPerHour = milesPerHour;
        }
    }


}