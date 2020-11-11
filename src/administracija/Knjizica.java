package administracija;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Knjizica {
	private long identifikator;
	LocalDateTime rokVazenja;
	ArrayList<Pregled> kolekcijaPregleda=new ArrayList<Pregled>(0);
	OsnovOsiguranja osnovOsiguranja;
	public Knjizica() {};
	public Knjizica(Long identifikator, LocalDateTime rokVazenja, ArrayList<Pregled> kolekcijaPregleda,
			OsnovOsiguranja osnovOsiguranja) {
		this.identifikator = identifikator;
		this.rokVazenja = rokVazenja;
		this.kolekcijaPregleda = kolekcijaPregleda;
		this.osnovOsiguranja = osnovOsiguranja;
	}
	public long getIdentifikator() {
		return identifikator;
	}
	public void setIdentifikator(long identifikator) {
		this.identifikator = identifikator;
	}
	public LocalDateTime getRokVazenja() {
		return rokVazenja;
	}
	public void setRokVazenja(LocalDateTime rokVazenja) {
		this.rokVazenja = rokVazenja;
	}
	public ArrayList<Pregled> getKolekcijaPregleda() {
		return kolekcijaPregleda;
	}
	public void setKolekcijaPregleda(ArrayList<Pregled> kolekcijaPregleda) {
		this.kolekcijaPregleda = kolekcijaPregleda;
	}
	public OsnovOsiguranja getOsnovOsiguranja() {
		return osnovOsiguranja;
	}
	public void setOsnovOsiguranja(OsnovOsiguranja osnovOsiguranja) {
		this.osnovOsiguranja = osnovOsiguranja;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (identifikator ^ (identifikator >>> 32));
		result = prime * result + ((kolekcijaPregleda == null) ? 0 : kolekcijaPregleda.hashCode());
		result = prime * result + ((osnovOsiguranja == null) ? 0 : osnovOsiguranja.hashCode());
		result = prime * result + ((rokVazenja == null) ? 0 : rokVazenja.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Knjizica))
			return false;
		Knjizica other = (Knjizica) obj;
		if (identifikator != other.identifikator)
			return false;
		if (kolekcijaPregleda == null) {
			if (other.kolekcijaPregleda != null)
				return false;
		} else if (!kolekcijaPregleda.equals(other.kolekcijaPregleda))
			return false;
		if (osnovOsiguranja != other.osnovOsiguranja)
			return false;
		if (rokVazenja == null) {
			if (other.rokVazenja != null)
				return false;
		} else if (!rokVazenja.equals(other.rokVazenja))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Knjizica [identifikator=" + identifikator + ", rokVazenja=" + rokVazenja + ", kolekcijaPregleda="
				+ kolekcijaPregleda.toString() + ", osnovOsiguranja=" + osnovOsiguranja + "]";
	}
	
	public static ArrayList<Knjizica> procitajFajlKnjizice(){
		ArrayList<Knjizica> knjizice=new ArrayList<Knjizica>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/knjizice.csv"));
			String row="";
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
			    String[] data = row.split("\\|");
				ArrayList<Pregled> pregledi=Pregled.procitajFajlPregledi();
				ArrayList<Pregled> izabraniPregledi=new ArrayList<Pregled>(0);
				String[] data1=data[2].split("\\*");
				if(data1.length!=1) {
					if(data1[2].length()>20) {
						/*System.out.println(pregledi.size());
						System.out.println(pregledi.get(0).getVremePocetka());
						System.out.println(pregledi.get(0).getPacijent().getKorisnickoIme());
						System.out.println();
						System.out.println("----------------------");
						System.out.println(LocalDateTime.parse(data1[0]));
						System.out.println(data1[1]);
						System.out.println(data1[2]);*/
						
						for(int i=0;i<pregledi.size();i++) {
							if(pregledi.get(i).getVremePocetka().isEqual(LocalDateTime.parse(data1[0])) &&
									pregledi.get(i).getPacijent().getKorisnickoIme().equalsIgnoreCase(data1[1]) &&
									pregledi.get(i).getIzvestaj().getBolnica().getNaziv().equalsIgnoreCase(data1[2])) {
								izabraniPregledi.add(pregledi.get(i));
								//System.out.println("++++++++++++++++++++++++++++++++");
							}
						}
					}																			
				}
				/*System.out.println(Long.valueOf(data[0]));
				System.out.println(LocalDateTime.parse(data[1]));
				System.out.println(izabraniPregledi);
				System.out.println(OsnovOsiguranja.valueOf(data[3].toUpperCase()));*/
				knjizice.add(new Knjizica(Long.valueOf(data[0]), LocalDateTime.parse(data[1]),izabraniPregledi,OsnovOsiguranja.valueOf(data[3].toUpperCase())));
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl knjizice nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla knjizice");
		}
		return knjizice;
	}
}
