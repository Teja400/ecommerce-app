package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.model.DatabaseSequences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
public class SequenceGeneratorService {


    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Integer generateSequence(String seqName) {

        DatabaseSequences counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequences.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }
}

//public class SequenceGeneratorService {

//    public static String generateSequence(Db db, String seq_name) {
//        String sequence_collection = "database_sequences";
//        String sequence_field = "seq";
//
//        DBCollection seq = db.(sequence_collection);
//
//        DBObject query = new BasicDBObject();
//        query.put("id", seq_name);
//
//        DBObject change = new BasicDBObject(sequence_field, 1);
//        DBObject update = new BasicDBObject("$inc", change);
//
//        DBObject res = seq.findAndModify(query, new BasicDBObject(), new BasicDBObject(), false, update, true, true);
//        return res.get(sequence_field).toString();
//    }
//
//    public static String generateSequence(String seq) {
//        DatabaseSequences counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
//                new Update().inc("seq",1), options().returnNew(true).upsert(true),
//                DatabaseSequences.class);
//        return !Objects.isNull(counter) ? counter.getSeq() : 1;
//    }
//}

