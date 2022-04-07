package application;

import application.console.Console;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        UiType ui=new Console();
        ui.Run();
    }
}
