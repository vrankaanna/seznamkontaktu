package cz.czechitas.webapp;

import java.util.*;
import java.util.concurrent.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
public class HlavniController {

    private Long sekvence = 100L;
    private List<Kontakt> kontakty = new CopyOnWriteArrayList<>(Arrays.asList(
            new Kontakt(sekvence++, "Veve Leto", "+421903672287", "veve@eli.sk"),
            new Kontakt(sekvence++, "Mary Babi", "+421903444555", "mary@babi.sk"),
            new Kontakt(sekvence++, "Katy Bretz", "+421903456456", "katy@bretz.sk"),
            new Kontakt(sekvence++, "Ivi Basch", "+421903555444", "ivi@basch.sk")
    ));

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView zobrazIndex() {
        return new ModelAndView("redirect:/kontakty");
    }
    @RequestMapping(value = "/kontakty", method = RequestMethod.GET)
    public ModelAndView zobrazKontakty() {
        ModelAndView drzakNaDataAJmenoSablony = new ModelAndView("index");
        drzakNaDataAJmenoSablony.addObject("kontakty", kontakty);
        return drzakNaDataAJmenoSablony;
    }
    @RequestMapping(value = "/kontakty/{id}", method = RequestMethod.POST,
            params = "_method=DELETE")
    public ModelAndView smazKontakt(@PathVariable("id") Long id){
        smazKontaktPodleId(id);
        return new ModelAndView("redirect:/kontakty");
    }
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ModelAndView zobrazDetail(@PathVariable("id") Long id) {
        ModelAndView drzakNaDataAJmenoSablony = new ModelAndView("detail");
        drzakNaDataAJmenoSablony.addObject("kontakt", ziskejKontaktPodleId(id));
        return drzakNaDataAJmenoSablony;
    }
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
    public ModelAndView zpracujDetail(@PathVariable("id") Long id, DetailForm detailForm) {
        upravKontakt(id, detailForm);
        return new ModelAndView("redirect:/kontakty");
    }
    @RequestMapping(value = "/novy", method = RequestMethod.GET)
    public ModelAndView zobrazNovy() {
        ModelAndView drzakNaDataAJmenoSablony = new ModelAndView("detail");
        DetailForm detailForm = new DetailForm();
        drzakNaDataAJmenoSablony.addObject("kontakt", detailForm);
        return drzakNaDataAJmenoSablony;
    }
    @RequestMapping(value = "/novy", method = RequestMethod.POST)
    public ModelAndView zpracujNovy(DetailForm detailForm) {
        ulozKontakt(detailForm);
        return new ModelAndView("redirect:/kontakty");
    }
    private Kontakt ziskejKontaktPodleId(Long id) {
        int index = ziskejIndexKontaktuPodleId(id);
        return kontakty.get(index);
    }
    private void upravKontakt(Long id, DetailForm detailForm) {
        int index = ziskejIndexKontaktuPodleId(id);

        Kontakt upravovanyKontakt = kontakty.get(index);
        upravovanyKontakt.setJmeno(detailForm.getJmeno());
        upravovanyKontakt.setTelefonniCislo(detailForm.getTelefonniCislo());
        upravovanyKontakt.setEmail(detailForm.getEmail());
    }

    private void ulozKontakt(DetailForm detailForm) {
        String jmeno = detailForm.getJmeno();
        String telefonniCislo = detailForm.getTelefonniCislo();
        String email = detailForm.getEmail();

        Kontakt novyKontakt = new Kontakt(sekvence++, jmeno, telefonniCislo,email);
        kontakty.add(novyKontakt);
    }
    private void smazKontaktPodleId(Long id) {
        int index = ziskejIndexKontaktuPodleId(id);
        kontakty.remove(index);
    }
    private int ziskejIndexKontaktuPodleId(Long id) {
        for (int i = 0; i < kontakty.size(); i++) {
            if (kontakty.get(i).getId().equals(id)) {
                return i;
            }}
        return -1;
    }
}
