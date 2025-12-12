
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**************************************************************************************************
 * 
 * Class Name: Sequential File
 * Authors name: PC, RCR, LD - Group 02
 * Creation date: 28/09/2025
 * Class version: 1.0
 * Class description: Implements 2. of introductory assignment of Data Structures. This is an 
 * implementation using generics to work with sequential files. It needs the path and separator
 * in order to parse the data on a CVS file.
 * 
***************************************************************************************************
*/

public class SequentialFile <T extends iface>{
    String nameOfFile, separator;
    Scanner f;

    public SequentialFile(String nameOfFile, String separator) throws IOException{
        this.nameOfFile = nameOfFile;
        this.separator = separator;
        this.f = new Scanner(new File(nameOfFile));
    }

    public void closeFile(){
        f.close();
    }

    public void read(T t){
        // Takes a line of the file, splits it every time the separator is found and
        // calls readData, which is found in iface. 

        String line = f.nextLine();
        String[] data = line.split(separator);
        t.readData(data);
    }

    public void skipLine(){
        f.nextLine();
    }

    public boolean checkEOF(){
        return f.hasNext();
    }
}