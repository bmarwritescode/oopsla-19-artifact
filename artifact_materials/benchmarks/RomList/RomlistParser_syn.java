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

    String line;
    BufferedReader br;
    File romlistFile;
    String[] data;

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

    generator boolean guard() {
	boolean cond = false;
	if (??) {
	    cond = line.startsWith("#");
	}
	if (??) {
	    cond = data.length >= 10;
	}
	return {| cond | !cond |};
    }
    
    generator void stmts() {
	if (??) {
	    // if (!line.startsWith("#")) {
	    if (guard()) {
		data = line.split(";", -1);
		// if (data.length >= 10) {
		if (guard()) {
		     RomlistGame game = new RomlistGame();
		     game.setRomlist(romlistFile.getName());
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
		     if (data.length > 10) {
			     game.setStatus(data[10]);
		     }
		     if (data.length > 11) {
			     game.setDisplayCount(data[11]);
		     }
		     if (data.length > 12) {
			     game.setDisplayType(data[12]);
		     }
		     if (data.length > 13) {
			     game.setAltRomname(data[13]);
		     }
		     if (data.length > 14) {
			     game.setAltTitle(data[14]);
		     }
		     if (data.length > 15) {
			     game.setExtra(data[15]);
		     }
		     if (data.length > 16) {
			     game.setButtons(data[16]);
		     }

		     game.setAvailable(false);
		     games.add(game);
		}
	    }
	}
	if (??) {
	    line = br.readLine();
	}
	if (??) {
	    stmts();
	}
    }
    
	/**
	 * @param romlistFile
	 * @param folderToSearch
	 * @return
	 * @throws Exception
	 */
	public ArrayList<RomlistGame> parse(File romlistFile2, String folderToSearch, boolean lookAvailable) throws Exception {
		romlistFile = romlistFile2;
		if (!romlistFile.exists()) {
			return new ArrayList<RomlistGame>();
		}
		games = new ArrayList<RomlistGame>();
		missingGames = new ArrayList<RomlistGame>();
		int gamesAvailable = 0;
		this.complete = true;
		br = new BufferedReader(romlistFile);		

		stmts();
		
		while(line != null) {
		    stmts();
		}

		this.totalGames = games.size();
		this.totalGamesAvailable = gamesAvailable;

		return games;
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
