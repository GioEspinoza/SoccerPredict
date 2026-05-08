/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package futcomp;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

/*
Path.of //creates directory path object used to check for path
Files.exists - checks whether file exists or not
Files.createDirectories - creates file directory if path not true
Fils.writeString - writes String in json when feeded data
Files.readString - reads json data and returns string when given json file
*/
public class filesTest {
    
    Path filePath = Path.of("test/test_data.json"); //path.of is a static method does not need new when creating instance of object
    
    
    //public String checkFile() throws IOException{
    //    if (Files.exists(filePath)){ //checks if file exist
    //        return Files.readString(filePath); //returns the string stored in file
    //  }
    //    else{
            
    //        return "File does not exist";
    //    }
    //}
    //public void createFile() throws IOException{
    //    Files.createDirectories(filePath.getParent()); //getParent returns folder part of path, creating the folder that will host the file
    //    Files.writeString(filePath,"Hello World"); //will write string to json and save
    //}
    public static void main(String[] args) throws IOException, InterruptedException{
        //filesTest tester = new filesTest(); // creates tester object
        
        
        JsonStorage test = new JsonStorage();
        String testFile = test.checkDataFile();
        System.out.println(testFile);
        
        
        
        
        //{String fileTxt = tester.checkFile(); //will set variable to file data or to "file does not exist" depending on whether the file exist or not
        
        //if (!fileTxt.equals("File does not exist")){
        //    System.out.println(fileTxt);
        //}
        //else{
        //   tester.createFile(); //creates file
        //    fileTxt = tester.checkFile();
        //    System.out.println("File created!\n" + fileTxt);
        //}
        
    }    
}
