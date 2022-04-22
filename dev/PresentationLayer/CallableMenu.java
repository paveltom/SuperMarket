package PresentationLayer;

import java.util.Map;
import java.util.concurrent.Callable;

public class CallableMenu {

    private final boolean callable;
    private final Callable<Integer> method;
    private final Map<Integer, CallableMenu> menuMap;
    private final String[] menuNames;

    public CallableMenu(Callable<Integer> function){
        this.callable = true;
        this.method = function;
        this.menuMap = null;
        this.menuNames = null;
    }

    public CallableMenu( Map<Integer, CallableMenu> menuMap, String[] menuNames){
        this.callable = false;
        this.menuMap = menuMap;
        this.menuNames = menuNames;
        this.method = null;
    }

    public Map<Integer, CallableMenu> getMenu(){return menuMap;}
    public Callable<Integer> getMethod(){return method;}
    public String[] getMenuNames(){return this.menuNames;}
    public boolean isMethod(){return this.callable;}

}
