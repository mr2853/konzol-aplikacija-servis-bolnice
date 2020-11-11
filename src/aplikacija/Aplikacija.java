package aplikacija;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import administracija.Bolnica;
import administracija.Dijagnoza;
import administracija.Izvestaj;
import administracija.Knjizica;
import administracija.Lek;
import administracija.OsnovOsiguranja;
import administracija.Pregled;
import administracija.Recept;
import osoblje.Administrator;
import osoblje.Doktor;
import osoblje.Pacijent;

public class Aplikacija {
	/*
	 * Radi lakse organizacije podataka, stavio sam u fajlove pacijenti, doktori i
	 * administratori naziv bolnice kojoj pripadaju. Vodim se time da su nazivi bolnica
	 * jedinstveni, takodje i da su korisnicka imena pacijenata, doktora i administratora
	 * jedinstvena za sve bolnice.
	 * 
	 * Datoteka bolnice. Sadrzi samo nazive bolnica. Za prijavu korisnika korisnicko ime
	 *  i lozinka se proveravaju u datotekama administratori/doktori/pacijenti, kada se
	 * korisnik prijavi prema nazivu bolnice u datoteci ulogovanog korisnika se pronalazi
	 *  naziv njegove bolnice. Dalje prema nazivu bolnice u polje bolnica tipa Bolnica
	 *  se ubacuju podaci o doktorima i pacijentima te bolnice.
	 * 
	 * Radi smanjenja kolicine podataka u datotekama, i manje mogucnosti za greske.
	 * Tamo gde trebaju da pisu doktori ili pacijenti, tu su njihova korisnicka imena
	 * i onda se prema korisnickim imenima dobavljaju ostale informacije o doktorima
	 * ili pacijentima iz njihovih datoteka. Tamo gde trebaju da budu knjizice, tamo
	 * stoje identifikatori knjizica. Mislim da nema potrebe da u drugim datotekama
	 * pisu sve informacije knjizice, dovoljno je da pise identifikator knjizice i 
	 * onda se prema njemu nadje odgovarajuca knjizica.
	 * 
	 * U slucaju da treba pronaci izvestaj. On se pretrazuje prema datumu izdavanja,
	 * korisnickog imena doktora i korisnickog imena pacijenta.
	 */
	private static String trenutnoUlogovaniKorisnik="";
	private static int indeksKorisnika=0;
	private static ArrayList<String> doktoriBolnice=new ArrayList<String>(0);
	private static ArrayList<String> pacijentiBolnice=new ArrayList<String>(0);
	private static ArrayList<Bolnica> bolnice=Bolnica.procitajFajlBolnice();
	private static ArrayList<Lek> lekovi=Lek.procitajFajlLekovi();
	private static ArrayList<Dijagnoza> dijagnoze=Dijagnoza.procitajFajlDijagnoze();
	private static ArrayList<Recept> recepti=Recept.procitajFajlRecepti();
	private static ArrayList<Izvestaj> izvestaji=Izvestaj.procitajFajlIzvestaji();
	private static ArrayList<Pregled> pregledi=Pregled.procitajFajlPregledi();
	private static ArrayList<Knjizica> knjizice=Knjizica.procitajFajlKnjizice();
	private static ArrayList<Administrator> administratori=Administrator.procitajFajlAdministratori();
	private static ArrayList<Doktor> doktori=Doktor.procitajFajlDoktori();
	private static ArrayList<Pacijent> pacijenti=Pacijent.procitajFajlPacijenti();
	private static Scanner sc=new Scanner(System.in);
	private static Pacijent pacijent=null;
	private static Doktor doktor=null;
	private static Administrator administrator=null;
	private static Bolnica bolnica;
	
