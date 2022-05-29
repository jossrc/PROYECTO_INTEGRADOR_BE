package com.postales.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseApi<T> {

    private boolean ok;
    private String mensaje;
    private List<T> datos;
    private T objeto;
    private String error;

    public ResponseApi() {
        this.datos = new ArrayList<T>();
        this.ok = true;
    }

    public ResponseApi(boolean ok, String mensaje, List<T> datos, T objeto) {
        this.ok = ok;
        this.mensaje = mensaje;
        this.datos = datos;
        this.objeto = objeto;
    }

    public ResponseApi(boolean ok, String mensaje, List<T> datos, T objeto, String error) {
        this.ok = ok;
        this.mensaje = mensaje;
        this.datos = datos;
        this.objeto = objeto;
        this.error = error;
    }

    public ResponseApi(boolean ok, String mensaje, T objeto) {
        this.ok = ok;
        this.mensaje = mensaje;
        this.objeto = objeto;
        this.datos = new ArrayList<T>();
    }

    public ResponseApi(boolean ok, String mensaje, List<T> datos) {
        this.ok = ok;
        this.mensaje = mensaje;
        this.objeto = null;
        this.datos = datos;
    }


}
