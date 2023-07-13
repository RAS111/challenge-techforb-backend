package com.challenge.techforb.auth.service.seeder;

import com.challenge.techforb.entity.Status;
import com.challenge.techforb.entity.TypeTransaction;
import com.challenge.techforb.enums.StatusName;
import com.challenge.techforb.enums.TypeTransactionName;
import com.challenge.techforb.repository.IStatusRepository;
import com.challenge.techforb.repository.ITypeTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StartData {

    private final ITypeTransactionRepository iTypeTransactionRepository;
    private final IStatusRepository iStatusRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        List<TypeTransaction> type = iTypeTransactionRepository.findAll();
        List<Status> status = iStatusRepository.findAll();
        if (type.isEmpty()) {
            this.createTypeTransaction();
        }
        if(status.isEmpty()){
            this.createStatus();
        }
    }

    private void createTypeTransaction(){
        this.createType(1L, TypeTransactionName.MAKE_TRANSFER);
        this.createType(2L, TypeTransactionName.DEPOSIT);
        this.createType(3L, TypeTransactionName.WITHDRAW);
        this.createType(4L, TypeTransactionName.INCOME);
        this.createType(5L, TypeTransactionName.PAYMENT);
    }

    private void createType(long l, TypeTransactionName name) {
        TypeTransaction type = new TypeTransaction();
        type.setTypeTransactionId(l);
        type.setTypeName(name.getName());
        iTypeTransactionRepository.save(type);
    }

    private void createStatus() {
        this.createState(1L, StatusName.CANCELLED);
        this.createState(2L, StatusName.PENDING);
        this.createState(3L, StatusName.COMPLETE);
    }

    private void createState(long l, StatusName name) {
        Status status = new Status();
        status.setStatusId(l);
        status.setName(name.getName());
        iStatusRepository.save(status);
    }

}
