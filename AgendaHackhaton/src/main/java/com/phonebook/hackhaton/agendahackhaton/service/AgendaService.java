package com.phonebook.hackhaton.agendahackhaton.service;

import com.phonebook.hackhaton.agendahackhaton.model.Contact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class AgendaService {

    private static final Pattern NAME_VALIDADOR = Pattern.compile("^[a-zA-Z\\s\u00C0-\u017F]+$");
    private static final Pattern PHONE_VALIDADOR = Pattern.compile("^\\d{10}$");

    private final List<Contact> contacts = new ArrayList<>();
    private int contactLimit;

    public AgendaService(int contactLimit) {
        this.contactLimit = contactLimit;
    }

    private boolean isValidNameOrLastName(String field) {
        return field != null && !field.trim().isEmpty() && NAME_VALIDADOR.matcher(field.trim()).matches();
    }

    private boolean isValidPhone(String phone) {
        return phone != null && PHONE_VALIDADOR.matcher(phone.trim()).matches();
    }


    public String anadirContacto(String name, String lastName, String phone) {
        String trimmedName = name.trim();
        String trimmedLastName = lastName.trim();
        String trimmedPhone = phone.trim();

        if (agendaLlena()) {
            return "Error: La agenda está llena. No se puede añadir el contacto.";
        }

        if (!isValidNameOrLastName(trimmedName) || !isValidNameOrLastName(trimmedLastName)) {
            return "Error: El nombre y apellido solo pueden contener letras y acentos.";
        }

        if (!isValidPhone(trimmedPhone)) {
            return "Error: El teléfono debe contener exactamente 10 dígitos numéricos.";
        }

        if (existeContacto(trimmedName, trimmedLastName)) {
            return "Error: El contacto " + trimmedName + " " + trimmedLastName + " ya existe.";
        }

        Contact contact = new Contact(trimmedName, trimmedLastName, trimmedPhone);
        contacts.add(contact);
        return "Contacto " + trimmedName + " " + trimmedLastName + " añadido con éxito.";
    }

    public boolean existeContacto(String name, String lastName) {
        return contacts.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name)
                        && c.getLastName().equalsIgnoreCase(lastName));
    }

    public List<Contact> listarContactos() {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getName)
                        .thenComparing(Contact::getLastName))
                .toList();
    }


    public Optional<Contact> buscarContacto(String name, String lastName) {
        return contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name)
                        && contact.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
    }

    public String eliminarContacto(String name, String lastName) {
        Predicate<Contact> predicate = contact ->
                contact.getName().equalsIgnoreCase(name)
                        && contact.getLastName().equalsIgnoreCase(lastName);

        boolean wasRemoved = contacts.removeIf(predicate);
        if (wasRemoved) {
            return "Contacto " + name + " " + lastName + " eliminado con éxito.";
        } else {
            return "Error: El contacto " + name + " " + lastName + " no existe.";
        }
    }

    public String modificarTelefono(String name, String lastName, String newPhone) {
        if (!isValidPhone(newPhone)) {
            return "Error: El nuevo teléfono debe contener exactamente 10 dígitos numéricos.";
        }

        Optional<Contact> contactOpt = contacts.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name) && c.getLastName().equalsIgnoreCase(lastName))
                .findFirst();

        if (contactOpt.isPresent()) {
            contactOpt.get().setPhone(newPhone);
            return "Teléfono de " + name + " " + lastName + " modificado a " + newPhone + ".";
        } else {
            return "Error: El contacto " + name + " " + lastName + " no existe.";
        }
    }

    public boolean agendaLlena() {
        return contacts.size() >= contactLimit;
    }

    public int espaciosLibres() {
        return contactLimit - contacts.size();
    }

    public String aumentarLimite(int espaciosAdicionales) {
        if (espaciosAdicionales <= 0) {
            return "Error: El número de espacios a añadir debe ser positivo.";
        }
        this.contactLimit += espaciosAdicionales;
        return "Éxito: Límite de la agenda aumentado a " + this.contactLimit + " contactos.";
    }
}