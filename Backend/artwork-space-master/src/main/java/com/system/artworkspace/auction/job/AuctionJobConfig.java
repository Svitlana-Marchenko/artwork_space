package com.system.artworkspace.auction.job;

import com.system.artworkspace.ArtworkSpaceApplication;
import com.system.artworkspace.auction.Auction;
import com.system.artworkspace.auction.AuctionEntity;
import com.system.artworkspace.auction.AuctionMapper;
import com.system.artworkspace.auction.AuctionRepository;
import com.system.artworkspace.auction.Sale.Sale;
import com.system.artworkspace.auction.Sale.SaleService;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class AuctionJobConfig {

    @Autowired
    private AuctionRepository auctionRepository;

    @Bean(name = "auctionClosingJob")
    public Job auctionClosingJob(JobRepository jobRepository,
                                 Step deleteClosingAuctionsStep,
                                 @Qualifier("formSale") Step formSaleStep) {
        return new JobBuilder("auctionClosingJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(deleteClosingAuctionsStep)
                .next(formSaleStep)
                .build();
    }

    @Bean(name = "deleteClosingAuctionsTasklet")
    protected Tasklet deleteClosingAuctionsTasklet() {
        return (contribution, chunkContext) -> {
            List<AuctionEntity> closingAuctions = auctionRepository.findClosingTodayWithNoBuyer(new Date(), PageRequest.of(0, 10));
            auctionRepository.deleteAllById(closingAuctions.stream().map(AuctionEntity::getId).collect(Collectors.toList()));
            for (AuctionEntity auction : closingAuctions) {
                log.info("Deleted auction with ID: {}", auction.getId());
            }
            return RepeatStatus.FINISHED;
        };
    }
    @Bean("deleteClosingAuctionsStep")
    public Step deleteClosingAuctionsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("deleteClosingAuctionsStep", jobRepository)
                .tasklet(deleteClosingAuctionsTasklet(), transactionManager)
                .build();
    }

    @Bean(name = "formSale")
    protected Step formSale(JobRepository jobRepository, PlatformTransactionManager transactionManager, @Qualifier("formingSaleReader") ItemReader<AuctionEntity> reader, @Qualifier("formingSaleProcessor") ItemProcessor<AuctionEntity, AuctionEntity> processor, @Qualifier("formingSaleWriter") ItemWriter<AuctionEntity> writer) {
        return new StepBuilder("formSale", jobRepository).<AuctionEntity,AuctionEntity> chunk(2, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
