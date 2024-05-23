package cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices;

import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.Player;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.PlayerWithPoints;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundDraw;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.Models.RoundPair;
import cz.cuni.mff.java.sezemskj.SimpleSwiss.SwissHandlers.SwissGenerator;

import java.io.*;
import java.util.ArrayList;

public class RoundGeneration {

    private static final String FILE_NAME = "round";

    private static final String EXTENSION = ".csv";

    private static final String DELIMITER = ";";

    public RoundGeneration(){

    }



    public boolean roundDrawExists(int roundNumber){
        var folderPath = new LocationService().getPath();
        if(folderPath == null){
            return false;
        }
        File fileToCheck = folderPath.toPath().resolve(FILE_NAME + roundNumber + EXTENSION).toFile();

        return fileToCheck.exists();
    }


    public void generateRound(int roundNumber){

        var registeredPlayersService = new RegistratedPlayersService();
        ArrayList<Player> registeredPlayers = registeredPlayersService.getAllRegisteredPlayers();

        if(roundNumber == 1){
            if(registeredPlayers.size() % 2 == 1){
                registeredPlayersService.addMule();
            }
        }

        registeredPlayers = registeredPlayersService.getAllRegisteredPlayers();

        var generator = new SwissGenerator(registeredPlayers);

        var previousRounds = loadPreviousRoundDraws(roundNumber - 1);

        var roundDraw = generator.GenerateRoundDraw(1, previousRounds);

        saveRoundDraw(roundNumber, roundDraw);
    }

    private String getMatchForCSV(RoundPair pair){
        return pair.White.Id + DELIMITER + pair.WhitePoints + DELIMITER + pair.Black.Id + DELIMITER + pair.BlackPoints;
    }

    public void saveRoundDraw(int roundNumber, RoundDraw roundDraw){
        File folderPath = new LocationService().getPath();
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(folderPath.toPath().resolve(FILE_NAME + roundNumber + EXTENSION).toFile()))) {
            for(var match : roundDraw.GetRoundPairs()){
                fileWriter.write(getMatchForCSV(match));
                fileWriter.newLine();

                System.out.println("Saved: " + getMatchForCSV(match));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RoundPair RoundPairFromCSVLine(String line, ArrayList<Player> registeredPlayers){
        String[] splitted = line.split(DELIMITER);

        Player white = registeredPlayers.stream().filter(x -> x.Id.equals(splitted[0])).findFirst().orElseThrow();
        Player black = registeredPlayers.stream().filter(x -> x.Id.equals(splitted[2])).findFirst().orElseThrow();

        var pair = new RoundPair(white, black);

        pair.WhitePoints = Double.parseDouble(splitted[1]);
        pair.BlackPoints = Double.parseDouble(splitted[3]);

        return pair;
    }


    public RoundDraw loadRoundDraw(int roundNumber){

        var registeredPlayersService = new RegistratedPlayersService();
        ArrayList<Player> registeredPlayers = registeredPlayersService.getAllRegisteredPlayers();

        RoundDraw roundDraw = new RoundDraw();
        var folderPath = new LocationService().getPath();
        var completePath = folderPath.toPath().resolve(FILE_NAME + roundNumber + EXTENSION).toFile();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(completePath))) {
            String newLine;
            while ((newLine = fileReader.readLine()) != null){
                roundDraw.AddPair(RoundPairFromCSVLine(newLine, registeredPlayers));
            }
        } catch (IOException e) {
            return new RoundDraw();
        }

        return roundDraw;
    }

    public ArrayList<RoundDraw> loadPreviousRoundDraws(int untilRound){
        ArrayList<RoundDraw> roundDraws = new ArrayList<>();
        for(int i = 1; i <= untilRound; i++){
            roundDraws.add(loadRoundDraw(i));
        }

        return roundDraws;
    }

    public int getMaximumRound(){
        var directory = new LocationService().getPath();
        File[] files = directory.listFiles();

        int maximum = 0;
        if (files != null) {
            for (File file : files) {
                String filename = file.getName();
                if(!filename.startsWith("round")){
                    continue;
                }
                else{
                    filename = filename.substring(5);
                }

                if(!filename.endsWith(".csv")){
                    continue;
                }
                else {
                    filename = filename.substring(0, filename.length() - 4);
                }

                System.out.println(Integer.parseInt(filename));

                if(Integer.parseInt(filename) > maximum){
                    maximum = Integer.parseInt(filename);
                }
            }
        }

        return maximum;
    }


    public ArrayList<PlayerWithPoints> loadCurrentStandings(int forRound){

        var registeredPlayersService = new RegistratedPlayersService();
        ArrayList<Player> registeredPlayers = registeredPlayersService.getAllRegisteredPlayers();

        ArrayList<RoundDraw> roundDraws = loadPreviousRoundDraws(forRound);

        var generator = new SwissGenerator(registeredPlayers);

        return generator.GetCurrentStandings(roundDraws, true);
    }

}