	public static ArrayList<String> getDoktoriBolnice() {
		return doktoriBolnice;
	}
	public static void setDoktoriBolnice(ArrayList<String> doktoriBolnice) {
		Aplikacija.doktoriBolnice = doktoriBolnice;
	}
	public static ArrayList<String> getPacijentiBolnice() {
		return pacijentiBolnice;
	}
	public static void setPacijentiBolnice(ArrayList<String> pacijentiBolnice) {
		Aplikacija.pacijentiBolnice = pacijentiBolnice;
	}
	public static Bolnica getBolnica() {
		return bolnica;
	}
	public static void setBolnica(Bolnica bolnica) {
		Aplikacija.bolnica = bolnica;
	}
	public static Pacijent getPacijent() {
		return pacijent;
	}
	public void setPacijent(Pacijent pacijent) {
		Aplikacija.pacijent = pacijent;
	}
	public static Doktor getDoktor() {
		return doktor;
	}
	public void setDoktor(Doktor doktor) {
		Aplikacija.doktor = doktor;
	}
	public static Administrator getAdministrator() {
		return administrator;
	}
	public void setAdministrator(Administrator administrator) {
		Aplikacija.administrator = administrator;
	}
	public static String getTrenutnoUlogovaniKorisnik() {
		return trenutnoUlogovaniKorisnik;
	}
	public static void setTrenutnoUlogovaniKorisnik(String trenutnoUlogovaniKorisnik) {
		Aplikacija.trenutnoUlogovaniKorisnik = trenutnoUlogovaniKorisnik;
	}
	public static int getIndeksKorisnika() {
		return indeksKorisnika;
	}
	public static void setIndeksKorisnika(int indeksKorisnika) {
		Aplikacija.indeksKorisnika = indeksKorisnika;
	}
	public static ArrayList<Bolnica> getBolnice() {
		return bolnice;
	}
	public static void setBolnice(ArrayList<Bolnica> bolnice) {
		Aplikacija.bolnice = bolnice;
	}
	public static ArrayList<Administrator> getAdministratori() {
		return administratori;
	}
	public static void setAdministratori(ArrayList<Administrator> administratori) {
		Aplikacija.administratori = administratori;
	}
	public static ArrayList<Doktor> getDoktori() {
		return doktori;
	}
	public static void setDoktori(ArrayList<Doktor> doktori) {
		Aplikacija.doktori = doktori;
	}
	public static ArrayList<Pacijent> getPacijenti() {
		return pacijenti;
	}
	public static void setPacijenti(ArrayList<Pacijent> pacijenti) {
		Aplikacija.pacijenti = pacijenti;
	}
	
