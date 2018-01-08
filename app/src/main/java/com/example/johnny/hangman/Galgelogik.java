package com.example.johnny.hangman;

/**
 * Created by Johnny on 22/10/17.
 */
        import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Galgelogik {
    private ArrayList<String> muligeOrd = new ArrayList<>();
    private String ordet;
    private ArrayList<String> brugteBogstaver = new ArrayList<>();
    private String synligtOrd;
    private int antalLiv, nrWrong, score = 0, streak = 0;
    private boolean spilletErVundet;
    private boolean spilletErTabt;

    public ArrayList<String> getBrugteBogstaver() {
        return brugteBogstaver;
    }

    public String getSynligtOrd() {
        return synligtOrd;
    }

    public String getOrdet() {
        return ordet;
    }

    public int getAntalLiv() {
        return antalLiv;
    }

    public int getNrWrong() { return nrWrong; }

    public boolean erSpilletVundet() {
        return spilletErVundet;
    }

    public boolean erSpilletTabt() {
        return spilletErTabt;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public Galgelogik() {

        // Her indsættes ord hvis man vil have sin egne
        //muligeOrd.add("Ord");
   }

    public void nulstil() {
        brugteBogstaver.clear();
        antalLiv = 6;
        nrWrong = 0;
        spilletErVundet = false;
        spilletErTabt = false;
        ordet = muligeOrd.get(new Random().nextInt(muligeOrd.size()));
        opdaterSynligtOrd();
    }

    private void opdaterSynligtOrd() {
        synligtOrd = "";
        spilletErVundet = true;
        for (int n = 0; n < ordet.length(); n++) {
            String bogstav = ordet.substring(n, n + 1);
            if (brugteBogstaver.contains(bogstav)) {
                synligtOrd = synligtOrd + bogstav;
            } else {
                synligtOrd = synligtOrd + " _ ";
                spilletErVundet = false;
            }
        }
    }

    public void gætBogstav(String bogstav) {
        if (bogstav.length() != 1) return;

        System.out.println("Der gættes på bogstavet: " + bogstav);

        if (brugteBogstaver.contains(bogstav))return;

        if (spilletErVundet || spilletErTabt) return;

        if (ordet.contains(bogstav)) {
            System.out.println("Bogstavet var korrekt: " + bogstav);
            streak++;

            if (streak == 2) {
                score = score + 100;
            }
            else if (streak == 3) {
                score = score + 150;
            }
            else {
                score = score + 50;
            }
        }
        else {
            // Vi gættede på et bogstav der ikke var i ordet.
            System.out.println("Bogstavet var IKKE korrekt: " + bogstav);
            streak = 0;
            score = score - 50;

            antalLiv = antalLiv - 1;
            nrWrong = nrWrong + 1;

            if (antalLiv == 0) {
                spilletErTabt = true;
            }
        }
        brugteBogstaver.add(bogstav);

        opdaterSynligtOrd();
    }

    public static String hentUrl(String url) throws IOException {
        System.out.println("Henter data fra " + url);
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String linje = br.readLine();
        while (linje != null) {
            sb.append(linje + "\n");
            linje = br.readLine();
        }
        return sb.toString();
    }

    public void hentOrdFraURL() throws Exception {
        String data = hentUrl("https://na.leagueoflegends.com/en/");
        //System.out.println("data = " + data);

        data = data.substring(data.indexOf("<body")). // fjern headere
                replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
                replaceAll("&#198;", "æ"). // erstat HTML-tegn
                replaceAll("&#230;", "æ"). // erstat HTML-tegn
                replaceAll("&#216;", "ø"). // erstat HTML-tegn
                replaceAll("&#248;", "ø"). // erstat HTML-tegn
                replaceAll("&oslash;", "ø"). // erstat HTML-tegn
                replaceAll("&#229;", "å"). // erstat HTML-tegn
                replaceAll("[^a-zæøå]", " "). // fjern tegn der ikke er bogstaver
                replaceAll(" [a-zæøå] "," "). // fjern 1-bogstavsord
                replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-bogstavsord

        System.out.println("data = " + data);
        System.out.println("data = " + Arrays.asList(data.split("\\s+")));
        muligeOrd.clear();
        muligeOrd.addAll(new HashSet<>(Arrays.asList(data.split(" "))));
        System.out.println("muligeOrd = " + muligeOrd);
        nulstil();
    }
}