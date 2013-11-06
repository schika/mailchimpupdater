/**
 * Created with IntelliJ IDEA.
 * User: DarkShade
 * Date: 10.09.13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String... args) {
        try {
            new Thread(new Updater()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