	public static ArrayList<Lek> getLekovi() {
		return lekovi;
	}
	public static void setLekovi(ArrayList<Lek> lekovi) {
		Aplikacija.lekovi = lekovi;
	}
	public static ArrayList<Dijagnoza> getDijagnoze() {
		return dijagnoze;
	}
	public static void setDijagnoze(ArrayList<Dijagnoza> dijagnoze) {
		Aplikacija.dijagnoze = dijagnoze;
	}
	public static ArrayList<Recept> getRecepti() {
		return recepti;
	}
	public static void setRecepti(ArrayList<Recept> recepti) {
		Aplikacija.recepti = recepti;
	}
	public static ArrayList<Izvestaj> getIzvestaji() {
		return izvestaji;
	}
	public static void setIzvestaji(ArrayList<Izvestaj> izvestaji) {
		Aplikacija.izvestaji = izvestaji;
	}
	public static ArrayList<Pregled> getPregledi() {
		return pregledi;
	}
	public static void setPregledi(ArrayList<Pregled> pregledi) {
		Aplikacija.pregledi = pregledi;
	}
	public static ArrayList<Knjizica> getKnjizice() {
		return knjizice;
	}
	public static void setKnjizice(ArrayList<Knjizica> knjizice) {
		Aplikacija.knjizice = knjizice;
	}
	private static void obnoviListe() {
		if(bolnica==null) {
			bolnice=Bolnica.procitajFajlBolnice();
			lekovi=Lek.procitajFajlLekovi();
			dijagnoze=Dijagnoza.procitajFajlDijagnoze();
			recepti=Recept.procitajFajlRecepti();
			izvestaji=Izvestaj.procitajFajlIzvestaji();
			pregledi=Pregled.procitajFajlPregledi();
			knjizice=Knjizica.procitajFajlKnjizice();
			administratori=Administrator.procitajFajlAdministratori();
			doktori=Doktor.procitajFajlDoktori();
			pacijenti=Pacijent.procitajFajlPacijenti();
		}
		else {
			bolnica.setPacijenti(pacijenti);
			administratori=Administrator.procitajFajlAdministratori();
			doktori=Doktor.procitajFajlDoktori();
			bolnica.setDoktori(doktori);
			izvestaji=Izvestaj.procitajFajlIzvestaji();
			pregledi=Pregled.procitajFajlPregledi();
			knjizice=Knjizica.procitajFajlKnjizice();
			pacijenti=Pacijent.procitajFajlPacijenti();
		}
		
		for(int i=0;i<pacijenti.size();i++) {
			pacijenti.get(i).dodajKnjizicu();
		}
		for(int i=0;i<bolnice.size();i++) {
			bolnice.get(i).dodajKnjizice();
		}
		if(bolnica!=null) {
			bolnica.setDoktori(doktori);
			bolnica.setPacijenti(pacijenti);
			for(int i=0;i<izvestaji.size();i++) {
				izvestaji.get(i).setBolnica(bolnica);
			}
		}
	}
	private static boolean prijavaKorisnika() {
		int odluka=0;
		boolean tacno=false;
		while(!tacno) {
			obnoviListe();
			try{
					System.out.println("Dobrodosli\n"
							+ "1. Prijava\n"
							+ "2. Izlaz\n"
							+ "Izaberite sta zelite:");
					odluka=sc.nextInt();
					if(sc.hasNextLine()) {
						sc.nextLine();
					}
				}catch(InputMismatchException e) {
					System.out.println("Potrebno je da unesete broj, a ne slovo\n");
					sc.nextLine();
					continue;
				}
			if(odluka==1 || odluka==2) {
				tacno=true;
			}
			else {
				System.out.println("Uneli ste broj van opsega (1-2)\n");
			}
		}
		if(odluka==2) {
			System.out.println("Pozdrav\n");
			return true;
		}
		if(odluka==1) {
			System.out.println("\nUnesite vase korisnicko ime:");
			String koriscnickoIme=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			System.out.println("\nUnesite vasu lozinku:");
			String lozinka=sc.next();
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
			boolean nadjen=false;
			if(!nadjen) {
				for(int i=0;i<doktori.size();i++) {
					if(doktori.get(i).getKorisnickoIme().equalsIgnoreCase(koriscnickoIme) &&
							doktori.get(i).getLozinka().equalsIgnoreCase(lozinka)) {
						System.out.println("\nDobrodosli doktore\n");
						trenutnoUlogovaniKorisnik="doktor";
						indeksKorisnika=i;
						nadjen=true;
						return false;
					}
				}
			}
			if(!nadjen) {
				for(int i=0;i<pacijenti.size();i++) {
					if(pacijenti.get(i).getKorisnickoIme().equalsIgnoreCase(koriscnickoIme) &&
							pacijenti.get(i).getLozinka().equalsIgnoreCase(lozinka)) {
						System.out.println("\nDobrodosli pacijente\n");
						trenutnoUlogovaniKorisnik="pacijent";
						nadjen=true;
						indeksKorisnika=i;
						return false;
					}
				}
			}
			if(!nadjen) {
				for(int i=0;i<administratori.size();i++) {
					if(administratori.get(i).getKorisnickoIme().equalsIgnoreCase(koriscnickoIme) &&
							administratori.get(i).getLozinka().equalsIgnoreCase(lozinka)) {
						System.out.println("\nDobrodosli administratore\n");
						trenutnoUlogovaniKorisnik="administrator";
						nadjen=true;
						indeksKorisnika=i;
						return false;
					}
				}
			}
			System.out.println("Uneli ste pogresno korisnicko ime ili lozinku\n");
		}
		return false;
	}
	
	private static void meniPacijenta() {
		if(!trenutnoUlogovaniKorisnik.equalsIgnoreCase("pacijent")) {
			System.out.println("Greska, ovo je meni za pacijente,"+trenutnoUlogovaniKorisnik+"e"+"\n");
			return;
		}
		pacijent=pacijenti.get(indeksKorisnika);
		Bolnica.ubaciBolnicu();
		boolean izlazak=false;
		int izbor=0;
		while(!izlazak) {
			Bolnica.ubaciKorisnickaImena();
			obnoviListe();
			Bolnica.ubaciDoktoreIPacijente();
			Pregled.dodajKnjizicu();
			Izvestaj.dodajKnjizicu();
			try {
				System.out.println("1. Zakazivanje termina pregleda\n"
						+ "2. Overavanje knjizice\n"
						+ "3. Pregledanje zakazanih termina\n"
						+ "4. Odjava\n"
						+ "Izaberite sta zelite unosom rednog broja:");
				izbor=sc.nextInt();
				System.out.println();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
			}catch(InputMismatchException e) {
				System.out.println("Potrebno je da unesete broj, a ne slovo\n");
				sc.nextLine();
				continue;
			}
			switch(izbor) {
			case 1:
				pacijent.zakazivanjeTerminaPregleda();
				break;
			case 2:
				pacijent.overavanjeKnjizice();
				break;
			case 3:
				pacijent.pregledanjeZakazanihTermina();
				break;
			case 4:
				trenutnoUlogovaniKorisnik="";
				izlazak=true;
				break;
			default:
				System.out.println("Izabrali ste nepostojecu opciju\n");
			}
		}
	}
	
