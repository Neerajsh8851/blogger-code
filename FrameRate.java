package jgame.util;

public class FrameRate {

    private String fps;  
    private long lastTime;
    private int count;
    private long deltaTime;

    public void start() {
        // get the current time and store it as last time
        lastTime = System.currentTimeMillis();

        fps = "FPS: 0";  // at this time fps is 0
    }

    public void calculate() {
        long currentTime = System.currentTimeMillis();
        deltaTime += currentTime - lastTime;

        count++;
        if (deltaTime > 1000) {
            deltaTime -= 1000;
            fps = "FPS: " + count;
            count = 0;
        }
        //Now current time is last time for the next call to calculate
        lastTime = currentTime;
    }

    // getter method to return fps
    public String getFps() {
        return fps;
    }
}
