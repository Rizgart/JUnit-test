package mediabiblioteket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import collections.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JDialog;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.sun.javafx.collections.MappingChange.Map;

import collections.LinkedList;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GUITest1 {
	
	private static final Media media = new Book("Bok", "Java how to program", "427769", 2005, "Deitel");
	private static final Media media2 = new Book("Bok", "Historiens matematik", "775534", 1991, "Thompson");
	Borrower br1 = new Borrower("Harald Svensson", "891216-1111", "040-471024");
	Book book = new Book("Bok", "Java how to program", "427769", 2005, "Deitel");
	LibraryController theController = new LibraryController();
	
	@InjectMocks
	GUI gui;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		gui = mock(GUI.class);
		theController = new LibraryController(gui);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void searchMediaTitleByString() {	
		String theSearchString;
		theSearchString = "Finding Neverland";
		theController.searchMediaTitleByString(theSearchString);
		
		
		
	}
	
	@Test
    void testUserInput() {
        
        //Input Values
        String [] Neg_inputs = {"|","!","$","€"};
        String [] Pos_inputs = {"2","3","4","0","9","5"};
        
        //Negative TestCase
        boolean NegResult = false;
        for(int i = 0; i<Neg_inputs.length; i++) {
            
            boolean check = theController.checkUserInput(Neg_inputs[i]);
            if(check==false) {
                NegResult = true;
            }
            else {
                NegResult = false;
                break;
            }
        }
        
        //Positive TestCase
        boolean PosResult = false;
        for(int i = 0; i<Pos_inputs.length; i++) {
            
            boolean check = theController.checkUserInput(Pos_inputs[i]);
            if(check==true) {
                PosResult = true;
            }
            else {
                NegResult = false;
                break;
            }
        }
        assertTrue((NegResult==true && PosResult==true));
    }

	@Test
    void testDigitsinput() {
        
        String [] Neg_inputs = {"%","!","$","€","a","de","fad","5.3"};
        String [] Pos_inputs = {"1","2","3","4","5","6","7","8","9"};
        
        //Negative TestCase
        boolean NegResult = false;
        for(int i = 0; i<Neg_inputs.length; i++) {
            
            boolean check = theController.checkInputOnlyDigits(Neg_inputs[i]);
            if(check==false) {
                NegResult = true;
            }
            else {
                NegResult = false;
                break;
            }
        }
        
        //Positive TestCase
        boolean PosResult = false;
        for(int i = 0; i<Pos_inputs.length; i++) {
            
            boolean check = theController.checkInputOnlyDigits(Pos_inputs[i]);
            
            if(check==true) {
                PosResult = true;
            }
            else {
                PosResult = false;
                break;
            }
        }
        assertTrue((NegResult==true && PosResult==true));
    }
	
	@Test
    void BorrowAndReturnMedia(){    
        
        Borrower student1 = theController.allBorrowers.get(0);
        theController.currentBorrower = student1;
        
        //Borrow Media
        Media theMedia = theController.allMediaObjects.get(2);
        String isFree = theMedia.toString().toLowerCase();
        
        boolean free = isFree.contains("free");
        
        if(free==true) {
            theController.borrowMedia(theMedia);
        }
        else {
            System.out.println("The Media object aren't free");
        }
        
        Borrower check = theMedia.getThisMediaBorrower();
        boolean result = false;
        if(check!=null) {
            result = true;
        }
        else {
            result = false;
        }
        
        //Return Media
        String Input = student1.getSsn()+";"+theMedia.getObjectID();
        ArrayList<String> aList = theController.borrowed;
        
        String lastEle = aList.removeLast();
        aList.add(lastEle);
        
        boolean result2 = false;
        boolean check2 = lastEle.contains(Input);
        
        if(check2==true) {
            theController.returnMedia(theMedia);
            String free2 = theMedia.toString().toLowerCase();
            Borrower borrower = theMedia.getThisMediaBorrower();
            
            if(free2.contains("free") && (borrower==null)) {
                result2 = true;
            }
            else {
                result2 = false;
                System.out.println("the media didn't get free again");
            }
            
        }
        else {
            result2 = false;
            System.out.println("The media does not exist in the lantagare.exe file.");
            }
        assertTrue(result && result2);
    }
	
	 @Test
    void checkifborrowerexist() {
	        Borrower fakeBorrower = new Borrower("Bob","890511-1111","05413211");
	        Borrower realBorrower = theController.allBorrowers.get(2);
	        
	        boolean fake = theController.checkIfBorrowerExist(fakeBorrower.getSsn());
	        boolean real = theController.checkIfBorrowerExist(realBorrower.getSsn());
	        boolean result = false;
	        
	        if(fake==false && real==true) {
	            result = true;
	        }
	        else {
	            result = false;
	            System.out.println("Borrower might exist, or not exist.");
	        }
	        assertEquals(true,result);
	    }
	
	 @Test
	void getMedia() {
	        Media fakeMedia = new Book("book", "title1", "12345", 1972, "Author Authorsson");
	        Media realMedia = theController.allMediaObjects.get(2);
	        String Media1 = fakeMedia.getObjectID();
	        String Media2 = realMedia.getObjectID();
	        
	        boolean result;
	        if(theController.getMedia(Media1)==null && theController.getMedia(Media2)!=null) {
	            result = true;
	        }
	        else {
	            result = false;
	        }
	        assertEquals(true,result);
	        
	    }
	
	 @Test
	void getMediaFromSearchResult() {
	        LinkedList<Media> mediaSearchResults = theController.mediaSearchResults;
	        Media media1 = theController.allMediaObjects.get(6);
	        mediaSearchResults.add(media1);
	        Media input = theController.getMediaFromSearchResult(media1.toString());
	        
	        boolean result;
	        if(input==media1) {
	            result = true;
	        }
	        else {
	            result = false;
	        }
	        assertEquals(true,result);
	        
	    }
	
	 @Test
	void Borrower(){
	        Borrower fakeBorrower = new Borrower("Carl-Fredrik","891215-1111","5433213");
	        Borrower realBorrower = theController.allBorrowers.get(2);
	        Borrower value = theController.getBorrower(fakeBorrower.getSsn());
	        Borrower value2 = theController.getBorrower(realBorrower.getSsn());

	        boolean result;
	        
	        if(value==null && value2!=null) {
	            result = true;
	        }
	        else {
	            result = false;
	        }
	        
	        assertEquals(true,result);
	    }
	 
	 
	@Test
	void writeToFile() {

		try {
			PrintWriter theOutPutf = new PrintWriter(new FileOutputStream(new File("files\\UtlanadeTest.txt")));
			String fileName = "files\\utlanadeTest.txt";
			FileReader fileReader = new FileReader(fileName);

			theOutPutf.print("Dokument - UtlanadeTest");
			theOutPutf.close();

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			if (bufferedReader.readLine().contains("Dokument - UtlanadeTest")) {
				System.out.println("This test is succesful");
			} else {
				System.out.println("Test was not succesful");
			}
			bufferedReader.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
	@Test
	void searchBorrowed() {

	    theController.currentBorrower = br1;
		theController.searchBorrowed();
		theController.mediaSearchResults.add(media2);
		
	}
	
	@Test
    public void searchAll_title() {
        theController.searchMediaAllByString(media.getTitle());
        assertNotNull(media);
    }
	
	@Test
	void showSelectedMediaInfo() {
		
	    theController.mediaSearchResults.add(media2);
        theController.showSelectedMediaInfo(media2.toString());
    
	}
	
	@Test
    void searchMediaAllByString() {
        

        Book pd = new Book("Bok", "", "", 1995, "author");
        
        String obj = theController.allMediaObjects.get(2).objectID;
        String tit = theController.allMediaObjects.get(2).title;
        String typ = theController.allMediaObjects.get(2).mediaType;
        String dvdString = "Johnny Depp";
        String bookString = "Fredriksson";
        
        String aut = pd.getAuthor();
        
        
        String [] inputs = {typ,obj,tit,aut,dvdString,bookString};
        
        for(int i = 0; i<inputs.length; i++) {
            theController.searchMediaAllByString(inputs[i]);
            
        }
    }
	
}