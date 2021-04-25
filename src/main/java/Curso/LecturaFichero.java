/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Curso;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 *
 * @author NitroPc
 */
public class LecturaFichero {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        // Fichero a leer
        String ficheroLeer = "hor_curso_1920_final.csv";

        // Variables para guardar los datos que se van leyendo
        String[] tokens;
        String linea;

        //Array para almacenar las posicione donde se encuentra la secuencia de caracteres
        ArrayList<Profesorado> listaProfesores = new ArrayList<>();

        try ( Scanner datosFichero = new Scanner(new File(ficheroLeer))) {

            while (datosFichero.hasNextLine()) {

                linea = datosFichero.nextLine();

                tokens = linea.split(";");

                Profesorado profesor = new Profesorado();

                //Comenzamos a introducir los datos en los atributos del objeto
                profesor.setFila(tokens[0]);
                profesor.setCurso(quitarComillasEspacios(tokens[1]));
                profesor.setNombre(quitarComillasEspacios(tokens[2]));
                profesor.setAsignatura(quitarComillasEspacios(tokens[3]));
                profesor.setAula(quitarComillasEspacios(tokens[4]));
                profesor.setDia(Integer.parseInt(tokens[5]));

                int hora = comprobarHoras(tokens[6]);

                if ((hora >= 1 && hora <= 6)) {
                    profesor.setHora(hora);
                    //Añadimos el objeto profesor a la lista
                    listaProfesores.add(profesor);
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //Ordenamos pro dia y despues por hora
        listaProfesores.stream()
                .sorted((d1, d2) -> (int) d1.getDia() - d2.getDia())
                .sorted((h1, h2) -> (int) h1.getHora() - h2.getHora());
                //Si se habilita esto podremos comprobar que la lista esta creada correctamente
//                .forEach(System.out::println);

        Set<String> profesor = new TreeSet<>(almacenarProfesores(listaProfesores));
        Set<String> grupo = new TreeSet<>(almacenarGrupo(listaProfesores));

        int eleccion = 0;

        do {
            System.out.println("Elije una de las dos opciones (1 0 2): "
                    + "\n1) Consultar horarios por profesor/a"
                    + "\n2) Consultar horarios por grupo.");
            eleccion = teclado.nextInt();

            switch (eleccion) {
                case 1:
                    boolean existeP = false;
                    String eleccionPro;
                    do {

                        for (String pro : profesor) {
                            System.out.println(pro);
                        }
                        System.out.println("\nElije el profesor escribiendolo tal como lo muestra la lista:");
                        eleccionPro = teclado.next();

                        existeP = existe(eleccionPro, profesor);
                    } while (!existeP);

                    String nombreAr = crearFichero(eleccionPro);
                    List<Integer> profe = horario(eleccionPro, listaProfesores);

                    escribir(profe, nombreAr);

                    break;
                case 2:
                    boolean existeG = false;
                    String eleccionGru;
                    do {
                        for (String gru : grupo) {
                            System.out.println(gru);
                        }
                        System.out.println("\nElije el grupo escribiendolo tal como lo muestra la lista:");
                        eleccionGru = teclado.next();

                        existeG = existe(eleccionGru, grupo);
                    } while (!existeG);

                    String nombre = crearFichero(eleccionGru);
                    List<Integer> grup = horario(eleccionGru, listaProfesores);

                    grup.forEach(System.out::println);
                    escribir(grup, nombre);

                    break;
            }
        } while (eleccion != 1 && eleccion != 2);

    }

    //Metodo para quitar las comillas del principio y del final a las palabras
    private static String quitarComillasEspacios(String palabra) {

        String sinComillas = palabra.substring(1, palabra.length() - 1);

        return sinComillas.trim();
    }

    //Metodo para comprobar las horas
    private static int comprobarHoras(String hora) {
        int h = Integer.parseInt(hora);

        if (h >= 1 && h <= 7) {
            switch (h) {
                case 1:
                    h = 1;
                    break;
                case 2:
                    h = 2;
                    break;
                case 3:
                    h = 3;
                    break;
                case 5:
                    h = 4;
                    break;
                case 6:
                    h = 5;
                    break;
                case 7:
                    h = 6;
                    break;
            }
        } else {
            h = 0;
        }

        return h;
    }

    //Metodo para almacenar en una estructura set todos los profesores
    private static Set almacenarProfesores(ArrayList<Profesorado> lista) {

        Set<String> profesor = new TreeSet<>();

        for (int i = 0; i < lista.size(); i++) {
            profesor.add(lista.get(i).getNombre());
        }

        return profesor;
    }

    //Metodo para almacenar en una estructura set todos los grupo
    private static Set almacenarGrupo(ArrayList<Profesorado> lista) {

        Set<String> grupo = new TreeSet<>();

        for (int i = 0; i < lista.size(); i++) {
            grupo.add(lista.get(i).getCurso());
        }

        return grupo;
    }

    //Metodo que comprueba si existe el profesor o grupo escrito por el usuario
    private static boolean existe(String parametro, Set<String> lista) {

        boolean existe = false;

        if (lista.contains(parametro)) {
            existe = true;
        }

        return existe;
    }

    //Metodo para crear el fichero
    private static String crearFichero(String titulo) {

        String fichero = "./" + titulo + ".csv";

        Path file = Paths.get(fichero);
        try {
            System.out.println("Su fichero a sido creado");
            // Este método no crea el archivo si ya existe
            Files.createFile(file);
        } catch (IOException e) {
            System.out.println("Problema creando el archivo.");
            System.out.println(e.toString());
        }

        return fichero;
    }

    //Metodo para devolver una lista
    private static List horario(String eleccion, ArrayList<Profesorado> lista) {

        List<Integer> list = lista.stream()
                .filter(s -> s.getNombre().equalsIgnoreCase(eleccion))
                .map(p -> p.getHora())//Nos hemos quedado con un stream de string con los apellidos
                .collect(Collectors.toList());
        return list;
    }

    //Metodo que escribe en el archivo los horarios
    private static void escribir(List<Integer> lista, String archivo) {

        String escribir = archivo;

        try ( BufferedWriter flujo1 = new BufferedWriter(new FileWriter(escribir))) {

            for (int i = 0; i < lista.size(); i++) {
                flujo1.write(lista.get(i) + "ºhora");
                flujo1.newLine();
            }

            System.out.println("Los horarios han sido escrito correctamente");
            // Metodo fluh() guarda cambios en disco 
            flujo1.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
