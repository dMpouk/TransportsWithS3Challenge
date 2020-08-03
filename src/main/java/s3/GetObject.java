package s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetObject {

  private static final Logger LOGGER = LoggerFactory.getLogger(GetObject.class);

  public static String getSelectedObject(String bucketName, String key) throws IOException {
    Regions clientRegion = Regions.EU_WEST_3;

    S3Object fullObject = null, objectPortion = null, headerOverrideObject = null;
    try {
      BasicAWSCredentials awsCreds =
          new BasicAWSCredentials(getProperty("access.key"), getProperty("secret.access.key"));
      AmazonS3 s3Client =
          AmazonS3ClientBuilder.standard()
              .withRegion(clientRegion)
              .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
              .build();

      // Get an object and print its contents.
      System.out.println("Downloading an object");
      fullObject = s3Client.getObject(new GetObjectRequest(bucketName, key));
      System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
      return getResultFromInputStream(fullObject.getObjectContent());

    } catch (AmazonServiceException e) {
      // The call was transmitted successfully, but Amazon S3 couldn't process
      // it, so it returned an error response.
      LOGGER.error(
          "Error, the call was transmitted successfully, but Amazon S3 couldn't process it. {}",
          e.getMessage());
      throw new RuntimeException(
          "Error, the call was transmitted successfully, but Amazon S3 couldn't process it."
              + e.getMessage());
    } catch (SdkClientException e) {
      // Amazon S3 couldn't be contacted for a response, or the client
      // couldn't parse the response from Amazon S3.
      LOGGER.error("Error, couldn't parse the response from Amazon S3. {}", e.getMessage());
      throw new RuntimeException(
          "Error, couldn't parse the response from Amazon S3." + e.getMessage());
    } finally {
      // To ensure that the network connection doesn't remain open, close any open input streams.
      if (fullObject != null) {
        fullObject.close();
      }
      if (objectPortion != null) {
        objectPortion.close();
      }
      if (headerOverrideObject != null) {
        headerOverrideObject.close();
      }
    }
  }

  private static String getResultFromInputStream(InputStream input) {
    return new BufferedReader(new InputStreamReader(input))
        .lines()
        .collect(Collectors.joining("\n"));
  }

  public static String getProperty(String propertyKey) {
    try (InputStream input =
        GetObject.class.getClassLoader().getResourceAsStream("aws-config.properties")) {
      Properties prop = new Properties();
      if (input == null) {
        throw new RuntimeException("Unable to find aws-config.properties");
      }
      prop.load(input);
      return prop.getProperty(propertyKey);
    } catch (IOException e) {
      throw new RuntimeException("Error while getting properties");
    }
  }
}
