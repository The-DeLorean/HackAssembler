import java.io.*;
import java.io.IOException;
import java.util.HashMap;

/*
Input (Prog.asm): a text file containing a
sequence of lines, each being a string
representing a comment, an A-instruction,
a C-instruction, or a label declaration

Output (Prog.hack): a text file containing
a sequence of lines, each being a string
of sixteen 0 and 1 characters

Usage:
$ java HackAssembler Prog.asm
Creates a Prog.hack file, containing the translated Hack program.
*/

public class HackAssembler
{
   static HashMap<String, Integer> symbolTable;
   
   public static void main(String[] args)
   {
      /* Initialize
      Opens the input file (Prog.asm) and gets ready to process it
      Constructs a symbol table, and adds to it all the predefined symbols */
      Parser parser = new Parser("Prog.asm");
      SymbolTable table = new SymbolTable();
      Code code = new Code();
      
      /* First pass
      Reads the program lines, one by one
      focusing only on (label) declarations.
      Adds the found labels to the symbol table*/
      
      
      /* Second pass (main loop)
      (starts again from the beginning of the file)
      While there are more lines to process:
      Gets the next instruction, and parses it
      If the instruction is @ symbol
      If symbol is not in the symbol table, adds it to the table
      Translates the symbol into its binary value
      If the instruction is dest =comp ; jump
      Translates each of the three fields into its binary value
      Assembles the binary values into a string of sixteen 0’s and 1’s
      Writes the string to the output file.*/
      
      FileWriter fileWriter = null;
        
      try {
         // Create a File object for the given filename
         File file = new File("test.txt");
         
         // FileWriter constructor with 'false' will overwrite the file
         fileWriter = new FileWriter(file, false);  // 'false' means overwrite the file
         
         // The content to be written to the file
         String content = "testing, attention please";
         
         
         // Write the content to the file
         fileWriter.write(content);
      
      } 
      catch (IOException e) {
         // Handle any IO exceptions
         System.err.println("Error writing to file: " + e.getMessage());
      }
      
      finally {
         // Ensure the FileWriter is closed properly
         if (fileWriter != null) {
            try {
               fileWriter.close();
            }
            catch (IOException e) {
                 System.err.println("Error closing the FileWriter: " + e.getMessage());
            }
         }
      }
      
   }
   
   //this method 
   public String cToBin(String cin)
   {
      return "";
   }
}