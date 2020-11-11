package administracija;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import aplikacija.Aplikacija;
import osoblje.Administrator;
import osoblje.Doktor;
import osoblje.Pacijent;

public class Bolnica {
	private String naziv;
	private ArrayList<Doktor> doktori=new ArrayList<Doktor>(0);
	private ArrayList<Pacijent> pacijenti=new ArrayList<Pacijent>(0);
	public Bolnica() {};
	public Bolnica(String naziv, ArrayList<Doktor> doktori, ArrayList<Pacijent> pacijenti) {
		this.naziv = naziv;
		this.doktori = doktori;
		this.pacijenti = pacijenti;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public ArrayList<Doktor> getDoktori() {
		return doktori;
	}

	public void setDoktori(ArrayList<Doktor> doktori) {
		this.doktori = doktori;
	}

	public ArrayList<Pacijent> getPacijenti() {
		return pacijenti;
	}

	public void setPacijenti(ArrayList<Pacijent> pacijenti) {
		this.pacijenti = pacijenti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doktori == null) ? 0 : doktori.hashCode());
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + ((pacijenti == null) ? 0 : pacijenti.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Bolnica))
			return false;
		Bolnica other = (Bolnica) obj;
		if (doktori == null) {
			if (other.doktori != null)
				return false;
		} else if (!doktori.equals(other.doktori))
			return false;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (pacijenti == null) {
			if (other.pacijenti != null)
				return false;
		} else if (!pacijenti.equals(other.pacijenti))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bolnica [naziv=" + naziv + ", doktori=" + doktori.toString() + ", pacijenti=" + pacijenti.toString() + "]";
	}
	
	public static ArrayList<Bolnica> procitajFajlBolnice(){
		ArrayList<Bolnica> bolnice=new ArrayList<Bolnica>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/bolnice.csv"));
			String row="";
			boolean prviRed=true;
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
			    String[] data = row.split("\\|");
				BufferedReader csvReader1 = new BufferedReader(new FileReader("data/doktori.csv"));
				String row1 ="";
				ArrayList<Doktor> doktori=new ArrayList<Doktor>(0);
				while ((row1 = csvReader1.readLine()) != null) {
				    String[] data1 = row1.split("\\|");
				    if(prviRed) {
				    	data1[0]=data1[0].substring(3);
				    	prviRed=false;
				    }
				    if(data1[5].equalsIgnoreCase(data[0])) {
				    	doktori.add(new Doktor(data1[0],data1[1],data1[2],data1[3],data1[4]));
				    }
				}
				csvReader1.close();
				BufferedReader csvReader2 = new BufferedReader(new FileReader("data/pacijenti.csv"));
				String row2="";
				ArrayList<Pacijent> pacijenti=new ArrayList<Pacijent>(0);
				while ((row2 = csvReader2.readLine()) != null) {
				    String[] data1 = row2.split("\\|");
				    if(data1[5].equalsIgnoreCase(data[0])) {
				    	//ArrayList<Knjizica> knjizice=Knjizica.procitajFajlKnjizice();
						Knjizica knjizica=null;
						/*for(int i=0;i<knjizice.size();i++) {
							if(knjizice.get(i).getIdentifikator()==Long.valueOf(data1[4])){
								knjizica=knjizice.get(i);
							}
						}*/
						if(data1[5].equalsIgnoreCase(data[0])) {
							pacijenti.add(new Pacijent(data1[0],data1[1],data1[2],data1[3],knjizica));
						}
				    }
				}
				csvReader2.close();
				bolnice.add(new Bolnica(data[0],doktori,pacijenti));
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl bolnice nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla bolnice");
		}
		return bolnice;
	}
	public void dodajKnjizice() {
		try {
		BufferedReader csvReader = new BufferedReader(new FileReader("data/pacijenti.csv"));
		String row="";
		ArrayList<Knjizica> knjizice=Knjizica.procitajFajlKnjizice();
		int brojacRedova=0;
		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split("\\|");
			
				/*System.out.println("Knjizice size: "+knjizice.size());
				System.out.println("data length: "+data.length);
				System.out.println(brojacRedova);
				System.out.println(this.getPacijenti());
				System.out.println(this.getPacijenti().get(brojacRedova));
				System.out.println(knjizice.get(brojacRedova));*/
		    if(this.getPacijenti().size() > brojacRedova) {
				for(int i=0;i<knjizice.size();i++) {
					if(knjizice.get(i).getIdentifikator()==Long.valueOf(data[4])) {
						this.getPacijenti().get(brojacRedova).setKnjizica(knjizice.get(i));
						break;
					}
				}
		    }
			brojacRedova++;
		}
		
		csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl pacijenti nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla pacijenti");
		}
	}
	
	public static void ubaciBolnicu() {
		if(Aplikacija.getTrenutnoUlogovaniKorisnik().equalsIgnoreCase("doktor")) {
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/doktori.csv"));
				String row ="";
				while ((row = csvReader.readLine()) != null) {
				    String[] data = row.split("\\|");
				    if(data[2].equalsIgnoreCase(Aplikacija.getDoktor().getKorisnickoIme())) {
				    	Bolnica bolnica=new Bolnica(data[5],new ArrayList<Doktor>(0), new ArrayList<Pacijent>(0));
				    	Aplikacija.setBolnica(bolnica);
				    	//Izvestaj.setBolnica(bolnica);
				    }
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl doktori nije nadjen");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla doktori");
			}
		}
		else if(Aplikacija.getTrenutnoUlogovaniKorisnik().equalsIgnoreCase("pacijent")) {
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/pacijenti.csv"));
				String row ="";
				while ((row = csvReader.readLine()) != null) {
				    String[] data = row.split("\\|");
				    if(data[2].equalsIgnoreCase(Aplikacija.getPacijent().getKorisnickoIme())) {
				    	Aplikacija.setBolnica(new Bolnica(data[5],new ArrayList<Doktor>(0), new ArrayList<Pacijent>(0)));
				    }
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl pacijenti nije nadjen");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla pacijenti");
			}
		}
		else if(Aplikacija.getTrenutnoUlogovaniKorisnik().equalsIgnoreCase("administrator")) {
			try {
				BufferedReader csvReader = new BufferedReader(new FileReader("data/administratori.csv"));
				String row ="";
				while ((row = csvReader.readLine()) != null) {
				    String[] data = row.split("\\|");
				    if(data[2].equalsIgnoreCase(Aplikacija.getAdministrator().getKorisnickoIme())) {
				    	Aplikacija.setBolnica(new Bolnica(data[4],new ArrayList<Doktor>(0), new ArrayList<Pacijent>(0)));
				    }
				}
				csvReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("Zao nam je fajl administratori nije nadjen");
			}
			catch(IOException e) {
				System.out.println("Zao nam je, imamo gresku u citanju fajla administratori");
			}
		}
		else {
			System.out.println("Greska, trenutno ulogovani korisnik je prazan tekst");
		}
	}
	
	public static void ubaciKorisnickaImena() {
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/doktori.csv"));
			String row ="";
			while ((row = csvReader.readLine()) != null) {
			    String[] data = row.split("\\|");
			    if(data[5].equalsIgnoreCase(Aplikacija.getBolnica().getNaziv())) {
			    	ArrayList<String> doktori=Aplikacija.getDoktoriBolnice();
			    	doktori.add(data[2]);
			    	Aplikacija.setDoktoriBolnice(doktori);
			    }
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl doktori nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla doktori");
		}
		
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/pacijenti.csv"));
			String row ="";
			while ((row = csvReader.readLine()) != null) {
			    String[] data = row.split("\\|");
			    if(data[5].equalsIgnoreCase(Aplikacija.getBolnica().getNaziv())) {
			    	ArrayList<String> pacijenti=Aplikacija.getPacijentiBolnice();
			    	pacijenti.add(data[2]);
			    	Aplikacija.setPacijentiBolnice(pacijenti);
			    }
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl pacijenti nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla pacijenti");
		}
	}
	
	public static void ubaciDoktoreIPacijente() {
		//ArrayList<String> doktoriBolnice=Aplikacija.getDoktoriBolnice();
		//ArrayList<String> pacijentiBolnice=Aplikacija.getPacijentiBolnice();
		ArrayList<Doktor> doktori=Aplikacija.getDoktori();
		ArrayList<Pacijent> pacijenti=Aplikacija.getPacijenti();
		Bolnica bolnica=Aplikacija.getBolnica();
		/*for(int i=0;i<doktoriBolnice.size();i++) {
			i
		}*/
		bolnica.setDoktori(doktori);
		bolnica.setPacijenti(pacijenti);
		Aplikacija.setBolnica(bolnica);
	}
}
