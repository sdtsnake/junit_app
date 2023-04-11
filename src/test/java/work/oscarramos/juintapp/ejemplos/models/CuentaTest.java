package work.oscarramos.juintapp.ejemplos.models;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import work.oscarramos.juintapp.ejemplos.exeption.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

/*
    Este nos permite crear una sola instancia de la clase

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
 */
class CuentaTest {
    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest() {
        this.cuenta = new Cuenta("Sebastian Ramos", new BigDecimal("1000.2500"));
        System.out.println("Iniciando el metodo.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando prueba");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando la clase test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando el test");

    }

    @Test
    @DisplayName("probando el nombre la cuenta y los saldos de las cuenta")
    void nombreCuentaTest() {
        Cuenta cuentaSet = new Cuenta();
        cuentaSet.setPersona("Oscar Andres");
        assertEquals("Oscar Andres", cuentaSet.getPersona(), "no es el nombre de la cuenta esperado Oscar Andres");
        assertNotNull(cuentaSet.getPersona(), "la cuenta no puede ser nula");
        Cuenta cuentaConstructor = new Cuenta("Paula Andrea", new BigDecimal("50000.123"));
        assertEquals("Paula Andrea", cuentaConstructor.getPersona(), "nombre esperado debe ser Paula Andrea");
    }

