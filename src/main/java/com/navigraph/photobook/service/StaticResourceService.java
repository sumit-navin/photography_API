package com.navigraph.photobook.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.navigraph.photobook.util.AwsClientFactory;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class StaticResourceService {
	public void download(String key, String bucket) {
		AmazonS3 s3Client = AwsClientFactory.s3();
		S3Object object = s3Client.getObject(
				new GetObjectRequest(
						bucket,
						key
				));
		return object.getObjectContent();
	}

	public void upload(String key, String bucket, File resource) {
		AmazonS3 s3Client = AwsClientFactory.s3();
		s3Client.putObject(
				new PutObjectRequest(
						bucket,
						key,
						resource
				));
	}
}
