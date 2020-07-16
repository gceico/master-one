/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  https://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;

import java.util.List;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * <p>
 * Prerequisites: You must have a valid Amazon Web Services developer account,
 * and be signed up to use Amazon SQS. For more information about Amazon SQS,
 * see https://aws.amazon.com/sqs
 * <p>
 * Make sure that your credentials are located in ~/.aws/credentials
 */
public class SqsReceiver {
        public static void main(String[] args) {
                /*
                 * Create a new instance of the builder with all defaults (credentials and
                 * region) set automatically. For more information, see Creating Service Clients
                 * in the AWS SDK for Java Developer Guide.
                 */
                final AmazonSQS sqs = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

                try {
                        // Initial que
                        final GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest("my-sqs");
                        final String myQueueUrl = sqs.getQueueUrl(getQueueUrlRequest).getQueueUrl();
                        System.out.println("My queue: " + myQueueUrl);

                        // Receive messages.
                        System.out.println("Receiving messages from MyQueue.\n");
                        final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
                        final List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
                        for (final Message message : messages) {
                                System.out.println("Body:          " + message.getBody());
                                //Send received messages to Slack
                                SlackMessage slackMessage = SlackMessage.builder().channel("Me")
                                                .username("Me").text(message.getBody())
                                                .icon_emoji(":twice:").build();

                                SlackUtils.sendMessage(slackMessage);
                        }
                        System.out.println();

                        // Delete the messages.
                        System.out.println("Deleting the messages that we received.\n");
                        for (final Message message : messages) {
                                sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, message.getReceiptHandle()));
                        }

                        // Delete the queue.
                        System.out.println("Deleting the test queue.\n");
                        sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));
                } catch (final AmazonServiceException ase) {
                        System.out.println("Caught an AmazonServiceException, which means "
                                        + "your request made it to Amazon SQS, but was "
                                        + "rejected with an error response for some reason.");
                        System.out.println("Error Message:    " + ase.getMessage());
                        System.out.println("HTTP Status Code: " + ase.getStatusCode());
                        System.out.println("AWS Error Code:   " + ase.getErrorCode());
                        System.out.println("Error Type:       " + ase.getErrorType());
                        System.out.println("Request ID:       " + ase.getRequestId());
                } catch (final AmazonClientException ace) {
                        System.out.println("Caught an AmazonClientException, which means "
                                        + "the client encountered a serious internal problem while "
                                        + "trying to communicate with Amazon SQS, such as not "
                                        + "being able to access the network.");
                        System.out.println("Error Message: " + ace.getMessage());
                }
        }
}
