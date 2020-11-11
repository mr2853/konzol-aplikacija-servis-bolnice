package administracija;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dijagnoza {
	private String naziv;
	long sifra;
	private ArrayList<Lek> lekovi=new ArrayList<Lek>(0);
	public Dijagnoza() {};
	public Dijagnoza(String naziv, long sifra, ArrayList<Lek> lekovi) {
		this.naziv = naziv;
		this.sifra = sifra;
		this.lekovi = lekovi;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public long getSifra() {
		return sifra;
	}

	public void setSifra(long sifra) {
		this.sifra = sifra;
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
		result = prime * result + ((lekovi == null) ? 0 : lekovi.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + (int) (sifra ^ (sifra >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Dijagnoza))
			return false;
		Dijagnoza other = (Dijagnoza) obj;
		if (lekovi == null) {
			if (other.lekovi != null)
				return false;
		} else if (!lekovi.equals(other.lekovi))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (sifra != other.sifra)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Dijagnoza [naziv=" + naziv + ", sifra=" + sifra + ", lekovi=" + lekovi.toString() + "]";
	}
	
	public static ArrayList<Dijagnoza> procitajFajlDijagnoze(){
		ArrayList<Dijagnoza> dijagnoze=new ArrayList<Dijagnoza>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/dijagnoze.csv"));
			String row="";
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
				ArrayList<Lek> lekovi=new ArrayList<Lek>(0);
			    String[] data = row.split("\\|");
				for(int i=2;i<data.length-1;i+=2) {
					lekovi.add(new Lek(data[i],Boolean.valueOf(data[i+1])));
				}
				dijagnoze.add(new Dijagnoza(data[0], Long.valueOf(data[1]), lekovi));
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl dijagnoze nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla dijagnoze");
		}
		return dijagnoze;
	}
}
