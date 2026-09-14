package mypackage;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileManager {
    public static ArrayList<String> MyFiles = new ArrayList<String>();
    public static Scanner kbd = new Scanner(System.in);
    
    public static void hr(){
        System.out.println("---------------------------------------------------");
        System.out.println(" ");
    }
    
    public static void Create_file(){
        try{
            System.out.print("Enter name of file to create: ");
            String FileName = kbd.nextLine();
            File MyFile = new File(FileName);
            if(MyFile.createNewFile()){
                //accessing time for creation
                LocalDateTime CreationTime = LocalDateTime.now();
                DateTimeFormatter TimeFormat = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
                String FormattedCreationTime = TimeFormat.format(CreationTime);
                
                //adding a file in array list
                MyFiles.add(MyFile.getName());
                
                //displaying created file info
                System.out.println("File "+MyFile.getName()+" Created successfully");
                System.out.println("Location: "+MyFile.getAbsolutePath());
                System.out.println("Time: "+FormattedCreationTime);
            }else{
                System.out.println("File named "+MyFile.getName()+" Already exists");
            }
        }catch(IOException e){
            System.out.println("[*] Error");
            //e.printStackTrace();
        }
    }
    
    public static void Write_in_file(String file){
        try{
            String FileName;
            
            //check if no file name argument
            if(file == null){
                System.out.print("Write name of file to be writen: ");
                FileName = kbd.nextLine();
            }else{
                FileName = file;
            }
            File MyFile1 = new File(FileName);
            
            //checking if file name exists to write
            if(MyFile1.exists()){
                FileWriter MyFile = new FileWriter(FileName,true);
                System.out.println("Enter text to be writen in a file "+FileName);
                System.out.println("Type \"End\" to end text");
                String text;
                while(!(text = kbd.nextLine()).equalsIgnoreCase("END")){
                    MyFile.write(text + System.lineSeparator());
                }
                MyFile.close();
                System.out.println("Text writen in file");
            }else{
                System.out.println("FILE "+FileName+" doesn't exist");
            }
        }catch(IOException e){
            System.out.println("[*] Error during writing");
            //e.printStackTrace();
        }
    }
    
    public static void Read_in_file(){
        try{
            System.out.print("write name of file to read: ");
            String FileName = kbd.nextLine();
            File MyFile = new File(FileName);
            if(MyFile.exists()){
                Scanner reader = new Scanner(MyFile);
                
                //in case a file is empty
                if(MyFile.length() < 1){
                    System.out.println("File "+FileName+" is empty");
                    System.out.print("did you want to Write in a file (yes/no): ");
                    String choice = kbd.nextLine();
                    if(choice.equalsIgnoreCase("yes")){
                        Write_in_file(FileName);
                    }
                }else{                              //if file isn't empty
                    int LineNo = 1;
                    while(reader.hasNextLine()){
                        String line = reader.nextLine();
                        System.out.println(LineNo++ +". "+ line);
                    }
                }
                reader.close();
            }else{
                System.out.println("File "+FileName+" doesn't exist");
            }
        }catch(IOException e){
            System.out.println("[*] Error while reading in file");
            //e.printStackTrace();
        }
    }
    
    public static void Delete_file(){
        try{
            System.out.print("Enter name of file to delete: ");
            String FileName = kbd.nextLine();
            File MyFile = new File(FileName);
            if(MyFile.exists()){
                if(MyFile.delete()){
                    System.out.println("File "+FileName+" Deleted seccessfully");
                    MyFiles.remove(MyFile.getName());
                }else{
                    System.out.println("Failed to delete a file "+FileName+" try again");
                    Delete_file();
                }
            }else{
                System.out.println("File named "+FileName+" Doesn't exist");
            }
        }catch(Exception e){
            System.out.println("[*] Unknown Error");
            //e.printStackTrace();
        }
    }
    
    public static void Display_all_files(){
        try{
            File curDir = new File(".");
            File[] FileList = curDir.listFiles();
            int count = 0;
            for(File file : FileList){
                if(file.isFile()){
                    count++;
                }
            }
            if(FileList == null || FileList.length == 0){
                System.out.println("No file has been created yet!");
            }else{
                hr();
                System.out.println("            Found Total of "+count+" Files");
                hr();
                int FileNo = 1;
                for(File MyFile : FileList){
                    if(MyFile.isFile()){
                        System.out.println(FileNo++ + ". " + MyFile.getName()+"     | Size:"+MyFile.length()+" bytes");
                    }
                }
            }
        }catch(Exception e){
            System.out.println("[*] Failed to display your files");
            //e.printStackTrace();
        }
    }
    
    public static void Search_file(){
        try{
            System.out.print("Enter name of file you search: ");
            String FileName = kbd.nextLine();
            File MyFile = new File(FileName);
            if(MyFile.exists()){
                System.out.println("File name: "+MyFile.getName());
                System.out.println("File path: "+MyFile.getAbsolutePath());
                System.out.println("File length: "+MyFile.length());
                System.out.println("Writable: "+MyFile.canWrite());
                System.out.println("Readable: "+MyFile.canRead());
            }else{
                System.out.println("File "+FileName+" Not found");
            }
        }catch(Exception e){
            System.out.println("[*] Unknown Error");
            //e.printStackTrace();
        }
    }
    
    public static void Rename_file(){
        try{
            System.out.print("Enter name of file to rename: ");
            String OldFileName = kbd.nextLine();
            File MyOldFile = new File(OldFileName);
            if(MyOldFile.exists()){
                System.out.print("Enter new file name to rename "+OldFileName+": ");
                String NewFileName = kbd.nextLine();
                File MyNewFile = new File(NewFileName);
                if(!MyNewFile.exists()){
                    if(MyOldFile.renameTo(MyNewFile)){
                        System.out.println("File renamed from "+OldFileName+" to "+NewFileName);
                        MyFiles.remove(OldFileName);
                        MyFiles.add(NewFileName);
                    }else{
                        System.out.println("Failed to rename please try again ");
                        Rename_file();
                    }
                }else{
                    System.out.println("File named "+NewFileName+" already exists");
                    Rename_file();
                }
            }else{
                System.out.println("File does not found \ntry again and Make sure the name is correct");
                Rename_file();
            }
        }catch(Exception e){
            System.out.println("[*] Unknown Error");
            e.printStackTrace();
        }
    }
    
    public static void Update_file(){
        try{
            System.out.print("Enter name of file to update: ");
            String FileName = kbd.nextLine();
            File MyFile = new File(FileName);

            if(!MyFile.exists()){
                System.out.println("File "+FileName+" Does not found");
                return;
            }

            Scanner reader = new Scanner(MyFile);
            String text,textOld,textNew;
            ArrayList<String> lines = new ArrayList<String>();

            while(reader.hasNextLine()){
                text = reader.nextLine();
                System.out.println(text);
                lines.add(text);
            }
            reader.close();

            if(lines.size() == 0){
                System.out.println("File is empty, nothing to update");
                return;
            }

            System.out.print("Enter targeted text from above to update: ");
            textOld= kbd.nextLine();
            System.out.print("Enter new text: ");
            textNew = kbd.nextLine();

            FileWriter writer = new FileWriter(FileName);
            for(int i = 0; i <= lines.size() - 1; i++){
                lines.set(i,lines.get(i).replace(textOld,textNew));
                writer.write(lines.get(i)+System.lineSeparator());
            }
            System.out.println("File updated !");
            writer.close();

        }catch(Exception e){
            System.out.println("[*] Unknown error occured");
        }
    }

    public static void Menu(){
        int choice = 0;
        do{
            System.out.println("=== ISAAC FILE MANAGER ===");
            System.out.println("_");
            System.out.println("1. Create a new file");
            System.out.println("2. Write in a file");
            System.out.println("3. Read in a file");
            System.out.println("4. Delete a file");
            System.out.println("5. Rename a file");
            System.out.println("6. update a file");
            System.out.println("7. Search a file");
            System.out.println("8. Display all files");
            System.out.println("9. Exit");
            System.out.print("-Select your choice (1-9): ");
            try{
                choice = kbd.nextInt();
                kbd.nextLine();
                switch(choice){
                    case 1:
                        Create_file();
                        hr();
                        break;
                    case 2:
                        Write_in_file(null);
                        hr();
                        break;
                    case 3:
                        Read_in_file();
                        hr();
                        break;
                    case 4:
                        Delete_file();
                        hr();
                        break;
                    case 5:
                        Rename_file();
                        hr();
                        break;
                    case 6:
                        Update_file();
                        hr();
                        break;
                    case 7:
                        Search_file();
                        hr();
                        break;
                    case 8:
                        Display_all_files();
                        hr();
                        break;
                    case 9:
                        System.out.println("you exit bye!");
                        hr();
                        return;
                    default:
                        System.out.println("invalid choice");
                }
            }catch(Exception e){
                System.out.println("[*] Error occured");
                System.out.println("Write a number and not a text");
                return;
            }
            
        }while(choice != 9);
    }
    
    public static void main(String[] args){
        Menu();
    }
}