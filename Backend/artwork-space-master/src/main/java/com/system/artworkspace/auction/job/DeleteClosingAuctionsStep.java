package com.system.artworkspace.auction.job;

import com.system.artworkspace.auction.AuctionEntity;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DeleteClosingAuctionsStep {

    @Bean(name = "deleteClosingAuctions")
    protected Step deleteClosingAuctions(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("closingAuctionsReader") ItemReader<AuctionEntity> reader, @Qualifier("closingAuctionsProcessor") ItemProcessor<AuctionEntity, AuctionEntity> processor, @Qualifier("closingAuctionsWriter") ItemWriter<AuctionEntity> writer) {
        return new StepBuilder("deleteClosingAuctions", jobRepository).<AuctionEntity,AuctionEntity> chunk(2, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
