package es.codeurjc.ais.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameTest {

	private static ChromeDriver driverJugador1; // Nacegador ganador
	private static ChromeDriver driverJugador2; // Navegador perdedor
	private static WebDriverWait driverWaitJugador1;
	private static WebDriverWait driverWaitJugador2;

	@BeforeClass
	public static void setUpClass() {
            ChromeDriverManager.getInstance().setup();

            WebApp.start();
	}
        
        @Before
        public void setUp(){
            driverJugador1 = new ChromeDriver();
            driverJugador2 = new ChromeDriver();
            driverWaitJugador1 = new WebDriverWait(driverJugador1, 30);
            driverWaitJugador2 = new WebDriverWait(driverJugador2, 30);
        }
        
        @Test
        public void testGanadorJugadorPrimerTurno() {
            login(driverJugador1, driverWaitJugador1, "Jugador 1");
            login(driverJugador2, driverWaitJugador2, "Jugador 2");
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-0","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-2","O",true);
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-3","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-5","O",true);
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-6","X",false);
            comprobarAlerta(driverJugador1,driverWaitJugador1,"Jugador 1 wins! Jugador 2 looses.");
            comprobarAlerta(driverJugador2,driverWaitJugador2,"Jugador 1 wins! Jugador 2 looses.");
        }

        @Test
        public void testGanadorJugadorSegundoTurno() {
            login(driverJugador1, driverWaitJugador1, "Jugador 2");
            login(driverJugador2, driverWaitJugador2, "Jugador 1");
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-0","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-2","O",true);
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-3","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-4","O",true);
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-1","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-6","O",false);
            comprobarAlerta(driverJugador1,driverWaitJugador1,"Jugador 1 wins! Jugador 2 looses.");
            comprobarAlerta(driverJugador2,driverWaitJugador2,"Jugador 1 wins! Jugador 2 looses.");
        }

        @Test
        public void testEmpate() {
            login(driverJugador1, driverWaitJugador1, "Jugador 1");
            login(driverJugador2, driverWaitJugador2, "Jugador 2");
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-4","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-1","O",true);
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-3","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-5","O",true);
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-2","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-6","O",true);
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-0","X",true);
            pulsarCelda(driverJugador2,driverWaitJugador2,"cell-8","O",true);
            pulsarCelda(driverJugador1,driverWaitJugador1,"cell-7","X",false);
            comprobarAlerta(driverJugador1,driverWaitJugador1,"Draw!");
            comprobarAlerta(driverJugador2,driverWaitJugador2,"Draw!");
        }
        
	private void login(ChromeDriver driver, WebDriverWait driverWait, String name) {
            driver.get("http://localhost:8082");

            WebElement nombreJugador1Element = driver.findElement(By.id("nickname"));
            nombreJugador1Element.click();
            nombreJugador1Element.sendKeys(name);
            WebElement botonPlay1Element = driver.findElement(By.id("startBtn"));
            botonPlay1Element.click();
            WebElement finalElement = driverWait
                    .until(ExpectedConditions.presenceOfElementLocated(By.id("gameBoard")));

            Assert.assertNotNull(finalElement);
	}
        
        public void comprobarAlerta(ChromeDriver navegador, WebDriverWait espera, String mensaje){
            Alert alerta = espera.until(ExpectedConditions.alertIsPresent());
            assertThat(alerta.getText()).isEqualTo(mensaje);
        }
	
        public boolean pulsarCelda(ChromeDriver navegador, WebDriverWait espera, String celda, String valor, boolean esperarCelda){
            WebElement celdaElement = navegador.findElement(By.id(celda));
            celdaElement.click();
            if (esperarCelda)
                return espera.until(ExpectedConditions.textToBe(By.id(celda), valor));
            else
                return true;
        }
        
        @After
        public void cerrarNavegadores() {
            driverJugador1.close();
            driverJugador2.close();
	}

	@AfterClass
	public static void terminarTest() {
            WebApp.stop();
	}

}
