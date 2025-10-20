package com.phonebook.hackhaton.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AgendaService {
    private static List<Contact> contacts = new ArrayList<>();
    private int contactLimit;

    public AgendaService(int contactLimit) {
        this.contactLimit = contactLimit;
    }

    // añadir contacto
    public void addContact(String name, String lastName, String phone) {
        Contact contact = new Contact(name, lastName, phone);
        contacts.add(contact);
    }

    // existe contacto
    public boolean existsContact(String name, String lastName) {
        return contacts.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name)
                        && c.getLastName().equalsIgnoreCase(lastName));
    }

    // buscar contacto (nombre y apellido)
    public void findContactByNameAndLastName(String name, String lastName) {
        contacts.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(name)
                        && contact.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .ifPresentOrElse(
                        contact -> System.out.println("Teléfono: " + contact.getPhone()),
                        () -> System.out.println("No se encontró el contacto.")
                );
    }

    // eliminar contacto
    public void deleteContactByNameAndId(String name, String lastName) {
        Predicate<Contact> predicate = contact ->
                contact.getName().equalsIgnoreCase(name)
                && contact.getLastName().equalsIgnoreCase(lastName);

        contacts.removeIf(predicate);
    }

    // modificar teléfono

    // listar contactos (falta ordenar)
    public List<Contact> showContacts() {
        return contacts;
    }

    // agenda llena
    public boolean isPhonebookFull() {
        return contacts.size() == contactLimit;
    }

    // espacios libres
    public void checkAvailableSpaces() {
        if (isPhonebookFull()) {
            System.out.println("Ya no hay espacios disponibles en tu agenda.");
        } else {
            System.out.println("Aún puedes almacenar : " + (contactLimit - contacts.size()) + " contactos más.");
        }
    }
}
