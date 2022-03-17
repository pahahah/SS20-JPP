package de.uniwue.jpp.oscidrawing.visualization;

import java.awt.Color;
import java.awt.image.BufferedImage;

import de.uniwue.jpp.oscidrawing.Signal;

public interface Osci2DPlotter {
    public int signalValToImageXCoord(double val);

    public int signalValToImageYCoord(double val);

    public void drawSignalAt(Signal signal, int index, Color col);

    public void drawSignal(Signal signal, Color col);

    public BufferedImage getImage();

    public static Osci2DPlotter createImageCreator(int size, double scale, Color bgcol) {
        return new Impl_Osci2DPlotter(size, scale, bgcol);
    }
}
