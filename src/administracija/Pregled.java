package administracija;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import aplikacija.Aplikacija;
import osoblje.Doktor;
import osoblje.Pacijent;

public class Pregled {
	private final static int TRAJANJE=20;
	private static LocalDateTime kraj=null;
	private final LocalDateTime vremePocetka;
	Doktor doktor=new Doktor(null,null,null,null,null);
	Pacijent pacijent=new Pacijent(null,null,null,null,null);
	Izvestaj izvestaj=new Izvestaj(kraj,false,null,null,null,null,null);
	
	public Pregled(LocalDateTime vremePocetka, Doktor doktor, Pacijent pacijent, Izvestaj izvestaj) {
		this.vremePocetka=vremePocetka;
		this.doktor = doktor;
		this.pacijent = pacijent;
		this.izvestaj = izvestaj;
	}
	
	public static int getTRAJANJE() {
		return TRAJANJE;
	}

	public Doktor getDoktor() {
		return doktor;
	}
	public void setDoktor(Doktor doktor) {
		this.doktor = doktor;
	}
	public Pacijent getPacijent() {
		return pacijent;
	}
	public void setPacijent(Pacijent pacijent) {
		this.pacijent = pacijent;
	}
	public Izvestaj getIzvestaj() {
		return izvestaj;
	}
	public void setIzvestaj(Izvestaj izvestaj) {
		this.izvestaj = izvestaj;
	}
	public LocalDateTime getVremePocetka() {
		return vremePocetka;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vremePocetka == null) ? 0 : vremePocetka.hashCode());
		result = prime * result + ((doktor == null) ? 0 : doktor.hashCode());
		result = prime * result + ((izvestaj == null) ? 0 : izvestaj.hashCode());
		result = prime * result + ((pacijent == null) ? 0 : pacijent.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Pregled))
			return false;
		Pregled other = (Pregled) obj;
		if (vremePocetka == null) {
			if (other.vremePocetka != null)
				return false;
		} else if (!vremePocetka.equals(other.vremePocetka))
			return false;
		if (doktor == null) {
			if (other.doktor != null)
				return false;
		} else if (!doktor.equals(other.doktor))
			return false;
		if (izvestaj == null) {
			if (other.izvestaj != null)
				return false;
		} else if (!izvestaj.equals(other.izvestaj))
			return false;
		if (pacijent == null) {
			if (other.pacijent != null)
				return false;
		} else if (!pacijent.equals(other.pacijent))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Pregled [vremePocetka=" + vremePocetka + ", doktor="
				+ doktor + ", pacijent=" + pacijent + ", izvestaj=" + izvestaj + "]";
	}
	
	public static ArrayList<Pregled> procitajFajlPregledi(){
		ArrayList<Pregled> pregledi=new ArrayList<Pregled>(0);
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader("data/pregledi.csv"));
			String row="";
			while ((row = csvReader.readLine()) != null && !row.isBlank()) {
			    String[] data = row.split("\\|");
			    if(Aplikacija.getBolnica()!=null) {
					boolean isNadjen=false;
					for(int i=0;i<Aplikacija.getPacijentiBolnice().size();i++) {
						if(data[2].equalsIgnoreCase(Aplikacija.getPacijentiBolnice().get(i))) {
							isNadjen=true;
						}
					}
					if(!isNadjen) {
						continue;
					}																												
			    }
				ArrayList<Doktor> doktori=Doktor.procitajFajlDoktori();
				Doktor doktor = null;
				for(int i=0;i<doktori.size();i++) {
					if(doktori.get(i).getKorisnickoIme().equalsIgnoreCase(data[1])) {
						doktor=doktori.get(i);
					}
				}
				
				ArrayList<Pacijent> pacijenti=Pacijent.procitajFajlPacijenti();
				Pacijent pacijent = null;
				for(int i=0;i<pacijenti.size();i++) {
					if(pacijenti.get(i).getKorisnickoIme().equalsIgnoreCase(data[2])) {
						pacijent=pacijenti.get(i);
					}
				}
	
				ArrayList<Izvestaj> izvestaji=Izvestaj.procitajFajlIzvestaji();
				Izvestaj izvestaj =new Izvestaj(kraj,false,null,null,null,null,null);
				String[] data1=data[3].split("\\*");
				//System.out.println(izvestaji.get(i).getBolnica().getNaziv());
				if(data1[0].length()>14) {
					if(Aplikacija.getBolnica()==null) {
						for(int i=0;i<izvestaji.size();i++) {
							if(izvestaji.get(i).getPacijent()!=null && izvestaji.get(i).getDatumIzdavanja().equals(LocalDateTime.parse(data1[0])) &&
									izvestaji.get(i).getPacijent().getKorisnickoIme().equalsIgnoreCase(data1[1])) {
								izvestaj=izvestaji.get(i);
							}
						}
					}
					else {
						for(int i=0;i<izvestaji.size();i++) {
							if(izvestaji.get(i).getDatumIzdavanja().equals(LocalDateTime.parse(data1[0])) &&
									izvestaji.get(i).getPacijent().getKorisnickoIme().equalsIgnoreCase(data1[1])) {
								izvestaj=izvestaji.get(i);
							}
						}
					}
				}
				pregledi.add(new Pregled(LocalDateTime.parse(data[0]),doktor, pacijent, izvestaj));
			}
			csvReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("Zao nam je fajl pregledi nije nadjen");
		}
		catch(IOException e) {
			System.out.println("Zao nam je, imamo gresku u citanju fajla pregledi");
		}
		return pregledi;
	}
	
	public static void dodajKnjizicu() {
		ArrayList<Pacijent> pacijenti=Aplikacija.getPacijenti();
		ArrayList<Pregled> pregledi=Aplikacija.getPregledi();
		//System.out.println(pacijenti.size());
		//System.out.println(pregledi.size());
		for(int i=0;i<pregledi.size();i++) {
			for(int j=0;j<pacijenti.size();j++) {
				if(pregledi.get(i).getPacijent().getKorisnickoIme().equalsIgnoreCase(pacijenti.get(j).getKorisnickoIme())) {
					pregledi.get(i).getPacijent().setKnjizica(pacijenti.get(j).getKnjizica());
				}
			}
		}
		Aplikacija.setPregledi(pregledi);
	}
}
