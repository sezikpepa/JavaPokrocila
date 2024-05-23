package cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices;

import java.io.*;

public class LocationService {


    private final String FILE_NAME = "location.txt";

    public LocationService(){

    }

    public void savePath(File path){
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            fileWriter.write(path.getAbsolutePath());
            System.out.println("Saved: " + path.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getPath(){
        try (BufferedReader fileReader = new BufferedReader(new FileReader(FILE_NAME))) {
            var path = fileReader.readLine();
            return new File(path);
        } catch (IOException e) {
            return null;
        }
    }


}
