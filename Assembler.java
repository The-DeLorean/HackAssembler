import java.util.Scanner;
import java.io.*;

public class Assembler
{
	private Parser parser;
	private Code code;
	private SymbolTable symbolTable;

	private BufferedWriter writer;

	//color for error text
	public static final String ANSI_RED ="\u001B[31m";
	//color to reset the text back to white
	public static final String ANSI_WHITE = "\u001b[0m";

	public Assembler()
	{
		code = new Code();
		symbolTable = new SymbolTable();
		try
		{
			writer = new BufferedWriter(new FileWriter("./output.txt"));
		}
		catch(IOException ioe){}
	}


	//iterate over the input file and populate the symbol table with L commands only
	//iterate over the instruction memory to sroe it in sybmbol table
	public void firstPass(String program)
	{
		parser = new Parser(program);

		//store the current address of the memory  -- ROMM ADDRESS 0
		int currentAddress = 0;
		
		while(parser.hasMoreLines())
		{
			//advance to read next instructionm
			String temp = parser.advance();
			//check if comment of blanke
			if(temp.trim().isEmpty() || parser.instructionType() == "COMMENT"){}
			else 
			{
				//do not increment the address for this instruction since Labels font occupy 
				if (parser.instructionType() == "L_INSTRUCTION")
				{
					//check if symbol table conttains the label
					if(symbolTable.contains(parser.symbol()))
					{
						System.out.println("ST already contains: " + parser.symbol());
					}
					else
					{
						symbolTable.addEntry(parser.symbol(), currentAddress + 1);
					}
					//label
				}
				//dealing with instruction -- save for second pass, increment instructions
				currentAddress++;
			}

		}
		parser.close();
		parser = null;
		System.out.println(symbolTable);

	}


	/*
	 * Second pass of the parser
	 * parse each intruction and resolve each symbol
	 * translate to binary using code class
	 * write to output file
	 */
	 public void secondPass(String program)
	 {
		//reinstantiate the parser class to start from top of file
		parser = new Parser(program);
		//create to write to file

		
		
		//instantiate code object to translate coode to binary
		code = new Code();

		//current intruction
		//associate with current address + 1 since labels point to next instruction

		int currentRAMAddress = 16;

		//loop while parse hasMoreLines
		while(parser.hasMoreLines())
		{
			//c instruction
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
					//check if the sumbol ise
					System.out.println("A_INSTRUCTION");
				}

				//dest = comp;jump
				//contains binary 111accccccdddjjj
				else if (parser.instructionType() == "C_INSTRUCTION")
				{
					//build the string to contain the format 111accccccdddjjj
					String binaryCString = "111";	//starts with 111 by default

					binaryCString += code.comp(parser.comp());
					System.out.println("comp\t" + code.comp(parser.comp()));
					binaryCString += code.dest(parser.dest());
					System.out.println("dest\t" + code.dest(parser.dest()));
					binaryCString += code.jump(parser.jump());
					System.out.println("jump\t" + code.jump(parser.jump()));

					writeToOutput(binaryCString);

					System.out.println("C_INSTRUCTION");
				}			 
				System.out.println(parser.symbol());
			}
			
		}
		//close parser
		parser.close();

		//close burffer write to output file
		closeWriter();

	 }

	//function from geeps for geeks
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

	public boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	//function to write to output
	public void writeToOutput(String line)
	{
		try
		{
			writer.write(line);
			writer.newLine();
		}
		catch(IOException ioe)
		{
			System.out.println("Error writing to line in Assember writeToOutPut with string: \t" + line);
		}
	}

	public void closeWriter()
	{
		try {
			if (writer != null) { // Check if the writer is not null
				writer.close(); // Close the writer
			}
		} catch (IOException e) {
			e.printStackTrace(); // Print the stack trace if an exception occurs
		}
	}

	public static void main(String[]args)
	{
		Assembler assembler;
		//scanner initialization to read from file passed to command line
		//Scanner read;



		//check if no cmd line arguments were passed -- exit program
		if(args.length==0)
		{
			System.out.println(ANSI_RED + "No file name passed to command line argument, exiting program" + ANSI_WHITE);
			System.exit(0);
		}
		else
		{
			assembler = new Assembler();

			assembler.firstPass(args[0]);

			assembler.secondPass(args[0]);

			
			// //read in file from cmd arg
			// try
			// {
			// 	//create a file 
			// 	//create a scanner object that reads the file from the first command line argument
			// 	read = new Scanner(new File(args[0]));
			// 	//loop while there are values to read from the command line argument
			// 	// while(read.hasNextLine())
			// 	// {
			// 	// 	System.out.println(read.nextLine());
			// 	// }
			// }
			// //if no file is found, throw and error and exit the program
			// catch(FileNotFoundException fnfe)
			// {
			// 	System.out.println(ANSI_RED);
			// 	fnfe.printStackTrace();
			// 	System.out.println(ANSI_WHITE);
			// 	System.exit(0);
			// }

			//perform second pass of the code and convert symbols created byparser to binary code


		}

	}
	
}

