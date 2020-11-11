package administracija;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import aplikacija.Aplikacija;
import osoblje.Doktor;
import osoblje.Pacijent;

public class Izvestaj implements Dokument{
	private LocalDateTime datumIzdavanja;
	private boolean overen;
	private static Bolnica bolnica;
	private Pacijent pacijent;
	private Doktor doktor;
	private ArrayList<Dijagnoza> dijagnoze;
	private ArrayList<Recept> recepti;
	public Izvestaj() {};
	public Izvestaj(LocalDateTime datumIzdavanja, boolean overen, Bolnica bolnica, Pacijent pacijent, Doktor doktor,
			ArrayList<Dijagnoza> dijagnoze, ArrayList<Recept> recepti) {
		super();
		this.datumIzdavanja = datumIzdavanja;
		this.overen = overen;
		Izvestaj.bolnica = bolnica;
		this.pacijent = pacijent;
		this.doktor = doktor;
		this.dijagnoze = dijagnoze;
		this.recepti = recepti;
	}

	public LocalDateTime getDatumIzdavanja() {
		return datumIzdavanja;
	}

	public void setDatumIzdavanja(LocalDateTime datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}

	public boolean isOveren() {
		return overen;
	}

	public void setOveren(boolean overen) {
		this.overen = overen;
	}

	public Bolnica getBolnica() {
		return bolnica;
	}

	public void setBolnica(Bolnica bolnica) {
		Izvestaj.bolnica = bolnica;
	}

