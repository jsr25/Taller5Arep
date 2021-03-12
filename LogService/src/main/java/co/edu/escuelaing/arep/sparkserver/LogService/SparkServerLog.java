package co.edu.escuelaing.arep.sparkserver.LogService;

import co.edu.escuelaing.arep.sparkserver.LogService.connection.ConnectAtlasMongo;


import static spark.Spark.*;

/**
 * Autor Juan Ramos
 *
 * Controlador de la parte web con la base de datos
 */
public class SparkServerLog {

    public static void main(String[] args) {

        port(getPort());
        get("/envio",(req,res) -> {
            System.out.println("LLego");
            ConnectAtlasMongo connectMongo = new ConnectAtlasMongo();
            String data = req.queryParams("datos");
            System.out.println(data);
            connectMongo.add(data);
            connectMongo.get();
            String json = connectMongo.getCollection();
            return json;
        });

        get("/data",(req,res)->{
            CoonectMongo connectMongo = new CoonectMongo();
            String json = connectMongo.get();
            return json;
        });
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35001;
    }
}
