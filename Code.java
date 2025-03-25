import java.util.*;
/* 
   Deals only with C-instructions: dest = comp ; jump
*/
public class Code
{
   // Returns the binary representation of the parsed dest field (string)
   public String dest(String tp)
   {
      if(tp == "na")
      {
         return "000";
      }
      String bin = new String();
      
      // Main switch
      switch (tp)
      {
         case("M"):
            bin = "001";
            break;
         
         case("D"):
            bin = "010";
            break;
         
         case("MD"):
            bin = "011";
            break;
         
         case("A"):
            bin = "100";
            break;
         
         case("AM"):
            bin = "101";
            break;
         
         case("AD"):
            bin = "110";
            break;
         
         case("ADM"):
            bin = "111";
            break;
         case("na"):
            bin = "000";
            break;
         default:
            break;
      }
      return bin;
   }
   
   //Returns the binary representation of the parsed comp field (string)
   public String comp(String tp)
   {
      String bin = new String();
      
      // Main switch
      switch (tp)
      {
         case("0"):
            bin = "0101010";
            break;
         
         case("1"):
            bin = "0111111";
            break;
         
         case("-1"):
            bin = "0111010";
            break;
         
         case("D"):
            bin = "0001100";
            break;
         
         case("A"):
            bin = "0110000";
            break;
         
         case("!D"):
            bin = "0001101";
            break;
         
         case("!A"):
            bin = "0110001";
            break;
         
         case("-D"):
            bin = "0001111";
            break;
         
         case("-A"):
            bin = "0110011";
            break;
         
         case("D+1"):
            bin = "0011111";
            break;
         
         case("A+1"):
            bin = "0110111";
            break;
         
         case("D-1"):
            bin = "0001110";
            break;
         
         case("A-1"):
            bin = "0110010";
            break;
         
         case("D+A"):
            bin = "0000010";
            break;
         
         case("D-A"):
            bin = "0010011";
            break;
         
         case("A-D"):
            bin = "0000111";
            break;
         
         case("D&A"):
            bin = "0000000";
            break;
         
         case("D|A"):
            bin = "0010101";
            break;
         
         case("M"):
            bin = "1110000";
            break;
         
         case("!M"):
            bin = "1110001";
            break;
         
         case("-M"):
            bin = "1110011";
            break;
         
         case("M+1"):
            bin = "1110111";
            break;
         
         case("M-1"):
            bin = "1110010";
            break;
         
         case("D+M"):
            bin = "1000010";
            break;
         
         case("D-M"):
            bin = "1010011";
            break;
         
         case("M-D"):
            bin = "1000111";
            break;
            
         case("D&M"):
            bin = "1000000";
            break;
            
         case("D|M"):
            bin = "1010101";
            break;
         
         default:
            break;
      }
      return bin;
   }
   
   //Returns the binary representation of the parsed jump field (string)
   public String jump(String tp)
   {
      if(tp == "na")
      {
         return "000";
      }
      String bin = new String();
      
      // Main switch
      switch (tp)
      {
         case("JGT"):
            bin = "001";
            break;
         
         case("JEQ"):
            bin = "010";
            break;
         
         case("JGE"):
            bin = "011";
            break;
         
         case("JLT"):
            bin = "100";
            break;
         
         case("JNE"):
            bin = "101";
            break;
         
         case("JLE"):
            bin = "110";
            break;
         
         case("JMP"):
            bin = "111";
            break;
         case("na"):
            bin = "000";
         default:
            break;
      }
      return bin;
   }
}
