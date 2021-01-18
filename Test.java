import jgame.util.FrameRate;
    
public class Test {
    public static void main(String[] args) {
        FrameRate fpsMeter = new FrameRate();
        fpsMeter.start();
        
            
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            fpsMeter.calculate();
            System.out.println(fpsMeter.getFps());
        }
    }
}
