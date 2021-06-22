package Model;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.regex.Pattern;

/**
 * MongoConnection is the class that manages the connection to the database it inserts documents that are already parsed
 * and send search queries to find info in the mongo database
 */
public class MongoConnnection {
    private final String connectionString;
    private Document document ;

    public MongoConnnection() {
        this.connectionString = "mongodb://localhost:27017";
        this.document = new Document();
    }

    /**
     * The goal of this method is to append strings into a document to later be inserted into the database
     * @param S1 This string will be the field name in the document
     * @param S2 This string si the value of the field or the text
     */
    public void append(String S1, String S2)
    {
        document.append(S1,S2);
    }

    /**
     * This method connects to the database and insert the document we have been adding strings to.
     */
    public void connect()
    {
        try(MongoClient mongoClient = MongoClients.create(connectionString))
        {
            MongoDatabase database = mongoClient.getDatabase("WebMetaData");
            database.getCollection("WebMetaData").insertOne(document);
            System.out.println("Document inserted successfully");
            document.clear();
            document = new Document();
        }
    }


    /**
     * The method connects to the database and searches for a specific text in a specific field using the regex syntax
     * @param query This is the string or text the user gave us to search in the DB
     * @param field This is the field where the search will occur
     * @return Here we return the link of websites where we found the text entered by the user
     */
    public String fullTextSearch(String query,String field) {

        String URL = new String();
        try(MongoClient mongoClient = MongoClients.create(connectionString)) {

            MongoDatabase database = mongoClient.getDatabase("WebMetaData");
            MongoCollection<Document> collection = database.getCollection("WebMetaData");
            collection.dropIndexes();

            Pattern regex = Pattern.compile(query,  Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

            BasicDBObject queryy = new BasicDBObject(field, regex);
            collection.createIndex(new Document(field, "text"), new IndexOptions());
            FindIterable<Document> cursor = collection.find(queryy);

            for(Document doc : cursor) {
                System.out.println(doc);
                URL += doc.getString("URL");
                URL += "\n";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(URL);
        return URL;
    }
}
