package DemoGame;

import org.NayaEngine.Tooling.Window;

import java.util.*;
import java.io.*;

/**
 * @author Theo willis
 * @version 1.0.0
 * ~ project outline here ~
 * @Javadoc
 */
public class DemoMain {
    public DemoMain() {

    }

    public static void main(String[] args) {
        Window.InitWindow(640, 480, "2D graphics inDev edition", new DemoRenderer());
    }
}
