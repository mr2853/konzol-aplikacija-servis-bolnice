package administracija;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import aplikacija.Aplikacija;
import osoblje.Doktor;

public class Recept implements Dokument{
	private boolean overen;
	private ArrayList<Lek> lekovi=new ArrayList<Lek>(0);
	public Recept() {};
	public Recept(boolean overen, ArrayList<Lek> lekovi) {
		super();
		this.overen = overen;
		this.lekovi = lekovi;
	}
	public boolean isOveren() {
		return overen;
	}
	public void setOveren(boolean overen) {
		this.overen = overen;
	}
	public ArrayList<Lek> getLekovi() {
		return lekovi;
	}
	public void setLekovi(ArrayList<Lek> lekovi) {
		this.lekovi = lekovi;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (overen ? 1231 : 1237);
		result = prime * result + ((lekovi == null) ? 0 : lekovi.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Recept))
			return false;
		Recept other = (Recept) obj;
		if (overen != other.overen)
			return false;
		if (lekovi == null) {
			if (other.lekovi != null)
				return false;
		} else if (!lekovi.equals(other.lekovi))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Recept [overen=" + overen + ", lekovi=" + lekovi.toString() + "]";
	}
	public void overi() {
		if(this.isOveren()) {
			System.out.println("Izvestaj je vec overen");
		}
		else {
			if(Aplikacija.getTrenutnoUlogovaniKorisnik().equalsIgnoreCase("doktor")) {
				this.setOveren(true);
			}
			else {
				System.out.println("Samo doktor moze da overi recept\n");
			}
		}
	}
	public void stampaj() {
		System.out.println(this.toString());
	}
	
	public static ArrayList<Recept> procitajFajlRecepti(){
		ArrayList<Recept> recepti=new ArrayList<Recept>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/recepti.csv"));
			String row="";
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
				ArrayList<Lek> lekovi=new ArrayList<Lek>(0);
			    String[] data = row.split("\\|");
				for(int i=1;i<data.length;i+=2) {
					lekovi.add(new Lek(data[i],Boolean.valueOf(data[i+1])));
				}
				recepti.add(new Recept(Boolean.valueOf(data[0]), lekovi));
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl recepti nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla recepti");
		}
		return recepti;
	}
	
}
