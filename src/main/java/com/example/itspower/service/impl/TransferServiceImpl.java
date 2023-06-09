package com.example.itspower.service.impl;

import com.example.itspower.repository.TransferRepository;
import com.example.itspower.response.transfer.TransferResponseGroup;
import com.example.itspower.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {
    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<TransferResponseGroup> findGroupIdAndTransferDate(int groupId,String reportDate) throws ParseException {
        return transferRepository.findGroupIdAndTransferDate(groupId, reportDate);
    }

    @Override
    public void updateTransferGroup(boolean isAccess, int groupId, String transferDate) {
        try {
            transferRepository.updateTransferGroup(isAccess, groupId, transferDate);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
