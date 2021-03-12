package co.edu.escuelaing.arep.sparkserver.Robin;
import co.edu.escuelaing.arep.sparkserver.Robin.entity.Dato;
import co.edu.escuelaing.arep.sparkserver.Robin.util.GeneradorFecha;
import co.edu.escuelaing.arep.sparkserver.Robin.connection.ConnectoDB;

import static spark.Spark.*;


public class SparkWebServer {
    private static int intento;
    public static void main(String... args){
        intento =1;
        port(getPort());

        get("/", (req,res) ->{
            return "<head>" +
                    "<title> Mensajes</title>" +
                    "</head>" +
                    "<body>" +
                    "<div>" +
                    "<form action=\"/envio\" method=\"get\">" +
                    "<h4>Ingrese la palabra de desea ingresar<h4>" +
                    "<input required name=\"datos\" id=\"datos\" value=\"\">" +
                    "<div>" +
                    "<button> Enviar </button>" +
                    "</div>" +
                    "<form>" +
                    "</div>";
        });
        get("/envio",(req,res)->{
            String puerto = log(intento);
            String path ="172.17.0.1:"+puerto+"/envio";
            Dato dato=new Dato(req.queryParams("datos"),GeneradorFecha.fecha());
            ConnectoDB conn = new ConnectoDB();
            String data =conn.getResult(req.queryParams("datos"),path);

            return "<div>" +
                    "" +
                    "<p>los datos resgitrados en la base de datos son</p>" +
                    "" +
                    "</div>" +
                    "" +
                    "<div>" +
                    data +
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

    private static String log(int val){
        return "35001";
    }

    private static String formatData(String data){
        return "";
    }
}
