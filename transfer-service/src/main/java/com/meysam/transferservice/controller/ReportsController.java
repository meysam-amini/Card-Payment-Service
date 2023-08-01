package com.meysam.transferservice.controller;

import com.meysam.transferservice.model.dto.TransferDates;
import com.meysam.transferservice.model.dto.TransferReportDto;
import com.meysam.transferservice.service.reports.TransfersReportService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/transfers-report")
@RequiredArgsConstructor
public class ReportsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferController.class);

    private final TransfersReportService transfersReportService;

    @PostMapping()
    public Flux<TransferReportDto> reportTransfers(@RequestBody TransferDates transferDates){

        LOGGER.info("Getting Reports Between: "+transferDates.toString());

        return transfersReportService.transfersReports(transferDates);
    }
}
