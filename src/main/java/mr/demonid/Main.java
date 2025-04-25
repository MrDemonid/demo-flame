package mr.demonid;

public class Main {
    public static void main(String[] args) {
        FullscreenPixelApp app = new FullscreenPixelApp(70);
        new Thread(app).start();
    }
}