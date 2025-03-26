import java.io.*;

/*
Main assembler class, utilizes Parser.java, SymbolTable.java, and Code.java
*/

public class Assembler
{
	private Parser parser;
	private Code code;
	private SymbolTable symbolTable;

	private BufferedWriter writer;


	//constructor 
	//craete a Symbol table that will be used in pass 1 and pass 2 as well as the output file writer
	public Assembler()
	{
		symbolTable = new SymbolTable();
		try
		{
			writer = new BufferedWriter(new FileWriter("./Rect.hack"));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
			System.exit(1);
		}
	}


	//method to check if a string value is numeric by attempting to convert it to a double
	public boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
		//if not empty 0 try and convert to a double and throw error if a string
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


	//function adapted from geeks for geeks
    // Function converting decimal to binary
	// Function converting decimal to binary and returning it as a string
	public String decimalToBinary(int num)
	{
		// Create a StringBuilder to store the binary representation
		StringBuilder binary = new StringBuilder();

		// Convert the number to binary
		while (num > 0) {
			binary.append(num % 2);
			num = num / 2;
		}

		// Reverse the binary string to get the correct order
		binary.reverse();

		// Pad with leading zeros to make it 16 bits
		while (binary.length() < 15) {
			binary.insert(0, "0");
		}

		// Return the binary string
		return binary.toString();
	}


	//function to write to output
	public void writeToOutput(String line)
	{
		try
		{
			writer.write(line + "\n");
		}
		catch(IOException ioe)
		{
			System.out.println("Error writing to line in Assember writeToOutPut with string: \t" + line);
		}
	}


	//method to close the writer if it is still open
	public void closeWriter()
	{
		try {
			if (writer != null) {
				writer.close(); 
			}
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}


	//iterate over the input file and populate the symbol table with L commands only
	//iterate over the instruction memory to store it in sybmbol table
	public void firstPass(String program)
	{
		//instantiate parser
		parser = new Parser(program);

		//store the current address of the memory  -- ROMM ADDRESS 0
		int currentAddress = 0;
		
		//loop while there are more instructions to read in the input file
		while(parser.hasMoreLines())
		{
			//advance to read next instructionm
			String temp = parser.advance();
			//check if comment of blanke
			if(temp.trim().isEmpty() || parser.instructionType().equals("COMMENT")){}
			else 
			{
				//do not increment the address for this instruction since Labels font occupy 
				if (parser.instructionType() == "L_INSTRUCTION")
				{
					//check if symbol table conttains the label
					if(!symbolTable.contains(parser.symbol()))
					{
						symbolTable.addEntry(parser.symbol(), currentAddress);
					}
				}
				//dealing with instruction -- save for second pass, increment instructions
				else
				{
					currentAddress++;
				}
			}

		}
		//close the buffer
		parser.close();
		parser = null;
	}


	/*
	 * Second pass of the parser
	 * parse each A or C intruction and resolve each symbol
	 * translate to binary using code class
	 * write to output file
	 */
	 public void secondPass(String program)
	 {
		//reinstantiate the parser class to start from top of file
		parser = new Parser(program);
		
		//instantiate code object to translate coode to binary
		code = new Code();

		//current intruction
		//set currentRAMAddress to be at 16 since R0-R15 take up address 0-15
		int currentRAMAddress = 16;

		//loop while parse hasMoreLines
		while(parser.hasMoreLines())
		{
			//store whole instruction in temp
			String temp = parser.advance();
			//empty line or comment check
			if(temp.trim().isEmpty() || parser.instructionType() == "COMMENT"){}
			else
			{
				//get the instruction type for each line
				//if A-Intruction is encountered, and is not a symbol, assembler looks up value in the table
				if (parser.instructionType() == "A_INSTRUCTION")
				{
					//get the symbol
					String symbol = parser.symbol();
					if(isNumeric(symbol))
					{
						//set the address to the symbol value, convert to binary, and write to output file
						writeToOutput("0" + decimalToBinary(Integer.parseInt(symbol)));
					}
					else
					{
						//if symbol is found in the table, replace intruction with numeric value
						//translate instruction
						if(symbolTable.contains(symbol))
						{
							//System.out.println(decimalToBinary(Integer.parseInt(symbol)));
							writeToOutput("0" +decimalToBinary(symbolTable.getAddress(symbol)));
						}
						//if instruction is not found in the table, add a new variable at the current instruction address
						else
						{
							//create kv with address as next avaliable RAM addy and increment
							symbolTable.addEntry(symbol, currentRAMAddress);
							//increment 
							currentRAMAddress++;
							//translate instruction
							writeToOutput("0" + decimalToBinary(symbolTable.getAddress(symbol)));
						}
					}
				}

				//dest = comp;jump
				//contains binary 111accccccdddjjj
				else if (parser.instructionType() == "C_INSTRUCTION")
				{
					//build the string to contain the format 111accccccdddjjj
					String binaryCString = "111";	//starts with 111 by default

					binaryCString += code.comp(parser.comp());
					binaryCString += code.dest(parser.dest());
					binaryCString += code.jump(parser.jump());

					writeToOutput(binaryCString);
				}			
			}
			
		}
		//close parser
		parser.close();
		//close burffer write to output file
		closeWriter();
	 }


	public static void main(String[]args)
	{
		//instantiate assembler object
		Assembler assembler;
		//check if no cmd line arguments were passed -- exit program
		if(args.length==0)
		{
			System.out.println("No file name passed to command line argument, exiting program");
			System.exit(0);
		}
		else
		{
			//initialize assembler
			assembler = new Assembler();
			//perform first pass to add L instructions to SymbolTable
			assembler.firstPass(args[0]);
			//perform second pass to parse A and C instructions and generate binary
			assembler.secondPass(args[0]);
		}
	}
	
}

