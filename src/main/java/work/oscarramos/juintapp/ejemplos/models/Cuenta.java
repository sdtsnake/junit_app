package work.oscarramos.juintapp.ejemplos.models;

import work.oscarramos.juintapp.ejemplos.exeption.DineroInsuficienteException;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;

    private Banco banco;
    public Cuenta(String persona, BigDecimal saldo) {
        this.persona = persona;
        this.saldo = saldo;
    }

    public Cuenta() {
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public void debito(BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO)<0){
            throw new DineroInsuficienteException("Dinero Insuficiente");
        }
        this.saldo = nuevoSaldo;
    }

    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cuenta)) return false;
        Cuenta c = (Cuenta) obj;
        if (this.persona == null || this.saldo == null) return false;

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }


}
