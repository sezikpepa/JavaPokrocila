package cz.cuni.mff.java.sezemskj.SimpleSwiss.PlayerServices;

import java.io.File;

public class TournamentService {

    private static final String FILE_NAME = "round";


    public TournamentService(){

    }


    public boolean TournamentHasStarted(){
        File directory = new LocationService().getPath();
        if(directory == null){
            return false;
        }
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith(FILE_NAME)) {
                    return true;

                }
            }
        }

        return false;
    }




}
