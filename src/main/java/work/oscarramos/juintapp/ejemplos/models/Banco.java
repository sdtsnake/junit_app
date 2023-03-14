package work.oscarramos.juintapp.ejemplos.models;

import javax.crypto.Cipher;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {

    private String nombre;
    private List<Cuenta> cuentas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas){
        this.cuentas =  cuentas;
    }

    public void addCuenta(Cuenta cuenta){
        this.cuentas.add(cuenta);
        cuenta.setBanco(this);
    }

    public Banco() {
        this.cuentas = new ArrayList<>();
    }

    public void transferir(Cuenta ctaOrigen, Cuenta ctaDestino, BigDecimal monto){
        ctaOrigen.debito(monto);
        ctaDestino.credito(monto);
    }
}
