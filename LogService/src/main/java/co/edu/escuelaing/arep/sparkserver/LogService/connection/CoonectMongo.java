package co.edu.escuelaing.arep.sparkserver.LogService.connection;

import co.edu.escuelaing.arep.sparkserver.LogService.util.GeneradorFecha;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CoonectMongo {
    MongoClient mongoClient = new MongoClient("192.168.0.22");


    public void add(String value){
        MongoDatabase db = mongoClient.getDatabase("lab5");
        MongoCollection collection = db.getCollection("datos");
        Document doc =new Document();
        doc.append("dato",value);
        doc.append("fecha", GeneradorFecha.fecha());
        collection.insertOne(doc);
    }

    public String get(){
        MongoDatabase db = mongoClient.getDatabase("lab5");
        MongoCollection collection = db.getCollection("datos");
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
        return json;
    }

}
