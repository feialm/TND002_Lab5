package lab5;

public class Person {

	private String givenName = "";
	private String surname = "";
	private int phoneNumber = 0;
	
	// constructor
	public Person(String name, String secondname, int number) {
		
		// initialize all instance variables
		givenName = name;
		surname = secondname;
		phoneNumber = number;
	}
	
	
	//The three methods return the surname, the
	//full name (given name and surname separated
	//by space) and the phone number.
	public String getSurname() {
		
		return surname;
	}
	
	
	public String getFullName() {
		
		String fullName = givenName + " " + surname;
		return fullName;	
	}

	
	
	public int getPhoneNumber() {
		
		return phoneNumber;
	}
	
	
}// måsvinge till class person