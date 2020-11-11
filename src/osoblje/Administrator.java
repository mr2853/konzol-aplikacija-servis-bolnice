package osoblje;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import administracija.Dijagnoza;
import administracija.Knjizica;
import administracija.Lek;
import administracija.OsnovOsiguranja;
import administracija.Pregled;
import aplikacija.Aplikacija;

public class Administrator extends Korisnik{
	Scanner sc=new Scanner(System.in);
	public Administrator(String ime, String prezime, String korisnickoIme, String lozinka) {
		super(ime, prezime, korisnickoIme, lozinka);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Administrator))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Administrator [Ime=" + getIme() + ", Prezime=" + getPrezime() + ", KorisnickoIme="
				+ getKorisnickoIme() + ", Lozinka=" + getLozinka() + "]\n";
	}
	
	public static ArrayList<Administrator> procitajFajlAdministratori() {
		ArrayList<Administrator> administratori=new ArrayList<Administrator>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/administratori.csv"));
			String row="";
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
			    String[] data = row.split("\\|");
				//if(Aplikacija.getBolnica()==null) {
					administratori.add(new Administrator(data[0],data[1],data[2],data[3]));
				/*}
				else{
					if(Aplikacija.getBolnica().getNaziv().equalsIgnoreCase(data[4])) {
						administratori.add(new Administrator(data[0],data[1],data[2],data[3]));
					}
				}*/
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl administratori nije nadjen\n");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla administratori\n");
		}
		
		return administratori;
	}
	
	public void registracijaPacijenta() {
		ArrayList<Pacijent> pacijenti=Pacijent.procitajFajlPacijenti();
		ArrayList<Knjizica> knjizice=Knjizica.procitajFajlKnjizice();
		ArrayList<Doktor> doktori=Doktor.procitajFajlDoktori();
		ArrayList<Administrator> administratori=Aplikacija.getAdministratori();
		for(int i=0;i<pacijenti.size();i++) {
			pacijenti.get(i).dodajKnjizicu();
		}
		String nazivBolnice=Aplikacija.getBolnica().getNaziv();
		System.out.println("Sad cete unositi podatke za novog pacijenta\n");
		boolean odradjeno=false;
		String ime="";
		while(!odradjeno) {
			System.out.println("Ime: ");
			ime=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			for(int i=0;i<ime.length();i++) {
				if(String.valueOf(ime.charAt(i)).hashCode()==124) {
					System.out.println("Ime ne sme da sadrzi znak | \n");
					odradjeno=true;
				}
			}
			if(!odradjeno) {
				odradjeno=true;
			}
			else {
				odradjeno=false;
				ime="";
			}
		}
		odradjeno=false;
		String prezime="";
		while(!odradjeno) {
			System.out.println("Prezime: ");
			prezime=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			for(int i=0;i<prezime.length();i++) {
				if(String.valueOf(prezime.charAt(i)).hashCode()==124) {
					System.out.println("Prezime ne sme da sadrzi znak | \n");
					odradjeno=true;
				}
			}
			if(!odradjeno) {
				odradjeno=true;
			}
			else {
				odradjeno=false;
				prezime="";
			}
		}
		String korisnickoIme="";
		odradjeno=false;
		while(!odradjeno) {
			System.out.println("Korisnicko ime: ");
			korisnickoIme=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			odradjeno=true;
			for(int i=0;i<pacijenti.size();i++) {
				if(pacijenti.get(i).getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
					odradjeno=false;
					System.out.println("Vec postoji korisnik sa datim korisnickim imenom\n");
				}
			}
			for(int i=0;i<doktori.size();i++) {
				if(doktori.get(i).getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
					odradjeno=false;
					System.out.println("Vec postoji korisnik sa datim korisnickim imenom\n");
				}
			}
			for(int i=0;i<administratori.size();i++) {
				if(administratori.get(i).getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
					odradjeno=false;
					System.out.println("Vec postoji korisnik sa datim korisnickim imenom\n");
				}
			}
			for(int i=0;i<korisnickoIme.length();i++) {
				if(String.valueOf(korisnickoIme.charAt(i)).hashCode()==124) {
					System.out.println("Korisnicko ime ne sme da sadrzi znak | \n");
					odradjeno=false;
				}
			}
			if(!odradjeno) {
				korisnickoIme="";
			}
		}
		odradjeno=false;
		String lozinka="";
		while(!odradjeno) {
			System.out.println("Lozinka: ");
			lozinka=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			for(int i=0;i<lozinka.length();i++) {
				if(String.valueOf(lozinka.charAt(i)).hashCode()==124) {
					System.out.println("Lozinka ne sme da sadrzi znak | \n");
					odradjeno=true;
				}
			}
			if(!odradjeno) {
				odradjeno=true;
			}
			else {
				odradjeno=false;
				lozinka="";
			}
		}
		odradjeno=false;
		long identifikator=0;
		while(!odradjeno) {
			try {
				System.out.println("Identifikator za knjizicu: ");
				identifikator=sc.nextLong();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
			}catch(InputMismatchException e) {
				System.out.println("Potrebno je da unesete broj, a ne slovo\n");
				sc.nextLine();
				continue;
			}
			odradjeno=true;
			for(int i=0;i<knjizice.size();i++) {
				if(knjizice.get(i).getIdentifikator()==identifikator) {
					odradjeno=false;
					System.out.println("Vec postoji pacijent sa datim identifikatorom\n");
				}
			}
		}
		LocalDateTime rokVazenja=LocalDateTime.now().withNano(0);
		rokVazenja=rokVazenja.plusYears(1);
		ArrayList<Pregled> kolekcijaPregleda=new ArrayList<Pregled>(0);
		OsnovOsiguranja osnovOsiguranja=null;
		odradjeno=false;
		while(!odradjeno) {
			System.out.println("Unesite osnov osiguranja jedan od ponudjenih\n"
					+ "(zaposleni, student, dete, korisnik_socijalnih_usluga, penzioner): ");
			String tekst=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			if(tekst.equalsIgnoreCase(String.valueOf(OsnovOsiguranja.DETE))) {
				osnovOsiguranja=OsnovOsiguranja.DETE;
				odradjeno=true;
				break;
			}
			else if(tekst.equalsIgnoreCase(String.valueOf(OsnovOsiguranja.KORISNIK_SOCIJALNIH_USLUGA))) {
				osnovOsiguranja=OsnovOsiguranja.KORISNIK_SOCIJALNIH_USLUGA;
				odradjeno=true;
				break;
			}
			else if(tekst.equalsIgnoreCase(String.valueOf(OsnovOsiguranja.PENZIONER))) {
				osnovOsiguranja=OsnovOsiguranja.PENZIONER;
				odradjeno=true;
				break;
			}
			else if(tekst.equalsIgnoreCase(String.valueOf(OsnovOsiguranja.STUDENT))) {
				osnovOsiguranja=OsnovOsiguranja.STUDENT;
				odradjeno=true;
				break;
			}
			else if(tekst.equalsIgnoreCase(String.valueOf(OsnovOsiguranja.ZAPOSLENI))) {
				osnovOsiguranja=OsnovOsiguranja.ZAPOSLENI;
				odradjeno=true;
				break;
			}
			else {
				System.out.println("Niste uneli ni jednu od ponudjenih opcija\n");
			}
		}
			Knjizica knjizica=new Knjizica(identifikator, rokVazenja,kolekcijaPregleda,
					osnovOsiguranja);
			Pacijent pacijent=new Pacijent(ime,prezime,korisnickoIme,lozinka,knjizica);
			
			
		try {
			ArrayList<String> staro=new ArrayList<String>(0);
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/pacijenti.csv"));
				String row="";
				while ((row = csvReader.readLine()) != null) {
					staro.add(row);
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl pacijenti nije nadjen\n");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla pacijenti\n");
			}
			FileWriter csv=new FileWriter("data/pacijenti.csv");
			for(int i=0;i<staro.size();i++) {
				csv.append(staro.get(i));
				csv.append("\n");
			}
			csv.append(ime);
			csv.append("|");
			csv.append(prezime);
			csv.append("|");
			csv.append(korisnickoIme);
			csv.append("|");
			csv.append(lozinka);
			csv.append("|");
			csv.append(String.valueOf(identifikator));
			csv.append("|");
			csv.append(nazivBolnice);
			
			csv.flush();
			csv.close();
			odradjeno=true;
			}catch(IOException e) {
				System.out.println("Greska u otvaranju fajla pacijenti.csv \n");
			}
		
		try {
			ArrayList<String> staro=new ArrayList<String>(0);
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/knjizice.csv"));
				String row="";
				while ((row = csvReader.readLine()) != null) {
					staro.add(row);
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl knjizice nije nadjen\n");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla knjizice\n");
			}
			FileWriter csv=new FileWriter("data/knjizice.csv");
			for(int i=0;i<staro.size();i++) {
				csv.append(staro.get(i));
				csv.append("\n");
			}
			csv.append(String.valueOf(identifikator));
			csv.append("|");
			csv.append(String.valueOf(rokVazenja));
			csv.append("|");
			csv.append("|");
			csv.append(String.valueOf(osnovOsiguranja));
			csv.append("|");
			
			csv.flush();
			csv.close();
			}catch(IOException e) {
				System.out.println("Greska u otvaranju fajla knjizice.csv \n");
			}
		Aplikacija.getPacijenti().add(pacijent);
		pacijenti.add(pacijent);
		ArrayList<Knjizica> noveKnjizice=Aplikacija.getKnjizice();
		noveKnjizice.add(knjizica);
		Aplikacija.setKnjizice(noveKnjizice);
		pacijent.dodajKnjizicu();
		for(int i=0;i<Aplikacija.getBolnice().size();i++) {
			if(Aplikacija.getBolnice().get(i).getNaziv().equalsIgnoreCase(Aplikacija.getBolnica().getNaziv())) {
				ArrayList<Pacijent> pacijentiBolnice=Aplikacija.getBolnica().getPacijenti();
				pacijentiBolnice.add(pacijent);
				Aplikacija.getBolnica().setPacijenti(pacijentiBolnice);
			}
			ArrayList<Pacijent> pacijentiBolnica=Aplikacija.getBolnice().get(i).getPacijenti();
			pacijentiBolnica.add(pacijent);
			Aplikacija.getBolnice().get(i).setPacijenti(pacijentiBolnica);
		}
	}
	
	public void registracijaDoktora() {
		ArrayList<Doktor> doktori=Doktor.procitajFajlDoktori();
		ArrayList<Pacijent> pacijenti=Pacijent.procitajFajlPacijenti();
		ArrayList<Administrator> administratori=Aplikacija.getAdministratori();
		String nazivBolnice=Aplikacija.getBolnica().getNaziv();
		System.out.println("Sad cete unositi podatke za novog doktora\n");
		boolean odradjeno=false;
		String ime="";
		while(!odradjeno) {
			System.out.println("Ime: ");
			ime=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			for(int i=0;i<ime.length();i++) {
				if(String.valueOf(ime.charAt(i)).hashCode()==124) {
					System.out.println("Ime ne sme da sadrzi znak | \n");
					odradjeno=true;
				}
			}
			if(!odradjeno) {
				odradjeno=true;
			}
			else {
				odradjeno=false;
				ime="";
			}
		}
		
		odradjeno=false;
		String prezime="";
		while(!odradjeno) {
			System.out.println("Prezime: ");
			prezime=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			for(int i=0;i<prezime.length();i++) {
				if(String.valueOf(prezime.charAt(i)).hashCode()==124) {
					System.out.println("Prezime ne sme da sadrzi znak | \n");
					odradjeno=true;
				}
			}
			if(!odradjeno) {
				odradjeno=true;
			}
			else {
				odradjeno=false;
				prezime="";
			}
		}
		String korisnickoIme="";
		odradjeno=false;
		while(!odradjeno) {
			System.out.println("Korisnicko ime: ");
			korisnickoIme=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			odradjeno=true;
			for(int i=0;i<doktori.size();i++) {
				if(doktori.get(i).getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
					odradjeno=false;
					System.out.println("Vec postoji doktor sa datim korisnickim imenom\n");
				}
			}
			for(int i=0;i<pacijenti.size();i++) {
				if(pacijenti.get(i).getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
					odradjeno=false;
					System.out.println("Vec postoji korisnik sa datim korisnickim imenom\n");
				}
			}
			for(int i=0;i<administratori.size();i++) {
				if(administratori.get(i).getKorisnickoIme().equalsIgnoreCase(korisnickoIme)) {
					odradjeno=false;
					System.out.println("Vec postoji korisnik sa datim korisnickim imenom\n");
				}
			}
			for(int i=0;i<korisnickoIme.length();i++) {
				if(String.valueOf(korisnickoIme.charAt(i)).hashCode()==124) {
					System.out.println("Korisnicko ime ne sme da sadrzi znak | \n");
					odradjeno=false;
				}
			}
			if(!odradjeno) {
				korisnickoIme="";
			}
		}
		String lozinka="";
		odradjeno=false;
		while(!odradjeno) {
			System.out.println("Lozinka: ");
			lozinka=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			for(int i=0;i<lozinka.length();i++) {
				if(String.valueOf(lozinka.charAt(i)).hashCode()==124) {
					System.out.println("Lozinka ne sme da sadrzi znak | \n");
					odradjeno=true;
				}
			}
			if(!odradjeno) {
				odradjeno=true;
			}
			else {
				odradjeno=false;
				lozinka="";
			}
		}
		odradjeno=false;
		String specijalizacija="";
		while(!odradjeno) {
			System.out.println("Specijalizacija: ");
			specijalizacija=sc.nextLine();
			for(int i=0;i<specijalizacija.length();i++) {
				if(String.valueOf(specijalizacija.charAt(i)).hashCode()==124) {
					System.out.println("Specijalizacija ne sme da sadrzi znak | \n");
					odradjeno=true;
				}
			}
			if(!odradjeno) {
				odradjeno=true;
			}
			else {
				odradjeno=false;
				specijalizacija="";
			}
		}
		try {
			ArrayList<String> staro=new ArrayList<String>(0);
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/doktori.csv"));
				String row="";
				while ((row = csvReader.readLine()) != null) {
					staro.add(row);
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl doktori nije nadjen\n");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla doktori\n");
			}
			FileWriter csv=new FileWriter("data/doktori.csv");
			for(int i=0;i<staro.size();i++) {
				csv.append(staro.get(i));
				csv.append("\n");
			}
			csv.append(ime);
			csv.append("|");
			csv.append(prezime);
			csv.append("|");
			csv.append(korisnickoIme);
			csv.append("|");
			csv.append(lozinka);
			csv.append("|");
			csv.append(specijalizacija);
			csv.append("|");
			csv.append(nazivBolnice);
			
			csv.flush();
			csv.close();
			}catch(IOException e) {
				System.out.println("Greska u otvaranju fajla doktori.csv\n");
			}
		 
	}

	public void dodavanjeLeka() {
		ArrayList<Lek> lekovi=Lek.procitajFajlLekovi();
		Lek lek=null;
		boolean postojiVec=false;
		while(!postojiVec) {
			System.out.println("Unosenje podataka za novi lek\n");
			String naziv="";
			boolean odradjeno=false;
			while(!odradjeno) {
				System.out.println("Naziv: ");
				naziv=sc.nextLine();
				for(int i=0;i<naziv.length();i++) {
					if(String.valueOf(naziv.charAt(i)).hashCode()==124) {
						System.out.println("Naziv ne sme da sadrzi znak | \n");
						odradjeno=true;
					}
				}
				if(!odradjeno) {
					odradjeno=true;
				}
				else {
					odradjeno=false;
					naziv="";
				}
			}
			odradjeno=false;
			boolean uvozni=false;
			while(!odradjeno) {
				System.out.println("Da li je uvozni(da/ne): ");
				String tekst=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				if(tekst.equalsIgnoreCase("da")) {
					uvozni=true;
					odradjeno=true;
				}
				else if(tekst.equalsIgnoreCase("ne")) {
					uvozni=false;
					odradjeno=true;
				}
				else {
					System.out.println("Niste uneli ni jednu od trazenih opcija (da/ne)\n");
				}
			}
			for(int i=0;i<lekovi.size();i++) {
				if(lekovi.get(i).getNaziv().equalsIgnoreCase(naziv) &&
						String.valueOf(lekovi.get(i).isUvozni()).equalsIgnoreCase(String.valueOf(uvozni))) {
					postojiVec=true;
				}
			}
			if(postojiVec) {
				System.out.println("Zadati lek vec postoji\n");
				postojiVec=false;
			}
			else {
				postojiVec=true;
			}
			lek=new Lek(naziv,uvozni);
		}
		try {
			ArrayList<String> staro=new ArrayList<String>(0);
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/lekovi.csv"));
				String row="";
				while ((row = csvReader.readLine()) != null) {
					staro.add(row);
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl lekovi nije nadjen\n");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla lekovi\n");
			}
			FileWriter csv=new FileWriter("data/lekovi.csv");
			for(int i=0;i<staro.size();i++) {
				csv.append(staro.get(i));
			}
			csv.append("|");
			csv.append(lek.getNaziv());
			csv.append("|");
			csv.append(String.valueOf(lek.isUvozni()));
			
			csv.flush();
			csv.close();
			}catch(IOException e) {
				System.out.println("Greska u otvaranju fajla lekovi.csv\n");
			}
		 
	}

	public void dodavanjeDijagnoze() {
		ArrayList<Dijagnoza> dijagnoze=Dijagnoza.procitajFajlDijagnoze();
		ArrayList<Lek> lekovi=Lek.procitajFajlLekovi();
		ArrayList<Lek> dozvoljeniLekovi=new ArrayList<Lek>(0);
		Dijagnoza dijagnoza=null;
		boolean postojiVec=false;
		while(!postojiVec) {
			String naziv="";
			boolean odradjeno=false;
			while(!odradjeno) {
				System.out.println("Unosenje podataka za novu dijagnozu\n");
				System.out.println("Naziv: ");
				naziv=sc.nextLine();
				for(int i=0;i<naziv.length();i++) {
					if(String.valueOf(naziv.charAt(i)).hashCode()==124) {
						System.out.println("Naziv ne sme da sadrzi znak | \n");
						odradjeno=true;
					}
				}
				if(!odradjeno) {
					odradjeno=true;
				}
				else {
					odradjeno=false;
					naziv="";
				}
			}
			odradjeno=false;
			long sifra=0;
			while(!odradjeno) {
				try {
					System.out.println("Sifra: ");
					sifra=sc.nextLong();
					if(sc.hasNextLine()) {
						sc.nextLine();
					}
					for(int i=0;i<dijagnoze.size();i++) {
						if(dijagnoze.get(i).getSifra()==sifra) {
							odradjeno=true;
							System.out.println("Zadata sifra dijagnoze vec postoji\n");
						}
					}
					if(odradjeno) {
						odradjeno=false;
						continue;
					}
					odradjeno=true;
				}catch(InputMismatchException e) {
					System.out.println("Potrebno je da unesete broj, a ne slovo\n");
					sc.nextLine();
					continue;
				}
			}
			odradjeno=false;
			while(!odradjeno) {
				System.out.println("Zelite li da dodate lek za novu dijagnozu(da/ne):");
				String tekst=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				if(tekst.equalsIgnoreCase("da")) {
					boolean petlja=false;
					int izbor=0;
					while(!petlja) {
						try {
							for(int i=0;i<lekovi.size();i++) {
								System.out.println((i+1)+". Naziv: "+lekovi.get(i).getNaziv()
										+", Uvozni: "+lekovi.get(i).isUvozni());
							}
							System.out.println("Izaberite lek unosom rednog broja ispred leka: ");
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
						if(izbor>=0 && izbor<lekovi.size()) {
							petlja=true;
							for(int i=0;i<dozvoljeniLekovi.size();i++) {
								if(dozvoljeniLekovi.get(i).equals(lekovi.get(izbor))) {
									petlja=false;
									System.out.println("Izabrani lek vec postoji u novoj dijagnozi\n");
									break;
								}	
							}
							if(petlja) {
								dozvoljeniLekovi.add(lekovi.get(izbor));
								lekovi.remove(izbor);
							}
						}
						else {
							System.out.println("Greska, trebate uneti broj u opsegu 1-"+lekovi.size());
							continue;
						}
					}
				}
				else if(tekst.equalsIgnoreCase("ne")) {
					dijagnoza=new Dijagnoza(naziv,sifra,dozvoljeniLekovi);
					odradjeno=true;
					for(int i=0;i<dijagnoze.size();i++) {
						if(dijagnoze.get(i).equals(dijagnoza)) {
							odradjeno=false;
						}
					}
					if(!odradjeno) {
						System.out.println("Zadata dijagnoza vec postoji\n");
						odradjeno=true;
						postojiVec=true;
						return;
					}
					postojiVec=true;
				}
			}
		}
		try {
			ArrayList<String> staro=new ArrayList<String>(0);
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/dijagnoze.csv"));
				String row="";
				while ((row = csvReader.readLine()) != null) {
					staro.add(row);
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl dijagnoze nije nadjen\n");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla dijagnoze\n");
			}
			FileWriter csv=new FileWriter("data/dijagnoze.csv");
			for(int i=0;i<staro.size();i++) {
				csv.append(staro.get(i));
				csv.append("\n");
			}
			csv.append(dijagnoza.getNaziv());
			csv.append("|");
			csv.append(String.valueOf(dijagnoza.getSifra()));
			
			for(int i=0;i<dijagnoza.getLekovi().size();i++) {
				csv.append("|");
				csv.append(dijagnoza.getLekovi().get(i).getNaziv());
				csv.append("|");
				csv.append(String.valueOf(dijagnoza.getLekovi().get(i).isUvozni()));	
			}
			
			csv.flush();
			csv.close();
			}catch(IOException e) {
				System.out.println("Greska u otvaranju fajla dijagnoze.csv\n");
			}
		 
	}

	public void uklanjanjeLeka() {
		ArrayList<Lek> lekovi=Lek.procitajFajlLekovi();
		ArrayList<Lek> odabraniLekovi=new ArrayList<Lek>(0);
		Lek lek=null;
		System.out.println("Unesite naziv leka: ");
		String nazivLeka=sc.nextLine();
		for(int i=0;i<lekovi.size();i++) {
			if(lekovi.get(i).getNaziv().contains(nazivLeka)) {
				odabraniLekovi.add(lekovi.get(i));
			}
		}
		if(odabraniLekovi.isEmpty()) {
			System.out.println("Nismo pronasli ni jedan lek koji sadrzi uneti naziv\n");
			return;
		}
		boolean petlja=false;
		int izbor=0;
		while(!petlja) {
			try {
				for(int i=0;i<odabraniLekovi.size();i++) {
					System.out.println((i+1)+". Naziv: "+odabraniLekovi.get(i).getNaziv()
							+", Uvozni: "+odabraniLekovi.get(i).isUvozni());
				}
				System.out.println("Odaberite lek koji zelite da uklonite: ");
				izbor=sc.nextInt();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				izbor--;
				if(izbor>=0 && izbor<odabraniLekovi.size()) {
					lek=odabraniLekovi.get(izbor);
					petlja=true;
				}
				else {
					System.out.println("Greska, trebate uneti broj u opsegu 1-"+odabraniLekovi.size()+"\n");
				}
			}catch(InputMismatchException e) {
				System.out.println("Potrebno je da unesete broj, a ne slovo\n");
				sc.nextLine();
				continue;
			}
		}

		try {
			ArrayList<String> staro=new ArrayList<String>(0);
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/lekovi.csv"));
				String row="";
				while ((row = csvReader.readLine()) != null) {
					String[] data=row.split("\\|");
					String tekst="";
					for(int i=0;i<data.length-1;i+=2) {
						if(data[i].equalsIgnoreCase(lek.getNaziv()) &&
								data[i+1].equalsIgnoreCase(String.valueOf(lek.isUvozni()))){
							
						}
						else {
							tekst=tekst.concat(data[i]+"|"+data[i+1]+"|");
						}
					}
					staro.add(tekst);
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl lekovi nije nadjen\n");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla lekovi\n");
			}
			FileWriter csv=new FileWriter("data/lekovi.csv");
			for(int i=0;i<staro.size();i++) {
				csv.append(staro.get(i));
			}
			
			csv.flush();
			csv.close();
		}catch(IOException e) {
			System.out.println("Greska u otvaranju fajla lekovi.csv\n");
		}
		 
	}

	public void uklanjanjeDijagnoze() {
		ArrayList<Dijagnoza> dijagnoze=Dijagnoza.procitajFajlDijagnoze();
		Dijagnoza dijagnoza=null;
		boolean odradjeno=false;
		while(!odradjeno) {
			long sifra=-1;
			try {
				System.out.println("Unesite sifru dijagnoze: ");
				sifra=sc.nextLong();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
			}catch(InputMismatchException e) {
				System.out.println("Potrebno je da unesete broj, a ne slovo\n");
				sc.nextLine();
				continue;
			}
			int indeks=-1;
			if(sifra != -1) {
				for(int i=0;i<dijagnoze.size();i++) {
					if(sifra == dijagnoze.get(i).getSifra()) {
						indeks=i;
					}
				}
			}
			if(indeks==-1) {
				System.out.println("Nismo nasli ni jednu dijagnozu sa zadatom sifrom\n");
				return;
			}
			boolean petlja=false;
			while(!petlja) {
				System.out.println("Da li je ovo dijagnoza koju zelite da obrisete(da/ne):");
				System.out.println("Sifra: "+dijagnoze.get(indeks).getSifra()
						+", Naziv:"+ dijagnoze.get(indeks).getNaziv());
				String tekst=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				if(tekst.equalsIgnoreCase("da")) {
					dijagnoza=dijagnoze.get(indeks);
					petlja=true;
					odradjeno=true;
				}
				else if(tekst.equalsIgnoreCase("ne")) {
					petlja=true;
				}
				else {
					System.out.println("Greska, potrebno je da unesete jednu od opcija (da ili ne)\n");
				}
			}
		}
		try {
			ArrayList<String> staro=new ArrayList<String>(0);
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/dijagnoze.csv"));
				String row="";
				while ((row = csvReader.readLine()) != null) {
					String[] data=row.split("\\|");
					boolean nadjen=false;
					if(data[0].equalsIgnoreCase(dijagnoza.getNaziv()) &&
							Long.valueOf(data[1])==dijagnoza.getSifra()) {
						nadjen=true;
					}
					if(!nadjen) {
						staro.add(row);
					}
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajla dijagnoze nije nadjen\n");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla dijagnoze\n");
			}
			FileWriter csv=new FileWriter("data/dijagnoze.csv");
			for(int i=0;i<staro.size();i++) {
				csv.append(staro.get(i));
				csv.append("\n");
			}
			
			csv.flush();
			csv.close();
		}catch(IOException e) {
			System.out.println("Greska u otvaranju fajla dijagnoze.csv\n");
		}
		 
	}
}
