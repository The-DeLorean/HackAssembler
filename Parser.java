import java.io.*;

/*
Reads and parses an instruction
*/

public class Parser
{
   private BufferedReader br = null;
   
   private boolean linesRemain = true;
   private String currentInstruction;
   
   
   // Constructor: Creates a Parser and opens the source text file
   public Parser(String program)
   {

      try {
         // Create a BufferedReader using a FileReader to read the file
         br = new BufferedReader(new FileReader(program));
            
         String line;
         while ((line = br.readLine()) != null) {
            System.out.println(line);  // Print each line to the console
         }
      
      } 
      catch (IOException e) {
           e.printStackTrace();
      } 
      finally {
         try {
             if (br != null) {
                 br.close();  // Close the BufferedReader to release resources
             }
         } 
         catch (IOException e) {
             e.printStackTrace();
         }
      }
   }
   
   // Checks if there is more work to do
   public boolean hasMoreLines()
   {
      return linesRemain;
   }
   
   // Returns the type of the current instruction, as a constant:
   public String instructionType()
   {
      if (currentInstruction.charAt(0) == '@')
      {
         return "A_INSTRUCTION";
      }
      else if (currentInstruction.charAt(0) == '(')
      {
         return "L_INSTRUCTION";
      }
      else
      {
         return "C_INSTRUCTION";
      }
         
   }
   
   // Returns the instruction’s symbol (string)
   //Used only if A or L instruction is current

   public String symbol()
   {
      if (currentInstruction.charAt(0) == '@')
      {
         return currentInstruction.substring(1);
      }
      else
      {
         return currentInstruction.substring(1, currentInstruction.length()-1);
      } 
   }
   
   // Returns the instruction’s dest field (string)
   public String dest()
   {
      // Finding the substring that is left of the equals sign
      return currentInstruction.substring(0,currentInstruction.indexOf('='));
   }
   
   // Returns the instruction’s comp field (string)
   public String comp()
   {  
      // Check if equals is present
      if(currentInstruction.contains("="))
      {
         // Finding the substring that is in between equals and semicolon
         return currentInstruction.substring(currentInstruction.indexOf('=')+1, currentInstruction.indexOf(';'));
      }
      // No equals sign
      else
      {
         return currentInstruction.substring(0,currentInstruction.indexOf(';'));
      }
   }
   
   // Returns the instruction’s jump field (string)
   public String jump()
   {
      // Check if semicolon is present
      if(currentInstruction.contains(";"))
      {
         // Finding the substring that is after  semicolon
         return currentInstruction.substring(currentInstruction.indexOf(';')+1);
      }
      // No semicolon means no jump field 
      else
      {
         return null;
      }
   }
   
   
   // Gets the next instruction and makes it the current instruction (string)
   public String advance()
   {
      
      try{
         currentInstruction = br.readLine();
      }
      catch (IOException e) {
      }
      
      if(currentInstruction == null)
      {
         linesRemain = false;
      }
      
      
      return currentInstruction;
   }
}
   