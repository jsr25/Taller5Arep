package co.edu.escuelaing.arep.sparkserver.LogService.connection;


import co.edu.escuelaing.arep.sparkserver.LogService.util.GeneradorFecha;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

;

public class ConnectAtlasMongo {

    private MongoCollection<Document> collection;
    public ConnectAtlasMongo(){
        try{
            MongoClientURI uri = new MongoClientURI(
                    "mongodb+srv://admin:admin@gettingstarted.zvedt.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase database = mongoClient.getDatabase("lab5");
             collection = database.getCollection("datos");

        }catch (Exception e){
            System.out.println("No Funciono la conexion");
            e.printStackTrace();

        }

    }



    public Document get(){
        Document query = new Document("_id", new ObjectId("604a8718c2362e13ba10bf76"));
        Document result = collection.find(query).iterator().next();
        return result;
    }

    public void add(String val){
        Document query = new Document();
        String date = GeneradorFecha.fecha();
        query.append("dato",val);
        query.append("fecha",date);

        collection.insertOne(query);

    }

    public String getCollection(){
        FindIterable<Document> f =collection.find();
        List<String> listDatos=new ArrayList<>();
        List<String> listFechas=new ArrayList<>();
        f.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                listDatos.add(document.getString("dato"));
                listFechas.add(document.getString("fecha"));

            }


        });
        return process(listDatos,listFechas);
    }

    private String process(List<String> datos,List<String> fecha){
        List<List<String>> list=new ArrayList<>();
        String json="[";
        if(datos.size()<10){
            for(int i=0;i<datos.size();i++){
                if(i!=datos.size()-1){
                    json=json+"{\"dato\":\""+datos.get(i)+"\",\"fecha\":\""+fecha.get(i)+"\"},";

                }
                else{
                    json=json+"{\"dato\":\""+datos.get(i)+"\",\"fecha\":\""+fecha.get(i)+"\"}";
                }
            }

        }
        else{
            for(int i=datos.size()-10;i<datos.size();i++){
                if(i!=datos.size()-1){
                    json=json+"{\"dato\":\""+datos.get(i)+"\",\"fecha\":\""+fecha.get(i)+"\"},";
                }
                else{
                    json=json+"{\"dato\":\""+datos.get(i)+"\",\"fecha\":\""+fecha.get(i)+"\"}";
                }
            }
        }
        json=json+"]";
        System.out.println(json);
        return json;
    }




}
