package com.phonebook.hackhaton.contact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class AgendaService {
    //REGEX de validación:
    private static final Pattern NAME_VALIDADOR=Pattern.compile("^[a-zA-Z\\s\u00C0-\u017F]+$");
    private static final Pattern PHONE_VALIDADOR=Pattern.compile("^\\d{10}$");


    private final List<Contact> contacts = new ArrayList<>();
    private final int contactLimit;

    public AgendaService(int contactLimit) {
        this.contactLimit = contactLimit;
    }
    //Validaciones de entradas de nombre y teléfono:
    private boolean isValidNameOrLastName(String field){
        return field != null && !field.trim().isEmpty() && NAME_VALIDADOR.matcher(field.trim()).matches();
    }
    private boolean isValidPhone(String phone){
        return phone != null && PHONE_VALIDADOR.matcher(phone.trim()).matches();
    }

    // Metodo para añadir contacto con todas las validaciones
    public boolean anadirContacto(String name, String lastName, String phone) {
        String trimmedName = name.trim();
        String trimmedLastName = lastName.trim();
        String trimmedPhone = phone.trim();

        if (agendaLlena()) {
            System.out.println("Error: La agenda está llena. No se puede añadir el contacto.");
            return false;
        }

        if (!isValidNameOrLastName(trimmedName) || !isValidNameOrLastName(trimmedLastName)) {
            System.out.println("Error: El nombre y apellido solo pueden contener letras y acentos, sin números ni símbolos especiales.");
            return false;
        }

        // Validación de formato de Teléfono
        if (!isValidPhone(trimmedPhone)) {
            System.out.println("Error: El teléfono debe contener exactamente 10 dígitos numéricos.");
            return false;
        }

        // Validación de duplicidad
        if (existeContacto(trimmedName, trimmedLastName)) {
            System.out.println("Error: El contacto " + trimmedName + " " + trimmedLastName + " ya existe en la agenda.");
            return false;
        }

        Contact contact = new Contact(trimmedName, trimmedLastName, trimmedPhone);
        contacts.add(contact);
        System.out.println("Contacto " + trimmedName + " " + trimmedLastName + " añadido con éxito.");
        return true;
    }

    // Verifica si un contacto existe
    public boolean existeContacto(String name, String lastName) {
        return contacts.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name)
                        && c.getLastName().equalsIgnoreCase(lastName));
    }

    // Lista de contactos ordenada
    public List<Contact> listarContactos() {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getName)
                        .thenComparing(Contact::getLastName))
                .toList();
    }

    // Buscar contacto
    public void buscarContacto(String name, String lastName) {
        Optional<Contact> found = contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name)
                        && contact.getLastName().equalsIgnoreCase(lastName))
                .findFirst();

        found.ifPresentOrElse(
                contact -> System.out.println("Teléfono de " + name + " " + lastName + ": " + contact.getPhone()),
                () -> System.out.println("No se encontró el contacto " + name + " " + lastName + ".")
        );
    }

    // Elimina contacto
    public boolean eliminarContacto(String name, String lastName) {
        Predicate<Contact> predicate = contact ->
                contact.getName().equalsIgnoreCase(name)
                        && contact.getLastName().equalsIgnoreCase(lastName);

        boolean wasRemoved = contacts.removeIf(predicate);
        if (wasRemoved) {
            System.out.println("Contacto " + name + " " + lastName + " eliminado con éxito.");
        } else {
            System.out.println("Error: El contacto " + name + " " + lastName + " no existe.");
        }
        return wasRemoved;
    }

    // Modifica el teléfono de un contacto existente (con nueva validación de teléfono)
    public boolean modificarTelefono(String name, String lastName, String newPhone) {
        if (!isValidPhone(newPhone)) {
            System.out.println("Error: El nuevo teléfono debe contener exactamente 10 dígitos numéricos.");
            return false;
        }

        Optional<Contact> contactOpt = contacts.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name) && c.getLastName().equalsIgnoreCase(lastName))
                .findFirst();

        if (contactOpt.isPresent()) {
            contactOpt.get().setPhone(newPhone);
            System.out.println("Teléfono de " + name + " " + lastName + " modificado a " + newPhone + ".");
            return true;
        } else {
            System.out.println("Error: El contacto " + name + " " + lastName + " no existe.");
            return false;
        }
    }

    // Indica si la agenda está llena
    public boolean agendaLlena() {
        boolean full = contacts.size() >= contactLimit;
        if (full) {
            System.out.println("Agenda llena: No hay espacio disponible para nuevos contactos.");
        }
        return full;
    }

    // Muestra cuántos espacios libres quedan
    public int espaciosLibres() {
        int available = contactLimit - contacts.size();
        if (available > 0) {
            System.out.println("Espacios libres: " + available + " contactos más.");
        } else {
            System.out.println("No hay espacios disponibles.");
        }
        return available;
    }
}