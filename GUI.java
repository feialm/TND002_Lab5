package lab5;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener, KeyListener{	
	//private JTextField myTextField; JLabel myLabel; 
	// private JButton Load; JButton Save; JButton Search; 

	//addActionListener(this); f�nsret ska lyssna p� kanppen
	// addActionListener(this); �r en lyssnare
	// ---> g�r vidare till actionperfomerd om man klickade p� kanppen

	private JTextField skrivaInText,namnRuta, nummerRuta;
	// private JLabel setEditable;
	private JButton loadButton, savep, search, nextname, addperson, deleteperson;
	private JPanel buttonPanel, dataPanel;

	private ArrayList<Person> result; // needed for search and next name buttons
	int countPerson = 0; // needed for search and next name button
	int clickAddbutton = 0;

	private PhoneBook phonebook = new PhoneBook();

	public GUI() {
		//setSize(400,600); // om pack() anv�nds p�verkar inte denna hur f�nstret ser ut
		setVisible(true);
		setTitle("�Interactive phone book");

		Font myFont= new Font("SansSerif", Font.PLAIN, 30);

		loadButton = new JButton("Load phonebook");
		loadButton.setPreferredSize(new Dimension(50,50));
		// till my pack()
		// eftersom gridlayout anv�nds bli alla andra knappar lika stora
		loadButton.setFont(myFont);
		loadButton.addActionListener(this);

		savep = new JButton("Save phonebook");
		savep.setFont(myFont);
		savep.addActionListener(this);
		savep.setEnabled(false);

		search = new JButton("Search");
		search.setFont(myFont);
		search.addActionListener(this);
		search.setEnabled(false);

		nextname = new JButton("Next name");
		nextname.setFont(myFont);
		nextname.addActionListener(this);
		nextname.setEnabled(false);

		addperson = new JButton("Add person");
		addperson.setFont(myFont);
		addperson.addActionListener(this);
		addperson.setEnabled(false);

		deleteperson = new JButton("Delete person");
		deleteperson.setFont(myFont);
		deleteperson.addActionListener(this);
		deleteperson.setEnabled(false);

		buttonPanel= new JPanel(new GridLayout(3,2));
		buttonPanel.add(loadButton);
		buttonPanel.add(savep);
		buttonPanel.add(search);
		buttonPanel.add(nextname);
		buttonPanel.add(addperson);
		buttonPanel.add(deleteperson);

		//this.add(buttonPanel);

		namnRuta = new JTextField ();
		namnRuta.setFont(myFont);
		namnRuta.setEnabled(false);

		nummerRuta = new JTextField ();
		nummerRuta.setFont(myFont);
		nummerRuta.setEnabled(false);

		skrivaInText = new JTextField();
		skrivaInText.setFont(myFont);
		skrivaInText.addActionListener(this);
		skrivaInText.addKeyListener(this);
		
		dataPanel = new JPanel(new GridLayout(3,1));
		dataPanel.add(skrivaInText);
		dataPanel.add(namnRuta);
		dataPanel.add(nummerRuta);

		//namnRuta.setText("Hej");
		//nummerRuta.setText("D�");

		Container c = getContentPane();
		c.setBackground(Color.BLUE);
		c.setLayout(new GridLayout(1,2));
		c.add(buttonPanel);
		c.add(dataPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE); // st�nger n�r man trycker p� krysset


	}
	
	
	
	public void keyPressed(KeyEvent e) {
		
		if(e.getSource() == skrivaInText) {
			loadPhoneBook();	
		}		
	}

	
	// dessa tv� m�ste vara med f�r man implementerade KeyListener
	// beh�ver ej skriva p� den
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	

	//s�tt disebaled p� alla knappar utan load
	// n�r k�rt load, enabled
	public void actionPerformed(ActionEvent e) {


		// denna fungerar
		if(e.getSource() == loadButton ) {
			loadPhoneBook();
		}


		// save fungerar
		// alla 50 personerna sparas
		// ny fil skapas och finns p� datorns h�rddisk
		if(e.getSource() == savep ) {
			// kallar p� save
			String text = skrivaInText.getText();

			//kallar p� save metoden och h�mtar returnstr�ngen fr�n den
			namnRuta.setText(phonebook.save(text));
		}


		if(e.getSource() == search){

			String text = namnRuta.getText();

			result = phonebook.search(text);

			if(result.size() == 0) {
				skrivaInText.setText("Provide a name");
				nummerRuta.setText("");
			}

			if(result.size() >= 0) {
				namnRuta.setText(result.get(countPerson).getFullName());
				nummerRuta.setText(String.valueOf(result.get(countPerson).getPhoneNumber()));

				if (result.size() > 1) {
					nextname.setEnabled(true); 
				}
			}
		}



		if (e.getSource() == nextname) {

			countPerson++;

			if(countPerson < result.size()) {
				// h�mtar en specifik person
				namnRuta.setText(result.get(countPerson).getFullName());
				nummerRuta.setText(String.valueOf(result.get(countPerson).getPhoneNumber()));
			}
			else {
				// end of array
				countPerson = 0;
				// set as 0 again, able to search again
				nextname.setEnabled(false);	
			}	
		}


		if(e.getSource() == addperson) {

			if(clickAddbutton == 0) {
				namnRuta.setText("");
				nummerRuta.setText("");
				skrivaInText.setText("Type in name and phone number");
				namnRuta.setEditable(true);
				nummerRuta.setEditable(true);
				clickAddbutton++;
			}
			else if(clickAddbutton == 1) {

				try {
					phonebook.addPerson(namnRuta.getText(), Integer.valueOf(nummerRuta.getText()));
				}
				catch(NumberFormatException err){
					namnRuta.setText("Error");
				}

				namnRuta.setText("");
				nummerRuta.setText("");

				skrivaInText.setText("Person added");

				clickAddbutton = 0;
			}
		}


		if (e.getSource() == deleteperson) {
			// aktiverar deletePerson metoden
			// skriver ut information om den personen man raderade
			// namn i namnrutan och hens nummer i nummerrutan i window
			skrivaInText.setText(phonebook.deletePerson(namnRuta.getText(), Integer.valueOf(nummerRuta.getText())));

		}		

	}



	
	// Laddar phonebook, g�r egen metod f�r att inte upprepa kod
	private void loadPhoneBook() {
		phonebook = new PhoneBook();
		// kallar p� load, text = textfilens namn
		String text = skrivaInText.getText();

		// kallar p� load och h�mtar str�ng fr�n load
		// laddar telefonboken
		namnRuta.setText(phonebook.load(text));	


		// n�r man har tryckt p� load knappen och laddat upp filen
		// aktivera de andra knapparnam INTE next knappen
		if ( namnRuta.getText().equals("Phone book loaded")) {

			savep.setEnabled(true);
			search.setEnabled(true);
			addperson.setEnabled(true);
			deleteperson.setEnabled(true);

			namnRuta.setEnabled(true);
			nummerRuta.setEnabled(true);
		}
	}
	
	
	public static void main(String[] args) {
		GUI myGUI = new GUI(); // kallar p� GUI	
		myGUI.pack();
		// pack() l�ser av programmet och p�verkar f�nster och knappars storlekar
	}
}