	public Pacijent getPacijent() {
		return pacijent;
	}

	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}

	public Doktor getDoktor() {
		return doktor;
	}

	public void setDoktor(Doktor doktor) {
		this.doktor = doktor;
	}

	public ArrayList<Dijagnoza> getDijagnoze() {
		return dijagnoze;
	}

	public void setDijagnoze(ArrayList<Dijagnoza> dijagnoze) {
		this.dijagnoze = dijagnoze;
	}

	public ArrayList<Recept> getRecepti() {
		return recepti;
	}

	public void setRecepti(ArrayList<Recept> recepti) {
		this.recepti = recepti;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bolnica == null) ? 0 : bolnica.hashCode());
		result = prime * result + ((datumIzdavanja == null) ? 0 : datumIzdavanja.hashCode());
		result = prime * result + ((dijagnoze == null) ? 0 : dijagnoze.hashCode());
		result = prime * result + ((doktor == null) ? 0 : doktor.hashCode());
		result = prime * result + (overen ? 1231 : 1237);
		result = prime * result + ((pacijent == null) ? 0 : pacijent.hashCode());
		result = prime * result + ((recepti == null) ? 0 : recepti.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Izvestaj))
			return false;
		Izvestaj other = (Izvestaj) obj;
		if (bolnica == null) {
			if (Izvestaj.bolnica != null)
				return false;
		} else if (!bolnica.equals(Izvestaj.bolnica))
			return false;
		if (datumIzdavanja == null) {
			if (other.datumIzdavanja != null)
				return false;
		} else if (!datumIzdavanja.equals(other.datumIzdavanja))
			return false;
		if (dijagnoze == null) {
			if (other.dijagnoze != null)
				return false;
		} else if (!dijagnoze.equals(other.dijagnoze))
			return false;
		if (doktor == null) {
			if (other.doktor != null)
				return false;
		} else if (!doktor.equals(other.doktor))
			return false;
		if (overen != other.overen)
			return false;
		if (pacijent == null) {
			if (other.pacijent != null)
				return false;
		} else if (!pacijent.equals(other.pacijent))
			return false;
		if (recepti == null) {
			if (other.recepti != null)
				return false;
		} else if (!recepti.equals(other.recepti))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String pocetak="Izvestaj [";

		if(this.getDatumIzdavanja() != null) {
			if(!String.valueOf(this.getDatumIzdavanja()).isEmpty()) {
				pocetak=pocetak.concat("datumIzdavanja=" + this.getDatumIzdavanja());
			}
		}
		if(!String.valueOf(this.isOveren()).isEmpty()) {
			pocetak=pocetak.concat(String.valueOf(", overen=" +this.isOveren()));
		}
		if(this.getBolnica() != null) {
			if(this.getBolnica().getNaziv() != null) {
				if(!this.getBolnica().getNaziv().isEmpty()) {
					pocetak=pocetak.concat(", bolnica=" +this.getBolnica().getNaziv());
				}
			}
		}
		if(this.getPacijent() != null) {
			if(this.getPacijent().getKorisnickoIme() != null) {
				if(!this.getPacijent().getKorisnickoIme().isEmpty()) {
					pocetak=pocetak.concat(", pacijent=" + this.getPacijent().getKorisnickoIme());
				}
			}
		}
		
		if(this.getDoktor() != null) {
			if(this.getDoktor().getKorisnickoIme() != null) {
				if(!this.getDoktor().getKorisnickoIme().isEmpty()) {
					pocetak=pocetak.concat(", doktor=" + this.getDoktor().getKorisnickoIme());
				}
			}
		}
		
		if(this.getDijagnoze() != null && !this.getDijagnoze().isEmpty()) {
			if(this.getDijagnoze().toString() != null) {
				pocetak=pocetak.concat(this.getDijagnoze().toString());
			}
		}
		
		if(this.getRecepti() != null && !this.getRecepti().isEmpty()) {
			if(this.getRecepti().toString() != null) {
				pocetak=pocetak.concat(this.getRecepti().toString());
			}
		}
		
		pocetak=pocetak.concat("]");
		return pocetak;
	}
	
	public void overi() {
		if(this.isOveren()) {
			System.out.println("Izvestaj je vec overen");
		}
		else {
			if(Aplikacija.getTrenutnoUlogovaniKorisnik().equalsIgnoreCase("doktor") &&
					Aplikacija.getDoktor().getClass().isInstance(Doktor.class)) {
				this.setOveren(true);
			}
			else {
				System.out.println("Samo doktor moze da overi izvestaj\n");
			}
		}
	}
	public void stampaj() {
		if(!Aplikacija.getTrenutnoUlogovaniKorisnik().equalsIgnoreCase("")) {
			System.out.println(this.toString());
		}
	}
	
	public static ArrayList<Izvestaj> procitajFajlIzvestaji(){
		ArrayList<Izvestaj> izvestaji=new ArrayList<Izvestaj>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/izvestaji.csv"));
			String row="";
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
			    String[] data = row.split("\\|");
			    if(Aplikacija.getBolnica()!=null) {
				    if(Aplikacija.getBolnica().getNaziv().equalsIgnoreCase(data[2])) {
					    bolnica=Aplikacija.getBolnica();
				    }
				    else {
				    	continue;
				    }
			    }
			    else {
					ArrayList<Bolnica> bolnice=Aplikacija.getBolnice();
					for(int i=0;i<bolnice.size();i++) {
						if(bolnice.get(i).getNaziv().equalsIgnoreCase(data[2])) {
							bolnica=bolnice.get(i);
							break;
						}
					}
			    }
				ArrayList<Pacijent> pacijenti=Pacijent.procitajFajlPacijenti();
				Pacijent pacijent = null;
				for(int i=0;i<pacijenti.size();i++) {
					if(pacijenti.get(i).getKorisnickoIme().equalsIgnoreCase(data[3])) {
						pacijent=pacijenti.get(i);
					}
				}
				
				ArrayList<Doktor> doktori=Doktor.procitajFajlDoktori();
				Doktor doktor = null;
				for(int i=0;i<doktori.size();i++) {
					if(doktori.get(i).getKorisnickoIme().equalsIgnoreCase(data[4])) {
						doktor=doktori.get(i);
					}
				}
				
				ArrayList<Dijagnoza> dijagnoze=new ArrayList<Dijagnoza>(0);
				if(data.length>5) {
					String[] data1=data[5].split("\\*");
					for(int i=0;i<data1.length-2;i+=3) {
						ArrayList<Lek> lekovi=new ArrayList<Lek>(0);
						String[] data2=data1[i+2].split("\\+");
						for(int j=0;j<data2.length-1;j+=2) {
							lekovi.add(new Lek(data2[j],Boolean.valueOf(data2[j+1])));
						}
						dijagnoze.add(new Dijagnoza(data1[i],Long.valueOf(data1[i+1]),lekovi));
					}
				}
				
				ArrayList<Recept> recepti=new ArrayList<Recept>(0);
				if((data.length-1)>5) {
					if(!data[6].equalsIgnoreCase("false")) {
						String[] data2=data[6].split("\\*");
						for(int i=0;i<data2.length-1;i+=2) {
							ArrayList<Lek> lekovi=new ArrayList<Lek>(0);
							String[] data3=data2[i+1].split("\\+");
							for(int j=0;j<data3.length-1;j+=2) {
								lekovi.add(new Lek(data3[j],Boolean.valueOf(data3[j+1])));
							}
							recepti.add(new Recept(Boolean.valueOf(data2[i]),lekovi));
						}
					}
				}
				
				izvestaji.add(new Izvestaj(LocalDateTime.parse(data[0]),Boolean.valueOf(data[1]),bolnica,pacijent,doktor,dijagnoze,recepti));
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl izvestaji nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla izvestaji");
		}
		return izvestaji;
	}
	
	public static void dodajKnjizicu() {
		ArrayList<Pacijent> pacijenti=Aplikacija.getPacijenti();
		ArrayList<Izvestaj> izvestaji=Aplikacija.getIzvestaji();
		//System.out.println(pacijenti.size());
		//System.out.println(pregledi.size());
		for(int i=0;i<izvestaji.size();i++) {
			for(int j=0;j<pacijenti.size();j++) {
				if(izvestaji.get(i).getPacijent()!=null) {
					if(izvestaji.get(i).getPacijent().getKorisnickoIme().equalsIgnoreCase(pacijenti.get(j).getKorisnickoIme())) {
						izvestaji.get(i).getPacijent().setKnjizica(pacijenti.get(j).getKnjizica());
					}
				}
			}
		}
		Aplikacija.setIzvestaji(izvestaji);
	}
}
