package fastcampus.group9.toyproject3.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    @Transactional
    public void saveReport(Report report){
        reportRepository.save(report);
    }
}