	private static void meniDoktora() {
		if(!trenutnoUlogovaniKorisnik.equalsIgnoreCase("doktor")) {
			System.out.println("Greska, ovo je meni za doktore,"+trenutnoUlogovaniKorisnik+"e"+"\n");
			return;
		}
		
		boolean izlazak=false;
		doktor=doktori.get(indeksKorisnika);
		Bolnica.ubaciBolnicu();
		while(!izlazak) {
			Bolnica.ubaciKorisnickaImena();
			obnoviListe();
			Bolnica.ubaciDoktoreIPacijente();
			Pregled.dodajKnjizicu();
			Izvestaj.dodajKnjizicu();
			int izbor=0;
			try {
				System.out.println("1. Pregledavanje\n"
						+ "2. Pregled dijagnoza pacijenta\n"
						+ "3. Odjava\n"
						+ "Izaberite sta zelite unosom rednog broja:");
				izbor=sc.nextInt();
				System.out.println();
				if(sc.hasNextLine()) {
					sc.nextLine();
			}
			}catch(InputMismatchException e) {
				System.out.println("Potrebno je da unesete broj, a ne slovo\n");
				sc.nextLine();
				continue;
			}
			switch(izbor) {
			case 1:
				doktor.pregledanje();
				break;
			case 2:
				doktor.pregledDijagnozaPacijenta();
				break;
			case 3:
				trenutnoUlogovaniKorisnik="";
				izlazak=true;
				break;
			default:
				System.out.println("Izabrali ste nepostojecu opciju\n");
			}
		}
	}
	
	private static void meniAdministratora() {
		if(!trenutnoUlogovaniKorisnik.equalsIgnoreCase("administrator")) {
			System.out.println("Greska, ovo je meni za administratore,"+trenutnoUlogovaniKorisnik+"e"+"\n");
			return;
		}
		administrator=administratori.get(indeksKorisnika);
		Bolnica.ubaciBolnicu();
		boolean izlazak=false;
		while(!izlazak) {
			Bolnica.ubaciKorisnickaImena();
			obnoviListe();
			Bolnica.ubaciDoktoreIPacijente();
			Pregled.dodajKnjizicu();
			Izvestaj.dodajKnjizicu();
			int izbor=0;
			try {
				System.out.println("1. Registracija pacijenta\n"
						+ "2. Registracija doktora\n"
						+ "3. Dodavanje leka\n"
						+ "4. Dodavanje dijagnoze\n"
						+ "5. Uklanjanje leka\n"
						+ "6. Uklanjanje dijagnoze\n"
						+ "7. Odjava\n"
						+ "Izaberite sta zelite unosom rednog broja:");
				izbor=sc.nextInt();
				System.out.println();
				if(sc.hasNextLine()) {
					sc.nextLine();
			}
			}catch(InputMismatchException e) {
				System.out.println("Potrebno je da unesete broj, a ne slovo\n");
				sc.nextLine();
				continue;
			}
			switch(izbor) {
			case 1:
				administrator.registracijaPacijenta();
				break;
			case 2:
				administrator.registracijaDoktora();
				break;
			case 3:
				administrator.dodavanjeLeka();
				break;
			case 4:
				administrator.dodavanjeDijagnoze();
				break;
			case 5:
				administrator.uklanjanjeLeka();
				break;
			case 6:
				administrator.uklanjanjeDijagnoze();
				break;
			case 7:
				trenutnoUlogovaniKorisnik="";
				izlazak=true;
				break;
			default:
				System.out.println("Izabrali ste nepostojecu opciju\n");
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean exit=false;
		while(!exit) {
			exit=prijavaKorisnika();
			switch(trenutnoUlogovaniKorisnik) {
			case "pacijent":
				meniPacijenta();
				break;
			case "doktor":
				meniDoktora();
				break;
			case "administrator":
				meniAdministratora();
				break;
			}
		}
		sc.close();
	}

}
