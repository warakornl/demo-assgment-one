package com.scb.assignmentone.batchprocessing;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.scb.assignmentone.model.UserBatch;

import javax.sql.DataSource;
import java.util.UUID;


@Configuration
public class BatchStep1Config {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean("step1ItemReader")
    public ItemReader<UserBatch> fileReader() {
        return new FlatFileItemReaderBuilder<UserBatch>()
                .resource(new ClassPathResource("userInfo.csv"))
                .name("file-reader")
                .targetType(UserBatch.class)
                .delimited().delimiter(",")
                .names(new String[]{"name", "email", "phone", "password"})
                .build();
    }

    @Bean("step1ItemWriter")
    public ItemWriter<UserBatch> dbWriter() {

        return new JdbcBatchItemWriterBuilder<UserBatch>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO \"user\" (id, name, email, phone, password, created_datetime) VALUES (:id, :name, :email, :phone, :password, now())")
                .build();
    }


    @Bean("step1ItemProcessor")
    public ItemProcessor<UserBatch, UserBatch> processor() {

        return new ItemProcessor<UserBatch, UserBatch>() {
            @Override
            public UserBatch process(UserBatch item) {
                String userId = UUID.randomUUID().toString();
                return UserBatch.builder()
                        .id(userId)
                        .name(item.getName())
                        .email(item.getEmail())
                        .password(item.getPassword())
                        .phone(item.getPhone())
                        .build();
            }
        };
    }

    @Bean
    public Step step1(ItemReader<UserBatch> step1ItemReader,
                      ItemProcessor<UserBatch, UserBatch> step1ItemProcessor,
                      ItemWriter<UserBatch> step1ItemWriter) {
        return stepBuilderFactory.get("Step1 - Import User Data")
                .<UserBatch, UserBatch>chunk(1000)
                .reader(step1ItemReader)
                .processor(step1ItemProcessor)
                .writer(step1ItemWriter)
                .build();
    }

}
