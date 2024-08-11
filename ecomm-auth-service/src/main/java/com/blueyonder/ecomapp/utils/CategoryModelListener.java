package com.blueyonder.ecomapp.utils;


import com.blueyonder.ecomapp.model.UserRequest;
import com.blueyonder.ecomapp.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class CategoryModelListener extends AbstractMongoEventListener<UserRequest> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public CategoryModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<UserRequest> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(UserRequest.SEQUENCE_NAME));
        }
    }

}
