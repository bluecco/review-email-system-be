# review-email-system-be
The backend is developed using Java Spring boot.
The db is a postgresql db.

CORS and resttemplate are defined into the Config file (WebConfig.class)


com.review.email.domain
EmailReview - Model used for the reviews
EmailReviewRepository - PagingAndSorting repository (JPA)

com.review.email.service
EmailReviewService - interface for the service
EmailReviewServiceImpl - implementation of the service. Used by the controller to access to the repository

com.review.email.web
EmailReviewController - web controller that maps all the requests:
  - "/" just a string "review email system api" :)
  - POST "/reviews" - called by the lambda function to save an email review to the db
  - GET  "/reviews" - get all the reviews (paginated and sorted by timestamp)
  - GET  "reviews/published" - get all the published reviews
  - PUT  "/reviews/{messageId}/publish" - publish or unpublish a message
  - PUT  "/reviews/{messageId}/analyze" - call azure to get the sentiment score

  com.review.email.web.azure
  Maps the input required by azure for the sentiment analysis
  Maps also the output given

  application.properties
  Properties needed by the application.
  Also has the azure api-key and endpoint.
  Usually I would not commit those sensitive data, but it only to show you
