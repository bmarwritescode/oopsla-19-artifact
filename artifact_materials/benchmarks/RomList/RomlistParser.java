package glog.frontend.attractmode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;

import glog.frontend.FrontendTools;
import glog.util.Util;

public class RomlistParser {
	private int totalGames;
	private int totalGamesAvailable;
	private int missingWheel;
	private int missingVideo;
	private int missingCart;
	private int missingBox;
	private ArrayList<RomlistGame> missingGames;
	private ArrayList<RomlistGame> games;
	private boolean complete;
	private String description;
	private String romPath;

	public RomlistParser() {
	    this.totalGames = 0;
	    this.totalGamesAvailable = 0;
	    this.missingWheel = 0;
	    this.missingVideo = 0;
	    this.missingCart = 0;
	    this.missingBox = 0;
	    this.missingGames = new ArrayList<>();
	    this.games = new ArrayList<>();
	    this.complete = false;
	    this.description = "";
	    this.romPath = "";
	}
    
	public ArrayList<RomlistGame> parse(File romlistFile) throws Exception {
		return parse(romlistFile, null);
	}

	public ArrayList<RomlistGame> parse(File romlistFile, String folderToSearch) throws Exception {
		return parse(romlistFile, folderToSearch, true);
	}

	/**
	 * @param romlistFile
	 * @param folderToSearch
	 * @return
	 * @throws Exception
	 */
	public ArrayList<RomlistGame> parse(File romlistFile, String folderToSearch, boolean lookAvailable) throws Exception {
	    Object[] localObjs = new Object[6];
	    localObjs[0] = romlistFile;
	    localObjs[1] = folderToSearch;
	    localObjs[3] = null;
	    int[] localInts = new int[1];

	    File f = (File) localObjs[0];

	    if (!f.exists()) {
	    	return new ArrayList<RomlistGame>();		
	    }

	    this.games = new ArrayList<RomListGame>();
	    this.missingGames = new ArrayList<RomListGame>();
	    localInts[0] = 0;
	    this.complete = true;

	    f = (File) localObjs[0];
	    localObjs[2] = new BufferedReader(f);

	    BufferedReader br = (BufferedReader) localObjs[2];
	    localObjs[3] = br.readLine();
	    
	    while (localObjs[3] != null) {
	    	String line = (String) localObjs[3];
	    	if (!line.startsWith("#")) {
	    	    localObjs[4] = new Strings(line.split(";", -1));
	    	    Strings blah = (Strings) localObjs[4];
	    	    String[] data = blah.toArray();
		    if (data.length >= 10) {
			    localObjs[5] = new RomlistGame();			    
	    		    RomlistGame game = (RomlistGame) localObjs[5];
	    		    f = (File) localObjs[0];
	    		    game.setRomlist(f.getName());
	    		    game.setName(data[0]);
	    		    game.setTitle(data[1]);
	    		    game.setEmulator(data[2]);
	    		    game.setCloneOf(data[3]);
	    		    game.setYear(data[4]);
	    		    game.setManufacturer(data[5]);
	    		    game.setCategory(data[6]);
	    		    game.setPlayers(data[7]);
	    		    game.setRotation(data[8]);
	    		    game.setControl(data[9]);
	    		    game.setAvailable(false);

	    		    this.games.add(game);
		    }
	    	}
	    	br = (BufferedReader) localObjs[2];
	    	localObjs[3] = br.readLine();
	    }

	    this.totalGames = this.games.size();
	    this.totalGamesAvailable = localInts[0];

	    return this.games;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}

	public int getTotalGamesAvailable() {
		return totalGamesAvailable;
	}

	public void setTotalGamesAvailable(int totalGamesAvailable) {
		this.totalGamesAvailable = totalGamesAvailable;
	}

	public int getMissingWheel() {
		return missingWheel;
	}

	public ArrayList<RomlistGame> getGames() {
		return games;
	}

	public void setMissingWheel(int missingWheel) {
		this.missingWheel = missingWheel;
	}

	public int getMissingVideo() {
		return missingVideo;
	}

	public int getMissingCart() {
		return missingCart;
	}

	public int getMissingBox() {
		return missingBox;
	}

	public void setMissingVideo(int missingVideo) {
		this.missingVideo = missingVideo;
	}

	public ArrayList<RomlistGame> getMissingGames() {
		return missingGames;
	}

	public void setMissingGames(ArrayList<RomlistGame> missingGames) {
		this.missingGames = missingGames;
	}

}
