package menuBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class MenuItem {

    private String menuTitle;
    private final ArrayList<OptionInvoker> setOfOptionInvoker;

    public MenuItem(String title) {
        menuTitle = title;
        setOfOptionInvoker = new ArrayList<OptionInvoker>();
    }

    public String getMenuTitle() {
        return menuTitle;
    }


    public void AddListener(OptionInvoker actionOptionInvoker) {
        setOfOptionInvoker.add(actionOptionInvoker);
    }

    public void ActivateWhenActionIsCommitted() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        notifyAllListeners();
    }

    private void notifyAllListeners() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (setOfOptionInvoker.size() == 0) {
            /*throw new AppDomainUnloadedException("Error:This choice do nothing");*/
        }
        for (OptionInvoker optionInvoker : setOfOptionInvoker) {
            optionInvoker.reportAction();
        }
    }
}

