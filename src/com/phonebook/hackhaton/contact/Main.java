package com.phonebook.hackhaton.contact;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static AgendaService agenda;

    public static void main(String[] args) {

        System.out.println("   SISTEMA DE GESTIÓN DE AGENDA TELEFÓNICA");

        // Configurar tamaño de la agenda
        configurarAgenda();

        // Menú principal
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> anadirContacto();
                case 2 -> existeContacto();
                case 3 -> listarContactos();
                case 9 -> {
                    System.out.println("\n╔════════════════════════════════════════╗");
                    System.out.println("  ¡Gracias por usar la Agenda!         ");
                    System.out.println("  ¡Hasta pronto!                        ");
                    System.out.println("╚════════════════════════════════════════╝\n");
                    salir = true;
                }
                default -> System.out.println("\n Opción inválida. Elige una opción del 1 al 9.\n");
            }
        }
        scanner.close();
    }

    /**
     * Permite al usuario configurar el tamaño de la agenda
     */
    private static void configurarAgenda() {
        System.out.println("¿Cómo deseas configurar tu agenda?");
        System.out.println("1. Usar tamaño por defecto (10 contactos)");
        System.out.println("2. Definir tamaño personalizado");
        System.out.print("Opción: ");

        int opcion = leerOpcion();

        if (opcion == 2) {
            System.out.print("Ingresa el tamaño máximo de la agenda: ");
            int tamano = leerOpcion();
            while (tamano <= 0) {
                System.out.print("El tamaño debe ser mayor a 0. Intenta nuevamente: ");
                tamano = leerOpcion();
            }
            agenda = new AgendaService(tamano);
            System.out.println("\n Agenda creada con capacidad de " + tamano + " contactos.\n");
        } else {
            agenda = new AgendaService(10);
            System.out.println("\n Agenda creada con capacidad por defecto de 10 contactos.\n");
        }

        presionaEnterParaContinuar();
    }

    /**
     * Muestra el menú principal
     */
    private static void mostrarMenu() {
        System.out.println("╔═════════════════════════════════════════════╗");
        System.out.println("                          MENÚ PRINCIPAL - AGENDA             ");
        System.out.println("╠═════════════════════════════════════════════╣");
        System.out.println("1. Añadir contacto                          ");
        System.out.println("2. Verificar si existe contacto             ");
        System.out.println("3. Listar todos los contactos               ");
        System.out.println("-------------------------------FUNCIONES FALTANTES-----------------------------");
        System.out.println("4. Buscar contacto                          ");
        System.out.println("5. Eliminar contacto                        ");
        System.out.println("6. Modificar teléfono                       ");
        System.out.println("7. Verificar si agenda está llena           ");
        System.out.println("8. Ver espacios libres                      ");
        System.out.println("9. Salir                                    ");
        System.out.println("╚═════════════════════════════════════════════╝");
        System.out.print("Selecciona una opción: ");
    }


    private static int leerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            return opcion;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Limpiar buffer
            return -1;
        }
    }

    /**
     * OPCIÓN 1: Añadir contacto
     */
    private static void anadirContacto() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("      AÑADIR NUEVO CONTACTO            ");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Teléfono (10 dígitos): ");
        String telefono = scanner.nextLine();

        System.out.println("\n" + "─".repeat(45));
        boolean resultado = agenda.anadirContacto(nombre, apellido, telefono);
        System.out.println("─".repeat(45) + "\n");

        presionaEnterParaContinuar();
    }

    /**
     * OPCIÓN 2: Verificar si existe contacto
     */
    private static void existeContacto() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("   VERIFICAR EXISTENCIA DE CONTACTO    ");
        System.out.println("╚═══════════════════════════════════════╝");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.println("\n" + "─".repeat(45));
        if (agenda.existeContacto(nombre, apellido)) {
            System.out.println("✅ El contacto " + nombre + " " + apellido + " SÍ existe.");
        } else {
            System.out.println("❌ El contacto " + nombre + " " + apellido + " NO existe.");
        }
        System.out.println("─".repeat(45) + "\n");

        presionaEnterParaContinuar();
    }

    /**
     * OPCIÓN 3: Listar contactos
     */
    private static void listarContactos() {
        System.out.println("║        LISTA DE CONTACTOS             ║");

        var contactos = agenda.listarContactos();

        if (contactos.isEmpty()) {
            System.out.println("\n⚠️  La agenda está vacía.\n");
        } else {
            System.out.println("\nTotal de contactos: " + contactos.size());
            System.out.println("─".repeat(45));
            int contador = 1;
            for (Contact contacto : contactos) {
                System.out.printf("%2d. %s\n", contador++, contacto.toString());
            }
            System.out.println("─".repeat(45) + "\n");
        }

        presionaEnterParaContinuar();
    }

    /*
    Aqui faltan añadir las otras funciones restantes del menú:
        buscaContacto(String nombre):
        Permite buscar un contacto por nombre y apellido.
        Si el contacto existe, muestra el teléfono. Si no existe, muestra un mensaje indicando que no se ha encontrado.

        eliminarContacto(Contacto c):
        Elimina un contacto de la agenda. Muestra un mensaje indicando si la eliminación ha sido exitosa o no.
        Si se intenta eliminar un contacto que no existe, debe indicarse al usuario.

         modificarTelefono(String nombre, String apellido, String nuevoTelefono):
         Permite modificar el teléfono de un contacto existente.
         Si el contacto no existe, debe mostrar un mensaje.

        agendaLlena():
        Indica si la agenda está llena.
        Muestra un mensaje indicando que no hay espacio disponible para nuevos contactos.

        espacioLibres():
        Muestra cuántos contactos más se pueden agregar a la agenda.
        Esto debe basarse en el tamaño máximo definido al crear la agenda.
     */

    //Función para continuar
    private static void presionaEnterParaContinuar() {
        System.out.print("Presiona ENTER para continuar...");
        scanner.nextLine();
        System.out.println("\n");
    }
}