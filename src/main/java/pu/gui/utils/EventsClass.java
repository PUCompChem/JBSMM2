package pu.gui.utils;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by gogo on 21.8.2017 г..
 */
public class EventsClass {
    public static ComponentListener getComponentListener() {
        return componentListener;
    }

    public static void setComponentListener(ComponentListener componentListener) {
        EventsClass.componentListener = componentListener;
    }

    static ComponentListener componentListener = new ComponentListener() {

        public void componentHidden(ComponentEvent arg0) {
            // TODO Auto-generated method stub

        }

        public void componentMoved(ComponentEvent arg0) {
            // TODO Auto-generated method stub

        }
        public void componentResized(ComponentEvent arg0) {
                System.out.println("adfs");
        }

        public void componentShown(ComponentEvent arg0) {
            // TODO Auto-generated method stub

        }
    };

}
