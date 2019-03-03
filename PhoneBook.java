package lab5;

// finns inget riktigt main program, window till programmet blir som ett "main"


import java.io.*;
import java.util.ArrayList;

public class PhoneBook {

	private ArrayList <Person> listOfNumbers;

	// constructor
	public PhoneBook(){
	
		listOfNumbers= new ArrayList <Person>();
		
	}
	
	// opens file named arg
	public String load(String textfile){
	    
		BufferedReader reader = null; // en reader
		File aFile; // filen
		// FileReader myFileReader;//skapar myFileReader som läser av filen
		String returningResult ="";
		
		try {
		
		aFile = new File(textfile);
		  	
		  	reader = new BufferedReader(new FileReader(aFile));
			String aLine;
			String []list;
			// !=null, aLine = reader.readLine() ska inte bli nulll, spara tills stöter på tom rad
			while (!((aLine = reader.readLine())==null))
			{	
				
				list = aLine.split(" +");		
				listOfNumbers.add(new Person(list[0], list[1], Integer.parseInt(list[2])));
			}
			
			returningResult = "Phone book loaded";
		}
		catch(IOException e)
		{
			returningResult = "Try again";
		}
		
		try {
			if(reader != null) {
				reader.close(); // stänger filen om den finns
			}
		}
		catch(IOException e) {
			// om filen inte finns, finns inte reader, ingenting att stänga
		}
		
		return returningResult;
	}
	
	// får tillbaka (return) en arrayList som innehåler personer
	// We create a dynamic array in the search method, add to it all the
	// persons we found (several persons can have the same surname) and return it
	public ArrayList<Person> search(String searchingFor){
		
		ArrayList<Person> result  = new ArrayList <Person>(); // deklarerar arraylistan result, skapar den;

		//gör en variabel av searchingFor av typ int
		// för att kunna jämföra telefinnummer
		
		for(int i = 0; i < listOfNumbers.size(); i++) {

			if(searchingFor.equals(Integer.toString(listOfNumbers.get(i).getPhoneNumber())) || searchingFor.equals(listOfNumbers.get(i).getSurname())){
				// kollar om det finns någon som har det telefonnumret man söker efter
				result.add(listOfNumbers.get(i));
			}		
		}	
		return result;		
	}
	
	
	
	
	public String deletePerson(String personFullName, int number) {
		
		String result = "";
		
		// Kontrollerar först namnet och om vi hittar den så kontrollerar vi också att telefonnummer 
		// också stämmer, då tar vi bort det från listan
		
		for(int i = 0; i < listOfNumbers.size(); i++) {
			
			if(personFullName.equals(listOfNumbers.get(i).getFullName())) {
				
				if(number == listOfNumbers.get(i).getPhoneNumber()){
					
					listOfNumbers.remove(i);
					
					result = "Person deleted";
					break;
				}
				else {
					result = "The Person/Number does not exist";
				}
			}
			
		}
		return result;
		
	}
	
	
	
	
	public boolean addPerson(String person, int number) {
		
		boolean svar = false;
		
		for(int i = 0; i < listOfNumbers.size(); i++) {
			
			// kontrollera att full name är två ord
			String [] list;
			String name = new String();
			name = person;
			
			list = name.split(" +");

				if(list.length == 2) {
					
					if(!person.equals(listOfNumbers.get(i).getFullName())) {
						
						// skapa ny person
						Person newPerson = new Person(list[0], list[1], number);
						
						// addera personen
						listOfNumbers.add(newPerson);
						svar = true;
						}	
				}
			}	
		return svar;
	}
	
	
	
	
	public String save(String newFileName) {
		
		File theFile = new File(newFileName);

		String returnMessage = "";

		try {
			FileWriter myFileWriter = new FileWriter (theFile);
			
				for(int j = 0; j < listOfNumbers.size(); j++) {
					
					String name = listOfNumbers.get(j).getFullName();
					int phoneNumber = listOfNumbers.get(j).getPhoneNumber();
					
					String text = String.format("%-20s%-5d\n", name, phoneNumber);
					myFileWriter.write(text);
				}			
				
			returnMessage = String.format("Saved " + listOfNumbers.size() + " people to the file");
			myFileWriter.close();
		}
		catch (IOException e) {
			returnMessage = "Could not save to the file";
		}			
		
		return returnMessage ;	
	}

	
	
}// måsvinge till class PhoneBook