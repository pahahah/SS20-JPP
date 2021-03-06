package de.uniwue.jpp.oscidrawing.generation;

import de.uniwue.jpp.oscidrawing.Signal;
import de.uniwue.jpp.oscidrawing.generation.pathutils.Line;
import de.uniwue.jpp.oscidrawing.generation.pathutils.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleUnaryOperator;

public abstract class SignalFactory {
    public static Signal fromValues(double[] signalData, int sampleRate) {
        if(sampleRate <= 0){
            throw new IllegalArgumentException("sample rate is negative");
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                return false;
            }

            @Override
            public int getSize() {
                return signalData.length;
            }

            @Override
            public int getChannelCount() {
                return 1;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return signalData[index];
            }
        };

    }

    public static Signal wave(DoubleUnaryOperator function, double frequency, double duration, int sampleRate) {

        if(frequency <= 0 || duration <= 0 || sampleRate <= 0){
            throw new IllegalArgumentException();
        }

        Signal signal = new Signal() {
            @Override
            public boolean isInfinite() {
                return false;
            }

            @Override
            public int getSize() {
                return (int)(sampleRate * duration);
            }

            @Override
            public int getChannelCount() {
                return 1;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                double step = (frequency * 2 * Math.PI) / sampleRate;
                return function.applyAsDouble(index * step);
            }
        };
        return signal;
    }

    public static Signal rampUp(double duration, int sampleRate) {
        if(duration <= 0 || sampleRate <= 0){
            throw new IllegalArgumentException();
        }
        int size = (int) (duration * sampleRate);

        if(size < 2){
            throw new IllegalArgumentException();
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                return false;
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public int getChannelCount() {
                return 1;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return (double)index/(getSize() - 1);
            }
        };
    }

    public static Signal combineMonoSignals(List<Signal> signals) {


        if(signals == null){
            throw new NullPointerException();
        }
        if(signals.isEmpty()){
            throw  new IllegalArgumentException();
        }

        int sampleRate = signals.get(0).getSampleRate();

        for(Signal s : signals){
            if(sampleRate != s.getSampleRate() || s.getChannelCount() != 1){
                throw new IllegalArgumentException();
            }

        }



        return new Signal() {
            @Override
            public boolean isInfinite() {

                int counter = 0;

                for(Signal s : signals){
                    if(s.isInfinite()){
                        counter++;
                    }

                }

                if(counter == signals.size()){
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public int getSize() {
                if(isInfinite()){
                    return -1;
                }else {
                    int size = signals.get(0).getSize();
                    for(Signal s : signals){
                        if(!s.isInfinite()){
                            if(size > s.getSize()){
                                size = s.getSize();
                            }
                        }

                    }
                    return size;
                }

            }

            @Override
            public int getChannelCount() {
                return signals.size();
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return signals.get(channel).getValueAt(0, index);
            }
        };


    }

    public static Signal combineMonoSignals(Signal... signals) {
        if(signals == null){
            throw new NullPointerException();
        }
        if(signals.length == 0){
            throw  new IllegalArgumentException();
        }

        int sampleRate = signals[0].getSampleRate();

        for(Signal s : signals){
            if(sampleRate != s.getSampleRate() || s.getChannelCount() != 1){
                throw new IllegalArgumentException();
            }

        }

        return new Signal() {
            @Override
            public boolean isInfinite() {

                int counter = 0;

                for(Signal s : signals){
                    if(s.isInfinite()){
                        counter++;
                    }

                }

                if(counter == signals.length){
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public int getSize() {
                if(isInfinite()){
                    return -1;
                }else {
                    int size = signals[0].getSize();
                    for(Signal s : signals){
                        if(!s.isInfinite()){
                            if(size > s.getSize()){
                                size = s.getSize();
                            }
                        }

                    }
                    return size;
                }

            }

            @Override
            public int getChannelCount() {
                return signals.length;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return signals[channel].getValueAt(0, index);
            }
        };




    }

    public static Signal stereoFromMonos(Signal left, Signal right) {
        if(left == null || right == null){
            throw new NullPointerException();
        }
        if(left.getChannelCount() != 1 || right.getChannelCount() != 1){
            throw new IllegalArgumentException();
        }

        if(left.getSampleRate() != right.getSampleRate()){
            throw new IllegalArgumentException();
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                if(left.isInfinite() && right.isInfinite()){
                    return true;
                }else {
                    return false;
                }

            }

            @Override
            public int getSize() {
                if(isInfinite()){
                    return -1;
                }else {
                    if(left.getSize() < right.getSize()){
                        return left.getSize();
                    }else {
                        return right.getSize();
                    }
                }
            }

            @Override
            public int getChannelCount() {
                return 2;
            }

            @Override
            public int getSampleRate() {
                return left.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                if(channel == 0){
                    return left.getValueAt(0, index);
                }else {
                    return right.getValueAt(0, index);
                }
            }
        };
    }

    public static Signal extractChannels(Signal source, int... channels) {

        if(source == null){
            throw new NullPointerException();
        }
        for(int channel : channels){
            if(channel < 0 || channel >= source.getChannelCount()){
                throw new IllegalArgumentException();
            }
        }


        return new Signal() {
            @Override
            public boolean isInfinite() {
                return source.isInfinite();
            }

            @Override
            public int getSize() {
                return source.getSize();
            }

            @Override
            public int getChannelCount() {
                return channels.length;
            }

            @Override
            public int getSampleRate() {
                return source.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return source.getValueAtValid(channels[channel], index);
            }
        };


    }

    public static Signal circle(double frequency, double duration, int sampleRate) {
        if(frequency <= 0 || duration <= 0 || sampleRate <= 0){
            throw new IllegalArgumentException();
        }
        return new Signal() {
            @Override
            public boolean isInfinite() {
                return false;
            }

            @Override
            public int getSize() {

                double size = duration * sampleRate;
                return  (int)size;
            }

            @Override
            public int getChannelCount() {
                return 2;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                double step = (frequency * 2 * Math.PI) / sampleRate;
                if(channel == 0){
                    return Math.sin(index * step);
                }else {
                    return Math.cos(index * step);
                }

            }
        };



    }

    public static Signal cycle(Signal signal) {
        if(signal == null){
            throw new NullPointerException();
        }

        int sampleRate = signal.getSampleRate();

        if(signal.isInfinite()){
            return new Signal() {
                @Override
                public boolean isInfinite() {
                    return true;
                }

                @Override
                public int getSize() {
                    return -1;
                }

                @Override
                public int getChannelCount() {
                    return signal.getChannelCount();
                }

                @Override
                public int getSampleRate() {
                    return signal.getSampleRate();
                }

                @Override
                public double getValueAtValid(int channel, int index) {
                    return signal.getValueAtValid(channel, index);
                }
            };
        }else {
            return new Signal() {
                @Override
                public boolean isInfinite() {
                    return true;
                }

                @Override
                public int getSize() {
                    return -1;
                }

                @Override
                public int getChannelCount() {
                    return signal.getChannelCount();
                }

                @Override
                public int getSampleRate() {
                    return signal.getSampleRate();
                }

                @Override
                public double getValueAtValid(int channel, int index) {
                    int index1 = index;
                    if(index >= signal.getSize()){
                        index1 = index % signal.getSize();
                    }

                    return signal.getValueAtValid(channel, index1);
                }
            };
        }

    }

    public static Signal infiniteFromValue(double value, int sampleRate) {
        return  new Signal() {
            @Override
            public boolean isInfinite() {
                return true;
            }

            @Override
            public int getSize() {
                return -1;
            }

            @Override
            public int getChannelCount() {
                return 1;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return value;
            }
        };
    }

    public static Signal take(int count, Signal source) {
        if(count < 0){
            throw new IllegalArgumentException();
        }
        return  new Signal() {
            @Override
            public boolean isInfinite() {
                return false;
            }

            @Override
            public int getSize() {
                return count;
            }

            @Override
            public int getChannelCount() {
                return source.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return source.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                if(source.isInfinite()){
                    return source.getValueAtValid(channel, index);
                }else {
                    if(source.getSize() < count){
                        if(index < source.getSize()){
                            return source.getValueAtValid(channel, index);
                        }else {
                            return 0;
                        }

                    }else {
                        return source.getValueAtValid(channel, index);
                    }
                }


            }
        };
    }

    public static Signal drop(int count, Signal source) {

        if(count < 0){
            throw new IllegalArgumentException();
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                return source.isInfinite();
            }

            @Override
            public int getSize() {
                if(isInfinite()){
                    return -1;
                }else {
                    if(source.getSize() > count){
                        return source.getSize() - count;
                    }else {
                        return 0;
                    }

                }

            }

            @Override
            public int getChannelCount() {
                return source.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return source.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return source.getValueAtValid(channel, index + count);
            }
        };

    }

    public static Signal transform(DoubleUnaryOperator function, Signal source) {
        if(function == null || source == null){
            throw new NullPointerException();
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                return source.isInfinite();
            }

            @Override
            public int getSize() {
                return source.getSize();
            }

            @Override
            public int getChannelCount() {
                return source.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return source.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return function.applyAsDouble(source.getValueAtValid(channel, index));
            }
        };
    }

    public static Signal scale(double amplitude, Signal source) {
        if(source == null){
            throw new NullPointerException();
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                return source.isInfinite();
            }

            @Override
            public int getSize() {
                return source.getSize();
            }

            @Override
            public int getChannelCount() {
                return source.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return source.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return source.getValueAtValid(channel, index) * amplitude;
            }
        };
    }

    public static Signal reverse(Signal source) {
        if(source == null){
            throw new NullPointerException();
        }
        if(source.isInfinite()){
            throw new IllegalArgumentException();
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                return false;
            }

            @Override
            public int getSize() {
                return source.getSize();
            }

            @Override
            public int getChannelCount() {
                return source.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return source.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return source.getValueAtValid(channel, source.getSize() - 1 - index);
            }
        };

    }

    public static Signal rampDown(double duration, int sampleRate) {
        if(duration < 0 || sampleRate < 0){
            throw new IllegalArgumentException();
        }
        int size = (int) (duration * sampleRate);

        if(size < 2){
            throw new IllegalArgumentException();
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                return false;
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public int getChannelCount() {
                return 1;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return (double)(getSize() - 1 - index) / (getSize() - 1);
            }
        };
    }

    public static Signal merge(BiFunction<Double, Double, Double> function, Signal s1, Signal s2) {
        if(s1 == null || s2 == null || function == null){
            throw new NullPointerException();
        }

        if(s1.getSampleRate() != s2.getSampleRate() || s1.getChannelCount() != s2.getChannelCount()){
            throw new IllegalArgumentException();
        }

        int size;

        if(s1.isInfinite() && s2.isInfinite()){
            size = -1;
        }else if(!s1.isInfinite() && !s2.isInfinite()){
            size = Math.min(s1.getSize(), s2.getSize());

        }else {
            if(!s1.isInfinite()){
                size = s1.getSize();
            }else {
                size = s2.getSize();
            }
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                if(s1.isInfinite() && s2.isInfinite()){
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public int getChannelCount() {
                return s1.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return s1.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return function.apply(s1.getValueAt(channel, index), s2.getValueAtValid(channel, index));
            }
        };

    }

    public static Signal add(Signal s1, Signal s2) {
        if(s1 == null || s2 == null){
            throw  new NullPointerException();
        }

        if(s1.getSampleRate() != s2.getSampleRate() || s1.getChannelCount() != s2.getChannelCount()){
            throw new IllegalArgumentException();
        }
        int size;

        if(s1.isInfinite() && s2.isInfinite()){
            size = -1;
        }else if(!s1.isInfinite() && !s2.isInfinite()){
            size = Math.min(s1.getSize(), s2.getSize());

        }else {
            if(!s1.isInfinite()){
                size = s1.getSize();
            }else {
                size = s2.getSize();
            }
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                if(s1.isInfinite() && s2.isInfinite()){
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public int getChannelCount() {
                return s1.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return s1.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return s1.getValueAt(channel, index) + s2.getValueAtValid(channel, index);
            }
        };
    }

    public static Signal mult(Signal s1, Signal s2) {
        if(s1 == null || s2 == null){
            throw  new NullPointerException();
        }

        if(s1.getSampleRate() != s2.getSampleRate() || s1.getChannelCount() != s2.getChannelCount()){
            throw new IllegalArgumentException();
        }
        int size;

        if(s1.isInfinite() && s2.isInfinite()){
            size = -1;
        }else if(!s1.isInfinite() && !s2.isInfinite()){
            size = Math.min(s1.getSize(), s2.getSize());

        }else {
            if(!s1.isInfinite()){
                size = s1.getSize();
            }else {
                size = s2.getSize();
            }
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                if(s1.isInfinite() && s2.isInfinite()){
                    return true;
                }else {
                    return false;
                }
            }

            @Override
            public int getSize() {
                return size;
            }

            @Override
            public int getChannelCount() {
                return s1.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return s1.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return s1.getValueAt(channel, index) * s2.getValueAtValid(channel, index);
            }
        };
    }

    public static Signal append(List<Signal> signals) {
        if(signals == null){
            throw new NullPointerException();
        }
        if(signals.isEmpty()){
            throw new IllegalArgumentException();
        }

        int sampleRate = signals.get(0).getSampleRate();
        int channelCount = signals.get(0).getChannelCount();

        for(int i = 0; i < signals.size() -1; i++){
            if(signals.get(i).isInfinite()){
                throw new IllegalArgumentException();
            }
        }

        for (Signal signal : signals) {
            if (signal.getChannelCount() != channelCount || signal.getSampleRate() != sampleRate) {
                throw new IllegalArgumentException();
            }
        }



        return new Signal() {
            @Override
            public boolean isInfinite() {
                return signals.get(signals.size() -1 ).isInfinite();
            }

            @Override
            public int getSize() {
                int size = 0;
                if(signals.get(signals.size() - 1).isInfinite()){
                    size = -1;
                }else {
                    for(Signal signal : signals){
                        size += signal.getSize();
                    }
                }
                return size;
            }

            @Override
            public int getChannelCount() {
                return channelCount;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                /*
                int size = 0;
                int index1 = index;
                Signal signal = signals.get(0);

                if(index >= signals.get(0).getSize()){

                    while (index > size){
                        for(Signal s : signals){
                            size += s.getSize();
                            signal = s;
                        }
                    }
                    size = size - signal.getSize();
                    index1 = index - size;
                }

                return signal.getValueAtValid(channel, index1);

                 */

                int sum = 0;

                for(Signal signal : signals){

                    if(index < signal.getSize() + sum){
                        return signal.getValueAt(channel, index - sum);
                    }
                    sum += signal.getSize();
                }

                return 0;
            }
        };



    }

    public static Signal append(Signal... signals) {
        if(signals == null){
            throw new NullPointerException();
        }
        if(signals.length < 1){
            throw new IllegalArgumentException();
        }

        int sampleRate = signals[0].getSampleRate();
        int channelCount = signals[0].getChannelCount();

        for(int i = 0; i < signals.length -1; i++){
            if(signals[i].isInfinite()){
                throw new IllegalArgumentException();
            }
        }

        for (Signal signal : signals) {
            if (signal.getChannelCount() != channelCount || signal.getSampleRate() != sampleRate) {
                throw new IllegalArgumentException();
            }
        }



        return new Signal() {
            @Override
            public boolean isInfinite() {
                return signals[signals.length-1].isInfinite();
            }

            @Override
            public int getSize() {
                int size = 0;
                if(signals[signals.length - 1].isInfinite()){
                    size = -1;
                }else {
                    for(Signal signal : signals){
                        size += signal.getSize();
                    }
                }
                return size;
            }

            @Override
            public int getChannelCount() {
                return channelCount;
            }

            @Override
            public int getSampleRate() {
                return sampleRate;
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                int sum = 0;

                for(Signal signal : signals){

                    if(index < signal.getSize() + sum){
                        return signal.getValueAt(channel, index - sum);
                    }
                    sum += signal.getSize();
                }

                return 0;
            }
        };
    }

    public static Signal translate(List<Double> distances, Signal signal) {
        if(signal == null){
            throw new NullPointerException();
        }

        if(distances.size() != signal.getChannelCount()){
            throw new IllegalArgumentException();
        }

        return new Signal() {
            @Override
            public boolean isInfinite() {
                return signal.isInfinite();
            }

            @Override
            public int getSize() {
                return signal.getSize();
            }

            @Override
            public int getChannelCount() {
                return signal.getChannelCount();
            }

            @Override
            public int getSampleRate() {
                return signal.getSampleRate();
            }

            @Override
            public double getValueAtValid(int channel, int index) {
                return signal.getValueAtValid(channel, index) + distances.get(channel);
            }
        };
    }

    public static Signal fromPath(List<Point> points, double frequency, int sampleRate) {
        if(frequency <= 0 || sampleRate <= 0){
            throw new IllegalArgumentException();
        }

        if(points.isEmpty() || points.size() == 1){
            throw new IllegalArgumentException();
        }

        double duration = 1 / frequency;

        List<Line> lines = new ArrayList<>();

        for(int i = 0; i < points.size() - 1; i++){
            lines.add(new Line(points.get(i), points.get(i + 1)));
        }

        System.out.println(lines);


        List<Double> lineLengths = new ArrayList<>();
        double pathLength = 0;
        for (Line line : lines) {
            lineLengths.add(line.length());
            pathLength += line.length();
        }
        System.out.println(lineLengths);
        System.out.println(pathLength);


        List<Double> normalizedLineLengths = new ArrayList<>();
        for(Double length : lineLengths){
            normalizedLineLengths.add(length / pathLength);
        }
        System.out.println(normalizedLineLengths);


        List<Integer> pointsPerLine = new ArrayList<>();

        for(Double nLen : normalizedLineLengths){
            double lDur = duration * nLen;
            System.out.println(lDur);

            double point = lDur * sampleRate;

            int result = (int) point;

            pointsPerLine.add(result);
        }
        System.out.println(pointsPerLine);


        List<Point> interpolatedPoints = new LinkedList<>();
        for(int i = 0; i < lines.size(); i++){
            int numPoints = pointsPerLine.get(i);

            List<Integer> indices = new ArrayList<>();
            for(int j = 0; j < numPoints; j++){
                indices.add(j);
            }

            List<Double> lineProgress = new ArrayList<>();
            for(int num : indices){
                lineProgress.add(((double) num / (double) numPoints));
            }

            List<Point> interpolatedPointsOfLine = new ArrayList<>();
            for(double num : lineProgress){
                interpolatedPointsOfLine.add(lines.get(i).getPointAt(num));
            }

            interpolatedPoints.addAll(interpolatedPointsOfLine);
        }

        double[] xCoordinates = new double[interpolatedPoints.size()];
        double[] yCoordinates = new double[interpolatedPoints.size()];
        for(int i = 0; i < interpolatedPoints.size(); i++){
            xCoordinates[i] = interpolatedPoints.get(i).getX();
            yCoordinates[i] = interpolatedPoints.get(i).getY();
        }
        Signal left = fromValues(xCoordinates, sampleRate);
        Signal right = fromValues(yCoordinates, sampleRate);

        return stereoFromMonos(left, right);



    }

    /* Optional */
    public static Signal myCoolSignal() {
        return null;
    }


    public static void main(String[] args) {
       //append(wave(Math::sin, 3, 2, 30), rampUp(1, 30), rampDown(2, 30)): getValueAtValid(0, 61) returned a wrong value ==> expected: <0.034482758620689655> but was: <0.0>
        Signal wave = SignalFactory.wave(Math::sin, 3, 2,30);
        Signal rampUp = SignalFactory.rampUp(1, 30);
        Signal rampDown = SignalFactory.rampDown(2, 30);
        Signal append = SignalFactory.append(wave,rampUp, rampDown);
        System.out.println(append.getValueAtValid(0,61));

       // Signal fromPath = SignalFactory.fromPath(List.of(new Point(0,0), new Point(1,1)), 1.000000, 100);
        //System.out.println(fromPath.getSize());

        Signal fromPath1 = fromPath(List.of(new Point(-0.056663, 1.159468), new Point(-0.350207, 0.515584), new Point(0.348358, 0.393947), new Point(-0.195850, 0.399093), new Point(-0.291129, 0.364192)), 0.337251, 617);

        System.out.println(fromPath1.getSize());
    }
}
