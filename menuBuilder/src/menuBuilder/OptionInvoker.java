package menuBuilder;

import java.lang.reflect.InvocationTargetException;

public interface OptionInvoker {

    void reportAction() throws InvocationTargetException, InstantiationException, IllegalAccessException;
}
