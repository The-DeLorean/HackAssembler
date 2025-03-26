import java.util.HashMap;

public class SymbolTable
{
   HashMap<String, Integer> symbolTable;
   
   // Default Constructor, has no params
   public SymbolTable()
   {
      symbolTable = new HashMap<>();
      
      // Populate the table with default values
      addDefaults();
   }
   
   // Adds <symbol, address> to the table
   public void addEntry(String symbol, int address)
   {
      symbolTable.put(symbol, address);
   }
   
   // Checks if symbol exists in the table
   public boolean contains(String symbol)
   {
      return symbolTable.containsKey(symbol);
   }
   
   // Returns the address associated with symbol
   public int getAddress(String symbol)
   {
      return symbolTable.getOrDefault(symbol, -1);
   }
   
   private void addDefaults()
   {
      for(int i=0; i<16; i++)
      {
         addEntry("R"+i, i);
      }
      
      addEntry("SP", 0);
      addEntry("LCL", 1);
      addEntry("ARG", 2);
      addEntry("THIS", 3);
      addEntry("THAT", 4);
      addEntry("SCREEN", 16384);
      addEntry("KBD", 24576);
      
   }


 
   public String toString()
   {
      StringBuilder sb = new StringBuilder();
      for (String key : symbolTable.keySet()) {
         sb.append(key).append(" = ").append(symbolTable.get(key)).append("\n");
      }
      return sb.toString();
   }
      
}