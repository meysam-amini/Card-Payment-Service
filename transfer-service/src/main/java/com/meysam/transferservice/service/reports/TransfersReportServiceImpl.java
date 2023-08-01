package com.meysam.transferservice.service.reports;

import com.meysam.transferservice.model.dto.TransferDates;
import com.meysam.transferservice.model.dto.TransferReportDto;
import com.meysam.transferservice.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class TransfersReportServiceImpl implements TransfersReportService<TransferDates> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransfersReportServiceImpl.class);


    private final TransferRepository transferRepository;
    private final ModelMapper modelMapper;
    private final DatabaseClient databaseClient;


    @Override
    public Flux<TransferReportDto> transfersReports(TransferDates transferDates) {

        LOGGER.info(String.valueOf(LocalDate.parse(transferDates.getDate1())));
            return this.databaseClient
                    .sql("SELECT status , count(status) from (SELECT * FROM transfer WHERE transfer.date BETWEEN SYMMETRIC :date1 AND :date2) A GROUP BY status")
                    .bind("date1", LocalDate.parse(transferDates.getDate1()) )
                    .bind("date2",  LocalDate.parse(transferDates.getDate2()) )
                    .fetch()
                    .all().map(transferReportDto-> modelMapper.map(transferReportDto,TransferReportDto.class));
    }
}
