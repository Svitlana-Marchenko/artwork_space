package com.system.artworkspace.auction;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.auction.AuctionEntity;
import com.system.artworkspace.auction.AuctionRepository;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
public class AuctionJobConfig {

    @Autowired
    private AuctionRepository auctionRepository;
    static final Logger logger = LoggerFactory.getLogger(ArtworkSpaceApplication.class);

    @Bean(name = "auctionClosingJob")
    public Job auctionClosingJob(JobRepository jobRepository, Step deleteClosingAuctionsStep) {
        return new JobBuilder("auctionClosingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(deleteClosingAuctionsStep)
                .build();
    }

    @Bean(name = "deleteClosingAuctions")
    protected Step deleteClosingAuctions(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemReader<AuctionEntity> reader, ItemProcessor<AuctionEntity, AuctionEntity> processor, ItemWriter<AuctionEntity> writer) {
        return new StepBuilder("deleteClosingAuctions", jobRepository).<AuctionEntity,AuctionEntity> chunk(2, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    @Bean(name = "closingAuctionsReader")
    @StepScope
    public ItemReader<AuctionEntity> closingAuctionsReader() {
        Date currentDate = new Date();

        return new RepositoryItemReaderBuilder<AuctionEntity>()
                .name("closingAuctionsReader")
                .methodName("findClosingTodayWithNoBuyer")
                .repository(auctionRepository)
                .arguments(new Object[]{currentDate})
                .pageSize(10)
                .build();
    }


    @Bean(name = "closingAuctionsProcessor")
    public ItemProcessor<AuctionEntity, AuctionEntity> closingAuctionsProcessor() {
        return auctionEntity -> {
            auctionRepository.deleteById(auctionEntity.getId());
            return auctionEntity;
        };
    }

    @Bean(name = "closingAuctionsWriter")
    public ItemWriter<AuctionEntity> closingAuctionsWriter() {
        return items -> {
            for (AuctionEntity auction : items) {
                logger.info("Deleted auction with ID: {}", auction.getId());
            }
        };
    }


}
