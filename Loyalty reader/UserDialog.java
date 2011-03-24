
import javax.swing.*;

/**
 * Defines simple dialog boxes for interacting with the user, by extending JOptionPane. 
 * 
 * It is able to show a simple string message, get an integer, float, double, boolean or string from the user, or get the user to confirm "yes" or "no". 
 * User input is converted to the appropriate type. If an inappropriate type is input, an error dialog box is displayed, and a default value returned. 
 * 
 * @version 1.2 07/12/03
 * @author (adapted from an original example by Ken Brown)
 */

public class UserDialog extends JOptionPane {
    
    /**
     * Displays a simple String object.
     */
    public void showMessage(String output) 
    {
       showMessageDialog(null, output);
    }

    /**
     * Displays a message and returns an int provided 
     * by the user. If the user does not provide an int, an error 
     * dialog box is displayed, and a default value returned.
     */
    public int getInt(String output) 
    {
        String inputStr = showInputDialog(null, output);
        try 
        {
            return Integer.parseInt(inputStr);
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(null, inputStr + " is not an integer", "ERROR", ERROR_MESSAGE);
            return -1;
        }
    }
    
    /**
     * Displays a message and returns a double 
     * provided by the user. If the user does not provide a double, an 
     * error dialog box is displayed, and a default value returned.
     */
    public double getDouble(String output) 
    {
        String inputStr = showInputDialog(null, output);
        try 
        {
            return Double.parseDouble(inputStr);
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(null, inputStr + " is not a double", "ERROR", ERROR_MESSAGE);
            return -1.0;
        }
    }

    /**
     * Displays a message and returns a float 
     * provided by the user. If the user does not provide a float, an 
     * error dialog box is displayed, and a default value returned.
     */
    public float getFloat(String output) 
    {
        String inputStr = showInputDialog(null, output);
        try 
        {
            return Float.parseFloat(inputStr);
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(null, inputStr + " is not a float", "ERROR", ERROR_MESSAGE);
            return (float)-1.0;
        }
    }

    /**
     * Displays a message and asks the user to 
     * click "yes", "no" or "cancel". If the user clicks "cancel", 
     * "false" is returned.
     *
     * Implemented using showConfirmDialog.
     */
    public boolean getBoolean(String output) 
    {
        int inputInt = showConfirmDialog(null, output);
        if (inputInt == 0)
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }

    /* original body of getBoolean:
     * Displays a message and returns a boolean 
     * provided by the user. If the user does not provide a boolean, 
     * an error dialog box is displayed, and a default value returned.
     *
     * String inputStr = showInputDialog(null, output);
     * return Boolean.valueOf(inputStr).booleanValue();
    */

   /**
     * Displays a message and returns a String 
     * provided by the user.
     */
    public String getString(String output) 
    {
        return showInputDialog(null, output);
    }

    /**
     * Displays a text message in a 10x20 text box inside the dialog box
     */
    public void showTextMessage(String message) 
    {
        JTextArea outputArea = new JTextArea(10,20);
        JScrollPane scroller = new JScrollPane(outputArea);
        outputArea.setText(message);
        outputArea.setEditable(false);
        showMessageDialog(null, scroller);
    }

    /**
     * Displays a text message in a text box inside the dialog box
     * Text box dimensions set by the user
     */
    public void showTextMessage(String message, int rows, int cols) 
    {
        JTextArea outputArea = new JTextArea(rows, cols);
        JScrollPane scroller = new JScrollPane(outputArea);
        outputArea.setText(message);
        outputArea.setEditable(false);
        showMessageDialog(null, scroller);
    }

    /**
     * Displays a text message in a text box inside the dialog box,
     * and a question,  and returns an int provided by the user.
     * If the user does not provide an int, an error 
     * dialog box is displayed, and a default value (-1) returned.
     */
    public int getIntText(String text, String question) 
    {
        Object display = createTextDisplay(text, question);
        String inputStr = showInputDialog(null, display);
        try 
        {
            return Integer.parseInt(inputStr);
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(null, inputStr + " is not an integer","ERROR", ERROR_MESSAGE);
            return -1;
        }
    }

    /**
     * Displays a text message in a text box inside the dialog box,
     * and a question,  and returns a double provided by the user.
     * If the user does not provide a double, an error 
     * dialog box is displayed, and a default value (-1.0) returned.
     */
    public double getDoubleText(String text, String question) 
    {
        Object display = createTextDisplay(text, question);
        String inputStr = showInputDialog(null, display);
        try 
        {
            return Double.parseDouble(inputStr);
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(null, inputStr + " is not a double", "ERROR", ERROR_MESSAGE);
            return -1.0;
        }
    }

    /**
     * Displays a text message in a text box inside the dialog box,
     * and a question,  and returns a float provided by the user.
     * If the user does not provide a float, an error 
     * dialog box is displayed, and a default value (-1.0) returned.
     */
    public float getFloatText(String text, String question) 
    {
        Object display = createTextDisplay(text, question);
        String inputStr = showInputDialog(null, display);
        try 
        {
            return Float.parseFloat(inputStr);
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(null, inputStr + " is not a float", "ERROR", ERROR_MESSAGE);
            return (float)-1.0;
        }
    }

    /**
     * Displays a text message in a text box inside the dialog box,
     * and a question,  and returns a boolean provided by the user.
     * If the user does not provide a boolean, a default value 
     * (false) returned.
     */
    public boolean getBooleanText(String text, String question) 
    {
        Object display = createTextDisplay(text, question);
        int inputInt = showConfirmDialog(null, display);
        if (inputInt == 0)
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }

    /**
     * Displays a text message in a text box inside the dialog box,
     * and a question,  and returns a string provided by the user.
     * If the user does not provide a string, an error 
     * dialog box is displayed, and a default value (null) returned.
     */
    public String getStringText(String text, String question) 
    {
        Object display = createTextDisplay(text, question);
        return showInputDialog(null, display);
    }

    /**
     * Creates an array of Objects for display in a dialog box.
     */
    private Object createTextDisplay(String text, String question) 
    {
        //array of objects to be displayed
        Object display[] = new Object[2];
        //The formatted text message
        JTextArea outputArea = new JTextArea(10,20);
        JScrollPane scroller = new JScrollPane(outputArea);
        outputArea.setText(text);
        outputArea.setEditable(false);
        //set the formatted message as first object, then the question
        display[0] = scroller;
        display[1] = question;
        //return the array
        return display;
    }

    /**
     * Displays a range of possible strings to the user, and asks the
     * user to select one. If the user does not select one, the
     * null String will be returned.
     */
    public String selectString(String message, String options[]) 
    {
        return (String) selectObject(message, options);
    }

    /**
     * Displays a range of possible objects to the user, and asks the
     * user to select one. If the user does not select one, the
     * null String will be returned.
     * This assumes each object has implemented a toString() method.
     */
    public Object selectObject(String message, Object possibleValues[]) 
    {
        return JOptionPane.showInputDialog(null, message, "Select one", JOptionPane.PLAIN_MESSAGE, 
                                           null, possibleValues, possibleValues[0]);
    }

    /**
     * Displays a range of possible strings to the user, and asks the
     * user to select one. The index of the string in the array of
     * options will be returned, or -1 if the user doesn't select one.
     */
    public int selectIndex(String message, Object options[]) 
    {
        return JOptionPane.showOptionDialog(null, message, "Select one", YES_NO_OPTION, 
                                            JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

}