package de.uniwue.jpp.oscidrawing.io;

import de.uniwue.jpp.oscidrawing.Signal;


import java.io.*;
import java.nio.ByteBuffer;


public class AudioExporter {

    public static boolean writeChannelToFile(String path, Signal signal, int channel) {

        if(signal.isInfinite()){
            throw new IllegalArgumentException("Signal is infinite");
        }

        try {
            FileOutputStream fos = new FileOutputStream(path + ".raw");
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(fos));
            for(int i = 0; i < signal.getSize(); i++){
                float var = (float)signal.getValueAtValid(channel, i);

                outputStream.writeFloat(var);
            }
            outputStream.close();


        } catch (IOException e) {
            return false;
        }

        return true;

    }

    public static boolean writeStereoToFiles(String path, Signal signal) {
        if(signal.isInfinite()){
            throw new IllegalArgumentException("signal is infinite");
        }

        if(signal.getChannelCount() != 2){
            throw new IllegalArgumentException();
        }

        try{
            FileOutputStream fosLeft = new FileOutputStream(path + "left.raw");
            DataOutputStream dosLeft = new DataOutputStream(new BufferedOutputStream(fosLeft));
            FileOutputStream fosRight = new FileOutputStream(path + "right.raw");
            DataOutputStream dosRight = new DataOutputStream(new BufferedOutputStream(fosRight));
            ByteBuffer buffer;
            ByteBuffer buffer1;
            for(int i = 0; i < signal.getSize(); i++){
                float var1 = (float)signal.getValueAtValid(0, i);
                dosLeft.writeFloat(var1);
                float var2 = (float)signal.getValueAtValid(1, i);

                dosRight.writeFloat(var2);
            }
            dosLeft.close();
            dosRight.close();

        } catch (IOException e) {
            return false;
        }


        return true;
    }

}
