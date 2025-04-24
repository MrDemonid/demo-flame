package mr.demonid.graphics;


public interface Palette {

    void setPalette(byte[] palette);
    byte[] getPalette();

    void setColor(int index, int red, int green, int blue);
    int getColor(int index);
}
