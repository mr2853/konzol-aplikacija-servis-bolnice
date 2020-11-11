package osoblje;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import administracija.Bolnica;
import administracija.Dijagnoza;
import administracija.Izvestaj;
import administracija.Lek;
import administracija.Pregled;
import administracija.Recept;
import aplikacija.Aplikacija;

public class Doktor extends Korisnik{
	private String specijalizacija;
	Scanner sc=new Scanner(System.in);
	
	public Doktor(String ime, String prezime, String korisnickoIme, String lozinka) {
		super(ime, prezime, korisnickoIme, lozinka);
		};
		
	public Doktor(String ime, String prezime, String korisnickoIme, String lozinka, String specijalizacija) {
		super(ime, prezime, korisnickoIme, lozinka);
		// TODO Auto-generated constructor stub
		this.specijalizacija=specijalizacija;
	}
	
	public String getSpecijalizacija() {
		return specijalizacija;
	}

	public void setSpecijalizacija(String specijalizacija) {
		this.specijalizacija = specijalizacija;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((specijalizacija == null) ? 0 : specijalizacija.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Doktor))
			return false;
		Doktor other = (Doktor) obj;
		if (specijalizacija == null) {
			if (other.specijalizacija != null)
				return false;
		} else if (!specijalizacija.equals(other.specijalizacija))
			return false;
		return true;
	}

	
	
	@Override
	public String toString() {
		return "Doktor [specijalizacija=" + specijalizacija + ", Ime=" + getIme() + ", Prezime="
				+ getPrezime() + ", KorisnickoIme=" + getKorisnickoIme() + ", Lozinka=" + getLozinka() + "]";
	}

	public static ArrayList<Doktor> procitajFajlDoktori() {
		ArrayList<Doktor> doktori=new ArrayList<Doktor>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/doktori.csv"));
			String row="";
			boolean prviRed=true;
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
				if(row.equalsIgnoreCase("﻿")) {
					continue;
				}
			    String[] data = row.split("\\|");
			    if(prviRed) {
			    	data[0]=data[0].substring(3);
			    	prviRed=false;
			    }
				if(Aplikacija.getBolnica()==null) {
					doktori.add(new Doktor(data[0],data[1],data[2],data[3], data[4]));
				}
				else{
					if(Aplikacija.getBolnica().getNaziv().equalsIgnoreCase(data[5])) {
						doktori.add(new Doktor(data[0],data[1],data[2],data[3], data[4]));
					}
				}
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl doktori nije nadjen\n");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla doktori\n");
		}
		if(Aplikacija.getBolnica()!=null) {
			Aplikacija.getBolnica().setDoktori(doktori);
		}
		return doktori;
	}
	
	public void pregledanje() {
		boolean tacno=false;
		while(!tacno) {
			BufferedReader csvReader=null;
			String row="";
			Bolnica bolnica = null;
			if(Aplikacija.getBolnica()!=null) {
				bolnica=Aplikacija.getBolnica();
			}
			
			ArrayList<Pregled> pregledi=Aplikacija.getPregledi();
			ArrayList<Pregled> preglediDoktora=new ArrayList<Pregled>(0);
			for(int i=0;i<pregledi.size();i++) {
				if(pregledi.get(i).getDoktor().getKorisnickoIme().equalsIgnoreCase(this.getKorisnickoIme()) &&
						String.valueOf(pregledi.get(i).getIzvestaj().getDatumIzdavanja()).equalsIgnoreCase("null")) {
					preglediDoktora.add(pregledi.get(i));
				}
			}
			int izbor=0;
			boolean petlja=false;
			while(!petlja) {
				try {
					for(int i=0;i<preglediDoktora.size();i++) {
						System.out.println((i+1)+". Vreme pocetka: "+preglediDoktora.get(i).getVremePocetka()
								+",\nIme pacijenta: "+preglediDoktora.get(i).getPacijent().getIme()
								+"\nPrezime pacijenta: "+preglediDoktora.get(i).getPacijent().getPrezime()+"\n");
					}
					if(preglediDoktora.isEmpty()) {
						System.out.println("Trenutno nemate zakazane preglede\n");
						return;
					}
					System.out.println("Izaberite pregled koji zelite da obavite: ");
					izbor=sc.nextInt();
					if(sc.hasNextLine()) {
						sc.nextLine();
					}
					izbor--;
					if(izbor>=0 && izbor<preglediDoktora.size()) {
						petlja=true;
					}
					else {
						System.out.println("Niste uneli broj u opsegu 1-"+preglediDoktora.size()+"\n");
					}
				}catch(InputMismatchException e) {
					System.out.println("Potrebno je da unesete broj, a ne slovo\n");
					sc.nextLine();
					continue;
				}
			}
			Pregled pregled=preglediDoktora.get(izbor);
			ArrayList<Dijagnoza> izabraneDijagnoze=new ArrayList<Dijagnoza>(0);
			ArrayList<Recept> izabraniRecepti=new ArrayList<Recept>(0);
			Izvestaj izvestaj=new Izvestaj(LocalDateTime.now().withNano(0),false,bolnica,pregled.getPacijent(),this,izabraneDijagnoze,izabraniRecepti);
			ArrayList<Dijagnoza> dijagnoze=Dijagnoza.procitajFajlDijagnoze();
			
			boolean novaDijagnoza=false;
			while(!novaDijagnoza) {
				System.out.println("Zelite li da dodelite dijagnozu pacijentu(da/ne)?");
				String izbor1=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				if(izbor1.equalsIgnoreCase("da")) {
					if(dijagnoze.size() == 0 && izabraneDijagnoze.size() != 0) {
						System.out.println("Trenutno nemamo preostalih dijagnoza\n");
						novaDijagnoza=true;
						break;
					}
					if(dijagnoze.size() == 0 && izabraneDijagnoze.size() == 0) {
						System.out.println("Trenutno nemamo dijagnoza\n");
						novaDijagnoza=true;
						break;
					}

					try {
						for(int i=0;i<dijagnoze.size();i++) {
							System.out.println((i+1)+". "+dijagnoze.get(i).getNaziv());
						}
						System.out.println("Izaberite zeljenu dijagnozu: ");
						izbor=sc.nextInt();
						if(sc.hasNextLine()) {
							sc.nextLine();
						}
					}catch(InputMismatchException e) {
						System.out.println("Potrebno je da unesete broj, a ne slovo\n");
						sc.nextLine();
						continue;
					}
					if(izbor<0 || izbor>dijagnoze.size()) {
						System.out.println("Izabrali ste nepostojecu opciju\n");
						continue;
					}
					izbor--;
					izabraneDijagnoze.add(dijagnoze.get(izbor));
					dijagnoze.remove(izbor);
				}
				else if(izbor1.equalsIgnoreCase("ne")) {
					novaDijagnoza=true;
				}
				else {
					System.out.println("Niste uneli tacan tekst ( da ili ne )\n");
					continue;
				}
			}
			izvestaj.setDijagnoze(izabraneDijagnoze);
			ArrayList<Lek> dozvoljeniLekovi=new ArrayList<Lek>(0);
			for(int i=0;i<izabraneDijagnoze.size();i++) {
				for(int j=0;j<izabraneDijagnoze.get(i).getLekovi().size();j++) {
					dozvoljeniLekovi.add(izabraneDijagnoze.get(i).getLekovi().get(j));
				}
			}
			if(!dozvoljeniLekovi.isEmpty()) {
				boolean noviRecept=false;
				while(!noviRecept) {
					System.out.println("Zelite li da dodelite recept pacijentu(da/ne)?");
					String izbor1=sc.next();
					if(sc.hasNextLine()) {
						sc.nextLine();
					}
					if(izbor1.equalsIgnoreCase("da")) {
						if(dozvoljeniLekovi.size() == 0) {
							System.out.println("Trenutno nemamo preostalih lekova\n");
							noviRecept=true;
							break;
						}
						boolean noviLek=false;
						ArrayList<Lek> izabraniLekovi=new ArrayList<Lek>(0);
						while(!noviLek) {
							System.out.println("Zelite li da dodelite novi lek receptu(da/ne)?");
							String izbor2=sc.next();
							if(sc.hasNextLine()) {
								sc.nextLine();
							}
							if(izbor2.equalsIgnoreCase("da")) {
								if(noviRecept == true || dozvoljeniLekovi.size() == 0) {
									System.out.println("Trenutno nemamo preostalih lekova\n");
									noviRecept=true;
									noviLek=true;
									break;
								}
								petlja=false;
								while(!petlja) {
									try {
										for(int i=0;i<dozvoljeniLekovi.size();i++) {
											System.out.println((i+1)+". "+dozvoljeniLekovi.get(i).getNaziv());
										}
										System.out.println("Izaberite lek koji zelite da dodelite receptu:");
										izbor=sc.nextInt();
										if(sc.hasNextLine()) {
											sc.nextLine();
										}
										izbor--;
										if(izbor>=0 && izbor<dozvoljeniLekovi.size()) {
											petlja=true;
										}
										else {
											System.out.println("Niste uneli broj u opsegu 1-"+dozvoljeniLekovi.size()+"\n");
										}
									}catch(InputMismatchException e) {
										System.out.println("Potrebno je da unesete broj, a ne slovo\n");
										sc.nextLine();
										continue;
									}
								}
								izabraniLekovi.add(dozvoljeniLekovi.get(izbor));
								dozvoljeniLekovi.remove(izbor);
							}
							else if(izbor2.equalsIgnoreCase("ne")) {
								noviLek=true;
							}
							else {
								System.out.println("Niste uneli tacan tekst ( da ili ne )\n");
							}
						}
						izabraniRecepti.add(new Recept(false,izabraniLekovi));
					}
					else if(izbor1.equalsIgnoreCase("ne")) {
						noviRecept=true;
					}
					else {
						System.out.println("Niste uneli tacan tekst ( da ili ne )\n");
						continue;
					}
				}	
			}
			izvestaj.setRecepti(izabraniRecepti);
			boolean zavrsenoOveravanje=false;
			while(!zavrsenoOveravanje) {
				System.out.println("Da li zelite da overite izdate dokumente(da/ne)?");
				String izbor1=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				if(izbor1.equalsIgnoreCase("da")) {
					if(!izabraniRecepti.isEmpty()) {
						for(int i=0;i<izabraniRecepti.size();i++) {
							izabraniRecepti.get(i).overi();
						}
					}
					izvestaj.setOveren(true);
					System.out.println(izvestaj.toString());
					zavrsenoOveravanje=true;
				}
				else if(izbor1.equalsIgnoreCase("ne")) {
					zavrsenoOveravanje=true;
				}
				else {
					System.out.println("Niste uneli u tacan tekst ( da ili ne )\n");
				}
			}
			try {
				if(izabraniRecepti.size()!=0) {
					ArrayList<String> staro=new ArrayList<String>(0);
					csvReader= new BufferedReader(new FileReader("data/recepti.csv"));
					row="";
					while((row = csvReader.readLine()) != null) {
						staro.add(row);
					}
					csvReader.close();
					FileWriter csv=new FileWriter("data/recepti.csv");
					for(int i=0;i<staro.size();i++) {
						csv.append(staro.get(i));
						csv.append("\n");
					}
					for(int i=0;i<izabraniRecepti.size();i++) {
						csv.append(String.valueOf(izabraniRecepti.get(i).isOveren()));
						for(int j=0;j<izabraniRecepti.get(i).getLekovi().size();j++) {
							csv.append("|");
							csv.append(izabraniRecepti.get(i).getLekovi().get(j).getNaziv());
							csv.append("|");
							csv.append(String.valueOf(izabraniRecepti.get(i).getLekovi().get(j).isUvozni()));
						}
						csv.append("\n");
					}
					csv.flush();
					csv.close();
				}
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl recepti nije nadjen\n");
			}catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla recepti\n");
			}
			
			try {
				ArrayList<String> staro=new ArrayList<String>(0);
				csvReader= new BufferedReader(new FileReader("data/izvestaji.csv"));
				row="";
				while((row = csvReader.readLine()) != null) {
					staro.add(row);
				}
				csvReader.close();
				
				FileWriter csv=new FileWriter("data/izvestaji.csv");
				for(int i=0;i<staro.size();i++) {
					boolean sadrzi=false;
					if(staro.get(i).contains(pregled.getPacijent().getKorisnickoIme()) &&
							staro.get(i).contains(String.valueOf(pregled.getVremePocetka())) &&
							staro.get(i).contains(pregled.getDoktor().getKorisnickoIme())) {
						sadrzi=true;
					}
					if(!sadrzi) {
						csv.append(staro.get(i));
						csv.append("\n");
					}
				}
				csv.append(String.valueOf(izvestaj.getDatumIzdavanja()));
				csv.append("|");
				csv.append(String.valueOf(izvestaj.isOveren()));
				csv.append("|");
				csv.append(izvestaj.getBolnica().getNaziv());
				csv.append("|");
				csv.append(izvestaj.getPacijent().getKorisnickoIme());
				csv.append("|");
				csv.append(izvestaj.getDoktor().getKorisnickoIme());
				csv.append("|");
				if(izabraneDijagnoze.size()!=0) {
					for(int i=0;i<izabraneDijagnoze.size();i++) {
						csv.append(izabraneDijagnoze.get(i).getNaziv());
						csv.append("*");
						csv.append(String.valueOf(izabraneDijagnoze.get(i).getSifra()));
						csv.append("*");
						for(int j=0;j<izabraneDijagnoze.get(i).getLekovi().size();j++) {
							csv.append(izabraneDijagnoze.get(i).getLekovi().get(j).getNaziv());
							csv.append("+");
							csv.append(String.valueOf(izabraneDijagnoze.get(i).getLekovi().get(j).isUvozni()));
							if(j<izabraneDijagnoze.get(i).getLekovi().size()-1) {
								csv.append("*");
							}
							else {
								csv.append("+");
							}
						}
						if(i==izabraneDijagnoze.size()-1) {
							csv.append("|");
						}
						else {
							csv.append("*");
						}
					}
				}
				if(izabraniRecepti.size()!=0) {
					for(int i=0;i<izabraniRecepti.size();i++) {
						csv.append(String.valueOf(izabraniRecepti.get(i).isOveren()));
						csv.append("+");
						for(int j=0;j<izabraniRecepti.get(i).getLekovi().size();j++) {
							csv.append(izabraniRecepti.get(i).getLekovi().get(j).getNaziv());
							csv.append("+");
							csv.append(String.valueOf(izabraniRecepti.get(i).getLekovi().get(j).isUvozni()));
							if(j<izabraniRecepti.get(i).getLekovi().size()-1) {
								csv.append("+");
							}
						}
						if(i < izabraniRecepti.size()-1) {
							csv.append("*");
						}
					}
				}
				csv.flush();
				csv.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl izvestaji nije nadjen\n");
			}catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla izvestaji\n");
			}
			
			try {
				ArrayList<String> staro=new ArrayList<String>(0);
				csvReader= new BufferedReader(new FileReader("data/pregledi.csv"));
				row="";
				while((row = csvReader.readLine()) != null) {
					staro.add(row);
				}
				csvReader.close();
				FileWriter csv=new FileWriter("data/pregledi.csv");
				for(int i=0;i<staro.size();i++) {
					if(staro.get(i).contains(String.valueOf(pregled.getVremePocetka().withNano(0))) &&
							staro.get(i).contains(pregled.getDoktor().getKorisnickoIme()) &&
							staro.get(i).contains(pregled.getPacijent().getKorisnickoIme())) {
						String[] data=staro.get(i).split("\\|");
						String[] data1=data[3].split("\\*");
						data1[0]=String.valueOf(izvestaj.getDatumIzdavanja().withNano(0));
						data1[1]=String.valueOf(izvestaj.getPacijent().getKorisnickoIme());
						data1[2]=String.valueOf(izvestaj.getBolnica().getNaziv());
						csv.append(data[0]);
						csv.append("|");
						csv.append(data[1]);
						csv.append("|");
						csv.append(data[2]);
						csv.append("|");
						csv.append(data1[0]);
						csv.append("*");
						csv.append(data1[1]);
						csv.append("*");
						csv.append(data1[2]);
						csv.append("\n");
						continue;
					}
					csv.append(staro.get(i));
					csv.append("\n");
					continue;
				}
				
				csv.flush();
				csv.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl pregledi nije nadjen\n");
			}catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla pregledi\n");
			}
			
			try {
				//if(izabraniRecepti.size()!=0) {
					ArrayList<String[]> staro=new ArrayList<String[]>(0);
					csvReader= new BufferedReader(new FileReader("data/knjizice.csv"));
					row="";
					while((row = csvReader.readLine()) != null) {
						staro.add(row.split("\\|"));
					}
					csvReader.close();
					FileWriter csv=new FileWriter("data/knjizice.csv");
					for(int i=0;i<staro.size();i++) {
						boolean sadrzi=true;
						if(!staro.get(i)[0].equalsIgnoreCase(String.valueOf(pregled.getPacijent().getKnjizica().getIdentifikator()))) {
							csv.append(staro.get(i)[0]);
							csv.append("|");
							csv.append(staro.get(i)[1]);
							csv.append("|");
							csv.append(staro.get(i)[2]);
							csv.append("|");
							csv.append(staro.get(i)[3]);
							csv.append("\n");
							sadrzi=false;
						}
						if(sadrzi) {
							staro.get(i)[2]=String.valueOf(izvestaj.getDatumIzdavanja())+"*"
									+izvestaj.getPacijent().getKorisnickoIme()+"*"
									+izvestaj.getBolnica().getNaziv();
							csv.append(staro.get(i)[0]);
							csv.append("|");
							csv.append(staro.get(i)[1]);
							csv.append("|");
							csv.append(staro.get(i)[2]);
							csv.append("|");
							csv.append(staro.get(i)[3]);
							csv.append("\n");
						}
					}
					csv.flush();
					csv.close();
				//}
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl knjizice nije nadjen\n");
			}catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla knjizice\n");
			}
			
			tacno=true;
		}
		 
	}
	
	public void pregledDijagnozaPacijenta() {
		ArrayList<Pacijent> pacijenti=Aplikacija.getPacijenti();
		for(int i=0;i<pacijenti.size();i++) {
			pacijenti.get(i).dodajKnjizicu();
		}
		boolean gotovo=false;
		while(!gotovo) {
			boolean odradjeno=false;
			int izbor=0;
			while(!odradjeno) {
				try {
					System.out.println("Kako zelite da trazite pacijente:\n"
							+"1. Po knjizici\n"
							+"2. Po imenu i prezimenu\n");
					izbor=sc.nextInt();
					if(sc.hasNextLine()) {
						sc.nextLine();
					}
					if(izbor == 1 || izbor == 2) {
						odradjeno=true;
					}
					else {
						System.out.println("Izabrali ste nepostojecu opciju");
						continue;
					}
				}catch(InputMismatchException e) {
					System.out.println("Potrebno je da unesete broj, a ne slovo\n");
					sc.nextLine();
					continue;
				}
			}
			if(izbor==1) {
				long izborL=-1;
				odradjeno=false;
				while(!odradjeno) {
					try {
						System.out.println("Unesite broj knjizice pacijenta:");
						izborL=sc.nextLong();
						if(sc.hasNextLine()) {
							sc.nextLine();
						}
						odradjeno=true;
					}catch(InputMismatchException e) {
						System.out.println("Potrebno je da unesete broj, a ne slovo\n");
						sc.nextLine();
						continue;
					}
				}
				ArrayList<Pacijent> izabraniPacijenti=new ArrayList<Pacijent>(0);
				ArrayList<Pregled> pregledi=Pregled.procitajFajlPregledi();
				for(int i=0;i<pacijenti.size();i++) {
					if(pacijenti.get(i).getKnjizica().getIdentifikator() == izborL) {
						izabraniPacijenti.add(pacijenti.get(i));
					}
				}
				if(izabraniPacijenti.isEmpty()) {
					System.out.println("Nepostoji pacijent sa zadatom knjizicom");
					gotovo=true;
					break;
				}
				odradjeno=false;
				while(!odradjeno) {
					for(int i=0;i<izabraniPacijenti.size();i++) {
						System.out.println((i+1)+". "+izabraniPacijenti.get(i).getIme()+" "+izabraniPacijenti.get(i).getPrezime());
					}
					try {
						System.out.println("Izaberite zeljenog pacijenta:");
						izbor=sc.nextInt();
						if(sc.hasNextLine()) {
							sc.nextLine();
						}
						if(izbor>0 && izbor<=izabraniPacijenti.size()) {
							izbor--;
							odradjeno=true;
						}
						else {
							System.out.println("Niste uneli broj u opsegu 1-"+izabraniPacijenti.size()+"\n");
						}
					}catch(InputMismatchException e) {
						System.out.println("Potrebno je da unesete broj, a ne slovo\n");
						sc.nextLine();
						continue;
					}
				}
				Pacijent pacijent=izabraniPacijenti.get(izbor);
				ArrayList<Dijagnoza> dijagnozePacijenta=new ArrayList<Dijagnoza>(0);
				ArrayList<Dijagnoza> jedinstveneDijagnoze=new ArrayList<Dijagnoza>(0);
				for(int i=0;i<pregledi.size();i++) {
					if(pregledi.get(i).getPacijent().getKorisnickoIme().equalsIgnoreCase(pacijent.getKorisnickoIme())) {
						if(pregledi.get(i).getIzvestaj().getDijagnoze() != null) {
							for(int j=0;j<pregledi.get(i).getIzvestaj().getDijagnoze().size();j++) {
								dijagnozePacijenta.add(pregledi.get(i).getIzvestaj().getDijagnoze().get(j));
							}
						}
					}
				}
				if(!dijagnozePacijenta.isEmpty()) {
					jedinstveneDijagnoze.add(dijagnozePacijenta.get(0));
				}
				for(int i=0;i<dijagnozePacijenta.size();i++) {
					for(int j=0;j<jedinstveneDijagnoze.size();j++) {
						if(!dijagnozePacijenta.get(i).getNaziv().equalsIgnoreCase(jedinstveneDijagnoze.get(j).getNaziv()) &&
								dijagnozePacijenta.get(i).getSifra() != jedinstveneDijagnoze.get(j).getSifra()) {
							jedinstveneDijagnoze.add(dijagnozePacijenta.get(i));
						}
					}
				}
				if(!jedinstveneDijagnoze.isEmpty()) {
					System.out.println("Dijagnoze pacijenta:\n");
					for(int i=0;i<jedinstveneDijagnoze.size();i++) {
						System.out.println("Sifra: "+jedinstveneDijagnoze.get(i).getSifra()+"\n"
								+"Naziv: "+jedinstveneDijagnoze.get(i).getNaziv());
					}
					gotovo=true;
				}
				else {
					System.out.println("Izabrani pacijent trenutno nema zadatih dijagnoza\n");
					gotovo=true;
				}
			}
			else if(izbor==2) {
				System.out.println("Unesite ime pacijenta:");
				String ime=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				System.out.println("Unesite prezime pacijenta:");
				String prezime=sc.next();
				if(sc.hasNextLine()) {
					sc.nextLine();
				}
				
				ArrayList<Pacijent> izabraniPacijenti=new ArrayList<Pacijent>(0);
				ArrayList<Pregled> pregledi=Pregled.procitajFajlPregledi();
				for(int i=0;i<pacijenti.size();i++) {
					if(pacijenti.get(i).getIme().equalsIgnoreCase(ime) &&
							pacijenti.get(i).getPrezime().equalsIgnoreCase(prezime)) {
						izabraniPacijenti.add(pacijenti.get(i));
					}
				}
				odradjeno=false;
				while(!odradjeno) {
					for(int i=0;i<izabraniPacijenti.size();i++) {
						System.out.println((i+1)+". "+izabraniPacijenti.get(i).getIme()+" "+izabraniPacijenti.get(i).getPrezime());
					}
					try {
						System.out.println("Izaberite zeljenog pacijenta:");
						izbor=sc.nextInt();
						if(sc.hasNextLine()) {
							sc.nextLine();
						}
						if(izbor>0 && izbor<=izabraniPacijenti.size()) {
							izbor--;
							odradjeno=true;
						}
						else {
							System.out.println("Niste uneli broj u opsegu 1-"+izabraniPacijenti.size()+"\n");
						}
					}catch(InputMismatchException e) {
						System.out.println("Potrebno je da unesete broj, a ne slovo\n");
						sc.nextLine();
						continue;
					}
				}
				Pacijent pacijent=izabraniPacijenti.get(izbor);
				ArrayList<Dijagnoza> dijagnozePacijenta=new ArrayList<Dijagnoza>(0);
				ArrayList<Dijagnoza> jedinstveneDijagnoze=new ArrayList<Dijagnoza>(0);
				for(int i=0;i<pregledi.size();i++) {
					if(pregledi.get(i).getPacijent().getKorisnickoIme().equalsIgnoreCase(pacijent.getKorisnickoIme())) {
						for(int j=0;j<pregledi.get(i).getIzvestaj().getDijagnoze().size();j++) {
							dijagnozePacijenta.add(pregledi.get(i).getIzvestaj().getDijagnoze().get(j));
						}
					}
				}
				if(!dijagnozePacijenta.isEmpty()) {
					jedinstveneDijagnoze.add(dijagnozePacijenta.get(0));
				}
				for(int i=0;i<dijagnozePacijenta.size();i++) {
					for(int j=0;j<jedinstveneDijagnoze.size();j++) {
						if(!dijagnozePacijenta.get(i).getNaziv().equalsIgnoreCase(jedinstveneDijagnoze.get(j).getNaziv()) &&
								dijagnozePacijenta.get(i).getSifra() != jedinstveneDijagnoze.get(j).getSifra()) {
							jedinstveneDijagnoze.add(dijagnozePacijenta.get(i));
						}
					}
				}
				if(!jedinstveneDijagnoze.isEmpty()) {
					System.out.println("Dijagnoze pacijenta:\n");
					for(int i=0;i<jedinstveneDijagnoze.size();i++) {
						System.out.println("Sifra: "+jedinstveneDijagnoze.get(i).getSifra()+"\n"
								+"Naziv: "+jedinstveneDijagnoze.get(i).getNaziv());
					}
					gotovo=true;
				}
				else {
					System.out.println("Izabrani pacijent trenutno nema zadatih dijagnoza\n");
					gotovo=true;
				}
			}
			else {
				System.out.println("Uneli ste broj van opsega (1-2)\nProbajte ponovo\n");
			}
		}
		 
	}
}
