/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Curso;

import java.util.Objects;

/**
 *
 * @author NitroPc
 */
public class Profesorado {
    
    //Atributos de la clase profesorado
    private String fila;
    private String curso;
    private String nombre;
    private String asignatura;
    private String aula;
    private int dia;
    private int hora;

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return  fila + "," + curso + "," + nombre + "," + asignatura + "," + aula + "," + dia + "," + hora ;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.fila);
        hash = 29 * hash + Objects.hashCode(this.curso);
        hash = 29 * hash + Objects.hashCode(this.nombre);
        hash = 29 * hash + Objects.hashCode(this.asignatura);
        hash = 29 * hash + Objects.hashCode(this.aula);
        hash = 29 * hash + this.dia;
        hash = 29 * hash + this.hora;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Profesorado other = (Profesorado) obj;
        if (this.dia != other.dia) {
            return false;
        }
        if (this.hora != other.hora) {
            return false;
        }
        if (!Objects.equals(this.fila, other.fila)) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.asignatura, other.asignatura)) {
            return false;
        }
        if (!Objects.equals(this.aula, other.aula)) {
            return false;
        }
        return true;
    }

    
    
    
}
