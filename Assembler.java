import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assembler
{
	//private Parser parser;
	//private Code code;
	//private SymbolTable
	//color for error text
	public static final String ANSI_RED ="\u001B[31m";
	//color to reset the text back to white
	public static final String ANSI_WHITE = "\u001b[0m";

	public Assembler()
	{
		
	}


	public static void main(String[]args)
	{
		Assembler assembler = new Assembler();
		//scanner initialization to read from file passed to command line
		Scanner read;



		//check if no cmd line arguments were passed -- exit program
		if(args.length==0)
		{
			System.out.println(ANSI_RED + "No file name passed to command line argument, exiting program" + ANSI_WHITE);
			System.exit(0);
		}
		else
		{
		
			//read in file from cmd arg
			try
			{
				//create a file 
				//create a scanner object that reads the file from the first command line argument
				read = new Scanner(new File(args[0]));
				//loop while there are values to read from the command line argument
				// while(read.hasNextLine())
				// {
				// 	System.out.println(read.nextLine());
				// }
			}
			//if no file is found, throw and error and exit the program
			catch(FileNotFoundException fnfe)
			{
				System.out.println(ANSI_RED);
				fnfe.printStackTrace();
				System.out.println(ANSI_WHITE);
				System.exit(0);
			}

		}

	}
	
}
