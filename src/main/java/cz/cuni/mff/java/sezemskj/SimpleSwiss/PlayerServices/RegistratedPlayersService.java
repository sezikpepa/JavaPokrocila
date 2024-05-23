package cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class RegistratedPlayersService {

    private static final String FILE_NAME = "registratedPlayers.csv";

    private static final String DELIMITER = ";";

    public RegistratedPlayersService(){

    }


    private String getPlayerForCSV(Player player){

        if(player.Id == null || player.Id.isEmpty()){
            player.Id = UUID.randomUUID().toString();
        }

        return  player.Id +
                DELIMITER +
                player.FirstName +
                DELIMITER +
                player.LastName +
                DELIMITER +
                player.ChessClub +
                DELIMITER +
                player.Elo;
    }

    private Player parsePlayerFromCSVLine(String line){
        String[] splitted = line.split(DELIMITER);

        return new Player(splitted[0], splitted[1], splitted[2], splitted[3], parseInt(splitted[4]));
    }


    public void registerNewPlayer(Player player){
        File folderPath = new LocationService().getPath();
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(folderPath.toPath().resolve(FILE_NAME).toFile(), true))) {
            fileWriter.write(getPlayerForCSV(player));
            fileWriter.newLine();

            System.out.println("Saved: " + getPlayerForCSV(player));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMule(){
        Player mule = new Player("123456789", "Free", "match", "Unknown", 1000);

        registerNewPlayer(mule);
    }


    public void removePlayerFromTournament(String id){

        ArrayList<Player> registeredPlayers = getAllRegisteredPlayers();

        File folderPath = new LocationService().getPath();
        File file = folderPath.toPath().resolve(FILE_NAME).toFile();
        if (file.exists()) {
            file.delete();
        }

        registeredPlayers = registeredPlayers.stream().filter(x -> !x.Id.equals(id)).collect(Collectors.toCollection(ArrayList::new));

        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file, true))) {
            for(var player : registeredPlayers){
                fileWriter.write(getPlayerForCSV(player));
                fileWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Player> getAllRegisteredPlayers(){
        ArrayList<Player> registeredPlayers = new ArrayList<>();
        File folderPath = new LocationService().getPath();
        if(folderPath == null){
            return new ArrayList<>();
        }
        File file = folderPath.toPath().resolve(FILE_NAME).toFile();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String newLine;
            while ((newLine = fileReader.readLine()) != null){
                registeredPlayers.add(parsePlayerFromCSVLine(newLine));
            }
        } catch (IOException e) {
            return new ArrayList<>();
        }

        return registeredPlayers;
    }


}
