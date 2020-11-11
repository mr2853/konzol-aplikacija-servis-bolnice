package administracija;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lek {
	private String naziv;
	private boolean uvozni;
	public Lek() {};
	public Lek(String naziv, boolean uvozni) {
		this.naziv = naziv;
		this.uvozni = uvozni;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public boolean isUvozni() {
		return uvozni;
	}
	public void setUvozni(boolean uvozni) {
		this.uvozni = uvozni;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + (uvozni ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Lek))
			return false;
		Lek other = (Lek) obj;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (uvozni != other.uvozni)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Lek [naziv=" + naziv + ", uvozni=" + uvozni + "]";
	}
	
	public static ArrayList<Lek> procitajFajlLekovi(){
		ArrayList<Lek> lekovi=new ArrayList<Lek>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/lekovi.csv"));
			String row="";
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
			    String[] data = row.split("\\|");
				for(int i=0;i<data.length-1;i+=2) {
					lekovi.add(new Lek(data[i], Boolean.valueOf(data[i+1])));
				}
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl lekovi nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla lekovi");
		}
		return lekovi;
	}
}
