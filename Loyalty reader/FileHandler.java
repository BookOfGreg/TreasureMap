import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import java.util.Scanner;
/**
 * Reads Items.txt then passes each line from the text file to the Item class.
 * 
 * @author AngryPirates, based on TestReader by Steve Townsend and Jim Hunter, and TextFileHandler by Jim Hunter.
 * @version 2011,03,11
 */
public class FileHandler
{
    private ArrayList<Item> items;
    private int current;
    private boolean open;
    private BufferedReader reader;
    private String currentLine;
    private File file;
    private final int JUMP_VALUE = 200;
    private UserDialog ud = new UserDialog();

    public static void main(String [ ] args)
    {
        new FileHandler();
    }

    public FileHandler()
    {
        createAllItems();
        accessLoyaltyFile();
        showFile();
    }

    private void accessLoyaltyFile()
    {
        ud.showMessage("Select Loyalty File.");
        try{
            file = getFile();
            try{
                reader = new BufferedReader(new FileReader(file));
            }
            catch(Exception e){
                //
            }
            open = true;
        }
        catch(Exception e){
            //
        }
    }

    private String getCurrent(int i)
    {
        for (int x = 0; x < i; x++){
            currentLine = readLine(open,reader);
        }
        currentLine = "";
        for (int x = 0; x < JUMP_VALUE; x++){
            String line = readLine(open,reader);
            System.out.println(line);
            if (line!=null){
                if (!line.equals("Start Store")){
                    currentLine += matchedId(line) + "\n";
                }
            }
        }
        return currentLine;
    }

    private String matchedId(String s)
    {
        for (Item i: items)
        {
            if (s.charAt(0) == "#".charAt(0))
            {
                return "customer " + s;
            }
            else if (i.getID().equals(s)){
                return i.toString();
            }
        }
        return "Unmatched Item" + s;
    }

    private boolean next(int i)
    {
        try{
            //skip over these lines
            for (int x = 0; x < i; x++){
                currentLine = readLine(open,reader);
            }
            //use these lines.
            for (int x = 0; x < JUMP_VALUE; x++){
                currentLine += readLine(open,reader);
            }
            try{
                reader = new BufferedReader(new FileReader(file));
            }
            catch(Exception e){
                //
            }
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    private boolean prev(int x)
    {
        if (x < 0){
            return false;
        }
        return true;

    }

    private void showFile()
    {
        current = 0;
        //getCurrent(current);
        String strings = null;

        String choice = ud.getStringText("Options","What do you want to do? Show current "+Integer.toString(JUMP_VALUE)+" ('current'), Show next "+Integer.toString(JUMP_VALUE)+" ('next') or show previous "+Integer.toString(JUMP_VALUE)+" ('prev')");
        while (!choice.equals("quit")){
            if ((choice.equals("next")) && (next(current))){
                current += JUMP_VALUE;
                strings = getCurrent(current);
                ud.showTextMessage(strings);
            }
            else if (choice.equals("next")){
                ud.showMessage("End of file");
                current += JUMP_VALUE;
                strings = getCurrent(current);
                if (strings != null){
                    ud.showTextMessage(strings);
                }
            }
            else if ((choice.equals("prev")) && (prev(current))){
                current -= JUMP_VALUE;
                strings = getCurrent(current);
                ud.showTextMessage(strings);
            }
            else if (choice == "prev"){
                ud.showMessage("Already at beginning of file");
            }
            else if (choice.equals("current")) {
                strings = getCurrent(current);
                ud.showTextMessage(strings);
            }
            else{
                ud.showMessage("Not a valid option, please type \"quit\", \"current\",\"next\" or \"prev\"");
            }
            try{
                reader = new BufferedReader(new FileReader(file));
            }
            catch(Exception e){
                //
            }
            choice = ud.getStringText("Options","What do you want to do? Show current "+Integer.toString(JUMP_VALUE)+" ('current'), Show next "+Integer.toString(JUMP_VALUE)+" ('next') or show previous "+Integer.toString(JUMP_VALUE)+" ('prev')");
        }
    }

    private void createAllItems()
    {
        ArrayList<Item> itemList;
        boolean open = false;
        String currentLine;
        itemList = new ArrayList<Item>();
        BufferedReader reader;
        ud.showMessage("Select Items List.");
        File file = getFile();
        if (file != null){
            try {
                // open new buffered reader for the specified file
                reader = new BufferedReader(new FileReader(file));
                open = true;
                currentLine = readLine(open,reader);
                while (currentLine != null){
                    Scanner s = new Scanner(currentLine).useDelimiter("\\s*,\\s*");
                    try{
                        String name = s.next();
                        String price = s.next();
                        String id = s.next();
                        Item myItem = new Item(name,price,id);
                        itemList.add(myItem);
                        currentLine = readLine(open,reader);
                    }
                    catch(Exception e){
                        System.err.println("You either chose the wrong file, or your formatting is inconsistent.");
                        System.err.println("Please check that you have selected the correct file, and that it is CSV.");

                    }
                }
            }
            catch (FileNotFoundException e) { 
                System.err.println("TextReader: Problem opening file for reading: "+ file.getAbsolutePath());
            }
        }
        items = itemList;
    }

    /**
     * This was taken from SimpleIO
     * 
     * Use a file choser to allow the user to select a file
     * @return file   the selected file or null if user cancels out
     */
    private File getFile() {
        File currentDirectory = null;
        //fix to make sure the file chooser is on top of the BlueJ window
        JFrame jf = new JFrame("TextFileHandler"); 
        jf.setVisible(true);
        jf.setAlwaysOnTop(true);
        jf.setAlwaysOnTop(false);
        JFileChooser fc = new JFileChooser();
        // use the current directory if there is one
        if (currentDirectory != null)
            fc.setCurrentDirectory(currentDirectory);
        int response = fc.showOpenDialog(null); // prompt user for filename
        jf.dispose();
        if (response == JFileChooser.APPROVE_OPTION){  // response ok
            currentDirectory = fc.getCurrentDirectory();
            return fc.getSelectedFile();
        }
        else{
            return null;
        }
    }

    /**
     * This was taken from SimpleIO.
     * 
     * Reads a complete line
     * @return  the remainder of the line as a string,
     *          null if the end-of-file is reached
     *          null if an I/O exception is raised
     */
    private String readLine(boolean open, BufferedReader reader)
    {
        if (open){
            try{
                return reader.readLine();
            } 
            catch (IOException e) {
                System.err.println("TextReader: IOException raised in readLine(): " +e);
                return null;
            }
        }
        else {
            System.err.println("TextReader: File is not open in readLine()");
            return null;
        }       
    }
}