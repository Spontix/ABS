package menuBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu extends MenuItem {

    private ArrayList<MenuItem> arrayOfMenusItems=null;
    private final Boolean RUN_MENU = true;
    private MenuType menuType;


    public MainMenu(String title, MenuType type)
    {
        super(title);
        menuType = type;
        arrayOfMenusItems = new ArrayList<MenuItem>();
    }

    public MenuType getMenuType()
    {
        return menuType;
    }

    public ArrayList<MenuItem> getSetOfMenusItem()
    {
        return arrayOfMenusItems;
    }

    public void insertToMenu(MenuItem menuItem)
    {
        arrayOfMenusItems.add(menuItem);
    }

    public void show() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        String menuDescription = null;
        int userInput = 0;

        if (arrayOfMenusItems.size() != 0) {
            menuDescription = getMenuDescription();

            while (RUN_MENU) {
               System.out.println(menuDescription);
               userInput = getUserInput();

               if (userInput == 8) {
                   break;
               }
               else {
                   activeMenuItem(arrayOfMenusItems.get(userInput-1));
               }
            }
        }

    }

    private String getMenuDescription()
    {
        String description = new String();
        int index = 1;
        description=getMenuTitle()+"\n";

        for(MenuItem menuItem : arrayOfMenusItems) {
            description=description+index+"."+menuItem.getMenuTitle()+"\n";
            index++;
        }
        return description;
    }

    private int getUserInput() {

        String userInput =null;
        Boolean isValid = false;

        while (!isValid) {//////////////
            System.out.println("Please select an option:");
            userInput = new Scanner(System.in).nextLine();
            isValid = intParseHelper(userInput);
            if (!isValid)
            {
                System.out.println("wrong input");
            }
        }

        return Integer.parseInt(userInput);
    }

    private Boolean intParseHelper(String userInput) {
        int number;
        try {
            number = Integer.parseInt(userInput);
        }
        catch (NumberFormatException ex){
        return false;
    }
        return number <= arrayOfMenusItems.size() && number >= 0;
    }

    private void activeMenuItem(MenuItem menuItem) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (menuItem instanceof MainMenu)
        {
            ((MainMenu) menuItem).show();
        }
            else
        {
            menuItem.ActivateWhenActionIsCommitted();
        }
    }

}



