
echo "Exporting GCLOUD_PROJECT and GCLOUD_BUCKET"
export GCLOUD_PROJECT=$DEVSHELL_PROJECT_ID
export GCLOUD_BUCKET=$DEVSHELL_PROJECT_ID-media

echo "Creating quiz-account Service Account"
gcloud iam service-accounts create quiz-account --display-name "Quiz Account"
gcloud iam service-accounts keys create key.json --iam-account=quiz-account@$DEVSHELL_PROJECT_ID.iam.gserviceaccount.com
gcloud projects add-iam-policy-binding $DEVSHELL_PROJECT_ID --member serviceAccount:quiz-account@$DEVSHELL_PROJECT_ID.iam.gserviceaccount.com --role roles/owner
export GOOGLE_APPLICATION_CREDENTIALS=key.json

echo "Creating Cloud Pub/Sub topics"
gcloud pubsub topics create feedback
gcloud pubsub topics create answers

echo "Creating Cloud Spanner Instance, Database, and Tables"
gcloud spanner instances create quiz-instance --config=regional-us-central1 --description="Quiz instance" --nodes=1
gcloud spanner databases create quiz-database --instance quiz-instance --ddl "CREATE TABLE Feedback ( feedbackId STRING(100) NOT NULL, email STRING(100), quiz STRING(20), feedback STRING(MAX), rating INT64, score FLOAT64, timestamp INT64 ) PRIMARY KEY (feedbackId); CREATE TABLE Answers (answerId STRING(100) NOT NULL, id INT64, email STRING(60), quiz STRING(20), answer INT64, correct INT64, timestamp INT64) PRIMARY KEY (answerId DESC);"

echo "Project ID: $DEVSHELL_PROJECT_ID"
