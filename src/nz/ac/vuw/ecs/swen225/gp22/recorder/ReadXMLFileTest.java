package nz.ac.vuw.ecs.swen225.gp22.recorder;

/**
 * This class tests a replay saved to xml file
 */
public class ReadXMLFileTest {

    public static void main(String argv[]) {

        Replay ReadReplay = Replay.readXML();
        System.out.println(ReadReplay.getLevel() + "   level");
        System.out.println(ReadReplay.getName() + "    name");
        ReadReplay.getMoves().stream().forEach(r -> {
            System.out.println(r.getName() + "(" + r.getTime() + ")");
        });

    }
}