    @Test
    @DisplayName("Prueba de los saldos de la cuentas")
    void saldoCuentaTest() {
        cuenta = new Cuenta("Sebastian Ramos", new BigDecimal("7656234.2500"));
        assertEquals(7656234.2500, cuenta.getSaldo().doubleValue(), "saldo de la cuenta no corresponde al esperador 7656234.2500");
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0, "Saldo no debe ser menor a cero");
    }

    @Test
    @DisplayName("Probando la comparacion de cuentas por referencia")
    void referenciaCuentaTest() {
        cuenta = new Cuenta("Marco Aurelio", new BigDecimal("7656234.2500"));
        Cuenta cuenta2 = new Cuenta("Marco Aurelio", new BigDecimal("7656234.2500"));

        assertEquals(cuenta2, cuenta, "cuentas no son iguales por referencia");

    }

    @Test
    @DisplayName("Validacion de la operacion debito de la cuenta")
    void debitoTest() {
        cuenta = new Cuenta("Bancos", new BigDecimal("1000.12345"));
        cuenta.debito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo(), "saldo no de sebe ser nulo");
        assertEquals(900, cuenta.getSaldo().intValue(), "Saldo de la cuenta no coincide al esperador 900");
        assertEquals("900.12345", cuenta.getSaldo().toPlainString(), "saldo de la cuenta no coincide al espérado 900.12345");
    }

    @Test
    @DisplayName("Validacion de la operacion credito de la cuenta")
    void creditoTest() {
        cuenta = new Cuenta("Bancos", new BigDecimal("1000.12345"));
        cuenta.credito(new BigDecimal(100));
        assertNotNull(cuenta.getSaldo(), "saldo no puede ser nulo");
        assertEquals(1100, cuenta.getSaldo().intValue(), "valor del saldo no corresponde al esperado 1100");
        assertEquals("1100.12345", cuenta.getSaldo().toPlainString(), "valor del saldo no corresponde al esperado 1100.12345");
    }

    @Test
    @DisplayName("Validacion de dinero insuficiente en la cuenta")
    void DineroInsuficienteExceptionTest() {
        cuenta = new Cuenta("Caja", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debito(new BigDecimal(15000));
        });
        assertEquals("Dinero Insuficiente", exception.getMessage(), "El mensaje no es el esperado -> Dinero Insuficiente");
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
        assertEquals("400000", ctaOrigen.getSaldo().toPlainString(), "saldo no corresponde al esperado en la cuenta origen 400000");
        assertEquals("900000", ctaDestino.getSaldo().toPlainString(), "saldo no correspnde en la cuenta destino 900000");
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
                    assertEquals("400000", ctaOrigen.getSaldo().toPlainString(), "saldo de la cuenta origen no corresponde 400000");
                },
                () -> {
                    assertEquals("900000", ctaDestino.getSaldo().toPlainString(), "saldo de la cuenta destino no corresponde 900000");
                },
                () -> {
                    assertEquals(2, banco.getCuentas().size(), "No estan el numero de cuentas esperadas para la prueba -> 2");
                },
                () -> {
                    assertEquals("BBVA", banco.getNombre(), "nombre del banco no corresponde -> BBVA");
                },
                () -> {
                    assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Oscar Ramos")), "El se encuentra en las cuentas del banco el nombre Oscar Ramos");
                },
                () -> {
                    assertEquals("Oscar Ramos", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Oscar Ramos"))
                            .findFirst()
                            .get().getPersona(), "no se encuentra el nombre en la cuentas de banco Oscar Ramos");
                });
    }
    @Test
    @DisplayName("Prueba de los saldos de la cuentas en DEV")
    void saldoCuentaDevTest() {
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        assumeTrue(esDev);
        cuenta = new Cuenta("Sebastian Ramos", new BigDecimal("7656234.2500"));
        assertEquals(7656234.2500, cuenta.getSaldo().doubleValue(), "saldo de la cuenta no corresponde al esperador 7656234.2500");
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0, "Saldo no debe ser menor a cero");
    }

    @Test
    @DisplayName("Prueba de los saldos de la cuentas assumingthat en DEV")
    void saldoCuentaAssumingDevTest() {
        boolean esDev = "dev".equals(System.getProperty("ENV"));
        cuenta = new Cuenta("Sebastian Ramos", new BigDecimal("7656234.2500"));
        assumingThat(esDev,()->{
            assumeTrue(esDev);

            assertEquals(7656234.2500, cuenta.getSaldo().doubleValue(), "saldo de la cuenta no corresponde al esperador 7656234.2500");

        });
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0, "Saldo no debe ser menor a cero");
    }
    @Nested
    @DisplayName("Provando Sistema operativo")
    class SistemaOperativoTest{
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void soloWindowsTest() {

        }
        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void soloLinuxTest() {

        }
        @Test
        @DisabledOnOs(OS.WINDOWS)
        void noWindowsTest() {

        }
    }
    @Nested
    @DisplayName("Provando por JDK")
    class JDKTest{
        @Test
        @EnabledOnJre(JRE.JAVA_8)
        void soloJDK8Test() {

        }
        @Test
        @EnabledOnJre(JRE.JAVA_18)
        void soloJDK15Test() {

        }
    }
    @Nested
    @DisplayName("Provando por variable de entorno")
    class variableEstornoTest{
        @Test
        void imprimirSystemPropertiesTest() {
            Properties properties = System.getProperties();
            properties.forEach((k, v) -> System.out.println(k + ":" + v));
        }

        @Test
        @EnabledIfSystemProperty(named = "java.version", matches = ".*18.*")
        void javaVersionTest() {

        }

        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void solo64Test() {

        }

        @Test
        @EnabledIfSystemProperty(named = "user.name", matches = "snake")
        void userNameTest() {

        }

        @Test
        void imprimirVariableAmbienteTest() {
            Map<String, String> getEnv = System.getenv();
            getEnv.forEach((k, v) -> System.out.println(k + " = " + v));
        }

        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*corretto-11.0.17.*")
        void javaHomeTest() {

        }

        @Test
        @EnabledIfEnvironmentVariable(named = "NUMBER_OF_PROCESSORS", matches = "16")
        void procesadoresTest() {

        }
    }
    @RepeatedTest(value =5, name = "repeticion numero {currentRepetition} de {totalRepetitions}")
    void nombreCuentaRespetidoTest(RepetitionInfo info) {
        if(info.getCurrentRepetition() == 3){
            System.out.println("iniciando la repeticion numero " + info.getCurrentRepetition());
        }
        Cuenta cuentaSet = new Cuenta();
        cuentaSet.setPersona("Oscar Andres");
        assertEquals("Oscar Andres", cuentaSet.getPersona(), "no es el nombre de la cuenta esperado Oscar Andres");
        assertNotNull(cuentaSet.getPersona(), "la cuenta no puede ser nula");
        Cuenta cuentaConstructor = new Cuenta("Paula Andrea", new BigDecimal("50000.123"));
        assertEquals("Paula Andrea", cuentaConstructor.getPersona(), "nombre esperado debe ser Paula Andrea");
    }
    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @ValueSource(strings = {"100","200","300","500","700","1000"})
    void debitoParametrizadoTest(String monto) {
        cuenta.debito(new BigDecimal(monto));
        assertNotNull(cuenta.getSaldo(), "saldo no de sebe ser nulo");
        assumeTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvFileSource(resources = "/data.csv")
    void debitoParametrizadoCsvFileSourceTest(String monto) {
        cuenta.debito(new BigDecimal(monto));
        assertNotNull(cuenta.getSaldo(), "saldo no de sebe ser nulo");
        assumeTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @MethodSource("montoList")
    void debitoParametrizadoMethodSourceTest(String monto) {
        cuenta.debito(new BigDecimal(monto));
        assertNotNull(cuenta.getSaldo(), "saldo no de sebe ser nulo");
        assumeTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }

    static List<String> montoList(){
        return Arrays.asList("100","200","300","500","700","1000");
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvSource({"1,100","2,200","3,300","4,500","5,700","6,1000"})
    void debitoParametrizadoCsvSourceTest(String index, String monto) {
        System.out.println(index + " -> " + monto);
        cuenta.debito(new BigDecimal(monto));
        assertNotNull(cuenta.getSaldo(), "saldo no de sebe ser nulo");
        assumeTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }

    @ParameterizedTest(name = "numero {index} ejecutando con valor {0} - {argumentsWithNames}")
    @CsvSource({"200,100,Andres,Andres","250,200,Pablo,Pablo","350,300,Paola,Paola","560,500,Juan,Juan","790,700,Carlos,Carlos","1200,1000,Mauricio,Mauricio"})
    void debitoParametrizadoCsvSource2Test(String saldo, String monto,String esperado, String actual) {
        System.out.println(saldo + " -> " + monto);
        cuenta.setPersona(actual);

        assertNotNull(cuenta.getPersona());

        assertEquals(esperado,cuenta.getPersona());

        cuenta.setSaldo(new BigDecimal(saldo));
        cuenta.debito(new BigDecimal(monto));
        assertNotNull(cuenta.getSaldo(), "saldo no de sebe ser nulo");
        assumeTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);
    }
}