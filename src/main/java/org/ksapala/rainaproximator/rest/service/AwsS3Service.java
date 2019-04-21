package org.ksapala.rainaproximator.rest.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

/**
 * @author krzysztof
 */
@Component
public class AwsS3Service {

    private final Logger logger = LoggerFactory.getLogger(AwsS3Service.class);

    private final String BUCKET_CREDENTIALS = "rain-timer-firebase";
    private final String KEY_CREDENTIALS = "rain-timer-firebase-adminsdk-mqi3i-5db48d95f6.json";
    private final Regions REGION = Regions.US_EAST_2;

    public InputStream getFirebaseCredentialsFile() {
        try {
            return doGetFirebaseCredentialsFile();
        } catch (Exception e) {
            logger.error("Exception getting firebase credentials.", e);
            throw e;
        }
    }

    public InputStream doGetFirebaseCredentialsFile() {
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(REGION)
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .build();

        S3Object object = s3Client.getObject(new GetObjectRequest(BUCKET_CREDENTIALS, KEY_CREDENTIALS));
        S3ObjectInputStream objectContent = object.getObjectContent();
        return Optional.ofNullable(objectContent)
                .orElseThrow(() -> new RuntimeException("S3 Object Content is null"));
    }
}
