package osoblje;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import administracija.Izvestaj;
import administracija.Knjizica;
import administracija.Pregled;
import aplikacija.Aplikacija;
public class Pacijent extends Korisnik{
	Scanner sc=new Scanner(System.in);
	Knjizica knjizica;
	/**
	 * 
	 * @param ime
	 * @param prezime
	 * @param korisnickoIme
	 * @param lozinka
	 * @return 
	 */
	public Pacijent(String ime, String prezime, String korisnickoIme, String lozinka) {
		super(ime, prezime, korisnickoIme, lozinka);
		};
	
	public Pacijent(String ime, String prezime, String korisnickoIme, String lozinka, Knjizica knjizica) {
		super(ime, prezime, korisnickoIme, lozinka);
		// TODO Auto-generated constructor stub
		this.knjizica=knjizica;
	}
	
	public Knjizica getKnjizica() {
		return knjizica;
	}

	public void setKnjizica(Knjizica knjizica) {
		this.knjizica = knjizica;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((knjizica == null) ? 0 : knjizica.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Pacijent))
			return false;
		Pacijent other = (Pacijent) obj;
		if (knjizica == null) {
			if (other.knjizica != null)
				return false;
		} else if (!knjizica.equals(other.knjizica))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "Pacijent [knjizica=" + knjizica + ", Ime=" + getIme() + ", Prezime=" + getPrezime()
				+ ", KorisnickoIme=" + getKorisnickoIme() + ", Lozinka=" + getLozinka() + "]";
	}

	public static ArrayList<Pacijent> procitajFajlPacijenti() {
		ArrayList<Pacijent> pacijenti=new ArrayList<Pacijent>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/pacijenti.csv"));
			String row="";
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
			    String[] data = row.split("\\|");
				//ArrayList<Knjizica> knjizice=Knjizica.procitajFajlKnjizice();
				Knjizica knjizica=null;
				/*for(int i=0;i<knjizice.size();i++) {
					if(knjizice.get(i).getIdentifikator()==Long.valueOf(data[4])){
						knjizica=knjizice.get(i);
					}
				}*/
				if(Aplikacija.getBolnica()==null) {
					pacijenti.add(new Pacijent(data[0],data[1],data[2],data[3],knjizica));
				}
				else{
					if(Aplikacija.getBolnica().getNaziv().equalsIgnoreCase(data[5])) {
						pacijenti.add(new Pacijent(data[0],data[1],data[2],data[3],knjizica));
					}
				}
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl pacijenti nije nadjen\n");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla pacijenti\n");
		}
		if(Aplikacija.getBolnica()!=null) {
			Aplikacija.getBolnica().setPacijenti(pacijenti);
		}
		return pacijenti;
	}
	
	public void dodajKnjizicu() {
		try {
		BufferedReader csvReader = new BufferedReader(new FileReader("data/knjizice.csv"));
		String row="";
		Knjizica knjizica=null;
		ArrayList<Knjizica> knjizice=Knjizica.procitajFajlKnjizice();
		boolean nadjen=false;
		int brojacReda=0;
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split("\\|");
			BufferedReader csvReader1 = new BufferedReader(new FileReader("data/pacijenti.csv"));
			String row1="";
			while ((row1 = csvReader1.readLine()) != null) {
				String[] data1 = row1.split("\\|");
				if(this.getKorisnickoIme().equals(data1[2])) {
					if(Long.valueOf(data1[4])==Long.valueOf(data[0])) {
						knjizica=knjizice.get(brojacReda);
						nadjen=true;
					}
					if(nadjen) {
						this.setKnjizica(knjizica);
						csvReader1.close();
						csvReader.close();
						return;
					}
				}
			}
			brojacReda++;
			csvReader1.close();
		}
		//this.setKnjizica(knjizica);
		csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl pacijenti nije nadjen\n");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla pacijenti\n");
		}
		 
	}
	
	public void zakazivanjeTerminaPregleda() {
		if(this.getKnjizica().getRokVazenja().isBefore(LocalDateTime.now().withNano(0))) {
			System.out.println("Knjizica vam trenutno nije vazeca,\n"
					+ "molimo vas produzite je\n");
			return;
		}
		boolean odradjeno=false;
		while(!odradjeno) {
			ArrayList<Doktor> doktori=Doktor.procitajFajlDoktori();
			ArrayList<Pregled> pregledi=Pregled.procitajFajlPregledi();
			boolean malaPetlja=false;
			int izbor=0;
			while(!malaPetlja) {
				try {
					if(doktori.size()==0) {
						System.out.println("Trenutno nemamo doktore\n");
						break;
					}
					for(int i=0;i<doktori.size();i++) {
						System.out.println((i+1)+". Ime:"+doktori.get(i).getIme()+
								", Prezime:"+doktori.get(i).getPrezime()+
								", Specijalizacija:"+doktori.get(i).getSpecijalizacija());
					}
					System.out.println("Izaberite kod kog doktora zelite unosenjem rednog broja:");
					izbor=sc.nextInt();
					if(sc.hasNextLine()) {
						sc.nextLine();
					}
					izbor--;
				}catch(InputMismatchException e) {
					System.out.println("Potrebno je da unesete broj, a ne slovo\n");
					sc.nextLine();
					continue;
				}
				if(izbor>=0 && izbor<doktori.size()) {
					malaPetlja=true;
				}
				else {
					System.out.println("Izabrali ste nepostojecu opciju");
					continue;
				}
			}
			if(izbor>=doktori.size() || izbor<0) {
				System.out.println("Uneti broj neodgovara ni jednom doktoru\nPokusajte povono\n");
				continue;
			}
			Doktor doktor=doktori.get(izbor);
			malaPetlja=false;
			LocalDateTime vremePregleda=null;
			while(!malaPetlja) {
				System.out.println("Unesite zeljeni datum pregleda u formatu(YYYY-MM-DD):");
				String datum=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				String znak="-";
				if(datum.length()==10) {
					if(!znak.equalsIgnoreCase(String.valueOf(datum.charAt(4))) ||
							!znak.equalsIgnoreCase(String.valueOf(datum.charAt(7))) ||
							datum.length() != 10) {
						System.out.println("Greska, unesite datum u formatu(YYYY-MM-DD) npr. 2020-10-04\nProbajte ponovo\n");
						continue;
					}
				}
				
				boolean tacno=true;
				for(int i=0;i<datum.length();i++) {
					int broj=String.valueOf((datum.charAt(i))).hashCode();
					if(i != 4 && i != 7) {
						if(broj<47 || broj>58) {
							tacno=false;
						}
					}
				}
				if(!tacno) {
					System.out.println("Greska, unesite datum u formatu(YYYY-MM-DD) npr. 2020-10-04\nProbajte ponovo\n");
					continue;
				}
				
				String godinaSuma="";
				String mesecSuma="";
				String danSuma="";
				for(int i=0;i<datum.length();i++) {
					if(i<4) {
						godinaSuma+=datum.charAt(i);
					}
					if(i>4 && i<7) {
						mesecSuma+=datum.charAt(i);
					}
					if(i>7 && i<10) {
						danSuma+=datum.charAt(i);
					}
				}
				if(Integer.valueOf(godinaSuma)<LocalDateTime.now().getYear()) {
					System.out.println("Greska, vreme ne moze da bude u proslosti\nProbajte ponovo\n");
					continue;
				}
				if(Integer.valueOf(mesecSuma)>12) {
					System.out.println("Greska, za mesece se unose brojevi 1-12\nProbajte ponovo\n");
					continue;
				}
				if(Integer.valueOf(godinaSuma)==LocalDateTime.now().getYear()) {
					if(Integer.valueOf(mesecSuma)<LocalDateTime.now().getMonthValue()) {
						System.out.println("Greska, vreme ne moze da bude u proslosti\nProbajte ponovo\n");
						continue;
					}
				}
				if(Integer.valueOf(danSuma)>31) {
					System.out.println("Greska, za dane se unose brojevi 1-31\nProbajte ponovo\n");
					continue;
				}
				if(Integer.valueOf(godinaSuma)==LocalDateTime.now().getYear() &&
						Integer.valueOf(mesecSuma)==LocalDateTime.now().getMonthValue()) {
					if(Integer.valueOf(danSuma)<LocalDateTime.now().getDayOfMonth()) {
						System.out.println("Greska, vreme ne moze da bude u proslosti\nProbajte ponovo\n");
						continue;
					}
				}
				
				System.out.println("Unesite zeljeno vreme pregleda u formatu(HH:MM):");
				String vreme=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				znak=":";
				if(vreme.length()!=5) {
					System.out.println("Greska, unesite vreme u formatu(HH:MM)\nProbajte ponovo\n");
					continue;
				}
				
				if(!znak.equalsIgnoreCase(String.valueOf(vreme.charAt(2))) || vreme.length() != 5) {
					System.out.println("Greska, unesite vreme u formatu(HH:MM)\nProbajte ponovo\n");
					continue;
				}
				
				tacno=true;
				for(int i=0;i<vreme.length();i++) {
					int broj=String.valueOf((vreme.charAt(i))).hashCode();
					if(i != 2) {
						if(broj<47 || broj>58) {
							tacno=false;
						}
					}
				}
				if(!tacno) {
					System.out.println("Greska, unesite vreme u formatu(HH:MM)\nProbajte ponovo\n");
					continue;
				}
				String satiSuma="";
				String minutiSuma="";
				for(int i=0;i<vreme.length();i++) {
					if(i<2) {
						satiSuma+=vreme.charAt(i);
					}
					if(i>2 && i<vreme.length()) {
						minutiSuma+=vreme.charAt(i);
					}
				}
				if(Integer.valueOf(satiSuma)>23) {
					System.out.println("Greska, za sate se unose brojevi u opsegu 00-23\nProbajte ponovo\n");
					continue;
				}
				if(Integer.valueOf(minutiSuma)>59) {
					System.out.println("Greska, za minute se unose brojevi u opsegu 00-59\nProbajte ponovo\n");
					continue;
				}
				try {
				vremePregleda=LocalDateTime.parse(datum+"T"+vreme+":00");
				}catch(DateTimeParseException e) {
					System.out.println("Zadati datum ne postoji\n");
					return;
				}
				if(vremePregleda.isBefore(LocalDateTime.now().withNano(0))) {
					System.out.println("Vreme pregleda ne moze da bude vreme u proslosti\n");
					continue;
				}
				boolean nadjen=false;
				for(int i=0;i<pregledi.size();i++) {
					if(pregledi.get(i).getDoktor().getKorisnickoIme().equalsIgnoreCase(doktor.getKorisnickoIme())) {
						if(pregledi.get(i).getVremePocetka().isBefore(vremePregleda) &&
								pregledi.get(i).getVremePocetka().plusMinutes(Pregled.getTRAJANJE()).isAfter(vremePregleda) ||
								pregledi.get(i).getVremePocetka().isEqual(vremePregleda)) {
							DateTimeFormatter t=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
							System.out.println("Nazalost izabrani doktor je zauzet u tom terminu\n"
									+"Ako zelite mozemo mi da vam zakazemo,\n"
									+" prvi sledeci slobodan termin je "+pregledi.get(i).getVremePocetka().plusMinutes(20).format(t));
							System.out.println("Da li zelite(da/ne)?");
							nadjen=true;
							String tekst=sc.next();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							if(tekst.equalsIgnoreCase("da")) {
								vremePregleda=pregledi.get(i).getVremePocetka().plusMinutes(20);
								malaPetlja=true;
							}
							else {
								break;
							}
						}
					}
				}
				if(!nadjen) {
					malaPetlja=true;
				}
			}
			try {
				ArrayList<String> staro=new ArrayList<String>(0);
				try {
					BufferedReader csvReader = new BufferedReader(new FileReader("data/pregledi.csv"));
					String row="";
					while ((row = csvReader.readLine()) != null) {
						staro.add(row);
					}
					csvReader.close();
				}catch(FileNotFoundException e) {
					System.out.println("Zao nam je fajl pregledi nije nadjen\n");
				}
				catch(IOException e) {
					System.out.println("Zao nam je, imamo gresku u citanju fajla pregledi\n");
				}
				FileWriter csv=new FileWriter("data/pregledi.csv");
				for(int i=0;i<staro.size();i++) {
					csv.append(staro.get(i));
					csv.append("\n");
				}
				
				csv.append(String.valueOf(vremePregleda));
				csv.append("|");
				csv.append(doktor.getKorisnickoIme());
				csv.append("|");
				csv.append(this.getKorisnickoIme());
				csv.append("|");
				csv.append(" ");
				csv.append("*");
				csv.append(" ");
				csv.append("*");
				csv.append(" ");
				csv.append("\n");
				csv.flush();
				csv.close();
				odradjeno=true;
				}catch(IOException e) {
					System.out.println("Greska u otvaranju fajla pregledi.csv\n");
				}
		}
		 
	}
	
	public void overavanjeKnjizice() {
		boolean odradjeno=false;
		while(!odradjeno) {
			int godina=0;
			int meseca=0;
			int dana=0;
			int izbor=0;
			try {
				System.out.println("Trenutno vam je knjizica produzena do "+this.getKnjizica().getRokVazenja());
				System.out.println("Izaberite u kom formatu zelite da unesete produzetak knjizice:\n"
						+"1. dan\n"
						+"2. dan-mesec\n"
						+"3. dan-mesec-godina\n"
						+"4. dan-godina\n"
						+"5. mesec\n"
						+"6. mesec-godina\n"
						+"7. godina\n");
				izbor=sc.nextInt();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				if(izbor<1 || izbor>7) {
					System.out.println("Uneli ste broj van opsega (1-7)\n");
					continue;
				}
			}catch(InputMismatchException e) {
				System.out.println("Potrebno je da unesete broj, a ne slovo\n");
				sc.nextLine();
				continue;
			}
			catch(NullPointerException e) {
				System.out.println("Imamo gresku sa citanjem datoteke knjizice\n");
				odradjeno=true;
				break;
			}
				boolean odradjeno1=false;
				while(!odradjeno1) {
					try {
						godina=0;
						meseca=0;
						dana=0;
						switch(izbor) {
						case 1:
							System.out.println("Unesite za koliko dana zelite da produzite knjizu:");
							dana=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							odradjeno1=true;
							break;
						case 2:
							System.out.println("Unesite za koliko dana zelite da produzite knjizu:");
							dana=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							System.out.println("Unesite za koliko meseca zelite da produzite knjizu:");
							meseca=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							odradjeno1=true;
							break;
						case 3:
							System.out.println("Unesite za koliko dana zelite da produzite knjizu:");
							dana=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							System.out.println("Unesite za koliko meseca zelite da produzite knjizu:");
							meseca=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							System.out.println("Unesite za koliko godina zelite da produzite knjizu:");
							godina=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							odradjeno1=true;
							break;
						case 4:
							System.out.println("Unesite za koliko dana zelite da produzite knjizu:");
							dana=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							System.out.println("Unesite za koliko godina zelite da produzite knjizu:");
							godina=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							odradjeno1=true;
							break;
						case 5:
							System.out.println("Unesite za koliko meseci zelite da produzite knjizu:");
							meseca=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							odradjeno1=true;
							break;
						case 6:
							System.out.println("Unesite za koliko meseci zelite da produzite knjizu:");
							meseca=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							System.out.println("Unesite za koliko godina zelite da produzite knjizu:");
							godina=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							odradjeno1=true;
							break;
						case 7:
							System.out.println("Unesite za koliko godina zelite da produzite knjizu:");
							godina=sc.nextInt();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							odradjeno1=true;
							break;
						}
					}catch(InputMismatchException e) {
						System.out.println("Potrebno je da unesete broj, a ne slovo\n");
						sc.nextLine();
						continue;
					}
				
			this.getKnjizica().setRokVazenja(this.getKnjizica().getRokVazenja().plusYears(godina));
			this.getKnjizica().setRokVazenja(this.getKnjizica().getRokVazenja().plusMonths(meseca));
			this.getKnjizica().setRokVazenja(this.getKnjizica().getRokVazenja().plusDays(dana));
			odradjeno=true;
		}
	}
}
	
	public void pregledanjeZakazanihTermina() {
		ArrayList<Pregled> pregledi=Pregled.procitajFajlPregledi();
		boolean nadjenPregled=false;
		for(int i=0;i<pregledi.size();i++) {
			if(pregledi.get(i).getVremePocetka().isAfter(LocalDateTime.now().withNano(0)) &&
					pregledi.get(i).getPacijent().getKorisnickoIme().equalsIgnoreCase(this.getKorisnickoIme())) {
				nadjenPregled=true;
				System.out.println("Datum: "+pregledi.get(i).getVremePocetka()
						+",\nInformacije o doktoru:\nIme: "+pregledi.get(i).getDoktor().getIme()
						+",\nPrezime: "+pregledi.get(i).getDoktor().getPrezime()
						+",\nSpecijalizacija: "+pregledi.get(i).getDoktor().getSpecijalizacija()+"\n");
			}
		}
		if(!nadjenPregled) {
			System.out.println("Trenutno nemate zakazanih termina\n");
			return;
		}
	}
}
