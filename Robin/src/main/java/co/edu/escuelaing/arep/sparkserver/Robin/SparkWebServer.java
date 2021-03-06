package co.edu.escuelaing.arep.sparkserver.Robin;
import co.edu.escuelaing.arep.sparkserver.Robin.entity.Dato;
import co.edu.escuelaing.arep.sparkserver.Robin.util.GeneradorFecha;
import co.edu.escuelaing.arep.sparkserver.Robin.connection.ConnectoDB;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

import static spark.Spark.*;

/**
 * Autor Juan Ramos
 *
 * Clase que controla la aplicacion
 */
public class SparkWebServer {

    private static String[] puertos={"35001","35002","35003"};
    private static AtomicInteger intento;
    public static void main(String... args){
        intento=new AtomicInteger(0);
        port(getPort());
        get("/", (req,res) ->{
            return "<head>" +
                    "<title> Mensajes</title>" +
                    "</head>" +
                    "<body>" +
                    "<div>" +
                    "<form action=\"/envio\" method=\"get\">" +
                    "<h4>Ingrese el mensaje separada por - <h4>" +
                    "<input required name=\"datos\" id=\"datos\" value=\"\">" +
                    "<div>" +
                    "<button> Enviar </button>" +
                    "</div>" +
                    "<form>" +
                    "</div>";
        });
        get("/envio",(req,res)->{
            String puerto = log();
            String path ="172.17.0.1:"+puerto+"/envio";
            Dato dato=new Dato(req.queryParams("datos"),GeneradorFecha.fecha());
            ConnectoDB conn = new ConnectoDB();
            String data =conn.getResult(req.queryParams("datos"),path);


            String[] datoss=data.split(",");

            String tb="<tr>" +
                    " <th>Mensaje</td>" +
                    " <th>Fecha</td>" +
                    "  </tr>";
            for(String c:datoss){
                String[] fel = c.split("/");
                tb=tb+"<tr>" +
                        " <td>"+fel[0].replace("-"," ")+"</td>" +
                        " <td>"+fel[1]+"</td>" +
                        "  </tr>";


            }
            return "<div>" +
                    "" +
                    "<p>los datos resgitrados en la base de datos son</p>" +
                    "" +
                    "</div>" +
                    "" +
                    "<div>" +
                    "<table class=\"default\">" +
                    "" +
                    tb +
                    "" +
                    "</table>" +

                    "</div>" +
                    "<div>" +
                    "<form action=\"/\" method=\"get\">" +
                    "<button> Volver </button>" +
                    "</form>" +
                    "</div>";
        });

    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

    private static String log(){
        String port=puertos[intento.get()];;
        if(intento.get()==0){
            intento=new AtomicInteger(1);
        }
        else if(intento.get()==1){
            intento=new AtomicInteger(2);
        }
        else{
            intento=new AtomicInteger(0);
        }
        return port;
    }


}
