package de.uniwue.jpp.oscidrawing;

import java.util.ArrayList;
import java.util.List;

public abstract class Signal {

    /*
    - 신호는 시간에 따른 하나 이상의 곡선을 나타냄
    - 파형은 연속적으로 저장되지 않음. 대신 곡선은 신호가 특정시점 (smaple point)에서 가정하는 값(double 타입)으로 정의
    - sample point는 시간이 지남에 따라 등거리로 분포
    - sample rate는 신호의 1초에 나타내는 sample point수를 나타냄. sample rate가 높을수록 신호를 더 정확하게 설명할 수 있지만 물론 더 많은 저장공간이 필요.
    - 유한신호와 무한신호 구별
       - 둘 다 음수값의 sample point로 정의되지 않음. (항상 양수)
       - 크기가 n인 유한신호는 0에서 n-1 까지의 sample point index에 대해 정의됨
       - 무한 신호는 0보다 크거나 같은 모든 샘플포인트 인덱스에 대해 정의됨.
    - 이미 언급되었듯이 신호는 여러 값 곡선을 설명할 수 있음. 하나의 진행을 채널이라고 하고, n개의 채널이 있는 신호에는 채널 인덱스가 0에서 n-1까지 유효하다.
    */


    public abstract boolean isInfinite();

    public abstract int getSize();

    public abstract int getChannelCount();

    public abstract int getSampleRate();

    public abstract double getValueAtValid(int channel, int index);

    public double getDuration() {
        if(isInfinite()){
            return -1;
        }else {
            double size = getSize();
            double rate = getSampleRate();
            return size / rate;
        }
    }

    public double getValueAt(int channel, int index) {

        if(channel < 0 || channel >= getChannelCount()){
            return 0;
        }

        if(isInfinite()){
            if(index < 0 ){
                return 0;
            }
        }else {
            if(index < 0 || index >= getSize() ){
                return 0;
            }
        }



        return getValueAtValid(channel, index);

    }






}
