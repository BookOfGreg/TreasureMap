import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import java.util.Scanner;
/**
 * Write a description of class ItemHandler here.
 * 
 * @author AngryPirates, based on TestReader by Steve Townsend and Jim Hunter, and TextFileHandler by Jim Hunter.
 * @version 0.n
 */
public abstract class FileHandler
{
    public static ArrayList<Item> createAllItems()
    {
        ArrayList<Item> itemList;
        boolean open = false;
        String currentLine;
        itemList = new ArrayList<Item>();
        BufferedReader reader;

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
                        double price = Double.parseDouble(s.next());
                        Item myItem = new Item(name,price);
                        itemList.add(myItem);
                        currentLine = readLine(open,reader);
                    }
                    catch(Exception e){
                        System.err.println("You either chose the wrong file, or your formatting is inconsistent.");
                        System.err.println("Please check that you have selected the correct file, and that it is CSV.");
                        return null;
                    }
                }
            }
            catch (FileNotFoundException e) { 
                System.err.println("TextReader: Problem opening file for reading: "+ file.getAbsolutePath());
            }
        }
        return itemList;
    }

    /**
     * This was taken from SimpleIO
     * 
     * Use a file choser to allow the user to select a file
     * @return file   the selected file or null if user cancels out
     */
    private static File getFile() {
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
    private static String readLine(boolean open, BufferedReader reader)
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