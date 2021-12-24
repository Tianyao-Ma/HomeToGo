package com.mty.hometogo.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mty.hometogo.exception.GCSUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ImageStorageService {
    @Value("${gcs.bucket}")
    private String bucketName;

    public String save(MultipartFile file) throws GCSUploadException {
        Credentials credentials = null;
        try {
            credentials = GoogleCredentials.fromStream(getClass().getClassLoader().getResourceAsStream("credentials.json"));
        } catch (IOException e) {
            throw new GCSUploadException("Failed to load GCP credentials");
        }
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        String filename = UUID.randomUUID().toString();
        BlobInfo blobInfo = null;
        try {
            blobInfo = storage.createFrom(
                    BlobInfo
                            .newBuilder(bucketName, filename)
                            .setContentType("image/jpeg")
                            // open access to image as all users. So that the front end will able to access it.
                            // If we only want frontend has access, we will create a key-pair, and Acl.users.of(credentials)
                            .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                            .build(),
                    file.getInputStream());
        } catch (IOException e) {
            throw new GCSUploadException("Failed to upload images to GCS");
        }
        return blobInfo.getMediaLink();
    }
}

