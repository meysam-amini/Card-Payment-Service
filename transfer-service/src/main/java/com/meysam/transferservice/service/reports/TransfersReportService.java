package com.meysam.transferservice.service.reports;

import com.meysam.transferservice.model.dto.TransferReportDto;
import reactor.core.publisher.Flux;

public interface TransfersReportService<T> {
    Flux<TransferReportDto> transfersReports(T object);

}