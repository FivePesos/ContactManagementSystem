import java.util.*;
import java.io.*;

public class Contact{
    
    public static void main(String... args){
        Scanner scan = new Scanner(System.in);
        File file = new File("contact.txt");
        File tempFile = new File("tempfile.txt");
        int response = 0;
        
        if(!tempFile.exists()){
            try{
                tempFile.createNewFile();
            }catch(Exception e){
                System.out.println("Something occured");
            }
        }
        if(!file.exists()){
            try{
                if(file.createNewFile()){
                    System.out.println("Created contact.txt");
                }
            }catch(Exception e){
                System.out.println("Something occured");
            }
        }

        while(response != 5){
            interFace();
            while(!scan.hasNextInt()){
                System.out.print("Please enter a whole number: ");
                scan.nextLine();
            }

            response = scan.nextInt();
            scan.nextLine();
            switch (response) {
                case 1:
                    String contact;
                    System.out.print("Enter contact name: ");
                    contact = scan.nextLine();
                    if(addContact(contact)){
                        System.out.println("Contact added.");
                    }else{
                        System.out.println("Contact already Existed");
                    }
                    break;
                case 2:
                    viewContacts();
                    break;

                case 3:
                    String oldContact, newContact;
                    System.out.print("Enter the name of the contact to update: ");
                    oldContact = scan.nextLine();

                    if(!alreadyExist(oldContact)){
                        System.out.println("Contact does not exist.");
                    }else{
                        System.out.print("Enter the new contact: ");
                        newContact = scan.nextLine();


                        if(alreadyExist(newContact)){
                            System.out.println("Contact already existed");
                        }else{
                            if(updateContact(oldContact, newContact)){
                                System.out.println("Contact Updated");
                            }else{
                                System.out.println("Cannot find contact");
                            }
                        }
                    }
                    break;
                case 4:
                    String deleteContact;
                    System.out.print("Enter contact to delete: ");
                    deleteContact = scan.nextLine();

                    if(deleteContact(deleteContact)){
                        System.out.println("Contact deleted.");
                    }else{
                        System.out.println("Cannot find contact");
                    }
                    break;

                case 5: 
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Please enter only 1 to 5");
                    break;
            }
        }

        

    }

    public static boolean alreadyExist(String contact){
        boolean match = false;
        File inputFile = new File("contact.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                if(currentLine.equals(contact)){
                    match = true;
                    break;
                }else{
                    match = false;
                }
            }
        }catch (Exception e) {
            return false;
        }
        return match;
    }

    public static void interFace(){
        System.out.println("===========================================");
        System.out.println("\tContact Management System");
        System.out.println("===========================================");
        System.out.println("1. Add Contact");
        System.out.println("2. View Contacts");
        System.out.println("3. Update Contact");
        System.out.println("4. Delete Contact");
        System.out.println("5. Exit");

        System.out.print("Choose an option: ");
    }

    public static boolean addContact(String value){
        if(!(alreadyExist(value))){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter("contact.txt", true))){
                writer.write(value + "\n");
                return true;
            }catch(Exception e){

                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public static void viewContacts(){
        try{
            FileReader reader = new FileReader("contact.txt");
            int data ;
            while((data = reader.read()) != -1){ 
                System.out.print((char) data);
            }
            reader.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static boolean updateContact(String oldContact, String newContact){
        boolean isUpdated = false;
        File inputFile = new File("contact.txt");
        File tempFile = new File("tempFile.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.trim().equals(oldContact)) {
                    writer.write(newContact + "\n");
                    isUpdated = true;
                } else {
                    writer.write(currentLine + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }

        inputFile.delete();
        tempFile.renameTo(inputFile);
        return isUpdated;
    }

    public static boolean deleteContact(String value){
        boolean isDeleted = false;
        File inputFile = new File("contact.txt");
        File tempFile = new File("tempfile.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader("contact.txt"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("tempFile.txt"))){
            String currentLine;

            while((currentLine = reader.readLine()) != null){
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(value)){
                    isDeleted = true;
                    continue;
                }
                writer.write(currentLine + "\n");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
        inputFile.delete();
        tempFile.renameTo(inputFile);
        return isDeleted;
    }

    
}