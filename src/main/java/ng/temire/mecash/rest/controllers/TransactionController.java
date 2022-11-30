package ng.temire.mecash.rest.controllers;

import lombok.RequiredArgsConstructor;
import ng.temire.mecash.rest.request.TransactionRecordRequest;
import ng.temire.mecash.rest.request.TransactionRequest;
import ng.temire.mecash.rest.response.GenericResponseDTO;
import ng.temire.mecash.service.TransactionService;
import ng.temire.mecash.service.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    TransactionService service;


    @PostMapping("/user-transactions")
    public ResponseEntity<GenericResponseDTO> retrieveUserTxns(@RequestBody TransactionRecordRequest request, Pageable pageable){
        GenericResponseDTO response = service.findUserTxns(request,pageable);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/user-transactions/all")
    public ResponseEntity<GenericResponseDTO> retrieveAllTxns(@RequestBody TransactionRecordRequest request, Pageable pageable){
        GenericResponseDTO response = service.findAllTxns(request,pageable);
        return new ResponseEntity<>(response, response.getStatus());
    }


    @PostMapping("/send")
    public ResponseEntity<GenericResponseDTO> sendMoney(@RequestBody TransactionRequest request){
        GenericResponseDTO response = service.send(request);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
