package de.uniwue.jpp.oscidrawing.visualization;

import de.uniwue.jpp.oscidrawing.Signal;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Impl_Osci2DPlotter implements Osci2DPlotter {
    private int size;
    private double scale;
    private BufferedImage bimage;

    public Impl_Osci2DPlotter(int size, double scale, Color bgcol){
        this.size = size;
        this.scale = scale;

        bimage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bimage.createGraphics();
        graphics2D.setColor(bgcol);
        graphics2D.fillRect(0,0,bimage.getWidth(),bimage.getHeight());



    }

    @Override
    public int signalValToImageXCoord(double val) {
        double in_min = -scale;
        double in_max = scale;
        int out_min = 0;
        int out_max = size - 1;
        return (int) ((val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
    }

    @Override
    public int signalValToImageYCoord(double val) {
        double in_min = scale;
        double in_max = -scale;
        int out_min = 0;
        int out_max = size - 1;
        return (int) ((val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min);
    }

    @Override
    public void drawSignalAt(Signal signal, int index, Color col) {

        if(signal.getChannelCount() != 2){
            throw new IllegalArgumentException();
        }

        int x = signalValToImageXCoord(signal.getValueAtValid(0, index));
        int y = signalValToImageYCoord(signal.getValueAtValid(1, index));

        Graphics2D g2d = bimage.createGraphics();

        g2d.setColor(col);

        g2d.drawLine(x,y,x,y);
        g2d.dispose();
    }

    @Override
    public void drawSignal(Signal signal, Color col) {

        if(signal.isInfinite()){
            throw new IllegalArgumentException();
        }
        if(signal.getChannelCount() != 2){
            throw new IllegalArgumentException();
        }

        Graphics2D g2d = bimage.createGraphics();

        g2d.setColor(col);

        for(int i = 0; i < signal.getSize(); i++){
            int x = signalValToImageXCoord(signal.getValueAtValid(0, i));
            int y = signalValToImageYCoord(signal.getValueAtValid(1, i));

            g2d.drawLine(x,y,x,y);

        }
        g2d.dispose();
    }

    @Override
    public BufferedImage getImage() {
        return bimage;
    }
}
