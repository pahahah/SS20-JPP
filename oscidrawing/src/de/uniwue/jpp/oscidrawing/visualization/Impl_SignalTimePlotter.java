package de.uniwue.jpp.oscidrawing.visualization;

import de.uniwue.jpp.oscidrawing.Signal;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

public class Impl_SignalTimePlotter implements SignalTimePlotter {

    private int width;
    private int height;
    private double valScale;
    private double timeScale;
    private BufferedImage bimage;
    Graphics2D graphics2D;

    public Impl_SignalTimePlotter(int width, int height, double valScale, double timeScale, Color bgcol, Color axiscol){

        this.width = width;
        this.height = height;
        this.valScale = valScale;
        this.timeScale = timeScale;

        bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        graphics2D = (Graphics2D) bimage.getGraphics();

        graphics2D.setColor(bgcol);
        graphics2D.fillRect(0,0,bimage.getWidth(),bimage.getHeight());

        graphics2D.setColor(axiscol);
        graphics2D.drawLine(0, height/2, width, height/2);
        graphics2D.dispose();


    }


    @Override
    public int sampleIndexToImageXCoord(int sampleIndex, int sampleRate) {

        int in_min = 0;
        double in_max = sampleRate * this.timeScale - 1;
        int out_min = 0;
        int out_max = this.width - 1;

        return (int)((sampleIndex - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
    }

    @Override
    public int signalValToImageYCoord(double val) {
        double in_min = valScale;
        double in_max = -valScale;
        int out_min = 0;
        int out_max = this.height - 1;

        double result = (val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        return (int)result;
    }

    @Override
    public void drawSignalAt(Signal signal, int channel, int index, Color col) {

        int x = sampleIndexToImageXCoord(index, signal.getSampleRate());
        int y = signalValToImageYCoord(signal.getValueAtValid(channel, index));

        Graphics2D g2d = bimage.createGraphics();

        g2d.setColor(col);
        g2d.drawLine(x,y,x,y);
        g2d.dispose();


    }

    @Override
    public void drawSignal(Signal signal, int channel, Color col) {
        Graphics2D g2d = bimage.createGraphics();
        g2d.setColor(col);


        for(int i = 0; i < signal.getSize(); i++){
            int x = sampleIndexToImageXCoord(i, signal.getSampleRate());
             int y = signalValToImageYCoord(signal.getValueAtValid(channel, i));

            g2d.drawLine(x, y, x, y);

        }
        g2d.dispose();



    }

    @Override
    public void drawSignal(Signal signal, Color... colors) {

        if(colors.length != signal.getChannelCount()){
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < signal.getChannelCount(); i++){
            drawSignal(signal, i, colors[i]);
        }

    }

    @Override
    public BufferedImage getImage() {

        return bimage;
    }


}
