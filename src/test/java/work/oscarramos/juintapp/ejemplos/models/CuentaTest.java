package work.oscarramos.juintapp.ejemplos.models;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import work.oscarramos.juintapp.ejemplos.exeption.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    @DisplayName("probando el nombre la cuenta y los saldos de las cuenta")
    void nombreCuentaTest() {
        Cuenta cuentaSet = new Cuenta();
        cuentaSet.setPersona("Oscar Amdres");
        assertEquals("Oscar Amdres", cuentaSet.getPersona(),"no es el nombre de la cuenta esperado Oscar Amdres");
        assertNotNull(cuentaSet.getPersona(),"la cuenta no puede ser nula");
        Cuenta cuentaConstructor = new Cuenta("Paula Andrea", new BigDecimal("50000.123"));
        assertEquals("Paula Andrea", cuentaConstructor.getPersona(),"nombre esperado debe ser Paula Andrea");
    }

    @Test
    @DisplayName("Prueba de los saldos de la cuentas")
    void saldoCuentaTest() {
        Cuenta cuenta = new Cuenta("Sebastian Ramos", new BigDecimal("7656234.2500"));
        assertEquals(7656234.2500, cuenta.getSaldo().doubleValue(),"saldo de la cuenta no corresponde al esperador 7656234.2500");
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0,"Saldo no debe ser menor a cero");
    }

    @Test
    @DisplayName("Probando la comparacion de cuentas por referencia")
    void referenciaCuentaTest() {
        Cuenta cuenta = new Cuenta("Marco Aurelio", new BigDecimal("7656234.2500"));
        Cuenta cuenta2 = new Cuenta("Marco Aurelio", new BigDecimal("7656234.2500"));

        assertEquals(cuenta2, cuenta,"cuentas no son iguales por referencia");

    }

    @Test
    @DisplayName("Validacion de la operacion debito de la cuenta")
    void debitoTest() {
        Cuenta cuenta = new Cuenta("Bancos", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo(),"saldo no de sebe ser nulo");
        assertEquals(900, cuenta.getSaldo().intValue(),"Saldo de la cuenta no coincide al esperador 900");
        assertEquals("900.12345", cuenta.getSaldo().toPlainString(),"saldo de la cuenta no coincide al espérado 900.12345");
    }

    @Test
    @DisplayName("Validacion de la operacion credito de la cuenta")
    void creditoTest() {
        Cuenta cuenta = new Cuenta("Bancos", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo(), "saldo no puede ser nulo");
        assertEquals(1100, cuenta.getSaldo().intValue(),"valor del saldo no corresponde al esperado 1100");
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString(),"valor del saldo no corresponde al esperado 1100.12345");
    }

    @Test
    @DisplayName("Validacion de dinero insuficiente en la cuenta")
    void DineroInsuficienteExceptionTest() {
        Cuenta cuenta = new Cuenta("Caja", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(15000));
        });
        assertEquals("Dinero Insuficiente", exception.getMessage(),"El mensaje no es el esperado -> Dinero Insuficiente");
    }

    @Test
    @Disabled
    @DisplayName("Validacion de la transferencia de cuentas")
    void tranferirDineroCuentasTest() {
        fail();
        Cuenta ctaOrigen = new Cuenta("Oscar Ramos", new BigDecimal("600000"));
        Cuenta ctaDestino = new Cuenta("Paula Nuñez", new BigDecimal("700000"));
        Banco banco = new Banco();
        banco.setNombre("BBVA");
        banco.transferir(ctaOrigen, ctaDestino, new BigDecimal(200000));
        assertEquals("400000", ctaOrigen.getSaldo().toPlainString(),"saldo no corresponde al esperado en la cuenta origen 400000");
        assertEquals("900000", ctaDestino.getSaldo().toPlainString(),"saldo no correspnde en la cuenta destino 900000");
    }

    @Test
    @DisplayName("Validacion de realcion de cuentas/bancos")
    void relacionBancoCuentasTest() {
        Cuenta ctaOrigen = new Cuenta("Oscar Ramos", new BigDecimal("600000"));
        Cuenta ctaDestino = new Cuenta("Paula Nuñez", new BigDecimal("700000"));
        Banco banco = new Banco();
        banco.addCuenta(ctaOrigen);
        banco.addCuenta(ctaDestino);

        banco.setNombre("BBVA");
        banco.transferir(ctaOrigen, ctaDestino, new BigDecimal(200000));

        assertAll(() -> {
                    assertEquals("400000", ctaOrigen.getSaldo().toPlainString(),"saldo de la cuenta origen no corresponde 400000");
                },
                () -> {
                    assertEquals("900000", ctaDestino.getSaldo().toPlainString(),"saldo de la cuenta destino no corresponde 900000");
                },
                () -> {
                    assertEquals(2, banco.getCuentas().size(),"No estan el numero de cuentas esperadas para la prueba -> 2");
                },
                () -> {
                    assertEquals("BBVA", banco.getNombre(),"nombre del banco no corresponde -> BBVA");
                },
                () -> {
                    assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Oscar Ramos")),"El se encuentra en las cuentas del banco el nombre Oscar Ramos");
                },
                () -> {
                    assertEquals("Oscar Ramos", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Oscar Ramos"))
                            .findFirst()
                            .get().getPersona(),"no se encuentra el nombre en la cuentas de banco Oscar Ramos");
                });
    }
}