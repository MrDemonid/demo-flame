package mr.demonid;

public class Main {
    public static void main(String[] args) {
        FullscreenPixelApp app = new FullscreenPixelApp(50);
        new Thread(app).start();
    }
}